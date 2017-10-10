import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WCETTargetInfo extends ConfigFileReader{
	private String avrFilesOutputDirectoryAbsolutePath;
	private String blockFileAbsolutePath;
	private String workDirectoryAbsolutePath;
	private String measurementResultsLogFileAbsolutePath;
	private String framePointerValue;
	private String stackPointerValueOfOriginalCode;
	private String stackPointerValueOfBlockCode;
	private String blockFileName;
	private String stepNumber;
	private String avrOutputFilesName;
	private int generatedInputLength;
	private int blockStartLineNumber;
	private int blockEndLineNumber;
	private Boolean logGeneratedInputsEnabledFlag;
	private Boolean appendMeasurementResultsEnabledFlag;
	
	WCETTargetInfo() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getAvrOutputFilesName(){
		this.avrOutputFilesName = "blockR" + this.getBlockStartLineNumber();
		return this.avrOutputFilesName;
		
	}
	
	public String getAvrFilesOutputDirectoryAbsolutePath() {
		this.avrFilesOutputDirectoryAbsolutePath = this.workDirectoryAbsolutePath + "\\Debug";
		return this.avrFilesOutputDirectoryAbsolutePath;
	}
	
	public String getMeasurementResultsLogFileAbsolutePath() {
		this.measurementResultsLogFileAbsolutePath = this.workDirectoryAbsolutePath + "\\Results.txt";
		return this.measurementResultsLogFileAbsolutePath;
	}

	public void caculateBlockStartLineNumber() {
		String[] blockfile_split = this.blockFileName.replaceAll(".c", "").split("_");
		int blockfile_split_counter = 0;

		for (String blockStartLineNumber : blockfile_split) {
			Matcher m = Pattern.compile("r([0-9]+)").matcher(blockStartLineNumber);
			if (m.matches()) {
				blockfile_split[blockfile_split_counter] = m.group(1);
				blockfile_split_counter++;
			}
		}

		this.blockStartLineNumber = Integer.parseInt(blockfile_split[0]);
	}
	
	public int getBlockStartLineNumber() {
		return this.blockStartLineNumber;
	}
	
	public void caculateBlockEndLineNumber() {
		String[] blockfile_split = this.blockFileName.replaceAll(".c", "").split("_");
		int blockfile_split_counter = 0;

		for (String blockStartLineNumber : blockfile_split) {
			Matcher m = Pattern.compile("r([0-9]+)").matcher(blockStartLineNumber);
			if (m.matches()) {
				blockfile_split[blockfile_split_counter] = m.group(1);
				blockfile_split_counter++;
			}
		}
		this.blockEndLineNumber = Integer.parseInt(blockfile_split[blockfile_split_counter - 1]);
	}
	
	public int getBlockEndLineNumber() {
		return this.blockEndLineNumber;
	}
	public String getStepNumber() {
		return this.stepNumber;
	}

	public void setStepNumber(String stepNumber) {
		this.stepNumber = stepNumber;
	}

	
	public Boolean isPreparingToGettingPointerValues() {
		return (this.stepNumber == "1");
	}
	
	public Boolean isGettingPointerValues() {
		return (this.stepNumber == "2");
	}
	
	public Boolean isMakingTimingMeasurementModule() {
		return (this.stepNumber == "3");
	}
	
	public Boolean isInsertingBlockContentToTimingMeasurementModule() {
		return (this.stepNumber == "4");
	}

	public Boolean isFinalStepToAdjustRcall() {
		return (this.stepNumber == "5");
	}
	
	public String getFramePointerValue() {
		return this.framePointerValue;
	}

	public void setFramePointerValue(String framePointerValue) {
		this.framePointerValue = framePointerValue;
	}

	public String getStackPointerValueOfOriginalCode() {
		return stackPointerValueOfOriginalCode;
	}

	public void setStackPointerValueOfOriginalCode(String stackPointerValueOfOriginalCode) {
		this.stackPointerValueOfOriginalCode = stackPointerValueOfOriginalCode;
	}

	public String getStackPointerValueOfBlockCode() {
		return stackPointerValueOfBlockCode;
	}

	public void setStackPointerValueOfBlockCode(String stackPointerValueOfBlockCode) {
		this.stackPointerValueOfBlockCode = stackPointerValueOfBlockCode;
	}
	
	public void setGeneratedInputLength(int generatedInputLength){
		this.generatedInputLength = generatedInputLength;
	}
	
	public int getGeneratedInputLength(){
		return(this.generatedInputLength);
	}
	
	public void setBlockFileName(String blockFileName){
		this.blockFileName = blockFileName;
		this.workDirectoryAbsolutePath = this.getWorkDirectoryAbsolutePath();
		caculateBlockStartLineNumber();
		caculateBlockEndLineNumber();
	}
	
	public String getBlockFileName(){
		return(this.blockFileName);
	}
	
	public void setLogGeneratedInputsEnabledFlag(Boolean logGeneratedInputsEnabledFlag){
		this.logGeneratedInputsEnabledFlag = logGeneratedInputsEnabledFlag;
	}
	
	public Boolean getLogGeneratedInputsEnabledFlag(){
		return(this.logGeneratedInputsEnabledFlag);
	}
	
	public void setAppendMeasurementResultsEnabledFlag(Boolean appendMeasurementResultsEnabledFlag){
		this.appendMeasurementResultsEnabledFlag = appendMeasurementResultsEnabledFlag;
	}
	
	public Boolean getAppendMeasurementResultsEnabledFlag(){
		return(this.appendMeasurementResultsEnabledFlag);
	}
	
	public String getBlockFileAbsolutePath(){
		this.blockFileAbsolutePath = super.getBlockFileDirectoryAbsolutePath() + "\\" + this.blockFileName + ".c";
		return(blockFileAbsolutePath);
	}
	
	public String getWorkDirectoryAbsolutePath(){
		String[] blockFileName_Split = this.blockFileName.split("_");
		int blockFileName_SplitCounter = 0;
		
		for (String blockStartLineNumber : blockFileName_Split) {
			Matcher m = Pattern.compile("r([0-9]+)").matcher(blockStartLineNumber);
			if (m.matches()) {
				blockFileName_Split[blockFileName_SplitCounter] = m.group(1);
				blockFileName_SplitCounter++;
			}
		}
		
		this.workDirectoryAbsolutePath = super.getWorkDirectoryRootAbsolutePath() + "\\blockR" + blockFileName_Split[0];
		
		return(this.workDirectoryAbsolutePath);
	}
	
	

}