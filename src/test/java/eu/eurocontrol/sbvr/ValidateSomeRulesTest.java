package eu.eurocontrol.sbvr;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTree;

import org.junit.Assert;
import org.junit.Test;

public class ValidateSomeRulesTest {
    @Test
    public void testAFewRules() {
        testRule("It is obligatory that someField has not assigned ABC value", false);
        testRule("It is prohibited that Runway has assigned lengthStrip.uom value", false);
        testRule("It is prohibited that lengthStrip.uom value equal-to ('FT','M')", false);
        testRule("It is prohibited that Runway has assigned lengthStrip.uom value and lengthStrip.uom value equal-to ('FT','M')", false);
        testRule("It is prohibited that lengthStrip.uom value not equal-to ('FT','M')", true);
    }

    private void testRule(String msg, boolean shouldFail) {

        AixmSbvrLexer lexer = new AixmSbvrLexer(
                CharStreams.fromString(msg));
        AixmSbvrParser parser = new AixmSbvrParser(new CommonTokenStream(lexer));
        // if (!shouldFail) {
        // VerboseErrorListener errListener = new VerboseErrorListener();
        // parser.addErrorListener(errListener);
        // }
        ParseTree tree = parser.statement();
        int nrErrors = parser.getNumberOfSyntaxErrors();
        // Assert.assertEquals("Got syntax errors!",0, nrErrors);
        if (shouldFail)
            Assert.assertTrue("Got no syntax errors, where I expected some!", nrErrors > 0);
        else
            Assert.assertEquals("Got unexpecte syntax errors!", 0, nrErrors);
        System.out.println('|' + tree.toStringTree(parser) + '|'); // print LISP-style tree }}
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

            Assert.fail("Syntax errors.");
        }

        @Override
        public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact,
                BitSet ambigAlts, ATNConfigSet configs) {
            Assert.fail("reportAmbiguity");
            // TODO Auto-generated method stub
            super.reportAmbiguity(recognizer, dfa, startIndex, stopIndex, exact, ambigAlts, configs);
        }

        @Override
        public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
                BitSet conflictingAlts, ATNConfigSet configs) {
            Assert.fail("reportAttemptingFullContext");
            // TODO Auto-generated method stub
            super.reportAttemptingFullContext(recognizer, dfa, startIndex, stopIndex, conflictingAlts, configs);
        }

        @Override
        public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction,
                ATNConfigSet configs) {
            Assert.fail("reportContextSensitivity");
            // TODO Auto-generated method stub
            super.reportContextSensitivity(recognizer, dfa, startIndex, stopIndex, prediction, configs);
        }
    }
}
