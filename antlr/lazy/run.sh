#!/usr/bin/env bash

export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"

rm -rf ./autogen/
mkdir -p ./autogen/target

java -jar /usr/local/lib/antlr-4.7.2-complete.jar LazyTester.g4

mv LazyTester*.java ./autogen
mv LazyTester*.tokens ./autogen
mv LazyTester*.interp ./autogen

cd ./autogen/
javac -d ./target/ LazyTester*.java

cd ./target/
echo -e "{ \n
            \"name\": \"scenario 1\" ,
            \"loop\": 13 ,
            \"steps\": [
                {
                  \"name\": \"step 1\" ,
                  \"loop\": 13,
                  \"url\": \"www.google.com\"
                }
             ]
         }" | java org.antlr.v4.gui.TestRig LazyTester scenario_file -gui
