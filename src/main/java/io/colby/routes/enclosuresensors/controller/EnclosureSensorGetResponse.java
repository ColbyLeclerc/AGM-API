package io.colby.routes.enclosuresensors.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.entity.EnclosureSensor;

import java.util.List;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class EnclosureSensorGetResponse {

    @JsonProperty("enclosure-id")
    private int enclosureId;
    @JsonProperty("enclosure-sensors")
    private List<EnclosureSensor> enclosureSensors;
//    @JsonProperty("plant-sensors")
//    private List<PlantSensor> plant_sensors;

    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }

    public List<EnclosureSensor> getEnclosureSensors() {
        return enclosureSensors;
    }

    public void setEnclosureSensors(List<EnclosureSensor> enclosureSensors) {
        this.enclosureSensors = enclosureSensors;
    }

//    public List<PlantSensor> getPlant_sensors() {
//        return plant_sensors;
//    }
//
//    public void setPlant_sensors(List<PlantSensor> plant_sensors) {
//        this.plant_sensors = plant_sensors;
//    }

    public int getEnclosureId() {
        return enclosureId;
    }
}
