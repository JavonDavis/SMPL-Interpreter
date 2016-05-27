package smpl.lang;


import smpl.sys.SMPLException;
import java.util.ArrayList;
/**
 * Created by Howard on 12/11/2015.
 * @author Javon Davis
 */
public class CaseExp extends SMPLExp {

	private ArrayList<CaseBinding> bindings;

	public CaseExp(ArrayList<CaseBinding> bs)
	{
		bindings = bs;
	}

	public ArrayList<CaseBinding> getBindings()
	{
		return bindings;
	}

	@Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitCaseExp(this, arg);
    }

    @Override
    public String toString() {
        return "case stmt";
    }
}
