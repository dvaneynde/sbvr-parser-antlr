package eu.eurocontrol.sbvr;

import eu.eurocontrol.sbvr.AixmSbvrParser.SingleValueContext;

/**
 * Very quick & dirty and limited imiplementation.
 */
public class AixmSbvrVisitorImpl extends AixmSbvrBaseVisitor<String> {

    static int nrConditions = 0;
    static int nrVars = 0;

    public int getNrConditions() {
        return nrConditions;
    }

    @Override
    public String visitStatement(AixmSbvrParser.StatementContext ctx) {
        String sMustOrNot;
        if (ctx.must() != null)
            sMustOrNot = visitMust(ctx.must());
        else
            sMustOrNot = visitMustNot(ctx.mustNot());
        String conditions = visitConditions(ctx.conditions());
        return conditions + sMustOrNot;
    }

    @Override
    public String visitMust(AixmSbvrParser.MustContext ctx) {
        return "";
    }

    @Override
    public String visitMustNot(AixmSbvrParser.MustNotContext ctx) {
        String s = visitChildren(ctx);
        s = "outcome = notOutcome(outcome);\n";
        System.out.println("visitMustNot: " + s);
        return s;
    }

    @Override
    public String visitConditions(AixmSbvrParser.ConditionsContext ctx) {
        String op = ctx.andOrBooleanOp().get(0).getText();
        String c1 = visitCondition(ctx.condition(0));
        String c2 = visitCondition(ctx.condition(1));
        String varC1 = "c" + nrVars++;
        String varC2 = "c" + nrVars++;
        String s = "Outcome outcome = new Outcome();\nOutcome "+varC1 + " = " + c1 + ";\nOutcome " + varC2 + " = " + c2 + ";\noutcome = "
                 + (op.equals("and") ? "andOutcomes(" : "orOutcomes(") + varC1+", " + varC2 + ");\n";
        return s;
    }

    @Override
    public String visitCondition(AixmSbvrParser.ConditionContext ctx) {
        String s = visitChildren(ctx);
        System.out.println("visitCondition: " + s);
        return s;
    }

    @Override
    public String visitElementaryCond(AixmSbvrParser.ElementaryCondContext ctx) {
        nrConditions++;
        String s = visitChildren(ctx);
        System.out.println("visitElementaryCond: " + s);
        return s;
    }

    @Override
    public String visitHasOrNotAssignedNameValueCond(AixmSbvrParser.HasOrNotAssignedNameValueCondContext ctx) {
        // this works, but better to use visitX methods, as in visitValueSimpleTestCond()
        boolean hasNot = ctx.notKeyword() != null;
        String s = "checkHasAssignedValue(\"" + ctx.name().getText() + "\",\"" + ctx.val().getText() + "\",\""
                + (hasNot ? false : true)
                + ")";
        System.out.println("visitHasOrNotAssignedNameValueCond: " + s);
        return s;
    }

    @Override
    public String visitValueSimpleTestCond(AixmSbvrParser.ValueSimpleTestCondContext ctx) {

        String name = ctx.name().getText();
        String st = visitSimpleTest(ctx.simpleTest());
        String s = "valueSimpleTest(\""+name+"\","+st+")";
        System.out.println("visitValueSimpleTestCond: " + s);
        return s;
    }

    @Override
    public String visitSimpleTest(AixmSbvrParser.SimpleTestContext ctx) {
        String op;
        {
            String sOp = ctx.booleanOp().getText();
            switch (sOp) {
                case "equal-to":
                    op = "evaluateEqualTo";
                    break;
                default:
                    throw new RuntimeException("Not yet implemented: " + sOp);
            }
        }
        String args;
        if (ctx.singleValue() != null)
            throw new RuntimeException("Not yet implemented: SimpleTest.SingleValue");
        else if (ctx.multipleValues() != null)
            args = visitMultipleValues(ctx.multipleValues());
        else if (ctx.name() != null)
            throw new RuntimeException("Not yet implemented: SimpleTest.Name");
        else
            throw new RuntimeException("Bug in SimpleTest");

        String s = "(name,multi)->"+op+"(name,multi), "+args;
        System.out.println("visitSimpleTest: " + s);
        return s;
    }

    @Override
    public String visitSingleValue(AixmSbvrParser.SingleValueContext ctx) {
        String s = ctx.STRING().getText().replace('\'', '"');
        System.out.println("visitSingleValue: " + s);
        return s;
    }

    @Override
    public String visitMultipleValues(AixmSbvrParser.MultipleValuesContext ctx) {
        // String[] as = new String[] { "a" };
        String s = "new String[] ";
        char sep = '{';
        for (SingleValueContext sv : ctx.singleValue()) {
            s += sep + visitSingleValue(sv);
            sep = ',';
        }
        s += '}';
        System.out.println("visitMultipleValues: " + s);
        return s;
    }

}
