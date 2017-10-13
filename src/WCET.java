import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WCET {
	private WCETTargetInfo wcetTargetInfo;
	private String stepNumber;
	private String workDirectoryAbsolutePath;
	private String workDirectoryRootAbsolutePath;
	private String framePointerValue;
	private String stackPointerValueOfOriginalCode;
	private String stackPointerValueOfBlockCode;

	public void makeWorkDirectoryRoot() {
		if (!new File(this.workDirectoryRootAbsolutePath).exists())
			new File(this.workDirectoryRootAbsolutePath).mkdir();
	}

	public void makeWorkDirectory() {
		if (!new File(this.workDirectoryAbsolutePath).exists())
			new File(this.workDirectoryAbsolutePath).mkdir();
	}

	public void copyOriginalFilesToWorkDirectory() throws IOException {
		for (File file : new File(this.wcetTargetInfo.getOriginalFileDirectoryAbsolutePath()).listFiles()) {
			String fileName = file.getName();
			Boolean fileIsSourceOrHeader = fileName.endsWith(".c") || fileName.endsWith(".h");
			String fileOutputStreamInWorkDirectory = this.wcetTargetInfo.getWorkDirectoryAbsolutePath() + "\\"
					+ fileName;
			Boolean fileAlreadyExistedInWorkDirectory = new File(fileOutputStreamInWorkDirectory).exists();

			if (fileIsSourceOrHeader && !fileAlreadyExistedInWorkDirectory)
				Files.copy(file.toPath(), new File(fileOutputStreamInWorkDirectory).toPath());
		}
	}

	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.wcetTargetInfo = wcetTargetInfo;
		this.stepNumber = wcetTargetInfo.getStepNumber();
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.workDirectoryRootAbsolutePath = wcetTargetInfo.getWorkDirectoryRootAbsolutePath();
		wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
		wcetTargetInfo.getAvrOutputFilesName();
	}

	public void setWcetTargetInfoPointerValutes(WCETTargetInfo wcetTargetInfo) {
		this.wcetTargetInfo.setFramePointerValue(framePointerValue);
		this.wcetTargetInfo.setStackPointerValueOfOriginalCode(stackPointerValueOfOriginalCode);
		this.wcetTargetInfo.setStackPointerValueOfBlockCode(stackPointerValueOfBlockCode);
	}

	public WCET(WCETTargetInfo wcetTargetInfo) throws Exception {
		readWcetTargetInfo(wcetTargetInfo);
		this.framePointerValue = "10fb";
		this.stackPointerValueOfOriginalCode = "10f3";
		this.stackPointerValueOfBlockCode = "10e3";
		setWcetTargetInfoPointerValutes(this.wcetTargetInfo);

		makeWorkDirectoryRoot();
		makeWorkDirectory();
		copyOriginalFilesToWorkDirectory();
		ProcessorProgrammer a1 = new ProcessorProgrammer(this.wcetTargetInfo);

		
		
		InputGenerator inputGenerator = new InputGenerator(this.wcetTargetInfo);	
		inputGenerator.generateInput();	
		InputsHeaderFileGenerator inputsHeaderFileGenerator = new InputsHeaderFileGenerator(this.wcetTargetInfo);
		inputsHeaderFileGenerator.generateHeaderFile();
		InputsSourceFileGenerator inputsSourceFileGenerator = new InputsSourceFileGenerator(this.wcetTargetInfo);
		
		
		if (this.wcetTargetInfo.isPreparingToGettingPointerValues()) {
			new ProcessorTemplateMainFileGenerator(this.wcetTargetInfo);
			inputsSourceFileGenerator.constructSourseFileForPreparingToGettingPointerValues();
			new OriginalFileModifier(this.wcetTargetInfo);
			new MakeFileGenerator(this.wcetTargetInfo);
			a1.cleanprogramfile();
			a1.makeprogramfile();

		} else if (stepNumber.equals("2")) {
			new ProcessorTemplateMainFileGenerator(this.wcetTargetInfo);
			inputsSourceFileGenerator.constructSourseFileForGettingPointerValues();
			new OriginalFileModifier( this.wcetTargetInfo);
			a1.cleanprogramfile();
			a1.makeprogramfile();
		//	a1.eraseprogram();
		//	a1.deployprogram();
		} else if (stepNumber == "3") {
			new ProcessorTemplateMainFileGenerator(this.wcetTargetInfo);
			a1.cleanprogramfile();
			a1.makeprogramfile();
			inputsSourceFileGenerator.constructSourseFileForMakingTimingMeasurementModule();
			new OriginalFileModifier(this.wcetTargetInfo);
			a1.cleanprogramfile();
			a1.makeprogramfile();

		} else if (stepNumber == "4") {
			inputGenerator.generateInput();	
			inputGenerator.logGeneratedInputs();
			inputsSourceFileGenerator.constructSourseFileForInsertingBlockContentToTimingMeasurementModule();
			new OriginalFileModifier(this.wcetTargetInfo);
			a1.cleanprogramfile();
			a1.makeprogramfile();
			inputsSourceFileGenerator.constructSourseFileForInsertingBlockContentToTimingMeasurementModule(); //adjust the rcall target address as it changed
		} else if (stepNumber == "5") {
			inputsSourceFileGenerator.finalStepToAdjustRcall();
			new OriginalFileModifier(this.wcetTargetInfo);
			a1.cleanprogramfile();
			a1.makeprogramfile();
			a1.eraseprogram();
			a1.deployprogram();
		}

		return;
	}

}
