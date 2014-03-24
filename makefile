JAVA := java
JAVAC := javac
JAVACC:= javacc
SUPPORT:=
JFLAGS:=

default	:	compiler

compiler :      check.jar compile
	chmod u+x compile

check.jar	:
	-@/bin/rm mc.mf
	echo "Main-Class: main.Check\nClass-Path: http://www.cs.fit.edu/~ryan/cse4251/support.jar" > mc.mf
	$(JAVACC) -OUTPUT_DIRECTORY=parser parser/checker.jj 
	$(JAVAC) -cp .:$(SUPPORT) parser/*.java main/*.java global/*.java 
	jar cvmf mc.mf $@ parser/*.class main/*.class global/*.class 
	-@/bin/rm mc.mf
	make clean
clean	:
	-/bin/rm *~ */*~
	-/bin/rm */*.class
	-/bin/rm parser/*.java
