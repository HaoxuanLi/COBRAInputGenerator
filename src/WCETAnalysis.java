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
		config = getConfig("config.properties");
		this.directory = directory;
	}

	public void makeprogramfile() throws IOException {

		try {

			String pathtargetprogram = directory + "\\Debug";

			makeprocessbuilder = new ProcessBuilder("make");
			makeprocessbuilder.directory(new File(pathtargetprogram));
			makeprocess = makeprocessbuilder.inheritIO().start();
			makeprocess.waitFor();
			// scanStream(makeprocess);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cleanprogramfile() throws IOException {

		try {
			String pathtargetprogram = directory + "\\Debug";

			makeprocessbuilder = new ProcessBuilder("make", "clean");
			makeprocessbuilder.directory(new File(pathtargetprogram));
			makeprocess = makeprocessbuilder.inheritIO().start();
			makeprocess.waitFor();
			// scanStream(makeprocess);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deployprogram() throws IOException {

		try {
			String pathtargetprogram = directory + "\\Debug";
			String atprogram = config.getProperty("atprogram");
			String toolname = config.getProperty("toolname");
			String interfacename = config.getProperty("interfacename");
			String devicename = config.getProperty("devicename");
			String deployfilestring = "";

			for (File file : new File(pathtargetprogram).listFiles()) {
				if (file.getName().endsWith(".elf")) {
					deployfilestring = file.getName();
				}

			}

			String[] deploycommand = { atprogram, "-t", toolname, "-i", interfacename, "-d", devicename, "program",
					"-f", deployfilestring };
			programprocessbuilder = new ProcessBuilder(deploycommand);
			programprocessbuilder.directory(new File(pathtargetprogram));
			programprocess = programprocessbuilder.start();

			scanStream(programprocess);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void eraseprogram() throws IOException {

		try {
			String pathtargetprogram = directory + "\\Debug";
			String atprogram = config.getProperty("atprogram");
			String toolname = config.getProperty("toolname");
			String interfacename = config.getProperty("interfacename");
			String devicename = config.getProperty("devicename");

			String[] deploycommand = { atprogram, "-t", toolname, "-i", interfacename, "-d", devicename, "chiperase" };

			programprocessbuilder = new ProcessBuilder(deploycommand);
			programprocessbuilder.directory(new File(pathtargetprogram));
			programprocess = programprocessbuilder.start();

			scanStream(programprocess);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CollectResults() throws IOException {
		int resultcounter = 0;
		String serialportNO = config.getProperty("serialportNO");
		SerialPort serialPort = new SerialPort(serialportNO);

		String logfilepath = directory + "\\log.txt";
		Formatter logformatter = new Formatter(new File(logfilepath));
		byte[] buffer;

		try {
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);// Set params.
			while (serialPort != null && resultcounter < 100) {
				buffer = serialPort.readBytes(4); // Read bytes from serial port
				String t = new String(buffer, 0, 4);

				logformatter.format("%s", t);
				//System.out.println(t);
				if (t.contains("\n"))
					resultcounter++;

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
		logformatter.close();
		System.out.println("Port Closed!");

	}

	public void AnalyzeResults() throws IOException {
		try {
			String logfilepath = directory + "\\log.txt";
			File pythonfile = new File(directory + "\\WCETAnalysis.py");
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
			pythonprocessbuilder.directory(new File(directory));
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
}
