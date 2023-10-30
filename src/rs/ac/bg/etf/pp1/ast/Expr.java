// generated with ast extension for cup
// version 0.8
// 26/5/2023 13:53:50


package rs.ac.bg.etf.pp1.ast;

public class Expr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private Negative Negative;
    private AddOpInExprList AddOpInExprList;

    public Expr (Negative Negative, AddOpInExprList AddOpInExprList) {
        this.Negative=Negative;
        if(Negative!=null) Negative.setParent(this);
        this.AddOpInExprList=AddOpInExprList;
        if(AddOpInExprList!=null) AddOpInExprList.setParent(this);
    }

    public Negative getNegative() {
        return Negative;
    }

    public void setNegative(Negative Negative) {
        this.Negative=Negative;
    }

    public AddOpInExprList getAddOpInExprList() {
        return AddOpInExprList;
    }

    public void setAddOpInExprList(AddOpInExprList AddOpInExprList) {
        this.AddOpInExprList=AddOpInExprList;
    }

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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Negative!=null) Negative.accept(visitor);
        if(AddOpInExprList!=null) AddOpInExprList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Negative!=null) Negative.traverseTopDown(visitor);
        if(AddOpInExprList!=null) AddOpInExprList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Negative!=null) Negative.traverseBottomUp(visitor);
        if(AddOpInExprList!=null) AddOpInExprList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr(\n");

        if(Negative!=null)
            buffer.append(Negative.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOpInExprList!=null)
            buffer.append(AddOpInExprList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr]");
        return buffer.toString();
    }
}
