// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class OneDsgn extends Designator {

    private String oneName;

    public OneDsgn (String oneName) {
        this.oneName=oneName;
    }

    public String getOneName() {
        return oneName;
    }

    public void setOneName(String oneName) {
        this.oneName=oneName;
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
        buffer.append("OneDsgn(\n");

        buffer.append(" "+tab+oneName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneDsgn]");
        return buffer.toString();
    }
}
