package io.colby.routes.sensors.entity.controller;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class SensorControllerError {

    private String message = "SensorControllerError";
    private int error_num = -1;


    public int getError_num() {
        return error_num;
    }

    public String getMessage() {
        return message;
    }
}
