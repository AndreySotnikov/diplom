package diplom.repository;

import diplom.entity.RightType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface RightTypeRepository extends CrudRepository<RightType, Integer> {
}
