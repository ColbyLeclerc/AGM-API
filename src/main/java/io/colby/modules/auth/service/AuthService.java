package io.colby.modules.auth.service;

import io.colby.modules.auth.model.entity.Auth;
import io.colby.modules.auth.model.repository.AuthRepository;
import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import io.colby.modules.routes.enclosures.repository.EnclosureRepository;
import io.colby.modules.routes.plants.model.entity.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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
    public boolean userHasAccessToEnclosure(Auth auth, int enclosureId) {

        Optional<Enclosure> enclosure = auth.getEnclosures().stream()
                .filter(x -> x.getEnclosureId() == enclosureId).findFirst();

        return enclosure.isPresent();
    }

    @Override
    public boolean userHasAccessToPlant(Auth auth, int plantId) {

        Optional<Plant> plant = auth.getPlants().stream()
                .filter(x -> x.getPlantId() == plantId).findFirst();

        return plant.isPresent();
    }

}
