import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import VariableClasses.Annotation;
import VariableClasses.Declaration;
import VariableClasses.StructDeclaration;
import VariableClasses.Variable;

public class InputGenerator {

	private String originalFileDirectoryAbsolutePath;
	private String originalFileAbsolutePath;
	private String annotationFileAbsolutePath;
	private String workDirectoryAbsolutePath;
	private String macrosForInputGeneration = "";
	private String blockFileAbsolutePath;
	private String storageclassspecifier = "typedef|extern|static|_Thread_local|auto|register";
	private String typequalifier = "restrict|volatile|_Atomic";
	private String structorunion = "struct|union";

	private int generatedInputLength;

	private Boolean logGeneratedInputsEnabledFlag;
	private File randomInputGenerator;
	private Formatter randomFormatter;

	private List<Variable> rightVariableList = new ArrayList<Variable>();
	private List<Annotation> annotationList = new ArrayList<Annotation>();
	private List<Declaration> inputDeclarationList = new ArrayList<Declaration>();
	private List<StructDeclaration> structDefinitationDeclarationList = new ArrayList<StructDeclaration>();
	private List<Declaration> nonStructInputList = new ArrayList<Declaration>();
	private List<StructDeclaration> structInputList = new ArrayList<StructDeclaration>();
	private WCETTargetInfo wcetTargetInfo;

	private OriginalFileReader originalFileReader;

	public InputGenerator(WCETTargetInfo wcetTargetInfo) throws IOException {
		readWcetTargetInfo(wcetTargetInfo);
		createAnnotationList();
		createInputDeclarationList();
		creatStructInputList();
		createNonStructInputList();
	}

	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.wcetTargetInfo = wcetTargetInfo;
		this.originalFileDirectoryAbsolutePath = wcetTargetInfo.getOriginalFileDirectoryAbsolutePath();
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.originalFileAbsolutePath = wcetTargetInfo.getOriginalFileAbsolutePath();
		this.annotationFileAbsolutePath = wcetTargetInfo.getAnnotationFileAbsolutePath();
		this.generatedInputLength = wcetTargetInfo.getGeneratedInputLength();
		this.blockFileAbsolutePath = wcetTargetInfo.getBlockFileAbsolutePath();
		this.logGeneratedInputsEnabledFlag = wcetTargetInfo.getLogGeneratedInputsEnabledFlag();
		this.originalFileReader = new OriginalFileReader(wcetTargetInfo);

	}

	public void createInputDeclarationList() throws IOException {
		List<Variable> rightVariableList = getRightVariableList();

		addDeclartionsOfRightVariablesToTheList(rightVariableList, this.inputDeclarationList);
	}

	public List<Variable> getRightVariableList() throws IOException {
		CobraCCodeParser blockCodeParser = getTheBlockCodeParser();
		List<Variable> rightVariableList = blockCodeParser.getRightVariableList();
		List<Variable> leftVariableList = getLeftVariableList();

		removeLeftVariablesFromRightVariableList(rightVariableList, leftVariableList);
		adjustVariableLineNumberFromBlockToLineNumberOfOriginalCode(rightVariableList);

		return rightVariableList;
	}

	private void removeLeftVariablesFromRightVariableList(List<Variable> rightVariableList,
			List<Variable> leftVariableList) throws IOException {
		List<Variable> removeablevariablelist = new ArrayList<Variable>();
		for (Variable rightVariable : rightVariableList) {
			if (getIsThisRightVariableRemoveableFromTheList(rightVariable, leftVariableList)) {
				removeablevariablelist.add(rightVariable);
			}
		}
		rightVariableList.removeAll(removeablevariablelist);
	}

	private void adjustVariableLineNumberFromBlockToLineNumberOfOriginalCode(List<Variable> variablelist)
			throws IOException {
		int blockStartLineNumber = this.wcetTargetInfo.getBlockStartLineNumber();
		int blockStartLineNumberOffSetInGeneratedBlockCodeContent = getBlockStartLineNumberOffSetInGeneratedBlockCodeContent();
		for (Variable Variable : variablelist) {
			Variable.setLineNumber(Variable.getLineNumber() + blockStartLineNumber
					- blockStartLineNumberOffSetInGeneratedBlockCodeContent);
		}
	}

	public int getBlockStartLineNumberOffSetInGeneratedBlockCodeContent() throws IOException {
		BufferedReader blockFileReader = getBlockFileReader();
		String blockFileReaderLine = "";
		int blockFileReaderLine_Counter = 0;
		boolean offsetIsFound = false;
		while ((blockFileReaderLine = blockFileReader.readLine()) != null && !offsetIsFound) {
			blockFileReaderLine_Counter++;

			if (blockFileReaderLine.equals("void benchmarkNode(void)"))
				offsetIsFound = true;
		}

		blockFileReader.close();
		blockFileReaderLine_Counter = blockFileReaderLine_Counter + 2;
		return blockFileReaderLine_Counter;

	}

	public BufferedReader getBlockFileReader() throws FileNotFoundException {
		File blockFile = getBlockFile();
		BufferedReader blockFileReader = new BufferedReader(new FileReader(blockFile));
		return blockFileReader;
	}

	public File getBlockFile() throws FileNotFoundException {
		File blockFile = new File(this.blockFileAbsolutePath);
		return blockFile;
	}

	private boolean getIsThisRightVariableRemoveableFromTheList(Variable rightVariable,
			List<Variable> leftVariableList) {
		boolean isRemoveable = false;
		for (Variable leftVariable : leftVariableList) {
			if (isRemoveable == false)
				isRemoveable = getIsThisRightVariableActuallyAnLeftVariable(rightVariable, leftVariable);
		}
		return isRemoveable;
	}

	private boolean getIsThisRightVariableActuallyAnLeftVariable(Variable rightVariable, Variable leftVariable) {
		boolean isSameName = getIsThisRightVariableHavingSameNameAsThisLeftVariable(rightVariable, leftVariable);
		boolean isAnLeftVariable = false;
		if (isSameName) {
			isAnLeftVariable = rightVariable.getLineNumber() > leftVariable.getLineNumber();

		}
		return isAnLeftVariable;
	}

	private boolean getIsThisRightVariableHavingSameNameAsThisLeftVariable(Variable rightVariable,
			Variable leftVariable) {
		boolean isThisRightVariableHavingSameNameAsThisLeftVariable = rightVariable.getVariableName()
				.equals(leftVariable.getVariableName());
		return isThisRightVariableHavingSameNameAsThisLeftVariable;
	}

	public List<Variable> getLeftVariableList() throws IOException {
		CobraCCodeParser blockCodeParser = getTheBlockCodeParser();
		List<Variable> leftVariableList = blockCodeParser.getLeftVariableList();
		return leftVariableList;
	}

	public CobraCCodeParser getTheBlockCodeParser() throws IOException {
		return parseTheBlockCode();
	}

	public CobraCCodeParser parseTheBlockCode() throws IOException {
		String blockContent = getTheContentOfTheCodeBlock();
		return (new CobraCCodeParser(blockContent));
	}

	public String getTheContentOfTheCodeBlock() throws IOException {
		return (new String(Files.readAllBytes(Paths.get(this.blockFileAbsolutePath))));
	}

	public void addDeclartionsOfRightVariablesToTheList(List<Variable> rightVariableList,
			List<Declaration> declarationListOfTheBlock) throws IOException {
		CobraCCodeParser originalFileParser = getTheOriginalFileParser();
		List<Declaration> declarationListOfTheCompleteOriginalFile = originalFileParser.getDeclarationList();

		removeDeclarationsAfterTheStartLineOfTheBlock(declarationListOfTheCompleteOriginalFile);
		removeDeclarationsBeforeTheLastOneUsingSameName(declarationListOfTheCompleteOriginalFile);

		for (Variable rightVariable : rightVariableList) {

			if (getIsVariableAPointer(rightVariable)) {
				Variable pointerTargetVariable = this.originalFileReader.getPointerTargetVariable(rightVariable);
				addRightVariableToDeclarationListOfTheBlock(pointerTargetVariable,
						declarationListOfTheCompleteOriginalFile, declarationListOfTheBlock);
			} else {
				addRightVariableToDeclarationListOfTheBlock(rightVariable, declarationListOfTheCompleteOriginalFile,
						declarationListOfTheBlock);
			}
		}
	}

	private void removeDeclarationsAfterTheStartLineOfTheBlock(List<Declaration> declarationList) {
		int blockStartLineNumber = this.wcetTargetInfo.getBlockStartLineNumber();
		List<Declaration> removeableDeclarationList = new ArrayList<Declaration>();
		for (Declaration declaration : declarationList) {
			if (getIsDeclarationAfterTheStartLineOfTheBlock(declaration, blockStartLineNumber))
				removeableDeclarationList.add(declaration);
		}
		declarationList.removeAll(removeableDeclarationList);
	}

	private void removeDeclarationsBeforeTheLastOneUsingSameName(List<Declaration> declarationList) {
		List<Declaration> removeableDeclarationList = new ArrayList<Declaration>();
		for (Declaration declaration : declarationList) {
			if (getIsDeclarationTheLastOneWithSameNameBeforeTheStartLineOfTheBlock(declaration,
					declarationList) == false)
				removeableDeclarationList.add(declaration);
		}
		declarationList.removeAll(removeableDeclarationList);
	}

	private boolean getIsVariableAPointer(Variable variable) {
		Boolean isPointer = variable.getVariableName().matches("\\*\\w*");
		return isPointer;

	}

	private boolean getIsDeclarationTheLastOneWithSameNameBeforeTheStartLineOfTheBlock(Declaration declaration,
			List<Declaration> declarationList) {
		boolean isTheLastOne = true;
		for (Declaration declarationCompare : declarationList) {
			if (declaration.getDeclarationName().equals(declarationCompare.getDeclarationName())
					&& (declarationCompare.getLineNumber() > declaration.getLineNumber()))
				isTheLastOne = false;
		}
		return isTheLastOne;
	}

	private boolean getIsDeclarationAfterTheStartLineOfTheBlock(Declaration declaration, int blockStartLineNumber) {
		boolean isDeclarationAfterTheStartLineOfTheBlock = (declaration.getLineNumber() >= blockStartLineNumber);
		return isDeclarationAfterTheStartLineOfTheBlock;
	}

	public CobraCCodeParser getTheOriginalFileParser() throws IOException {
		return parseTheOriginalFile();
	}

	private void addRightVariableToDeclarationListOfTheBlock(Variable rightVariable,
			List<Declaration> declarationListOfTheCompleteOriginalFile, List<Declaration> declarationListOfTheBlock) {
		for (Declaration declaration : declarationListOfTheCompleteOriginalFile) {
			// there is no need to add loop counters
			if (getIsDeclarationForThisRightVariable(declaration, rightVariable)
					&& !getIsDeclarationAlreadyExistInDeclarationList(declaration, declarationListOfTheBlock)
					&& !rightVariable.getIsForLoopCounter() && !getIsDeclarationAPointer(declaration)) {
				declarationListOfTheBlock.add(declaration);
			}
		}
	}

	private boolean getIsDeclarationAlreadyExistInDeclarationList(Declaration declaration,
			List<Declaration> declarationList) {
		boolean isDeclarationAlreadyExistInDeclarationList = false;
		for (Declaration declarationInList : declarationList) {
			if (declaration.getDeclarationType().equals(declarationInList.getDeclarationType())
					&& declaration.getDeclarationName().equals(declarationInList.getDeclarationName())
					&& declaration.getLineNumber().equals(declarationInList.getLineNumber()))
				isDeclarationAlreadyExistInDeclarationList = true;
		}

		return isDeclarationAlreadyExistInDeclarationList;

	}

	private boolean getIsDeclarationForThisRightVariable(Declaration declaration, Variable rightVariable) {
		boolean isDeclarationForThisRightVariable = (rightVariable.getVariableName().replaceAll("\\[.*\\]", "")
				.replaceAll("^\\*", "").equals(declaration.getDeclarationName().replaceAll("^\\*", ""))
				&& (declaration.getLineNumber() <= rightVariable.getLineNumber()));

		return isDeclarationForThisRightVariable;

	}

	public CobraCCodeParser parseTheOriginalFile() throws IOException {
		String originalFileContent = getTheContentOfTheOriginalFile();
		CobraCCodeParser originalFileParser = new CobraCCodeParser(originalFileContent);
		return originalFileParser;
	}

	public String getTheContentOfTheOriginalFile() throws IOException {
		String originalFileContent = new String(Files.readAllBytes(Paths.get(this.originalFileAbsolutePath)));
		return originalFileContent;
	}

	public Formatter createInputsLogFileFormatter() throws IOException {
		Formatter inputsLogFileFormatter = new Formatter(
				new BufferedWriter(new FileWriter(this.workDirectoryAbsolutePath + "\\inputslog.txt", true)));
		return inputsLogFileFormatter;
	}

	public void logGeneratedInputs() throws IOException {
		if (this.logGeneratedInputsEnabledFlag == true) {

			Formatter inputsLogFileFormatter = createInputsLogFileFormatter();
			BufferedReader inputsTxtFileReader = new BufferedReader(
					new FileReader(this.workDirectoryAbsolutePath + "\\inputs.txt"));
			String inputsTxtFileReaderLine = "";

			while ((inputsTxtFileReaderLine = inputsTxtFileReader.readLine()) != null) {
				inputsLogFileFormatter.format("%s", inputsTxtFileReaderLine + "\n");
			}

			inputsLogFileFormatter.format("%s", inputsTxtFileReaderLine + "\n\n\n");
			inputsTxtFileReader.close();
			inputsLogFileFormatter.close();
		}
	}

	private void createAnnotationList() throws NumberFormatException, IOException {
		BufferedReader annotationFileReader = getAnnotationFileReader();
		String annotationLine = "";

		while ((annotationLine = annotationFileReader.readLine()) != null) {
			Annotation annotation = createAnnotation(annotationLine);
			this.annotationList.add(annotation);
		}
	}

	public File getAnnotationFile() throws FileNotFoundException {
		File annotationFile = new File(this.annotationFileAbsolutePath);
		return annotationFile;
	}

	public BufferedReader getAnnotationFileReader() throws FileNotFoundException {
		File annotationFile = getAnnotationFile();
		BufferedReader annotationFileReader = new BufferedReader(new FileReader(annotationFile));
		return annotationFileReader;
	}

	public Annotation createAnnotation(String annotationLine) {
		Annotation annotation = new Annotation();
		String[] annotationLineSplit = annotationLine.split("\\s+");
		annotation.setVariableType(annotationLineSplit[0]);
		annotation.setVariableName(annotationLineSplit[1]);
		annotation.setDeclarationLineNumber(Integer.parseInt(annotationLineSplit[2]));
		annotation.setVariableMin(annotationLineSplit[3]);
		annotation.setVariableMax(annotationLineSplit[4]);
		annotation.setVariableLineNumber(Integer.parseInt(annotationLineSplit[5]));
		return annotation;

	}

	public void createNonStructInputList() {

		for (Declaration declaration : this.inputDeclarationList) {
			// no needs to generate inputs for pointers
			if (!getIsDeclarationStructOrUnion(declaration) && !getIsDeclarationAPointer(declaration)) {

				declaration.setDeclarationType(declaration.getDeclarationType()
						.replaceAll("\\b(" + this.storageclassspecifier + ")\\s+\\b", "")
						.replaceAll("\\b(" + this.typequalifier + ")\\s+\\b", "").replaceAll("^\\s*", ""));
				this.nonStructInputList.add(declaration);
			}
		}
	}

	public void creatStructInputList() {
		StructDeclaration structInput = new StructDeclaration();

		for (Declaration declaration : this.inputDeclarationList) {
			if (getIsDeclarationStructOrUnion(declaration) && !getIsDeclarationAPointer(declaration)) {
				structInput.setArraySize(declaration.getArraySize());
				structInput.setBlockAddress(declaration.getBlockAddress());
				structInput.setDeclarationName(declaration.getDeclarationName());
				structInput.setDeclarationType(declaration.getDeclarationType()
						.replaceAll("\\b(" + this.storageclassspecifier + ")\\s+\\b", "")
						.replaceAll("\\b(" + this.typequalifier + ")\\s+\\b", "").replaceAll("^\\s*", ""));
				structInput
						.setStructName(declaration
								.getDeclarationType().replaceAll("\\b(" + this.storageclassspecifier + "|"
										+ this.typequalifier + "|" + this.structorunion + ")\\s+\\b", "")
								.replaceAll("^\\s*", ""));
				structInput.setIsArray(declaration.getIsArray());
				structInput.setLineNumber(declaration.getLineNumber());
				StructDeclaration stuctDeclarationOfTheStructInput = getStuctDeclarationOfTheStructInput(structInput);
				List<StructDeclaration> structComponentListOfTheStructInput = stuctDeclarationOfTheStructInput
						.getStructComponentList();
				List<Declaration> nonStructComponentListOfTheStructInput = stuctDeclarationOfTheStructInput
						.getNonStructComponentList();

				setArrayConfigurationForStructComponentsOfThisStructInput(structComponentListOfTheStructInput,
						structInput);
				setArrayConfigurationForNonStructComponentsOfThisStructInput(nonStructComponentListOfTheStructInput,
						structInput);
				structInput.setStructComponentList(structComponentListOfTheStructInput);
				structInput.setNonStructComponentList(nonStructComponentListOfTheStructInput);

				this.structInputList.add(structInput);
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

	private boolean getIsDeclarationAPointer(Declaration declaration) {
		Boolean isPointer = declaration.getDeclarationName().matches("\\*\\w*");
		return isPointer;

	}

	private boolean getIsDeclarationStructOrUnion(Declaration declaration) {
		boolean isDeclarationStruct = declaration.getDeclarationType().matches("((struct)+.*)|((union)+.*)");
		return isDeclarationStruct;
	}

	private void integrateAnnotationsToInputs() throws NumberFormatException, IOException {
		integrateAnnotationForNonStructInputs();
		integrateAnnotationForStructInputs();
	}

	private void integrateAnnotationForNonStructInputs() {
		for (Annotation annotation : this.annotationList) {
			for (Declaration nonStructInput : this.nonStructInputList) {
				if (getIsAnnotationForOneInputAtASpecificLocation(annotation)) {
					integrateAnnotationForNonStructInputAtASpecificLocation(annotation, nonStructInput);
				} else {
					// if (declaration.getIsArray())
					// integrateAnnotationForAllTheNonSpecificInputsOfThisStructDefinitionDeclaration(annotation,
					// nonStructInput);
					// } else {
					integrateAnnotationForNonStructInputWithoutASpecificLocation(annotation, nonStructInput);
					// }
				}
			}
		}
	}

	private void integrateAnnotationForStructInputs() {
		for (Annotation annotation : this.annotationList) {
			for (StructDeclaration structInput : this.structInputList) {
				if (getIsAnnotationForOneInputAtASpecificLocation(annotation)) {
					integrateAnnotationForStructInputAtSpecificLocation(annotation, structInput);
				} else {
					integrateAnnotationForStructInputWithoutASpecificLocation(annotation, structInput);
				}
			}
		}
	}

	private void integrateAnnotationForStructInputAtSpecificLocation(Annotation annotation,
			StructDeclaration structInput) {
		if (getIsAnnotationForStructComponentVariable(annotation)) {
			for (Declaration nonStructComponent : structInput.getNonStructComponentList()) {
				for (Variable variable : this.rightVariableList) {
					if (variable.getLineNumber().equals(annotation.getVariableLineNumber())
							&& getIsAnnotationForThisNonStuctComponentOfThisStructInput(annotation, nonStructComponent,
									structInput)) {
						nonStructComponent.setVariableMax(annotation.getVariableMax());
						nonStructComponent.setVariableMin(annotation.getVariableMin());

					}
				}
			}
		}
	}

	private void integrateAnnotationForStructInputWithoutASpecificLocation(Annotation annotation,
			StructDeclaration structInput) {
		if (getIsAnnotationForStructComponentVariable(annotation)) {
			for (Declaration nonStructComponent : structInput.getNonStructComponentList()) {
				if (getIsAnnotationForThisNonStuctComponentOfThisStructInput(annotation, nonStructComponent,
						structInput)) {
					nonStructComponent.setVariableMax(annotation.getVariableMax());
					nonStructComponent.setVariableMin(annotation.getVariableMin());
				}
			}
		}
	}

	private void integrateAnnotationForNonStructInputAtASpecificLocation(Annotation annotation, Declaration input) {
		if (!getIsAnnotationForStructComponentVariable(annotation)) {
			for (Variable variable : this.rightVariableList) {
				if (variable.getLineNumber().equals(annotation.getVariableLineNumber())
						&& getIsAnnotationForThisNonStructInput(annotation, input)) {

					input.setVariableMax(annotation.getVariableMax());
					input.setVariableMin(annotation.getVariableMin());

				}
			}
		}
	}

	private void integrateAnnotationForNonStructInputWithoutASpecificLocation(Annotation annotation,
			Declaration input) {
		if (!getIsAnnotationForStructComponentVariable(annotation)) {
			if (getIsAnnotationForThisNonStructInput(annotation, input)) {
				input.setVariableMax(annotation.getVariableMax());
				input.setVariableMin(annotation.getVariableMin());

			}
		}
	}

	private boolean getIsAnnotationForStructComponentVariable(Annotation annotation) {
		Boolean isAnnotationForStructVariable = annotation.getVariableName().contains(".");
		return isAnnotationForStructVariable;
	}

	private boolean getIsAnnotationForThisNonStructInput(Annotation annotation, Declaration input) {
		boolean isAnnotationForThisNonStructInput = input.getDeclarationType().replace(" ", "").equals(
				annotation.getVariableType()) && input.getLineNumber().equals(annotation.getDeclarationLineNumber())
				&& input.getDeclarationType().replace(" ", "").equals(annotation.getVariableType());
		return isAnnotationForThisNonStructInput;
	}

	private Boolean getIsAnnotationForThisNonStuctComponentOfThisStructInput(Annotation annotation,
			Declaration nonStructComponent, StructDeclaration structInput) {
		String[] annotationVariableName_split = annotation.getVariableName().split("\\.");
		String annotationStructComponentName = annotationVariableName_split[annotationVariableName_split.length - 1];
		String annotationStructInputName = annotationVariableName_split[0];
		String annotationStructName = annotation.getVariableType().replaceAll("struct|union", "");
		String structInputName = structInput.getDeclarationName();
		String structInputStructName = structInput.getStructName();
		String nonStructComponentName = nonStructComponent.getDeclarationName();
		Boolean isAnnotationForThisNonStuctComponentOfThisStructInput = nonStructComponentName
				.equals(annotationStructComponentName) && structInputStructName.equals(annotationStructName)
				&& structInputName.equals(annotationStructInputName);

		return isAnnotationForThisNonStuctComponentOfThisStructInput;
	}

	private boolean getIsAnnotationForOneInputAtASpecificLocation(Annotation annotation) {
		Boolean isAnnotationForVariableAtASpecificPartOfTheCode = !annotation.getVariableLineNumber().equals(0);
		return isAnnotationForVariableAtASpecificPartOfTheCode;
	}

	public void constructPythonRandomInputGenerator() throws IOException {
		integrateAnnotationsToInputs();

		randomInputGenerator = new File("randomInputGenerator.py");
		randomFormatter = new Formatter(randomInputGenerator);

		randomFormatter.format("%s", "# Automatically-generated file. Do not edit! \n \n \n");
		randomFormatter.format("%s",
				"import numpy as np \nimport re as replace\n\nnp.set_printoptions(threshold=np.inf)\n\n");
		randomFormatter.format("%s", getMacrosForInputGeneration() + "\n\n");
		// writing the python file for input generation

		for (Declaration input : this.nonStructInputList) {

			inputGenerationForNonStructInput(input);

		}

		for (StructDeclaration structInput : this.structInputList) {
			inputGenerationForStructInput(structInput);
		}
		// randomFormatter.format("%s", "print(i)");
		randomFormatter.close();
	}

	public String getMacrosForInputGeneration() throws IOException {
		List<File> headerFileList = getHeaderFileList();
		for (File headerFile : headerFileList) {
			appendMacrosForInputGenerationWithAllMacrosFromThisFile(headerFile);
		}
		return this.macrosForInputGeneration;
	}

	public void appendMacrosForInputGenerationWithAllMacrosFromThisFile(File headerFile) throws IOException {
		BufferedReader headerFileReader = new BufferedReader(new FileReader(headerFile));
		String headerFileReadLine = "";
		while ((headerFileReadLine = headerFileReader.readLine()) != null) {
			if (isMacro(headerFileReadLine)) {
				String rewrittenMacro = rewriteDefinedMacroForInputGeneration(headerFileReadLine);
				appendMacrosForInputGeneration(this.macrosForInputGeneration, rewrittenMacro);
			}
		}
		headerFileReader.close();
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

	public List<File> getHeaderFileList() {
		List<File> headerFileList = new ArrayList<File>();
		for (File headerFile : new File(this.originalFileDirectoryAbsolutePath).listFiles()) {
			if (headerFile.isFile() && headerFile.getName().matches(".*\\.h$")) {
				headerFileList.add(headerFile);
			}
		}
		return headerFileList;
	}

	public void inputGenerationForNonStructInput(Declaration input) throws IOException {
		Boolean isInputWithAnnotation = getIsInputWithAnnotation(input);
		if (!isInputWithAnnotation) {
			inputGenerationForInputWithoutAnnotation(input);
		} else {
			inputGenerationForInputWithAnnotation(input);
		}
	}

	private Boolean getIsStructDefinitationDeclarationOfTheStructInput(Declaration structDefinitationDeclaration,
			StructDeclaration structInput) {
		String structNameOfTheStructInput = structInput.getStructName();
		String nameOfTheStructDefinitationDeclaration = structDefinitationDeclaration.getDeclarationName();
		Boolean isStructDefinitationDeclarationOfTheStructInput = nameOfTheStructDefinitationDeclaration
				.equals(structNameOfTheStructInput);

		return isStructDefinitationDeclarationOfTheStructInput;
	}

	private StructDeclaration getStuctDeclarationOfTheStructInput(StructDeclaration structInput) {
		StructDeclaration structDefinitationDeclarationofTheStructInput = new StructDeclaration();

		for (StructDeclaration structDefinitationDeclaration : this.structDefinitationDeclarationList) {
			Boolean isStructDefinitationDeclarationOfTheStructInput = getIsStructDefinitationDeclarationOfTheStructInput(
					structDefinitationDeclaration, structInput);
			if (isStructDefinitationDeclarationOfTheStructInput) {
				structDefinitationDeclarationofTheStructInput = structDefinitationDeclaration;
				break;
			}
		}
		return structDefinitationDeclarationofTheStructInput;
	}

	public Boolean getIsStructComponentAStructOrUnion(String structComponentName) {
		Boolean isStructComponentAStruct = structComponentName.matches("((struct)+.*)|((union)+.*)");
		return isStructComponentAStruct;

	}

	public void inputGenerationForStructInput(StructDeclaration structInput) throws IOException {
		for (Declaration x : structInput.getNonStructComponentList()) {
			x.setDeclarationName(structInput.getDeclarationName() + "_" + x.getDeclarationName());
			inputGenerationForNonStructInput(x);
		}
	}

	public void inputGenerationForInputWithAnnotation(Declaration input) {
		String inputType = input.getDeclarationType();

		switch (inputType) {
		case "float":
			randomInputGeneration(input, "uniform", input.getVariableMin(), input.getVariableMax());
			break;
		case "double":
			randomInputGeneration(input, "uniform", input.getVariableMin(), input.getVariableMax());
			break;
		case "long double":
			randomInputGeneration(input, "uniform", input.getVariableMin(), input.getVariableMax());
			break;
		default:
			randomInputGeneration(input, "randint", input.getVariableMin(), input.getVariableMax());
			break;
		}

	}

	public void inputGenerationForInputWithoutAnnotation(Declaration input) {
		String inputType = input.getDeclarationType();

		switch (inputType) {
		case "char":
			randomInputGeneration(input, "randint", "0", "255");
			break;
		case "unsigned char":
			randomInputGeneration(input, "randint", "0", "255");
			break;
		case "signed char":
			randomInputGeneration(input, "randint", "-32768", "32767");
			break;
		case "int":
			randomInputGeneration(input, "randint", "-32768", "32767");
			break;
		case "unsigned int":
			randomInputGeneration(input, "randint", "0", "65535");
			break;
		case "short":
			randomInputGeneration(input, "randint", "-32768", "32767");
			break;
		case "unsigned short":
			randomInputGeneration(input, "randint", "0", "65535");
			break;
		case "long":
			randomInputGeneration(input, "randint", "-2147483648", "2147483647");
			break;
		case "unsigned long":
			randomInputGeneration(input, "randint", "0", "4294967295");
			break;
		case "float":
			randomInputGeneration(input, "uniform", "1.2e-38", "3.4e+38");
			break;
		case "double":
			randomInputGeneration(input, "uniform", "1.2e-38", "3.4e+38");
			break;
		case "long double":
			randomInputGeneration(input, "uniform", "3.4e-4932", "1.1e+4932");
			break;
		}
	}

	public void randomInputGeneration(Declaration input, String inputDistributionType, String min, String max) {
		String inputType = input.getDeclarationType();
		String inputName = input.getDeclarationName();
		boolean isArray = input.getIsArray();
		String arraySize = input.getArraySize();

		String dtype = "dtype=np.int64";
		if (inputDistributionType.equals("uniform"))
			dtype = "";
		inputName = replaceOperatorsInNameWithLetters(inputName);
		if (!isArray) {
			this.randomFormatter.format("%s",
					"temp = str(" + "np.random." + inputDistributionType + "(low=" + min + ",high=" + max + "+1"
							+ ",size=(1," + this.generatedInputLength + ")" + "," + dtype
							+ ")).replace(\"[[\",\"{\").replace(\"]]\",\"};\") \n");
			this.randomFormatter.format("%s",
					"temp = replace.sub(\"\\s+|\\n\",\",\", temp).replace(\"{,\",\"{\").replace(\",}\",\"}\").replace(\",,\",\",\") \n");
			this.randomFormatter.format("%s", "print(\'" + inputType + " " + inputName + "_inputs["
					+ this.generatedInputLength + "] = \' + temp + \';\' " + ") \n");
		} else {
			this.randomFormatter.format("%s",
					"temp = str(" + "np.random." + inputDistributionType + "(low=" + min + ",high=" + max + "+1"
							+ ",size=(" + this.generatedInputLength + "," + arraySize + ")" + "," + dtype + ")) \n");
			this.randomFormatter.format("%s", "temp = replace.sub(\"\\s|\\n\",\",\",temp)\n");
			this.randomFormatter.format("%s", "temp = replace.sub(\"\\,+\",\",\",temp)\n");
			this.randomFormatter.format("%s",
					"temp = temp.replace(\"[,\",\"[\").replace(\",]\",\"]\").replace(\"[\",\"{\").replace(\"]\",\"}\")\n");
			this.randomFormatter.format("%s",
					"print(\'" + inputType + " " + inputName + "_inputs[" + this.generatedInputLength + "]" + "["
							+ arraySize.replace(",", "][") + "]" + "= \' + temp + \';\' " + ") \n");
		}
	}

	private String replaceOperatorsInNameWithLetters(String nameString) {
		String replacedName = nameString.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
				.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
				.replaceAll(",", "com");

		return replacedName;
	}

	public Boolean getIsInputWithAnnotation(Declaration input) {
		Boolean isInputWithAnnotation = !(input.getVariableMax() == null);

		return isInputWithAnnotation;
	}

	public void generateInput() throws IOException {

		constructPythonRandomInputGenerator();

		ProcessBuilder randombuilder = new ProcessBuilder("python", this.randomInputGenerator.getAbsolutePath());
		Process process = randombuilder.start();

		String generatedInputsFilePath = this.workDirectoryAbsolutePath + "\\inputs.txt";
		new StreamScanner(process, generatedInputsFilePath);
	}

	public List<StructDeclaration> getStructInputList() {

		return this.structInputList;
	}

	public List<Declaration> getNonStructInputList() {

		return this.nonStructInputList;
	}

	public List<Declaration> getInputDeclarationList() {

		return this.inputDeclarationList;
	}
}
