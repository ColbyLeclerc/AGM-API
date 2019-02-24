package io.colby.plantsensors.controller;

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
import io.colby.plantsensors.model.PlantSensorModel;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class PlantSensorController {

    @RequestMapping(value = "/sensor/plant",
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<PlantSensorGetResponse>> getAllPlantSensors(
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        ArrayList<PlantSensorGetResponse> arrResp = new ArrayList<>();


        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(arrResp);
        }

        //TODO only get plant sensors user has access to

        arrResp.addAll(new PlantSensorModel().getAllPlants(metaId));

        return CompletableFuture.completedFuture(arrResp);


    }

    @RequestMapping(value = {"/sensor/plant/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<PlantSensorGetResponse>> getSinglePlantSensor(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        ArrayList<PlantSensorGetResponse> arrResp = new ArrayList<>();

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(arrResp);
        }

        if (!new AuthorizationModel().userHaveAccessToPlant(metaId, id)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(arrResp);
        }

        arrResp.add(new PlantSensorModel().getSinglePlant(id));


        return CompletableFuture.completedFuture(arrResp);

    }

    @RequestMapping(value = {"/sensor/plant"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<PlantSensorCreateResponse>> createPlantSensor(
            @Valid @RequestBody ArrayList<PlantSensorCreateRequest> request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        ArrayList<PlantSensorCreateResponse> arrResp = new ArrayList<>();

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(arrResp);
        }

        arrResp.addAll(new PlantSensorModel().createPlantSensors(metaId, request));

        return CompletableFuture.completedFuture(arrResp);

    }

    @RequestMapping(value = {"/sensor/plant/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<PlantSensorDeleteResponse> deletePlantSensor(
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

        if (!new AuthorizationModel().userHaveAccessToPlant(metaId, id)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return CompletableFuture.completedFuture(null);
        }

        if (!new PlantSensorModel().plantSensorExists(id)){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(new PlantSensorDeleteResponse(id, false));
        }

        return CompletableFuture.completedFuture(new PlantSensorModel().deletePlantSensor(id));

    }

}
