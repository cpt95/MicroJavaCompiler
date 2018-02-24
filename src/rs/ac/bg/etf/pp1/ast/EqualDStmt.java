// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class EqualDStmt extends DesignatorStatement {

    private DummyE DummyE;
    private EqualExp EqualExp;

    public EqualDStmt (DummyE DummyE, EqualExp EqualExp) {
        this.DummyE=DummyE;
        if(DummyE!=null) DummyE.setParent(this);
        this.EqualExp=EqualExp;
        if(EqualExp!=null) EqualExp.setParent(this);
    }

    public DummyE getDummyE() {
        return DummyE;
    }

    public void setDummyE(DummyE DummyE) {
        this.DummyE=DummyE;
    }

    public EqualExp getEqualExp() {
        return EqualExp;
    }

    public void setEqualExp(EqualExp EqualExp) {
        this.EqualExp=EqualExp;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DummyE!=null) DummyE.accept(visitor);
        if(EqualExp!=null) EqualExp.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DummyE!=null) DummyE.traverseTopDown(visitor);
        if(EqualExp!=null) EqualExp.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DummyE!=null) DummyE.traverseBottomUp(visitor);
        if(EqualExp!=null) EqualExp.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("EqualDStmt(\n");

        if(DummyE!=null)
            buffer.append(DummyE.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EqualExp!=null)
            buffer.append(EqualExp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [EqualDStmt]");
        return buffer.toString();
    }
}
