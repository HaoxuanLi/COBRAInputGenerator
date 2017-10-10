import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

public class InputsHeaderFileGenerator {
	private String workDirectoryAbsolutePath = "";
	private int blockStartLineNumber;

	public InputsHeaderFileGenerator(WCETTargetInfo wcetTargetInfo) throws FileNotFoundException{
		readWcetTargetInfo(wcetTargetInfo);
		
	}
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.blockStartLineNumber = wcetTargetInfo.getBlockStartLineNumber();
	}

	public File getInputsHeaderFile() {
		File inputsHeaderFile = new File(this.workDirectoryAbsolutePath + "\\inputs.h");
		return inputsHeaderFile;
	}
	

	public void writeToHeaderFile(File inputsHeaderFile) throws FileNotFoundException {
		Formatter inputsHeaderFileFormatter = new Formatter(inputsHeaderFile);

		inputsHeaderFileFormatter.format("%s", "#ifndef __INPUTS" + " \n");
		inputsHeaderFileFormatter.format("%s", "#define __INPUTS" + " \n\n");

		// include the main header file
		inputsHeaderFileFormatter.format("%s", "// include the main header" + " \n");
		inputsHeaderFileFormatter.format("%s", "#include \"main.h\" " + " \n\n");

		// forward declaration
		inputsHeaderFileFormatter.format("%s", "// forward declarations" + " \n");
		inputsHeaderFileFormatter.format("%s", "void blockInputUpdate(int);" + " \n");
		inputsHeaderFileFormatter.format("%s", "void benchmarkBlock(void);" + " \n\n");

		inputsHeaderFileFormatter.format("%s", "#endif" + " \n");

		inputsHeaderFileFormatter.close();
	}

	public void generateHeaderFile() throws FileNotFoundException {
		System.out.println("\n Generating inputs.h file for blockR" + this.blockStartLineNumber + "...");
		File inputsHeaderFile = getInputsHeaderFile();
		writeToHeaderFile(inputsHeaderFile);
		System.out.println("\n Generated inputs.h file for blockR" + this.blockStartLineNumber + "...");

	}
}
