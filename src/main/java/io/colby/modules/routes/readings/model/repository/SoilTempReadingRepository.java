package io.colby.modules.routes.readings.model.repository;

import io.colby.modules.routes.readings.model.entity.Reading;
import io.colby.modules.routes.readings.model.entity.SoilTempReading;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoilTempReadingRepository extends CrudRepository<SoilTempReading, Integer> {


    /**
     * Retrieves SoilTempReading record using the passed
     * record id
     *
     * @param id soil temp reading id
     * @return SoilTempReading record
     */
    Optional<SoilTempReading> findBySoilTempReadingId(int id);

    /**
     * Returns all associated readings given the passed authId
     *
     * @param authId id associated with the Auth record
     * @return List of all associated readings
     */
    List<Reading> findAllByAuthId(int authId);

}
