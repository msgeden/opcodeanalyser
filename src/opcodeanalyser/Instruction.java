package opcodeanalyser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {

	public Instruction() {

	}
	public Instruction(int address, String rawBytes, Opcode opcode) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operandCount = 0;
		setControlTransfer();
		setMemoryRead();
		setMemoryWrite();
	}

	public Instruction(int address, String rawBytes, Opcode opcode, Operand operand1) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operandCount = 1;
		setControlTransfer();
		setMemoryRead();
		setMemoryWrite();
	}

	public Instruction(int address, String rawBytes, Opcode opcode, Operand operand1, Operand operand2) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operandCount = 2;
		setControlTransfer();
		setMemoryRead();
		setMemoryWrite();
	}

	public int getAddress() {
		return address;
	}
	public String getRawBytes() {
		return rawBytes;
	}

	private int address;
	private String rawBytes;
	private Opcode opcode;
	private Operand operand1;
	private Operand operand2;
	private int operandCount;
	public boolean isControlTransfer() {
		return controlTransfer;
	}
	public boolean isMemoryRead() {
		return memoryRead;
	}
	public boolean isMemoryWrite() {
		return memoryWrite;
	}

	private boolean controlTransfer;
	private boolean memoryRead;
	private boolean memoryWrite;	

	public void setControlTransfer() {

		controlTransfer = (Definitions.CJUMP_OPCODES.contains(opcode.getValue())
				|| Definitions.UCJUMP_OPCODES.contains(opcode.getValue())
				|| Definitions.CALL_OPCODES.contains(opcode.getValue())
				|| Definitions.RET_OPCODES.contains(opcode.getValue()));
	}

	public void setMemoryRead() {
		boolean found = false;
		if (operandCount == 0) {
			if (Definitions.RET_OPCODES.contains(opcode.getValue()))
				found=true;			
		} else if (operandCount == 1) {
			if (opcode.getValue().equals("pop"))
				found=true;			
		} else {
			String regex[] = FileHandler.readArchitectureValue(Definitions.ARC_MEMORY_OPERANDS)
					.split(Definitions.COMMA);
			//String regex[] = new String[] {"^&.+","^@r.+",".+(r.+)"};
					//.split(Definitions.COMMA);			
			for (String feature : regex) {
				Pattern p = Pattern.compile(feature);
				Matcher m = p.matcher(operand1.getValue());
				if (m.matches()) {
					System.out.println("R:"+printInstruction());
					found = true;
				}
			}			
		}
		memoryRead=found;
	}

	public void setMemoryWrite() {
		boolean found = false;
		if (operandCount == 1) {
			if (opcode.getValue().equals("push"))
				found=true;
		
		} else if (operandCount == 2){
			String regex[] = FileHandler.readArchitectureValue(Definitions.ARC_MEMORY_OPERANDS)
					.split(Definitions.COMMA);
			//String regex[] = new String[] {"^&.+","^@r.+",".+(r.+)"};			
			for (String feature : regex) {
				Pattern p = Pattern.compile(feature);
				Matcher m = p.matcher(operand2.getValue());
				if (m.matches()) {
					System.out.println("W:"+printInstruction());
					found = true;
				}
			}			
		}
		memoryWrite=found;
	}

	public String printInstruction() {
		switch (operandCount) {
		case 0:
			return opcode.getValue();
		case 1:
			return opcode.getValue() + Definitions.TAB_CHAR + operand1.getValue();
		case 2:
			return opcode.getValue() + Definitions.TAB_CHAR + operand1.getValue() + Definitions.COMMA
					+ operand2.getValue();
		default:
			return opcode.getValue();
		}
	}
}
