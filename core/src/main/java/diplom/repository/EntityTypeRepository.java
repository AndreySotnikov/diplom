package diplom.repository;

import diplom.entity.EntityType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface EntityTypeRepository extends CrudRepository<EntityType, Integer> {
}
