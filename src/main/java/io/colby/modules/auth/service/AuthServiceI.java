package io.colby.modules.auth.service;

import io.colby.modules.auth.model.entity.Auth;

import java.util.Optional;

//@Service
public interface AuthServiceI {


    Optional<Auth> getFromToken(String token);

    boolean userHasAccessToEnclosure(Auth auth, int id);

    boolean userHasAccessToPlant(Auth auth, int id);
}
