package diplom.repository;

import diplom.entity.Characteristic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Repository
public interface CharacteristicRepository extends CrudRepository<Characteristic, Integer> {

    @Query(value = "select ch from Characteristic ch where ch.id IN :ids")
    List<Characteristic> getChars(@Param("ids") List<Integer> ids);
}
