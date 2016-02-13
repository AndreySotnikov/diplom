package diplom.repository;

import diplom.entity.Entity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created on 13.02.2016.
 */
public interface EntityRepository extends CrudRepository<Entity, Integer> {
}
