package opcodeanalyser;

public class Opcode {
	enum Type {
		Data,ALU,Control;
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
	public int getNumberOfOperands() {
		return numberOfOperands;
	}
	public void setNumberOfOperands(int numberOfOperands) {
		this.numberOfOperands = numberOfOperands;
	}
	public Opcode(String value) {
		this.value = value;		
	}
	private Type type;
	private String value;
	private int numberOfOperands;
}
