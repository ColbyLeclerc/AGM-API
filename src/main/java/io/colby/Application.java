package io.colby;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static final boolean DEV_MODE = true;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    public static boolean isDevMode(){
        return DEV_MODE;
    }

}