package diplom.repository;

import diplom.entity.EntityType;
import diplom.entity.Group;
import diplom.entity.NewEntitiesRights;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Repository
public interface NewEntitiesRightsRepository extends CrudRepository<NewEntitiesRights, Integer> {
    @Query(value = "select count(r) from NewEntitiesRights r where r.user.login=:username " +
            "and r.entityType.name=:entityType and r.rightType.name=:rightType")
    int checkAccessToEntityType(@Param("username") String username,
                             @Param("entityType") String entityType,
                             @Param("rightType") String rightType);

    @Query(value = "select r.group from NewEntitiesRights r where " +
            "r.entityType.name=:entityType and r.rightType.name=:rightType")
    List<Group> checkGroupAccessToEntityType(@Param("entityType") String entityType,
                                             @Param("rightType") String rightType);
}
