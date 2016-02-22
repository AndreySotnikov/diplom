package diplom.repository;

import diplom.entity.Revision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 22.02.2016.
 */
@Repository
public interface RevisionRepository extends CrudRepository<Revision, Integer> {
}
