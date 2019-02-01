TO INSTALL
$ cd /usr/local/lib
$ sudo curl -O https://www.antlr.org/download/antlr-4.7.2-complete.jar
$ export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"
$ alias antlr4='java -jar /usr/local/lib/antlr-4.7.2-complete.jar'
$ alias grun='java org.antlr.v4.gui.TestRig'

TO RUN
$ antlr4 Expr.g4
$ javac Expr*.java
$ grun Expr prog -gui
100+2*34
^D