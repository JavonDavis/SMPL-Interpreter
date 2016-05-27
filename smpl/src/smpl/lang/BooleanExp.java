package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public class BooleanExp extends SMPLExp {

    String exp;

    public BooleanExp(String exp) {
        this.exp = exp;
    }

    public String getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitBooleanExp(this, arg);
    }

    @Override
    public String toString() {
        return "boolean: " + exp;
    }
}
