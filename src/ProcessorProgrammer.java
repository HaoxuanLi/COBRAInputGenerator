import java.lang.ProcessBuilder;
import java.io.*;

public class ProcessorProgrammer {
	private ProcessBuilder makeProcessBuilder;
	private ProcessBuilder programProcessBuilder;
	private Process makeProcess;
	private Process programProcess;
	private String workDirectoryAbsolutePath;
	private String avrFilesOutputDirectoryAbsolutePath;
	private String atprogramPath;
	private String toolName;
	private String interfaceName;
	private String deviceName;
	
	ProcessorProgrammer(WCETTargetInfo wcetTargetInfo) throws IOException {
		readWcetTargetInfo(wcetTargetInfo);
	}
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.avrFilesOutputDirectoryAbsolutePath = wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
		this.atprogramPath = wcetTargetInfo.getAtprogramPath();
		this.toolName = wcetTargetInfo.getToolName();
		this.interfaceName = wcetTargetInfo.getInterfaceName();
		this.deviceName = wcetTargetInfo.getDeviceName();
	}
	
	public void makeprogramfile() throws IOException, InterruptedException {
			this.makeProcessBuilder = new ProcessBuilder("make");
			this.makeProcessBuilder.directory(new File(this.avrFilesOutputDirectoryAbsolutePath));
			this.makeProcess = this.makeProcessBuilder.inheritIO().start();
			this.makeProcess.waitFor();
			// scanStream(makeprocess);
	}

	public void cleanprogramfile() throws IOException, InterruptedException {
	this.makeProcessBuilder = new ProcessBuilder("make", "clean");
			this.makeProcessBuilder.directory(new File(this.avrFilesOutputDirectoryAbsolutePath));
			this.makeProcess = this.makeProcessBuilder.inheritIO().start();
			this.makeProcess.waitFor();
			// scanStream(makeprocess);
	}

	public void deployprogram() throws IOException {

		try {
			String deployfilestring = "";

			for (File file : new File(this.avrFilesOutputDirectoryAbsolutePath).listFiles()) {
				if (file.getName().endsWith(".elf")) {
					deployfilestring = file.getName();
				}

			}
			String[] deploycommand = { atprogramPath, "-t", toolName, "-i", interfaceName, "-d", deviceName, "program",
					"-f", deployfilestring };
			this.programProcessBuilder = new ProcessBuilder(deploycommand);
			this.programProcessBuilder.directory(new File(avrFilesOutputDirectoryAbsolutePath));
			this.programProcess = this.programProcessBuilder.start();

			new StreamScanner(this.programProcess);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eraseprogram() throws IOException {

		try {

			String[] deploycommand = { this.atprogramPath, "-t", this.toolName, "-i", this.interfaceName, "-d", this.deviceName, "chiperase" };

			this.programProcessBuilder = new ProcessBuilder(deploycommand);
			this.programProcessBuilder.directory(new File(this.avrFilesOutputDirectoryAbsolutePath));
			this.programProcess = this.programProcessBuilder.start();

			new StreamScanner(this.programProcess);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearData() {
		File file = new File(this.workDirectoryAbsolutePath);
		for (File subfile : file.listFiles()) {
			if (subfile.isDirectory()) {
				clearData(subfile.getAbsolutePath());
			} else {
				subfile.delete();
			}
		}
		file.delete();
	}

	public void clearData(String workDirectoryAbsolutePath) {
		File file = new File(workDirectoryAbsolutePath);
		for (File subfile : file.listFiles()) {
			if (subfile.isDirectory()) {
				clearData(subfile.getAbsolutePath());
			} else {
				subfile.delete();
			}
		}
		file.delete();
	}

}
