package io.colby.entity;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class MetaID {

    private final int metaID;

    public MetaID (int metaID){
        this.metaID = metaID;
    }

    public int getInt(){
        return this.metaID;
    }

    /**
     * Returns false if MetaID does not exist
     * @return false if MetaID does not exist, true otherwise
     */
    public boolean doesExist(){
        return metaID > 0;
    }
}
