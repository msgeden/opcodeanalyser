package opcodeanalyser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author msgeden
 */
public class FileHandler {


	private static final String configFile = "config.properties";
	private static final String architectureFile = "msp430.properties";
	public static String readConfigValue(String key) {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(configFile);
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Cannot read configuration file(s)\n" + e.getMessage());
		}
		return prop.getProperty(key);
	}
	public static String readArchitectureValue(String key) {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(architectureFile);
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Cannot read architecture configuration file(s)\n" + e.getMessage());
		}
		return prop.getProperty(key);
	}
	public static void writeConfigValue(String key, String val) {
		Properties prop = new Properties();
		InputStream input;
		OutputStream output;
		try {
			input = new FileInputStream(configFile);
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Cannot read configuration file(s)\n" + e.getMessage());
		}
		prop.setProperty(key, val);
		try {
			output = new FileOutputStream(configFile);
			prop.store(output, "");
		} catch (IOException e) {
			System.out.println("Cannot read configuration file(s)\n" + e.getMessage());
		}
	}

	public static String createDirectory(String path, String dir) {
		File directory = new File(path + dir);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		return directory.getAbsolutePath();
	}

	public static void moveFileToDirectory(String filePath, String destinationPath) throws IOException {
		FileUtils.moveFileToDirectory(new File(filePath), new File(destinationPath), true);
	}

	public static byte[] readFileToByteArray(String filePath) throws IOException {
		return FileUtils.readFileToByteArray(FileUtils.getFile(filePath));
	}

	public static String readFileToString(String filePath) throws IOException {
		return FileUtils.readFileToString(FileUtils.getFile(filePath), Charset.defaultCharset());
	}
	
	public static Output parseObjectFile(String path) throws IOException
	{
		Output objectFile = new Output();		
		String fileContent = FileHandler.readFileToString(path);
		objectFile.setFileName(new File(path).getName());
		List<String> functionsStr = Arrays.asList(fileContent.split(Definitions.NEW_LINE + Definitions.NEW_LINE));
		ArrayList<BasicBlock> blocks = new ArrayList<BasicBlock>();
		for (String functionStr : functionsStr) {
			if (!functionStr.contains(">:"))
				continue;
			BasicBlock block = new BasicBlock();
			int instructionCount = -1;
			int address = 0;
			List<String> instructionsStr = Arrays.asList(functionStr.split(Definitions.NEW_LINE));
			ArrayList<Instruction> instructions = new ArrayList<Instruction>();
			for (String instructionStr : instructionsStr) {
				System.out.println(instructionStr);//TODO:remove
				if (instructionCount == -1) {
					block.setBlockName(instructionStr.substring(instructionStr.indexOf("<")+1,instructionStr.indexOf(">")));
					instructionCount++;
					continue;
				}
				if (instructionStr.contains(Definitions.SEMICOLON_CHAR))
						instructionStr = instructionStr.substring(0,instructionStr.lastIndexOf(Definitions.SEMICOLON_CHAR));
				List<String> elements = Arrays.asList(instructionStr.split(Definitions.TAB_CHAR,4));
				address = Integer.parseInt(elements.get(0).trim().substring(0, elements.get(0).trim().lastIndexOf(Definitions.COLON_CHAR)),
						16);

				if (instructionCount == 0)
					block.setStartAddress(address);
				if (elements.size() > 2) {					
					int operandCount = 0;
					List<String> operandsStr = Arrays.asList(elements.get(3).split(Definitions.COMMA_CHAR));
					operandCount =operandsStr.size(); 
					
					if (operandCount == 0)
						instructions.add(new Instruction(address, elements.get(1).trim(),new Opcode(elements.get(2).trim().replace("\t", ""))));
					else if (operandCount == 1) 
						instructions.add(new Instruction(address, elements.get(1).trim(),new Opcode(elements.get(2).trim().replace("\t", "")), new Operand(operandsStr.get(0).trim().replace("\t", ""))));							
					else if (operandCount == 2) 
						instructions.add(new Instruction(address, elements.get(1).trim(),new Opcode(elements.get(2).trim().replace("\t", "")), new Operand(operandsStr.get(0).trim().replace("\t", "")),new Operand(operandsStr.get(1).trim().replace("\t", ""))));
					
				}				
			}
			block.setEndAddress(address);
			block.setInstructions(instructions);
			blocks.add(block);
		}
		objectFile.setFunctions(blocks);		
		return objectFile;
	}
	public static Collection<File> findFiles(String path, String[] extensions) {
		return FileUtils.listFiles(FileUtils.getFile(path), extensions, true);
	}
}