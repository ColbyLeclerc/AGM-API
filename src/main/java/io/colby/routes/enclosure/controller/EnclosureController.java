package io.colby.routes.enclosure.controller;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import io.colby.entity.MetaID;
import io.colby.entity.Token;
import io.colby.model.AuthenticationModel;
import io.colby.model.AuthorizationModel;
import io.colby.model.entity.Enclosure;
import io.colby.model.entity.Plant;
import io.colby.model.repository.EnclosureRepository;
import io.colby.model.repository.PlantRepository;
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

//TODO update documentation to reflect changes in response JSON not being a map, and removing plant sensors from response

@RestController
public class EnclosureController {

    @Autowired
    EnclosureRepository enclosureRepository;

    @Autowired
    PlantRepository plantRepository;

    @RequestMapping(value = {"/enclosures/{encId}/plants/"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<Plant>> getAssociatedPlants(
            @PathVariable("encId") int enclosureId,
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

        //TODO change to repo
        if (!new AuthorizationModel().userHaveAccessToEnclosure(metaId, enclosureId)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(null);
        }

//also check for plant access (also, when creating plant-enclosure relationship from plant POST, ensure user has access to enclosure)
//        if (!new AuthorizationModel().userHaveAccessToEnclosure(metaId, enclosureId)) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return CompletableFuture.completedFuture(null);
//        }

        return CompletableFuture.completedFuture(plantRepository.findAllByEnclosureId(enclosureId));

    }

    @RequestMapping(value = {"/enclosures/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Enclosure> getSingleEnclosure(
            @PathVariable("id") int id,
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

        //TODO change to repo
        if (!new AuthorizationModel().userHaveAccessToEnclosure(metaId, id)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(enclosureRepository.findByEnclosureId(id));

    }


    @RequestMapping(value = {"/enclosures"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Enclosure> createEnclosure(
            @Valid @RequestBody Enclosure request,
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

        int enclosureId = Optional.of(enclosureRepository.save(request)).orElse(new Enclosure(0)).getEnclosureId();

        if (enclosureId > 0)
            response.setStatus(HttpServletResponse.SC_CREATED);

        return CompletableFuture.completedFuture(enclosureRepository.findByEnclosureId(enclosureId));

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

        enclosureRepository.deleteById(id);

        if (enclosureRepository.findById(id).isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete enclosure\", \"deleted\": \"false\", \"enclosure-id\": " + id + "}");
        }

        return CompletableFuture.completedFuture("{\"message\": \"enclosure deleted successfully\", \"deleted\": \"true\", \"enclosure-id\": " + id + "}");

    }

}
