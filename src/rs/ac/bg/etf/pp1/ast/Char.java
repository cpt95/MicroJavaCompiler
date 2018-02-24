// generated with ast extension for cup
// version 0.8
// 5/1/2018 20:53:17


package rs.ac.bg.etf.pp1.ast;

public class Char extends Const {

    private String charName;
    private Character chr;

    public Char (String charName, Character chr) {
        this.charName=charName;
        this.chr=chr;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName=charName;
    }

    public Character getChr() {
        return chr;
    }

    public void setChr(Character chr) {
        this.chr=chr;
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
        buffer.append("Char(\n");

        buffer.append(" "+tab+charName);
        buffer.append("\n");

        buffer.append(" "+tab+chr);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Char]");
        return buffer.toString();
    }
}
