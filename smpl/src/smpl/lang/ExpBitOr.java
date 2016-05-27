package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/22/2015.
 */
public class ExpBitOr extends SMPLExp {

    SMPLExp smplExpA, smplExpB;

    public ExpBitOr(SMPLExp smplExpA, SMPLExp smplExpB) {
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
        return v.visitExpBitOr(this, arg);
    }

    @Override
    public String toString() {
        return "bitor";
    }
}
