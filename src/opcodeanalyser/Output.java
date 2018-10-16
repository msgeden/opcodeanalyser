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
	public ArrayList<Function> getFunctions() {
		return functions;
	}
	public void setFunctions(ArrayList<Function> functions) {
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
		for (Function function:functions)
		{
			super.controlCount+=function.getControlCount();
			super.readCount+=function.getReadCount();
			super.writeCount+=function.getWriteCount();
			super.otherCount+=function.getOtherCount();
		}
	}
	private String fileName;
	private String fileFormat;
	private ArrayList<Function> functions; 
	private ArrayList<String> interrupts;
}
