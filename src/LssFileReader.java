import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import VariableClasses.Declaration;

public class LssFileReader {
	String avrFilesOutputDirectoryAbsolutePath = "";
	String avrAssemblyInstructions = "ADC|ADD|ADIW|AND|ANDI|ASR|BCLR|BLD|BRBC|BRBS|BRCC|BRCS|BREAK|BREQ|BRGE|BRHC|BRHS|BRID|BRIE|BRLO|BRLT|BRMI|BRNE|BRPL|BRSH|BRTC|BRTS|BRVC|BRVS|BSET|BST|CALL|CBI|CBR|CLC|CLH|CLI|CLN|CLR|CLS|CLT|CLV|CLZ|COM|CP|CPC|CPI|CPSE|DEC|EICALL|EIJMP|ELPM|EOR|FMUL|FMULS|FMULSU|ICALL|IJMP|IN|INC|JMP|LD|LAT|LAS|LAC|LD|LDD|LDI|LDS|LPM|LSL|LSR|MOV|MOVW|MUL|MULS|MULSU|NEG|NOP|OR|ORI|OUT|POP|PUSH|RCALL|RET|RETI|RJMP|ROL|ROR|SBC|SBCI|ySBI|SBIC|SBIS|SBIW|SBR|SBRC|SBRS|SEC|SEH|SEI|SEN|SER|SES|SET|SEV|SEZ|SLEEP|SPM|ST|STD|STS|SUB|SUBI|SWAP|TST|WDR|XCH|NOP";
	public LssFileReader(WCETTargetInfo wcetTargetInfo) {
		readWcetTargetInfo(wcetTargetInfo);
	}

	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.avrFilesOutputDirectoryAbsolutePath = wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
	}

	public File getLssFile() throws FileNotFoundException {
		File lssFile = null;
		for (File file : new File(this.avrFilesOutputDirectoryAbsolutePath).listFiles()) {
			if (file.getName().matches(".*\\.lss$"))
				lssFile = file;
		}
		return lssFile;
	}

	public BufferedReader getLssFileReader() throws FileNotFoundException {
		File lssFile = getLssFile();
		BufferedReader lssFileReader = new BufferedReader(new FileReader(lssFile));
		return lssFileReader;
	}

	public String getInitializationAssembly(Declaration input) throws IOException {
		String lssFileReaderLine = "";
		String initializationAssembly = "";
		BufferedReader lssFileReader = getLssFileReader();
		Boolean isCorrespondingAssemblyCode = false;
		
		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {
			
			String assemblyCodeLine = "";
			
			int stdInstructionCounter = 0;
			int stdInstructionCounter_Pre = 0;
			Boolean isAssemblyCode = lssFileReaderLine.replaceAll("^\\s*", "").matches("\\w*:.*");
			
			
			
			if (lssFileReaderLine.matches(".*<Tic_flag>:$"))
				break;
			
			if (isAssemblyCode==false && lssFileReaderLine
					.replaceAll("^\\s*", "").matches(".*\\b" + input.getDeclarationName() + "\\s?=.*")){
				isCorrespondingAssemblyCode = true;
				
			}else if(isAssemblyCode==false && isCorrespondingAssemblyCode == true){
				isCorrespondingAssemblyCode = false;
				break;
			}
		
			if (isCorrespondingAssemblyCode && !lssFileReaderLine
					.replaceAll("^\\s*", "").matches(".*\\b" + input.getDeclarationName() + "\\s?=.*")) {
				assemblyCodeLine = lssFileReaderLine;
				
				String[] assemblyCodeLine_Split = assemblyCodeLine.replaceAll(";.*", "").replaceAll("\\s+", " ")
						.split("\\s");
				String assemblyInstruction = assemblyCodeLine_Split[assemblyCodeLine_Split.length - 3];
				
				if (assemblyInstruction.matches("ldi|lds|ld|ldd|mov|movw|")) {
					initializationAssembly = assemblyCodeLine + "\n" + initializationAssembly;
				}
				
				else if (assemblyCodeLine_Split[assemblyCodeLine_Split.length - 3].matches("st|std")) {

					stdInstructionCounter = (assemblyCodeLine_Split[assemblyCodeLine_Split.length - 2]
							.replaceAll("[^\\d]", "") == "") ? 0
									: Integer.parseInt(assemblyCodeLine_Split[assemblyCodeLine_Split.length - 2]
											.replaceAll("[^\\d]", ""));

					// depends on the assembly code direction: descent or ascent
					if (stdInstructionCounter < stdInstructionCounter_Pre)
						initializationAssembly = assemblyCodeLine + "\n" + initializationAssembly;
					else if (initializationAssembly != "")
						initializationAssembly = initializationAssembly + "\n" + assemblyCodeLine;
					else if (initializationAssembly == "")
						initializationAssembly = assemblyCodeLine;

					stdInstructionCounter_Pre = stdInstructionCounter;
				}
				else {
					break;
				}
			}		
		}
		lssFileReader.close();
		return initializationAssembly;
	}

	public String getRcallAdressesOfBlockContentInMeaurementModule() throws IOException{
		String rcallAdressesOfBlockContentInMeaurementModule = "";
		BufferedReader lssFileReader = getLssFileReader();
		
		boolean isBlockContent = false;
		String lssFileReaderLine = "";
		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {

			if (lssFileReaderLine.matches(".*<BlockContent_Start>:$"))
				isBlockContent = true;

			if (lssFileReaderLine.matches(".*<BlockContent_End>:$")){
				isBlockContent = false;
				break;
				}

			if (isBlockContent) {
				if (lssFileReaderLine.replaceAll("^\\s*", "").matches("\\w+:(\\s+\\w+)+\\s+.*")) {
					if (lssFileReaderLine.contains("rcall"))
						rcallAdressesOfBlockContentInMeaurementModule = rcallAdressesOfBlockContentInMeaurementModule + ";"
								+ lssFileReaderLine.replaceAll("^\\s*", "").replaceAll(":.*", "");

				}
			}
		}
		
		
		lssFileReader.close();
		
		rcallAdressesOfBlockContentInMeaurementModule = rcallAdressesOfBlockContentInMeaurementModule.replaceFirst(";", "");
		
		return rcallAdressesOfBlockContentInMeaurementModule;
	}
 	public String getBlockContentAssemblyAnnotatedWithRcallTargetAddress() throws IOException {
		String blockContentAssembly = "";

		String lssFileReaderLine = "";
		Boolean isBlockContent = false;
		BufferedReader lssFileReader = getLssFileReader();

		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {

			if (lssFileReaderLine.matches(".*<Tic_flag>:$")) {
				isBlockContent = true;
			}

			if (lssFileReaderLine.matches(".*<Toc_flag>:$")) {
				isBlockContent = false;
				break;
			}

			if (isBlockContent) {
				if (lssFileReaderLine.replaceAll("^\\s*", "").matches("\\w+:(\\s+\\w+)+\\s+.*")) {

					String rcallTargetAddress = "";
					String blockContentAssemblyLine = "";

					if (lssFileReaderLine.contains("rcall")) {
						String readline = lssFileReaderLine.replaceAll("^\\s*|\\s*$", "").replaceAll("\\s+", " ");
						String readline_split[] = readline.split(" ");

						rcallTargetAddress = readline_split[readline_split.length - 2];
					}
					String readline = lssFileReaderLine.replaceAll(".*:|;.*$", "").replaceAll("^\\s*|\\s*$", "").replaceAll("\\s+", " ");

					String readline_split[] = readline.split(" ");
					String blockline = "";
					Boolean isAssemblyInstruction = false;
					for (int i = 0; i < readline_split.length; i++) {
						if(readline_split[i].matches("(?i)"+this.avrAssemblyInstructions))
							isAssemblyInstruction=true;
						
						if(isAssemblyInstruction)
							blockline = blockline + " " + readline_split[i];
					}
		
					if (rcallTargetAddress == "")
						blockContentAssemblyLine = "\t\"" + blockline.replaceFirst("\\s+", "") + "\\n\\t\"\n";
					else {
						blockContentAssemblyLine = "\t\"" + blockline.replaceFirst("\\s+", "") + "\\n\\t\" //"
								+ rcallTargetAddress + "\n";
					}

					blockContentAssembly = blockContentAssembly + blockContentAssemblyLine;
				}
			}

		}
		lssFileReader.close();
		return blockContentAssembly;

	}

}
