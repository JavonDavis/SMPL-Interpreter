package smpl.lang;

import smpl.sys.SMPLException;

public class ReadIntExp extends ReadExp {

	public ReadIntExp() {
		
	}

	@Override
	public String toString() {
		return "Read Int";
	}

	@Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitReadIntExp(this, arg);
    }

}
