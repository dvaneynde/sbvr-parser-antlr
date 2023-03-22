package eu.eurocontrol.sbvr;

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;

import org.junit.Assert;
import org.junit.Test;

public class ValidateSomeRulesTest
{
    @Test
    public void testAFewRules() {
        testRule("It is obligatory that someField has not assigned ABC value", false);
        testRule("It is prohibited that Runway has assigned lengthStrip value", false);
        testRule("It is prohibited that Runway has assigned lengthStrip value has and lengthStrip.uom value not equal-to ('FT','M')", false);
    }

    private void testRule(String msg, boolean shouldFail) {

        AixmSbvrLexer lexer = new AixmSbvrLexer(
                CharStreams.fromString(msg));
        AixmSbvrParser parser = new AixmSbvrParser(new CommonTokenStream(lexer));
        // if (!shouldFail) {
        //     VerboseErrorListener errListener = new VerboseErrorListener();
        //     parser.addErrorListener(errListener);
        // }
        ParseTree tree = parser.statement();
        if (shouldFail)
            Assert.assertTrue(parser.getNumberOfSyntaxErrors() > 0);
        else 
            Assert.assertEquals(0, parser.getNumberOfSyntaxErrors());
        System.out.println('|'+tree.toStringTree(parser)+'|'); // print LISP-style tree }}
    }

    public static class VerboseErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                int line, int charPositionInLine, String msg,
                RecognitionException e) {
            List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
            Collections.reverse(stack);
            System.err.println("rule stack: " + stack);
            System.err.println("line " + line + ":" + charPositionInLine + " at " +
                    offendingSymbol + ": " + msg);
        }
    }
}
