package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/22/2015.
 */
public class ExpBitNot extends SMPLExp {

    SMPLExp exp;

    public ExpBitNot(SMPLExp exp) {
        this.exp = exp;
    }

    public SMPLExp getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpBitNot(this, arg);
    }

    @Override
    public String toString() {
        return "bitNot";
    }
}
