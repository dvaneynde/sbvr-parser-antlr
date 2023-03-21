package eu.eurocontrol.sbvr;

import org.antlr.v4.runtime.*; 
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws Exception {
        // create a CharStream that reads from standard input
        CharStream input = CharStreams.fromStream(System.in); // create a lexer that feeds off of input CharStream
        AixmSbvrLexer lexer = new AixmSbvrLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        AixmSbvrParser parser = new AixmSbvrParser(tokens);
        ParseTree tree = parser.statement(); // begin parsing at init rule
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree }}
    }
}
