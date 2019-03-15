package io.colby.modules.routes.plants.model.repository;

import io.colby.modules.routes.plants.model.entity.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends CrudRepository<Plant, Integer> {


    /**
     * Returns populated Plant object based on passed plantId if
     * plantId exists.
     *
     * @param plantId - ID of the Plant object
     * @return - Populated Plant if found
     */
    Optional<Plant> findByPlantId(Integer plantId);

    /**
     * Returns all plants associated with the passed enclosureId. Returns
     * an empty list if no plants are found.
     *
     * @param enclosureId - ID of the associated Enclosure object
     * @return List of populated Plant objects (if association exists)
     */
    List<Plant> findAllByEnclosureId(Integer enclosureId);

}
