package io.colby.plantsensors.model;

import io.colby.entity.PlantSensor;
import io.colby.entity.MetaID;
import io.colby.plantsensors.controller.PlantSensorCreateRequest;
import io.colby.plantsensors.controller.PlantSensorCreateResponse;
import io.colby.plantsensors.controller.PlantSensorDeleteResponse;
import io.colby.plantsensors.controller.PlantSensorGetResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class PlantSensorModel {

    private static ConcurrentHashMap<Integer, ArrayList<PlantSensor>> tempDb = new ConcurrentHashMap<>();

    /**

     */
    public PlantSensorGetResponse getSinglePlant(int id) {

        PlantSensorGetResponse response = new PlantSensorGetResponse();
        if (tempDb.containsKey(id)){
            response.setPlantSensors(tempDb.get(id));
            response.setPlantId(id);
        }


        return response;
    }

    //TODO change userId to User object once user management system is built
    public ArrayList<PlantSensorGetResponse> getAllPlants(MetaID metaID) {

        ArrayList<PlantSensorGetResponse> arrRes = new ArrayList<>();

        for (Integer enclosureId : tempDb.keySet()) {
            PlantSensorGetResponse response = new PlantSensorGetResponse();
            response.setPlantId(enclosureId);
            response.setPlantSensors(tempDb.get(enclosureId));
            arrRes.add(response);
        }

        return arrRes;
    }


    public ArrayList<PlantSensorCreateResponse> createPlantSensors(MetaID metaId,
                                                                   List<PlantSensorCreateRequest> enclosure) {

        ArrayList<PlantSensorCreateResponse> arrRes = new ArrayList<>();

        int i = tempDb.keySet().size() + 1;

        for (PlantSensorCreateRequest req : enclosure) {
            PlantSensorCreateResponse resp = new PlantSensorCreateResponse();

            resp.setPlantId(i);
            resp.setPlantSensors(getPlantSensors(req.getPlantSensors(), i));

            arrRes.add(resp);

            ArrayList<PlantSensor> tempArr = new ArrayList<>(getPlantSensors(req.getPlantSensors(), i));

            if (tempDb.containsKey(i)) {
                tempArr.addAll(tempDb.get(i));
            }

            tempDb.put(i, tempArr);

            i++;

        }

        return arrRes;
    }

    private ArrayList<PlantSensor> getPlantSensors(ArrayList<PlantSensor> plantSensors, int encId) {

        ArrayList<PlantSensor> resp = new ArrayList<>();

        int i = 1;

        for (PlantSensor sen : plantSensors) {
            PlantSensor enc = new PlantSensor();
            enc.setType(sen.getType());
            enc.setDateCreated(new Date());
            enc.setSensorId(1);
            enc.setPlantId(encId);

            resp.add(enc);

            i++;
        }

        return resp;

    }

    public PlantSensorDeleteResponse deletePlantSensor(int id) {
        if (tempDb.containsKey(id)){
            tempDb.remove(id);
            return new PlantSensorDeleteResponse(id, true);
        } else {
            return new PlantSensorDeleteResponse(id, false);
        }
    }

    public boolean plantSensorExists(int id) {
        return tempDb.containsKey(id);
    }
}
