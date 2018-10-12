package opcodeanalyser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
*
* @author msgeden
*/
public class Definitions {
	
	public static final String BINARY_PATH = "binaryPath";
	public static final String DISSASSEMBLED_PATH = "disassembledPath";
	public static final String COLUMN_CHAR = ":";
	public static final String EDGE_CHAR = ">";
	public static final String COMMA = ",";
	public static final String BLANK_CHAR = "\b";
	public static final String COMMA_CHAR = ",";
	public static final String TAB_CHAR = "\t";
	public static final String NEW_LINE = "\n";	
	public static final String LINUX_ELF = "bin";
	public static final String WINDOWS_ELF = "exe";
	public static final String[] LINUX_ELFS = new String[] { "axf", "bin", "elf", "o", "prx", "puff", "ko", "mod", "so"};

	public static final String[] JUMPS = new String[] { "JEQ","JZ","JNE","JNZ","JC","JNC","JN","JGE","JL","JMP"};
	public static final String[] CALLS = new String[] { "CALL","BR"};
	public static final String[] RETS = new String[] { "RETI","RET"};
	public static final String[] DATA = new String[] { "POP","PUSH"};
	public static final String FUNCTION_SPLITTER = "\n";
}
