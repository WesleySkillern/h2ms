package edu.harvard.h2ms.repository;

import edu.harvard.h2ms.domain.core.Location;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Spring JPA is equipped with a built in query creation mechanism. For a full listing of H2MS
 * available end points please visit: http://localhost:XXXX/swagger-ui.html
 */
@RepositoryRestResource(collectionResourceRel = "locations", path = "locations")
public interface LocationRepository extends PagingAndSortingRepository<Location, Long>, CustomLocationRepository {
  Location findByName(String name);

  Set<Location> findByParent(Location parent);
  
  @Query("SELECT l FROM Location WHERE l.parent is null")
  Set<Location> findTopLevel();
}
