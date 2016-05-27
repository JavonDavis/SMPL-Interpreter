package smpl.values;

import java.util.ArrayList;
import java.util.Iterator;

public class Vector extends CompoundType {

	protected ArrayList<SMPLValue> list;

	public Vector(ArrayList<SMPLValue> lst) {
		super();
		list = lst;
	}

	public Vector() {
		this(new ArrayList<SMPLValue>());
	}

	public boolean isEmpty() {
		return null == list || list.isEmpty();
	}

	public int size() {
		return list.size();
	}

	public String toString() {
		Iterator iter = list.iterator();
        String result = "[:";
        for (SMPLValue item : list) {
        	result += item.getVal().toString() + ","; 
        }
        return result.substring(0,result.length()-1) + ":]";
	}

	public ArrayList<SMPLValue> getList() {
		return list;
	}

	@Override
	public boolean equals(Object obj) {
		Vector vector = (Vector) obj;
		return list.equals(vector.getList());
	}

}