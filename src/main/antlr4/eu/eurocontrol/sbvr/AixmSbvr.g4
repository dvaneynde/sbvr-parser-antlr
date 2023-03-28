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

valueKeyword : 'value';       
notKeyword : 'not';
simpleTest : booleanOp (singleValue | multipleValues | name 'value'? );

booleanOp :   'equal-to'        
            | 'not-equal-to'    
            | 'other' 'than'    
            | 'higher-than'     
            | 'less-than'       
            | 'starting-with'   
            | 'equal-to-one-of' 
            | 'not-equal-to-one-of' 
            ;

singleValue : STRING;   // TODO quotes are optional
multipleValues : '(' singleValue (',' singleValue)* ')'; 


// Lexical rules
ID          :   LETTER (LETTER|DIGIT)*;
STRING : '\'' LETTER* '\'' ;    // TODO should also be able to contain ','
fragment LETTER      :   [a-zA-Z\u0080-\u00FF_.] ;
fragment DIGIT : [0-9] ;
INT : '0' | [1-9] [0-9]* ; // no leading zeros
WS : [ \t\r\n]+ -> skip ; // Define whitespace rule, toss it out
