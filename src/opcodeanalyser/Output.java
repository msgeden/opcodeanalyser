package opcodeanalyser;

import java.util.ArrayList;

public class Output {
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
	public ArrayList<Function> getCode() {
		return code;
	}
	public void setCode(ArrayList<Function> code) {
		this.code = code;
	}
	public ArrayList<String> getInterrupts() {
		return interrupts;
	}
	public void setInterrupts(ArrayList<String> interrupts) {
		this.interrupts = interrupts;
	}
	private String fileName;
	private String fileFormat;
	private ArrayList<Function> code; 
	private ArrayList<String> interrupts;
}
