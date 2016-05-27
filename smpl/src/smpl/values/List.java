package smpl.values;

public class List extends Pair {

	public List(SMPLValue value1, SMPLValue<List> value2) {
		super(value1, value2);
	}

	public List() {
		super();
	}

	public List add(SMPLValue val) {
		setValue1(val);
		setValue2(new SMPLValue<List>(new List()));
		return (List) getValue2().getVal();
	}

	public List next(){
		return (List) getValue2().getVal();
	}

	public String toString() {
		String result = "LIST: ";
		List temp = this;
		while (temp.getValue2() != null) {
			result += temp.getValue1().getVal().toString() + " ";
			temp = temp.next();
		}
		return result;
	}
}