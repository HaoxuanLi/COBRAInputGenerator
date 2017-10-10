package VariableClasses;

public class Variable {
	private String variableType;
	private String variableName;
	private Integer lineNumber;
	private Boolean isForLoopCounter;

	public String getVariableType() {
		return variableType;
	}
	
	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}
	
	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String VariableName) {
		this.variableName = VariableName;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public Boolean getIsForLoopCounter() {
		return isForLoopCounter;
	}

	public void setIsForLoopCounter(Boolean isForLoopCounter) {
		this.isForLoopCounter = isForLoopCounter;
	}
}

