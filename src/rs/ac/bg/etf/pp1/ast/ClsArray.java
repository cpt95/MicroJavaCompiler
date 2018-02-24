// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class ClsArray extends ClassVarDecl {

    private String clsArr;

    public ClsArray (String clsArr) {
        this.clsArr=clsArr;
    }

    public String getClsArr() {
        return clsArr;
    }

    public void setClsArr(String clsArr) {
        this.clsArr=clsArr;
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
        buffer.append("ClsArray(\n");

        buffer.append(" "+tab+clsArr);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClsArray]");
        return buffer.toString();
    }
}
