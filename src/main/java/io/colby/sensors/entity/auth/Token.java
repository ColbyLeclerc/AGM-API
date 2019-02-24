package io.colby.entity.auth;

/*
 * Copyright (c) 2019. CJ Software Company All rights reserved.
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Colby Leclerc <colby@colby.io>, January 1, 2018
 */

public class Token {


    private final String token;

    public Token(String token){
        this.token = token.toUpperCase();
    }

    @Override
    public String toString(){
        return this.token;
    }

}
