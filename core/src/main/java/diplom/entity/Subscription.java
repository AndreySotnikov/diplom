package diplom.entity;

import javax.persistence.*;

/**
 * Created on 13.02.2016.
 */
@javax.persistence.Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String email;
    @ManyToOne
    private Entity entity;
    private boolean active;
}