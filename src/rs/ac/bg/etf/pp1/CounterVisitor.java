package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ArrayVarDeclaration;
import rs.ac.bg.etf.pp1.ast.MatrixVarDeclaration;
import rs.ac.bg.etf.pp1.ast.SingleVarDeclaration;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class VarCounter extends CounterVisitor{
			
			public void visit(SingleVarDeclaration varDecl){
				count++;
			}
			
			public void visit(ArrayVarDeclaration varDecl){
				count++;
			}
			
			public void visit(MatrixVarDeclaration varDecl){
				count++;
			}
			
	}

}
 