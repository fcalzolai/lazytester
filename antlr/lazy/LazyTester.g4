grammar LazyTester;

@header {
package antlr.lazytester.autogen;
}

scenario_file : '{' scenario '}' ;

scenario     : (scenario_name | loop_def | steps_def) (',' scenario)*;

scenario_name  : NAME ':' STRING ;
steps_def      : STEPS ':' '[' steps ']';
steps          : '{' step_def '}' (','  steps )* ;
step_def       : (step_name | loop_def | url_def | operation | assertions_def) (',' step_def)* ;
step_name      : NAME ':' STRING ;
url_def        : URL ':' STRING ;
operation      : OPERATION ':' HTTP_OPS ;
assertions_def : ASSERTIONS ':' '{' assertions '}' ;
assertions     : STATUS ':' INT;
loop_def       : LOOP ':' INT ;

NAME          : '"name"' ;
LOOP          : '"loop"' ;
STEPS         : '"steps"' ;
URL           : '"url"' ;
OPERATION     : '"operation"' ;
HTTP_OPS      : '"GET"' | '"POST"' | '"PUT"' | '"DELETE"' ;
ASSERTIONS    : '"assertions"' ;
STATUS        : '"status"' ;
STRING        : '"' ~('"')+ '"';
INT           : [0-9]+ ;
WS            :  [ \t\r\n\f]+ -> skip;



