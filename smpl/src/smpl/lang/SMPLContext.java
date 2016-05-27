package smpl.lang;

import smpl.sys.SMPLEnvironment;
import smpl.sys.SMPLException;
import smpl.values.CompoundType;
import smpl.values.SMPLFunction;

import java.util.HashMap;

public class SMPLContext {

	protected SMPLEnvironment<Object> primitiveEnvironment;
	protected SMPLEnvironment<CompoundType> compoundTypeEnvironment;
	protected SMPLEnvironment<SMPLFunction> functionEnvironment;
    protected HashMap<String, String> builtinFunctions;

	public SMPLContext(SMPLEnvironment<Object> primitiveEnv, SMPLEnvironment<CompoundType> compoundEnvironment, SMPLEnvironment<SMPLFunction> funcEnvironment, HashMap<String, String> builtins) {
		primitiveEnvironment = primitiveEnv;
		compoundTypeEnvironment = compoundEnvironment;
		functionEnvironment = funcEnvironment;
        builtinFunctions = builtins;
	}

	public SMPLContext() {
		this(new SMPLEnvironment<Object>(), new SMPLEnvironment<CompoundType>(), new SMPLEnvironment<SMPLFunction>(), new HashMap<String, String>());
	}

    public SMPLContext extend() {
        return new SMPLContext(primitiveEnvironment.copy(), compoundTypeEnvironment.copy(), functionEnvironment.copy(), builtinFunctions);
    }

	public SMPLEnvironment<Object> getPrimitiveEnvironment() {
		return this.primitiveEnvironment;
	}

	public SMPLEnvironment<CompoundType> getCompoundTypeEnvironment() {
		return this.compoundTypeEnvironment;
	}

	public SMPLEnvironment<SMPLFunction> getFunctionEnvironment() {
		return this.functionEnvironment;
	}

    public String getBuiltInName(String name) {
        return builtinFunctions.get(name);
    }

    public void addBuiltIn(String name) {
        builtinFunctions.put(name, name);
    }

	public static SMPLContext makeGlobalEnv() {
        SMPLContext globalEnv = new SMPLContext();
        //BuiltinFunctions go here

        globalEnv.addBuiltIn("pair");
        globalEnv.addBuiltIn("car");
        globalEnv.addBuiltIn("cdr");
        globalEnv.addBuiltIn("pair?");
        globalEnv.addBuiltIn("size");
        globalEnv.addBuiltIn("eqv?");
        globalEnv.addBuiltIn("equal?");
        globalEnv.addBuiltIn("substr");
        globalEnv.addBuiltIn("list");

        // add definitions for any primitive procedures or
        // constants here

        return globalEnv;
    }

    /**
     * Store a binding for the given name to the given SMPL function.
     * @param name The identifier of the binding
     * @param p The SMPL function to be associated with the name
     */
    public void putF(String name, SMPLFunction p) {
    	functionEnvironment.put(name, p);
    }

    /**
     * Lookup a reference to a SMPL function.
     * @param name The identifier of the SMPL function
     * @return The SMPL function associated with the given name in this context
     * @throws SMPLException if the name is not bound to a function in this
     * context
     */
    public SMPLFunction getF(String name) throws SMPLException {
    	return functionEnvironment.get(name);
    }

    /**
     * Store a binding for the given name to the given Object.
     * @param name The identifier of the binding
     * @param p The Object to be associated with the name
     */
    public void putP(String name, Object p) {
    	primitiveEnvironment.put(name, p);
    }

    /**
     * Lookup a reference to a Object.
     * @param name The identifier of the Object
     * @return The Object associated with the given name in this context
     * @throws SMPLException if the name is not bound to an Object in this
     * context
     */
    public Object getP(String name) throws SMPLException {
    	return primitiveEnvironment.get(name);
    }

	/**
     * Store a binding for the given name to the given Data Structure.
     * @param name The identifier of the binding
     * @param p The Data Structure to be associated with the name
     */
    public void putCT(String name, CompoundType p) {
    	compoundTypeEnvironment.put(name, p);
    }

    /**
     * Lookup a reference to a CompoundType.
     * @param name The identifier of the CompoundType
     * @return The CompoundType associated with the given name in this context
     * @throws SMPLException if the name is not bound to an CompoundType in this
     * context
     */
    public CompoundType getCT(String name) throws SMPLException {
    	return compoundTypeEnvironment.get(name);
    }

}