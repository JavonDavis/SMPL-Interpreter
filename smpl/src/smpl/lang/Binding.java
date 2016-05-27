package smpl.lang;

/**
 * Created by Howard on 12/11/2015.
 */
public class Binding {

    protected String name;
    protected SMPLExp expression;

    public Binding(String var, SMPLExp exp) {
        name = var;
        expression = exp;
    }

    public String getName() {
        return name;
    }

    public SMPLExp getExpression() {
        return expression;
    }
}
