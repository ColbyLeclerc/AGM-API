package io.colby.modules.routes.enclosures.repository;

import io.colby.modules.routes.enclosures.model.entity.Enclosure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EnclosureRepository extends CrudRepository<Enclosure, Integer> {


    Enclosure findByEnclosureId(Integer enclosureId);

    List<Enclosure> findAll();

}
