package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public class PrintStmt extends SMPLExp {

    SMPLExp smplExp;

    public PrintStmt() {
    }

    public PrintStmt(SMPLExp smplExp) {
        this.smplExp = smplExp;
    }

    public SMPLExp getSmplExp() {
        return smplExp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitPrintStmt(this, arg);
    }

    @Override
    public String toString() {
        return "print: " + smplExp.toString();
    }
}
