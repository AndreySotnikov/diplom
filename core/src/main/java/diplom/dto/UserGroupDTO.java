package diplom.dto;

import diplom.entity.Group;

/**
 * Created on 12.03.2016.
 */
public class UserGroupDTO {
    private Group group;
    private boolean enabled;

    public UserGroupDTO(Group group, boolean enabled) {
        this.group = group;
        this.enabled = enabled;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserGroupDTO that = (UserGroupDTO) o;

        if (enabled != that.enabled) return false;
        return group != null ? group.equals(that.group) : that.group == null;

    }

    @Override
    public int hashCode() {
        int result = group != null ? group.hashCode() : 0;
        result = 31 * result + (enabled ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserGroupDTO{" +
                "group=" + group +
                ", enabled=" + enabled +
                '}';
    }
}
