package io.colby.entity;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum SensorType {
    @JsonProperty("temperature-humidity")
    TEMPERATURE_HUMIDITY {
        @Override
        public String toString() {
            return "TEMPERATURE_HUMIDITY";
        }
    },
    @JsonProperty("soil-moisture")
    SOIL_MOISTURE {
        @Override
        public String toString() {
            return "SOIL_MOISTURE";
        }
    },
    @JsonProperty("soil-temperature")
    SOIL_TEMPERATURE {
        @Override
        public String toString() {
            return "SOIL_TEMPERATURE";
        }
    },
//    @JsonProperty("error")
    ERROR {
        @Override
        public String toString() {
            return "ERROR";
        }
    };

    @JsonCreator
    public static SensorType fromText(String text) {
        switch (text.trim().toUpperCase()) {
            case "TEMPERATURE_HUMIDITY":
                return TEMPERATURE_HUMIDITY;
            case "SOIL_MOISTURE":
                return SOIL_MOISTURE;
            case "SOIL_TEMPERATURE":
                return SOIL_TEMPERATURE;
            case "TEMPERATURE-HUMIDITY":
                return TEMPERATURE_HUMIDITY;
            case "SOIL-MOISTURE":
                return SOIL_MOISTURE;
            case "SOIL-TEMPERATURE":
                return SOIL_TEMPERATURE;
            default:
                return ERROR;
        }
    }
}
