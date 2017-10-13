package VariableClasses;


public class Declaration {
	private String declarationName;
	private String declarationType;
	private String variableMax;
	private String variableMin;
	private boolean isArray;
	private String arraySize;
	private String blockaddress;
	private String originaladdress;
	private int variablelength;
	private int lineNumber;
	private String initializationassembly;
	private Boolean isForLoopCounter;

	public String getDeclarationType() {
		return declarationType;
	}
	
	public void setDeclarationType(String declarationType) {
		this.declarationType = declarationType;
	}
	
	public String getDeclarationName() {
		return declarationName;
	}

	public void setDeclarationName(String declarationName) {
		this.declarationName = declarationName;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getVariableMax() {
		return variableMax;
	}

	public void setVariableMax(String variableMax) {
		this.variableMax = variableMax;
	}

	public String getVariableMin() {
		return variableMin;
	}

	public void setVariableMin(String variableMin) {
		this.variableMin = variableMin;
	}


	public boolean getIsArray() {
		return isArray;
	}

	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}

	public String getArraySize() {
		return arraySize;
	}

	public void setArraySize(String arraySize) {
		this.arraySize = arraySize;
	}

	public String getBlockAddress() {
		return blockaddress;
	}

	public void setBlockAddress(String blockaddress) {
		this.blockaddress = blockaddress;
	}

	public String getOriginalAddress() {
		return originaladdress;
	}

	public void setOriginalAddress(String originaladdress) {
		this.originaladdress = originaladdress;
	}

	public int getVariableLength() {
		return variablelength;
	}

	public void setVariableLength(int variablelength) {
		this.variablelength = variablelength;
	}

	public String getInitializationAssembly() {
		return initializationassembly;
	}

	public void setInitializationAssembly(String initializationassembly) {
		this.initializationassembly = initializationassembly;
	}
	
	public Boolean getIsForLoopCounter() {
		return isForLoopCounter;
	}

	public void setIsForLoopCounter(Boolean isForLoopCounter) {
		this.isForLoopCounter = isForLoopCounter;
	}

}
