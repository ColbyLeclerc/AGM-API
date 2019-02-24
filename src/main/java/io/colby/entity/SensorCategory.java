package io.colby.entity;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import java.util.Optional;

public enum SensorCategory {

    PLANT {
        @Override
        public String toString() {
            return "PLANT";
        }
    }, ENCLOSURE {
        @Override
        public String toString() {
            return "ENCLOSURE";
        }
    }, ERROR {
        @Override
        public String toString() {
            return "ERROR";
        }
    };

    public static SensorCategory toEnum(String str){
        switch (str.trim().toUpperCase()){
            case "PLANT":
                return SensorCategory.PLANT;
            case "ENCLOSURE":
                return SensorCategory.ENCLOSURE;
            default:
                return SensorCategory.ERROR;
        }
    }
}
