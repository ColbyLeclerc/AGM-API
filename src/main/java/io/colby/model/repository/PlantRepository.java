package io.colby.model.repository;

import io.colby.model.entity.Plant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends CrudRepository<Plant, Integer> {


    Plant findByPlantId(Integer plantId);

}
