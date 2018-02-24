// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class Declarations extends DeclareList {

    private DeclareList DeclareList;
    private Declare Declare;

    public Declarations (DeclareList DeclareList, Declare Declare) {
        this.DeclareList=DeclareList;
        if(DeclareList!=null) DeclareList.setParent(this);
        this.Declare=Declare;
        if(Declare!=null) Declare.setParent(this);
    }

    public DeclareList getDeclareList() {
        return DeclareList;
    }

    public void setDeclareList(DeclareList DeclareList) {
        this.DeclareList=DeclareList;
    }

    public Declare getDeclare() {
        return Declare;
    }

    public void setDeclare(Declare Declare) {
        this.Declare=Declare;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DeclareList!=null) DeclareList.accept(visitor);
        if(Declare!=null) Declare.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DeclareList!=null) DeclareList.traverseTopDown(visitor);
        if(Declare!=null) Declare.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DeclareList!=null) DeclareList.traverseBottomUp(visitor);
        if(Declare!=null) Declare.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Declarations(\n");

        if(DeclareList!=null)
            buffer.append(DeclareList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Declare!=null)
            buffer.append(Declare.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Declarations]");
        return buffer.toString();
    }
}
