// generated with ast extension for cup
// version 0.8
// 26/5/2023 13:53:50


package rs.ac.bg.etf.pp1.ast;

public class ConstDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private ValueAssignment ValueAssignment;
    private ValueAssignmentList ValueAssignmentList;

    public ConstDecl (Type Type, ValueAssignment ValueAssignment, ValueAssignmentList ValueAssignmentList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ValueAssignment=ValueAssignment;
        if(ValueAssignment!=null) ValueAssignment.setParent(this);
        this.ValueAssignmentList=ValueAssignmentList;
        if(ValueAssignmentList!=null) ValueAssignmentList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ValueAssignment getValueAssignment() {
        return ValueAssignment;
    }

    public void setValueAssignment(ValueAssignment ValueAssignment) {
        this.ValueAssignment=ValueAssignment;
    }

    public ValueAssignmentList getValueAssignmentList() {
        return ValueAssignmentList;
    }

    public void setValueAssignmentList(ValueAssignmentList ValueAssignmentList) {
        this.ValueAssignmentList=ValueAssignmentList;
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
        if(Type!=null) Type.accept(visitor);
        if(ValueAssignment!=null) ValueAssignment.accept(visitor);
        if(ValueAssignmentList!=null) ValueAssignmentList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ValueAssignment!=null) ValueAssignment.traverseTopDown(visitor);
        if(ValueAssignmentList!=null) ValueAssignmentList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ValueAssignment!=null) ValueAssignment.traverseBottomUp(visitor);
        if(ValueAssignmentList!=null) ValueAssignmentList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDecl(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ValueAssignment!=null)
            buffer.append(ValueAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ValueAssignmentList!=null)
            buffer.append(ValueAssignmentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDecl]");
        return buffer.toString();
    }
}
