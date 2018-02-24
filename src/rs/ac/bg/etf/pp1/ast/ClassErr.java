// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class ClassErr extends ClassVarDeclList {

    private ClassError ClassError;

    public ClassErr (ClassError ClassError) {
        this.ClassError=ClassError;
        if(ClassError!=null) ClassError.setParent(this);
    }

    public ClassError getClassError() {
        return ClassError;
    }

    public void setClassError(ClassError ClassError) {
        this.ClassError=ClassError;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassError!=null) ClassError.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassError!=null) ClassError.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassError!=null) ClassError.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassErr(\n");

        if(ClassError!=null)
            buffer.append(ClassError.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassErr]");
        return buffer.toString();
    }
}
