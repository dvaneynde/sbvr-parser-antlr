package eu.eurocontrol.sbvr;

public class AixmSbvrVisitorImpl extends AixmSbvrBaseVisitor<Void> {

   static int nrConditions = 0;

    @Override
    public Void visitElementaryCond(AixmSbvrParser.ElementaryCondContext ctx) {
        nrConditions++;
        System.out.println("Visit: "+ctx.getText());
        return super.visitChildren(ctx);
    }

    public int getNrConditions() {
        return nrConditions;
    }
}
