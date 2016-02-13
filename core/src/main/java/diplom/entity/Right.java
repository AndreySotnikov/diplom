package diplom.entity;


import javax.persistence.*;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public class Right {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private boolean value;
    @ManyToOne
    private Entity entity;
    @ManyToOne
    private Group group;
    @ManyToOne
    private User user;
//    @Enumerated(EnumType.STRING)
    private String rightType;

    public Right() {
    }

    public Right(boolean value, Entity entity, Group group, User user, String rightType) {
        this.value = value;
        this.entity = entity;
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

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Right right = (Right) o;

        if (id != right.id) return false;
        if (value != right.value) return false;
        if (entity != null ? !entity.equals(right.entity) : right.entity != null) return false;
        if (group != null ? !group.equals(right.group) : right.group != null) return false;
        if (user != null ? !user.equals(right.user) : right.user != null) return false;
        return rightType == right.rightType;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value ? 1 : 0);
        result = 31 * result + (entity != null ? entity.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (rightType != null ? rightType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Right{" +
                "id=" + id +
                ", value=" + value +
                ", entity=" + entity +
                ", group=" + group +
                ", user=" + user +
                ", rightType=" + rightType +
                '}';
    }
}
