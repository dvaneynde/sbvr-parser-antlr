package eu.eurocontrol.sbvr;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.w3c.dom.Document;

/**
 * TODO
 */
public class App {

    public static void main(String[] args) throws Exception {
        //parseSbvrRuleFromStdIn();

        CharStream input = CharStreams.fromString("It is prohibited that Runway has assigned lengthStrip.uom value and lengthStrip.uom value equal-to ('FT','M')"); // create a lexer that feeds off of input CharStream
        AixmSbvrLexer lexer = new AixmSbvrLexer(input); // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); // create a parser that feeds off the tokens buffer
        AixmSbvrVisitorImpl visitor = new AixmSbvrVisitorImpl();
        AixmSbvrParser parser = new AixmSbvrParser(tokens);
        ParseTree tree = parser.statement(); // begin parsing at init rule
        visitor.visit(tree);
        System.out.println("Nr conditions: "+ visitor.getNrConditions());
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

    // https://www.baeldung.com/java-xerces-dom-parsing
    public static void parseXml(String fileName) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));
        doc.getDocumentElement().normalize();
    }
}
