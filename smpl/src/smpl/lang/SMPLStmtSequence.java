package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Howard on 12/11/2015.
 */
public class SMPLStmtSequence extends SMPLExp {

    ArrayList<SMPLExp> seq;

    public SMPLStmtSequence() {
        seq = new ArrayList<>();
    }

    public SMPLStmtSequence(SMPLStatement s) {
        this();
        seq.add(s);
    }

    public ArrayList<SMPLExp> getSeq() {
        return seq;
    }

    public SMPLStmtSequence add(SMPLStatement s) {
        seq.add(s);
        return this;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitSMPLStmtSequence(this, arg);
    }

    @Override
    public String toString() {
        Iterator iter = seq.iterator();

        String result = "";
        while (iter.hasNext()) {
            result = result + iter.next().toString() + "\n";
        }

        return result;

    }
}
