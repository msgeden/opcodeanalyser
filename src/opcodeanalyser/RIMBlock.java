package opcodeanalyser;

import java.util.ArrayList;

public class RIMBlock{
	public RIMBlock(int source, int target) {
		super();
		this.source = source;
		this.target = target;
	}
	public int getSource() {
		return source;
	}
	public int getTarget() {
		return target;
	}
	private int source=-1;
	private int target=-1;
	//public ArrayList<Integer> callStack=new ArrayList<Integer>();
	private boolean isData=false;
	private boolean isWrite=false;//false:Read, true:Write
	private int cellId=-1;
	@Override
	public boolean equals(Object o){
	   if(this==o){
	      return true;
	   }
	   if(o instanceof RIMBlock){
		   RIMBlock other = (RIMBlock) o;
	       return (this.source == other.source && this.target == other.target);
	   }
	   return false;
	}
}
