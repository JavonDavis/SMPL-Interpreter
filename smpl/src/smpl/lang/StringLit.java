package smpl.lang;

import smpl.sys.SMPLException;

public class StringLit extends SMPLExp {

	protected String string;

	public StringLit(String s) {
		string = s;
	}

	public String getString() {
		return string;
	}

	@Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitStringLiteral(this, arg);
    }

    @Override
    public String toString() {
    	return string;
    }

}