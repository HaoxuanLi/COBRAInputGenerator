import java.io.File;
import java.io.IOException;
import java.util.*;

public class ProcessorTemplateGenerator {
	private Properties config;
	private String device;
	private String targetfilepath;
	private File maincfile;
	private Formatter maincformatter;
	private File mainhfile;
	private Formatter mainhformatter;

	public ProcessorTemplateGenerator(String directory) throws IOException { // directory: block file path
		config = new WCETAnalysis().getConfig("config.properties");
		device = config.getProperty("devicename");
		targetfilepath = config.getProperty("pathtargetprogram");
		
		if (device.equals("at90can128")) {
			// generate main.c file
			new File(directory).mkdir(); // 
			maincfile = new File(directory + "\\main.c");
			maincformatter = new Formatter(maincfile);
			maincformatter.format("%s", "/* Automatically-generated file. Do not edit! */ \n\n/*Include headerfiles*/");
			maincformatter.format("%s", "\n#include \"main.h\" ");
			maincformatter.format("%s", "\n#include \"inputs.h\" ");
			maincformatter.format("%s", "\n#include <avr/interrupt.h>");

			
			maincformatter.format("%s", "\n\n/*Forward declaration of functions.*/");
			maincformatter.format("%s", "\nint main(void);" + " \n");
			
			maincformatter.format("%s", "\n\n/*Forward declaration of variables.*/");
			maincformatter.format("%s", "\nstatic int i=0;" + " \n");
			
			maincformatter.format("%s", "\n/*main function*/");
			maincformatter.format("%s", "\nint main(void){\n");

			maincformatter.format("%s", "\n\t/*GPIO Pins*/");
			maincformatter.format("%s", "\n\tDDRA = 0x00;\n\tDDRC = 0xF0;");

			maincformatter.format("%s", "\n\t/*External interrupt settings*/");
			maincformatter.format("%s",
					"\n\tPORTD = (1 << PD1);\n\tEICRA =	(1 << ISC11) | (0 << ISC10);\n\tEIMSK = (1 << INT1);");

			maincformatter.format("%s", "\n\t/*Initialize function*/");
			maincformatter.format("%s", "\n\t ");

			maincformatter.format("%s", "\n\t/*Enable FPGA Interupt trigger*/");
			maincformatter.format("%s", "\n\tPORTC = (1 << PC5);");
			maincformatter.format("%s", "\n\tPORTC = (0 << PC5);");		
			
			
			maincformatter.format("%s", "\n\t/*Initialize input update counter*/");
			
			maincformatter.format("%s", "\n\t/*Enable interrupt*/");
			maincformatter.format("%s", "\n\tsei();");

			maincformatter.format("%s", "\n\t/*while loop*/");
			maincformatter.format("%s", "\n\twhile(1){}");

			maincformatter.format("%s", "\n\t/*Return*/");
			maincformatter.format("%s", "\n\treturn(1);");

			maincformatter.format("%s", "\n}");

			maincformatter.format("%s", "\n\n/*Interrupt handler*/");
			maincformatter.format("%s", "\nISR(INT1_vect){");

			maincformatter.format("%s", "\n\n\t/*Read input values*/");
			maincformatter.format("%s", "\n\tblockInputUpdate(i);");

			maincformatter.format("%s", "\n\t/*Pull up output pin*/");
			//maincformatter.format("%s", "\n\tPORTC = (1 << PC4);");

			maincformatter.format("%s", "\n\t/*Measure the execution time of the code block*/");
			maincformatter.format("%s", "\n\tbenchmarkBlock();");

			maincformatter.format("%s", "\n\t/*Pull down output pin*/");
			maincformatter.format("%s", this.config.getProperty("tocflag") + " \n");

			maincformatter.format("%s", "\n\ti++;");

			maincformatter.format("%s", "\n}");	

			maincformatter.close();

			mainhfile = new File(directory + "\\main.h");
			mainhformatter = new Formatter(mainhfile);
			mainhformatter.format("%s", "/* Automatically-generated file. Do not edit! */ \n\n/*Include headerfiles*/");

			for (File file : new File(targetfilepath).listFiles()) {
				if (file.getName().endsWith(".h")) {
					mainhformatter.format("%s", "\n#include \"../../" + file.getName() + "\"");
				}
			}
			mainhformatter.format("%s", "\n#include <stdio.h>\n#include <avr/io.h>");

			mainhformatter.format("%s", "\n/*Define macros*/");
			mainhformatter.format("%s", "\n#define bit_is_clear(sfr,bit) (!(_SFR_BYTE(sfr) & _BV(bit)))");

			mainhformatter.close();
		}

	}

}
