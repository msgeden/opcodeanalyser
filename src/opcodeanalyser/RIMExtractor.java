package opcodeanalyser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


public class RIMExtractor {
	static Stack<Integer> shadowStack = new Stack<Integer>();
	static Stack<RIMBlock> awaitingBlocks = new Stack<RIMBlock>(); 
	static ArrayList<Instruction> instructions= new ArrayList<Instruction>();
	static HashMap<Integer, Set<Integer>> RIM = new HashMap<Integer, Set<Integer>>();
	static ArrayList<RIMBlock> visited = new ArrayList<RIMBlock>();	
	static int mainAddress=0;
	
	public static ArrayList<Instruction> parseObjectFileAsInstructionList(String path,String functionName) throws IOException {
		ArrayList<Instruction> instructions= new ArrayList<Instruction>();
		String fileContent = FileHandler.readFileToString(path);
		List<String> functionsStr = Arrays.asList(fileContent.split(Definitions.NEW_LINE + Definitions.NEW_LINE));
		for (String functionStr : functionsStr) {
			if (functionStr.contains("<"+functionName +">"))
			{				
				mainAddress=Integer.parseInt(functionStr.substring(0,functionStr.indexOf(" ")),16);
			}
			if (!functionStr.contains(">:"))
				continue;
			int instructionCount = -1;
			int address = 0;
			List<String> instructionsStr = Arrays.asList(functionStr.split(Definitions.NEW_LINE));
			for (String instructionStr : instructionsStr) {
				//System.out.println(instructionStr);// TODO:remove
				if (instructionCount == -1) {
					instructionCount++;
					continue;
				}
				
				if (instructionStr.contains(Definitions.SEMICOLON_CHAR))
					instructionStr = instructionStr.substring(0,
							instructionStr.lastIndexOf(Definitions.SEMICOLON_CHAR));
				List<String> elements = Arrays.asList(instructionStr.split(Definitions.TAB_CHAR, 4));
				if (elements.size() > 2) {
					address = Integer.parseInt(elements.get(0).trim().substring(0,
							elements.get(0).trim().lastIndexOf(Definitions.COLON_CHAR)), 16);

					if (elements.size() > 2) {
						int operandCount = 0;
						List<String> operandsStr = Arrays.asList(elements.get(3).split(Definitions.COMMA_CHAR));
						operandCount = operandsStr.size();

						if (operandCount == 0)
							instructions.add(new Instruction(address, elements.get(1).trim(),
									new Opcode(elements.get(2).trim().replace("\t", ""))));
						else if (operandCount == 1)
							instructions.add(new Instruction(address, elements.get(1).trim(),
									new Opcode(elements.get(2).trim().replace("\t", "")),
									new Operand(operandsStr.get(0).trim().replace("\t", ""))));
						else if (operandCount == 2)
							instructions.add(new Instruction(address, elements.get(1).trim(),
									new Opcode(elements.get(2).trim().replace("\t", "")),
									new Operand(operandsStr.get(0).trim().replace("\t", "")),
									new Operand(operandsStr.get(1).trim().replace("\t", ""))));

					}
				}
			}
			
		}		
		return instructions;
	}
	public static void main(String[] args) throws IOException 
	{
		
		String filePath = FileHandler.readConfigValue(Definitions.DISSASSEMBLED_PATH) + "vul-o.txt";	
		String functionName="main";
		
		instructions=parseObjectFileAsInstructionList(filePath,functionName);
		
		if(mainAddress==0)
		    System.out.print("Could not find root function for CFG: " + functionName);

		//shadowStack.push(Integer.valueOf(mainAddress));
		awaitingBlocks.push(new RIMBlock(0,mainAddress));
		while (!awaitingBlocks.isEmpty())
		{
			RIMBlock block = awaitingBlocks.pop();
			iterateBlock(block.getSource(),block.getTarget());
		}
		printRIM(RIM);		
	}
	private static void printRIM(HashMap<Integer, Set<Integer>> RIM) {
		// TODO Auto-generated method stub
		for (Map.Entry<Integer, Set<Integer>> entry : RIM.entrySet()) {
		    String source = Integer.toHexString(entry.getKey());
		    System.out.print("s:" + source + "-> d:[");
		    for (Integer destination:entry.getValue())
		    {
		    	System.out.print(" " + Integer.toHexString(destination));
		    }
		    System.out.println("]");
		}
	}
	private static void iterateBlock(int source, int blockAddress) {
		// TODO Auto-generated method stub
		if (blockAddress==0 || blockAddress==1)
			return;
		
		if (!RIM.containsKey(Integer.valueOf(source)))
		{			
			RIM.put(Integer.valueOf(source), new HashSet<Integer>());			
		}
		if (!RIM.get(Integer.valueOf(source)).contains(Integer.valueOf(blockAddress)))
		{	
			Set<Integer> existing=RIM.get(Integer.valueOf(source));
			existing.add(Integer.valueOf(blockAddress));
			RIM.put(Integer.valueOf(source), existing);
			//RIM.put(Integer.valueOf(source), new HashSet<Integer>(Arrays.asList(Integer.valueOf(blockAddress))));			
		}
		if (visited.contains(new RIMBlock(source,blockAddress)))
		{			
			return;
		}
		visited.add(new RIMBlock(source,blockAddress));
		boolean skipNextJump=false;
		
		for (int i=getInstructionIndex(blockAddress);i<instructions.size();i++)
		{
			int targetAddress=0;
			System.out.println("index:"+i+" address:"+Integer.toHexString(instructions.get(i).getAddress()));
			System.out.println(instructions.get(i).printInstruction());
			if (instructions.get(i).iscJumpTransfer())
			{
				//jnz $+2
				targetAddress=instructions.get(i).getAddress()+Integer.parseInt(instructions.get(i).getOperand1().getValue().substring(1));;
				if (skipNextJump){
					awaitingBlocks.push(new RIMBlock(blockAddress,instructions.get(i+1).getAddress()));				
				}
				else {
					awaitingBlocks.push(new RIMBlock(blockAddress,instructions.get(i+1).getAddress()));
					awaitingBlocks.push(new RIMBlock(blockAddress,targetAddress));
				}
				System.out.println("block address:"+Integer.toHexString(blockAddress)+" target address:"+Integer.toHexString(targetAddress));	
				return;
			}
			if (instructions.get(i).isUcJumpTransfer())
			{
				//br #0x123
				targetAddress=Integer.parseInt(instructions.get(i).getOperand1().getValue().substring(3),16);
				//jmp $+2
				//targetAddress=instructions.get(i).getAddress()+Integer.parseInt(instructions.get(i).getOperand1().getValue().substring(2));
				System.out.println("block address:"+Integer.toHexString(blockAddress)+" target address:"+Integer.toHexString(targetAddress));
				awaitingBlocks.push(new RIMBlock(blockAddress,targetAddress));
				return;
			}
			if (instructions.get(i).isCallTransfer())
			{
				//calll/br #123				
				targetAddress=Integer.parseInt(instructions.get(i).getOperand1().getValue().substring(1));
				shadowStack.push(Integer.valueOf(instructions.get(i+1).getAddress()));
				awaitingBlocks.push(new RIMBlock(blockAddress,targetAddress));
				System.out.println("block address:"+Integer.toHexString(blockAddress)+" target address:"+Integer.toHexString(targetAddress));
				return;
			}
			if (instructions.get(i).isRetTransfer())
			{
				//calll/br #123				
				if (shadowStack.isEmpty())
					return;
				targetAddress=shadowStack.pop().intValue();
				awaitingBlocks.push(new RIMBlock(blockAddress,targetAddress));
				System.out.println("block address:"+Integer.toHexString(blockAddress)+" target address:"+Integer.toHexString(targetAddress));
				
				return;
			}
		}			
	}
	private static int getInstructionIndex(int address)
	{
	    int index = Collections.binarySearch(instructions, new Instruction(address)); 
		return index;	
	}
	
}
