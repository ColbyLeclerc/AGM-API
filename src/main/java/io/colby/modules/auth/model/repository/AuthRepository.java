package io.colby.modules.auth.model.repository;


import io.colby.modules.auth.model.entity.Auth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends CrudRepository<Auth, Integer> {

    /**
     * Retrieve the Auth record using a passed token
     *
     * @param token Authorization token found in header
     * @return Auth record
     */
    Optional<Auth> findAuthByToken(String token);

    /**
     * Retrieve the Auth record using the record's ID
     *
     * @param authId ID of the Auth record
     * @return Auth record
     */
    Optional<Auth> findAuthByAuthId(int authId);

}
