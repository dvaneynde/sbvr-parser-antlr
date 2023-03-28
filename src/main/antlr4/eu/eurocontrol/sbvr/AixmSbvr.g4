grammar AixmSbvr;

statement: (must | mustNot) conditions EOF;
must : 'It is obligatory that';
mustNot : 'It is prohibited that';
conditions : condition (andOrBooleanOp condition )*;
andOrBooleanOp : 'and' | 'or';
condition : 'not'? elementaryCond ;
elementaryCond :  quantifiedCond
                | hasDescendantCond
                | isDescendantOfCond
                | refToExactlyOneCond
                | isReferencedByAtLeastOneCond
                | isPropertyOfCond
                | hasNameValueSimpleTestCond   
                | hasOrNotAssignedNameValueCond 
                | valueSimpleTestCond
                ;

quantifiedCond : quantification INT name;       // TODO MultipleInteger !
quantification : 'at-least' | 'at-most' | 'exactly' | 'more-than' | 'less-than';
hasDescendantCond : name 'has-descendant' name valueKeyword? ;
refToExactlyOneCond : name 'references-to' 'exactly-one' name;
isReferencedByAtLeastOneCond : name 'is-referenced-by' 'at-least-one' name;
isDescendantOfCond : name 'is-descendant-of' name;
isPropertyOfCond : name 'is-property-of' name;
hasNameValueSimpleTestCond : name 'has' name (valueKeyword simpleTest?)?;
hasOrNotAssignedNameValueCond : name 'has' notKeyword? 'assigned' val valueKeyword;
valueSimpleTestCond : name 'value' simpleTest;

name : ID;
val : ID;   // TODO quotes, or not, or optional?

valueKeyword : 'value';        // TODO lexer?
notKeyword : 'not';
simpleTest : booleanOp (singleValue | multipleValues | name 'value'? );

booleanOp :   'equal-to'        //# EqualTo
            | 'not-equal-to'    //# NotEqualTo
            | 'other' 'than'    //# OtherThan
            | 'higher-than'     //# HigherThan
            | 'less-than'       //# LessThan
            | 'starting-with'   //# StartingWith
            | 'equal-to-one-of' //# OneOf
            | 'not-equal-to-one-of' //# NotOneOf
            ;

singleValue : STRING;   // TODO quotes are optional
multipleValues : '(' singleValue (',' singleValue)* ')'; 


// Lexical rules
ID          :   LETTER (LETTER|DIGIT|'.')*;
fragment LETTER      :   [a-zA-Z\u0080-\u00FF_.] ;
fragment DIGIT : [0-9] ;

STRING : '\'' (ESC | ~["\\])* '\'' ;
fragment ESC : '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ; fragment HEX : [0-9a-fA-F] ;
INT : '0' | [1-9] [0-9]* ; // no leading zeros
WS : [ \t\r\n]+ -> skip ; // Define whitespace rule, toss it out
