package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Howard on 12/11/2015.
 */
public class CallExp extends SMPLExp {

    String procId;
    SMPLExp exp;

    public CallExp(String id, SMPLExp exp) {
        this.procId = id;
        this.exp = exp;
    }

    public String getId()
    {
        return this.procId;
    }

    public SMPLExp getExp() {
        return exp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitCallExp(this, arg);
    }

    @Override
    public String toString() {
        String result = "call: \n";
        return result;
    }
}
