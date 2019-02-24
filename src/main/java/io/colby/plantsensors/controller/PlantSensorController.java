package io.colby.plantsensors.controller;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import io.colby.enclosuresensors.controller.EnclosureSensorCreateRequest;
import io.colby.enclosuresensors.controller.EnclosureSensorCreateResponse;
import io.colby.enclosuresensors.controller.EnclosureSensorDeleteResponse;
import io.colby.enclosuresensors.controller.EnclosureSensorGetResponse;
import io.colby.enclosuresensors.model.EnclosureSensorModel;
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
    public CompletableFuture<List<PlantSensorGetResponse>> getAllPlantSensors(@RequestHeader(value = "Authorization") String auth,
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

        arrResp.addAll(new PlantSensorModel().getAllEnclosures(metaId));

        return CompletableFuture.completedFuture(arrResp);


    }

    @RequestMapping(value = {"/sensor/plant/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<EnclosureSensorGetResponse>> getSinglePlantSensor(@PathVariable("id") int id,
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

    @RequestMapping(value = {"/sensor/plant"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<EnclosureSensorCreateResponse>> createPlantSensor(
            @Valid @RequestBody ArrayList<EnclosureSensorCreateRequest> request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        ArrayList<EnclosureSensorCreateResponse> arrResp = new ArrayList<>();

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

        Token token = new Token(tokenParam);
        MetaID metaId = new AuthenticationModel().getFromToken(token);

        if (!metaId.doesExist()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(arrResp);
        }

        System.out.println(request);

        arrResp.addAll(new EnclosureSensorModel().createEnclosureSensors(metaId, request));


        return CompletableFuture.completedFuture(arrResp);

    }

    @RequestMapping(value = {"/sensor/plant/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<EnclosureSensorDeleteResponse> deletePlantSensor(
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

//    @RequestMapping(value = {"/sensors/plant"},
//            method = RequestMethod.POST,
//            consumes = "application/json")
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<U> createPlantSensor(@Valid @RequestBody Map<String, List<PlantSensorCreateRequest>> request,
//                                                    @RequestHeader(value = "Authorization") String auth,
//                                                     HttpServletResponse response
//    ) {
//
//
//        @NotBlank(message = "Authorization header cannot be blank")
//        String tokenParam = auth;
//
//        //TODO replace with real auth check
//        //Token token = new Token(tokenParam);
//        //MetaID metaId = new APIMeta().getFromToken(token);
//        if (auth.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return (CompletableFuture<U>) CompletableFuture.completedFuture(new SensorControllerError());
//        }
//
//
//        Map<String, PlantSensorCreateResponse[]> plantMapResp = new HashMap<>();
//
//        //TODO get User obj that contains ID associated w/ token
//        //TODO validate id
//        plantMapResp.put("plant", new PlantSensorCreateResponse[]{new PlantSensorCreateResponse()});
//
//        return (CompletableFuture<U>) CompletableFuture.completedFuture(plantMapResp);
//
//    }
//
//
//
//    @RequestMapping(value = {"/sensors/plant/{id}"},
//            method = RequestMethod.GET)
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<U> getSinglePlantSensor(@PathVariable("id") int id,
//                                             @RequestHeader(value = "Authorization") String auth,
//                                             HttpServletResponse response
//    ) {
//
//
//        @NotBlank(message = "Authorization header cannot be blank")
//        String tokenParam = auth;
//
//        //TODO replace with real auth check
//        //Token token = new Token(tokenParam);
//        //MetaID metaId = new APIMeta().getFromToken(token);
//        if (auth.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return (CompletableFuture<U>) CompletableFuture.completedFuture(new SensorControllerError());
//        }
//
//
//        Map<String, PlantSensorControllerResponse[]> plantMapResp = new HashMap<>();
//
//        //TODO get User obj that contains ID associated w/ token
//        //TODO validate id
//        plantMapResp.put("plant", new PlantSensorControllerResponse[]{new PlantSensorModel().getSinglePlantSensor(id)});
//
//        return (CompletableFuture<U>) CompletableFuture.completedFuture(plantMapResp);
//
//    }
//
//    @RequestMapping(value = {"/sensors/plant"},
//            method = RequestMethod.GET)
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<U> getAllPlantSensor(@RequestHeader(value = "Authorization") String auth,
//                                               HttpServletResponse response
//    ) {
//
//
//        @NotBlank(message = "Authorization header cannot be blank")
//        String tokenParam = auth;
//
//        //TODO replace with real auth check
//        //Token token = new Token(tokenParam);
//        //MetaID metaId = new APIMeta().getFromToken(token);
//        if (auth.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return (CompletableFuture<U>) CompletableFuture.completedFuture(new SensorControllerError());
//        }
//
//
//        Map<String, List<PlantSensorControllerResponse>> plantMapResp = new HashMap<>();
//
//        //TODO get User obj that contains ID associated w/ token
//        plantMapResp.put("plant", new PlantSensorModel().getAllPlantSensors(0));
//
//        return (CompletableFuture<U>) CompletableFuture.completedFuture(plantMapResp);
//
//    }
//
//    @RequestMapping(value = {"/test"},
//            method = RequestMethod.POST,
//            consumes = "application/json",
//            produces = APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<U> test(@Valid TestRequest request,
//                                     HttpServletResponse response,
//                                     HttpEntity<String> httpEntity
//    ) {
//
//        String json = httpEntity.getBody();
//
//        System.out.println("GOT A REQUEST");
//        System.out.println(request.toString());
//        System.out.println(json);
//
//
//        return (CompletableFuture<U>) CompletableFuture.completedFuture(null);
//
//    }

}
