package opcodeanalyser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction implements Comparable<Instruction> {
	private int address;
	private String rawBytes;
	private Opcode opcode;
	private Operand operand1;
	private Operand operand2;
	private int operandCount;
	private String comment;
	public Instruction() {

	}
	public Instruction(int address) {
		super();
		this.address = address;	
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
	public Instruction(int address, String rawBytes, Opcode opcode, Operand operand1, Operand operand2,String comment) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operandCount = 2;
		this.comment=comment;
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
	public Opcode getOpcode() {
		return opcode;
	}
	public Operand getOperand1() {
		return operand1;
	}
	public Operand getOperand2() {
		return operand2;
	}
	public int getOperandCount() {
		return operandCount;
	}
	public String getComment() {
		return comment;
	}
	public boolean isControlTransfer() {
		return controlTransfer;
	}
	public boolean isMemoryRead() {
		return memoryRead;
	}
	public boolean isMemoryWrite() {
		return memoryWrite;
	}
	public boolean isRetTransfer() {
		return retTransfer;
	}
	public boolean isCallTransfer() {
		return callTransfer;
	}
	public boolean iscJumpTransfer() {
		return cJumpTransfer;
	}
	public boolean isUcJumpTransfer() {
		return ucJumpTransfer;
	}
	public boolean iscControlTransfer() {
		return cControlTransfer;
	}
	public boolean isUcControlTransfer() {
		return ucControlTransfer;
	}

	private boolean controlTransfer;
	private boolean retTransfer;	
	private boolean callTransfer;
	private boolean cJumpTransfer;
	private boolean ucJumpTransfer;
	private boolean cControlTransfer;
	private boolean ucControlTransfer;
	private boolean memoryRead;
	private boolean memoryWrite;	

	public void setControlTransfer() {

		controlTransfer = (Definitions.CJUMP_OPCODES.contains(opcode.getValue())
				|| Definitions.UCJUMP_OPCODES.contains(opcode.getValue())
				|| Definitions.CALL_OPCODES.contains(opcode.getValue())
				|| Definitions.RET_OPCODES.contains(opcode.getValue()));
		setRetTransfer();
		setCallTransfer();
		setCJumpTransfer();
		setUcJumpTransfer();
		setCControlTransfer();
		setUcControlTransfer();
	}
	public void setRetTransfer() {
		retTransfer = Definitions.RET_OPCODES.contains(opcode.getValue());
	}
	public void setCallTransfer() {
		callTransfer = Definitions.CALL_OPCODES.contains(opcode.getValue());
	}
	public void setCJumpTransfer() {
		cJumpTransfer = Definitions.CJUMP_OPCODES.contains(opcode.getValue());
	}
	public void setUcJumpTransfer() {
		ucJumpTransfer = Definitions.UCJUMP_OPCODES.contains(opcode.getValue());
	}
	public void setCControlTransfer() {
		cControlTransfer = Definitions.CJUMP_OPCODES.contains(opcode.getValue());
	}
	public void setUcControlTransfer() {
		ucControlTransfer = Definitions.UCCONTROL_OPCODES.contains(opcode.getValue());
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
			for (String feature : regex) {
				Pattern p = Pattern.compile(feature);
				Matcher m = p.matcher(operand1.getValue());
				if (m.matches()) {
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
			for (String feature : regex) {
				Pattern p = Pattern.compile(feature);
				Matcher m = p.matcher(operand2.getValue());
				if (m.matches()) {
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
	@Override
	public int compareTo(Instruction i) {		
		return Integer.compare(address, i.address);
		
	}
}
