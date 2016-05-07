package diplom.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 07.05.2016.
 */
public class SubscriptionEvent {
    private String eventType;
    private String entityName;
    private String login;
    private List<String> users;

    public SubscriptionEvent() {
        users = new ArrayList<>();
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEntityName() {
        return entityName;
    }
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }
}
