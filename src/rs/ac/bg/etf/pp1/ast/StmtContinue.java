// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class StmtContinue extends Statement {

    private Cont Cont;

    public StmtContinue (Cont Cont) {
        this.Cont=Cont;
        if(Cont!=null) Cont.setParent(this);
    }

    public Cont getCont() {
        return Cont;
    }

    public void setCont(Cont Cont) {
        this.Cont=Cont;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Cont!=null) Cont.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Cont!=null) Cont.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Cont!=null) Cont.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StmtContinue(\n");

        if(Cont!=null)
            buffer.append(Cont.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StmtContinue]");
        return buffer.toString();
    }
}
