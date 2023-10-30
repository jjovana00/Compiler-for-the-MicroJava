// generated with ast extension for cup
// version 0.8
// 26/5/2023 13:53:50


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Designator Designator);
    public void visit(AddOpInExprList AddOpInExprList);
    public void visit(Factor Factor);
    public void visit(Consts Consts);
    public void visit(Mulop Mulop);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(ProgDecl ProgDecl);
    public void visit(PrintArgument PrintArgument);
    public void visit(MulOpInExprList MulOpInExprList);
    public void visit(DesignatorMatrixName DesignatorMatrixName);
    public void visit(ProgDeclList ProgDeclList);
    public void visit(VarDeclList VarDeclList);
    public void visit(DesignatorOpListElem DesignatorOpListElem);
    public void visit(VarDecl VarDecl);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(Addop Addop);
    public void visit(VarDeclarationList VarDeclarationList);
    public void visit(Negative Negative);
    public void visit(Statement Statement);
    public void visit(ValueAssignmentList ValueAssignmentList);
    public void visit(StatementList StatementList);
    public void visit(DesignatorOpList DesignatorOpList);
    public void visit(ModOp ModOp);
    public void visit(DivOp DivOp);
    public void visit(MultiplicationOp MultiplicationOp);
    public void visit(MinusOp MinusOp);
    public void visit(PlusOp PlusOp);
    public void visit(Assignop Assignop);
    public void visit(Label Label);
    public void visit(DesignatorArrayName DesignatorArrayName);
    public void visit(DesignatorMatirx DesignatorMatirx);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(DesignatorVar DesignatorVar);
    public void visit(ExprFactor ExprFactor);
    public void visit(NewMatrixFaxtor NewMatrixFaxtor);
    public void visit(NewArrayFactor NewArrayFactor);
    public void visit(ConstFactor ConstFactor);
    public void visit(DesignatorFactor DesignatorFactor);
    public void visit(NoMulOpInExprList NoMulOpInExprList);
    public void visit(IsMulOpInExprList IsMulOpInExprList);
    public void visit(Term Term);
    public void visit(NoAddOpInExprList NoAddOpInExprList);
    public void visit(IsAddOpInExprList IsAddOpInExprList);
    public void visit(NoNegative NoNegative);
    public void visit(IsNegative IsNegative);
    public void visit(Expr Expr);
    public void visit(DesignatorStatementError DesignatorStatementError);
    public void visit(DesignatorDecrementOp DesignatorDecrementOp);
    public void visit(DesignatorIncrementOp DesignatorIncrementOp);
    public void visit(DesignatorAssignmentOp DesignatorAssignmentOp);
    public void visit(NoPrintArgument NoPrintArgument);
    public void visit(IsPrintArgument IsPrintArgument);
    public void visit(StatementsBlock StatementsBlock);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(DesigStatement DesigStatement);
    public void visit(NoStmtList NoStmtList);
    public void visit(StmtList StmtList);
    public void visit(VarDeclarationError VarDeclarationError);
    public void visit(MatrixVarDeclaration MatrixVarDeclaration);
    public void visit(ArrayVarDeclaration ArrayVarDeclaration);
    public void visit(SingleVarDeclaration SingleVarDeclaration);
    public void visit(NoVarDeclarationList NoVarDeclarationList);
    public void visit(VarDeclarationListError VarDeclarationListError);
    public void visit(MulipleVarDeclaration MulipleVarDeclaration);
    public void visit(VarDecls VarDecls);
    public void visit(NoVarDeclList NoVarDeclList);
    public void visit(IsVarDeclList IsVarDeclList);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(ValueAssignment ValueAssignment);
    public void visit(NoValueAssignments NoValueAssignments);
    public void visit(ValueAssignments ValueAssignments);
    public void visit(ConstDecl ConstDecl);
    public void visit(Type Type);
    public void visit(MethodName MethodName);
    public void visit(MainMethodDecl MainMethodDecl);
    public void visit(VarProgDecl VarProgDecl);
    public void visit(ConstProgDecl ConstProgDecl);
    public void visit(NoProgDecl NoProgDecl);
    public void visit(ProgDeclarations ProgDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
