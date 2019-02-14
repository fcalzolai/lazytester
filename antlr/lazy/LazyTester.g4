grammar LazyTester;

@header {
package antlr.lazytester.autogen;
}

scenario_file  : IMPORT* ('{' scenario '}' | scenario_array) ;
scenario_array : '[' '{' scenario '}' (',' '{' scenario '}')* ']' ;
scenario       : (scenario_name | loop_def | steps_def | ignore_step_failures_def) (',' scenario)? ;

scenario_name  : NAME ':' STRING ;
steps_def      : STEPS ':' '[' steps ']' ;
steps          : '{' step_def '}' (','  steps )? ;
step_def       : (step_name | extend_def | loop_def | url_def | operation | params_def | headers_def | body_def | assertions_def) (',' step_def)? ;
extend_def     : EXTEND ':' STRING ;
params_def     : PARAMS ':' '{' params '}' ;
params         : STRING ':' STRING (',' STRING ':' STRING)*;
headers_def    : HEADERS ':' STRING ;
body_def       : BODY ':' STRING ;
step_name      : NAME ':' STRING ;
url_def        : URL ':' STRING ;
operation      : OPERATION ':' HTTP_OPS ;
assertions_def : ASSERTIONS ':' '{' assertions '}' ;
assertions     : STATUS ':' INT ;
loop_def       : LOOP ':' INT ;
ignore_step_failures_def : '"ignoreStepFailures"' ':' ('"true"' | '"false"');

NAME          : '"name"' ;
LOOP          : '"loop"' ;
STEPS         : '"steps"' ;
URL           : '"url"' ;
OPERATION     : '"operation"' ;
HTTP_OPS      : '"GET"' | '"POST"' | '"PUT"' | '"DELETE"' ;
ASSERTIONS    : '"assertions"' ;
STATUS        : '"status"' ;
PARAMS        : '"params"' ;
HEADERS       : '"headers"' ;
BODY          : '"body"' ;
EXTEND        : '"parent"' ;
STRING        : '"' ~["]+ '"';
IMPORT        : 'import' ~[;]+ ';';
INT           : [0-9]+ ;
WS            :  [ \t\r\n\f]+ -> skip ;



