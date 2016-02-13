package diplom.repository;

import diplom.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created on 13.02.2016.
 */
public interface UserRepository extends CrudRepository<User, String> {
}
