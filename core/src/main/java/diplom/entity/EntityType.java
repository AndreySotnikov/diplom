package diplom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public enum EntityType {

    STUDENT("student"),

    TEACHER("teacher"),

    FILE("file");

    @Id
    private String name;

    EntityType(String name) {
        this.name = name;
    }
}
