package opcodeanalyser;

import java.util.ArrayList;

public class Output extends Code{
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileFormat() {
		return fileFormat;
	}
	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}
	public ArrayList<Block> getFunctions() {
		return functions;
	}
	public void setFunctions(ArrayList<Block> functions) {
		this.functions = functions;
	}
	public ArrayList<String> getInterrupts() {
		return interrupts;
	}
	public void setInterrupts(ArrayList<String> interrupts) {
		this.interrupts = interrupts;
	}
	public void countInstructions()
	{
		for (Block function:functions)
		{
			super.controlCount+=function.getControlCount();
			super.conditionalControlCount+=function.getConditionalControlCount();
			super.unconditionalControlCount+=function.getUnconditionalControlCount();
			super.readCount+=function.getReadCount();
			super.writeCount+=function.getWriteCount();
			super.otherCount+=function.getOtherCount();
			super.totalCount+=function.getTotalCount();
		}
	}
	private String fileName;
	private String fileFormat;
	private ArrayList<Block> functions; 
	private ArrayList<String> interrupts;	
}
