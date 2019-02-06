grammar LazyTester;

@header {
package antlr.lazytester.autogen;
}

scenario_file  : (IMPORT paths)? (step)*;
paths          : ELEM* ;
//scenario_array : '[' '{' scenario '}' (',' '{' scenario '}')* ']' ;
//scenario       : (scenario_name | loop_def | steps_def | ignore_step_failures_def) (',' scenario)* ;
//
//scenario_name  : NAME ':' STRING ;
//steps_def      : STEPS ':' '[' steps ']' ;
steps          : step_def '}' (','  steps )* ;
step_def       : (step_name) ; // | extend_def | loop_def | url_def | operation | param_def | headers_def | body_def | assertions_def) ;
//extend_def     : EXTEND ':' STRING ;
//param_def      : PARAMS ':' STRING ;
//headers_def    : HEADERS ':' STRING ;
//body_def       : BODY ':' STRING ;
step_name      : NAME ':' STRING ;
//url_def        : URL ':' STRING ;
//operation      : OPERATION ':' HTTP_OPS ;
//assertions_def : ASSERTIONS ':' '{' assertions '}' ;
//assertions     : STATUS ':' INT ;
//loop_def       : LOOP ':' INT ;
//ignore_step_failures_def : '"ignoreStepFailures"' ':' ('"true"' | '"false"');

NAME          : 'name' ;
//LOOP          : '"loop"' ;
STEP         : 'step' ;
//URL           : '"url"' ;
//OPERATION     : '"operation"' ;
//HTTP_OPS      : '"GET"' | '"POST"' | '"PUT"' | '"DELETE"' ;
//ASSERTIONS    : '"assertions"' ;
//STATUS        : '"status"' ;
//PARAMS        : '"params"' ;
//HEADERS       : '"headers"' ;
//BODY          : '"body"' ;
//EXTEND        : '"extend"' ;
//INT           : [0-9]+ ;
IMPORT        : 'import' ':' STRING NEWLINE;
ELEM          : '-' STRING NEWLINE;
STRING     : (NO_NEWLINE)+;

fragment NEWLINE    : [\r\n\f] ;
fragment NO_NEWLINE : ~[\r\n\f] ;
WS            :  [ \t\r\n\f]+ -> skip ;



