package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/20/2015.
 */
public class PrintLnStmt extends SMPLExp {

    SMPLExp exp;

    public PrintLnStmt() {
    }

    public PrintLnStmt(SMPLExp exp) {
        this.exp = exp;
    }

    public SMPLExp getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitPrintLnStmt(this, arg);
    }

    @Override
    public String toString() {
        return "println: " + exp.toString();
    }
}
