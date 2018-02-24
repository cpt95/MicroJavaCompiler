// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class MthdVar extends MethodVarDecl {

    private String MVarName;

    public MthdVar (String MVarName) {
        this.MVarName=MVarName;
    }

    public String getMVarName() {
        return MVarName;
    }

    public void setMVarName(String MVarName) {
        this.MVarName=MVarName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MthdVar(\n");

        buffer.append(" "+tab+MVarName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MthdVar]");
        return buffer.toString();
    }
}
