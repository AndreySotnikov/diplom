package diplom.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String description;
    private String address;
    @JsonIgnore
    @OneToMany(mappedBy = "service")
    private List<UserService> userServices;
    @JsonIgnore
    @OneToMany(mappedBy = "service")
    private List<Right> rights;
    @JsonIgnore
    @OneToMany(mappedBy = "service")
    private List<NewEntitiesRights> newEntitiesRights;

    public Service() {
    }

    public Service(int id) {
        this.id = id;
    }

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Right> getRights() {
        return rights;
    }

    public void setRights(List<Right> rights) {
        this.rights = rights;
    }

    public List<NewEntitiesRights> getNewEntitiesRights() {
        return newEntitiesRights;
    }

    public void setNewEntitiesRights(List<NewEntitiesRights> newEntitiesRights) {
        this.newEntitiesRights = newEntitiesRights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (id != service.id) return false;
        if (name != null ? !name.equals(service.name) : service.name != null) return false;
        return description != null ? description.equals(service.description) : service.description == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public List<UserService> getUserServices() {
        return userServices;
    }

    public void setUserServices(List<UserService> userServices) {
        this.userServices = userServices;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
