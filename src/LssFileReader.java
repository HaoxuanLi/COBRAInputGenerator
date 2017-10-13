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

		String initializationAssembly = "";

		initializationAssembly = getInitializationAssemblyForGeneralLocalInput(input);

		if (input.getIsForLoopCounter() && initializationAssembly == "") {
			initializationAssembly = getInitializationAssemblyForCompilerOptimizedForLoopCounter(input);
		}

		return initializationAssembly;
	}

	private int getTheLineNumberOfTheLastForLoopHeaderCCodeBeforeTheTicFlag(Declaration input) throws IOException{
		int lineNumber = 0;
		int lineNumberOfTheLastForLoopHeader = 0;
		BufferedReader lssFileReader = getLssFileReader();
		String lssFileReaderLine = "";
		
		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {
			lineNumber++;
			
			if (lssFileReaderLine.matches(".*<Tic_flag>:$"))
				break;
			
			Boolean isForLoopHeader = lssFileReaderLine.replaceAll("\\s", "").matches("for\\(" + input.getDeclarationName() + "=.*");
			
			if(isForLoopHeader)
				lineNumberOfTheLastForLoopHeader = lineNumber;
		}
		lssFileReader.close();
		return lineNumberOfTheLastForLoopHeader;
		
	}
	private String getCompilerOptimizedForLoopCounterRegisterInitialValue(Declaration input, String optimizedForLoopCounterRegister) throws IOException{
		String registerInitialValue_Hex = "";
		String lssFileReaderLine = "";
		BufferedReader lssFileReader = getLssFileReader();
		int lineNumber = 0;
		int lineNumberOfTheLastForLoopHeader = getTheLineNumberOfTheLastForLoopHeaderCCodeBeforeTheTicFlag(input);
	
		while (((lssFileReaderLine = lssFileReader.readLine()) != null) && lineNumber<lineNumberOfTheLastForLoopHeader) {
			lineNumber++;
			if(getIsAssemblyCodeLine(lssFileReaderLine)){
				String assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);
				if (assemblyCode.matches("ldi\\s+"+optimizedForLoopCounterRegister+",.*")){
					registerInitialValue_Hex = "0x" + assemblyCode.replaceAll(".*0x", "");
				}	
			}
		}
		
		lssFileReader.close();
		return registerInitialValue_Hex;
	}
	
	private String getInitializationAssemblyForCompilerOptimizedForLoopCounter(Declaration input) throws IOException {
		String lssFileReaderLine = "";
		String initializationAssembly = "";
		BufferedReader lssFileReader = getLssFileReader();
		Boolean isAfterTocFlag = false;
		Boolean isAfterForLoopHeader = false;
		Boolean isCompareAssemblyInstruction = false;
		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {
			Boolean isAssemblyCode = getIsAssemblyCodeLine(lssFileReaderLine);

			if (lssFileReaderLine.matches(".*<Toc_flag>:$"))
				isAfterTocFlag = true;

			if (lssFileReaderLine.replaceAll("\\s", "").matches("for\\(" + input.getDeclarationName() + "=.*"))
				isAfterForLoopHeader = true;

			if (isAfterTocFlag && isAfterForLoopHeader && isAssemblyCode) {

				String assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);

				if (assemblyCode.matches("cpi.*")) {
					isCompareAssemblyInstruction = true;
				}

				if (assemblyCode.matches("[^\\.\\s]*\\s+\\.(\\+|-)\\d+")) {
					isCompareAssemblyInstruction = false;
					break;
				}
			}

			if (isCompareAssemblyInstruction) {
				String assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);
				String[] assemblyCode_Split = assemblyCode.split("\\s");
				String optimizedForLoopCounterRegister = assemblyCode_Split[1].replace(",", "");
				String registerInitialValue_Hex = getCompilerOptimizedForLoopCounterRegisterInitialValue(input, optimizedForLoopCounterRegister);
				initializationAssembly = initializationAssembly + "\n" + assemblyCode + "\t;registerInitialValue_Hex:"+registerInitialValue_Hex;
			}
		}

		lssFileReader.close();
		//System.out.print(initializationAssembly);
		initializationAssembly = initializationAssembly.replaceFirst("\\n", "");
		return initializationAssembly;
	}

	public String getInitializationAssemblyForGeneralLocalInput(Declaration input) throws IOException {
		String lssFileReaderLine = "";
		String initializationAssembly = "";
		BufferedReader lssFileReader = getLssFileReader();
		Boolean isCorrespondingAssemblyCode = false;

		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {
			String assemblyCode = "";

			int stdInstructionCounter = 0;
			int stdInstructionCounter_Pre = 0;
			Boolean isAssemblyCode = getIsAssemblyCodeLine(lssFileReaderLine);

			if (lssFileReaderLine.matches(".*<Tic_flag>:$"))
				break;

			if (isAssemblyCode == false && lssFileReaderLine.replaceAll("^\\s*", "")
					.matches(".*\\b" + input.getDeclarationName() + "\\s?=.*")) {
				isCorrespondingAssemblyCode = true;

			} else if (isAssemblyCode == false && isCorrespondingAssemblyCode == true) {
				isCorrespondingAssemblyCode = false;
				break;
			}

			if (isCorrespondingAssemblyCode && !lssFileReaderLine.replaceAll("^\\s*", "")
					.matches(".*\\b" + input.getDeclarationName() + "\\s?=.*")) {

				assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);

				String[] assemblyCode_Split = assemblyCode.replaceAll(";.*", "").replaceAll("\\s+", " ").split("\\s");

				String assemblyInstruction = assemblyCode_Split[0];

				if (assemblyInstruction.matches("ldi|lds|ld|ldd|mov|movw|")) {
					initializationAssembly = assemblyCode + "\n" + initializationAssembly;
				}

				else if (assemblyInstruction.matches("st|std")) {

					stdInstructionCounter = (assemblyCode_Split[1].replaceAll("[^\\d]", "") == "") ? 0
							: Integer.parseInt(assemblyCode_Split[1].replaceAll("[^\\d]", ""));

					// depends on the assembly code direction: descent or
					// ascent
					if (stdInstructionCounter < stdInstructionCounter_Pre)
						initializationAssembly = assemblyCode + "\n" + initializationAssembly;
					else if (initializationAssembly != "")
						initializationAssembly = initializationAssembly + "\n" + assemblyCode;
					else if (initializationAssembly == "")
						initializationAssembly = assemblyCode;

					stdInstructionCounter_Pre = stdInstructionCounter;
				} else {
					break;
				}
			}
		}

		lssFileReader.close();
		return initializationAssembly;
	}

	public String getRcallAdressesOfBlockContentInMeaurementModule() throws IOException {
		String rcallAdressesOfBlockContentInMeaurementModule = "";
		BufferedReader lssFileReader = getLssFileReader();

		boolean isBlockContent = false;
		String lssFileReaderLine = "";
		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {

			if (lssFileReaderLine.matches(".*<BlockContent_Start>:$"))
				isBlockContent = true;

			if (lssFileReaderLine.matches(".*<BlockContent_End>:$")) {
				isBlockContent = false;
				break;
			}

			if (isBlockContent && getIsAssemblyCodeLine(lssFileReaderLine)) {

				if (lssFileReaderLine.contains("rcall"))
					rcallAdressesOfBlockContentInMeaurementModule = rcallAdressesOfBlockContentInMeaurementModule + ";"
							+ lssFileReaderLine.replaceAll("^\\s*", "").replaceAll(":.*", "");

			}
		}

		lssFileReader.close();

		rcallAdressesOfBlockContentInMeaurementModule = rcallAdressesOfBlockContentInMeaurementModule.replaceFirst(";",
				"");

		return rcallAdressesOfBlockContentInMeaurementModule;
	}

	public String getCodeAfterTheTocFlagDueToJumpInstructions() throws IOException {
		String codeAfterTheTocFlagDueToJumpInstructions = "";
		BufferedReader lssFileReader = getLssFileReader();
		String lssFileReaderLine = "";
		Boolean isBlockContent = false;
		Boolean isCodeAfterTheTocFlag = false;
		Boolean isExistCodeAfterTheTocFlag = false;
		int isInstructionWithCodeAfterTheTocFlag_Counter = 0;
		int jumpDistance = 0;

		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {

			if (lssFileReaderLine.matches(".*<Tic_flag>:$")) {
				isBlockContent = true;
			}

			if (lssFileReaderLine.matches(".*<Toc_flag>:$")) {
				isBlockContent = false;
			}

			if (isBlockContent && getIsAssemblyCodeLine(lssFileReaderLine)) {

				if (getIsInstructionWithCodeAfterTheTocFlag(lssFileReaderLine)) {
					if (lssFileReaderLine.contains("<Toc_flag+0x")) {
						String jumpDistance_Hex = lssFileReaderLine.replaceAll(".*0x", "").replaceAll(">", "");
						jumpDistance = Integer.parseInt(jumpDistance_Hex, 16) / 2;
						isInstructionWithCodeAfterTheTocFlag_Counter++;
						isExistCodeAfterTheTocFlag = true;

					} else if (lssFileReaderLine.contains("<Toc_flag>")) {
						isExistCodeAfterTheTocFlag = false;
					} else {
						IOException e = new IOException();
						throw e;
					}
				}
			}

			if (isInstructionWithCodeAfterTheTocFlag_Counter > 1) {
				IOException e = new IOException();
				throw e;
			}

			if (isCodeAfterTheTocFlag && getIsAssemblyCodeLine(lssFileReaderLine)) {

				String assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);
				String codeAfterTheTocFlagDueToJumpInstructionsLine = "\t\"" + assemblyCode + "\\n\\t\"\n";

				// adjust the jump distance because the Toc assembly code takes
				// one line
				if (getIsJumpBackToBlockContent(lssFileReaderLine)) {
					isCodeAfterTheTocFlag = false;

					String adjustedAssemblyCode = this
							.adjustJumpDistanceForJumpBackToBlockContentAfterTheTocFlag(assemblyCode);
					codeAfterTheTocFlagDueToJumpInstructionsLine = "\t\"" + adjustedAssemblyCode + "\\n\\t\"\n";

					codeAfterTheTocFlagDueToJumpInstructions = codeAfterTheTocFlagDueToJumpInstructions
							+ codeAfterTheTocFlagDueToJumpInstructionsLine;
					break;
				}
				codeAfterTheTocFlagDueToJumpInstructions = codeAfterTheTocFlagDueToJumpInstructions
						+ codeAfterTheTocFlagDueToJumpInstructionsLine;
			}

			if (isExistCodeAfterTheTocFlag && !isBlockContent && getIsAssemblyCodeLine(lssFileReaderLine)) {

				String codeAfterTheTocFlagDueToJumpInstructionsLine = "\t\"nop\t\\n\\t\"\n";

				if (jumpDistance == 1 && !lssFileReaderLine.contains("rjmp")) {
					IOException e = new IOException();
					throw e;
				}

				if (jumpDistance == 1 && lssFileReaderLine.contains("rjmp")) {
					String assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);

					codeAfterTheTocFlagDueToJumpInstructionsLine = "\t\"" + assemblyCode + "\\n\\t\"\n";

				}
				codeAfterTheTocFlagDueToJumpInstructions = codeAfterTheTocFlagDueToJumpInstructions
						+ codeAfterTheTocFlagDueToJumpInstructionsLine;

				jumpDistance--;

				if (jumpDistance == 0)
					isCodeAfterTheTocFlag = true;
			}
		}

		lssFileReader.close();
		return codeAfterTheTocFlagDueToJumpInstructions;
	}

	public String getAssemblyCodeFromCodeLine(String codeLine) {
		String assemblyCode = "";

		String readline = codeLine.replaceAll(".*:|;.*$", "").replaceAll("^\\s*|\\s*$", "").replaceAll("\\s+", " ");

		String readline_split[] = readline.split(" ");
		String blockline = "";
		Boolean isAssemblyInstruction = false;
		for (int i = 0; i < readline_split.length; i++) {
			if (readline_split[i].matches("(?i)" + this.avrAssemblyInstructions))
				isAssemblyInstruction = true;

			if (isAssemblyInstruction)
				blockline = blockline + " " + readline_split[i];
		}

		assemblyCode = blockline.replaceFirst("\\s+", "");

		return assemblyCode;
	}

	public Boolean getIsAssemblyCodeLine(String codeLine) {
		Boolean isAssemblyCodeLine = codeLine.replaceAll("^\\s*", "").matches("\\w+:(\\s+\\w+)+\\s+.*");
		return isAssemblyCodeLine;

	}

	public Boolean getIsInstructionWithCodeAfterTheTocFlag(String codeLine) {
		Boolean isInstructionWithCodeAfterTheTocFlag = codeLine.contains(".+") && codeLine.contains("<Toc_flag+0x");
		return isInstructionWithCodeAfterTheTocFlag;

	}

	public Boolean getIsJumpBackToBlockContent(String codeLine) {
		Boolean isJumpBackToBlockContent = codeLine.contains("rjmp") && codeLine.contains(".-")
				&& codeLine.contains("<Tic_flag");
		return isJumpBackToBlockContent;

	}

	public String getBlockContentAssemblyAnnotatedWithRcallTargetAddress() throws IOException {
		String blockContentAssembly = "";
		BufferedReader lssFileReader = getLssFileReader();
		String lssFileReaderLine = "";
		Boolean isBlockContent = false;

		while ((lssFileReaderLine = lssFileReader.readLine()) != null) {

			if (lssFileReaderLine.matches(".*<Tic_flag>:$")) {
				isBlockContent = true;
			}

			if (lssFileReaderLine.matches(".*<Toc_flag>:$")) {
				isBlockContent = false;
				break;
			}

			if (isBlockContent && getIsAssemblyCodeLine(lssFileReaderLine)) {

				String rcallTargetAddress = "";
				String blockContentAssemblyLine = "";

				if (lssFileReaderLine.contains("rcall")) {
					String readline = lssFileReaderLine.replaceAll("^\\s*|\\s*$", "").replaceAll("\\s+", " ");
					String readline_split[] = readline.split(" ");

					rcallTargetAddress = readline_split[readline_split.length - 2];
				}
				String assemblyCode = getAssemblyCodeFromCodeLine(lssFileReaderLine);

				if (rcallTargetAddress == "")
					blockContentAssemblyLine = "\t\"" + assemblyCode + "\\n\\t\"\n";
				else {
					blockContentAssemblyLine = "\t\"" + assemblyCode + "\\n\\t\" //" + rcallTargetAddress + "\n";
				}

				// adjust the jump distance because the Toc assembly code takes
				// one line
				if (getIsInstructionWithCodeAfterTheTocFlag(lssFileReaderLine)) {
					String adjustedAssemblyCode = adjustJumpDistanceForBlockContentAssemblyCode(assemblyCode);
					blockContentAssemblyLine = "\t\"" + adjustedAssemblyCode + "\\n\\t\"\n";

				}

				blockContentAssembly = blockContentAssembly + blockContentAssemblyLine;
			}

		}
		lssFileReader.close();
		return blockContentAssembly;

	}

	public String adjustJumpDistanceForBlockContentAssemblyCode(String assemblyCode) {
		int jumpDistance = Integer.parseInt(assemblyCode.replaceAll(".*\\+", ""));
		int adjustedJumpDistance = jumpDistance + 2;
		String adjustedAssemblyCode = assemblyCode.replaceAll("\\+.*", "") + "+" + adjustedJumpDistance;
		return adjustedAssemblyCode;
	}

	public String adjustJumpDistanceForJumpBackToBlockContentAfterTheTocFlag(String assemblyCode) {
		int jumpDistance = Integer.parseInt(assemblyCode.replaceAll(".*-", ""));
		int adjustedJumpDistance = jumpDistance + 2;
		String adjustedAssemblyCode = assemblyCode.replaceAll("-.*", "") + "-" + adjustedJumpDistance;
		return adjustedAssemblyCode;
	}

}
