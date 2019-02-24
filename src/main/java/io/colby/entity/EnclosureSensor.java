package io.colby.entity;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.Date;

public class EnclosureSensor {

    @JsonProperty("sensor-id")
    private int sensorId;
    @JsonProperty("type")
    @NotNull
    private SensorType type;
    @JsonProperty("location")
    private String location;
    @JsonProperty("date-created")
    private Date dateCreated;
    @JsonIgnore
    private int enclosureId;

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensor_id) {
        this.sensorId = sensor_id;
    }

    public SensorType getType() {
        return type;
    }

    public void setType(SensorType type) {

        if (!type.equals(SensorType.TEMPERATURE_HUMIDITY)){
            throw new InvalidParameterException("Error: enclosure sensor cannot have SensorType of " + type);
        }

        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date date_created) {
        this.dateCreated = date_created;
    }

    public int getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(int enclosureId) {
        this.enclosureId = enclosureId;
    }

/*
{
          "sensor-id": 32,
          "type": "temperature-humidity",
          "location": "Near entrance",
          "date-created": "2018-11-20 09:00:53"
        },
 */

}
