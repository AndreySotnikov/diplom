package diplom.repository;

import diplom.entity.Group;
import diplom.entity.Service;
import diplom.entity.User;
import diplom.entity.UserService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface UserServiceRepository extends CrudRepository<UserService,Integer> {

    @Query(value = "select us from UserService us where " +
            "us.user.login=:userId and us.service.id=:serviceId")
    List<UserService> getByIds(@Param("userId") String userId,
                               @Param("serviceId") int serviceId);

    @Query(value = "select us.service from UserService us where us.user.login=:userId and us.value=true")
    List<Service> getServices(@Param("userId") String userId);

}
