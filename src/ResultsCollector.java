import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;

import jssc.SerialPort;
import jssc.SerialPortException;

public class ResultsCollector {
	private String serialPortNumber;
	private String measurementResultsLogFileAbsolutePath;
	private int generatedInputLength;
	private Boolean appendMeasurementResultsEnabledFlag;
	private Formatter measurementResultsLogFileAbsolutePathFormatter;
	private SerialPort serialPortReader;
	
	ResultsCollector(WCETTargetInfo wcetTargetInfo) throws IOException, SerialPortException {
		readWcetTargetInfo(wcetTargetInfo);
		
	}
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.measurementResultsLogFileAbsolutePath = wcetTargetInfo.getMeasurementResultsLogFileAbsolutePath();
		this.serialPortNumber = wcetTargetInfo.getSerialPortNumber();
		this.appendMeasurementResultsEnabledFlag = wcetTargetInfo.getAppendMeasurementResultsEnabledFlag();
		this.generatedInputLength = wcetTargetInfo.getGeneratedInputLength();
	}

	public void makeMeasurementResultsLogFileAbsolutePathFormatter() throws IOException {
		this.measurementResultsLogFileAbsolutePathFormatter = new Formatter(new BufferedWriter(
				new FileWriter(this.measurementResultsLogFileAbsolutePath, this.appendMeasurementResultsEnabledFlag)));

	}
	public void closeMeasurementResultsLogFileAbsolutePathFormatter(){
		this.measurementResultsLogFileAbsolutePathFormatter.close();
	}
	

	public void makeSerialPortReader() {
		this.serialPortReader = new SerialPort(this.serialPortNumber);
	}

	public void openSerialPortReader() throws SerialPortException {
		this.serialPortReader.openPort();
	}

	public void setParametersOfSerialPortReader() throws SerialPortException {
		this.serialPortReader.setParams(9600, 8, 1, 0);// Set params.
	}

	public void closeSerialPortReader() throws SerialPortException {
		this.serialPortReader.closePort();
	}

	public void logMeasurementResult(String executionTime) {
		this.measurementResultsLogFileAbsolutePathFormatter.format("%s", executionTime);
	}

	public void printMeasurementResult(String executionTime) {
		System.out.println(executionTime);
	}
	
	public String getExecutionTime() throws SerialPortException{
		int serialPortByteCount = 4;
		byte[] executionTimeBuffer;
		executionTimeBuffer = this.serialPortReader.readBytes(serialPortByteCount); 															// port
		String executionTime = new String(executionTimeBuffer, 0, serialPortByteCount);
		return executionTime;
	}

	public void logAndPrintMeasurementResults() throws IOException, SerialPortException {
		makeMeasurementResultsLogFileAbsolutePathFormatter();
		
		int measurementRoundCounter = 0;
		while (this.serialPortReader != null && measurementRoundCounter < generatedInputLength) {
			String executionTime = getExecutionTime();
			logMeasurementResult(executionTime);
			printMeasurementResult(executionTime);
			if (executionTime.contains("\n"))
				measurementRoundCounter++;
		}
	}
	public void finishCollection() throws SerialPortException{
		closeMeasurementResultsLogFileAbsolutePathFormatter();
		closeSerialPortReader();
		System.out.println("Results collected!");	
		System.out.println("Port Closed!");
	}
	
	public void collectResults() throws  IOException, SerialPortException{
		setupSerialPort();
		logAndPrintMeasurementResults();		
		finishCollection();
	}
	
	public void setupSerialPort() throws SerialPortException{
		makeSerialPortReader();
		openSerialPortReader();
		setParametersOfSerialPortReader();
	}
	
}