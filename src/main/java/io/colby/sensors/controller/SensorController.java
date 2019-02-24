package io.colby.sensors.controller;

import io.colby.entity.SensorCategory;
import io.colby.enclosuresensors.controller.EnclosureSensorGetResponse;
import io.colby.sensors.entity.controller.SensorControllerError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */


public class SensorController<U> {


    //TODO break this into the actually doced sections (i.e. /sensor/enclosure should have its own package,
    //TODO along with /sensor/plant and /sensor/ (this class) )


//    @RequestMapping(value = "/sensor/{sensorCategory}",
//            method = RequestMethod.GET)
//    @ResponseBody
//    @Async("asyncExecutor")
    public CompletableFuture<U> sensorsAll(@PathVariable("sensorCategory") String sensorCategory,
                                                              @RequestHeader(value = "Authorization") String auth,
                                                              HttpServletResponse response
    ) {

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

//TODO do authentication check
//        Token token = new Token(tokenParam);
//        MetaID metaId = new APIMeta().getFromToken(token);

//        if (!metaId.doesExist()) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return CompletableFuture.completedFuture(new ValidateRequest());
//        }


        SensorCategory category = SensorCategory.toEnum(sensorCategory);

        switch(category){
            case ENCLOSURE:
                Map<String, List<EnclosureSensorGetResponse>> encMapResp = new HashMap<>();

//                encMapResp.put("enslosure", new EnclosureSensorModel().getEnclosures(1,1));

                return (CompletableFuture<U>) CompletableFuture.completedFuture(encMapResp);
            case PLANT:
                Map<String, List<PlantSensorControllerResponse>> plantMapResp = new HashMap<>();

                //plantMapResp.put("plant", new PlantSensorModel().getPlants(1,1));

                return (CompletableFuture<U>) CompletableFuture.completedFuture(plantMapResp);
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return (CompletableFuture<U>) CompletableFuture.completedFuture(new SensorControllerError());
        }

    }

//    @RequestMapping(value = {"/sensor/{sensorCategory}/{id}"},
//            method = RequestMethod.GET)
//    @ResponseBody
//    @Async("asyncExecutor")
    public CompletableFuture<U> sensorSingle(@PathVariable("sensorCategory") String sensorCategory,
                                                               @PathVariable("id") int id,
                                                               @RequestHeader(value = "Authorization") String auth,
                                                               HttpServletResponse response
    ) {

        @NotBlank(message = "Authorization header cannot be blank")
        String tokenParam = auth;

//TODO do authentication check
//        Token token = new Token(tokenParam);
//        MetaID metaId = new APIMeta().getFromToken(token);

//        if (!metaId.doesExist()) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return CompletableFuture.completedFuture(new ValidateRequest());
//        }


        SensorCategory category = SensorCategory.toEnum(sensorCategory);

        switch(category){
            case ENCLOSURE:
                Map<String, List<EnclosureSensorGetResponse>> encMapResp = new HashMap<>();

//                encMapResp.put("enslosure", new EnclosureSensorModel().getEnclosures(0,1));

                return (CompletableFuture<U>) CompletableFuture.completedFuture(encMapResp);
            case PLANT:
                Map<String, List<PlantSensorControllerResponse>> plantMapResp = new HashMap<>();

                //plantMapResp.put("plant", new PlantSensorModel().getPlants(0,1));

                return (CompletableFuture<U>) CompletableFuture.completedFuture(plantMapResp);
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return (CompletableFuture<U>) CompletableFuture.completedFuture(new SensorControllerError());
        }

    }




    /*
    {
  "enclosures": [
    {
      "sensor-id": 12,
      "type": "temperature-humidity",
      "enclosure-id": 86
    },
    {
      "sensor-id": 13,
      "type": "temperature-humidity",
      "enclosure-id": 86
    },
    {
      "sensor-id": 18,
      "type": "temperature-humidity",
      "enclosure-id": 98
    },
  ],
  "plants": [
    {
      "sensor-id": 33,
      "type": "soil-moisture",
      "plant-id": 222
    },
    {
      "plant-sensor-id": 34,
      "type": "soil-moisture",
      "plant-id": 222
    },
    {
      "plant-sensor-id": 44,
      "type": "soil-temperature",
      "plant-id": 223
    },
  ]
}
     */
}
