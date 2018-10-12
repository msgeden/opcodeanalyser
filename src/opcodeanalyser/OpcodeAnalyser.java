package opcodeanalyser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Iterables;

public class OpcodeAnalyser {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = FileHandler.readConfigValue(Definitions.DISSASSEMBLED_PATH) + "small.txt";
		String fileContent=FileHandler.readFileToString(filePath);
		
		List<String> functionsStr=Arrays.asList(fileContent.split(Definitions.NEW_LINE+Definitions.NEW_LINE));
		ArrayList<Function> functions= new ArrayList<Function>();
		for (String functionStr : functionsStr) {
			Function function = new Function();
			int instructionCount=-1;
			int address=0;
			List<String> instructionsStr = Arrays.asList(functionStr.split(Definitions.NEW_LINE));
			ArrayList<Instruction> instructions= new ArrayList<Instruction>();
			for (String instructionStr : instructionsStr) {
				
				if (instructionCount==-1)
				{
					function.setIdentifier(instructionStr);
					instructionCount++;
					continue;				
				}				
				List<String> elements = Arrays.asList(instructionStr.split(Definitions.TAB_CHAR));
				address = Integer.parseInt(elements.get(0).trim().substring(0, elements.get(0).trim().lastIndexOf(":")),16);
			
				if (instructionCount==0)
					function.setStartAddress(address);
				
				Opcode opcode=new Opcode(elements.get(2));
				List<String>  operandsStr = Arrays.asList(elements.get(3).split(Definitions.COMMA_CHAR));
				Operand operand1=null;
				Operand operand2=null;
				if (operandsStr.size()>0)
					operand1=new Operand(operandsStr.get(0));
				if (operandsStr.size()>1)
					operand2=new Operand(operandsStr.get(1));
				Instruction instruction = new Instruction(address,elements.get(1),opcode,operand1,operand2);
				instructionCount++;		
				instructions.add(instruction);
			}
			
			function.setEndAddress(address);
						
			
		}
	}

}
//0000d23a <.Loc.178.1>:
//    d23a:	1c 42 08 03 	mov	&0x0308,r12	;0x0308
//    d23e:	0a 4c       	mov	r12,	r10	;
//    d240:	4b 43       	clr.b	r11		;
//    d242:	0e 4a       	mov	r10,	r14	;
//    d244:	0e 5a       	add	r10,	r14	;
//    d246:	81 4e 00 00 	mov	r14,	0(r1)	;
//    d24a:	0f 4b       	mov	r11,	r15	;
//    d24c:	0f 6b       	addc	r11,	r15	;
//    d24e:	81 4f 02 00 	mov	r15,	2(r1)	;
//    d252:	2c 41       	mov	@r1,	r12	;
//    d254:	1d 41 02 00 	mov	2(r1),	r13	;
//    d258:	3e 40 e8 03 	mov	#1000,	r14	;#0x03e8
//    d25c:	4f 43       	clr.b	r15		;
//    d25e:	b0 12 de d8 	call	#55518		;#0xd8de
//    d262:	0e 4c       	mov	r12,	r14	;
//    d264:	0f 4d       	mov	r13,	r15	;
//    d266:	1e 51 12 00 	add	18(r1),	r14	;0x00012
//    d26a:	1f 61 14 00 	addc	20(r1),	r15	;0x00014
//    d26e:	81 4e 12 00 	mov	r14,	18(r1)	; 0x0012
//    d272:	81 4f 14 00 	mov	r15,	20(r1)	; 0x0014
//
//0000d276 <.Loc.181.1>:
//    d276:	1c 41 12 00 	mov	18(r1),	r12	;0x00012
//    d27a:	1d 41 14 00 	mov	20(r1),	r13	;0x00014
//    d27e:	3e 40 e8 03 	mov	#1000,	r14	;#0x03e8
//    d282:	4f 43       	clr.b	r15		;
//    d284:	b0 12 ec d8 	call	#55532		;#0xd8ec
//    d288:	0d 4c       	mov	r12,	r13	;
//    d28a:	1c 41 10 00 	mov	16(r1),	r12	;0x00010
//    d28e:	8c 4d 02 00 	mov	r13,	2(r12)	;