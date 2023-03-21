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
    public void testApp() {
        AixmSbvrLexer lexer = new AixmSbvrLexer(
                CharStreams.fromString("It is obligatory that someField has not assigned ABC value"));
        AixmSbvrParser parser = new AixmSbvrParser(new CommonTokenStream(lexer));
        ValidListener errListener = new ValidListener();
        parser.addErrorListener(errListener);
        ParseTree tree = parser.statement();

        System.out.println(tree.toStringTree(parser)); // print LISP-style tree }}
    }

    public static class ValidListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                int line, int charPositionInLine, String msg,
                RecognitionException e) {
            Assert.fail(msg);
        }
    }

    public static class VerboseListener extends BaseErrorListener {
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
