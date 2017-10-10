package VariableClasses;

import java.util.ArrayList;
import java.util.List;

public class StructDeclaration extends Declaration {
	private String structName;
	private String originalHeaderFileAbsolutePath;
	private List<Declaration> nonStructComponentList= new ArrayList<Declaration>();
	private List<StructDeclaration> structComponentList= new ArrayList<StructDeclaration>();
	
	public String getStructName() {
		return structName;
	}

	public void addStructComponentToList(StructDeclaration structComponent) {
		structComponentList.add(structComponent);
	}
	
	public List<Declaration> getNonStructComponentList() {
		return nonStructComponentList;
	}
	
	public void setNonStructComponentList(List<Declaration> nonStructComponentList) {
		this.nonStructComponentList = nonStructComponentList;
	}

	public void addNonStructComponentToList(Declaration nonStructComponent) {
		nonStructComponentList.add(nonStructComponent);
	}
	
	public List<StructDeclaration> getStructComponentList() {
		return structComponentList;
	}
	
	public void setStructComponentList(List<StructDeclaration> structComponentList) {
		this.structComponentList = structComponentList;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public String getOriginalHeaderFileAbsolutePath() {
		return originalHeaderFileAbsolutePath;
	}

	public void setOriginalHeaderFileAbsolutePath(String originalHeaderFileAbsolutePath) {
		this.originalHeaderFileAbsolutePath = originalHeaderFileAbsolutePath;
	}
	
	
}
