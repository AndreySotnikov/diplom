package diplom.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created on 24.04.2016.
 */
public class RightDTO implements Serializable{
    private int id;
    private String type;
    private boolean enabled;

    public RightDTO() {
    }

    public RightDTO(int id, String type, boolean enabled) {
        this.id = id;
        this.type = type;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        RightDTO rightDTO = (RightDTO) o;
        return id == rightDTO.id &&
                enabled == rightDTO.enabled &&
                Objects.equals(type, rightDTO.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, enabled);
    }

    @Override
    public String toString() {
        return "RightDTO{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
