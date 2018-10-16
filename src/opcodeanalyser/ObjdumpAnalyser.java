package opcodeanalyser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;


public class ObjdumpAnalyser {
	public static int printStatistics(Output objectFile) throws IOException {
		
		int count=0;
		File instructionStatsFile = new File(FileHandler.readConfigValue(Definitions.STATS_PATH)
				+ "memory_control_"+objectFile.getFileName()+".tsv");
		
		FileUtils.write(instructionStatsFile,"FUNCTION" + Definitions.TAB_CHAR 
				+ "CONTROL_TRANSFERS"+Definitions.TAB_CHAR+"MEMORY_READ"+Definitions.TAB_CHAR+"MEMORY_WRITE"+Definitions.TAB_CHAR+"OTHER",Charset.defaultCharset(),true);
		System.out.print("FUNCTION" + Definitions.TAB_CHAR 
				+ "CONTROL_TRANSFERS"+Definitions.TAB_CHAR+"MEMORY_READ"+Definitions.TAB_CHAR+"MEMORY_WRITE"+Definitions.TAB_CHAR+"OTHER");
	
		for (Function function:objectFile.getFunctions())
		{
			function.countInstructions();
			FileUtils.write(instructionStatsFile,
					Definitions.NEW_LINE + function.getFunctionName() + Definitions.TAB_CHAR +
					function.getControlCount()+Definitions.TAB_CHAR+function.getReadCount()+Definitions.TAB_CHAR+function.getWriteCount()+Definitions.TAB_CHAR+function.getOtherCount(),Charset.defaultCharset(),true);
			System.out.print(
					Definitions.NEW_LINE + function.getFunctionName() + Definitions.TAB_CHAR +
					function.getControlCount()+Definitions.TAB_CHAR+function.getReadCount()+Definitions.TAB_CHAR+function.getWriteCount()+Definitions.TAB_CHAR+function.getOtherCount());
		
		}
		objectFile.countInstructions();
		FileUtils.write(instructionStatsFile,
				Definitions.NEW_LINE + objectFile.getFileName() + Definitions.TAB_CHAR +
				objectFile.getControlCount()+Definitions.TAB_CHAR+objectFile.getReadCount()+Definitions.TAB_CHAR+objectFile.getWriteCount()+Definitions.TAB_CHAR+objectFile.getOtherCount(),Charset.defaultCharset(),true);
		
		return count;
	}
	public int getControlInstructionCount() {
		int count=0;
		return count;
	}

}
