package io.colby.enclosuresensors.controller;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class EnclosureSensorDeleteResponse {

    private Integer id;
    private String message;

    public EnclosureSensorDeleteResponse(int id, boolean exist){
        if (!exist){
            this.message = "Resource not deleted. Enclosure ID does not exist";
        } else {
            this.message = "Resource deleted successfully";
        }

        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
