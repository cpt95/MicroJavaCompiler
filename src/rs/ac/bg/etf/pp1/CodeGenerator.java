package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.AddopM;
import rs.ac.bg.etf.pp1.ast.AddopP;
import rs.ac.bg.etf.pp1.ast.ArrayDsgn;
import rs.ac.bg.etf.pp1.ast.Bool;
import rs.ac.bg.etf.pp1.ast.Char;
import rs.ac.bg.etf.pp1.ast.DummyE;
import rs.ac.bg.etf.pp1.ast.EqualDStmt;
import rs.ac.bg.etf.pp1.ast.ExprM;
import rs.ac.bg.etf.pp1.ast.FactorMany;
import rs.ac.bg.etf.pp1.ast.FctBool;
import rs.ac.bg.etf.pp1.ast.FctChar;
import rs.ac.bg.etf.pp1.ast.FctDesignator;
import rs.ac.bg.etf.pp1.ast.FctNewArr;
import rs.ac.bg.etf.pp1.ast.FctNum;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MinusDStmt;
import rs.ac.bg.etf.pp1.ast.MulopD;
import rs.ac.bg.etf.pp1.ast.MulopM;
import rs.ac.bg.etf.pp1.ast.MulopP;
import rs.ac.bg.etf.pp1.ast.Num;
import rs.ac.bg.etf.pp1.ast.OneDsgn;
import rs.ac.bg.etf.pp1.ast.ParsDStmt;
import rs.ac.bg.etf.pp1.ast.PlusDStmt;
import rs.ac.bg.etf.pp1.ast.ReturnType;
import rs.ac.bg.etf.pp1.ast.StmtDsgn;
import rs.ac.bg.etf.pp1.ast.StmtExp;
import rs.ac.bg.etf.pp1.ast.StmtExpNum;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermMany;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.VoidType;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

	private int arrayPc, mainPc;
	private int mulIndex = 0, addIndex = 0;
	private boolean isArray = false;
	private List<Integer> mul = new ArrayList<>();
	private List<Integer> add = new ArrayList<>();

	public int getMainPc() {
		return mainPc;
	}

	public void visit(VoidType VoidType) {
		if ("main".equalsIgnoreCase(VoidType.getVoidName())) {
			mainPc = Code.pc;
		}
		VoidType.obj.setAdr(Code.pc);
		// Collect arguments and local variables.
		SyntaxNode methodNode = VoidType.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}

	public void visit(ReturnType ReturnType) {
		if ("main".equalsIgnoreCase(ReturnType.getMethName())) {
			mainPc = Code.pc;
		}
		ReturnType.obj.setAdr(Code.pc);

		// Collect arguments and local variables.
		SyntaxNode methodNode = ReturnType.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);

		// Generate the entry.
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}

	public void visit(MethodDecl MethodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ParsDStmt ParsDStmt) {
		int dst = ParsDStmt.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(dst);
		if (ParsDStmt.getDesignator().obj.getType() != ExtendedTab.noType)
			Code.put(Code.pop);
	}

	public void visit(EqualDStmt EqualDStmt) {
		Code.store(EqualDStmt.getDummyE().obj);
	}

	public void visit(MinusDStmt MinusDStmt) {
		if (isArray)
			Code.put(Code.aload);
		Code.put(Code.const_1);
		Code.put(Code.sub);
		Code.store(MinusDStmt.getDesignator().obj);
	}

	public void visit(PlusDStmt PlusDStmt) {
		if (isArray)
			Code.put(Code.aload);
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(PlusDStmt.getDesignator().obj);
	}

	public void visit(DummyE DummyE) {
		DummyE.obj = DummyE.getDesignator().obj;
	}

	public void visit(OneDsgn OneDsgn) {
		SyntaxNode parent = OneDsgn.getParent();
		if (PlusDStmt.class == parent.getClass() || MinusDStmt.class == parent.getClass()
				|| ArrayDsgn.class == parent.getClass()) {
			if (FctDesignator.class != parent.getClass())
				arrayPc = Code.pc;
			Code.load(OneDsgn.obj);
		}
	}

	public void visit(ArrayDsgn ArrayDsgn) {
		isArray = true;
		Obj o = ArrayDsgn.getDesignator().obj;
		int x = Code.pc;
		SyntaxNode parent = ArrayDsgn.getParent();
		if (parent.getClass().equals(PlusDStmt.class) || parent.getClass().equals(MinusDStmt.class))
			for (int i = arrayPc; i < x; i++)
				Code.put(Code.get(i));
	}

	public void visit(StmtDsgn StmtDsgn) {
		Code.put(Code.read);
		Code.store(StmtDsgn.getDesignator().obj);
	}

	public void visit(StmtExpNum StmtExpNum) {
		Code.loadConst(StmtExpNum.getI());
		if(StmtExpNum.getExpr().struct.equals(ExtendedTab.intType) 
				|| StmtExpNum.getExpr().struct.equals(ExtendedTab.boolType))
			Code.put(Code.print);
		else
			Code.put(Code.bprint);
	}

	public void visit(StmtExp StmtExp) {
		Code.put(Code.const_5);
		if(StmtExp.getExpr().struct.equals(ExtendedTab.intType) 
				|| StmtExp.getExpr().struct.equals(ExtendedTab.boolType))
			Code.put(Code.print);
		else
			Code.put(Code.bprint);
	}

	public void visit(FctNewArr FctNewArr) {
		Code.put(Code.newarray);
		if (FctNewArr.getType().struct != ExtendedTab.charType)
			Code.put(1);
		else
			Code.put(0);
	}

	public void visit(FctNum FctNum) {
		Code.load(new Obj(Obj.Con, "$", FctNum.struct, FctNum.getN1(), 0));
	}

	public void visit(FctBool FctBool) {
		if (FctBool.getB1())
			Code.load(new Obj(Obj.Con, "$", FctBool.struct, 1, 0));
		else
			Code.load(new Obj(Obj.Con, "$", FctBool.struct, 0, 0));
	}

	public void visit(FctChar FctChar) {
		Code.load(new Obj(Obj.Con, "$", FctChar.struct, FctChar.getC1(), 0));
	}

	public void visit(FctDesignator FctDesignator) {
		Code.load(FctDesignator.getDesignator().obj);
	}

	public void visit(Bool Bool) {
		if (Bool.getBool())
			Bool.obj.setAdr(1);
		else
			Bool.obj.setAdr(0);
	}

	public void visit(Char Char) {
		Char.obj.setAdr(Char.getChr());
	}

	public void visit(Num Num) {
		Num.obj.setAdr(Num.getNum());
	}

	public void visit(FactorMany FactorMany) {
		int currMul = mul.remove(--mulIndex);
		switch (currMul) {
		case 1:
			Code.put(Code.rem);
			break;
		case 2:
			Code.put(Code.div);
			break;
		case 3:
			Code.put(Code.mul);
			break;
		}
	}

	public void visit(TermMany TermMany) {
		int currAdd = add.remove(--addIndex);
		switch (currAdd) {
		case 1:
			Code.put(Code.sub);
			break;
		case 2:
			Code.put(Code.add);
			break;
		}
	}

	public void visit(ExprM ExprM) {
		Code.put(Code.neg);
	}

	public void visit(MulopP MulopP) {
		mul.add(1);
		mulIndex++;
	}

	public void visit(MulopD MulopD) {
		mul.add(2);
		mulIndex++;
	}

	public void visit(MulopM MulopM) {
		mul.add(3);
		mulIndex++;
	}

	public void visit(AddopM AddopM) {
		add.add(1);
		addIndex++;
	}

	public void visit(AddopP AddopP) {
		add.add(2);
		addIndex++;
	}

}
