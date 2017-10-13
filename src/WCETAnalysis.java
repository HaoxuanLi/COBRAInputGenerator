import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Formatter;
import java.util.Properties;
import java.lang.ProcessBuilder;
import java.io.*;

public class WCETAnalysis {
	// Config
	private Properties config;
	private ProcessBuilder makeprocessbuilder;
	private ProcessBuilder programprocessbuilder;
	private Process makeprocess;
	private Process programprocess;
	private String directory;

	WCETAnalysis() {

	}

	WCETAnalysis(String directory) throws IOException {
		this.config = getConfig("config.properties");
		this.directory = directory;
	}

	public void makeprogramfile() throws IOException {

		try {

			String pathtargetprogram = this.directory + "\\Debug";

			this.makeprocessbuilder = new ProcessBuilder("make");
			this.makeprocessbuilder.directory(new File(pathtargetprogram));
			this.makeprocess = this.makeprocessbuilder.inheritIO().start();
			this.makeprocess.waitFor();
			// scanStream(makeprocess);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cleanprogramfile() throws IOException {

		try {
			String pathtargetprogram = this.directory + "\\Debug";

			this.makeprocessbuilder = new ProcessBuilder("make", "clean");
			this.makeprocessbuilder.directory(new File(pathtargetprogram));
			this.makeprocess = this.makeprocessbuilder.inheritIO().start();
			this.makeprocess.waitFor();
			// scanStream(makeprocess);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deployprogram() throws IOException {

		try {
			String pathtargetprogram = this.directory + "\\Debug";
			String atprogram = this.config.getProperty("atprogram");
			String toolname = this.config.getProperty("toolname");
			String interfacename = this.config.getProperty("interfacename");
			String devicename = this.config.getProperty("devicename");
			String deployfilestring = "";

			for (File file : new File(pathtargetprogram).listFiles()) {
				if (file.getName().endsWith(".elf")) {
					deployfilestring = file.getName();
				}

			}

			String[] deploycommand = { atprogram, "-t", toolname, "-i", interfacename, "-d", devicename, "program",
					"-f", deployfilestring };
			this.programprocessbuilder = new ProcessBuilder(deploycommand);
			this.programprocessbuilder.directory(new File(pathtargetprogram));
			this.programprocess = this.programprocessbuilder.start();

			scanStream(this.programprocess);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eraseprogram() throws IOException {

		try {
			String pathtargetprogram = this.directory + "\\Debug";
			String atprogram = this.config.getProperty("atprogram");
			String toolname = this.config.getProperty("toolname");
			String interfacename = this.config.getProperty("interfacename");
			String devicename = this.config.getProperty("devicename");

			String[] deploycommand = { atprogram, "-t", toolname, "-i", interfacename, "-d", devicename, "chiperase" };

			this.programprocessbuilder = new ProcessBuilder(deploycommand);
			this.programprocessbuilder.directory(new File(pathtargetprogram));
			this.programprocess = this.programprocessbuilder.start();

			scanStream(this.programprocess);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CollectResults(long inputlength, int bytelength, Boolean appendflag) throws IOException {
		int resultcounter = 0;
		String serialportNO = this.config.getProperty("serialportNO");
		SerialPort serialPort = new SerialPort(serialportNO);
		String logfilepath = this.directory + "\\log.txt";
		Formatter logformatter = new Formatter(new BufferedWriter(new FileWriter(logfilepath, appendflag)));


		byte[] buffer;

		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);// Set params.
			while (serialPort != null && resultcounter < inputlength) {
				buffer = serialPort.readBytes(bytelength); // Read bytes from
															// serial port
				String t = new String(buffer, 0, bytelength);
				logformatter.format("%s", t);
				if (t.contains("\n"))
					resultcounter++;
			
				
				System.out.println(t);
			}

		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// System.out.println(serialportNO + "is already in use!");
		} // Open serial port

		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Results collected!");
		logformatter.close();
		System.out.println("Port Closed!");
		
		

	}

	public void AnalyzeResults() throws IOException {
		try {
			String logfilepath = this.directory + "\\log.txt";
			File pythonfile = new File(this.directory + "\\WCETAnalysis.py");
			Formatter pythonformatter = new Formatter(pythonfile);

			pythonformatter.format("%s", "import matplotlib.pyplot as plt\n");
			pythonformatter.format("%s", "\nf = open(" + "r" + "\"" + logfilepath + "\")");
			pythonformatter.format("%s", "\nlines = f.readlines()");
			pythonformatter.format("%s", "\nf.close()");
			pythonformatter.format("%s", "\ndata_hex = [line.strip().strip('\\x00') for line in lines]");
			pythonformatter.format("%s", "\ndata = [int(n,16) for n in data_hex]");
			pythonformatter.format("%s", "\nplt.plot(range(1,len(data)+1), data, 'ro')");
			pythonformatter.format("%s", "\nplt.show()");
			pythonformatter.format("%s", "\nprint(max(data))");

			pythonformatter.close();

			ProcessBuilder pythonprocessbuilder = new ProcessBuilder("python", "WCETAnalysis.py");
			pythonprocessbuilder.directory(new File(this.directory));
			Process pythonprocess = pythonprocessbuilder.start();

			scanStream(pythonprocess);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void scanStream(Process process) {
		Scanner outputmsg = new Scanner(process.getInputStream());
		Scanner errormsg = new Scanner(process.getErrorStream());

		while (outputmsg.hasNextLine()) {
			System.out.println(outputmsg.nextLine());
		}
		outputmsg.close();
		while (errormsg.hasNextLine()) {
			System.out.println(errormsg.nextLine());
		}
		errormsg.close();
	}

	public Properties getConfig(String configfilename) throws IOException {
		Properties properities = new Properties();
		InputStream inputstream = getClass().getClassLoader().getResourceAsStream(configfilename);

		if (inputstream != null) {
			try {
				properities.load(inputstream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inputstream.close();
			}
		} else {
			throw new FileNotFoundException("property file '" + configfilename + "' not found in the classpath");

		}
		return (properities);

	}

	public void clearData() {
		File file = new File(this.directory);
		for (File subfile : file.listFiles()) {
			if (subfile.isDirectory()) {
				clearData(subfile.getAbsolutePath());
			} else {
				subfile.delete();
			}
		}
		file.delete();
	}

	public void clearData(String directory) {
		File file = new File(directory);
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
