// generated with ast extension for cup
// version 0.8
// 26/5/2023 13:53:50


package rs.ac.bg.etf.pp1.ast;

public class ValueAssignments extends ValueAssignmentList {

    private ValueAssignmentList ValueAssignmentList;
    private ValueAssignment ValueAssignment;

    public ValueAssignments (ValueAssignmentList ValueAssignmentList, ValueAssignment ValueAssignment) {
        this.ValueAssignmentList=ValueAssignmentList;
        if(ValueAssignmentList!=null) ValueAssignmentList.setParent(this);
        this.ValueAssignment=ValueAssignment;
        if(ValueAssignment!=null) ValueAssignment.setParent(this);
    }

    public ValueAssignmentList getValueAssignmentList() {
        return ValueAssignmentList;
    }

    public void setValueAssignmentList(ValueAssignmentList ValueAssignmentList) {
        this.ValueAssignmentList=ValueAssignmentList;
    }

    public ValueAssignment getValueAssignment() {
        return ValueAssignment;
    }

    public void setValueAssignment(ValueAssignment ValueAssignment) {
        this.ValueAssignment=ValueAssignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ValueAssignmentList!=null) ValueAssignmentList.accept(visitor);
        if(ValueAssignment!=null) ValueAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ValueAssignmentList!=null) ValueAssignmentList.traverseTopDown(visitor);
        if(ValueAssignment!=null) ValueAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ValueAssignmentList!=null) ValueAssignmentList.traverseBottomUp(visitor);
        if(ValueAssignment!=null) ValueAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ValueAssignments(\n");

        if(ValueAssignmentList!=null)
            buffer.append(ValueAssignmentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ValueAssignment!=null)
            buffer.append(ValueAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ValueAssignments]");
        return buffer.toString();
    }
}
