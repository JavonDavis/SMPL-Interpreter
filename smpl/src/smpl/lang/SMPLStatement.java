package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public class SMPLStatement extends SMPLExp {

    SMPLExp exp;

    public SMPLStatement() {
        super();
    }

    public SMPLStatement(SMPLExp exp) {
        this.exp = exp;
    }

    public SMPLExp getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitSMPLStatement(this, arg);
    }

    @Override
    public String toString() {
        return exp.toString();
    }
}
