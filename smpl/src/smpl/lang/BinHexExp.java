package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/22/2015.
 */
public class BinHexExp extends SMPLExp {

    String binOrHexExp;

    public BinHexExp(String binOrHexExp) {
        this.binOrHexExp = binOrHexExp;
    }

    public String getBinOrHexExp() {
        return binOrHexExp;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitBinOrHexExp(this, arg);
    }

    @Override
    public String toString() {
        return binOrHexExp;
    }
}
