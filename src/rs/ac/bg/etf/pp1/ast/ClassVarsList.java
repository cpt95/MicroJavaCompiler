// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class ClassVarsList extends ClassVars {

    private ClassVars ClassVars;
    private ClassVar ClassVar;

    public ClassVarsList (ClassVars ClassVars, ClassVar ClassVar) {
        this.ClassVars=ClassVars;
        if(ClassVars!=null) ClassVars.setParent(this);
        this.ClassVar=ClassVar;
        if(ClassVar!=null) ClassVar.setParent(this);
    }

    public ClassVars getClassVars() {
        return ClassVars;
    }

    public void setClassVars(ClassVars ClassVars) {
        this.ClassVars=ClassVars;
    }

    public ClassVar getClassVar() {
        return ClassVar;
    }

    public void setClassVar(ClassVar ClassVar) {
        this.ClassVar=ClassVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassVars!=null) ClassVars.accept(visitor);
        if(ClassVar!=null) ClassVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassVars!=null) ClassVars.traverseTopDown(visitor);
        if(ClassVar!=null) ClassVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassVars!=null) ClassVars.traverseBottomUp(visitor);
        if(ClassVar!=null) ClassVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarsList(\n");

        if(ClassVars!=null)
            buffer.append(ClassVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVar!=null)
            buffer.append(ClassVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarsList]");
        return buffer.toString();
    }
}
