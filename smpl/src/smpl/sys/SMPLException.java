package smpl.sys;

/**
 * Created by Howard on 12/11/2015.
 */

public class SMPLException extends Exception {
    private static final long serialVersionUID = 1L;

    SMPLException cause;

    public SMPLException() {
        super();
    }

    public SMPLException(String s) {
        super(s);
    }

    public SMPLException(String s, SMPLException smple) {
        super(s);
        cause = smple;
    }

    public String report() {
        if (cause == null)
            return getMessage();
        else
            return getMessage() + " caused by " + cause.report();
    }
}