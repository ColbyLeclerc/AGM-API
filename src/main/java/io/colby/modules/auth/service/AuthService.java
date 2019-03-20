package io.colby.modules.auth.service;

import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.model.repository.AuthRepository;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.enclosures.repository.EnclosureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements AuthServiceI{


    @Autowired
    AuthRepository authRepository;

    @Autowired
    EnclosureRepository enclosureRepository;

    @Override
    public Optional<Auth> getFromToken(String token) {

        return authRepository.findAuthByToken(token);
    }

    @Override
    public boolean userHaveAccessToEnclosure(Auth auth, int enclosureId) {

        Optional<Enclosure> encOpt = enclosureRepository.findByEnclosureId(enclosureId);

        if (!encOpt.isPresent())
            return false;
//
//        for (Enclosure enc : auth.getEnclosures()){
//            if (enc.getEnclosureId() == enclosureId)
//                return true;
//        }

        return false;
    }

}
