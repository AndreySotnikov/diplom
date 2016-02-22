package diplom.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private File file;
    private boolean active;
    @OneToMany(mappedBy = "subscription")
    private List<Characteristic> characteristics;
    public Subscription() {
    }

    public Subscription(File file, boolean active) {
        this.file = file;
        this.active = active;
    }

    public Subscription(File file, boolean active, List<Characteristic> characteristics) {
        this.file = file;
        this.active = active;
        this.characteristics = characteristics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subscription that = (Subscription) o;

        if (id != that.id) return false;
        if (active != that.active) return false;
        return file != null ? file.equals(that.file) : that.file == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", file=" + file +
                ", active=" + active +
                '}';
    }
}