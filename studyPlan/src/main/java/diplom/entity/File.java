package diplom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String directory;
    private String name;
    private String description;
    @OneToMany(mappedBy = "cfile")
    @JsonIgnore
    private List<Characteristic> characteristics;
    @OneToMany(mappedBy = "file")
    @JsonIgnore
    private List<Revision> revisions;
    @Column(name = "user_lock")
    private boolean lock;
    @OneToMany(mappedBy = "file")
    @JsonIgnore
    private List<Subscription> subscriptions;
    private int entityId;

    public File() {
    }

    public File(String directory, String name, String description) {
        this.directory = directory;
        this.name = name;
        this.description = description;
    }

    public File(String directory, String name, String description, boolean lock, int entityId) {
        this.directory = directory;
        this.name = name;
        this.description = description;
        this.lock = lock;
        this.entityId = entityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (id != file.id) return false;
        if (lock != file.lock) return false;
        if (directory != null ? !directory.equals(file.directory) : file.directory != null) return false;
        if (name != null ? !name.equals(file.name) : file.name != null) return false;
        return (description != null ? !description.equals(file.description) : file.description != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (directory != null ? directory.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (lock ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", directory='" + directory + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", lock=" + lock +
                '}';
    }
}
