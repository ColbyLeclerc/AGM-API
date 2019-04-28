package io.colby.modules.routes.enclosures.repository;

import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnclosureRepository extends CrudRepository<Enclosure, Integer> {


    /**
     * Given the id of the enclosure, return the populated enclosure object
     *
     * @param enclosureId ID of associated enclosure
     * @return Enclosure record
     */
    Optional<Enclosure> findByEnclosureId(Integer enclosureId);


}
