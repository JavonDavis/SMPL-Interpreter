JLEX = ./JLex
BASE_PATH = /Users/javon/Desktop/School/Language_Processors/project/smpl/
SRC_PATH = $(BASE_PATH)src/
MAIN = /JLex.Main
LANG_PATH = $(SRC_PATH)smpl/lang/
SYS_PATH = $(SRC_PATH)smpl/sys/
VALUES_PATH = $(SRC_PATH)smpl/values/
CUP = /Users/javon/Desktop/School/Language_Processors/project/java_cup
CUPFLAGS = -parser
JAVAC = javac
JAVA = java
CLASSPATH = /Users/javon/Desktop/School/Language_Processors/project/java_cup.runtime.jar:$(SRC_PATH)

all: build run
build: lex parser compile


lex: $(LANG_PATH)SMPLLexer
	$(JAVA) -classpath $(JLEX) $(MAIN) $(LANG_PATH)SMPLLexer

parser: $(LANG_PATH)SMPLParser.cup 
	$(CUP) $(CUPFLAGS) SMPLParser $(LANG_PATH)SMPLParser.cup
	mv SMPLParser.java sym.java $(LANG_PATH)

compile: $(LANG_PATH)sym.java $(LANG_PATH)SMPLParser.java 
	$(JAVAC) -classpath $(CLASSPATH) $(LANG_PATH)*.java $(SYS_PATH)*.java $(VALUES_PATH)*.java 

run: $(SYS_PATH)SMPLRepl.class
	$(JAVA) -classpath $(CLASSPATH) smpl.sys.SMPLRepl 

clean:
	rm $(SRC_PATH)smpl/*/*.class $(LANG_PATH)SMPLParser.java $(LANG_PATH)SMPLLexer.java $(LANG_PATH)sym.java
