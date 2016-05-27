package smpl.lang;

import smpl.sys.SMPLException;
import smpl.values.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by Howard on 12/20/2015.
 */
public class SMPLEvaluator implements SMPLVisitor<SMPLContext, SMPLValue> {

    public SMPLEvaluator() {
    }

    @Override
    public SMPLValue visitSMPLProgram(SMPLProgram smplProgram, SMPLContext arg) throws SMPLException {
        return smplProgram.getSeq().visit(this, arg);
    }

    @Override
    public SMPLValue visitSMPLStatement(SMPLStatement smplStatement, SMPLContext arg) throws SMPLException {
        return smplStatement.getExp().visit(this, arg);
    }

    @Override
    public SMPLValue visitSMPLStmtDefinition(SMPLStmtDefinition smplStmtDefinition, SMPLContext arg) throws SMPLException {
        SMPLValue smplValue = smplStmtDefinition.getExp().visit(this, arg);
        String id = smplStmtDefinition.getId();
        Object t = smplValue.getVal();

        if (t.getClass().equals(Integer.class)) {
            arg.putP(id, t);
        }

        else if (t.getClass().equals(String.class)) {
            arg.putP(id, t);
        }

        else if (t.getClass().equals(Vector.class)) {
            arg.putCT(id, (CompoundType)t);
        }

        else if (t.getClass().equals(Pair.class)) {
            arg.putCT(id, (CompoundType)t);
        }

        else if (t.getClass().equals(List.class)) {
            arg.putCT(id, (CompoundType)t);
        }

        else if (t.getClass().equals(SMPLFunction.class)) {
            arg.putF(id, (SMPLFunction)t);
        }

        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitSMPLStmtSequence(SMPLStmtSequence smplStmtSequence, SMPLContext arg) throws SMPLException {
        SMPLStatement smplStatement;
        ArrayList seq = smplStmtSequence.getSeq();
        Iterator iterator = seq.iterator();

        SMPLValue val = new SMPLValue();

        while (iterator.hasNext()) {
            smplStatement = (SMPLStatement) iterator.next();
            val = smplStatement.visit(this, arg);
        }

        return val;
    }

    public SMPLValue visitPairExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 2)
            throw new SMPLException(name + " takes two(2) arguments");

        SMPLExp expA = expressions.get(0);
        SMPLExp expB = expressions.get(1);

        SMPLValue value1 = expA.visit(this, arg);
        SMPLValue value2 = expB.visit(this, arg);

        Pair pair = new Pair(value1,value2);
        return new SMPLValue<Pair>(pair);
    }

    public SMPLValue visitCarExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 1)
            throw new SMPLException(name + " takes one(1) argument");

        SMPLValue<Pair> pair = expressions.get(0).visit(this, arg);
        return pair.getVal().getValue1();
    }

    public SMPLValue visitCdrExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 1)
            throw new SMPLException(name + " takes one(1) argument");

        SMPLValue<Pair> pair = expressions.get(0).visit(this, arg);
        return pair.getVal().getValue2();
    }

    public SMPLValue visitPairQExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 1)
            throw new SMPLException(name + " takes one(1) argument");

        SMPLValue<Pair> pair = expressions.get(0).visit(this, arg);
        Boolean isPair = new Boolean(pair.getVal() instanceof Pair);
        return new SMPLValue<Boolean>(isPair);
    }

    public SMPLValue visitListExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        ArrayList<SMPLValue> results = new ArrayList<SMPLValue>();

        for (SMPLExp expression : expressions) {
            results.add(expression.visit(this, arg));
        }

        List list = new List();
        List subList = list;

        int count = 0;

        while (count < results.size()) {
            subList = subList.add(results.get(count));
            count++;
        }
        
        return new SMPLValue<List>(list);
    }

    public SMPLValue visitCaseExp(CaseExp caseExp, SMPLContext arg) throws SMPLException {
        ArrayList<CaseBinding> bindings = caseExp.getBindings();

        for(int i = bindings.size()-1;i>-1;i--)
        {
            CaseBinding binding = bindings.get(i);
            SMPLExp logic = binding.getLogic();
            SMPLValue<Boolean> value = logic.visit(this,arg);
            if(value.getVal())
            {
                SMPLExp resultm = binding.getResult();
                if(resultm!=null) 
                {
                    resultm.visit(this,arg);
                }
                else
                {
                    binding.getResults().visit(this,arg);
                }
                break;
            }
                

        }
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitVectorExp(VectorExp vectorExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = vectorExp.getExpArrayList();
        ArrayList<SMPLValue> results = new ArrayList<SMPLValue>();

        for (SMPLExp expression : expressions) {
            if(expression instanceof GeneratorExp)
            {
                SMPLValue<ArrayList<SMPLValue>> partition = expression.visit(this, arg);
                ArrayList<SMPLValue> values = partition.getVal();
                results.addAll(values);
            }
            else
                results.add(expression.visit(this, arg));
        }

        Vector vector = new Vector(results);

        return new SMPLValue<Vector>(vector);
    }

    @Override
    public SMPLValue visitGeneratorExp(GeneratorExp generatorExp, SMPLContext state) throws SMPLException {
        SMPLExp arg = generatorExp.getArg();
        ProcExp proc = generatorExp.getProc();

        SMPLValue<Integer> end = arg.visit(this, state);
        SMPLValue<SMPLFunction> functionValue = proc.visit(this, state);
        SMPLFunction function = functionValue.getVal();
        ArrayList<String> parameters = function.getParams();

        SMPLContext newEnvironment = function.getEnv().extend();

        String parameter = parameters.get(0);
        

        ArrayList<SMPLValue> results = new ArrayList<SMPLValue>();
        for (int param = 0 ; param<end.getVal(); param++) {

                newEnvironment.putP(parameter, param);
                results.add(function.getBody().visit(this, newEnvironment));
                    
        }

        return new SMPLValue<ArrayList<SMPLValue>>(results);
    }


    @Override
    public SMPLValue visitListLiteral(ListLiteral ll, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = ll.getExpArrayList();
        ArrayList<SMPLValue> results = new ArrayList<SMPLValue>();

        for (SMPLExp expression : expressions) {
            results.add(expression.visit(this, arg));
        }

        List list = new List();
        List subList = list;

        int count = 0;

        while (count < results.size()) {
            subList = subList.add(results.get(count));
            count++;
        }
        
        return new SMPLValue<List>(list);
    }

    public SMPLValue visitVectorSizeExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 1)
            throw new SMPLException(name + " takes one(1) argument");

        SMPLValue<Vector> value = expressions.get(0).visit(this, arg);
        Integer size = new Integer(value.getVal().size());
        return new SMPLValue<Integer>(size);
    }

    public SMPLValue visitEQVExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 2)
            throw new SMPLException(name + " takes two(2) arguments");

        SMPLValue value1 = expressions.get(0).visit(this, arg);
        SMPLValue value2 = expressions.get(1).visit(this, arg);

        Boolean same = new Boolean(value1 == value2);
        return new SMPLValue<Boolean>(same);
    }

    public SMPLValue visitEQExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 2)
            throw new SMPLException(name + " takes two(2) arguments");

        SMPLValue value1 = expressions.get(0).visit(this, arg);
        SMPLValue value2 = expressions.get(1).visit(this, arg);

        Boolean same = new Boolean(((Object)value1.getVal()).equals(((Object) value2.getVal())));
        return new SMPLValue<Boolean>(same);
    }

    public SMPLValue visitSubStrExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();
        String name = smplFunctionCallExp.getName();
        if (expressions.size() != 3)
            throw new SMPLException(name + " takes three(3) arguments");

        ArrayList<SMPLValue> arguments = new ArrayList<SMPLValue>();

        for(int i = 0; i < 3; i++) {
            arguments.add(expressions.get(i).visit(this, arg));
        }

        String str = (String) arguments.get(0).getVal();
        Integer beginning = (Integer) arguments.get(1).getVal();
        Integer end = (Integer) arguments.get(2).getVal();
        return new SMPLValue<String>(str.substring(beginning.intValue(), end.intValue()));
    }

    @Override
    public SMPLValue visitReadExp(ReadExp rExp, SMPLContext arg) throws SMPLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> ");
        String s = scanner.nextLine();

        System.out.println(s);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitReadIntExp(ReadIntExp rIntExp, SMPLContext arg) throws SMPLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">> ");
        int i = scanner.nextInt();

        System.out.println(i);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitLetStmt(LetStmt letStmt, SMPLContext arg) throws SMPLException {
        ArrayList<Binding> bindings = letStmt.getBindings();

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<SMPLValue> results = new ArrayList<SMPLValue>();

        for (Binding binding : bindings) {
            names.add(binding.getName());
            results.add(binding.getExpression().visit(this, arg));
        }

        SMPLContext newEnvironment = arg.extend();

        for (int i = 0; i < results.size(); i++) {

            String name = names.get(i);
            Object t = results.get(i).getVal();

            if (t.getClass().equals(Integer.class)) {
                newEnvironment.putP(name, t);
            }

            else if (t.getClass().equals(String.class)) {
                newEnvironment.putP(name, t);
            }

            else if (t.getClass().equals(Vector.class)) {
                newEnvironment.putCT(name, (CompoundType)t);
            }

            else if (t.getClass().equals(Pair.class)) {
                newEnvironment.putCT(name, (CompoundType)t);
            }

            else if (t.getClass().equals(List.class)) {
                newEnvironment.putCT(name, (CompoundType)t);
            }

            else if (t.getClass().equals(SMPLFunction.class)) {
                newEnvironment.putF(name, (SMPLFunction)t);
            }

        }

        SMPLStmtSequence smplStmtSequence = letStmt.getBody();
        if(smplStmtSequence!= null)
        {
            return smplStmtSequence.visit(this, newEnvironment);
        }
        else
        {
            SMPLExp statement = letStmt.getExp();
            return statement.visit(this, newEnvironment);
        }

    }

    @Override
    public SMPLValue visitSMPLFunctionCallExp(SMPLFunctionCallExp smplFunctionCallExp, SMPLContext arg) throws SMPLException {
        String name = smplFunctionCallExp.getName();
        String builtInFunctionName = arg.getBuiltInName(name);
            if (builtInFunctionName == null || builtInFunctionName.isEmpty()) {
                // If the function being called is not built in, business as usual
                SMPLFunction function = arg.getF(name);

                ArrayList<String> parameters = function.getParams();
                ArrayList<SMPLValue> arguments = new ArrayList<SMPLValue>();

                ArrayList<SMPLExp> expressions = smplFunctionCallExp.getArguments();

                for (SMPLExp exp : expressions) {
                    arguments.add(exp.visit(this, arg));
                }

                SMPLContext newEnvironment = function.getEnv().extend();

                for (int i = 0; i < arguments.size(); i++) {

                    String parameter = parameters.get(i);
                    Object t = arguments.get(i).getVal();

                    if (t.getClass().equals(Integer.class)) {
                        newEnvironment.putP(parameter, t);
                    }

                    else if (t.getClass().equals(String.class)) {
                        newEnvironment.putP(parameter, t);
                    }

                    else if (t.getClass().equals(Vector.class)) {
                        newEnvironment.putCT(name, (CompoundType)t);
                    }

                    else if (t.getClass().equals(Pair.class)) {
                        newEnvironment.putCT(name, (CompoundType)t);
                    }

                    else if (t.getClass().equals(List.class)) {
                        newEnvironment.putCT(name, (CompoundType)t);
                    }

                    else if (t.getClass().equals(SMPLFunction.class)) {
                        newEnvironment.putF(parameter, (SMPLFunction)t);
                    }
                }
                return function.getBody().visit(this, newEnvironment);
            } else {
                // If the function being called is built in, do the appropriate action
                switch (name) {
                    case "substr":
                        return visitSubStrExp(smplFunctionCallExp, arg);
                    case "pair":
                        return visitPairExp(smplFunctionCallExp, arg);
                    case "car":
                        return visitCarExp(smplFunctionCallExp, arg);
                    case "cdr":
                        return visitCdrExp(smplFunctionCallExp, arg);
                    case "pair?":
                        return visitPairQExp(smplFunctionCallExp, arg);
                    case "size":
                        return visitVectorSizeExp(smplFunctionCallExp, arg);
                    case "eqv?":
                        return visitEQVExp(smplFunctionCallExp, arg);
                    case "equal?":
                        return visitEQExp(smplFunctionCallExp, arg);
                    case "list":
                        return visitListExp(smplFunctionCallExp, arg);
                }
            }
            return SMPLValue.NO_RESULT;
        }

    @Override
    public SMPLValue visitProcExp(ProcExp procExp, SMPLContext arg) throws SMPLException {
        ArrayList<String> parameters = procExp.getParams();
        SMPLStmtSequence body = procExp.getBody();
        SMPLFunction smplFunction = new SMPLFunction(parameters, body, arg);

        return new SMPLValue<SMPLFunction>(smplFunction);
    }

    @Override
    public SMPLValue visitIfExp(IfExp ifExp, SMPLContext arg) throws SMPLException {
        SMPLExp pred = ifExp.getPredicate();
        SMPLExp conseq = ifExp.getConsequent();

        SMPLValue<Boolean> value = pred.visit(this, arg);
        if (value.getVal()) {
            return conseq.visit(this,arg);
        } else {
            if (ifExp.getAlternative() != null) {
                return ifExp.getAlternative().visit(this, arg);
            } else {
                return SMPLValue.NO_RESULT;
            }
        }
    }

    @Override
    public SMPLValue visitLazyExp(LazyExp lazyExp, SMPLContext arg) throws SMPLException {
        return null;
    }

    @Override
    public SMPLValue visitCompoundExp(CompoundExp compoundExp, SMPLContext arg) throws SMPLException {
        SMPLStmtSequence sequence = compoundExp.getSequence();
        return sequence.visit(this, arg);
    }

    @Override
    public SMPLValue visitCallExp(CallExp callExp, SMPLContext arg) throws SMPLException {
        String procId = callExp.getId();
        SMPLFunction function = arg.getF(procId);

        ArrayList<String> parameters = function.getParams();
        ArrayList<SMPLValue> arguments = new ArrayList<>();

        SMPLExp exp = callExp.getExp();
        if(exp!=null)
        {
            SMPLValue<List> lst = exp.visit(this,arg);
            List result = lst.getVal();

            List temp = result;
            while (temp.getValue2() != null) {
                arguments.add(temp.getValue1());
                temp = temp.next();
            }
        }

        SMPLContext newEnvironment = function.getEnv().extend();

        for (int i = 0; i < arguments.size(); i++) {

            String parameter = parameters.get(i);
            Object t = arguments.get(i).getVal();

            if (t.getClass().equals(Integer.class)) {
                newEnvironment.putP(parameter, t);
            }

            else if (t.getClass().equals(String.class)) {
                newEnvironment.putP(parameter, t);
            }

            else if (t.getClass().equals(Vector.class)) {
                newEnvironment.putCT(parameter, (CompoundType)t);
            }

            else if (t.getClass().equals(Pair.class)) {
                newEnvironment.putCT(parameter, (CompoundType)t);
            }

            else if (t.getClass().equals(List.class)) {
                newEnvironment.putCT(parameter, (CompoundType)t);
            }

            else if (t.getClass().equals(SMPLFunction.class)) {
                newEnvironment.putF(parameter, (SMPLFunction)t);
            }
        }
        return function.getBody().visit(this, newEnvironment);
    }

    @Override
    public SMPLValue visitBooleanExp(BooleanExp booleanExp, SMPLContext arg) throws SMPLException {
        if (booleanExp.getExp().equals("#t"))
            return new SMPLValue<Boolean>(true);
        else
            return new SMPLValue<Boolean>(false);
    }

    @Override
    public SMPLValue visitPrintStmt(PrintStmt printStmt, SMPLContext arg) throws SMPLException {
        if (printStmt.getSmplExp().toString().contains("\\n")) {
            int j = 1;
            StringBuffer buffer = new StringBuffer(printStmt.getSmplExp().toString());
            for (int i = 0; i < printStmt.getSmplExp().toString().length() ; i ++ ) {
                if (printStmt.getSmplExp().toString().charAt(i) == '\\') {
                    if (printStmt.getSmplExp().toString().charAt(j) == 'n')
                        buffer.replace(i, j+1, "\n");
                    else break;
                }
                j++;
            }
            System.out.print(buffer);
        } else {
            System.out.print(printStmt.getSmplExp().visit(this, arg).getVal());
        }
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitPrintLnStmt(PrintLnStmt printLnStmt, SMPLContext arg) throws SMPLException {
        if (printLnStmt.getExp().toString().contains("\\n")) {
            int j = 1;
            StringBuffer buffer = new StringBuffer(printLnStmt.getExp().toString());
            for (int i = 0; i < printLnStmt.getExp().toString().length() ; i ++ ) {
                if (printLnStmt.getExp().toString().charAt(i) == '\\') {
                    if (printLnStmt.getExp().toString().charAt(j) == 'n')
                        buffer.replace(i, j+1, "\n");
                    else break;
                }
                j++;
            }
            System.out.println(buffer);
        } else {
            System.out.println(printLnStmt.getExp().visit(this, arg).getVal());
        }
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpSub(ExpSub expSub, SMPLContext arg) throws SMPLException {
        SMPLValue<Integer> val1, val2;
        val1 = expSub.getExpL().visit(this, arg);
        val2 = expSub.getExpR().visit(this, arg);

        Integer num1, num2;

        num1 = val1.getVal();
        num2 = val2.getVal();

        SMPLValue<Integer> result = new SMPLValue<Integer>(new Integer(num1.intValue() - num2.intValue()));

        return result;
    }

    @Override
    public SMPLValue visitExpAdd(ExpAdd expAdd, SMPLContext arg) throws SMPLException {
        SMPLValue<Integer> val1, val2;
        val1 = expAdd.getExpL().visit(this, arg);
        val2 = expAdd.getExpR().visit(this, arg);

        Integer num1, num2;

        num1 = val1.getVal();
        num2 = val2.getVal();

        SMPLValue<Integer> result = new SMPLValue<Integer>(new Integer(num1.intValue() + num2.intValue()));

        return result;
    }

    @Override
    public SMPLValue visitExpDiv(ExpDiv expDiv, SMPLContext arg) throws SMPLException {
        SMPLValue<Integer> val1, val2;
        val1 = expDiv.getExpL().visit(this, arg);
        val2 = expDiv.getExpR().visit(this, arg);

        Integer num1, num2;

        num1 = val1.getVal();
        num2 = val2.getVal();

        SMPLValue<Integer> result = new SMPLValue<Integer>(new Integer(num1.intValue() / num2.intValue()));

        return result;
    }

    @Override
    public SMPLValue visitExpLit(ExpLit expLit, SMPLContext arg) throws SMPLException {
        return new SMPLValue<Integer>(expLit.getVal());
    }

    @Override
    public SMPLValue visitExpMod(ExpMod expMod, SMPLContext arg) throws SMPLException {
        SMPLValue<Integer> val1, val2;
        val1 = expMod.getExpL().visit(this, arg);
        val2 = expMod.getExpR().visit(this, arg);

        Integer num1, num2;

        num1 = val1.getVal();
        num2 = val2.getVal();

        SMPLValue<Integer> result = new SMPLValue<Integer>(new Integer(num1.intValue() % num2.intValue()));

        return result;
    }

    @Override
    public SMPLValue visitExpMul(ExpMul expMul, SMPLContext arg) throws SMPLException {
        SMPLValue<Integer> val1, val2;
        val1 = expMul.getExpL().visit(this, arg);
        val2 = expMul.getExpR().visit(this, arg);

        Integer num1, num2;

        num1 = val1.getVal();
        num2 = val2.getVal();

        SMPLValue<Integer> result = new SMPLValue<Integer>(new Integer(num1.intValue() * num2.intValue()));

        return result;
    }

    @Override
    public SMPLValue visitExpVar(ExpVar expVar, SMPLContext arg) throws SMPLException {

        String name = expVar.getVar();

        try {
            Object result = arg.getP(name);
            if (result.getClass().equals(String.class)) {
                return new SMPLValue<String>((String) result);
            }
        } catch (SMPLException e) {}

        try {
            Object result = arg.getP(name);
            if (result.getClass().equals(Integer.class)) {
                return new SMPLValue<Integer>((Integer) result);
            }
        } catch (SMPLException e) {}

        try {
            Object result = arg.getP(name);
            if (result.getClass().equals(Boolean.class)) {
                return new SMPLValue<Boolean>((Boolean) result);
            }
        } catch (SMPLException e) {}

        try {
            SMPLFunction result = arg.getF(name);
            return new SMPLValue<SMPLFunction>(result);
        } catch (SMPLException e) {}

        try {
            CompoundType result = arg.getCT(name);
            return new SMPLValue<CompoundType>(result);
        } catch (SMPLException e) {}

        throw new SMPLException("Unbound variable: " + name);

    }

    @Override
    public SMPLValue visitStringLiteral(StringLit strLit, SMPLContext arg) throws SMPLException {
        return new SMPLValue<String>(strLit.getString());
    }

    @Override
    public SMPLValue visitUnnamedFunctionCall(UnnamedFunctionCall ufc, SMPLContext arg) throws SMPLException {
        SMPLValue<SMPLFunction> value = ufc.getProcExp().visit(this, arg);
        SMPLFunction function = value.getVal();


        ArrayList<String> formalParameters = function.getParams();

        ArrayList<SMPLValue> arguments = new ArrayList<SMPLValue>();
        ArrayList<SMPLExp> expressions = ufc.getArguments();

        for (SMPLExp exp : expressions) {
            arguments.add(exp.visit(this, arg));
        }

        SMPLContext newEnvironment = arg.extend();

        for (int i = 0; i < arguments.size(); i++) {

            String parameter = formalParameters.get(i);
            Object t = arguments.get(i).getVal();

            if (t.getClass().equals(Integer.class)) {
                newEnvironment.putP(parameter, t);
            }

            else if (t.getClass().equals(String.class)) {
                newEnvironment.putP(parameter, t);
            }

            else if (t.getClass().equals(CompoundType.class)) {
                newEnvironment.putCT(parameter, (CompoundType)t);
            }

            else if (t.getClass().equals(SMPLFunction.class)) {
                newEnvironment.putF(parameter, (SMPLFunction)t);
            }
        }
        return function.getBody().visit(this, newEnvironment);
    }

    @Override
    public SMPLValue visitExpBitAnd(ExpBitAnd expBitAnd, SMPLContext arg) throws SMPLException {
        SMPLExp lhs = expBitAnd.getSmplExpA();
        SMPLExp rhs = expBitAnd.getSmplExpB();
        boolean b1 = true, b2 = true;

        try {
            Object result = arg.getP(lhs.toString());
            if (result.getClass().equals(Boolean.class)) {
                b1 = (boolean) result;
            }
        } catch (SMPLException e) {e.getCause();}

        try {
            Object result = arg.getP(rhs.toString());
            if (result.getClass().equals(Boolean.class)) {
                b2 = (boolean) result;
            }
        } catch (SMPLException e) {e.getCause();}

        System.out.print(b1 & b2);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpBitNot(ExpBitNot expBitNot, SMPLContext arg) throws SMPLException {
        int i = (int) expBitNot.getExp().visit(this, arg).getVal();

        System.out.println(~i);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpBitOr(ExpBitOr expBitOr, SMPLContext arg) throws SMPLException {
        SMPLExp lhs = expBitOr.getSmplExpA();
        SMPLExp rhs = expBitOr.getSmplExpB();
        boolean b1 = true, b2 = true;

        try {
            Object result = arg.getP(lhs.toString());
            if (result.getClass().equals(Boolean.class)) {
                b1 = (boolean) result;
            }
        } catch (SMPLException e) { e.getCause(); }

        try {
            Object result = arg.getP(rhs.toString());
            if (result.getClass().equals(Boolean.class)) {
                b2 = (boolean) result;
            }
        } catch (SMPLException e) {e.getCause();}

        System.out.print(b1 | b2);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpCmp(ExpCmp expCmp, SMPLContext arg) throws SMPLException {
        int valA = (int) expCmp.getSmplExpA().visit(this, arg).getVal();
        int valB = (int) expCmp.getSmplExpB().visit(this, arg).getVal();
        switch (expCmp.getCmp()) {
            case "=":
                if (valA == valB)
                    return new SMPLValue<Boolean>(true);
                else
                    return new SMPLValue<Boolean>(false);
            case ">":
                if (valA > valB)
                    return new SMPLValue<Boolean>(true);
                else
                    return new SMPLValue<Boolean>(false);
            case "<":
                if (valA < valB)
                    return new SMPLValue<Boolean>(true);
                else
                    return new SMPLValue<Boolean>(false);
            case "<=":
                if (valA <= valB)
                    return new SMPLValue<Boolean>(true);
                else
                    return new SMPLValue<Boolean>(false);
            case ">=":
                if (valA >= valB)
                    return new SMPLValue<Boolean>(true);
                else
                    return new SMPLValue<Boolean>(false);
            case "!=":
                if (valA != valB)
                    return new SMPLValue<Boolean>(true);
                else
                    return new SMPLValue<Boolean>(false);
        }
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpNot(ExpNot expNot, SMPLContext arg) throws SMPLException {
        SMPLExp smplExp = expNot.getSmplExp();
        boolean b = true;

        try {
            Object result = arg.getP(smplExp.toString());
            if (result.getClass().equals(Boolean.class)) {
                b = (boolean) result;
            }
        } catch (SMPLException e) {e.getCause();}

        System.out.println(!b);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpAnd(ExpAnd expAnd, SMPLContext arg) throws SMPLException {
        SMPLExp lhs = expAnd.getSmplExpA();
        SMPLExp rhs = expAnd.getSmplExpB();
        boolean b1 = true, b2 = true;

        try {
            Object result = lhs.visit(this, arg).getVal();
            if (result.getClass().equals(Boolean.class)) {
                b1 = (boolean) result;
            }
        } catch (SMPLException e) { e.getCause(); }

        try {
            Object result = rhs.visit(this, arg).getVal();
            if (result.getClass().equals(Boolean.class)) {
                b2 = (boolean) result;
            }
        } catch (SMPLException e) { e.getCause(); }

        System.out.print(b1 && b2);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitExpOr(ExpOr expOr, SMPLContext arg) throws SMPLException {
        SMPLExp lhs = expOr.getSmplExpA();
        SMPLExp rhs = expOr.getSmplExpB();
        boolean b1 = true, b2 = true;

        try {
            Object result = arg.getP(lhs.toString());
            if (result.getClass().equals(Boolean.class)) {
                b1 = (boolean) result;
            }
        } catch (SMPLException e) {e.getCause();}

        try {
            Object result = arg.getP(rhs.toString());
            if (result.getClass().equals(Boolean.class)) {
                b2 = (boolean) result;
            }
        } catch (SMPLException e) {e.getCause();}

        System.out.print(b1 || b2);
        return SMPLValue.NO_RESULT;
    }

    @Override
    public SMPLValue visitBinOrHexExp(BinHexExp binHexExp, SMPLContext arg) {
        String binOrHex = binHexExp.getBinOrHexExp();

        if (binOrHex.substring(0,2).equals("#b")) {
            String bin = binOrHex.substring(2, binOrHex.length());
            int n = Integer.parseInt(bin, 2);

            return new SMPLValue<Integer>(n);
        } else if (binOrHex.substring(0, 2).equals("#x")) {
            String hex = binOrHex.substring(2, binOrHex.length());
            int n = Integer.parseInt(hex, 16);

            return new SMPLValue<Integer>(n);
        }
        return SMPLValue.NO_RESULT;
    }
}