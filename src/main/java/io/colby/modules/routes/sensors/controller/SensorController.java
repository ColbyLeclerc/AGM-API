package io.colby.modules.routes.sensors.controller;



import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.service.AuthService;
import io.colby.modules.routes.sensors.model.entity.Sensor;
import io.colby.modules.routes.sensors.model.repository.SensorRepository;
import io.colby.utility.StringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//TODO update documentation to reflect changes in response JSON not being a map, and removing plant sensors from response

@RestController
public class SensorController {

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    AuthService authService;

    @RequestMapping(value = {"/sensors/{id}"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Sensor> getSingleSensor(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToSensor(authRec.get(), id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        Optional<Sensor> sensorSearch = sensorRepository.findBySensorId(id);

        if (!sensorSearch.isPresent()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(sensorSearch.get());

    }

    @RequestMapping(value = {"/sensors"},
            method = RequestMethod.GET)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<List<Sensor>> getAllSensors(
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        return CompletableFuture.completedFuture(authRec.get().getSensors());

    }


    @RequestMapping(value = {"/sensors"},
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<Sensor> createSensor(
            @Valid @RequestBody Sensor request,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {

        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        //TODO for other post requests
        request.setAuthId(authRec.get().getAuthId());

        System.out.println(StringUtility.applyCyan(request.toString()));

        Sensor sensor = sensorRepository.save(request);

        System.out.println(StringUtility.applyCyan(sensor.toString()));

        Optional<Sensor> optionalSensor = sensorRepository.findBySensorId(sensor.getSensorId());

        if (!optionalSensor.isPresent()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        response.setStatus(HttpServletResponse.SC_CREATED);

        return CompletableFuture.completedFuture(optionalSensor.get());

    }

    @RequestMapping(value = {"/sensors/{id}"},
            method = RequestMethod.DELETE,
            produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @Async("asyncExecutor")
    public CompletableFuture<String> deleteSensor(
            @PathVariable("id") int id,
            @RequestHeader(value = "Authorization") String auth,
            HttpServletResponse response
    ) {


        Optional<Auth> authRec = authService.getFromToken(auth);

        if (!authRec.isPresent()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return CompletableFuture.completedFuture(null);
        }

        if (!authService.userHasAccessToSensor(authRec.get(), id)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return CompletableFuture.completedFuture(null);
        }

        sensorRepository.deleteById(id);

        if (sensorRepository.findById(id).isPresent()) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return CompletableFuture.completedFuture("{\"message\": \"error when attempting to delete sensor\", \"deleted\": \"false\", \"sensor-id\": " + id + "}");
        }

        return CompletableFuture.completedFuture("{\"message\": \"sensor deleted successfully\", \"deleted\": \"true\", \"sensor-id\": " + id + "}");

    }

}
