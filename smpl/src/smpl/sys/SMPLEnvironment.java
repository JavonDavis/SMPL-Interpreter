package smpl.sys;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Howard on 12/20/2015.
 */
public class SMPLEnvironment<T> {

    HashMap<String, T> dictionary;
    SMPLEnvironment<T> parent;

    /**
     * Create a new (empty) top level SMPL Environment.
     */
    public SMPLEnvironment() {
        parent = null;
        dictionary = new HashMap<String, T>();
    }

    /**
     * Creates a new <code>SMPLEnvironment</code> instance that is
     * initialised with the given collection of bindings
     *
     * @param p      The parent environment that this one extends.
     * @param ids    the collection of identifiers to be bound.
     * @param values the corresponding collection of values for the
     *               identifiers.
     */
    public SMPLEnvironment(SMPLEnvironment<T> p, ArrayList<String> ids, ArrayList<T> values) {
        this();
        parent = p;
        for (int i = 0; i < ids.size(); i++) {
            dictionary.put(ids.get(i), values.get(i));
        }
    }

    public SMPLEnvironment<T> copy()
    {
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<T> values = new ArrayList<>();
        return new SMPLEnvironment<T>(this, ids, values);
    }

    /**
     * Store a binding for the given identifier to the given T
     * object within this environment.
     *
     * @param id    the name to be bound
     * @param value the value to which the name is bound.
     */
    public void put(String id, T value) {
        dictionary.put(id, value);
    }

    /**
     * Return the T object associated with the given identifier.
     *
     * @param id the identifier.
     * @return the T object associated with the identifier in
     * this environment.
     * @throws SMPLException if <code>id</code> is unbound
     */
    public T get(String id) throws SMPLException {
        T result = dictionary.get(id);
        if (result == null)
            if (parent == null)
                throw new SMPLException("Unbound variable " + id);
            else
                return parent.get(id);
        else
            return result;
    }

    /**
     * Create a string representation of this SMPL environment.
     *
     * @return a string of all the names bound in this environment.
     */
    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();

        for (String name : dictionary.keySet()) {
            result = result.append(name);
            result = result.append(" ");
        }
        return result.toString();
    }
}
