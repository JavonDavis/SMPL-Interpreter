package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Howard on 12/20/2015.
 */
public class ListLiteral extends SMPLExp {

    ArrayList<SMPLExp> expArrayList;

    public ListLiteral() {
        expArrayList = new ArrayList<SMPLExp>();
    }

    public ListLiteral(ArrayList<SMPLExp> expArrayList) {
        this.expArrayList = expArrayList;
    }

    public ArrayList<SMPLExp> getExpArrayList() {
        return expArrayList;
    }

    public ListLiteral add(SMPLExp s) {
        expArrayList.add(s);
        return this;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitListLiteral(this, arg);
    }

    @Override
    public String toString() {
        Iterator iter = expArrayList.iterator();

        String result = "list: ";
        while (iter.hasNext()) {
            result = result + iter.next().toString() + "\n";
        }

        return result;
    }
}
