#!/usr/bin/env bash

export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"

rm -rf ./autogen/
mkdir -p ./autogen/target

java -jar /usr/local/lib/antlr-4.7.2-complete.jar Json.g4

mv Json*.java ./autogen
mv Json*.tokens ./autogen
mv Json*.interp ./autogen

cd ./autogen/
javac -d ./target/ Json*.java

cd ./target/

echo -e "
{
\"steps\": [
    {
      \"name\": \"get google\",
      \"url\": \"https://www.google.com\",
      \"operation\": \"GET\",
      \"params\" : {
        \"q\" : \"lbg\",
        \"aq\" : \"f\"
      },
      \"headers\" : {
        \"user-agent\" : \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\",
        \"accept-encoding\" : \"gzip, deflate, br\"
      },
      \"body\": {\"test\" : \"test\"},
      \"assertions\": {
      \"status\": 200
      }
    }
  ]
}" | java org.antlr.v4.gui.TestRig antlr.lazytester.autogen.Json json -gui
