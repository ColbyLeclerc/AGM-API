package io.colby.modules.routes.readings.model.entity;

//TODO add filtering and pagnation to all endpoints
public interface Reading {

    /**
     * Set the Auth record id associated with the reading
     *
     * @param authId
     */
    void setAuthId(int authId);

}
