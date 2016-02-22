package diplom.repository;

import diplom.entity.UserService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface UserServiceRepository extends CrudRepository<UserService,Integer> {
}
