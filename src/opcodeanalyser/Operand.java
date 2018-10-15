package opcodeanalyser;

public class Operand {
	enum Type {
		Immediate,Register,Memory;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private Type type;
	private String value;	
	public Operand(String value) {
		this.value = value;
	}
	public Operand() {
		// TODO Auto-generated constructor stub
	}
		
}
