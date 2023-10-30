package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPC;
	
	
	public int getMainPC() {
		return mainPC;
	}
	
	
	public void visit (MethodName main) {
		mainPC = Code.pc;
		Code.mainPc = Code.pc;
		main.obj.setAdr(Code.pc);
		
		SyntaxNode methodNode = main.getParent();
	
		VarCounter varCnt = new VarCounter();
		
		methodNode.traverseTopDown(varCnt);
		
		
		Code.put(Code.enter);
		Code.put(main.obj.getLevel());
		Code.put(main.obj.getLevel() + varCnt.getCount());
	}
	
	public void visit(MainMethodDecl mainMethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(DesignatorFactor factor) {
		if (factor.getDesignator().obj.getAdr() == Obj.NO_VALUE) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		Code.load(factor.getDesignator().obj);
	}
	
	public void visit(DesignatorMatirx matrix) {
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.load(matrix.obj);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		
		
		/*  dup_x2
			pop
			aload
			dup_x1
			pop

		*/
	}
	
	public void visit(DesignatorArrayName designator) {
		Code.load(designator.obj);
	}
	
	public void visit(DesignatorIncrementOp increment) {
		if (increment.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(increment.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(increment.getDesignator().obj);
	}
	
	public void visit(DesignatorDecrementOp decrement) {
		if (decrement.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(decrement.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(decrement.getDesignator().obj);
	}
	
	public void visit(IsNegative negative) {
		Code.put(Code.neg);
		
	}
	
	public void visit(ConstFactor constant) {
		Code.load(constant.getConsts().obj);
	}
	
	public void visit(NewArrayFactor factor) {
		Code.put(Code.newarray);
		if (factor.getType().struct == Tab.charType) Code.put(0);
		else Code.put(1);
	}
	
	public void visit(NewMatrixFaxtor factor) {
		int i = 0;
		Code.put(Code.dup2);
		Code.put(Code.newarray);
		if (factor.getType().struct == Tab.charType) Code.put(0);
		else Code.put(1);
		int loopBegin = Code.pc;
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.put(Code.dup_x2);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.put(Code.newarray);
		if (factor.getType().struct == Tab.charType) Code.put(0);
		else Code.put(1);
		if (factor.getType().struct == Tab.charType) Code.put(Code.bastore);
		else Code.put(Code.astore);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.dup_x2);
		Code.loadConst(0);
		Code.putFalseJump(Code.eq, loopBegin);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.pop);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.pop);
		
		
		/*dup2
		pop
		newarrey 
		//loopbegin
		dup_x2
		dup_x2
		pop
		dup_x2
		dup_x1
		pop
		const_1
		sub
		dup_x2
		dup_x1
		const_-1
		//pc = Code.pc + 1
		jne 
		newarray
		astore
		dup_x2
		pop
		dup_x1
		pop
		jmp //na loop begin
		dup_x2
		fixup
		pop
		pop
		pop
		pop
		pop*/
		
	}
	
	public void visit(IsMulOpInExprList mulOp) {
		SyntaxNode op = mulOp.getMulop();
		if (op.getClass() == MultiplicationOp.class) Code.put(Code.mul);
		else if (op.getClass() == DivOp.class) Code.put(Code.div);
		else if (op.getClass() == ModOp.class) Code.put(Code.rem);
	}
	
	public void visit(IsAddOpInExprList addOp) {
		SyntaxNode op = addOp.getAddop();
		if (op.getClass() == PlusOp.class) Code.put(Code.add);
		else if (op.getClass() == MinusOp.class) Code.put(Code.sub);
	}
	
	public void visit(DesignatorAssignmentOp assignment) {
		Code.store(assignment.getDesignator().obj);
	}
	
	public void visit(ReturnStatement returnStmt) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(PrintStatement printStmt) {
		boolean arg = false;
		if (printStmt.getPrintArgument().obj != null) {
			Code.loadConst(printStmt.getPrintArgument().obj.getAdr());
			arg = true;
		}
		int type = printStmt.getExpr().obj.getType().getKind();
		if(type == Struct.Char) {
			if (!arg) Code.loadConst(1);
			Code.put(Code.bprint);
		}
		else if(type == Struct.Bool) { 
			if (!arg) Code.loadConst(5);
			Code.put(Code.print);
		}
		else { 
			if (!arg) Code.loadConst(5);
			Code.put(Code.print);
		}
	}
	
	
	public void visit(ReadStatement readStmt) {
		int type = readStmt.getDesignator().obj.getType().getKind();
		if(type == Struct.Char) {
			Code.put(Code.bread);
		}
		else {
			Code.put(Code.read);
		}
		Code.store(readStmt.getDesignator().obj);
	}
	
	
	
	
	
}
