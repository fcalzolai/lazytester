#!/usr/bin/env bash

export CLASSPATH=".:/usr/local/lib/antlr-4.7.2-complete.jar:$CLASSPATH"

rm -rf ./autogen/
mkdir -p ./autogen/target

java -jar /usr/local/lib/antlr-4.7.2-complete.jar LazyTester.g4

rm -rf ../../src/main/java/com/lloyds/antlr/lazytester/autogen
mkdir -p ../../src/main/java/com/lloyds/antlr/lazytester/autogen
cp LazyTester*.java ../../src/main/java/com/lloyds/antlr/lazytester/autogen

mv LazyTester*.java ./autogen
mv LazyTester*.tokens ./autogen
mv LazyTester*.interp ./autogen

cd ./autogen/
javac -d ./target/ LazyTester*.java

cd ./target/

echo -e "import googleSteps;

steps: [
  {
    name: \"step1 \";
    url: \"http://www.google.com\";
    operation: GET;
    loop: 2;
    params: {
      q: lbg;
      aq: f;
    };
    headers: {
      user-agent: \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Safari/537.36\";
      accept-encoding: \"gzip, deflate, br\";
    };
    body: [];
    body: { };
    assertions:{
      status:200;
    };
  };
]
" | java org.antlr.v4.gui.TestRig com.lloyds.antlr.lazytester.autogen.LazyTester scenario_file -gui
