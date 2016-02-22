package diplom.repository;

import diplom.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 22.02.2016.
 */
@Repository
public interface FileRepository extends CrudRepository<File,Integer> {
}
