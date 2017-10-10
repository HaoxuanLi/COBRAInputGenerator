import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Formatter;

import jssc.SerialPort;

public class ResultsAnalyser{
	private String workDirectoryAbsolutePath;
	private String measurementResultsLogFileAbsolutePath;
	private File pythonFile;
	private Formatter pythonFileFomatter;
	private ProcessBuilder analysisProcessBuilder;
	private Process analysisProcess;
	
	ResultsAnalyser(WCETTargetInfo wcetTargetInfo) throws IOException {		
		makeWCETAnalysisPythonFile();
		analyseResults();
		outputAnalysisResults();
	}
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.measurementResultsLogFileAbsolutePath = wcetTargetInfo.getMeasurementResultsLogFileAbsolutePath();
	}
	
	public void makePythonFile(){
		this.pythonFile = new File(this.workDirectoryAbsolutePath + "\\WCETAnalysis.py");
	}
	public void makePythonFileFormatter() throws FileNotFoundException{
		this.pythonFileFomatter = new Formatter(pythonFile);		
	}
	
	public void closePythonFileFormatter() throws FileNotFoundException{
		this.pythonFileFomatter.close();
	}
	
	public void writePythonCodeForWCETAnalysis() throws FileNotFoundException{
		this.pythonFileFomatter.format("%s", "import matplotlib.pyplot as plt\n");
		this.pythonFileFomatter.format("%s", "\nf = open(" + "r" + "\"" + this.measurementResultsLogFileAbsolutePath + "\")");
		this.pythonFileFomatter.format("%s", "\nlines = f.readlines()");
		this.pythonFileFomatter.format("%s", "\nf.close()");
		this.pythonFileFomatter.format("%s", "\ndata_hex = [line.strip().strip('\\x00') for line in lines]");
		this.pythonFileFomatter.format("%s", "\ndata = [int(n,16) for n in data_hex]");
		this.pythonFileFomatter.format("%s", "\nplt.plot(range(1,len(data)+1), data, 'ro')");
		this.pythonFileFomatter.format("%s", "\nplt.show()");
		this.pythonFileFomatter.format("%s", "\nprint(max(data))");
	}
	
	public void makeWCETAnalysisPythonFile() throws FileNotFoundException{
		makePythonFile();
		makePythonFileFormatter();
		writePythonCodeForWCETAnalysis();
		closePythonFileFormatter();
	}
	
	public void makeAnalysisProcessBuilder(){
		this.analysisProcessBuilder = new ProcessBuilder("python", "WCETAnalysis.py");
		this.analysisProcessBuilder.directory(new File(this.workDirectoryAbsolutePath));
	}
	
	public void startAnalysisProcess() throws IOException{
		this.analysisProcess= analysisProcessBuilder.start();
	}
	public void analyseResults() throws IOException{
		makeAnalysisProcessBuilder();
		startAnalysisProcess();
	}
	
	public void outputAnalysisResults(){
		new StreamScanner(this.analysisProcess);
	}

}