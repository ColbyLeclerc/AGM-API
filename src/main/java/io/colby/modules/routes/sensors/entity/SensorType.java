package io.colby.modules.routes.sensors.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.colby.modules.routes.sensors.model.entity.Sensor;

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
