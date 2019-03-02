package io.colby.routes.enclosuresensors.controller;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import io.colby.routes.enclosuresensors.model.EnclosureSensorModel;
import io.colby.entity.MetaID;
import io.colby.entity.Token;
import io.colby.model.AuthenticationModel;
import io.colby.model.AuthorizationModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//TODO update documentation to reflect changes in response JSON not being a map, and removing plant sensors from response

@RestController
public class EnclosureSensorController {

    @RequestMapping(value = "/sensor/enclosure",
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<EnclosureSensorGetResponse>> getAllEnclosureSensors(
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        ArrayList<EnclosureSensorGetResponse> arrResp = new ArrayList<>();


        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(arrResp);
        }

        //TODO only get enclosures user has access to

        arrResp.addAll(new EnclosureSensorModel().getAllEnclosures(metaId));

        return CompletableFuture.completedFuture(arrResp);


    }

    @RequestMapping(value = {"/sensor/enclosure/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<EnclosureSensorGetResponse>> getSingleEnclosureSensor(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        ArrayList<EnclosureSensorGetResponse> arrResp = new ArrayList<>();

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(arrResp);
        }

        if (!new AuthorizationModel().userHaveAccessToEnclosure(metaId, id)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(arrResp);
        }

        arrResp.add(new EnclosureSensorModel().getSingleEnclosure(id));


        return CompletableFuture.completedFuture(arrResp);

    }

    @RequestMapping(value = {"/sensor/enclosure"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<EnclosureSensorCreateResponse> createEnclosureSensor(
            @Valid @RequestBody EnclosureSensorCreateRequest request,
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

        System.out.println(request);

        return CompletableFuture.completedFuture(new EnclosureSensorModel().createEnclosureSensors(metaId, request));

    }

    @RequestMapping(value = {"/sensor/enclosure/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<EnclosureSensorDeleteResponse> deleteEnclosureSensor(
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

        if (!new EnclosureSensorModel().enclosureSensorExists(id)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(new EnclosureSensorDeleteResponse(id, false));
        }

        return CompletableFuture.completedFuture(new EnclosureSensorModel().deleteEnclosureSensor(id));

    }


}
