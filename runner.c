#include <stdio.h>
#include <string.h>

int main ()
{
   char command1[] = "java -cp \".:./JLex\" JLex.Main /Users/javon/Desktop/School/Language_Processors/project/smpl/src/smpl/lang/SMPLLexer";
   char command2[] = "java -jar java_cup.jar -parser SMPLParser /Users/javon/Desktop/School/Language_Processors/project/smpl/src/smpl/lang/SMPLParser.cup";
   char command3[] = "mv *.java smpl/src/smpl/lang";
   char command4[] = "find . -name \"*.java\" > sources.txt";
   char command5[] = "javac -cp \"java_cup.runtime.jar:.\" @sources.txt";
   char command6[] = "java -cp \"java_cup.runtime.jar:.:./smpl/src/\" smpl/sys/SMPLRepl smpl/src/smpl/smpl-tests.smpl";
    
    int pid,pid2,pid3,pid4;
        pid = fork();
    if(pid == 0){    
        system(command1);
        printf("Lexer ran successfully\n");

        pid2 = fork();

        if(pid2 == 0)
        {
            system(command2);
            printf("Parser ran successfully\n");
        }
        else
        {
            wait(NULL);
            pid3 = fork();
            if(pid3 == 0)
            {
                system(command3);
                printf("Moved SMPLParser.java and sym.java\n");
            }
            else
            {
                wait(NULL);
                pid4 = fork();
                if(pid4 == 0)
                {
                    system(command4);
                    printf("Generated location to java files\n");
                }
                else
                {
                    wait(NULL);
                    system(command5);
                    printf("Compiled all classes successfully\n");
                }
                
            }
            
        }
    }else if(pid > 0){
        /*parent*/
        wait(NULL);
        system(command6);
        printf("Program ran successfully\n");
    }
   
   return(0);
} 
