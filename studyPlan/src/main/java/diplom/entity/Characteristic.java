package diplom.entity;


import javax.persistence.*;

/**
 * Created on 22.02.2016.
 */
@Entity
public class Characteristic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String value;
    private String type;
    @Enumerated(EnumType.STRING)
    private Operator operator;
    @ManyToOne
    private Attribute attribute;
    @ManyToOne
    private File cfile;
    @ManyToOne
    private Subscription subscription;

    public Characteristic() {
    }

    public Characteristic(String value, String type, Attribute attribute, File cfile, Subscription subscription) {
        this.value = value;
        this.type = type;
        this.attribute = attribute;
        this.cfile = cfile;
        this.subscription = subscription;
    }

    public Characteristic(String value, String type, Operator operator, Attribute attribute, File cfile, Subscription subscription) {
        this.value = value;
        this.type = type;
        this.operator = operator;
        this.attribute = attribute;
        this.cfile = cfile;
        this.subscription = subscription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public File getcfile() {
        return cfile;
    }

    public void setcfile(File cfile) {
        this.cfile = cfile;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Characteristic that = (Characteristic) o;

        if (id != that.id) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (operator != that.operator) return false;
        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        if (cfile != null ? !cfile.equals(that.cfile) : that.cfile != null) return false;
        return subscription != null ? subscription.equals(that.subscription) : that.subscription == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (cfile != null ? cfile.hashCode() : 0);
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Characteristic{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", operator=" + operator +
                ", attribute=" + attribute +
                ", cfile=" + cfile +
                ", subscription=" + subscription +
                '}';
    }
}
