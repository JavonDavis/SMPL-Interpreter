package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;

/**
 * Created by Howard on 12/11/2015.
 */
public class CompoundExp extends SMPLExp {

    SMPLStmtSequence smplSequence;

    public CompoundExp(SMPLStmtSequence smplSequence) {
        this.smplSequence = smplSequence;
    }

    public SMPLStmtSequence getSequence() {
        return smplSequence;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitCompoundExp(this, arg);
    }

    @Override
    public String toString() {
        return "compoundExp: " + smplSequence.toString();
    }
}
