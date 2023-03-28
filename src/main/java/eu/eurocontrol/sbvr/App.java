package eu.eurocontrol.sbvr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

/**
 * For now, just testing multiple things.
 */
public class App {

    public static void main(String[] args) throws Exception {
        if (false)
            parseSbvrRuleFromStdIn();
        else {
            // String rule = "It is prohibited that Runway has assigned lengthStrip.uom
            // value";
            String rule = "It is prohibited that Runway has assigned lengthStrip.uom value or lengthStrip.uom value equal-to ('FT','M')";
            // String rule = "It is obligatory that Runway has assigned lengthStrip.uom
            // value and lengthStrip.uom value equal-to ('FT','M')";
            String javaProgram = generateValidatorFromRule(rule);
            System.out.println("Program:\n----------\n" + javaProgram + "----------\n");
        }
    }

    public static String generateValidatorFromRule(String rule) {
        CharStream input = CharStreams.fromString(rule); // create a lexer that feeds off of input CharStream
        AixmSbvrLexer lexer = new AixmSbvrLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        AixmSbvrVisitorImpl visitor = new AixmSbvrVisitorImpl();
        AixmSbvrParser parser = new AixmSbvrParser(tokens);
        ParseTree tree = parser.statement(); // begin parsing at init rule
        String javaProgram = visitor.visit(tree);
        // System.out.println("Nr conditions: "+ visitor.getNrConditions());
        return javaProgram;
    }

    public static void parseSbvrRuleFromStdIn() throws Exception {
        // create a CharStream that reads from standard input
        CharStream input = CharStreams.fromStream(System.in); // create a lexer that feeds off of input CharStream
        AixmSbvrLexer lexer = new AixmSbvrLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        AixmSbvrParser parser = new AixmSbvrParser(tokens);
        ParseTree tree = parser.statement(); // begin parsing at init rule
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree }}
    }
}
