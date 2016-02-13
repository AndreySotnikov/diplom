package diplom.repository;

import diplom.entity.Group;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created on 13.02.2016.
 */
public interface GroupRepository extends CrudRepository<Group, Integer> {
}
