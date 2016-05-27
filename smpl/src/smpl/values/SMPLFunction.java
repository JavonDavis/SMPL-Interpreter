package smpl.values;

import smpl.lang.*;

import java.util.ArrayList;

/**
 * Created by Howard on 12/20/2015.
 */
public class SMPLFunction {

    ArrayList<String> params;
    SMPLStmtSequence body;
    SMPLContext env;
    SMPLExp arg;

    public SMPLFunction(ArrayList<String> params, SMPLStmtSequence bdy, SMPLContext env) {
        this.params = params;
        this.body = bdy;
        this.env = env;
    }

    public SMPLContext getEnv() {
        return env;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public SMPLStmtSequence getBody() {
        return body;
    }
}