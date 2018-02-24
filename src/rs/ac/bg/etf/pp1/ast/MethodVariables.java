// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class MethodVariables extends MethodVars {

    private MethodVars MethodVars;
    private MethodVar MethodVar;

    public MethodVariables (MethodVars MethodVars, MethodVar MethodVar) {
        this.MethodVars=MethodVars;
        if(MethodVars!=null) MethodVars.setParent(this);
        this.MethodVar=MethodVar;
        if(MethodVar!=null) MethodVar.setParent(this);
    }

    public MethodVars getMethodVars() {
        return MethodVars;
    }

    public void setMethodVars(MethodVars MethodVars) {
        this.MethodVars=MethodVars;
    }

    public MethodVar getMethodVar() {
        return MethodVar;
    }

    public void setMethodVar(MethodVar MethodVar) {
        this.MethodVar=MethodVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodVars!=null) MethodVars.accept(visitor);
        if(MethodVar!=null) MethodVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodVars!=null) MethodVars.traverseTopDown(visitor);
        if(MethodVar!=null) MethodVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodVars!=null) MethodVars.traverseBottomUp(visitor);
        if(MethodVar!=null) MethodVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodVariables(\n");

        if(MethodVars!=null)
            buffer.append(MethodVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodVar!=null)
            buffer.append(MethodVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodVariables]");
        return buffer.toString();
    }
}
