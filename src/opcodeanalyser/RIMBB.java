package opcodeanalyser;

public class RIMBB{
	public int startAddress=-1;
	public int endAddress=-1;
	public boolean isData=false;
	public boolean isWrite=false;//false:Read, true:Write
	public short cellID=-1;
	public int outgoing1=-1;
	public int outgoing2=-1;
}
