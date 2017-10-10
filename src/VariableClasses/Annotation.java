package VariableClasses;

public class Annotation extends Variable {
	private Integer declarationLineNumber;
	private Integer variableLineNumber;
	private String variableMax;
	private String variableMin;
	private boolean isArray;
	private String arraySize;

	public Integer getDeclarationLineNumber() {
		return declarationLineNumber;
	}

	public void setDeclarationLineNumber(Integer declarationLineNumber) {
		this.declarationLineNumber = declarationLineNumber;
	}

	public Integer getVariableLineNumber() {
		return variableLineNumber;
	}

	public void setVariableLineNumber(Integer variableLineNumber) {
		this.variableLineNumber = variableLineNumber;
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
}