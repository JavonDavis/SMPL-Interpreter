package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public class SMPLProgram extends SMPLExp {

    SMPLStmtSequence seq;

    public SMPLProgram(SMPLStmtSequence seq) {
        this.seq = seq;
    }

    public SMPLStmtSequence getSeq() {
        return seq;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitSMPLProgram(this, arg);
    }

    @Override
    public String toString() {
        return seq.toString();
    }
}
