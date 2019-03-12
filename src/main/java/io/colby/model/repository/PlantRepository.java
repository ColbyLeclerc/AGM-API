package io.colby.model.repository;

import io.colby.model.entity.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlantRepository extends CrudRepository<Plant, Integer> {


    Plant findByPlantId(Integer plantId);

    List<Plant> findAll();

    List<Plant> findAllByEnclosureId(Integer enclosureId);

}
