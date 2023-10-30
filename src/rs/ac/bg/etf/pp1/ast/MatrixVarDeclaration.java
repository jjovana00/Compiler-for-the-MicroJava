// generated with ast extension for cup
// version 0.8
// 26/5/2023 13:53:50


package rs.ac.bg.etf.pp1.ast;

public class MatrixVarDeclaration extends VarDeclaration {

    private String matrixName;

    public MatrixVarDeclaration (String matrixName) {
        this.matrixName=matrixName;
    }

    public String getMatrixName() {
        return matrixName;
    }

    public void setMatrixName(String matrixName) {
        this.matrixName=matrixName;
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
        buffer.append("MatrixVarDeclaration(\n");

        buffer.append(" "+tab+matrixName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MatrixVarDeclaration]");
        return buffer.toString();
    }
}
