package opcodeanalyser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class RIMExtractor {

	ArrayList<Instruction> instructions= new ArrayList<Instruction>();
	HashMap<Integer, Integer[]> RIM = new HashMap<Integer, Integer[]>();
	Stack<Integer> awatingBlocks = new Stack<>(); 
	
}
