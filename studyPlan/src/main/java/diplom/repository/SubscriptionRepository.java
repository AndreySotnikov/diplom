package diplom.repository;

import diplom.entity.Subscription;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {

    @Query(value = "select s from Subscription s where s.login = :login")
    List<Subscription> getByLogin(@Param("login") String login);
}
