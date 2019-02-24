package io.colby.model;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

import io.colby.entity.MetaID;

public class AuthorizationModel {

    public boolean userHaveAccessToEnclosure(MetaID metaId, int enclosureId){

        if (enclosureId < 1){
            return false;
        }

        return true;
    }

}
