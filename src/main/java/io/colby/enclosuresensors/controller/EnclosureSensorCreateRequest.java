package io.colby.enclosuresensors.controller;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.entity.EnclosureSensor;

import java.util.ArrayList;

public class EnclosureSensorCreateRequest {

    @JsonProperty("enclosure-id")
    private int enclosureId;
    @JsonProperty("enclosure-sensors")
    private ArrayList<EnclosureSensor> plantSensors;

    public int getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }

    public ArrayList<EnclosureSensor> getPlantSensors() {
        return plantSensors;
    }

    public void setPlantSensors(ArrayList<EnclosureSensor> plantSensors) {
        this.plantSensors = plantSensors;
    }
}
