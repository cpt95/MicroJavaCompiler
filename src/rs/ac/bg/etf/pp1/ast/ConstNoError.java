// generated with ast extension for cup
// version 0.8
// 31/0/2018 13:10:39


package rs.ac.bg.etf.pp1.ast;

public class ConstNoError extends Const {

    private ConstWOError ConstWOError;

    public ConstNoError (ConstWOError ConstWOError) {
        this.ConstWOError=ConstWOError;
        if(ConstWOError!=null) ConstWOError.setParent(this);
    }

    public ConstWOError getConstWOError() {
        return ConstWOError;
    }

    public void setConstWOError(ConstWOError ConstWOError) {
        this.ConstWOError=ConstWOError;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstWOError!=null) ConstWOError.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstWOError!=null) ConstWOError.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstWOError!=null) ConstWOError.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstNoError(\n");

        if(ConstWOError!=null)
            buffer.append(ConstWOError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstNoError]");
        return buffer.toString();
    }
}
