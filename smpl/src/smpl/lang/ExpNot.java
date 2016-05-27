package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/22/2015.
 */
public class ExpNot extends SMPLExp {

    SMPLExp smplExp;

    public ExpNot(SMPLExp smplExp) {
        this.smplExp = smplExp;
    }

    public SMPLExp getSmplExp() {
        return smplExp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpNot(this, arg);
    }

    @Override
    public String toString() {
        return "expnot";
    }
}
