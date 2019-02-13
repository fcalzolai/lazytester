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
#echo -e "import test1; {\"name\": \"get google\"} " | java org.antlr.v4.gui.TestRig antlr.lazytester.autogen.LazyTester scenario_file -gui
echo -e "
{
\"steps\": [
    {
      \"name\": \"get google\",
      \"url\": \"https://www.google.com\",
      \"operation\": \"GET\",
      \"params\" : {
        \"key1\" : \"value1\",
        \"key2\" : \"value2\"
      },
      \"assertions\": {
      \"status\": 200
      }
    }
  ]
}" | java org.antlr.v4.gui.TestRig antlr.lazytester.autogen.LazyTester scenario_file -gui

#echo -e "import path1; \n
#         import path2; \n
#          [{  \n
#            \"name\": \"scenario 1\" ,
#            \"ignoreStepFailures\": \"false\",
#            \"loop\" : 13 ,
#            \"steps\": [
#                {
#                  \"name\": \"step 1\" ,
#                  \"operation\": \"GET\",
#                  \"loop\": 13,
#                  \"url\": \"www.google.com\",
#                  \"params\": \"**** params ****\",
#                  \"headers\": \"**** headers ****\",
#                  \"body\": \"**** body ****\",
#                  \"assertions\": {
#                     \"status\": 200
#                    }
#                }
#             ]
#         },
#         {  \n
#            \"name\": \"scenario 1\" ,
#            \"ignoreStepFailures\": \"false\",
#            \"loop\" : 13 ,
#            \"steps\": [
#                {
#                  \"name\": \"step 1\" ,
#                  \"operation\": \"GET\",
#                  \"loop\": 13,
#                  \"url\": \"www.google.com\",
#                  \"params\": \"**** params ****\",
#                  \"headers\": \"**** headers ****\",
#                  \"body\": \"**** body ****\",
#                  \"assertions\": {
#                     \"status\": 200
#                    }
#                }
#             ]
#         }]
#         " | java org.antlr.v4.gui.TestRig antlr.lazytester.autogen.LazyTester scenario_file -gui