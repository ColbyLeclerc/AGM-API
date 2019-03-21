package io.colby.modules.routes.enclosures.repository;

import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnclosureRepository extends CrudRepository<Enclosure, Integer> {


    Optional<Enclosure> findByEnclosureId(Integer enclosureId);


}
