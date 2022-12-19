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
    Iterable<Venue> findByDistanceFromPublicTransportInKm(int maxDistance);
    @Query("select v from Venue v where :min IS NULL OR :min <= v.capacity")
    List<Venue> findByCapacityGreaterThan(@Param("min") Integer min);
    @Query("select v from Venue v where :min IS NULL or :max IS NULL OR :min <= v.capacity and :max >= v.capacity")
    List<Venue> findByCapacityBetweenQuery(@Param("min") Integer min,@Param("max") Integer max);
    @Query("SELECT v FROM Venue v WHERE " +
            "(:minCapacity IS NULL OR :minCapacity <= v.capacity) AND " +
            "(:maxCapacity IS NULL OR v.capacity <= :maxCapacity) AND " +
            "(:maxDistance IS NULL OR v.distanceFromPublicTransportInKm <= :maxDistance) AND " +
            "(:foodProvided IS NULL OR v.foodProvided = :foodProvided) AND " +
            "(:indoor IS NULL OR v.indoor=:indoor) AND " +
            "(:outdoor IS NULL OR v.outdoor=:outdoor) ")
    List<Venue> findByFilter(@Param("minCapacity") Integer minCapacity,
                             @Param("maxCapacity") Integer maxCapacity,
                             @Param("maxDistance") Integer maxDistance,
                             @Param("foodProvided") Boolean foodProvided,
                             @Param("indoor") Boolean indoor,
                             @Param("outdoor") Boolean outdoor);
    Iterable<Venue> findByCapacityGreaterThanEqual(int capacity);
}