
ANTLR4 getting started https://github.com/antlr/antlr4/blob/master/doc/getting-started.md


```bash
# To Build:
mvn antlr4:help -Ddetail=true -Dgoal=antlr4
mvn antlr4:antlr4
mvn package
java -jar target/sbvr-parser-1.0-SNAPSHOT-jar-with-dependencies.jar App

# To Test:
cd target/classes
grun eu.eurocontrol.sbvr.AixmSbvr statement -gui
# type:
# It is obligatory that at-least 3 abc
# Ctrl-D
grun eu.eurocontrol.sbvr.ArrayInit init -gui
# type "{ init 3 }" and ctrl-d; or give it a filename


```