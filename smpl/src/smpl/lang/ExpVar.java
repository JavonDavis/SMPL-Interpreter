package smpl.lang;

import smpl.sys.SMPLException;

public class ExpVar extends SMPLExp {

    String var;

    public ExpVar(String id) {
        var = id;
    }

    public String getVar() {
        return var;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpVar(this, arg);
    }

    public String toString() {
        return var;
    }
}
