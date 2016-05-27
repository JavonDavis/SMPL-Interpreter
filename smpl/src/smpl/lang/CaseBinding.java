package smpl.lang;

/**
 * Created by Howard on 12/11/2015.
 */
public class CaseBinding {

    private SMPLExp logic;
    private SMPLExp result;
    private SMPLStmtSequence results;

    public CaseBinding(SMPLExp l, SMPLExp r) {

        this.logic = l;
        this.result = r;
    }

    public CaseBinding(SMPLExp l, SMPLStmtSequence r)
    {
        this.logic = l;
        this.results = r;
    }

    public SMPLExp getLogic()
    {
        return logic;
    }

    public SMPLExp getResult()
    {
        return this.result;
    }

    public SMPLExp getResults()
    {
        return this.results;
    }

}
