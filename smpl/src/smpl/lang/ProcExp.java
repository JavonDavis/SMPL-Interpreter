package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;

/**
 * Created by Howard on 12/11/2015.
 */
public class ProcExp extends SMPLExp {

    ArrayList<String> params;
    SMPLStmtSequence body;

    public ProcExp(ArrayList<String> params, SMPLStmtSequence bdy) {
        this.params = params;
        this.body = bdy;
    }

    public ProcExp(ArrayList<String> params, SMPLExp bdy) {
        this.params = params;
        this.body = new SMPLStmtSequence(new SMPLStatement(bdy));
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public SMPLStmtSequence getBody() {
        return body;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitProcExp(this, arg);
    }

    @Override
    public String toString() {
        return "proc: " + params.toString();
    }
}
