// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:10:39


package rs.ac.bg.etf.pp1.ast;

public class ConstError extends Const {

    public ConstError () {
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
        buffer.append("ConstError(\n");

        buffer.append(tab);
        buffer.append(") [ConstError]");
        return buffer.toString();
    }
}
