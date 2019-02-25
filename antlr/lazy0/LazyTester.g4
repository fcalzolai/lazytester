grammar LazyTester;

import Json;

@header {
package antlr.lazytester.autogen;
}

scenario_file  : import_section steps_section scenarios_section ;

import_section    : IMPORT* ;
steps_section     : ('{' STEPS ':' '[' step_array ']' '}')? ;
scenarios_section : ('{' scenario '}' | '[' '{' scenario '}' (',' '{' scenario '}')* ']')? ;

scenario       : (scenario_name | loop_def | steps_section | ignore_step_failures_def) (',' scenario)? ;

scenario_name  : NAME ':' STRING ;
step_array     : step_def (','  step_array)? ;
step_def       : '{' step_component '}' ;
step_component : (step_name | extend_def | loop_def | url_def | operation | params_def | headers_def | body_def | assertions_def) (',' step_component)? ;
extend_def     : EXTEND ':' STRING ;
params_def     : PARAMS ':' '{' params '}' ;
headers_def    : HEADERS ':' '{' params '}' ;
params         : STRING ':' STRING (',' STRING ':' STRING)*;
body_def       : BODY ':' json ;
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
STRING        : '"' (.)+? '"';
IMPORT        : 'import' ~[;]+ ';';
INT           : [0-9]+ ;
WS            :  [ \t\r\n\f]+ -> skip ;



