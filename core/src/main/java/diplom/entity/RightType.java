package diplom.entity;

import javax.persistence.*;

/**
 * Created on 13.02.2016.
 */
public enum  RightType {
    READ("read"),
    WRITE("write"),
    DELETE("delete"),
    GRANT("grant");

    private String type;

    RightType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RightType{" +
                "type='" + type + '\'' +
                '}';
    }
}
