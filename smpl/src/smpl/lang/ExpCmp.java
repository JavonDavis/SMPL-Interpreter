package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/22/2015.
 */
public class ExpCmp extends SMPLExp {

    SMPLExp smplExpA, smplExpB;
    String cmp;

    public ExpCmp(String cmp, SMPLExp smplExpA, SMPLExp smplExpB) {
        this.cmp = cmp;
        this.smplExpA = smplExpA;
        this.smplExpB = smplExpB;
    }

    public String getCmp() {
        return cmp;
    }

    public SMPLExp getSmplExpA() {
        return smplExpA;
    }

    public SMPLExp getSmplExpB() {
        return smplExpB;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitExpCmp(this, arg);
    }

    @Override
    public String toString() {
        return "expcmp";
    }
}
