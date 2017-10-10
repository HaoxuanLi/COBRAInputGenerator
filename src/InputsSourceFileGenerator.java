import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import VariableClasses.Declaration;
import VariableClasses.StructDeclaration;

public class InputsSourceFileGenerator {
	private String originalFileDirectoryAbsolutePath;
	private String originalFileAbsolutePath;
	private String workDirectoryAbsolutePath;
	private String avrFilesOutputDirectoryAbsolutePath;
	private String blockInputUpdateFunctionBody = "";
	private String blockInputsForwardDeclaration = "";
	private String framePointerValue;
	private String stackPointerValueOfOriginalCode;
	private String stackPointerValueOfBlockCode;


	private List<Declaration> inputDeclarationList = new ArrayList<Declaration>();
	private List<Declaration> nonStructInputList = new ArrayList<Declaration>();
	private List<StructDeclaration> structInputList = new ArrayList<StructDeclaration>();

	private int processorRegisterLength;
	private int blockStartLineNumber;
	
	private MapFileReader mapFileReader;
	private LssFileReader lssFileReader;
	private WCETTargetInfo wcetTargetInfo;
	private InputGenerator inputGenerator;
	
	
	public InputsSourceFileGenerator(WCETTargetInfo wcetTargetInfo) throws Exception {

		readWcetTargetInfo(wcetTargetInfo);
		
		
		this.inputGenerator = new InputGenerator(this.wcetTargetInfo);
		
		this.inputDeclarationList = this.inputGenerator.getInputDeclarationList();
		this.structInputList = this.inputGenerator.getStructInputList();		
		this.nonStructInputList = this.inputGenerator.getNonStructInputList();
		this.blockInputsForwardDeclaration = getBlockInputsForwardDeclaration();

		constructBlockInputUpdateFunction();
	}

	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		//wcetTargetInfo.getTicStamp();
		//wcetTargetInfo.getTocStamp();
		this.wcetTargetInfo = wcetTargetInfo;
		this.originalFileDirectoryAbsolutePath = wcetTargetInfo.getOriginalFileDirectoryAbsolutePath();
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.avrFilesOutputDirectoryAbsolutePath = wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
		this.originalFileAbsolutePath = wcetTargetInfo.getOriginalFileAbsolutePath();
		this.blockStartLineNumber = wcetTargetInfo.getBlockStartLineNumber(); 
		this.framePointerValue = wcetTargetInfo.getFramePointerValue();
		this.stackPointerValueOfBlockCode = wcetTargetInfo.getStackPointerValueOfBlockCode();
		this.stackPointerValueOfOriginalCode = wcetTargetInfo.getStackPointerValueOfOriginalCode();
		this.processorRegisterLength = wcetTargetInfo.getProcessorRegisterLength();
		this.mapFileReader = new MapFileReader(wcetTargetInfo);
		this.lssFileReader = new LssFileReader(wcetTargetInfo);
	}


	public List<File> getHeaderFileList() {
		List<File> headerFileList = new ArrayList<File>();
		for (File headerFile : new File(this.originalFileDirectoryAbsolutePath).listFiles()) {
			if (headerFile.isFile() && headerFile.getName().matches(".*\\.h$")) {
				headerFileList.add(headerFile);
			}
		}
		return headerFileList;
	}

	public CobraCCodeParser parseHeaderFile(File headerFile) throws IOException {
		String headerFileContent = new String(Files.readAllBytes(Paths.get(headerFile.getAbsolutePath())));
		String prefixForCobra = "void benchmarkNode(void)";
		CobraCCodeParser headerFileParser = new CobraCCodeParser(prefixForCobra + headerFileContent);
		return headerFileParser;
	}

	public CobraCCodeParser getHeaderFileParser(File headerFile) throws IOException {
		return parseHeaderFile(headerFile);
	}

	public void setOriginalHeaderFileAbsolutePathForstructDefinitionDeclarationList(List<StructDeclaration> structDefinitionDeclarationList,
			String originalHeaderFileAbsolutePath) {
		for (StructDeclaration structdeclaration : structDefinitionDeclarationList) {
			structdeclaration.setOriginalHeaderFileAbsolutePath(originalHeaderFileAbsolutePath);
		}
	}


	public void appendMacrosForInputGeneration(String macrosForInputGeneration, String newRewrittenMacro) {
		macrosForInputGeneration = macrosForInputGeneration.replaceAll(newRewrittenMacro, "") + newRewrittenMacro;
	}

	public boolean isMacro(String line) {
		boolean isMacro = line.matches("^#define\\s+\\w*\\s+\\d*");
		return isMacro;
	}

	public String rewriteDefinedMacroForInputGeneration(String definedMacro) {
		String[] definedMacro_Split = definedMacro.split("\\s+");
		String macroForInputGeneration = definedMacro_Split[1] + "=" + definedMacro_Split[2] + "\n";
		return macroForInputGeneration;

	}


	public File getMapFile() throws FileNotFoundException {
		File mapFile = null;
		for (File file : new File(this.avrFilesOutputDirectoryAbsolutePath).listFiles()) {
			if (file.getName().matches(".*\\.map$"))
				mapFile = file;
		}
		return mapFile;
	}

	public BufferedReader getMapFileReader() throws FileNotFoundException {
		File mapFile = getMapFile();
		BufferedReader mapFileReader = new BufferedReader(new FileReader(mapFile));
		return mapFileReader;
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

	private boolean getIsDeclarationAPointer(Declaration declaration) {
		Boolean isPointer = declaration.getDeclarationName().matches("\\*\\w*");
		return isPointer;

	}

	public void setArrayConfigurationForNonStructComponentsOfThisStructInput(
			List<Declaration> structComponentListOfThisStructInput, StructDeclaration structInput) {
		for (Declaration structComponent : structComponentListOfThisStructInput) {
			if (structInput.getIsArray()) {
				structComponent.setIsArray(true);
				structComponent.setArraySize((structComponent.getArraySize() == null)
						? (structInput.getArraySize() + "," + structComponent.getArraySize())
						: (structInput.getArraySize()));
			}
		}
	}

	public void setArrayConfigurationForStructComponentsOfThisStructInput(
			List<StructDeclaration> structComponentListOfThisStructInput, StructDeclaration structInput) {
		for (StructDeclaration structComponent : structComponentListOfThisStructInput) {
			if (structInput.getIsArray()) {
				structComponent.setIsArray(true);
				structComponent.setArraySize((structComponent.getArraySize() == null)
						? (structInput.getArraySize() + "," + structComponent.getArraySize())
						: (structInput.getArraySize()));
			}
		}
	}


	private boolean getIsDeclarationConstant(Declaration declaration) {
		boolean isDeclarationConstant = declaration.getDeclarationType().matches("(const)+.*");
		return isDeclarationConstant;
	}


	private String replaceOperatorsInNameWithLetters(String nameString) {
		String replacedName = nameString.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
				.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
				.replaceAll(",", "com");

		return replacedName;
	}

	private String getGeneratedInputName(Declaration input) {
		String generatedInputName = replaceOperatorsInNameWithLetters(input.getDeclarationName()) + "_inputs";
		return generatedInputName;
	}

	private void constructBlockInputUpdateFunctionNonArrayNonStructInputs(Declaration input) {
		String blockInputName = input.getDeclarationName().replaceAll("^\\*", "") + "_block";
		String assignValueToInput_Left = blockInputName;
		String assignValueToInput_Right = getGeneratedInputName(input) + "[blockInputUpdate_i];";
		String assignValueToInput = assignValueToInput_Left + " = " + assignValueToInput_Right;

		updateBlockInputUpdateFunctionBody(assignValueToInput);
	}

	private void constructBlockInputUpdateFunctionForArrayNonStructInputs(Declaration input) {
		int counterNumber = 0;
		String blockInputName = input.getDeclarationName().replaceAll("^\\*", "") + "_block";
		String arraySize[] = input.getArraySize().split(",");
		String assignValuesToArrayInput = "";
		String assignValuesToArrayInput_LoopCounterDeclaration = "";
		String assignValuesToArrayInput_ForLoopHead = "";
		String assignValuesToArrayInput_ForLoopTail = "";
		String assignValueToArrayElement = "";
		String assignValueToArrayElement_Left = blockInputName;
		String assignValueToArrayElement_Right = getGeneratedInputName(input) + "[blockInputUpdate_i]";

		for (int i = 0; i < arraySize.length; i++) {
			String counter = blockInputName + "_counter" + counterNumber;
			assignValuesToArrayInput_LoopCounterDeclaration = assignValuesToArrayInput_LoopCounterDeclaration + "int "
					+ counter + ";\n";
			assignValuesToArrayInput_ForLoopHead = assignValuesToArrayInput_ForLoopHead + "\tfor(" + counter + "=0;"
					+ counter + "<" + arraySize[i] + ";" + counter + "++){\n";
			assignValueToArrayElement_Left = assignValueToArrayElement_Left + "[" + counter + "]";
			assignValueToArrayElement_Right = assignValueToArrayElement_Right + "[" + counter + "]";
			assignValuesToArrayInput_ForLoopTail = assignValuesToArrayInput_ForLoopTail + "\t}\n";
			counterNumber++;
		}

		assignValueToArrayElement = "\t\t" + assignValueToArrayElement_Left + "=" + assignValueToArrayElement_Right
				+ ";\n";

		assignValuesToArrayInput = assignValuesToArrayInput_LoopCounterDeclaration
				+ assignValuesToArrayInput_ForLoopHead + assignValueToArrayElement
				+ assignValuesToArrayInput_ForLoopTail;

		updateBlockInputUpdateFunctionBody(assignValuesToArrayInput);
	}

	private void constructBlockInputUpdateFunctionForNonStructInputs() {
		for (Declaration input : this.nonStructInputList) {
			if (!getIsDeclarationAPointer(input) && !getIsDeclarationConstant(input)) {
				if (!input.getIsArray()) {
					constructBlockInputUpdateFunctionNonArrayNonStructInputs(input);
				} else {
					constructBlockInputUpdateFunctionForArrayNonStructInputs(input);
				}
			}
		}
	}

	private void constructBlockInputUpdateFunctionNonArrayStructInputs(StructDeclaration structInput) {
		String blockInputName = structInput.getDeclarationName().replaceAll("^\\*", "") + "_block";
		String assignInputToVariable_Left = "";
		String assignInputToVariable_Right = "";
		String assignInputToVariable = "";

		for (Declaration nonStructComponent : structInput.getNonStructComponentList()) {
			String assignInputToVariable_temp = "";
			String nonStructComponentName = nonStructComponent.getDeclarationName();
			assignInputToVariable_Left = blockInputName + "." + nonStructComponentName;
			assignInputToVariable_Right = getGeneratedInputName(structInput).replaceAll("_inputs$",
					"_" + nonStructComponentName + "_inputs");

			assignInputToVariable_temp = assignInputToVariable_Left + " = " + assignInputToVariable_Right;
			assignInputToVariable = assignInputToVariable + "\n\t" + assignInputToVariable_temp;
		}

		updateBlockInputUpdateFunctionBody(assignInputToVariable);
	}

	private void constructBlockInputUpdateFunctionArrayStructInputs(StructDeclaration structInput) {
		String blockInputName = structInput.getDeclarationName().replaceAll("^\\*", "") + "_block";
		String arraySize[] = structInput.getArraySize().split(",");
		String assignValuesToArrayInput = "";
		String assignValueToComponent = "";
		String assignValuesToArrayInput_LoopCounterDeclaration = "";
		String assignValuesToArrayInput_ForLoopHead = "";
		String assignValuesToArrayInput_ForLoopTail = "";

		for (Declaration nonStructComponent : structInput.getNonStructComponentList()) {
			int counterNumber = 0;
			String nonStructComponentName = nonStructComponent.getDeclarationName();
			String assignValueToStructComponent_Left = blockInputName;
			String assignValueToStructComponent_Right = getGeneratedInputName(structInput).replaceAll("_inputs$",
					"_" + nonStructComponentName + "_inputs") + "[blockInputUpdate_i]";
			String assignValuesToArrayInput_LoopCounterDeclaration_temp = "";
			String assignValuesToArrayInput_ForLoopHead_temp = "";
			String assignValuesToArrayInput_ForLoopTail_temp = "";

			for (int i = 0; i < arraySize.length; i++) {
				String counter = blockInputName + "_counter" + counterNumber;

				assignValuesToArrayInput_LoopCounterDeclaration_temp = assignValuesToArrayInput_LoopCounterDeclaration_temp
						+ "int " + counter + ";\n";
				assignValuesToArrayInput_ForLoopHead_temp = assignValuesToArrayInput_ForLoopHead_temp + "\tfor("
						+ counter + "=0;" + counter + "<" + arraySize[i] + ";" + counter + "++){\n";

				assignValueToStructComponent_Left = assignValueToStructComponent_Left + "[" + counter + "]";
				assignValueToStructComponent_Right = assignValueToStructComponent_Right + "[" + counter + "]";

				assignValuesToArrayInput_ForLoopTail_temp = assignValuesToArrayInput_ForLoopTail_temp + "\t}\n";
				counterNumber++;
			}

			assignValueToStructComponent_Left = assignValueToStructComponent_Left + "." + nonStructComponentName;

			assignValueToComponent = assignValueToComponent + "\t\t" + assignValueToStructComponent_Left + "="
					+ assignValueToStructComponent_Right + ";\n";

			assignValuesToArrayInput_LoopCounterDeclaration = assignValuesToArrayInput_LoopCounterDeclaration_temp;
			assignValuesToArrayInput_ForLoopHead = assignValuesToArrayInput_ForLoopHead_temp;
			assignValuesToArrayInput_ForLoopTail = assignValuesToArrayInput_ForLoopTail_temp;
		}

		assignValuesToArrayInput = assignValuesToArrayInput_LoopCounterDeclaration
				+ assignValuesToArrayInput_ForLoopHead + assignValueToComponent + assignValuesToArrayInput_ForLoopTail;

		updateBlockInputUpdateFunctionBody(assignValuesToArrayInput);
	}

	private void constructBlockInputUpdateFunctionForStructInputs() {
		for (StructDeclaration structInput : this.structInputList) {
			if (!getIsDeclarationAPointer(structInput)) {
				if (!structInput.getIsArray()) {
					constructBlockInputUpdateFunctionNonArrayStructInputs(structInput);
				} else {
					constructBlockInputUpdateFunctionArrayStructInputs(structInput);
				}
			}
		}
	}

	private void constructBlockInputUpdateFunction() {

		constructBlockInputUpdateFunctionForNonStructInputs();
		constructBlockInputUpdateFunctionForStructInputs();
	}

	private String getBlockInputsForwardDeclaration() {
		// String blockInputsForwardDeclaration = "";
		for (Declaration declaration : this.inputDeclarationList) {
			if (!declaration.getIsArray())
				blockInputsForwardDeclarationUpdate(
						declaration.getDeclarationType().replaceAll("register ", "") + " " + declaration.getDeclarationName() + "_block" + ";");
			else
				blockInputsForwardDeclarationUpdate(
						declaration.getDeclarationType().replaceAll("register ", "") + " " + declaration.getDeclarationName() + "_block" + "["
								+ declaration.getArraySize().replaceAll(",", "\\]\\[") + "];");
		}

		return this.blockInputsForwardDeclaration;
	}

	private void blockInputsForwardDeclarationUpdate(String update) {
		if (!this.blockInputsForwardDeclaration.contains(update))
			this.blockInputsForwardDeclaration = this.blockInputsForwardDeclaration + update + "\n";
	}

	private void updateBlockInputUpdateFunctionBody(String update) {
		if (!this.blockInputUpdateFunctionBody.contains(update))
			this.blockInputUpdateFunctionBody = this.blockInputUpdateFunctionBody + update + "\n\t";
	}


	public Boolean getIsStructComponentAStructOrUnion(String structComponentName) {
		Boolean isStructComponentAStruct = structComponentName.matches("((struct)+.*)|((union)+.*)");
		return isStructComponentAStruct;
	}

	
//	public void updateBlockInputUpdateFunctionBodyForStructInput(StructDeclaration structInput){
//		String structInputName = structInput.getDeclarationName();
//		if (!structInput.getIsArray()) {
//			if (!structInput.getDeclarationType().matches("(struct.*)|(union.*)")) {
//				/*
//				 * String update = this.structInputName + "." +
//				 * structInput.getStructComponentName() + " = " +
//				 * this.structInputName + "_" +
//				 * structInput.getDeclarationName().replaceAll("\\+",
//				 * "plus").replaceAll("\\" + "-", "minus") .replaceAll("^\\*",
//				 * "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
//				 * .replaceAll("\\(|\\)", "bra").replaceAll(",", "com") +
//				 * "_inputs[blockInputUpdate_i];";
//				 * updateBlockInputUpdateFunctionBody(update);
//				 */
//			} else {
//				String update = structInputName + "_block" + "." + structInput.getDeclarationName() + " = "
//						+ structInput.getDeclarationName().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
//								.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
//								.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
//						+ "_block";
//				updateBlockInputUpdateFunctionBody(update);
//			}
//		}
//	}



	public void writeBlockInputsForwardDeclarationToInputsSourceFile(Formatter inputsSourceFileFormatter) {
		inputsSourceFileFormatter.format("%s", "/*Forward declaration*/\n");
		inputsSourceFileFormatter.format("%s", "\n" + this.blockInputsForwardDeclaration + " \n");
	}

	public void writeBlockInputUpdateFunctionToInputsSourceFile(Formatter inputsSourceFileFormatter)
			throws IOException {
		if (this.wcetTargetInfo.isPreparingToGettingPointerValues() || this.wcetTargetInfo.isGettingPointerValues()) {
			inputsSourceFileFormatter.format("%s", "/*Block input update*/\n");
			inputsSourceFileFormatter.format("%s", "\nvoid blockInputUpdate(int blockInputUpdate_i) \n{\n\t"
					+ this.blockInputUpdateFunctionBody + " \n");
			inputsSourceFileFormatter.format("%s", "\n} \n" + " \n");
		}
		if (this.wcetTargetInfo.isMakingTimingMeasurementModule() || this.wcetTargetInfo.isFinalStepToAdjustRcall() || this.wcetTargetInfo.isInsertingBlockContentToTimingMeasurementModule()) {
			writeUpdatedBlockInputUpdateFunctionForMakingTimingMeasurementModule(inputsSourceFileFormatter);
		}

	}


	public void writeBlockExecutionTimeMeasurementForPreparingToGettingPointerValues(
			Formatter inputsSourceFileFormatter) {
		inputsSourceFileFormatter.format("%s", "/*BLock execution time measurement*/\n");
		inputsSourceFileFormatter.format("%s", "void benchmarkBlock(void)\n");
		inputsSourceFileFormatter.format("%s", "{\n");
		inputsSourceFileFormatter.format("%s", "\n");
		inputsSourceFileFormatter.format("%s", "\n}");
	}
	
	public void writeIncludedHeaderFilesToInputsSourceFile(Formatter inputsSourceFileFormatter){
		inputsSourceFileFormatter.format("%s", "#include \"inputs.h\"\n");
		inputsSourceFileFormatter.format("%s",
				"#include \"" + this.originalFileAbsolutePath.replace(".c", ".h") + "\"\n" + " \n");
	}
	
	public void writeGeneratedInputToInputsSourceFile(Formatter inputsSourceFileFormatter) throws IOException{
		BufferedReader inputsTxtFileReader = new BufferedReader(new FileReader(this.workDirectoryAbsolutePath + "\\inputs.txt"));
		String inputsTxtFileReaderLine = "";
		while((inputsTxtFileReaderLine = inputsTxtFileReader.readLine()) != null){
			inputsSourceFileFormatter.format("%s", inputsTxtFileReaderLine + "\n\n");
		}
		
		inputsTxtFileReader.close();
	}
	
	public void constructSourseFileForPreparingToGettingPointerValues()
			throws IOException {
		System.out.println("\n Generating inputs.c file for blockR" + this.blockStartLineNumber + "...");
		Formatter inputsSourceFileFormatter = getInputsSourceFileFormatter();
		writeIncludedHeaderFilesToInputsSourceFile(inputsSourceFileFormatter);
		writeGeneratedInputToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputsForwardDeclarationToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputUpdateFunctionToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockExecutionTimeMeasurementForPreparingToGettingPointerValues(inputsSourceFileFormatter);
		inputsSourceFileFormatter.close();
		System.out.println("\ninputs.c file generated for blockR" + this.blockStartLineNumber + "!");
	}


	public void writeBlockExecutionTimeMeasurementForGettingPointerValues(Formatter inputsSourceFileFormatter)
			throws IOException {
		String register_address_r14r15 = this.mapFileReader.getAddress("register_address_r14r15");
		String stackpointer_address_block = this.mapFileReader.getAddress("stackpointer_address_block");

		inputsSourceFileFormatter.format("%s", "/*BLock execution time measurement*/\n");
		inputsSourceFileFormatter.format("%s", "void benchmarkBlock(void)\n");
		inputsSourceFileFormatter.format("%s", "{\n");
		inputsSourceFileFormatter.format("%s", "\tasm(\n");
		inputsSourceFileFormatter.format("%s", "\t/*store values in r14 and r15*/\n");
		inputsSourceFileFormatter.format("%s", "\n\t\"sts	0x" + register_address_r14r15 + ", r14\\n\\t\"");
		inputsSourceFileFormatter.format("%s", "\n\t\"sts	0x"
				+ Integer.toHexString(Integer.parseInt(register_address_r14r15, 16) + 1) + ", r15\\n\\t\"");

		inputsSourceFileFormatter.format("%s", "\t/*store stack pointer to stackpointer_address_block*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"in	r14, 0x3d\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"in	r15, 0x3e\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\n\t\"sts	0x" + stackpointer_address_block + ", r14\\n\\t\"");
		inputsSourceFileFormatter.format("%s", "\n\t\"sts	0x"
				+ Integer.toHexString(Integer.parseInt(stackpointer_address_block, 16) + 1) + ", r15\\n\\t\"");

		inputsSourceFileFormatter.format("%s", "\t/*set the values in r14 and r15 back*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r14, 0x" + register_address_r14r15 + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r14, 0x"
				+ Integer.toHexString(Integer.parseInt(register_address_r14r15, 16) + 1) + "\\n\\t\"\n");

		inputsSourceFileFormatter.format("%s", "\t);\n");

		inputsSourceFileFormatter.format("%s", "\n");
		inputsSourceFileFormatter.format("%s", "\n}");
	}

	public void constructSourseFileForGettingPointerValues() throws IOException {
		System.out.println("\n Generating inputs.c file for blockR" + this.blockStartLineNumber + "...");
		Formatter inputsSourceFileFormatter = getInputsSourceFileFormatter();
		writeIncludedHeaderFilesToInputsSourceFile(inputsSourceFileFormatter);
		writeGeneratedInputToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputsForwardDeclarationToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputUpdateFunctionToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockExecutionTimeMeasurementForGettingPointerValues(inputsSourceFileFormatter);
		inputsSourceFileFormatter.close();
		System.out.println("\ninputs.c file generated for blockR" + this.blockStartLineNumber + "!");
	}

	public void getAndSetBlockAndOriginalAddressesForGlobalInputs() throws IOException {
		for (Declaration declaration : this.inputDeclarationList) {
			String inputNameBlock = declaration.getDeclarationName() + "_block";
			String inputNameOriginal = declaration.getDeclarationName();
			String inputAddressBlock = this.mapFileReader.getAddress(inputNameBlock);
			String inputAddressOriginal = this.mapFileReader.getAddress(inputNameOriginal);
			declaration.setBlockAddress(inputAddressBlock);
			declaration.setOriginalAddress(inputAddressOriginal);
		}
	}

	private void getAndSetInitializationAssemblyForLocalInputs() throws IOException {
		for (Declaration input : this.inputDeclarationList) {
			String initializationAssembly = this.lssFileReader.getInitializationAssembly(input);
			input.setInitializationAssembly(initializationAssembly);

		}
	}

	public void writeCodeToStoreRegisterValuesToDedicatedCorrespondentMemorySpace(Formatter inputsSourceFileFormatter)
			throws IOException {
		String register_address_block = this.mapFileReader.getAddress("register_address_block");

		inputsSourceFileFormatter.format("%s", "\t/*store register values to register_address_block*/\n");
		for (int r = 0; r < this.processorRegisterLength; r++) {
			inputsSourceFileFormatter.format("%s", "\t\"sts	0x"
					+ Integer.toHexString(Integer.parseInt(register_address_block, 16) + r) + ", r" + r + "\\n\\t\"\n");
		}
	}

	public void writeCodeToStoreBlockStackPointerValueToDedicatedCorrespondentMemorySpace(
			Formatter inputsSourceFileFormatter) throws IOException {
		String stackpointer_address_block = this.mapFileReader.getAddress("stackpointer_address_block");

		inputsSourceFileFormatter.format("%s", "\t/*store stack pointer to stackpointer_address_block*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"in	r14, 0x3d\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"in	r15, 0x3e\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"sts	0x" + stackpointer_address_block + ", r14\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"sts	0x"
				+ Integer.toHexString(Integer.parseInt(stackpointer_address_block, 16) + 1) + ", r15\\n\\t\"\n");
	}

	public void writeCodeToStoreTheStackBeforeExecutingTheBlockCodeToDedicatedCorrespondentMemorySpace(
			Formatter inputsSourceFileFormatter) throws IOException {
		String stackpointer_address_block = this.mapFileReader.getAddress("stackpointer_address_block");
		String stackBeforeExecutingTheBlockCodeStartAddressInMeasurementModule = this.mapFileReader
				.getAddress("stack_address_block");
		int stackBeforeExecutingTheBlockCodeInMeasurementModuleStackLength = (Integer.parseInt(this.framePointerValue,
				16) - Integer.parseInt(this.stackPointerValueOfBlockCode, 16) + 1);

		inputsSourceFileFormatter.format("%s", "\t/*store the stack to stack_address_block*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r24, 0x00\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r25, 0x00\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r26, 0x" + stackpointer_address_block + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r27, 0x"
				+ Integer.toHexString(Integer.parseInt(stackpointer_address_block, 16) + 1) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r30, 0x"
				+ stackBeforeExecutingTheBlockCodeStartAddressInMeasurementModule.substring(2, 4) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r31, 0x"
				+ stackBeforeExecutingTheBlockCodeStartAddressInMeasurementModule.substring(0, 2) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ld     r14, X+\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"st     Z+,	r14\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"adiw	r24, 0x01\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s",
				"\t\"cpi	r24, 0x" + stackBeforeExecutingTheBlockCodeInMeasurementModuleStackLength + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"brne	.-10\\n\\t\"\n");

	}

	public void writeCodeToSetTheStackBeforeExecutingTheBlockCodeInTheMeasurementModuleToStackBeforeExecutingTheBlockCodeInTheOriginalCode(
			Formatter inputsSourceFileFormatter) throws IOException {

		String stackpointer_address_org = this.mapFileReader.getAddress("stackpointer_address_org");
		String stackBeforeExecutingTheBlockCodeStartAddressInOriginalCode = this.mapFileReader
				.getAddress("stack_address_org");
		int stackBeforeExecutingTheBlockCodeInOrginalCodeStackLength = (Integer.parseInt(this.framePointerValue, 16)
				- Integer.parseInt(this.stackPointerValueOfOriginalCode, 16) + 1);

		inputsSourceFileFormatter.format("%s", "\t/*set the stack to stack_address_org*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r24, 0x00\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r25, 0x00\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r26, 0x" + stackpointer_address_org + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r27, 0x"
				+ Integer.toHexString(Integer.parseInt(stackpointer_address_org, 16) + 1) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r30, 0x"
				+ stackBeforeExecutingTheBlockCodeStartAddressInOriginalCode.substring(2, 4) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r31, 0x"
				+ stackBeforeExecutingTheBlockCodeStartAddressInOriginalCode.substring(0, 2) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ld     r14, Z+\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"st     X+,	r14\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"adiw	r24, 0x01\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s",
				"\t\"cpi	r24, 0x" + stackBeforeExecutingTheBlockCodeInOrginalCodeStackLength + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"brne	.-10\\n\\t\"\n");

	}

	public void writeCodeToSetRegisterValuesBeforeExecutingTheBlockCodeInTheMeasurementModuleToRegisterValuesBeforeeExecutingTheBlockCodeInTheOriginalCode(
			Formatter inputsSourceFileFormatter) throws IOException {
		String register_address_org = this.mapFileReader.getAddress("register_address_org");

		inputsSourceFileFormatter.format("%s", "\t/*set register values to register_address_org*/\n");
		for (int r = 0; r < this.processorRegisterLength; r++) {
			inputsSourceFileFormatter.format("%s", "\t\"lds	r" + r + ", 0x"
					+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + r) + "\\n\\t\"\n");
		}
	}

	public void writeCodeToStartExecutionCycleCounter_Tic(Formatter inputsSourceFileFormatter) throws IOException {
		String register_address_org = this.mapFileReader.getAddress("register_address_org");
		String registerR24DedicatedMemoryAddressInTheOriginalCode = Integer
				.toHexString(Integer.parseInt(register_address_org, 16) + 24);

		inputsSourceFileFormatter.format("%s", "\t/*Tic*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r24, 0x10\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"out	0x08, r24\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s",
				"\t\"lds	r24, 0x" + registerR24DedicatedMemoryAddressInTheOriginalCode + "\\n\\t\"\n");
	}

	public void writeCodeToStopExecutionCycleCounter_Toc(Formatter inputsSourceFileFormatter) throws IOException {
		inputsSourceFileFormatter.format("%s", "\t/*Toc*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"out	0x08, r1\\n\\t\"\n");
	}

	public void writeCodeToSetTheStackBacktoTheStateBeforeExecutingTheBlockCodeInTheMeasurementModule(
			Formatter inputsSourceFileFormatter) throws IOException {
		String stackpointer_address_block = this.mapFileReader.getAddress("stackpointer_address_block");
		String blockStackMemorySpaceStartAddress = this.mapFileReader.getAddress("stack_address_block");
		int stackBeforeExecutingTheBlockCodeInMeasurementModuleStackLength = (Integer.parseInt(this.framePointerValue,
				16) - Integer.parseInt(this.stackPointerValueOfBlockCode, 16) + 1);

		inputsSourceFileFormatter.format("%s", "\t/*set stack back to stack_address_block*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r24, 0x00\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ldi	r25, 0x00\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r26, 0x" + stackpointer_address_block + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r27, 0x"
				+ Integer.toHexString(Integer.parseInt(stackpointer_address_block, 16) + 1) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s",
				"\t\"ldi	r30, 0x" + blockStackMemorySpaceStartAddress.substring(2, 4) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s",
				"\t\"ldi	r31, 0x" + blockStackMemorySpaceStartAddress.substring(0, 2) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"ld     r14, Z+\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"st     X+,	r14\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"adiw	r24, 0x01\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s",
				"\t\"cpi	r24, 0x" + stackBeforeExecutingTheBlockCodeInMeasurementModuleStackLength + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"brne	.-10\\n\\t\"\n");
	}

	public void writeCodeToSetRegisterValuesBacktToTheStateBefore(Formatter inputsSourceFileFormatter)
			throws IOException {
		String register_address_block = this.mapFileReader.getAddress("register_address_block");

		inputsSourceFileFormatter.format("%s", "\t/*restore register values to register_address_block*/\n");
		for (int r = 0; r < this.processorRegisterLength; r++) {
			inputsSourceFileFormatter.format("%s", "\t\"lds	r" + r + ", 0x"
					+ Integer.toHexString(Integer.parseInt(register_address_block, 16) + r) + "\\n\\t\"\n");
		}
	}

	public void writeCodeToRestoreValuesInRegister14And15(Formatter inputsSourceFileFormatter) throws IOException {
		String register_address_block = this.mapFileReader.getAddress("register_address_block");

		inputsSourceFileFormatter.format("%s", "\t/*restore the values in registers r14 and r15*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r14, 0x"
				+ Integer.toHexString(Integer.parseInt(register_address_block, 16) + 14) + "\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"lds	r15, 0x"
				+ Integer.toHexString(Integer.parseInt(register_address_block, 16) + 15) + "\\n\\t\"\n");
	}

	public void insertTheAssemblyBlockContentToTheMeasurementModule(Formatter inputsSourceFileFormatter)
			throws IOException {
		String blockContentAssemblyAnnotatedWithRcallTargetAddress = this.lssFileReader
				.getBlockContentAssemblyAnnotatedWithRcallTargetAddress();
		inputsSourceFileFormatter.format("%s", "\t/*Block content*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"BlockContent_Start:\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", blockContentAssemblyAnnotatedWithRcallTargetAddress);
		inputsSourceFileFormatter.format("%s", "\t\"BlockContent_End:\\n\\t\"\n");
	}

	public void writeBlockExecutionTimeMeasurementForMakingTimingMeasurementModule(Formatter inputsSourceFileFormatter)
			throws IOException {
		inputsSourceFileFormatter.format("%s", "/*BLock execution time measurement*/\n");
		inputsSourceFileFormatter.format("%s", "void benchmarkBlock(void)\n");
		inputsSourceFileFormatter.format("%s", "{\n");
		inputsSourceFileFormatter.format("%s", "\tasm(\n");

		writeCodeToStoreRegisterValuesToDedicatedCorrespondentMemorySpace(inputsSourceFileFormatter);
		writeCodeToStoreBlockStackPointerValueToDedicatedCorrespondentMemorySpace(inputsSourceFileFormatter);
		writeCodeToStoreTheStackBeforeExecutingTheBlockCodeToDedicatedCorrespondentMemorySpace(
				inputsSourceFileFormatter);
		writeCodeToSetTheStackBeforeExecutingTheBlockCodeInTheMeasurementModuleToStackBeforeExecutingTheBlockCodeInTheOriginalCode(
				inputsSourceFileFormatter);
		writeCodeToSetRegisterValuesBeforeExecutingTheBlockCodeInTheMeasurementModuleToRegisterValuesBeforeeExecutingTheBlockCodeInTheOriginalCode(
				inputsSourceFileFormatter);
		writeCodeToStartExecutionCycleCounter_Tic(inputsSourceFileFormatter);
		inputsSourceFileFormatter.format("%s", "\t/*Block content*/\n");
		inputsSourceFileFormatter.format("%s", "\t\"BlockContent_Start:\\n\\t\"\n");
		inputsSourceFileFormatter.format("%s", "\t\"BlockContent_End:\\n\\t\"\n");
		writeCodeToStopExecutionCycleCounter_Toc(inputsSourceFileFormatter);
		writeCodeToSetTheStackBacktoTheStateBeforeExecutingTheBlockCodeInTheMeasurementModule(
				inputsSourceFileFormatter);
		writeCodeToSetRegisterValuesBacktToTheStateBefore(inputsSourceFileFormatter);
		writeCodeToRestoreValuesInRegister14And15(inputsSourceFileFormatter);

		inputsSourceFileFormatter.format("%s", "\t);\n");
		inputsSourceFileFormatter.format("%s", "\n");
		inputsSourceFileFormatter.format("%s", "\n}");

	}

	public Boolean getIsInputGlobal(Declaration input) {
		Boolean isInputGlobal = (input.getOriginalAddress() != "");
		return isInputGlobal;
	}

	public void writeCodeToAssignGeneratedInputValueToGlobalInput(Declaration input,
			Formatter inputsSourceFileFormatter) throws IOException {

		
		int inputDataMemorySize = this.mapFileReader.getMemorySpaceSizeOfInput(input);
		
		for (int variablelengthcounter = 0; variablelengthcounter < inputDataMemorySize; variablelengthcounter++) {
			
			
			inputsSourceFileFormatter.format("%s",
					"\t\"lds	" + "r14" + ", 0x"
							+ Integer.toHexString(Integer.parseInt(input.getBlockAddress(), 16) + variablelengthcounter)
							+ "\\n\\t\"\n");
			inputsSourceFileFormatter.format("%s",
					"\t\"sts	" + "0x"
							+ Integer.toHexString(
									Integer.parseInt(input.getOriginalAddress(), 16) + variablelengthcounter)
							+ ", r14" + "\\n\\t\"\n");
		}
	}

	public Boolean getIsInputLocal(Declaration input) {
		Boolean isInputLocal = (input.getInitializationAssembly() != "");
		return isInputLocal;
	}

	public void writeCodeToAssignGeneratedInputValueToLocalInput(Declaration input, Formatter inputsSourceFileFormatter)
			throws IOException {

		String stack_address_org = this.mapFileReader.getAddress("stack_address_org");
		String register_address_org = this.mapFileReader.getAddress("register_address_org");
		String initializationAssembly = input.getInitializationAssembly();
		String[] initializationAssembly_Split = initializationAssembly.split("\\n");

		int numberOfInitializationAssemblyLines = initializationAssembly_Split.length;
		int lineCounter = 0;
		int stdInstructionCounter = 0;

		for (lineCounter = 0; lineCounter < numberOfInitializationAssemblyLines; lineCounter++) {
			String initializationAssemblyLineCut = initializationAssembly_Split[lineCounter].replaceAll(",.*", "")
					.replaceAll("\\s+", " ");

			String[] initializationAssemblyLineCut_Split = initializationAssemblyLineCut.split("\\s");
			String register = initializationAssemblyLineCut_Split[initializationAssemblyLineCut_Split.length - 1];
		
			int registerNumber = initializationAssemblyLineCut_Split[initializationAssemblyLineCut_Split.length - 1]
					.matches("^r\\d*")
							? Integer.parseInt(
									initializationAssemblyLineCut_Split[initializationAssemblyLineCut_Split.length - 1]
											.replaceAll("r", ""))
							: 0;

			String assemblyInstruction = initializationAssemblyLineCut_Split[initializationAssemblyLineCut_Split.length
					- 2];

			if (assemblyInstruction.matches("ldi|lds|ld|ldd|mov")) {
				inputsSourceFileFormatter.format("%s",
						"\t\"lds	" + register + ", 0x"
								+ Integer.toHexString(Integer.parseInt(input.getBlockAddress(), 16) + lineCounter)
								+ "\\n\\t\"\n");
				inputsSourceFileFormatter.format("%s",
						"\t\"sts	0x"
								+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + registerNumber)
								+ ", " + register + "\\n\\t\"\n");

			} else if (assemblyInstruction.matches("movw")) {

				inputsSourceFileFormatter.format("%s",
						"\t\"lds	" + register + ", 0x"
								+ Integer.toHexString(Integer.parseInt(input.getBlockAddress(), 16) + lineCounter)
								+ "\\n\\t\"\n");
				inputsSourceFileFormatter.format("%s",
						"\t\"lds	" + "r" + ((lineCounter + 1) * 2 - 1) + ", 0x"
								+ Integer.toHexString(
										Integer.parseInt(input.getBlockAddress(), 16) + ((lineCounter + 1) * 2 - 1))
								+ "\\n\\t\"\n");
				inputsSourceFileFormatter.format("%s",
						"\t\"sts	0x"
								+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + registerNumber)
								+ ", s" + register + "\\n\\t\"\n");
				inputsSourceFileFormatter.format("%s",
						"\t\"sts	0x"
								+ Integer.toHexString(
										Integer.parseInt(register_address_org, 16) + ((lineCounter + 1) * 2 - 1))
								+ "," + "r" + ((lineCounter + 1) * 2 - 1) + "\\n\\t\"\n");

			} else if (assemblyInstruction.matches("st|std")) {

				if (register.matches("^Y.*")) {

					inputsSourceFileFormatter.format("%s",
							"\t\"ldi	r28, 0x" + stack_address_org.substring(2, 4) + "\\n\\t\"\n");
					inputsSourceFileFormatter.format("%s",
							"\t\"ldi	r29, 0x" + stack_address_org.substring(0, 2) + "\\n\\t\"\n");

					inputsSourceFileFormatter.format("%s",
							"\t\"lds	" + "r14" + ", 0x"
									+ Integer.toHexString(
											Integer.parseInt(input.getBlockAddress(), 16) + stdInstructionCounter)
									+ "\\n\\t\"\n");
					inputsSourceFileFormatter.format("%s",
							"\t\"" + assemblyInstruction + " " + register + ", r14" + "\\n\\t\"\n");
					stdInstructionCounter++;

				} else {
					System.err.println("Wrong stack register!");
				}

			} else {
				System.err.println("Unexpected assembly instruction!");
			}
		}

	}

	public void writeUpdatedBlockInputUpdateFunctionForMakingTimingMeasurementModule(
			Formatter inputsSourceFileFormatter) throws IOException {

		getAndSetBlockAndOriginalAddressesForGlobalInputs();
		getAndSetInitializationAssemblyForLocalInputs();

		// inputUpdate function
		inputsSourceFileFormatter.format("%s",
				"\nvoid blockInputUpdate(int blockInputUpdate_i) \n{\n\t" + this.blockInputUpdateFunctionBody + " \n");

		inputsSourceFileFormatter.format("%s", "\tasm(\n");

		writeCodeToStoreRegisterValuesToDedicatedCorrespondentMemorySpace(inputsSourceFileFormatter);

		inputsSourceFileFormatter.format("%s", "\t/*Assign the inputs values to the block*/\n");
		for (Declaration input : this.inputDeclarationList) {

			if (getIsInputGlobal(input)) {
				writeCodeToAssignGeneratedInputValueToGlobalInput(input, inputsSourceFileFormatter);
			} else if (getIsInputLocal(input)) {
				
				writeCodeToAssignGeneratedInputValueToLocalInput(input, inputsSourceFileFormatter);
			} /*
				 * else if(!getIsInputGlobal(input)&&!getIsInputLocal(input)) {
				 * IOException e = new IOException(); throw e; }
				 */
		}

		// restore register values from register_address_block
		writeCodeToSetRegisterValuesBacktToTheStateBefore(inputsSourceFileFormatter);

		inputsSourceFileFormatter.format("%s", "\t);\n");
		inputsSourceFileFormatter.format("%s", "\n} \n" + " \n");
	}

	public void constructSourseFileForMakingTimingMeasurementModule()
			throws IOException {
		System.out.println("\n Generating inputs.c file for blockR" + this.blockStartLineNumber + "...");
		Formatter inputsSourceFileFormatter = getInputsSourceFileFormatter();
		writeIncludedHeaderFilesToInputsSourceFile(inputsSourceFileFormatter);
		writeGeneratedInputToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputsForwardDeclarationToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputUpdateFunctionToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockExecutionTimeMeasurementForMakingTimingMeasurementModule(inputsSourceFileFormatter);
		inputsSourceFileFormatter.close();
		System.out.println("\ninputs.c file generated for blockR" + this.blockStartLineNumber + "!");
	}
	
	public void writeBlockExecutionTimeMeasurementForInsertingBlockContentToTimingMeasurementModule(Formatter inputsSourceFileFormatter)
			throws IOException {
		inputsSourceFileFormatter.format("%s", "/*BLock execution time measurement*/\n");
		inputsSourceFileFormatter.format("%s", "void benchmarkBlock(void)\n");
		inputsSourceFileFormatter.format("%s", "{\n");
		inputsSourceFileFormatter.format("%s", "\tasm(\n");

		writeCodeToStoreRegisterValuesToDedicatedCorrespondentMemorySpace(inputsSourceFileFormatter);
		writeCodeToStoreBlockStackPointerValueToDedicatedCorrespondentMemorySpace(inputsSourceFileFormatter);
		writeCodeToStoreTheStackBeforeExecutingTheBlockCodeToDedicatedCorrespondentMemorySpace(
				inputsSourceFileFormatter);
		writeCodeToSetTheStackBeforeExecutingTheBlockCodeInTheMeasurementModuleToStackBeforeExecutingTheBlockCodeInTheOriginalCode(
				inputsSourceFileFormatter);
		writeCodeToSetRegisterValuesBeforeExecutingTheBlockCodeInTheMeasurementModuleToRegisterValuesBeforeeExecutingTheBlockCodeInTheOriginalCode(
				inputsSourceFileFormatter);
		writeCodeToStartExecutionCycleCounter_Tic(inputsSourceFileFormatter);
		insertTheAssemblyBlockContentToTheMeasurementModule(inputsSourceFileFormatter);
		writeCodeToStopExecutionCycleCounter_Toc(inputsSourceFileFormatter);
		writeCodeToSetTheStackBacktoTheStateBeforeExecutingTheBlockCodeInTheMeasurementModule(
				inputsSourceFileFormatter);
		writeCodeToSetRegisterValuesBacktToTheStateBefore(inputsSourceFileFormatter);
		writeCodeToRestoreValuesInRegister14And15(inputsSourceFileFormatter);

		inputsSourceFileFormatter.format("%s", "\t);\n");
		inputsSourceFileFormatter.format("%s", "\n");
		inputsSourceFileFormatter.format("%s", "\n}");

	}
	
	public void constructSourseFileForInsertingBlockContentToTimingMeasurementModule()
			throws IOException {
		System.out.println("\n Generating inputs.c file for blockR" + this.blockStartLineNumber + "...");
		Formatter inputsSourceFileFormatter = getInputsSourceFileFormatter();
		writeIncludedHeaderFilesToInputsSourceFile(inputsSourceFileFormatter);
		writeGeneratedInputToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputsForwardDeclarationToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockInputUpdateFunctionToInputsSourceFile(inputsSourceFileFormatter);
		writeBlockExecutionTimeMeasurementForInsertingBlockContentToTimingMeasurementModule(inputsSourceFileFormatter);
		inputsSourceFileFormatter.close();
		System.out.println("\ninputs.c file generated for blockR" + this.blockStartLineNumber + "!");
	}

	public void finalStepToAdjustRcall() throws IOException {
		System.out.println("\n Adjusting rcall target in inputs.c file for blockR" + this.blockStartLineNumber + "...");
		
		File inputsSourceFile = this.getInputsSourceFile();
		BufferedReader inputsFileReader = this.getInputsSourceFileReader();
		String inputsFileReaderLine = "";
		File inputsSourceFile_Temp = new File(this.avrFilesOutputDirectoryAbsolutePath + "\\inputs_temp.c");
		Formatter inputcformatter_temp = new Formatter(inputsSourceFile_Temp);
		Boolean isBlockContent = false;
		String rcallAdressesOfBlockContentInMeaurementModule = this.lssFileReader.getRcallAdressesOfBlockContentInMeaurementModule();
		String[] rcallAdressesOfBlockContentInMeaurementModule_Split = rcallAdressesOfBlockContentInMeaurementModule.split(";");

		int rcallCounter = 0;
		while ((inputsFileReaderLine = inputsFileReader.readLine()) != null) {
			if (inputsFileReaderLine.matches("\\s*\"BlockContent_Start:.*"))
				isBlockContent = true;

			if (inputsFileReaderLine.matches("\\s*\"BlockContent_End:.*"))
				isBlockContent = false;

			if (isBlockContent) {
				if (inputsFileReaderLine.contains("rcall")) {
					int rcallDistance = 0;
					String rcallTargetAddress = inputsFileReaderLine.replaceAll("^.*//0x", "");
					String assemblyCodeAddress = rcallAdressesOfBlockContentInMeaurementModule_Split[rcallCounter];
						
					rcallDistance = Integer.parseInt(rcallTargetAddress, 16) - Integer.parseInt(assemblyCodeAddress, 16) - 2;
					if (rcallDistance > 0)
						inputsFileReaderLine = "\t\"rcall .+" + rcallDistance +"\\n\\t\"" + "    //0x" + rcallTargetAddress;
					else
						inputsFileReaderLine = "\t\"rcall ." + rcallDistance  + "\\n\\t\"" + "    //0x" + rcallTargetAddress;
					
					rcallCounter++;
				}
			}
			inputcformatter_temp.format("%s", inputsFileReaderLine + "\n");
		}
		
		inputcformatter_temp.close();
		inputsFileReader.close();
		inputsSourceFile.delete();
		inputsSourceFile_Temp.renameTo(inputsSourceFile);
		System.out.println("\nrcall target adjusted for inputs.c file for blockR" + this.blockStartLineNumber + "!");
	}
	

	public File getInputsSourceFile() {
		File inputsSourceFile = new File(this.workDirectoryAbsolutePath + "\\inputs.c");
		return inputsSourceFile;
	}

	public BufferedReader getInputsSourceFileReader() throws FileNotFoundException{
		BufferedReader inputsSourceFileReader = new BufferedReader(new FileReader(getInputsSourceFile()));
		return inputsSourceFileReader;
	}
	
	public Formatter getInputsSourceFileFormatter() throws FileNotFoundException{
		Formatter inputsSourceFileFormatter = new Formatter(getInputsSourceFile());
		return inputsSourceFileFormatter;
	}
	
	public void logGeneratedInputs() throws IOException {

	}

}