package io.colby.modules.routes.plants.controller;


import io.colby.modules.auth.MetaID;
import io.colby.modules.auth.Token;
import io.colby.modules.auth.model.AuthenticationModel;
import io.colby.modules.auth.model.AuthorizationModel;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.modules.routes.plants.model.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//TODO change authentication and authorization requests from direct model call to service call
//TODO update documentation to reflect changes in response JSON not being a map, and removing plant sensors from response
//TODO change endpoint from /sensor/enclosure to /enclosure
@RestController
public class PlantController {

//    final
//    private PlantRepository repository;


//    @Autowired
//    public PlantController(PlantRepository repository) {
//        this.repository = repository;
//    }

    @Autowired
    PlantRepository plantRepository;

    @RequestMapping(value = {"/plants/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Plant> getSingleEnclosure(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Token token = new Token(auth);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

//        TODO change to repo
        if (!new AuthorizationModel().userHaveAccessToEnclosure(metaId, id)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(plantRepository.findByPlantId(id));

    }

    @RequestMapping(value = {"/plants"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<Plant>> getAllPlants(
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

//        TODO in auth or meta repo, get list of all enclosures associated with token/meta id. Then iterate and find

        return CompletableFuture.completedFuture(plantRepository.findAll());

    }


    @RequestMapping(value = {"/plants"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Plant> createPlant(
            @Valid @RequestBody Plant request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        int enclosureId = Optional.of(plantRepository.save(request)).orElse(new Plant(0)).getPlantId();

        if (enclosureId > 0)
            response.setStatus(HttpServletResponse.SC_CREATED);

        return CompletableFuture.completedFuture(plantRepository.findByPlantId(enclosureId));

    }

    @RequestMapping(value = {"/plants/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<String> deletePlant(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {


        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        if (auth.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!new AuthorizationModel().userHaveAccessToEnclosure(metaId, id)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(null);
        }

        plantRepository.deleteById(id);

        if (plantRepository.findById(id).isPresent()) {
            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete enclosure\", \"deleted\": \"false\", \"enclosure-id\": " + id + "}");
        }

        return CompletableFuture.completedFuture("{\"message\": \"enclosure deleted successfully\", \"deleted\": \"true\", \"enclosure-id\": " + id + "}");

    }

}
