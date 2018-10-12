package opcodeanalyser;

public class Function {
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public Instruction[] getBody() {
		return body;
	}
	public void setBody(Instruction[] body) {
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
	public Function(String identifier, Instruction[] body, int startAddress, int endAddress) {
		super();
		this.identifier = identifier;
		this.body = body;
		this.startAddress = startAddress;
		this.endAddress = endAddress;
	}
	private String identifier;
	private Instruction[] body; 
	private int startAddress;
	private int endAddress;
}
