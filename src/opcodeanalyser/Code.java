package opcodeanalyser;

public class Code {
	public int getControlCount() {
		return controlCount;
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
	protected int controlCount=0;
	protected int readCount=0;
	protected int writeCount=0;
	protected int otherCount=0;
}
