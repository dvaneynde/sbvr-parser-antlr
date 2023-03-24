grammar AixmSbvr;

statement: (must | mustNot) conditions EOF;
must : 'It is obligatory that';
mustNot : 'It is prohibited that';
conditions : condition (( 'and' | 'or' ) condition )*;
condition : 'not'? elementaryCond ;
elementaryCond :  quantifiedCond
                | hasDescendantCond
                | isDescendantOfCond
                | refToExactlyOneCond
                | isReferencedByAtLeastOneCond
                | isPropertyOfCondition
                | hasNameValueSimpleTestCondition       // HasNounConceptValueSimpleTest
                | hasNotAssignedNameValueCondition      // HasOrNotAssignedNounConceptValueCondition
                | valueSimpleTestCondition
                ;

quantifiedCond : quantification INT name;       // TODO MultipleInteger !
quantification : 'at-least' | 'at-most' | 'exactly' | 'more-than' | 'less-than';
hasDescendantCond : name 'has-descendant' name value? ;
refToExactlyOneCond : name 'references-to' 'exactly-one' name;
isReferencedByAtLeastOneCond : name 'is-referenced-by' 'at-least-one' name;
isDescendantOfCond : name 'is-descendant-of' name;
isPropertyOfCondition : name 'is-property-of' name;
hasNameValueSimpleTestCondition : name 'has' name (value simpleTest?)?;
hasNotAssignedNameValueCondition : name 'has' 'not'? 'assigned' name 'value';
valueSimpleTestCondition : name 'value' simpleTest;

name : ID;
value : 'value';        // TODO lexer?
simpleTest : booleanOp (singleValue | multipleValues | name 'value'? );
booleanOp :   'equal-to' | 'not-equal-to' | 'other' 'than' | 'higher-than' | 'less-than'
                 | 'starting-with' | 'equal-to-one-of' | 'not-equal-to-one-of';
singleValue : STRING;   // TODO quotes are optional
multipleValues : '(' singleValue (',' singleValue)* ')'; 


// Lexical rules
ID          :   LETTER (LETTER|DIGIT)*;
fragment LETTER      :   [a-zA-Z\u0080-\u00FF_.] ;
fragment DIGIT : [0-9] ;

STRING : '\'' (ESC | ~["\\])* '\'' ;
fragment ESC : '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ; fragment HEX : [0-9a-fA-F] ;
INT : '0' | [1-9] [0-9]* ; // no leading zeros
WS : [ \t\r\n]+ -> skip ; // Define whitespace rule, toss it out
