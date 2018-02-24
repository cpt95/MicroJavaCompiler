// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class MthdArray extends MethodVarDecl {

    private String MArrayName;

    public MthdArray (String MArrayName) {
        this.MArrayName=MArrayName;
    }

    public String getMArrayName() {
        return MArrayName;
    }

    public void setMArrayName(String MArrayName) {
        this.MArrayName=MArrayName;
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
        buffer.append("MthdArray(\n");

        buffer.append(" "+tab+MArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MthdArray]");
        return buffer.toString();
    }
}
