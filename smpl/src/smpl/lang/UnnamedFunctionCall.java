package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;


public class UnnamedFunctionCall extends SMPLExp {

	protected ProcExp procedureExpression;
	protected ArrayList<SMPLExp> arguments;

	public UnnamedFunctionCall(ProcExp procExp, ArrayList<SMPLExp> args) {
		procedureExpression = procExp;
		arguments = args;
	}

	public ProcExp getProcExp() {
		return procedureExpression;
	}

	public ArrayList<SMPLExp> getArguments() {
		return arguments;
	}

	public String toString() {
		return "Calling: " + procedureExpression.toString() + "with " + arguments.toString();
	}

	@Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitUnnamedFunctionCall(this, arg);
    }

}