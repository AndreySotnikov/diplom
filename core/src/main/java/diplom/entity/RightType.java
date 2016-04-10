package diplom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Entity
public class RightType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "rightType")
    private List<Right> rights;

    public RightType() {
    }

    public RightType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * read
     * write
     * delete
     * update
     * grant/revoke
     * everything
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RightType rightType = (RightType) o;

        if (id != rightType.id) return false;
        if (name != null ? !name.equals(rightType.name) : rightType.name != null) return false;
        return rights != null ? rights.equals(rightType.rights) : rightType.rights == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rights != null ? rights.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RightType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rights=" + rights +
                '}';
    }
}
