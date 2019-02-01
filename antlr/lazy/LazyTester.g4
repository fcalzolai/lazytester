grammar LazyTester;

scenario_file : '{' scenario '}' ;

scenario     : scenario_def (step_loop_def)*;

scenario_def  : SCENARIO_NAME ':' STRING ;
step_loop_def : ',' STEP_LOOP ':' INT ;

SCENARIO_NAME : '"scenarioName"' ;
STEP_LOOP     : '"stepLoop"' ;
STRING : '"' ~('"')+ '"';
INT      : [0-9]+ ;
WS       :  [ \t\r\n\f]+ -> skip;



