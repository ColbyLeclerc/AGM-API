package io.colby.modules.auth.model.repository;


import io.colby.modules.auth.model.entity.Auth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<Auth, Integer> {

    Optional<Auth> findAuthByToken(String token);

    Optional<Auth> findAuthByAuthId(int authId);

//    Auth save(Auth auth);

}
