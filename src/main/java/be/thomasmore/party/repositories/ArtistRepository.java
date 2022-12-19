package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
    Iterable<Artist> findArtistByArtistNameIsContainingIgnoreCase(String artistName);
    @Query("select a from Artist a where lower(a.artistName) LIKE CONCAT('%', lower(:name), '%') OR LOWER(a.genre) LIKE CONCAT('%', lower(:name), '%') OR LOWER(a.portfolio) LIKE CONCAT('%', lower(:name), '%') OR LOWER(a.bio) LIKE CONCAT('%', lower(:name), '%')  ")
    List<Artist> findArtistByArtistName(@Param("name") String name);
}
