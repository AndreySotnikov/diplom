package diplom.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private EntityType entityType;

    public Entity() {
    }

    public Entity(EntityType entityType) {
        this.entityType = entityType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (id != entity.id) return false;
        return entityType != null ? entityType.equals(entity.entityType) : entity.entityType == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (entityType != null ? entityType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", entityType=" + entityType +
                '}';
    }
}
