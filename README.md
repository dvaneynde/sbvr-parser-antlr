# AIXM SBVR Parser and Validator

## SBVR

https://ext.eurocontrol.int/aixm_confluence/display/AIXMBR/Rules+definition+using+SBVR

## ANTLR4

ANTLR4 getting started https://github.com/antlr/antlr4/blob/master/doc/getting-started.md

To avoid problem where only a part is processed and a later invalid part is just skipped:
https://stackoverflow.com/questions/2579118/why-does-antlr-not-parse-the-entire-input

## Using grun
```bash
mvn package
cd target/classes
grun eu.eurocontrol.sbvr.AixmSbvr statement -gui
# type:
# It is obligatory that at-least 3 abc
# Ctrl-D
```

## Build, Test, Run

```bash
# To Build & Test:
mvn antlr4:help -Ddetail=true -Dgoal=antlr4
mvn antlr4:antlr4
mvn package

# To Run:
java -jar target/sbvr-parser-1.0-SNAPSHOT-jar-with-dependencies.jar App

```