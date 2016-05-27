package smpl.sys;

import java_cup.runtime.*;
import java.io.*;
import smpl.values.*;
import smpl.lang.*;

public class SMPLRepl {

    public static final String PROMPT = ">";

    public static SMPLEvaluator evaluator;

    public static void main(String args[]) {
    	if (args.length == 0)
			repl(System.in, SMPLContext.makeGlobalEnv());
		else {
			try {
				parseEvalShow(new FileReader(new File(args[0])), SMPLContext.makeGlobalEnv());
			} catch(FileNotFoundException e) {
				System.out.println("Could not find file " + args[0]);
			}
		}
    }

    public static void repl(InputStream is, SMPLContext env){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while (true) {
		    	System.out.print(PROMPT);
                StringBuffer input = new StringBuffer();
                String line = reader.readLine();
                while (line != null) {
                    input.append("\n");
                    input.append(line);
                    line = reader.readLine();
                }
                StringReader r = new StringReader(new String(input));
                parseEvalShow(r, env);
            }
        } catch (IOException ex) {
            System.out.println("Bye bye!");
        }
    }

    public static void parseEvalShow(Reader r, SMPLContext env) {
		SMPLParser parser;
		SMPLProgram program = null;
		SMPLEvaluator interp = new SMPLEvaluator();
	    try {
			parser = new SMPLParser(new SMPLLexer(r));
			program = (SMPLProgram) parser.parse().value;
	    } catch (Exception e) {
			System.out.println("Parse Error: " + e.getMessage());
	    }

	    if (program != null)
			try {
			    SMPLValue result;
			    result = program.visit(interp, env);
			    if(result.getVal() != null && !result.getVal().equals(SMPLValue.NO_RESULT.getVal()))
			    	System.out.println(""+result.getVal());
			} catch (Exception e) {
			    System.out.println(e.getMessage());
			} 
    }
}
