import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import VariableClasses.Variable;

public class OriginalFileReader {
	private String originalFileAbsolutePath;
	
	public OriginalFileReader(WCETTargetInfo wcetTargetInfo){
		readWcetTargetInfo(wcetTargetInfo);
	}
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.originalFileAbsolutePath = wcetTargetInfo.getOriginalFileAbsolutePath();
	}

	
	public File getOriginalFile() throws FileNotFoundException {
		File originalFile = new File(this.originalFileAbsolutePath);
		
		return originalFile;
	}

	public BufferedReader getOriginalFileReader() throws FileNotFoundException {
		File originalFile = getOriginalFile();
		BufferedReader originalFileReader = new BufferedReader(new FileReader(originalFile));
		return originalFileReader;
	}
	
	public Variable getPointerTargetVariable(Variable pointer) throws IOException{
		Variable pointerTargetVariable = new Variable();
		String originalFileReaderLine = "";
		String pointerTargetVariableName = "";
		int pointerTargetLineNumber = 0; 
		BufferedReader originalFileReader = getOriginalFileReader();
		int originalFileReaderLineCounter = 1;
		while((originalFileReaderLine=originalFileReader.readLine())!=null){		
			
			if(originalFileReaderLine.matches("\\s*"+pointer.getVariableName().replace("*", "")+"\\s*=\\s*\\&.*;")){
				pointerTargetVariableName = originalFileReaderLine.replaceAll(".*&", "").replaceAll("\\W.*", "");
				pointerTargetLineNumber = originalFileReaderLineCounter;		
				break;
			}
			
			originalFileReaderLineCounter++;
			
			if(originalFileReaderLineCounter==pointer.getLineNumber())
				break;
		}
		
		originalFileReader.close();
		
		pointerTargetVariable.setVariableName(pointerTargetVariableName);
		pointerTargetVariable.setLineNumber(pointerTargetLineNumber);
		
		return pointerTargetVariable;
		
	}
}
