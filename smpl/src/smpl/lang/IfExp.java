package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/20/2015.
 */
public class IfExp extends SMPLStatement {

    SMPLExp predicate, consequent, alternative;

    public IfExp(SMPLExp predicate, SMPLExp consequent) {
        this.predicate = predicate;
        this.consequent = consequent;
        this.alternative = null;
    }

    public IfExp(SMPLExp predicate, SMPLExp consequent, SMPLExp alternative) {
        this.predicate = predicate;
        this.consequent = consequent;
        this.alternative = alternative;
    }

    public SMPLExp getPredicate() {
        return predicate;
    }

    public SMPLExp getConsequent() {
        return consequent;
    }

    public SMPLExp getAlternative() {
        return alternative;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitIfExp(this, arg);
    }

    @Override
    public String toString() {
        return "if " + predicate.toString() + " then " + consequent.toString() + " else " + alternative.toString();
    }
}
