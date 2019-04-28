package io.colby.modules.routes.plants.controller;


import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.service.AuthService;
import io.colby.modules.routes.plants.model.entity.Plant;
import io.colby.modules.routes.plants.model.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PlantController {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    AuthService authService;

    @RequestMapping(value = {"/plants/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Plant> getSinglePlant(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToPlant(authRec.get(), id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        Optional<Plant> plantSearch = plantRepository.findByPlantId(id);

        if (!plantSearch.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(plantSearch.get());

    }

    @RequestMapping(value = {"/plants"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    @CrossOrigin(origins = "http://localhost:3000")
    public CompletableFuture<List<Plant>> getAllPlants(
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(authRec.get().getPlants());

    }


    @RequestMapping(value = {"/plants"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    @CrossOrigin(origins = "http://localhost:3000")
    public CompletableFuture<Plant> createPlant(
            @Valid @RequestBody Plant request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        request.setAuthId(authRec.get().getAuthId());

        Plant plant = plantRepository.save(request);
        Optional<Plant> plantSearch = plantRepository.findByPlantId(plant.getPlantId());

        if (!plantSearch.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

        return CompletableFuture.completedFuture(plantSearch.get());

    }

    @RequestMapping(value = {"/plants/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    @CrossOrigin(origins = "http://localhost:3000")
    public CompletableFuture<String> deletePlant(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToPlant(authRec.get(), id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        plantRepository.deleteById(id);

        //TODO perhaps create innner class to represent error message?
        if (plantRepository.findById(id).isPresent()) {
            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete plant\", \"deleted\": \"false\", \"plant-id\": " + id + "}");
        }

        return CompletableFuture.completedFuture("{\"message\": \"plant deleted successfully\", \"deleted\": \"true\", \"plant-id\": " + id + "}");

    }

}
