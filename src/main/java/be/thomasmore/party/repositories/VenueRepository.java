package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean outdoor);
    Iterable<Venue> findByIndoor(boolean indoor);
    Iterable<Venue> findByCapacity(int capacity);
    Iterable<Venue> findByCapacityLessThan(int capacity);
    @Query("select v from Venue v where v.capacity >= ?1")
    List<Venue> findByCapacityGreaterThanEqualQuery(int min);
    @Query("select v from Venue v where v.capacity between ?1 and ?2")
    List<Venue> findByCapacityBetweenQuery(int min, int max);
    Iterable<Venue> findByCapacityBetween(int startCapacity, int endCapacity);
    Iterable<Venue> findByCapacityGreaterThanEqual(int capacity);
}