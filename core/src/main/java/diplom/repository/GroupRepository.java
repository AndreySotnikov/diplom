package diplom.repository;

import diplom.entity.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
}
