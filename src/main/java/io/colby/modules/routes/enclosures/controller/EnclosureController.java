package io.colby.modules.routes.enclosures.controller;


import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.service.AuthService;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.enclosures.repository.EnclosureRepository;
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

/*
Note: @CrossOrigin annotation is to allow a local instance of the frontend
server to ping the api. In production, such architecture would not be used,
as the requests will be going to the same domain.
 */
@RestController
public class EnclosureController {

    @Autowired
    EnclosureRepository enclosureRepository;

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    AuthService authService;

    @RequestMapping(value = {"/enclosures/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Enclosure> getSingleEnclosure(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToEnclosure(authRec.get(), id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        Optional<Enclosure> enclosureSearch = enclosureRepository.findByEnclosureId(id);

        if (!enclosureSearch.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(enclosureSearch.get());

    }

    @RequestMapping(value = {"/enclosures"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    @CrossOrigin(origins = "http://localhost:3000")
    public CompletableFuture<List<Enclosure>> getAllEnclosures(
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

//        response.setHeader("Access-Control-Allow-Origin", "*");

        return CompletableFuture.completedFuture(authRec.get().getEnclosures());

    }


    @RequestMapping(value = {"/enclosures"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    @CrossOrigin(origins = "http://localhost:3000")
    public CompletableFuture<Enclosure> createEnclosure(
            @Valid @RequestBody Enclosure request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        request.setAuthId(authRec.get().getAuthId());

        Enclosure enclosure = enclosureRepository.save(request);
        Optional<Enclosure> optionalEnclosure = enclosureRepository.findByEnclosureId(enclosure.getEnclosureId());

        if (!optionalEnclosure.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

        return CompletableFuture.completedFuture(optionalEnclosure.get());

    }

    @RequestMapping(value = {"/enclosures/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<String> deleteEnclosureSensor(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {


        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToEnclosure(authRec.get(), id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        Optional<Enclosure> enc = enclosureRepository.findByEnclosureId(id);

        if (enc.isPresent() && (enc.get().getPlants().size() != 0 || enc.get().getSensors().size() != 0)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete enclosure. Cannot delete enclosure with associated sensors and/or plants.\", \"deleted\": \"false\", \"enclosure-id\": " + id + "}");
        }

        enclosureRepository.deleteById(id);

        if (enclosureRepository.findById(id).isPresent()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete enclosure\", \"deleted\": \"false\", \"enclosure-id\": " + id + "}");
        }

        return CompletableFuture.completedFuture("{\"message\": \"enclosure deleted successfully\", \"deleted\": \"true\", \"enclosure-id\": " + id + "}");

    }

}
