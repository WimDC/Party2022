package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean outdoor);
    Iterable<Venue> findByIndoor(boolean indoor);
    Iterable<Venue> findByCapacity(int capacity);
    Iterable<Venue> findByCapacityLessThan(int capacity);
    @Query("select v from Venue v where :min IS NULL OR :min <= v.capacity")
    List<Venue> findByCapacityGreaterThan(@Param("min") Integer min);
    @Query("select v from Venue v where :min IS NULL or :max IS NULL OR :min <= v.capacity and :max >= v.capacity")
    List<Venue> findByCapacityBetweenQuery(@Param("min") Integer min,@Param("max") Integer max);
    Iterable<Venue> findByCapacityGreaterThanEqual(int capacity);
}