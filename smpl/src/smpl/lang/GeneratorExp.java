package smpl.lang;

import smpl.sys.SMPLException;

public class GeneratorExp extends SMPLExp {

    private SMPLExp arg;
    private ProcExp proc;

    public GeneratorExp(SMPLExp e1,ProcExp pe) {
        this.arg = e1;
        this.proc = pe;
    }

    public SMPLExp getArg()
    {
        return this.arg;
    }
    public ProcExp getProc()
    {
        return this.proc;
    }

    @Override
    public <S, T> T visit(SMPLVisitor<S, T> v, S arg) throws SMPLException {
        return v.visitGeneratorExp(this, arg);
    }

    @Override
    public String toString() {
        return "<Generator obj>";
    }
}
