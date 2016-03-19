package diplom.repository;

import diplom.entity.Entity;
import diplom.entity.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface EntityRepository extends CrudRepository<Entity, Integer> {

    @Query(value = "select e.id from Entity e where e.entityType.name=:entityType")
    List<Integer> getEntityByType(@Param("entityType") String entityType);
}
