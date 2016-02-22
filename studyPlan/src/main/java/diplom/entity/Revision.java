package diplom.entity;

import javax.persistence.*;

/**
 * Created on 22.02.2016.
 */
@Entity
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String path;
    @ManyToOne
    private File file;
    private String username;
    private String description;

    public Revision() {
    }

    public Revision(String path, File file, String username, String description) {
        this.path = path;
        this.file = file;
        this.username = username;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Revision revision = (Revision) o;

        if (id != revision.id) return false;
        if (path != null ? !path.equals(revision.path) : revision.path != null) return false;
        if (file != null ? !file.equals(revision.file) : revision.file != null) return false;
        if (username != null ? !username.equals(revision.username) : revision.username != null) return false;
        return description != null ? description.equals(revision.description) : revision.description == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Revision{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", file=" + file +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
