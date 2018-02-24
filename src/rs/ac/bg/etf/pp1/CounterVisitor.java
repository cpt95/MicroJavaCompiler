package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormArray;
import rs.ac.bg.etf.pp1.ast.FormVar;
import rs.ac.bg.etf.pp1.ast.MthdArray;
import rs.ac.bg.etf.pp1.ast.MthdVar;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {

	protected int count;

	public int getCount() {
		return count;
	}

	public static class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(FormVar FormVar) {
			count++;
		}

		@Override
		public void visit(FormArray FormArray) {
			count++;
		}
	}

	public static class VarCounter extends CounterVisitor {

		@Override
		public void visit(MthdVar MthdVar) {
			count++;
		}

		@Override
		public void visit(MthdArray MthdArray) {
			count++;
		}

	}
}