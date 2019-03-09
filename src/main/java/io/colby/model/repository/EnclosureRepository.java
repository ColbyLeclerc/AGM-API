package io.colby.model.repository;

import io.colby.model.entity.Enclosure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnclosureRepository extends CrudRepository<Enclosure, Integer> {


    Enclosure findByEnclosureId(Integer enclosureId);

    List<Enclosure> findAll();

}
