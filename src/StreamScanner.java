import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class StreamScanner {
	
	StreamScanner(Process process) {
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
	
	StreamScanner(Process process, String logFile) throws FileNotFoundException {
		
		Formatter logFileFormatter = new Formatter(new File(logFile));
		
		Scanner outputmsg = new Scanner(process.getInputStream());
		Scanner errormsg = new Scanner(process.getErrorStream());

		while (outputmsg.hasNextLine()) {
			logFileFormatter.format("%s", outputmsg.nextLine()+"\n");
		}
		outputmsg.close();
		while (errormsg.hasNextLine()) {
			System.out.println(errormsg.nextLine());
		}
		errormsg.close();
		logFileFormatter.close();
	}
}
