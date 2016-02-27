package diplom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public class EntityType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "entityType")
    private List<Entity> entities;

    public EntityType() {
    }

    public EntityType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityType that = (EntityType) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return entities != null ? entities.equals(that.entities) : that.entities == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (entities != null ? entities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EntityType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", entities=" + entities +
                '}';
    }
}
