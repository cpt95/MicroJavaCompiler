package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.Type;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class ExtendedTab extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);
	
	public static Struct getArrayStruct(Type t) {
		return new Struct(Struct.Array, t.struct);
	}
	
	public static void dump(boolean simple) {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		SymbolTableVisitor stv = new SimpleSymbolTableVisitor(simple);
		for (Scope s = currentScope; s != null; s = s.getOuter()) {
			s.accept(stv);
		}
		System.out.println(stv.getOutput());
	}
}
