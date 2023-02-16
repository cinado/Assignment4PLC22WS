grammar BigCalcProg;

expressionStatement
        : (statement ';') + EOF
        ;

statement
        : expression                            # statementExpr
        | assignment                            # statementAssign
        ;

expression  
        : expression op=('*' | '/') expression  # mulDiv
        | expression op=('+' | '-') expression  # addSub
        | Number                                # num
        | Variable                              # variable
        | '(' expression ')'                    # parentheses
        ;

assignment
        : Variable '=' expression
        ;

Variable
        : [a-zA-Z] Digit*
        ;

Number  
        : Digit* '.' Digit+
        | Digit+
        ;

Digit   
        : [0-9]
        ;

WS      : [ \t\r\n\u000C]+ -> skip  
        ;

COMMENT
        :   '/*' .*? '*/' -> skip
        ;

LINE_COMMENT
        : '//' ~[\r\n]* -> skip 
        ;


