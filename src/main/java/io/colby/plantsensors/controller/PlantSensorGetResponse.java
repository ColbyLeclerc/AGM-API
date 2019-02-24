package io.colby.plantsensors.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.entity.EnclosureSensor;
import io.colby.entity.PlantSensor;

import java.util.List;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class PlantSensorGetResponse {

    @JsonProperty("plant-id")
    private int plantId;
    @JsonProperty("plant-sensors")
    private List<PlantSensor> plantSensors;
//    @JsonProperty("plant-sensors")
//    private List<PlantSensor> plant_sensors;

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public List<PlantSensor> getPlantSensors() {
        return plantSensors;
    }

    public void setPlantSensors(List<PlantSensor> plantSensors) {
        this.plantSensors = plantSensors;
    }

//    public List<PlantSensor> getPlant_sensors() {
//        return plant_sensors;
//    }
//
//    public void setPlant_sensors(List<PlantSensor> plant_sensors) {
//        this.plant_sensors = plant_sensors;
//    }

    public int getPlantId() {
        return plantId;
    }
}
