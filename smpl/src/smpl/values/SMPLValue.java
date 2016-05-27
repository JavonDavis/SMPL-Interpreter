package smpl.values;

/**
 * Created by Howard on 12/20/2015.
 */
public class SMPLValue<T> {

	public static SMPLValue NO_RESULT = new SMPLValue<String>("NO RESULT");

    T val;

    public SMPLValue() {
    }

    public SMPLValue(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }
}
