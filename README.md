
ANTLR4 getting started https://github.com/antlr/antlr4/blob/master/doc/getting-started.md


```bash
grun eu.eurocontrol.sbvr.ArrayInit init -gui
# type "{ init 3 }" and ctrl-d; or give it a filename

mvn antlr4:help -Ddetail=true -Dgoal=antlr4
mvn antlr4:antlr4
mvn package
java -jar target/sbvr-parser-1.0-SNAPSHOT-jar-with-dependencies.jar App

```