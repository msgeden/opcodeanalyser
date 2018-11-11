package opcodeanalyser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class BinaryAnalyser {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Collection<File> files = FileHandler.findFiles(FileHandler.readConfigValue(Definitions.DISSASSEMBLED_PATH),
				new String[] { "txt" });
		//String filePath = FileHandler.readConfigValue(Definitions.DISSASSEMBLED_PATH) + "main.o.txt";
		//ArrayList<Instruction> instructions=FileHandler.parseObjectFileAsInstructionList(filePath);
		File totalStatsFile = new File(FileHandler.readConfigValue(Definitions.STATS_PATH) + "total_memory_control.tsv");
		FileUtils.write(totalStatsFile,
				"\nModule" + Definitions.TAB_CHAR + "Cjump" 
				+Definitions.TAB_CHAR + "Ujump"
				+Definitions.TAB_CHAR + "Call"
				+Definitions.TAB_CHAR + "Ret"
						+ Definitions.TAB_CHAR + "Memory Read"
						+ Definitions.TAB_CHAR + "Memory Write" + Definitions.TAB_CHAR + "Other" + Definitions.TAB_CHAR
						+ "Total"
						+ Definitions.TAB_CHAR + "Cjump Ratio"
						+ Definitions.TAB_CHAR + "Ujump Ratio"
						+ Definitions.TAB_CHAR + "Call Ratio"
						+ Definitions.TAB_CHAR + "Ret Ratio"
						+ Definitions.TAB_CHAR + "Read Ratio"+ Definitions.TAB_CHAR + "Write Ratio"+ Definitions.TAB_CHAR + "Other Ratio",
				Charset.defaultCharset(), true);
		for (File file:files)
		{
			String filePath = file.getAbsolutePath();
			System.out.println(filePath);
			Output objectFile=FileHandler.parseObjectFile(filePath);
			ObjdumpAnalyser.printStatistics(objectFile);
			ObjdumpAnalyser.printOpcodeFrequencies(objectFile);
		}
	}
}
//0000d23a <.Loc.178.1>:
//    d23a:	1c 42 08 03 	mov	&0x0308,r12	;0x0308
//    d252:	2c 41       	mov	@r1,	r12	;
//    d254:	1d 41 02 00 	mov	2(r1),	r13	;
//    d258:	3e 40 e8 03 	mov	#1000,	r14	;#0x03e8
//    d25e:	b0 12 de d8 	call	#55518		;#0xd8de
//    d26e:	81 4e 12 00 	mov	r14,	18(r1)	; 0x0012
//
//0000d276 <.Loc.181.1>:
//    d284:	b0 12 ec d8 	call	#55532		;#0xd8ec