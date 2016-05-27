package smpl.lang;

import smpl.sys.SMPLException;

import java.util.ArrayList;

public class SMPLFunctionCallExp extends SMPLExp {

    String name;
    ArrayList<SMPLExp> arguments;

    public SMPLFunctionCallExp(String var, ArrayList<SMPLExp> args) {
        name = var;
        arguments = args;
    }

    SMPLFunctionCallExp(String var) {
        name = var;
        arguments = new ArrayList<SMPLExp>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<SMPLExp> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "function call";
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitSMPLFunctionCallExp(this, arg);
    }

}