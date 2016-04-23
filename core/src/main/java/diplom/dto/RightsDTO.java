package diplom.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created on 10.04.2016.
 */
public class RightsDTO {
    private int entityId;
    private String name;
    private List<String> types;
    private boolean enabled;

    public RightsDTO() {
        this.types = new ArrayList<>();
    }

    public RightsDTO(int entityId, String name, List<String> types, boolean enabled) {
        this.entityId = entityId;
        this.name = name;
        this.types = types;
        this.enabled = enabled;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public RightsDTO addType(String type){
        this.getTypes().add(type);
        return this;
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
        RightsDTO rightsDTO = (RightsDTO) o;
        return entityId == rightsDTO.entityId &&
                Objects.equals(name, rightsDTO.name) &&
                Objects.equals(types, rightsDTO.types) &&
                enabled == rightsDTO.enabled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, name, types, enabled);
    }

    @Override
    public String toString() {
        return "RightsDTO{" +
                "enabled=" + enabled +
                ", types=" + types +
                ", name='" + name + '\'' +
                ", entityId=" + entityId +
                '}';
    }
}
