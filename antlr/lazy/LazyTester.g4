grammar LazyTester;

scenario_file : '{' scenario '}' ;

scenario     : scenario_def (step_loop_def | steps_def)*;

scenario_def  : SCENARIO_NAME ':' STRING ;
step_loop_def : ',' STEP_LOOP ':' INT ;
steps_def     : ',' STEPS ':' '[' steps ']';
steps         : step (','  step )* ;
step          : '{' 'step' '}';

SCENARIO_NAME : '"scenarioName"' ;
STEP_LOOP     : '"stepLoop"' ;
STEPS         : '"steps"' ;
STRING        : '"' ~('"')+ '"';
INT           : [0-9]+ ;
WS            :  [ \t\r\n\f]+ -> skip;



