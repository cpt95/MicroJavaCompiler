// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class Extend extends Extending {

    private ExtendingError ExtendingError;

    public Extend (ExtendingError ExtendingError) {
        this.ExtendingError=ExtendingError;
        if(ExtendingError!=null) ExtendingError.setParent(this);
    }

    public ExtendingError getExtendingError() {
        return ExtendingError;
    }

    public void setExtendingError(ExtendingError ExtendingError) {
        this.ExtendingError=ExtendingError;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendingError!=null) ExtendingError.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendingError!=null) ExtendingError.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendingError!=null) ExtendingError.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Extend(\n");

        if(ExtendingError!=null)
            buffer.append(ExtendingError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Extend]");
        return buffer.toString();
    }
}
