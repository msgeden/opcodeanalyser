package opcodeanalyser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class ObjdumpAnalyser {
	public static void printStatistics(Output objectFile) throws IOException {

		File totalStatsFile = new File(FileHandler.readConfigValue(Definitions.STATS_PATH) + "total_memory_control.tsv");
		
		File instructionStatsFile = new File(FileHandler.readConfigValue(Definitions.STATS_PATH) + "memory_control_"
				+ objectFile.getFileName().replaceAll("txt", "tsv"));
		FileUtils.deleteQuietly(instructionStatsFile);
		FileUtils.write(instructionStatsFile,
				"FUNCTION" + Definitions.TAB_CHAR + "CONTROL_TRANSFERS" + Definitions.TAB_CHAR + "MEMORY_READ"
						+ Definitions.TAB_CHAR + "MEMORY_WRITE" + Definitions.TAB_CHAR + "OTHER" + Definitions.TAB_CHAR
						+ "TOTAL",
				Charset.defaultCharset(), true);
		System.out.print("FUNCTION" + Definitions.TAB_CHAR + "CONTROL_TRANSFERS" + Definitions.TAB_CHAR + "MEMORY_READ"
				+ Definitions.TAB_CHAR + "MEMORY_WRITE" + Definitions.TAB_CHAR + "OTHER" + Definitions.TAB_CHAR
				+ "TOTAL");

		for (Block block : objectFile.getFunctions()) {
			block.countInstructions();
			FileUtils.write(instructionStatsFile,
					Definitions.NEW_LINE + block.getBlockName() + Definitions.TAB_CHAR + block.getControlCount()
							+ Definitions.TAB_CHAR + block.getReadCount() + Definitions.TAB_CHAR + block.getWriteCount()
							+ Definitions.TAB_CHAR + block.getOtherCount() + Definitions.TAB_CHAR
							+ block.getTotalCount(),
					Charset.defaultCharset(), true);
			System.out.print(Definitions.NEW_LINE + block.getBlockName() + Definitions.TAB_CHAR
					+ block.getControlCount() + Definitions.TAB_CHAR + block.getReadCount() + Definitions.TAB_CHAR
					+ block.getWriteCount() + Definitions.TAB_CHAR + block.getOtherCount() + Definitions.TAB_CHAR
					+ block.getTotalCount());

		}
		objectFile.countInstructions();
		FileUtils.write(instructionStatsFile,
				Definitions.NEW_LINE + objectFile.getFileName() + Definitions.TAB_CHAR + objectFile.getControlCount()
						+ Definitions.TAB_CHAR + objectFile.getReadCount() + Definitions.TAB_CHAR
						+ objectFile.getWriteCount() + Definitions.TAB_CHAR + objectFile.getOtherCount()
						+ Definitions.TAB_CHAR + objectFile.getTotalCount(),
				Charset.defaultCharset(), true);
		System.out.print(Definitions.NEW_LINE + objectFile.getFileName() + Definitions.TAB_CHAR
				+ objectFile.getControlCount() + Definitions.TAB_CHAR + objectFile.getReadCount() + Definitions.TAB_CHAR
				+ objectFile.getWriteCount() + Definitions.TAB_CHAR + objectFile.getOtherCount() + Definitions.TAB_CHAR
				+ objectFile.getTotalCount());
		FileUtils.write(totalStatsFile,
				Definitions.NEW_LINE + objectFile.getFileName() + Definitions.TAB_CHAR + objectFile.getControlCount()
						+ Definitions.TAB_CHAR + objectFile.getReadCount() + Definitions.TAB_CHAR
						+ objectFile.getWriteCount() + Definitions.TAB_CHAR + objectFile.getOtherCount()
						+ Definitions.TAB_CHAR + objectFile.getTotalCount()+ Definitions.TAB_CHAR+(float)objectFile.getControlCount()/objectFile.getTotalCount()+ Definitions.TAB_CHAR+(float)objectFile.getReadCount()/objectFile.getTotalCount()+ Definitions.TAB_CHAR+(float)objectFile.getWriteCount()/objectFile.getTotalCount()+ Definitions.TAB_CHAR+(float)objectFile.getOtherCount()/objectFile.getTotalCount(),
				Charset.defaultCharset(), true);
	}

	public static void printOpcodeFrequencies(Output objectFile) throws IOException {

		File opcodeFrequenciesFile = new File(FileHandler.readConfigValue(Definitions.STATS_PATH)
				+ "opcode_frequencies_" + objectFile.getFileName().replaceAll("txt", "tsv"));
		FileUtils.deleteQuietly(opcodeFrequenciesFile);
		FileUtils.write(opcodeFrequenciesFile, "\nOPCODE" + Definitions.TAB_CHAR + "COUNT", Charset.defaultCharset(),
				true);
		System.out.print("\nOPCODE" + Definitions.TAB_CHAR + "COUNT");
		HashMap<String, Integer> opcodeFrequencies = new HashMap<String, Integer>();
		for (Block block : objectFile.getFunctions()) {
			for (Instruction instruction : block.getInstructions()) {
				String opcode = instruction.getOpcode().getValue();
				if (!opcodeFrequencies.containsKey(opcode))
					opcodeFrequencies.put(opcode, Integer.valueOf(1));
				else {
					int freq = opcodeFrequencies.get(opcode).intValue();
					opcodeFrequencies.put(opcode, Integer.valueOf(freq + 1));
				}
			}
		}
		for (Map.Entry<String, Integer> entry : opcodeFrequencies.entrySet()) {
			FileUtils.write(opcodeFrequenciesFile,
					"\n" + entry.getKey() + Definitions.TAB_CHAR + entry.getValue().intValue(),
					Charset.defaultCharset(), true);
			System.out.print("\n" + entry.getKey() + Definitions.TAB_CHAR + entry.getValue().intValue());

		}
	}
	public static void createCFG(Output objectFile) throws IOException {

	}
}
