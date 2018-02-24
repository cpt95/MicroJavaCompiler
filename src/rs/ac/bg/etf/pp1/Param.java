package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.Type;

public class Param {
	private String name;
	private Type type;
	private boolean array;
	
	public Param(String name, Type type, boolean array) {
		super();
		this.name = name;
		this.type = type;
		this.array = array;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public boolean isArray() {
		return array;
	}
	public void setArray(boolean array) {
		this.array = array;
	}
	
	
}
