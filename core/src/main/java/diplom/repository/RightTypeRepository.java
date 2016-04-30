package diplom.repository;

import diplom.entity.RightType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created on 13.02.2016.
 */
@Repository
public interface RightTypeRepository extends CrudRepository<RightType, Integer> {
    @Query("select rt from RightType rt where rt.name=:name")
    RightType getRightTypeByName(@Param("name")String name);
}
