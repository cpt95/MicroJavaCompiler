// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public abstract class RetType implements SyntaxNode {

    private SyntaxNode parent;

    private int line;

    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public abstract void accept(Visitor visitor);
    public abstract void childrenAccept(Visitor visitor);
    public abstract void traverseTopDown(Visitor visitor);
    public abstract void traverseBottomUp(Visitor visitor);

    public String toString() { return toString(""); }
    public abstract String toString(String tab);
}
