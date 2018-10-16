package opcodeanalyser;

import java.util.ArrayList;

public class Function extends Code{
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public ArrayList<Instruction> getInstructions() {
		return instructions;
	}
	public void setInstructions(ArrayList<Instruction> instructions) {
		this.instructions = instructions;
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
	public void countInstructions()
	{
		for (Instruction instruction:instructions)
		{
			if (instruction.isControlTransfer())
				super.controlCount++;
			else if (instruction.isMemoryRead())
				super.readCount++;
			else if (instruction.isMemoryWrite())
				super.readCount++;
			else
				super.otherCount++;
		}
	}
	private String functionName;
	private ArrayList<Instruction> instructions; 
	private int startAddress;
	private int endAddress;
	
}
