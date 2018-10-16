package opcodeanalyser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	public static final String STATS_PATH = "statsPath";
	public static final String CPU_ARCHITECTURE = "architecture";
	
	public static final String ARC_CONDITIONAL_JUMPS = "conditionalJumps";
	public static final String ARC_UNCONDITIONAL_JUMPS = "unconditionalJumps";
	public static final String ARC_BRANCHES = "branches";
	public static final String ARC_RETURNS = "returns";
	public static final String ARC_DATA = "data";
	public static final String ARC_MEMORY_OPERANDS = "memoryOperands";
	
	public static final String COLUMN_CHAR = ":";
	public static final String EDGE_CHAR = ">";
	public static final String COMMA = ",";
	public static final String BLANK_CHAR = "\b";
	public static final String SEMICOLON_CHAR = ";";
	public static final String COLON_CHAR = ":";
	public static final String COMMA_CHAR = ",";
	public static final String TAB_CHAR = "\t";
	public static final String NEW_LINE = "\n";	
	public static final String LINUX_ELF = "bin";
	public static final String WINDOWS_ELF = "exe";
	public static final String[] LINUX_ELFS = new String[] { "axf", "bin", "elf", "o", "prx", "puff", "ko", "mod", "so"};

	public static final Set<String> CJUMP_OPCODES = new HashSet<String>(Arrays.asList(
		     new String[]{ "jeq","jz","jne","jnz","jc","jnc","jn","jge","jl"}
		));
	public static final Set<String> UCJUMP_OPCODES = new HashSet<String>(Arrays.asList(
		     new String[]{ "jmp"}
		));
	public static final Set<String> CALL_OPCODES = new HashSet<String>(Arrays.asList(
		     new String[]{ "call","br"}
		));
	public static final Set<String> RET_OPCODES = new HashSet<String>(Arrays.asList(
			 new String[]{ "reti","ret"}
		));
//	public static final String[] C_JUMPS = new String[] { "jeq","jz","jne","jnz","jc","jnc","jn","jge","jl"};
//	public static final String[] UC_JUMPS = new String[] { "jmp"};
//	public static final String[] CALLS = new String[] { "call","br"};
//	public static final String[] RETS = new String[] { "reti","ret"};
//	public static final String[] DATA = new String[] { "pop","push","mov"};
	public static final String FUNCTION_SPLITTER = "\n";
}
