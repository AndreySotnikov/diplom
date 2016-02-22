package diplom.entity;

/**
 * Created on 22.02.2016.
 */
public enum Operator {
    EQ("=="),
    LE("<="),
    GE(">="),
    LT("<"),
    GT(">");
    String op;

    Operator(String op) {
        this.op = op;
    }
}
