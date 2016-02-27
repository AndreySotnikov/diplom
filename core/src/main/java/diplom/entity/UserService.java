package diplom.entity;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created on 13.02.2016.
 */
@Entity
public class UserService implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Service service;
    private boolean value;

    public UserService() {
    }

    public UserService(User user, Service service, boolean value) {
        this.user = user;
        this.service = service;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserService that = (UserService) o;

        if (id != that.id) return false;
        if (value != that.value) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return service != null ? service.equals(that.service) : that.service == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (value ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "id=" + id +
                ", user=" + user +
                ", service=" + service +
                ", value=" + value +
                '}';
    }
}
