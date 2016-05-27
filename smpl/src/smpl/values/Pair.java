package smpl.values;

public class Pair extends CompoundType
{
	SMPLValue[] pair = new SMPLValue[2];

	public Pair(SMPLValue value1,SMPLValue value2)
	{
		pair[0] = value1;
		pair[1] = value2;
	}

	public Pair()
	{
		pair[0] = null;
		pair[1] = null;
	}

	public void setValue1(SMPLValue value)
	{
		pair[0] = value;
	}

	public void setValue2(SMPLValue value)
	{
		pair[1] = value;
	}

	public SMPLValue getValue1()
	{
		return pair[0];
	}

	public SMPLValue getValue2()
	{
		return pair[1];
	}

	public String toString() {
		return "( " + pair[0].getVal().toString() + " . " + pair[1].getVal().toString() + " )";
	}

}