package io.colby.modules.routes.readings.model.entity;

//TODO update ERD to reflect all reading changes (added auth_id column)
//TODO add filtering and pagnation to all endpoints
public interface Reading {

    void setAuthId(int authId);

}
