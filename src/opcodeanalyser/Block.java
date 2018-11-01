package opcodeanalyser;

import java.util.ArrayList;

public class Block extends Code {
	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String functionName) {
		this.blockName = functionName;
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

	public void countInstructions() {
		for (Instruction instruction : instructions) {
			super.totalCount++;
			if (instruction.isControlTransfer()) {
				super.controlCount++;
				if (instruction.iscControlTransfer())
					super.conditionalControlCount++;
				else if (instruction.isUcControlTransfer())
					super.unconditionalControlCount++;
			} 
			else if (instruction.isMemoryRead())
				super.readCount++;
			else if (instruction.isMemoryWrite())
				super.writeCount++;
			else
				super.otherCount++;
		}
	}

	private String blockName;
	private ArrayList<Instruction> instructions;
	private int startAddress;
	private int endAddress;
}
