package diplom.entity;


import javax.persistence.*;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public class NewEntitiesRights {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private EntityType entityType;
    @ManyToOne
    private Group group;
    @ManyToOne
    private User user;
    @ManyToOne
    private RightType rightType;

    public NewEntitiesRights() {
    }

    public NewEntitiesRights(EntityType entityType, Group group, User user, RightType rightType) {
        this.entityType = entityType;
        this.group = group;
        this.user = user;
        this.rightType = rightType;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RightType getRightType() {
        return rightType;
    }

    public void setRightType(RightType rightType) {
        this.rightType = rightType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewEntitiesRights that = (NewEntitiesRights) o;

        if (id != that.id) return false;
        if (entityType != null ? !entityType.equals(that.entityType) : that.entityType != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return rightType != null ? rightType.equals(that.rightType) : that.rightType == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (entityType != null ? entityType.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (rightType != null ? rightType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewEntitiesRights{" +
                "id=" + id +
                ", entityType=" + entityType +
                ", group=" + group +
                ", user=" + user +
                ", rightType=" + rightType +
                '}';
    }
}
