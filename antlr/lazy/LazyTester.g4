grammar LazyTester;

scenario: LB SCENARIO RB ;

SCENARIO      : SCENARIO_DEF (STEP_LOOP_DEF)*;

SCENARIO_DEF  : SCENARIO_NAME ':' STRING ;
STEP_LOOP_DEF : COMMA STEP_LOOP ':' INT ;

SCENARIO_NAME : '"scenarioName"' ;
STEP_LOOP     : '"stepLoop"' ;

STRING : '"'[a-zA-Z0-9]+'"';
LB       : '{' ;
RB       : '}' ;
COMMA    : ',' ;
INT      : [0-9]+ ;
WS       :  [ \t\r\n\f]+ -> skip;



