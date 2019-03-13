package io.colby.modules.routes.plants.model.repository;

import io.colby.modules.routes.plants.model.entity.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlantRepository extends CrudRepository<Plant, Integer> {


    Plant findByPlantId(Integer plantId);

    List<Plant> findAll();

    List<Plant> findAllByEnclosureId(Integer enclosureId);

}
