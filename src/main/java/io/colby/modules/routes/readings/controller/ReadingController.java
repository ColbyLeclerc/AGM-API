package io.colby.modules.routes.readings.controller;


import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.service.AuthService;
import io.colby.modules.routes.readings.config.SensorTypeEnumConverter;
import io.colby.modules.routes.readings.model.entity.Reading;
import io.colby.modules.routes.readings.model.entity.SoilMoistureReading;
import io.colby.modules.routes.readings.model.entity.SoilTempReading;
import io.colby.modules.routes.readings.model.entity.TempHumidReading;
import io.colby.modules.routes.readings.model.repository.SoilMoistureReadingRepository;
import io.colby.modules.routes.readings.model.repository.SoilTempReadingRepository;
import io.colby.modules.routes.readings.model.repository.TempHumidReadingRepository;
import io.colby.modules.routes.sensors.entity.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class ReadingController {

    @Autowired
    SoilMoistureReadingRepository soilMoistureRepository;

    @Autowired
    SoilTempReadingRepository soilTempRepository;

    @Autowired
    TempHumidReadingRepository tempHumidRepository;

    @Autowired
    AuthService authService;

    @RequestMapping(value = {"/readings/{sensor}/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Reading> getSingleReading(
            @PathVariable("id") int id,
            @PathVariable("sensor") SensorType sensor,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        System.out.println("Request for sensor " + sensor + " id: " + id);

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToReading(authRec.get(), sensor ,id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }


        switch (sensor) {
            case TEMPERATURE_HUMIDITY:
                Optional<TempHumidReading> tempHumidSearch = tempHumidRepository.findByTempHumidReadingId(id);
                if (!tempHumidSearch.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }
                return CompletableFuture.completedFuture(tempHumidSearch.get());

            case SOIL_MOISTURE:
                Optional<SoilMoistureReading> soilMoisSearch = soilMoistureRepository.findBySoilMoistureReadingId(id);
                if (!soilMoisSearch.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }
                return CompletableFuture.completedFuture(soilMoisSearch.get());

            case SOIL_TEMPERATURE:
                Optional<SoilTempReading> soilTempSearch = soilTempRepository.findBySoilTempReadingId(id);
                if (!soilTempSearch.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }
                return CompletableFuture.completedFuture(soilTempSearch.get());

            default:
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }


        return CompletableFuture.completedFuture(null);


    }
//
//    @RequestMapping(value = {"/plants"},
//            method = RequestMethod.GET)
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<List<Plant>> getAllPlants(
//            @RequestHeader(value = "Authorization") String auth,
//            HttpServletResponse response
//    ) {
//
//        Optional<Auth> authRec = authService.getFromToken(auth);
//
//        if (!authRec.isPresent()){
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return CompletableFuture.completedFuture(null);
//        }
//
//        return CompletableFuture.completedFuture(authRec.get().getPlants());
//
//    }
//
//
//    @RequestMapping(value = {"/plants"},
//            method = RequestMethod.POST,
//            consumes = "application/json",
//            produces = APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<Plant> createPlant(
//            @Valid @RequestBody Plant request,
//            @RequestHeader(value = "Authorization") String auth,
//            HttpServletResponse response
//    ) {
//
//        Optional<Auth> authRec = authService.getFromToken(auth);
//
//        if (!authRec.isPresent()){
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return CompletableFuture.completedFuture(null);
//        }
//
//        request.setAuthId(authRec.get().getAuthId());
//
//        Plant plant = plantRepository.save(request);
//        Optional<Plant> plantSearch = plantRepository.findByPlantId(plant.getPlantId());
//
//        if (!plantSearch.isPresent()){
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return CompletableFuture.completedFuture(null);
//        }
//
//        response.setStatus(HttpServletResponse.SC_CREATED);
//
//        return CompletableFuture.completedFuture(plantSearch.get());
//
//    }
//
//    @RequestMapping(value = {"/plants/{id}"},
//            method = RequestMethod.DELETE,
//            produces = APPLICATION_JSON_VALUE)
//    @ResponseBody
//    @Async("asyncExecutor")
//    public CompletableFuture<String> deletePlant(
//            @PathVariable("id") int id,
//            @RequestHeader(value = "Authorization") String auth,
//            HttpServletResponse response
//    ) {
//
//        Optional<Auth> authRec = authService.getFromToken(auth);
//
//        if (!authRec.isPresent()){
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return CompletableFuture.completedFuture(null);
//        }
//
//        if (!authService.userHasAccessToPlant(authRec.get(), id)) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            return CompletableFuture.completedFuture(null);
//        }
//
//        plantRepository.deleteById(id);
//
//        //TODO perhaps create innner class to represent error message?
//        if (plantRepository.findById(id).isPresent()) {
//            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete plant\", \"deleted\": \"false\", \"plant-id\": " + id + "}");
//        }
//
//        return CompletableFuture.completedFuture("{\"message\": \"plant deleted successfully\", \"deleted\": \"true\", \"plant-id\": " + id + "}");
//
//    }

}
