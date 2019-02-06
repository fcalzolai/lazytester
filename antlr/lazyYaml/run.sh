#!/usr/bin/env bash

export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"

rm -rf ./autogen/
mkdir -p ./autogen/target

java -jar /usr/local/lib/antlr-4.7.2-complete.jar LazyTester.g4

rm -rf ../../src/main/java/antlr/lazytester/autogen
mkdir -p ../../src/main/java/antlr/lazytester/autogen
cp LazyTester*.java ../../src/main/java/antlr/lazytester/autogen

mv LazyTester*.java ./autogen
mv LazyTester*.tokens ./autogen
mv LazyTester*.interp ./autogen

cd ./autogen/
javac -d ./target/ LazyTester*.java

cd ./target/
#echo -e "import   test1/test1; import test2;}" | java org.antlr.v4.gui.TestRig antlr.lazytester.autogen.LazyTester import_def -gui

echo -e "import: path0 \n
           - path1 \n
           - path2 \n
           " | java org.antlr.v4.gui.TestRig antlr.lazytester.autogen.LazyTester scenario_file -gui