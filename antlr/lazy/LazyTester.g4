grammar LazyTester;

import Json;

@header {
package com.lloyds.antlr.lazytester.autogen;
}

scenario_file  : import_section (steps_section)? scenarios_section ;

import_section    : IMPORT* ;
steps_section     : STEPS ':' '[' (step_def)* ']' ';' ;
scenarios_section : (SCENARIOS ':' '[' (scenario_def)*  ']')? ;

step_def       : '{' (step_component)* '}' ';' ;
step_component : (step_name | extend_def | loop_def | url_def | operation | params_def | headers_def | body_def | assertions_def) ;
scenario_def   : '{' (scenario_component)* '}' ';' ;
scenario_component: (scenario_name | loop_def | steps_section | ignore_step_failures_def)  ;
scenario_name  : NAME ':' (STRING | QSTRING) ';' ;
extend_def     : EXTEND ':' (STRING | QSTRING) ';' ;
params_def     : PARAMS ':' '{' params '}' ';' ;
headers_def    : HEADERS ':' '{' params '}' ';' ;
params         : (STRING ':' (STRING | QSTRING) ';')*;
body_def       : BODY ':' json ';' ;
step_name      : NAME ':' (STRING | QSTRING) ';' ;
url_def        : URL ':' (STRING | QSTRING) ';' ;
operation      : OPERATION ':' HTTP_OPS ';' ;
assertions_def : ASSERTIONS ':' '{' assertions '}' ';' ;
assertions     : STATUS ':' INT ';' ;
loop_def       : LOOP ':' INT ';' ;
ignore_step_failures_def : 'ignoreStepFailures' ':' ('true' | 'false') ';' ;

STEPS         : 'steps' ;
SCENARIOS     : 'scenarios' ;
NAME          : 'name' ;
LOOP          : 'loop' ;
URL           : 'url' ;
OPERATION     : 'operation' ;
HTTP_OPS      : 'GET' | 'POST' | 'PUT' | 'DELETE' ;
ASSERTIONS    : 'assertions' ;
STATUS        : 'status' ;
PARAMS        : 'params' ;
HEADERS       : 'headers' ;
BODY          : 'body' ;
EXTEND        : 'parent' ;
IMPORT        : 'import' ~[;]+ ';';
INT           : [0-9]+ ;
STRING        : [a-zA-Z0-9._/\-]+ ;
QSTRING       : '"' ~["]+ '"' ;
WS            :  [ \t\r\n\f]+ -> skip ;



