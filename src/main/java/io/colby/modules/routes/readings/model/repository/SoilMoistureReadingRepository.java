package io.colby.modules.routes.readings.model.repository;

import io.colby.modules.routes.readings.model.entity.Reading;
import io.colby.modules.routes.readings.model.entity.SoilMoistureReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoilMoistureReadingRepository extends CrudRepository<SoilMoistureReading, Integer> {

    /**
     * Retrieves the SoilMoistureReading record using the passed
     * id
     *
     * @param id soil moisture reading id
     * @return SoilMoistureReading record
     */
    Optional<SoilMoistureReading> findBySoilMoistureReadingId(int id);

    /**
     * Returns all associated readings given the passed authId
     *
     * @param authId id associated with the Auth record
     * @return List of all associated readings
     */
    List<Reading> findAllByAuthId(int authId);


}
