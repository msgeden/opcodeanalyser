package opcodeanalyser;

import java.util.ArrayList;

public class Function {
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public ArrayList<Instruction> getBody() {
		return body;
	}
	public void setBody(ArrayList<Instruction> body) {
		this.body = body;
	}
	public int getStartAddress() {
		return startAddress;
	}
	public void setStartAddress(int startAddress) {
		this.startAddress = startAddress;
	}
	public int getEndAddress() {
		return endAddress;
	}
	public void setEndAddress(int endAddress) {
		this.endAddress = endAddress;
	}
	private String identifier;
	private ArrayList<Instruction> body; 
	private int startAddress;
	private int endAddress;
}
