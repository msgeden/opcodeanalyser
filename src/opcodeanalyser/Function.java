package opcodeanalyser;

import java.util.ArrayList;

public class Function {
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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
	public Function() {		
	}
	
	public int getInstructionCount() {
		return instructionCount;
	}
	public void setInstructionCount(int instructionCount) {
		this.instructionCount = instructionCount;
	}
	public ArrayList<Instruction> getBody() {
		return body;
	}
	public void setBody(ArrayList<Instruction> body) {
		this.body = body;
	}
	private String identifier;
	private ArrayList<Instruction> body; 
	private int startAddress;
	private int endAddress;
	private int instructionCount;
	
	public int getMemoryInstructionCount() {
		int count=0;
		for (Instruction instruction:body) {
			if (instruction.isMemoryWrite())
				count++;
			
		}
		return count;
	}
	public int getControlInstructionCount() {
		int count=0;
		for (Instruction instruction:body)
			if (instruction.isControlTransfer())
				count++;
		return count;
	}
}
