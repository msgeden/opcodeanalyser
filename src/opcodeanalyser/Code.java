package opcodeanalyser;

public class Code {
	public int getControlCount() {
		return controlCount;
	}
	public int getConditionalJumpCount() {
		return conditionalJumpCount;
	}
	public int getUnconditionalJumpCount() {
		return unconditionalJumpCount;
	}
	public int getCallCount() {
		return callCount;
	}
	public int getRetCount() {
		return retCount;
	}
	public int getReadCount() {
		return readCount;
	}
	public int getWriteCount() {
		return writeCount;
	}
	public int getOtherCount() {
		return otherCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	protected int controlCount=0;
	protected int conditionalJumpCount=0;
	protected int unconditionalJumpCount=0;
	protected int callCount=0;
	protected int retCount=0;
	protected int readCount=0;
	protected int writeCount=0;
	protected int otherCount=0;
	protected int totalCount=0;
}
