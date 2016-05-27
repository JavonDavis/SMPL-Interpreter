package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;

/**
 * Created by Howard on 12/11/2015.
 */
public class LetStmt extends SMPLExp {

    ArrayList<Binding> bindings;
    SMPLStmtSequence body;
    SMPLExp statement;

    public LetStmt(ArrayList<Binding> bndngs, SMPLExp statement) {
        bindings = bndngs;
        this.statement = statement;
    }

    public LetStmt(ArrayList<Binding> bndngs, SMPLStmtSequence seq) {
        bindings = bndngs;
        body = seq;
    }

    public ArrayList<Binding> getBindings() {
        return bindings;
    }

    public SMPLStmtSequence getBody() {
        return body;
    }

    public SMPLExp getExp()
    {
        return this.statement;
    }

    @Override
    public String toString() {
        return "let";
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitLetStmt(this, arg);
    }

}
