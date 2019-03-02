package io.colby.routes.enclosuresensors.controller;

/*
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import com.fasterxml.jackson.annotation.JsonProperty;

public class EnclosureSensorDeleteResponse {

    private boolean deleted;
    @JsonProperty("enclosure-id")
    private Integer enclosureId;

    public EnclosureSensorDeleteResponse(int sensorId, boolean deleted){
        this.deleted = deleted;
        this.enclosureId = sensorId;
    }

    public Integer getEnclosureId() {
        return enclosureId;
    }

    public void setEnclosureId(Integer enclosureId) {
        this.enclosureId = enclosureId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
