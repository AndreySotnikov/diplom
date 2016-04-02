package diplom.repository;

import diplom.entity.File;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Repository
public interface FileRepository extends CrudRepository<File, Integer> {

    @Query(value = "select f from File f where f.id IN :ids")
    List<File> getByIDs(@Param("ids") List<Integer> ids);

}
