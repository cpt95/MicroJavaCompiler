package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.ActualPar;
import rs.ac.bg.etf.pp1.ast.ActualParamsList;
import rs.ac.bg.etf.pp1.ast.ActualPars;
import rs.ac.bg.etf.pp1.ast.Array;
import rs.ac.bg.etf.pp1.ast.ArrayDsgn;
import rs.ac.bg.etf.pp1.ast.Bool;
import rs.ac.bg.etf.pp1.ast.Char;
import rs.ac.bg.etf.pp1.ast.ClassDecl;
import rs.ac.bg.etf.pp1.ast.ClsArray;
import rs.ac.bg.etf.pp1.ast.ClsName;
import rs.ac.bg.etf.pp1.ast.ClsVar;
import rs.ac.bg.etf.pp1.ast.CondFactOne;
import rs.ac.bg.etf.pp1.ast.CondFactOr;
import rs.ac.bg.etf.pp1.ast.CondList;
import rs.ac.bg.etf.pp1.ast.CondOne;
import rs.ac.bg.etf.pp1.ast.CondTermList;
import rs.ac.bg.etf.pp1.ast.CondTermOne;
import rs.ac.bg.etf.pp1.ast.DotDsgn;
import rs.ac.bg.etf.pp1.ast.DummyD;
import rs.ac.bg.etf.pp1.ast.DummyE;
import rs.ac.bg.etf.pp1.ast.DummyL;
import rs.ac.bg.etf.pp1.ast.EqualDStmt;
import rs.ac.bg.etf.pp1.ast.EqualExpr;
import rs.ac.bg.etf.pp1.ast.Expr;
import rs.ac.bg.etf.pp1.ast.ExprM;
import rs.ac.bg.etf.pp1.ast.ExprNM;
import rs.ac.bg.etf.pp1.ast.ExtendingType;
import rs.ac.bg.etf.pp1.ast.FactorMany;
import rs.ac.bg.etf.pp1.ast.FactorOne;
import rs.ac.bg.etf.pp1.ast.FctBool;
import rs.ac.bg.etf.pp1.ast.FctChar;
import rs.ac.bg.etf.pp1.ast.FctDesignator;
import rs.ac.bg.etf.pp1.ast.FctNew;
import rs.ac.bg.etf.pp1.ast.FctNewArr;
import rs.ac.bg.etf.pp1.ast.FctNum;
import rs.ac.bg.etf.pp1.ast.FctParenExp;
import rs.ac.bg.etf.pp1.ast.FctPars;
import rs.ac.bg.etf.pp1.ast.FormArray;
import rs.ac.bg.etf.pp1.ast.FormVar;
import rs.ac.bg.etf.pp1.ast.FormalParamList;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MinusDStmt;
import rs.ac.bg.etf.pp1.ast.MthdArray;
import rs.ac.bg.etf.pp1.ast.MthdVar;
import rs.ac.bg.etf.pp1.ast.Num;
import rs.ac.bg.etf.pp1.ast.OneDsgn;
import rs.ac.bg.etf.pp1.ast.ParsDStmt;
import rs.ac.bg.etf.pp1.ast.PlusDStmt;
import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.RelopEQ;
import rs.ac.bg.etf.pp1.ast.RelopGR;
import rs.ac.bg.etf.pp1.ast.RelopGRE;
import rs.ac.bg.etf.pp1.ast.RelopLS;
import rs.ac.bg.etf.pp1.ast.RelopLSE;
import rs.ac.bg.etf.pp1.ast.RelopNEQ;
import rs.ac.bg.etf.pp1.ast.ReturnType;
import rs.ac.bg.etf.pp1.ast.StmtContinue;
import rs.ac.bg.etf.pp1.ast.StmtDoWhile;
import rs.ac.bg.etf.pp1.ast.StmtDsgn;
import rs.ac.bg.etf.pp1.ast.StmtDsgnS;
import rs.ac.bg.etf.pp1.ast.StmtExp;
import rs.ac.bg.etf.pp1.ast.StmtExpNum;
import rs.ac.bg.etf.pp1.ast.StmtReturn;
import rs.ac.bg.etf.pp1.ast.StmtReturnExp;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermF;
import rs.ac.bg.etf.pp1.ast.TermMany;
import rs.ac.bg.etf.pp1.ast.TermOne;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.Var;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidType;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	boolean returnFound = false;
	boolean isVoid = false;
	boolean global = false;
	boolean doWhile = false;
	int printCallCount = 0, index = 0;
	int currRelOp = 0;
	int nVars;
	String dsgnName = "";
	String arrayName = "";
	Obj currentMethod = null;
	Obj passedObj = null;
	Obj methObj = null;
	Type currentVarType = null;

	Logger log = Logger.getLogger(getClass());
	HashMap<Obj, List<Param>> params = new HashMap<Obj, List<Param>>();
	List<Param> currentParams;
	List<String> array = new ArrayList<String>();
	Collection<Obj> locals = new ArrayList<>();
	List<Expr> actPars = new ArrayList<>();

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(": na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(": na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(Program program) {
		nVars = ExtendedTab.currentScope.getnVars();
		ExtendedTab.chainLocalSymbols(program.getProgName().obj);
		ExtendedTab.closeScope();
	}

	public void visit(ProgName progName) {
		progName.obj = ExtendedTab.insert(Obj.Prog, progName.getPName(), ExtendedTab.noType);
		ExtendedTab.openScope();
	}

	public void visit(ClsName ClsName) {
		ClsName.obj = ExtendedTab.insert(Obj.Type, ClsName.getClsName(), ExtendedTab.nullType);
		report_info("Deklarisana klasa <" + ClsName.getClsName() + ">", ClsName);
		ExtendedTab.openScope();
	}

	public void visit(ClassDecl ClassDecl) {
		ClassDecl.obj = ClassDecl.getClassName().obj;
		ExtendedTab.chainLocalSymbols(ClassDecl.obj.getType());
		ExtendedTab.closeScope();
	}

	public void visit(VoidType VoidType) {
		isVoid = true;
		currentParams = new ArrayList<Param>();
		report_info("Obrada metode <" + VoidType.getVoidName() + ">", VoidType);
		currentMethod = ExtendedTab.insert(Obj.Meth, VoidType.getVoidName(), ExtendedTab.noType);
		VoidType.obj = currentMethod;
		ExtendedTab.openScope();
	}

	public void visit(ReturnType ReturnType) {
		Struct t = ReturnType.getType().struct;
		if (t == Tab.noType)
			isVoid = true;
		currentParams = new ArrayList<Param>();
		report_info("Obrada metode <" + ReturnType.getMethName() + ">", ReturnType);
		currentMethod = ExtendedTab.insert(Obj.Meth, ReturnType.getMethName(), currentVarType.struct);
		ReturnType.obj = currentMethod;
		ExtendedTab.openScope();
	}

	public void visit(MethodDecl MethodDecl) {
		if (!returnFound && currentMethod.getType() != ExtendedTab.noType) {
			report_error("Semanticka greska funcija <" + currentMethod.getName() + "> nema return iskaz", MethodDecl);
		}
		params.put(currentMethod, currentParams);

		ExtendedTab.chainLocalSymbols(currentMethod);
		ExtendedTab.closeScope();

		currentMethod = null;
		returnFound = false;
		isVoid = false;
	}

	public void visit(FormalParamList FormalParamList) {
		params.put(currentMethod, currentParams);
	}

	public void visit(FormVar FormVar) {
		ExtendedTab.insert(Obj.Fld, FormVar.getFormVar(), currentVarType.struct);
		Param p = new Param(FormVar.getFormVar(), currentVarType, false);
		currentParams.add(p);
	}

	public void visit(FormArray FormArray) {
		ExtendedTab.insert(Obj.Fld, FormArray.getFormArray(), ExtendedTab.getArrayStruct(currentVarType));
		Param p = new Param(FormArray.getFormArray(), currentVarType, true);
		currentParams.add(p);
	}

	public void visit(ClsVar ClsVar) {
		Obj varNode = ExtendedTab.insert(Obj.Var, ClsVar.getClsVar(), currentVarType.struct);
		report_info("Deklarisano polje klase <" + ClsVar.getClsVar() + ">", ClsVar);
	}

	public void visit(ClsArray ClsArray) {
		Obj arrayNode = ExtendedTab.insert(Obj.Var, ClsArray.getClsArr(), ExtendedTab.getArrayStruct(currentVarType));
		report_info("Deklarisano polje klase, niz <" + ClsArray.getClsArr() + ">", ClsArray);
	}

	public void visit(StmtDoWhile StmtDoWhile) {
		doWhile = false;
	}

	public void visit(DummyD DummyD) {
		doWhile = true;
	}

	public void visit(DummyL DummyL) {
		if (arrayName == "")
			array.add(dsgnName);
		else
			array.add(arrayName);
		arrayName = "";
		index++;
	}

	public void visit(DummyE DummyE) {
		Obj o;
		if (arrayName == "")
			o = ExtendedTab.find(dsgnName);
		else
			o = ExtendedTab.find(arrayName);
		if (passedObj != null)
			o = passedObj;
		if (o.getKind() == Obj.Con)
			report_info("Greska, nedozvoljeno je dodeljivati vrednost konstatama!", DummyE);
		DummyE.obj = DummyE.getDesignator().obj;
	}

	public void visit(ActualParamsList ActualParamsList) {
		actPars = new ArrayList<>();
	}

	public void visit(ActualPar ActualPar) {
		actPars.add(ActualPar.getExpr());
	}

	public void visit(ActualPars ActualPars) {
		actPars.add(ActualPars.getExpr());
	}

	public void visit(StmtReturnExp StmtReturnExp) {
		returnFound = true;
		Struct currMethType = currentMethod.getType();
		if (isVoid) {
			report_error("Greska, metod ne sme imati return sa izrazom, jer je tipa void!", StmtReturnExp);
		}
		if (!currMethType.compatibleWith(StmtReturnExp.getExpr().struct)) {
			report_error("Greska, tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije <"
					+ currentMethod.getName() + ">", StmtReturnExp);
		}
	}

	public void visit(StmtExpNum StmtExpNum) {
		if (StmtExpNum.getExpr().struct != ExtendedTab.boolType && StmtExpNum.getExpr().struct != ExtendedTab.charType
				&& StmtExpNum.getExpr().struct != ExtendedTab.intType)
			report_error("Greska, argument poziva metode mora biti odgovarajuceg tipa!", StmtExpNum);
	}

	public void visit(StmtExp StmtExp) {
		if (StmtExp.getExpr().struct != ExtendedTab.boolType && StmtExp.getExpr().struct != ExtendedTab.charType
				&& StmtExp.getExpr().struct != ExtendedTab.intType)
			report_error("Greska, argument poziva metode mora biti odgovarajuceg tipa!", StmtExp);
	}

	public void visit(StmtDsgn StmtDsgn) {
		if (StmtDsgn.getDesignator().obj.getType() != ExtendedTab.boolType
				&& StmtDsgn.getDesignator().obj.getType() != ExtendedTab.charType
				&& StmtDsgn.getDesignator().obj.getType() != ExtendedTab.intType)
			report_error("Greska, argument poziva metode mora biti odgovarajuceg tipa!", StmtDsgn);
		Obj o = ExtendedTab.find(dsgnName);
		if (o.getKind() == Obj.Con && array.isEmpty())
			report_error("Greska, argument poziva metode ne sme biti const", StmtDsgn);
		if (index == 1)
			array.remove(--index);
	}

	public void visit(StmtContinue StmtContinue) {
		if (!doWhile)
			report_error("Greska, naredba |continue| mora biti unutar do-while petlje!", StmtContinue);
	}

	public void visit(StmtReturn StmtReturn) {
		returnFound = true;
		if (!isVoid) {
			report_error("Greska, metod mora imati return iskaz sa izrazom, jer nije void!", StmtReturn);
		}
	}

	public void visit(MthdVar MthdVar) {
		Obj varNode = ExtendedTab.insert(Obj.Var, MthdVar.getMVarName(), currentVarType.struct);
		report_info("Deklarisana lokalna promenljiva <" + MthdVar.getMVarName() + ">", MthdVar);
	}

	public void visit(MthdArray MthdArray) {
		Obj arrayNode = ExtendedTab.insert(Obj.Var, MthdArray.getMArrayName(),
				ExtendedTab.getArrayStruct(currentVarType));
		report_info("Deklarisana lokalna promenljiva <" + MthdArray.getMArrayName() + ">", MthdArray);
	}

	public void visit(OneDsgn OneDsgn) {
		global = true;
		Obj o = Tab.find(OneDsgn.getOneName());
		if (o != Tab.noObj)
			if (o.getKind() == Obj.Fld)
				report_info("Koriscenje formalnog argumenta funkcije <" + OneDsgn.getOneName() + ">", OneDsgn);
			else
				report_info("Pristup simbolu <" + OneDsgn.getOneName() + ">", OneDsgn);
		if (o.getType() == ExtendedTab.nullType) {
			locals = o.getType().getMembers();
		} else {
			locals = new ArrayList<>();
			passedObj = o;
			if (o.getKind() == Obj.Meth)
				methObj = o;
		}

		OneDsgn.obj = o;
		dsgnName = OneDsgn.getOneName();
	}

	public void visit(ArrayDsgn ArrayDsgn) {
		String arr = array.get(index - 1);
		Obj o = Tab.find(arr);
		for (Obj x : locals)
			if (x.getName().equals(arr))
				o = x;
		if (locals.isEmpty())
			o = passedObj;
		if (passedObj.getKind() != Struct.Array)
			o = ExtendedTab.find(arr);
		if (o != Tab.noObj) {
			if (o.getKind() == Obj.Fld)
				report_info("Koriscenje formalnog argumenta funkcije <" + arr + ">", ArrayDsgn);
			if (ArrayDsgn.getExpr().struct.compatibleWith(ExtendedTab.intType)) {
				report_info("Pristup elementu niza <" + arr + ">", ArrayDsgn);
			} else {
				report_error("Nekompatibilan tip za indeksiranje niza", ArrayDsgn);
			}
		}
		passedObj = o;
		if (index > 1)
			array.remove(--index);
		if (o.getType().getKind() != Struct.Array)
			report_error("Greska, promenljiva <" + arr + "> nije niz", ArrayDsgn);
		ArrayDsgn.obj = new Obj(Obj.Elem, "", o.getType().getElemType());
	}

	public void visit(DotDsgn DotDsgn) {
		global = false;
		boolean found = false;
		Obj o = Tab.find(DotDsgn.getDotName());
		for (Obj x : locals) {
			if (x.getName().equals(DotDsgn.getDotName())) {
				o = x;
				found = true;
			}
		}
		if (!found)
			report_error("Greska, <" + DotDsgn.getDotName() + "> nije polje klase!", DotDsgn);
		if (locals.isEmpty())
			report_error("Greska, promenljiva nije objekat tipa klase!", DotDsgn);
		if (o.getType() == ExtendedTab.nullType) {
			locals = o.getLocalSymbols();
		} else {
			locals = new ArrayList<>();
			passedObj = o;
		}
		arrayName = DotDsgn.getDotName();
		DotDsgn.obj = o;
	}

	public void visit(TermF TermF) {
		TermF.struct = TermF.getFactorList().struct;
	}

	public void visit(ExprM ExprM) {
		if (ExprM.getTermList().struct != ExtendedTab.intType)
			report_error("Greska, operator neispravnog tipa!", ExprM);
		ExprM.struct = ExprM.getTermList().struct;
	}

	public void visit(ExprNM ExprNM) {
		ExprNM.struct = ExprNM.getTermList().struct;
	}

	public void visit(TermOne TermOne) {
		TermOne.struct = TermOne.getTerm().struct;
	}

	public void visit(TermMany TermMany) {
		if (TermMany.getTermList().struct != null) {
			if (TermMany.getTermList().struct == ExtendedTab.intType
					&& TermMany.getTerm().struct == ExtendedTab.intType) {
				TermMany.struct = TermMany.getTerm().struct;
			} else {
				TermMany.struct = ExtendedTab.noType;
				report_error("Greska, operatori nisu celobrojnog tipa!", TermMany);
			}
		}
	}

	public void visit(FctDesignator FctDesignator) {
		FctDesignator.struct = FctDesignator.getDesignator().obj.getType();
	}

	public void visit(FctPars FctPars) {
		FctPars.struct = methObj.getType();
		if (global) {
			report_info("Poziv globalne metode", FctPars);
		} else {
			report_info("Poziv unutrasnje metode", FctPars);
		}
		global = false;
	}

	public void visit(FctNewArr FctNewArr) {
		FctNewArr.struct = ExtendedTab.getArrayStruct(currentVarType);
		if (FctNewArr.getExpr().struct != ExtendedTab.intType)
			report_error("Greska, indeks nije celobrojnog tipa!", FctNewArr);
	}

	public void visit(FctBool FctBool) {
		FctBool.struct = ExtendedTab.boolType;
	}

	public void visit(FctChar FctChar) {
		FctChar.struct = ExtendedTab.charType;
	}

	public void visit(FctNum FctNum) {
		FctNum.struct = ExtendedTab.intType;
	}

	public void visit(FctParenExp FctParenExp) {
		FctParenExp.struct = FctParenExp.getExpr().struct;
	}

	public void visit(FctNew FctNew) {
		FctNew.struct = currentVarType.struct;
		if (currentVarType.struct == ExtendedTab.nullType) {
			report_info("Kreiran je nov objekat klase <" + currentVarType.getTypeName() + ">", FctNew);
		} else {
			report_error("Greska, tip <" + currentVarType.getTypeName() + "> nije klasni tip", FctNew);
		}
	}

	public void visit(FactorOne FactorOne) {
		FactorOne.struct = FactorOne.getFactor().struct;
	}

	public void visit(FactorMany FactorMany) {
		if (FactorMany.getFactorList().struct != null) {
			if (FactorMany.getFactorList().struct.compatibleWith(FactorMany.getFactor().struct)) {
				FactorMany.struct = FactorMany.getFactor().struct;
			} else {
				FactorMany.struct = ExtendedTab.noType;
				report_error("Greska, operatori se moraju slagati po tipu!", FactorMany);
			}
		}
	}

	public void visit(Num num) {
		if (currentVarType.struct.compatibleWith(ExtendedTab.intType)) {
			num.obj = ExtendedTab.insert(Obj.Con, num.getNumName(), currentVarType.struct);
			report_info("Deklarisana simbolicka konstanta <" + num.getNumName() + ">", num);
		} else {
			report_error(
					"Greska prilikom dodele vrednosti promenljivoj <" + num.getNumName() + ">, tipovi se ne poklapaju",
					num);
		}
	}

	public void visit(Char Char) {
		if (currentVarType.struct.compatibleWith(ExtendedTab.charType)) {
			Char.obj = ExtendedTab.insert(Obj.Con, Char.getCharName(), currentVarType.struct);
			report_info("Deklarisana simbolicka konstanta <" + Char.getCharName() + ">", Char);
		} else {
			report_error("Greska prilikom dodele vrednosti promenljivoj <" + Char.getCharName()
					+ ">, tipovi se ne poklapaju", Char);
		}
	}

	public void visit(Bool Bool) {
		if (currentVarType.struct.compatibleWith(ExtendedTab.boolType)) {
			Bool.obj = ExtendedTab.insert(Obj.Con, Bool.getBoolName(), currentVarType.struct);
			report_info("Deklarisana simbolicka konstanta <" + Bool.getBoolName() + ">", Bool);
		} else {
			report_error("Greska prilikom dodele vrednosti promenljivoj <" + Bool.getBoolName()
					+ ">, tipovi se ne poklapaju", Bool);
		}
	}

	public void visit(Array array) {
		Obj arrayNode = ExtendedTab.insert(Obj.Var, array.getArrayName(), ExtendedTab.getArrayStruct(currentVarType));
		report_info("Deklarisana globalna promenljiva (niz) <" + array.getArrayName() + ">", array);
	}

	public void visit(Var var) {
		Obj varNode = ExtendedTab.insert(Obj.Var, var.getVarName(), currentVarType.struct);
		report_info("Deklarisana globalna promenljiva <" + var.getVarName() + ">", var);
	}

	public void visit(ExtendingType ExtendingType) {
		if (!currentVarType.struct.compatibleWith(ExtendedTab.nullType)) {
			report_error("Klasa ne moze nasledjivati tipa <" + currentVarType.getTypeName() + ">, jer nije klasni",
					ExtendingType);
		}
	}

	public void visit(Type type) {
		Obj typeNode = ExtendedTab.find(type.getTypeName());
		if (typeNode == ExtendedTab.noObj) {
			report_error("Nije pronadjen tip <" + type.getTypeName() + "> u tabeli simbola", type);
			type.struct = ExtendedTab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime <" + type.getTypeName() + "> ne predstavlja tip ", type);
				type.struct = ExtendedTab.noType;
			}
		}
		currentVarType = type;
	}

	public void visit(EqualExpr EqualExpr) {
		EqualExpr.struct = EqualExpr.getExpr().struct;
	}

	public void visit(MinusDStmt MinusDStmt) {
		if (!array.isEmpty()) {
			Obj o = ExtendedTab.find(array.get(index - 1));
			if (o.getType().getElemType() != ExtendedTab.intType)
				report_error("Greska, tip promenljive koja se dekrementira mora biti int!", MinusDStmt);
		} else {
			Obj o = ExtendedTab.find(dsgnName);
			if (o.getType() != ExtendedTab.intType || o.getKind() != Obj.Var)
				report_error("Greska, tip promenljive koja se dekrementira mora biti int!", MinusDStmt);
			if (o.getKind() == Obj.Con)
				report_error("Greska, objekat ne sme biti konstanta!", MinusDStmt);
		}
	}

	public void visit(PlusDStmt PlusDStmt) {
		if (!array.isEmpty()) {
			Obj o = ExtendedTab.find(array.get(index - 1));
			if (o.getType().getElemType() != ExtendedTab.intType)
				report_error("Greska, tip promenljive koja se dekrementira mora biti int!", PlusDStmt);
		} else {
			Obj o = ExtendedTab.find(dsgnName);
			if (o.getType() != ExtendedTab.intType)
				report_error("Greska, tip promenljive koja se dekrementira mora biti int!", PlusDStmt);
			if (o.getKind() == Obj.Con)
				report_error("Greska, objekat ne sme biti konstanta!", PlusDStmt);
		}
	}

	public void visit(ParsDStmt ParsDStmt) {

		Collection<Obj> c = passedObj.getLocalSymbols();
		int i = 0;
		for (Obj x : c) {
			if (i < actPars.size())
				if (!x.getType().compatibleWith(actPars.get(i).struct))
					report_error("Greska, neadekvatan parametar", ParsDStmt);
			i++;
		}
	}

	public void visit(EqualDStmt EqualDStmt) {
		if (EqualDStmt.getDummyE().obj.getType().getKind() == Struct.None) {
			report_error("Greska, promenljiva kojoj se dodeljuje vrednost nije deklarisana", EqualDStmt);
		} else {
			if (!EqualDStmt.getDummyE().obj.getType().compatibleWith(EqualDStmt.getEqualExp().struct))
				report_error("Greska, nekompatibilni tipovi u izrazu dodele!", EqualDStmt);
		}
	}

	public void visit(StmtDsgnS StmtDsgnS) {
		if (index == 1)
			array.remove(--index);
	}

	public void visit(CondFactOr CondFactOr) {
		if (!CondFactOr.getExpr().struct.compatibleWith(CondFactOr.getExpr1().struct))
			report_error("Greska, izrazi moraju biti kompatibilni po tipu!", CondFactOr);
		if (CondFactOr.getExpr().struct.getKind() == Struct.Array
				|| CondFactOr.getExpr().struct.getKind() == Struct.Class)
			if (currRelOp == 2)
				report_error("Greska, uz klase i nizove mogu ici samo provere jednakosti!", CondFactOr);
		CondFactOr.struct = CondFactOr.getExpr().struct;
	}

	public void visit(CondTermList CondTermList) {
		if (CondTermList.getCondFact().struct != ExtendedTab.boolType
				|| CondTermList.getCondTerm().struct != ExtendedTab.boolType)
			report_error("Greska, neispravno zadat uslov", CondTermList);
		CondTermList.struct = ExtendedTab.boolType;
	}

	public void visit(CondList CondList) {
		if (CondList.getCondition().struct != ExtendedTab.boolType
				|| CondList.getCondTerm().struct != ExtendedTab.boolType)
			report_error("Greska, neispravno zadat uslov", CondList);
		CondList.struct = CondList.getCondition().struct;
	}

	public void visit(CondFactOne CondFactOne) {
		CondFactOne.struct = CondFactOne.getExpr().struct;
	}

	public void visit(CondTermOne CondTermOne) {
		CondTermOne.struct = CondTermOne.getCondFact().struct;
	}

	public void visit(CondOne CondOne) {
		CondOne.struct = CondOne.getCondTerm().struct;
	}

	public void visit(RelopLSE RelopLSE) {
		currRelOp = 2;
	}

	public void visit(RelopLS RelopLS) {
		currRelOp = 2;
	}

	public void visit(RelopGRE RelopGRE) {
		currRelOp = 2;
	}

	public void visit(RelopGR RelopGR) {
		currRelOp = 2;
	}

	public void visit(RelopNEQ RelopNEQ) {
		currRelOp = 1;
	}

	public void visit(RelopEQ RelopEQ) {
		currRelOp = 1;
	}

	public boolean passed() {
		return !errorDetected;
	}

}
