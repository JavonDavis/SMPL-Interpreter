package smpl.lang;

import smpl.sys.SMPLException;

public class ExpLit extends SMPLExp {

    Integer val;

    public ExpLit(Integer v) {
        val = v;
    }

    public Integer getVal() {
        return val;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpLit(this, arg);
    }

    public String toString() {
        return Integer.toString(val);
    }
}

