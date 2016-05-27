package smpl.lang;

import smpl.sys.SMPLException;

public class ExpMul extends SMPLExp {

    SMPLExp exp1, exp2;

    public ExpMul(SMPLExp e1, SMPLExp e2) {
        exp1 = e1;
        exp2 = e2;
    }

    public SMPLExp getExpL() {
        return exp1;
    }

    public SMPLExp getExpR() {
        return exp2;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpMul(this, arg);
    }

    public String toString() {
        return exp1.toString() + " * " + exp2.toString();
    }
}

