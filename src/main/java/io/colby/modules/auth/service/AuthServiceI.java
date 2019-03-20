package io.colby.modules.auth.service;

import io.colby.modules.auth.model.entity.Auth;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
public interface AuthServiceI {


    Optional<Auth> getFromToken(String token);

    boolean userHaveAccessToEnclosure(Auth auth, int id);
}
