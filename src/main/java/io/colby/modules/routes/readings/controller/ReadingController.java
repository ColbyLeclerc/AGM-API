package io.colby.modules.routes.readings.controller;

import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.service.AuthService;
import io.colby.modules.routes.readings.model.entity.*;
import io.colby.modules.routes.readings.model.repository.SoilMoistureReadingRepository;
import io.colby.modules.routes.readings.model.repository.SoilTempReadingRepository;
import io.colby.modules.routes.readings.model.repository.TempHumidReadingRepository;
import io.colby.modules.routes.sensors.entity.SensorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

//TODO add filtering
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//TODO Figure out proper parameters to grab readings for each enclosure (get by enclosure ID, or get by sensor ID)

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

    //    @CrossOrigin(origins = "http://localhost:3000")
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

        if (!authService.userHasAccessToReading(authRec.get(), sensor, id)) {
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

    @RequestMapping(value = {"/readings/{sensor}"},
            method = RequestMethod.GET
    )
    @ResponseBody
    @Async("asyncExecutor")
    @CrossOrigin(origins = "http://localhost:3000")
    public CompletableFuture<List<Reading>> getReadingSensorAll(
            @PathVariable("sensor") SensorType sensorType,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        switch (sensorType) {
            case TEMPERATURE_HUMIDITY:

                return CompletableFuture.completedFuture(tempHumidRepository.findAllByAuthId(authRec.get().getAuthId()));

            case SOIL_MOISTURE:

                return CompletableFuture.completedFuture(soilMoistureRepository.findAllByAuthId(authRec.get().getAuthId()));

            case SOIL_TEMPERATURE:

                return CompletableFuture.completedFuture(soilTempRepository.findAllByAuthId(authRec.get().getAuthId()));

            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }

        return CompletableFuture.completedFuture(null);

    }

    @RequestMapping(value = {"/readings/{sensor}"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Reading> createReading(
            @Valid @RequestBody ReadingRequest request,
            @PathVariable("sensor") SensorType sensorType,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        switch (sensorType) {
            case TEMPERATURE_HUMIDITY:

                TempHumidReading tHReading = new TempHumidReading();
                tHReading.setAuthId(authRec.get().getAuthId());
                tHReading.setSensorId(request.getSensorId());
                tHReading.setHumidity(request.getHumidity());
                tHReading.setHumidityUnits(request.getHumidityUnits());
                tHReading.setTempLevel(request.getTempLevel());
                tHReading.setTempScale(request.getTempScale());
                tHReading.setTimeRecorded(request.getTimeRecorded());

                TempHumidReading tempHumid = tempHumidRepository.save(tHReading);

                Optional<TempHumidReading> tempHumidSearch = tempHumidRepository.findByTempHumidReadingId(
                        tempHumid.getTempHumidReadingId());

                if (!tempHumidSearch.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }

                response.setStatus(HttpServletResponse.SC_CREATED);

                return CompletableFuture.completedFuture(tempHumidSearch.get());

            case SOIL_MOISTURE:

                SoilMoistureReading sMReading = new SoilMoistureReading();
                sMReading.setAuthId(authRec.get().getAuthId());
                sMReading.setSensorId(request.getSensorId());
                sMReading.setMoistureLevel(request.getMoistureLevel());
                sMReading.setMoistureLevelUnits(request.getMoistureLevelUnits());
                sMReading.setTimeRecorded(request.getTimeRecorded());

                SoilMoistureReading soilMois = soilMoistureRepository.save(sMReading);

                Optional<SoilMoistureReading> soilMoisSearch = soilMoistureRepository.findBySoilMoistureReadingId(
                        soilMois.getSoilMoistureReadingId());

                if (!soilMoisSearch.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }

                response.setStatus(HttpServletResponse.SC_CREATED);

                return CompletableFuture.completedFuture(soilMoisSearch.get());

            case SOIL_TEMPERATURE:

                SoilTempReading sTReading = new SoilTempReading();
                sTReading.setAuthId(authRec.get().getAuthId());
                sTReading.setSensorId(sTReading.getSensorId());
                sTReading.setTempLevel(request.getTempLevel());
                sTReading.setTempScale(request.getTempScale());
                sTReading.setTimeRecorded(request.getTimeRecorded());

                SoilTempReading soilTemp = soilTempRepository.save(sTReading);

                Optional<SoilTempReading> soilTempSearch = soilTempRepository.findBySoilTempReadingId(
                        soilTemp.getSoilTempReadingId());

                if (!soilTempSearch.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    break;
                }

                response.setStatus(HttpServletResponse.SC_CREATED);

                return CompletableFuture.completedFuture(soilTempSearch.get());

            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }

        return CompletableFuture.completedFuture(null);

    }

    //TODO create way to mass delete readings more easily

    @RequestMapping(value = {"/readings/{sensor}/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<String> deleteReading(
            @PathVariable("id") int id,
            @PathVariable("sensor") SensorType sensor,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToReading(authRec.get(), sensor, id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        switch (sensor) {
            case TEMPERATURE_HUMIDITY:

                tempHumidRepository.deleteById(id);

                if (tempHumidRepository.findById(id).isPresent()) {
                    return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete reading\", \"deleted\": \"false\", \"temperature-humidity-reading-id\": " + id + "}");
                }

                return CompletableFuture.completedFuture("{\"message\": \"reading deleted successfully\", \"deleted\": \"true\", \"temperature-humidity-reading-id\": " + id + "}");


            case SOIL_MOISTURE:

                soilMoistureRepository.deleteById(id);

                if (soilMoistureRepository.findById(id).isPresent()) {
                    return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete reading\", \"deleted\": \"false\", \"soil-moisture-reading-id\": " + id + "}");
                }

                return CompletableFuture.completedFuture("{\"message\": \"reading deleted successfully\", \"deleted\": \"true\", \"soil-moisture-reading-id\": " + id + "}");


            case SOIL_TEMPERATURE:

                soilTempRepository.deleteById(id);

                if (soilTempRepository.findById(id).isPresent()) {
                    return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete reading\", \"deleted\": \"false\", \"soil-temperature-reading-id\": " + id + "}");
                }

                return CompletableFuture.completedFuture("{\"message\": \"reading deleted successfully\", \"deleted\": \"true\", \"soil-temperature-reading-id\": " + id + "}");


            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        }

        return CompletableFuture.completedFuture(null);

    }

    //TODO have ability to grab sensor readings using filters
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
