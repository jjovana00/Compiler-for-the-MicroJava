package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	
	Logger log = Logger.getLogger(getClass());
	Struct currentType = null;
	boolean errorDetected = false;
	boolean inMain = false;
	int nVars;
	
	public SemanticPass() {
		Tab.insert(Obj.Type, "bool", new Struct(Struct.Bool));
	}
	
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(Program program){
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(Type type){
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    	currentType = type.struct;
    }
    
	public void visit(NumConst constant) {
		constant.obj = new Obj(Obj.Con, "", Tab.intType);
		constant.obj.setAdr(constant.getNumConst());
		if (inMain) constant.obj.setLevel(1);
		else constant.obj.setLevel(0);
	}
	
	public void visit(CharConst constant) {
		constant.obj = new Obj(Obj.Con, "", Tab.charType);
		constant.obj.setAdr(constant.getCharConst());
		if (inMain) constant.obj.setLevel(1);
		else constant.obj.setLevel(0);
	}
	
	public void visit(BoolConst constant) {
		constant.obj = new Obj(Obj.Con, "", Tab.find("bool").getType());
		if (constant.getBoolConst().booleanValue()) constant.obj.setAdr(1);
		else constant.obj.setAdr(0);
		if (inMain) constant.obj.setLevel(1);
		else constant.obj.setLevel(0);
	}
	
	public void visit(ValueAssignment constant) {
		if (Tab.find(constant.getConstName()) != Tab.noObj) {
			report_error("Promenljiva sa naziov "+ constant.getConstName() + "vec postoji", constant);
		}
		else if (constant.getConsts().obj.getType() != currentType) {
			report_error("Konstanti "+ constant.getConstName() + "dodeljena vrednost pogresnog tipa", constant);
		}
		else {
			Obj obj = Tab.insert(Obj.Con, constant.getConstName(), currentType);
			obj.setAdr(constant.getConsts().obj.getAdr());
			obj.setLevel(constant.getConsts().obj.getLevel());
			report_info("Deklarisana konstanta "+ constant.getConstName(), constant);
		}
	
	}
	
	public void visit(SingleVarDeclaration decl) {
		if(Tab.find(decl.getVarName()) != Tab.noObj) {
			report_error("Promenljiva sa naziov "+ decl.getVarName()+ "vec postoji", decl);
		}
		else {
			Tab.insert(Obj.Var, decl.getVarName(), currentType);
			report_info("Deklarisana promenljiva "+ decl.getVarName(), decl);
		}
	}
	
	public void visit(ArrayVarDeclaration decl) {
		if(Tab.find(decl.getArrayName()) != Tab.noObj) {
			report_error("Promenljiva sa naziov "+ decl.getArrayName()+ "vec postoji", decl);
		}
		else {
			Tab.insert(Obj.Var, decl.getArrayName(), new Struct(Struct.Array, currentType));
			report_info("Deklarisan niz "+ decl.getArrayName(), decl);
		}
	}
	
	public void visit(MatrixVarDeclaration decl) {
		if(Tab.find(decl.getMatrixName()) != Tab.noObj) {
			report_error("Promenljiva sa naziov "+ decl.getMatrixName()+ " vec postoji", decl);
		}
		else {
			Obj obj = Tab.insert(Obj.Var, decl.getMatrixName(), new Struct(Struct.Array, new Struct(Struct.Array, currentType)));
			report_info("Deklarisana matrica "+ decl.getMatrixName(), decl);
		}
	}
	
	public void visit(MethodName main) {
		if(!main.getMethodName().equals("main")) {
			report_error("Metoda mora imati naziv main, a ne "+ main.getMethodName(), main);
		}
		else if (Tab.find(main.getMethodName()) != Tab.noObj) {
			report_error("Main metoda je vec deklarisana", main);
		}
		else {
			inMain = true;
			main.obj = Tab.insert(Obj.Meth, main.getMethodName(), new Struct(Struct.None));
	    	Tab.openScope();
			report_info("Ulazak u main metodu", main);
		}
	}
	
	public void visit(DesignatorVar designator) {
		Obj obj = Tab.find(designator.getVarName());
		designator.obj = obj;
		if (obj != Tab.noObj){
			if (obj.getType().getKind() == Struct.Array) {
				report_info("Pretrazivanje niza/matrice " + designator.obj.getName(), designator);
			}
			else report_info("Pretrazivanje promenljive" + designator.obj.getName(), designator);
		}
		else {
			report_error("Promenljiva sa naziov "+ designator.getVarName() + " ne postoji", designator);
		}
		
	}
	
	public void visit (DesignatorArrayName designator) {
		Obj obj = Tab.find(designator.getArrayName());
		designator.obj = obj;
		if (obj != Tab.noObj){
			if (obj.getKind() != Obj.Var) {
				report_error(obj.getName() + " nije moguce korisiti u datom izrazu", designator);
			}
			else if (obj.getType().getKind() != Struct.Array) {
				report_error("Promenljivu " + obj.getName() + " nije moguce korisiti u datom izrazu", designator);
			}
			else report_info("Pretrazivanje niza/matrice " + obj.getName(), designator);
		}
		else {
			report_error("Niz/matrica sa nazivom "+ designator.getArrayName() + " ne postoji", designator);
		}
	}
	
	public void visit(DesignatorArray designator) {
		Obj obj = designator.getDesignatorArrayName().obj;
		designator.obj = obj;
		if (designator.obj.getType().getKind() == Struct.Array) {
			if (designator.getExpr().obj.getType().getKind() == Struct.Int) {
				designator.obj = new Obj(Obj.Elem, obj.getName(), obj.getType().getElemType(), obj.getAdr(), obj.getLevel());
				report_info("Indeksiranje niza " + designator.obj.getName(), designator);
			}
			else {
				report_error("Indeksiranje se mora raditi celobrojnim podatkom", designator);
			}
		}
	}
	
	public void visit(DesignatorMatirx designator) {
		Obj obj = designator.getDesignatorArrayName().obj;
		designator.obj = obj;
		if (designator.obj.getType().getElemType().getKind() == Struct.Array) {
			if ((designator.getExpr().obj.getType().getKind() == Struct.Int) &&
			(designator.getExpr1().obj.getType().getKind() == Struct.Int)) {
				designator.obj = new Obj(Obj.Elem, obj.getName(), obj.getType().getElemType().getElemType(), obj.getAdr(), obj.getLevel());
				report_info("Indeksiranje matrice " + designator.obj.getName(), designator);
			}
			else {
				report_error("Indeksiranje se mora raditi celobrojnim podatkom", designator);
			}
		}
	}
	
	public void visit(DesignatorAssignmentOp assOp) {
		Obj lValue = assOp.getDesignator().obj;
		Obj rValue = assOp.getExpr().obj;
		int lValueType = lValue.getType().getKind();
		if (lValue.getType().getElemType() != null) {
			if (lValue.getType().getElemType().getElemType() != null)
				lValueType = lValue.getType().getElemType().getElemType().getKind();
			else lValueType = lValue.getType().getElemType().getKind();
		}
		if (lValue.getKind() == Obj.Var || lValue.getKind() == Obj.Elem) {
			if (lValueType == rValue.getType().getKind() ) {
				report_info("Uspesna dodela vrenosti promeljivoj/nizu/matrici " + lValue.getName(), assOp);
			}
			else {
				report_error("Tipovi nekompatabilni za dodelu " , assOp);
			}
		}
		else {
			report_error("Nije moguce dodeliti vrednost " + lValue.getName() , assOp);
		}
	}
	
	public void visit(DesignatorIncrementOp incOp) {
		if (incOp.getDesignator().obj.getKind() == Obj.Var || incOp.getDesignator().obj.getKind() == Obj.Elem) {
			if (incOp.getDesignator().obj.getType().getKind() == Struct.Int) {
				report_info("Uspesno inkrementiranje ", incOp);
			}
			else {
				report_error("Nije moguce inkrementirati " + incOp.getDesignator().obj.getName() + " zato sto nije celobrojnog tipa", incOp);
			}
		}
		else {
			report_error("Nije moguce inkrementirati " + incOp.getDesignator().obj.getName() , incOp);
		}
	}
	
	public void visit(DesignatorDecrementOp decOp) {
		if (decOp.getDesignator().obj.getKind() == Obj.Var || decOp.getDesignator().obj.getKind() == Obj.Elem) {
			if (decOp.getDesignator().obj.getType().getKind() == Struct.Int) {
				report_info("Uspesno dekrementianje ", decOp);
			}
			else {
				report_error("Nije moguce dekrementirati " + decOp.getDesignator().obj.getName() + " zato sto nije celobrojnog tipa", decOp);
			}	
		}
		else {
			report_error("Nije moguce dekrementirati " + decOp.getDesignator().obj.getName() , decOp);
		}
	}
	
	public void visit(ReturnStatement stmt) {
		if (!inMain) {
			report_error("Nije moguce izvrsiti return van funkcije" , stmt);
		}
	}
	
	public void visit(ReadStatement stmt) {
		Obj obj = stmt.getDesignator().obj;
		if(obj.getKind() == Obj.Var || obj.getKind() == Obj.Elem) {
			if (obj.getType().getKind() == Struct.Bool || obj.getType().getKind() == Struct.Char || obj.getType().getKind() == Struct.Int) report_info("Uspesno citanje", stmt);
			else report_error("Nije moguce izvrsiti citanje iz " + obj.getName() + ", zato sto nije odgovarajuceg tipa", stmt);
		}
		else {
			report_error("Nije moguce izvrsiti citanje iz " + obj.getName() + ", zato sto ne predstavlja promenljivu ili element niza/matrice", stmt);
		}
	}
	
	public void visit(PrintStatement printStmt) {
		int typeOfVar = printStmt.getExpr().obj.getType().getKind();
		if (typeOfVar != Struct.Bool && typeOfVar != Struct.Char && typeOfVar != Struct.Int) {
			report_error(printStmt.getExpr().obj.getName() + " mora biti tipa bool, char ili int", printStmt);
		}
	}	
	
	public void visit(IsPrintArgument printArgument)
	{
		printArgument.obj = new Obj(Obj.Con, "", new Struct(Struct.Int), printArgument.getNumValue(),1);
	
	}
	
	public void visit(DesignatorFactor designator) {
		designator.obj = designator.getDesignator().obj;
	}
	
	public void visit(ConstFactor constant) {
		constant.obj = constant.getConsts().obj;
	}
	
	public void visit(NewArrayFactor arrayFactor) {
		arrayFactor.obj = new Obj(Obj.Var, "", arrayFactor.getType().struct); 
		if(arrayFactor.getExpr().obj.getType().getKind() != Struct.Int) {
			report_error("Velicina niza mora se biti ceo broj", arrayFactor);
		}
	}
	
	public void visit(NewMatrixFaxtor matrixFactor) {
		matrixFactor.obj = new Obj(Obj.Var, "", matrixFactor.getType().struct); 
		if(matrixFactor.getExpr().obj.getType().getKind() != Struct.Int) {
			report_error("Velicina niza mora se biti ceo broj", matrixFactor);
		}
	}
	
	public void visit(ExprFactor expr) {
		expr.obj = expr.getExpr().obj;
	}
	
	public void visit(IsMulOpInExprList mulOp) {
		if (mulOp.getFactor().obj.getType().getKind() != Struct.Int) {
			report_error("Promenljiva " + mulOp.getFactor().obj.getName() + " mora biti ceo broj", mulOp);
		}
	}
	
	public void visit(Term term) {
		term.obj = term.getFactor().obj;
	}
	
	public void visit(IsAddOpInExprList addOp) {
		if(addOp.getTerm().obj.getType().getKind() != Struct.Int) {
			report_error("Promenljiva " + addOp.getTerm().obj.getName() + " mora biti ceo broj", addOp);
		}
	}
	
	public void visit(IsNegative negativeTerm) {
		negativeTerm.obj = negativeTerm.getTerm().obj;
		if(negativeTerm.getTerm().obj.getType().getKind() != Struct.Int) {
			report_error("Tip promeljive u aritmetickom izrazu mora biti int", negativeTerm);
		}
	}
	
	public void visit(NoNegative positiveTerm) {
		positiveTerm.obj = positiveTerm.getTerm().obj;
	}
	
	public void visit(Expr expr) {
		expr.obj = expr.getNegative().obj;
	}
	
	public void visit(MainMethodDecl endMain) {
		Tab.chainLocalSymbols(endMain.getMethodName().obj);
    	Tab.closeScope();
		inMain=false;
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	
}
	
	
