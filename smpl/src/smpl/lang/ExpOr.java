package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/22/2015.
 */
public class ExpOr extends SMPLExp {

    SMPLExp smplExpA, smplExpB;

    public ExpOr(SMPLExp smplExpA, SMPLExp smplExpB) {
        this.smplExpA = smplExpA;
        this.smplExpB = smplExpB;
    }

    public SMPLExp getSmplExpA() {
        return smplExpA;
    }

    public SMPLExp getSmplExpB() {
        return smplExpB;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpOr(this, arg);
    }

    @Override
    public String toString() {
        return "expor";
    }
}
