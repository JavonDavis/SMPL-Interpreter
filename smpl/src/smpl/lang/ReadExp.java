package smpl.lang;

import smpl.sys.SMPLException;

public class ReadExp extends SMPLExp {

	public ReadExp() {
		
	}

	@Override
	public String toString(){
		return "Read";
	}

	@Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitReadExp(this, arg);
    }

}
