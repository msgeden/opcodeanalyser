package opcodeanalyser;

import java.io.IOException;

public class BinaryAnalyser {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filePath = FileHandler.readConfigValue(Definitions.DISSASSEMBLED_PATH) + "large.txt";
		Output objectFile=FileHandler.parseObjectFile(filePath);
		ObjdumpAnalyser.printStatistics(objectFile);
		ObjdumpAnalyser.printOpcodeFrequencies(objectFile);
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