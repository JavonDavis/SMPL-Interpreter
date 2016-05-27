package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public class LazyExp extends SMPLExp {

    SMPLExp exp;

    public LazyExp(SMPLExp exp) {
        this.exp = exp;
    }

    public SMPLExp getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitLazyExp(this, arg);
    }

    @Override
    public String toString() {
        return "lazy: " + exp.toString();
    }

}
