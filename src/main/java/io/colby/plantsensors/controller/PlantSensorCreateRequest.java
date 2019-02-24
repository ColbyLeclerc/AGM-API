package io.colby.plantsensors.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.entity.PlantSensor;

import java.util.ArrayList;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class PlantSensorCreateRequest {

    @JsonProperty("plant-id")
    private int plantId;
    @JsonProperty("plant-sensors")
    private ArrayList<PlantSensor> plantSensors;

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public ArrayList<PlantSensor> getPlantSensors() {
        return plantSensors;
    }

    public void setPlantSensors(ArrayList<PlantSensor> plantSensors) {
        this.plantSensors = plantSensors;
    }
}
