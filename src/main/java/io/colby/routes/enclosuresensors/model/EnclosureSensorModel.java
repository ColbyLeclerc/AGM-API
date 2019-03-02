package io.colby.routes.enclosuresensors.model;

import io.colby.routes.enclosuresensors.controller.EnclosureSensorCreateResponse;
import io.colby.routes.enclosuresensors.controller.EnclosureSensorDeleteResponse;
import io.colby.routes.enclosuresensors.controller.EnclosureSensorGetResponse;
import io.colby.routes.enclosuresensors.controller.EnclosureSensorCreateRequest;
import io.colby.entity.EnclosureSensor;
import io.colby.entity.MetaID;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class EnclosureSensorModel {

    private static ConcurrentHashMap<Integer, ArrayList<EnclosureSensor>> tempDb = new ConcurrentHashMap<>();

    /**

     */
    public EnclosureSensorGetResponse getSingleEnclosure(int id) {


        //First check status
//        EnclosureSensor sensorEnc = new EnclosureSensor();
//        sensorEnc.setDateCreated(new Date());
//        sensorEnc.setLocation("In plant 1");
//        sensorEnc.setType(SensorType.TEMPERATURE_HUMIDITY);
//        sensorEnc.setEnclosureId(1);
//
//        EnclosureSensor sensorEnc2 = new EnclosureSensor();
//        sensorEnc2.setDateCreated(new Date());
//        sensorEnc2.setLocation("Above plant 2");
//        sensorEnc2.setType(SensorType.TEMPERATURE_HUMIDITY);
//        sensorEnc2.setEnclosureId(2);
//
//        PlantSensor sensorPlnt = new PlantSensor();
//        sensorPlnt.setDateCreated(new Date());
//        sensorPlnt.setEnclosureId(3);
//        sensorPlnt.setType(SensorType.TEMPERATURE_HUMIDITY);

//        ArrayList<PlantSensor> pltSnsr = new ArrayList<>();
//        pltSnsr.add(sensorPlnt);


//
//        ArrayList<EnclosureSensor> encSnsr = new ArrayList<>();
//        encSnsr.add(sensorEnc);
//        encSnsr.add(sensorEnc2);
        EnclosureSensorGetResponse response = new EnclosureSensorGetResponse();
        if (tempDb.containsKey(id)) {
//        response.setPlant_sensors(pltSnsr);
            response.setEnclosureSensors(tempDb.get(id));
            response.setEnclosureId(id);
        }


        return response;
    }

    //TODO change userId to User object once user management system is built
    public ArrayList<EnclosureSensorGetResponse> getAllEnclosures(MetaID metaID) {

        ArrayList<EnclosureSensorGetResponse> arrRes = new ArrayList<>();

        //First check status
//        EnclosureSensor sensorEnc = new EnclosureSensor();
//        sensorEnc.setDateCreated(new Date());
//        sensorEnc.setLocation("In plant 1");
//        sensorEnc.setType(SensorType.TEMPERATURE_HUMIDITY);
//        sensorEnc.setEnclosureId(1);
//
//        EnclosureSensor sensorEnc2 = new EnclosureSensor();
//        sensorEnc2.setDateCreated(new Date());
//        sensorEnc2.setLocation("Above plant 2");
//        sensorEnc2.setType(SensorType.TEMPERATURE_HUMIDITY);
//        sensorEnc2.setEnclosureId(2);

//        PlantSensor sensorPlnt = new PlantSensor();
//        sensorPlnt.setDateCreated(new Date());
//        sensorPlnt.setEnclosureId(3);
//        sensorPlnt.setType(SensorType.TEMPERATURE_HUMIDITY);

//        ArrayList<PlantSensor> pltSnsr = new ArrayList<>();
//        pltSnsr.add(sensorPlnt);


        for (Integer enclosureId : tempDb.keySet()) {
            EnclosureSensorGetResponse response = new EnclosureSensorGetResponse();
            response.setEnclosureId(enclosureId);
            response.setEnclosureSensors(tempDb.get(enclosureId));
            arrRes.add(response);
        }

        return arrRes;
    }


    public EnclosureSensorCreateResponse createEnclosureSensors(MetaID metaId,
                                                                EnclosureSensorCreateRequest enclosure) {
        int i = tempDb.keySet().size() + 1;

        EnclosureSensorCreateResponse resp = new EnclosureSensorCreateResponse();

        resp.setEnclosureId(i);
        resp.setEnclosureSensors(getEncSensors(enclosure.getEnclosureSensors(), i));

        ArrayList<EnclosureSensor> tempArr = new ArrayList<>(getEncSensors(enclosure.getEnclosureSensors(), i));

        if (tempDb.containsKey(i)) {
            tempArr.addAll(tempDb.get(i));
        }

        tempDb.put(i, tempArr);

        i++;


        return resp;
    }

    private ArrayList<EnclosureSensor> getEncSensors(ArrayList<EnclosureSensor> plantSensors, int encId) {

        ArrayList<EnclosureSensor> resp = new ArrayList<>();

        int i = 1;

        for (EnclosureSensor sen : plantSensors) {
            EnclosureSensor enc = new EnclosureSensor();
            enc.setType(sen.getType());
            enc.setLocation(sen.getLocation());
            enc.setDateCreated(new Date());
            enc.setSensorId(1);
            enc.setEnclosureId(encId);

            resp.add(enc);

            i++;
        }

        return resp;

    }

    public EnclosureSensorDeleteResponse deleteEnclosureSensor(int id) {

        if (tempDb.containsKey(id)) {
            tempDb.remove(id);
            return new EnclosureSensorDeleteResponse(id, true);
        } else {
            return new EnclosureSensorDeleteResponse(id, false);
        }
    }

    public boolean enclosureSensorExists(int id) {
        return tempDb.containsKey(id);
    }
}
