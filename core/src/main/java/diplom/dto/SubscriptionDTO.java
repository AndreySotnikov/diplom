package diplom.dto;

/**
 * Created on 07.05.2016.
 */
public class SubscriptionDTO {
    private int id;
    private String file;
    private String login;
    private boolean active;

    public SubscriptionDTO() {
    }

    public SubscriptionDTO(String file, boolean active, String login) {
        this.file = file;
        this.active = active;
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubscriptionDTO that = (SubscriptionDTO) o;

        if (id != that.id) return false;
        if (active != that.active) return false;
        return file != null ? file.equals(that.file) : that.file == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (file != null ? file.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", file=" + file +
                ", active=" + active +
                '}';
    }
}
