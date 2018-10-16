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
		this.isControlTransfer = isControlTransfer();
		this.isMemoryRead = isMemoryRead();
		this.isMemoryWrite = isMemoryWrite();
	}

	public Instruction(int address, String rawBytes, Opcode opcode, Operand operand1) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operandCount = 1;
		this.isControlTransfer = isControlTransfer();
		this.isMemoryRead = isMemoryRead();
		this.isMemoryWrite = isMemoryWrite();
	}

	public Instruction(int address, String rawBytes, Opcode opcode, Operand operand1, Operand operand2) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operandCount = 2;
		this.isControlTransfer = isControlTransfer();
		this.isMemoryRead = isMemoryRead();
		this.isMemoryWrite = isMemoryWrite();
	}

	private int address;
	private String rawBytes;
	private Opcode opcode;
	private Operand operand1;
	private Operand operand2;
	private int operandCount;
	private boolean isControlTransfer;
	private boolean isMemoryWrite;
	private boolean isMemoryRead;

	public boolean isControlTransfer() {

		return (Definitions.CJUMP_OPCODES.contains(opcode.getValue())
				|| Definitions.UCJUMP_OPCODES.contains(opcode.getValue())
				|| Definitions.CALL_OPCODES.contains(opcode.getValue())
				|| Definitions.RET_OPCODES.contains(opcode.getValue()));
	}

	public boolean isMemoryRead() {
		if (operandCount == 0) {
			if (Definitions.RET_OPCODES.contains(opcode.getValue()))
				return true;
			else
				return false;
		} else if (operandCount == 1) {
			if (opcode.getValue().equals("pop"))
				return true;
			else
				return false;

		} else {
			String regex[] = FileHandler.readArchitectureValue(Definitions.ARC_MEMORY_OPERANDS)
					.split(Definitions.COMMA);
			boolean found = false;
			for (String feature : regex) {
				Pattern p = Pattern.compile(feature);
				Matcher m = p.matcher(operand1.getValue());
				if (m.matches()) {
					System.out.println("R:"+printInstruction());
					found = true;
				}
			}
			return found;
		}
	}

	public boolean isMemoryWrite() {
		if (operandCount == 0)
			return false;
		else if (operandCount == 1) {
			if (opcode.getValue().equals("push"))
				return true;
			else
				return false;
		} else {
			String regex[] = FileHandler.readArchitectureValue(Definitions.ARC_MEMORY_OPERANDS)
					.split(Definitions.COMMA);
			boolean found = false;
			for (String feature : regex) {
				Pattern p = Pattern.compile(feature);
				Matcher m = p.matcher(operand2.getValue());
				if (m.matches()) {
					System.out.println("W:"+printInstruction());
					found = true;
				}
			}
			return found;
		}
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
