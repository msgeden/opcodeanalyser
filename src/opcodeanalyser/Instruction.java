package opcodeanalyser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//movl    -8(%ebp, %edx, 4), %eax  # Full example: load *(ebp + (edx * 4) - 8) into eax
//movl    -4(%ebp), %eax           # Typical example: load a stack variable into eax
//movl    (%ecx), %edx             # No index: copy the target of a pointer into a register
//leal    8(,%eax,4), %eax         # Arithmetic: multiply eax by 4 and add 8
//leal    (%edx,%eax,2), %eax      # Arithmetic: multiply eax by 2 and add edx
public class Instruction {
	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getRawBytes() {
		return rawBytes;
	}

	public void setRawBytes(String rawBytes) {
		this.rawBytes = rawBytes;
	}

	public Opcode getOpcode() {
		return opcode;
	}

	public void setOpcode(Opcode opcode) {
		this.opcode = opcode;
	}

	public Operand getOperand1() {
		return operand1;
	}

	public void setOperand1(Operand operand1) {
		this.operand1 = operand1;
	}

	public Operand getOperand2() {
		return operand2;
	}

	public void setOperand2(Operand operand2) {
		this.operand2 = operand2;
	}

	public boolean isControlTransfer() {

		return (Definitions.CJUMP_OPCODES.contains(this.opcode.getValue())
				|| Definitions.UCJUMP_OPCODES.contains(this.opcode.getValue())
				|| Definitions.CALL_OPCODES.contains(this.opcode.getValue())
				|| Definitions.RET_OPCODES.contains(this.opcode.getValue()));
	}

	public boolean isMemoryRead() {
		if (operand1==null)
			return false;
		else if (opcode.getValue().equals("pop"))
			return true;
		else {
		String regex[] = new String[] { ".+\\(r.+\\)", "^@r.+", "^&.+" };
		boolean found = false;
		for (String feature : regex) {
			Pattern p = Pattern.compile(feature);
			Matcher m = p.matcher(operand1.getValue());
			if (m.matches())
			{
				System.out.println("R:"+address+opcode.getValue() +"\t"+operand1.getValue());
				found=true;
			}			
		}
		return found;
		}
	}

	public boolean isMemoryWrite() {
		if (operand2==null)
			return false;
		else if (opcode.getValue().equals("pop"))
			return true;
		else {
		String regex[] = new String[] { ".+\\(r.+\\)", "^@r.+", "^&.+" };
		boolean found = false;
		for (String feature : regex) {
			Pattern p = Pattern.compile(feature);
			Matcher m = p.matcher(operand2.getValue());
			if (m.matches())
			{
				System.out.println("W:"+address+opcode.getValue() +"\t"+operand1.getValue());
				found=true;
			}			
		}
		return found;
		}
	}

	public void setControlTransfer(boolean isControlTransfer) {
		this.isControlTransfer = isControlTransfer;
	}

	public Instruction() {

	}

	public Instruction(int address, String rawBytes, Opcode opcode, Operand operand1, Operand operand2) {
		super();
		this.address = address;
		this.rawBytes = rawBytes;
		this.opcode = opcode;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	private int address;
	private String rawBytes;
	private Opcode opcode;
	private Operand operand1;
	private Operand operand2;
	private boolean isControlTransfer;
	private boolean isMemoryWrite;
	private boolean isMemoryRead;
}
