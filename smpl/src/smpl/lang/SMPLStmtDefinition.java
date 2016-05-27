package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/19/2015.
 */
public class SMPLStmtDefinition extends SMPLExp {

    String id;
    SMPLExp exp;

    public SMPLStmtDefinition(String id, SMPLExp exp) {
        this.exp = exp;
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public SMPLExp getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitSMPLStmtDefinition(this, arg);
    }

    @Override 
    public String toString() {
        return "id: " + exp.toString();
    }
}
