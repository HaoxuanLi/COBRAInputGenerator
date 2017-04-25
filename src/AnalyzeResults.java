import java.io.*;
import java.util.Formatter;

class AnalyzeResults {
	
	
	private static File pythonfile;
	private static Formatter pythonformatter ;

	String logfilepath = ("C:\\Users\\HLi\\AppData\\Roaming\\Local Libraries\\Local Documents\\workspaceJava\\WCET\\PW_DRV_90can128\\PW_Template_90can1282\\log.txt");

	public static void main(String a[]) throws IOException {
		String pathtargetprogram = "C:\\Users\\HLi\\AppData\\Roaming\\Local Libraries\\Local Documents\\workspaceJava\\WCET\\PW_DRV_90can128\\PW_Template_90can1282";
		pythonfile = new File(pathtargetprogram + "\\WCETAnalysis.py");
		
		pythonformatter = new Formatter(pythonfile);
		pythonformatter.format("%s", "import matplotlib.pyplot as plt\n");
		pythonformatter.format("%s", "\nf = open(" + "r" + "\"" +  pathtargetprogram  + "\\log.txt" + "\")");
		pythonformatter.format("%s", "\nlines = f.readlines()");
		pythonformatter.format("%s", "\nf.close()");
		pythonformatter.format("%s", "\ndata_hex = [line.strip().strip('\\x00') for line in lines]");
		pythonformatter.format("%s", "\ndata = [int(n,16) for n in data_hex]");
		pythonformatter.format("%s", "\nplt.plot(range(1,len(data)+1), data, 'ro')");
		pythonformatter.format("%s", "\nplt.show()");
		pythonformatter.format("%s", "\nmax(data)");
		
		pythonformatter.close();
			
			

		ProcessBuilder pythonbuilder = new ProcessBuilder("python", pythonfile.getPath());
	    pythonbuilder.start();

		
		
	}
}