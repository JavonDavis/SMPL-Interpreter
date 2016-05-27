package smpl.lang;

import smpl.sys.SMPLException;

/**
 * Created by Howard on 12/11/2015.
 */
public abstract class SMPLExp {

    public abstract <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException;

    public abstract String toString();
}
