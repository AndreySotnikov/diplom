package diplom.repository;

import diplom.entity.EntityType;
import diplom.entity.Group;
import diplom.entity.Right;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface RightRepository extends CrudRepository<Right,Integer>{
    @Query(value = "select count(r) from Right r where r.user.login=:username " +
            "and r.entity.id=:id and r.rightType.name=:rightType and r.value=true")
    int checkAccessToEntity(@Param("username") String username,
                             @Param("id") int id,
                             @Param("rightType") String rightType);
    @Query(value = "select r.group from Right r where " +
            "r.entity.id=:id and r.rightType.name=:rightType")
    List<Group> checkGroupAccessToEntity(@Param("id") int id,
                                         @Param("rightType") String rightType);
}
