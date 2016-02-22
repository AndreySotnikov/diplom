package diplom.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Entity
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "attribute")
    private List<Characteristic> characteristics;

    public Attribute() {
    }

    public Attribute(String name) {
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

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (id != attribute.id) return false;
        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        return characteristics != null ? characteristics.equals(attribute.characteristics) : attribute.characteristics == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (characteristics != null ? characteristics.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
