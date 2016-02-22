package diplom.repository;

import diplom.entity.Entity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface EntityRepository extends CrudRepository<Entity, Integer> {
}
