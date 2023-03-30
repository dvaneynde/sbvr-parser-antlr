SbvrRule = ItIsProhibitedOrObligatory Whitespace ConditionsSeparated Whitespace?
ConditionsSeparated = Conditions (Whitespace SplitterCondtion Whitespace Conditions)*
Conditions = (Not Whitespace)? ElementaryCondition
ElementaryCondition = QuantifiedCondition 
                    / HasDescendantCondition 
                    / IsDescendantOfCondition 
                    / ReferencesToExactlyOneCondition 
                    / IsReferencedByAtLeastOneCondition 
                    / IsPropertyOfCondition 
                    / HasNounConceptValueSimpleTest 
                    / HasOrNotAssignedNounConceptValueCondition 
                    / ValueSimpleTestCondition
QuantifiedCondition = Quantification Whitespace MultipleInteger Whitespace NounConcept
HasDescendantCondition = NounConcept Whitespace HasDescendant Whitespace NounConcept (Whitespace Value)?
ReferencesToExactlyOneCondition = NounConcept Whitespace ReferencesTo Whitespace ExactlyOne Whitespace NounConcept
IsReferencedByAtLeastOneCondition = NounConcept Whitespace IsReferencedBy Whitespace AtLeastOne Whitespace NounConcept
IsDescendantOfCondition = NounConcept Whitespace IsDescendantOf Whitespace NounConcept
IsPropertyOfCondition = NounConcept Whitespace IsPropertyOf Whitespace NounConcept
HasNounConceptValueSimpleTest = NounConceptHas NounConcept (Whitespace Value (Whitespace SimpleTest)?)?
HasOrNotAssignedNounConceptValueCondition = NounConceptHas AssignedOrNot Whitespace NounConcept Whitespace Value
NounConceptHas = NounConcept Whitespace Has Whitespace
ValueSimpleTestCondition = NounConcept Whitespace Value Whitespace SimpleTest

SimpleTest = BooleanOp Whitespace (MultipleOrSingleValues / (NounConcept (Whitespace Value)?))

MultipleOrSingleValues = (SingleValue / MultipleValues)
MultipleValues = "(" SingleValue ("," SingleValue)+ ")"
SingleValue = (SingleQuote LetterOrDigitsGroup SingleQuote) / (DoubleQuote LetterOrDigitsGroup DoubleQuote)

NounConcept = NounConceptChars+

Has = "has"
HasDescendant = "has-descendant"
IsDescendantOf = "is-descendant-of"
IsPropertyOf = "is-property-of"
ReferencesTo = "references-to"
IsReferencedBy = "is-referenced-by"

ItIsProhibitedOrObligatory = ("It is prohibited that" / "It is obligatory that")
Quantification = ("at-least" / "at-most" / "exactly" / "more-than" / "less-than")
BooleanOp = ("other-than" / "higher-or-equal-to" / "higher-than" / "lower-or-equal-to" / "lower-than" / "starting-with" / "equal-to-one-of" / "not-equal-to-one-of" / "not-equal-to" / "equal-to" / "contains-substring" / "intersects" / "longer-than")
AtLeastOne = "at-least-one"
ExactlyOne = "exactly-one"
AssignedOrNot = (("not ")? "assigned")
Value = "value"
Not = "not" 
SplitterCondtion =  ("and" / "or") 

MultipleInteger = Integer+
LetterOrDigitsGroup = (Char / Integer)+
NounConceptChars = (Char / [.:@_${}])

SingleQuote = [']
DoubleQuote = ["]
Char = [a-zA-Z]
Integer = [0-9]
StrictWhitespace = [ ]
Whitespace = (StrictWhitespace / [\n\t\r])+