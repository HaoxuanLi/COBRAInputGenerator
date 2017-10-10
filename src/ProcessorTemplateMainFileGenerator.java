import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ProcessorTemplateMainFileGenerator {
	private WCETTargetInfo wcetTargetInfo;
	private String deviceName;
	private String originalFileDirectoryAbsolutePath;
	private String avrFilesOutputDirectoryAbsolutePath;
	private String workDirectoryAbsolutePath;
	private String F_CPU;
	private File mainCFile;
	private Formatter mainCFileFormatter;
	private File mainHFile;
	private Formatter mainHFileFormatter;
	private int generatedInputLength;
	private String framepointer_address;
	private String mainContent;
	private String framePointerValue;
	private String stackPointerValueOfOriginalCode;
	private String stackPointerValueOfBlockCode;
	private int processorRegisterLength;
	
	public ProcessorTemplateMainFileGenerator(WCETTargetInfo wcetTargetInfo) throws IOException { 
		readWcetTargetInfo(wcetTargetInfo);
		
		if (this.deviceName.equals("at90can128")) {
			// generate main.c file
			makeWorkDirectory(); //
			createMainCFile();
			createMainHFile();			
		}
	}
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.wcetTargetInfo = wcetTargetInfo;
		this.deviceName = wcetTargetInfo.getDeviceName();
		this.originalFileDirectoryAbsolutePath = wcetTargetInfo.getOriginalFileDirectoryAbsolutePath();
		this.F_CPU = wcetTargetInfo.getF_CPU();
		this.generatedInputLength = wcetTargetInfo.getGeneratedInputLength();
		this.mainContent = wcetTargetInfo.getMainContent();
		this.framePointerValue = wcetTargetInfo.getFramePointerValue();
		this.stackPointerValueOfOriginalCode = wcetTargetInfo.getStackPointerValueOfOriginalCode();
		this.stackPointerValueOfBlockCode = wcetTargetInfo.getStackPointerValueOfBlockCode();
		this.processorRegisterLength = wcetTargetInfo.getProcessorRegisterLength();
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.avrFilesOutputDirectoryAbsolutePath = wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
	}

	public void makeWorkDirectory() {
		if (!new File(this.workDirectoryAbsolutePath).exists())
			new File(this.workDirectoryAbsolutePath).mkdir();
	}

	public void makeMainCFileFormatter() throws FileNotFoundException {
		this.mainCFile = new File(this.workDirectoryAbsolutePath + "\\main.c");
		this.mainCFileFormatter = new Formatter(this.mainCFile);
	}

	public void mainCFileIncludeHeaders() {
		this.mainCFileFormatter.format("%s",
				"/* Automatically-generated file. Do not edit! */ \n\n/*Include headerfiles*/");
		this.mainCFileFormatter.format("%s", "\n#include \"main.h\" ");
		this.mainCFileFormatter.format("%s", "\n#include \"inputs.h\" ");
		this.mainCFileFormatter.format("%s", "\n#include <avr/interrupt.h>");
		this.mainCFileFormatter.format("%s", "\n");
	}

	public void mainCFileDefineMacros() {
		this.mainCFileFormatter.format("%s", "\n#define F_CPU " + this.F_CPU + "UL");
		this.mainCFileFormatter.format("%s", "\n#define BAUD    9600UL");
		this.mainCFileFormatter.format("%s", "\n#define RBAUD   ((F_CPU/(16UL*BAUD)) - 1)");
		this.mainCFileFormatter.format("%s", "\n#define pointer_length   2");
		mainCFileDefineMacrosForReservedSpace();
	}

	public void mainCFileDefineMacrosForReservedSpace() {
		if (this.wcetTargetInfo.isMakingTimingMeasurementModule()){
			mainCFileDefineRegisterLength();
			mainCFileDefineStackLength();
		}
	}

	public void mainCFileDefineRegisterLength() {
		this.mainCFileFormatter.format("%s", "\n#define register_length " + this.processorRegisterLength);
	}

	public void mainCFileDefineStackLength() {
		this.mainCFileFormatter.format("%s",
				"\n#define stack_length_org " + (Integer.parseInt(this.framePointerValue, 16)
						- Integer.parseInt(this.stackPointerValueOfOriginalCode, 16) + 1));
		this.mainCFileFormatter.format("%s",
				"\n#define stack_length_block " + (Integer.parseInt(this.framePointerValue, 16)
						- Integer.parseInt(this.stackPointerValueOfBlockCode, 16) + 1));
	}

	public void mainCFileForwardDeclaration() {
		mainCFileFowardDeclarationOfFunctions();
		mainCFileFowardDeclarationOfVariables();

	}

	public void mainCFileFowardDeclarationOfFunctions() {
		this.mainCFileFormatter.format("%s", "\n\n/*Forward declaration of functions.*/");
		this.mainCFileFormatter.format("%s", "\nint main(void);");
		this.mainCFileFormatter.format("%s", "\nint uart_putchar(char c, FILE *stream);" + " \n");
	}

	public void mainCFileFowardDeclarationOfVariables() {
		this.mainCFileFormatter.format("%s", "\n\n/*Forward declaration of variables.*/");
		this.mainCFileFormatter.format("%s", "\nstatic int measurementRoundCounter=0;");

		mainCFileFowardDeclarationOfReservedSpace();

		mainCFileFormatter.format("%s", "\nvolatile char register_address_r14r15[pointer_length];");
		mainCFileFormatter.format("%s", "\nvolatile char framepointer_address[pointer_length];");
		mainCFileFormatter.format("%s", "\nvolatile char stackpointer_address_org[pointer_length];");
		mainCFileFormatter.format("%s", "\nvolatile char stackpointer_address_block[pointer_length];");
		mainCFileFormatter.format("%s",
				"\nFILE mystdout = FDEV_SETUP_STREAM(uart_putchar, NULL, _FDEV_SETUP_RW);" + " \n");
	}

	public void mainCFileFowardDeclarationOfReservedSpace() {
		if (this.wcetTargetInfo.isMakingTimingMeasurementModule()) {
			mainCFileFowardDeclarationOfSpaceForStoringRegisters();
			mainCFileFowardDeclarationOfSpaceForStoringStacks();
		}
	}

	public void mainCFileFowardDeclarationOfSpaceForStoringRegisters() {
		mainCFileFormatter.format("%s", "\nvolatile char register_address_org[register_length];");
		mainCFileFormatter.format("%s", "\nvolatile char register_address_block[register_length];");
	}

	public void mainCFileFowardDeclarationOfSpaceForStoringStacks() {
		mainCFileFormatter.format("%s", "\nvolatile char stack_address_org[stack_length_org];");
		mainCFileFormatter.format("%s", "\nvolatile char stack_address_block[stack_length_block];");
	}

	public void mainCFileInitializeVariables() {
		mainCFileFormatter.format("%s", "\n\t/*Initialize the pointer and register address variables*/");
		mainCFileFormatter.format("%s", "\n\tint x_registercounter;");
		mainCFileInitializePointerAddressVariables();
		mainCFileInitializeRegisterAddressVariables();
		mainCFileInitializeStackAddressVariables();
	}

	public void mainCFileInitializePointerAddressVariables() {
		mainCFileFormatter.format("%s",
				"\n\tfor(x_registercounter=0; x_registercounter < pointer_length; x_registercounter++){");
		mainCFileFormatter.format("%s", "\n\t\tregister_address_r14r15[x_registercounter]=0;");
		mainCFileFormatter.format("%s", "\n\t\tframepointer_address[x_registercounter]=0;");
		mainCFileFormatter.format("%s", "\n\t\tstackpointer_address_org[x_registercounter]=0;");
		mainCFileFormatter.format("%s",
				"\n\t\tstackpointer_address_block[x_registercounter]=0 ;stackpointer_address_block[x_registercounter]=0 ;");
		mainCFileFormatter.format("%s", "\n\t}");
	}

	public void mainCFileInitializeRegisterAddressVariables() {
		if (this.wcetTargetInfo.isMakingTimingMeasurementModule()) {
			mainCFileFormatter.format("%s",
					"\n\tfor(x_registercounter=0; x_registercounter < register_length; x_registercounter++){");
			mainCFileFormatter.format("%s", "\n\tregister_address_org[x_registercounter];");
			mainCFileFormatter.format("%s", "\n\tregister_address_block[x_registercounter];");
			mainCFileFormatter.format("%s", "\n\t}");
		}
	}

	public void mainCFileInitializeStackAddressVariables() {
		if (this.wcetTargetInfo.isMakingTimingMeasurementModule()) {
			mainCFileFormatter.format("%s",
					"\n\tfor(x_registercounter=0; x_registercounter < stack_length_org; x_registercounter++){");
			mainCFileFormatter.format("%s", "\n\tstack_address_org[x_registercounter];");
			mainCFileFormatter.format("%s", "\n\t}");

			mainCFileFormatter.format("%s",
					"\n\tfor(x_registercounter=0; x_registercounter < stack_length_block; x_registercounter++){");
			mainCFileFormatter.format("%s", "\n\tstack_address_block[x_registercounter];");
			mainCFileFormatter.format("%s", "\n\t}");
		}
	}

	public BufferedReader getMapFileReader() throws FileNotFoundException {
		BufferedReader mapFileReader = null;
		for (File mapfile : new File(this.avrFilesOutputDirectoryAbsolutePath).listFiles()) {
			if (mapfile.getName().matches(".*\\.map$"))
				mapFileReader = new BufferedReader(new FileReader(mapfile));
		}
		return mapFileReader;
	}
	
	public void getFramePointerAddressFromMapFile() throws IOException{
		BufferedReader mapFileReader = getMapFileReader();
		String mapfilereaderline = "";
		
		while ((mapfilereaderline = mapFileReader.readLine()) != null) {
			if (mapfilereaderline.matches("\\s*0x\\w*\\s*framepointer_address$")) {
				String framepointer_address_temp = mapfilereaderline.replaceAll("framepointer_address$", "")
						.replaceAll("\\s", "");
				this.framepointer_address = framepointer_address_temp
						.substring(framepointer_address_temp.length() - 4, framepointer_address_temp.length());
			}
		}
		mapFileReader.close();
	}
	
	public void mainCFileCreateAssemblyCodeToStoreMainFunctionFramePointerValueToFramePointerAddress(){
		mainCFileFormatter.format("%s", "\n\tasm(");
		mainCFileFormatter.format("%s", "\n\t\"in	r14, 0x3d\\n\\t\"");
		mainCFileFormatter.format("%s", "\n\t\"in	r15, 0x3e\\n\\t\"");
		mainCFileFormatter.format("%s", "\n\t\"sts	0x" + this.framepointer_address + ", r14\\n\\t\"");
		mainCFileFormatter.format("%s", "\n\t\"sts	0x"
				+ Integer.toHexString(Integer.parseInt(this.framepointer_address, 16) + 1) + ", r15\\n\\t\"");
		mainCFileFormatter.format("%s", "\n\t);");
	}
	
	public void mainCFileProcessorSpecificConfiguration(){
		processorGPIOConfiguration();
		processorExternalInterruptHandlerConfiguration();
		processorSerialPortConfiguration();
	}
	
	public void processorGPIOConfiguration(){
		mainCFileFormatter.format("%s", "\n\t/*GPIO Pins*/");
		mainCFileFormatter.format("%s", "\n\tDDRA = 0x00;\n\tDDRC = 0xF0;");
		
	}
	
	public void processorExternalInterruptHandlerConfiguration(){
		mainCFileFormatter.format("%s", "\n\t/*External interrupt settings*/");
		mainCFileFormatter.format("%s",
				"\n\tPORTD = (1 << PD1);\n\tEICRA =	(1 << ISC11) | (0 << ISC10);\n\tEIMSK = (1 << INT1);");

	}
	
	public void processorSerialPortConfiguration(){
		mainCFileFormatter.format("%s", "\n\t/*Serial port configuration*/");
		mainCFileFormatter.format("%s", "\n\tUBRR0H = (RBAUD >> 8);");
		mainCFileFormatter.format("%s", "\n\tUBRR0L =  RBAUD;");
		mainCFileFormatter.format("%s", "\n\tUCSR0B = (1 << TXEN0) | (1 << RXEN0);");
		mainCFileFormatter.format("%s", "\n\tUCSR0C = (1 << UCSZ01) | (1 << UCSZ00);");
	}
	
	public void mainCFileExecuteTheCompleteOriginalCode(){
		mainCFileFormatter.format("%s", "\n\t/*Execute the complete original code*/");
		mainCFileFormatter.format("%s", "\n\t" + this.mainContent);
	}

	public void mainCFileEnableFPGAInteruptTrigger(){
		mainCFileFormatter.format("%s", "\n\t/*Enable FPGA Interupt trigger*/");
		mainCFileFormatter.format("%s", "\n\tPORTC = (1 << PC5);");
		mainCFileFormatter.format("%s", "\n\tPORTC = (0 << PC5);");
	}
	
	public void mainCFileEnableInterruptHandler(){
		mainCFileFormatter.format("%s", "\n\t/*Enable external interrupt handler*/");
		mainCFileFormatter.format("%s", "\n\tsei();");
	}
	
	public void mainCFileWhileLoop(){
		mainCFileFormatter.format("%s", "\n\t/*while loop*/");
		mainCFileFormatter.format("%s", "\n\twhile(" + "measurementRoundCounter<" + this.generatedInputLength + "){}");
	}
	
	public void mainCFileReturn(){
		mainCFileFormatter.format("%s", "\n\t/*Return*/");
		mainCFileFormatter.format("%s", "\n\treturn(1);");
	}
	
	public void mainCFileCreateMainFunction() throws IOException {
		mainCFileFormatter.format("%s", "\n/*main function*/");
		mainCFileFormatter.format("%s", "\nint main(void){\n");
		mainCFileInitializeVariables();

		if (!wcetTargetInfo.isPreparingToGettingPointerValues()) {
			getFramePointerAddressFromMapFile();
			mainCFileCreateAssemblyCodeToStoreMainFunctionFramePointerValueToFramePointerAddress();
		}
		mainCFileProcessorSpecificConfiguration();
		mainCFileExecuteTheCompleteOriginalCode();
		mainCFileEnableFPGAInteruptTrigger();
		mainCFileEnableInterruptHandler();
		mainCFileWhileLoop();
		mainCFileReturn();
		mainCFileFormatter.format("%s", "\n}");
	}
	
	public void interruptHandlerPrintPointerValues(){
		mainCFileFormatter.format("%s", "\n\t/*Print pointer values*/");
		mainCFileFormatter.format("%s", "\n\tif(measurementRoundCounter==1){");
		mainCFileFormatter.format("%s",
				"\n\t\tfprintf(&mystdout,\"%02x%02x\\n\",framepointer_address[1],framepointer_address[0]);");
		mainCFileFormatter.format("%s",
				"\n\t\tfprintf(&mystdout,\"%02x%02x\\n\",stackpointer_address_org[1],stackpointer_address_org[0]);");
		mainCFileFormatter.format("%s",
				"\n\t\tfprintf(&mystdout,\"%02x%02x\\n\",stackpointer_address_block[1],stackpointer_address_block[0]);");
		mainCFileFormatter.format("%s", "\n\t}");
	}

	public void mainCFileCreateInterruptHandler(){
		
		mainCFileFormatter.format("%s", "\n\n/*Interrupt handler*/");
		mainCFileFormatter.format("%s", "\nISR(INT1_vect){");

		mainCFileFormatter.format("%s", "\n\n\t/*Read input values*/");
		mainCFileFormatter.format("%s", "\n\tblockInputUpdate(measurementRoundCounter);");

		//mainCFileFormatter.format("%s", "\n\t/*Pull up output pin*/");
		// mainCFileFormatter.format("%s", "\n\tPORTC = (1 << PC4);");

		mainCFileFormatter.format("%s", "\n\t/*Measure the execution time of the code block*/");
		mainCFileFormatter.format("%s", "\n\tbenchmarkBlock();");

		//mainCFileFormatter.format("%s", "\n\t/*Pull down output pin*/");
		//mainCFileFormatter.format("%s", this.config.getProperty("tocStamp") + " \n");
		
		mainCFileFormatter.format("%s", "\n\t/*Update measurement round counter*/");
		mainCFileFormatter.format("%s", "\n\tmeasurementRoundCounter++;");
		
		interruptHandlerPrintPointerValues();
		
		mainCFileFormatter.format("%s", "\n}");
	}
	public void mainCFileCreateUARTFunction(){
		mainCFileFormatter.format("%s", "\n\n/*USART*/");
		mainCFileFormatter.format("%s", "\nint uart_putchar(char c, FILE *stream)");
		mainCFileFormatter.format("%s", "\n{");
		mainCFileFormatter.format("%s", "\n\tif (c == '\\n') uart_putchar('\\r', stream);");
		mainCFileFormatter.format("%s", "\n\twhile( ! ( UCSR0A & (1 << UDRE0)));");
		mainCFileFormatter.format("%s", "\n\tUDR0 = c;");
		mainCFileFormatter.format("%s", "\n\treturn 0;");
		mainCFileFormatter.format("%s", "\n}");
	}
	
	public void closeMainCFileFormatter(){
		this.mainCFileFormatter.close();
	}
	
	public void writeToMainCFile() throws IOException{
		mainCFileIncludeHeaders();
		mainCFileDefineMacros();
		mainCFileForwardDeclaration();
		mainCFileCreateMainFunction();
		mainCFileCreateInterruptHandler();
		mainCFileCreateUARTFunction();
	}
	
	public void createMainCFile() throws IOException{
		makeMainCFileFormatter();
		writeToMainCFile();
		closeMainCFileFormatter();
	}
	
	public void makeMainHFileFormatter() throws FileNotFoundException{
		this.mainHFile = new File(this.workDirectoryAbsolutePath + "\\main.h");
		this.mainHFileFormatter = new Formatter(this.mainHFile);
	}
	
	public void mainHFileIncludeHeaders(){
		mainHFileFormatter.format("%s",
				"/* Automatically-generated file. Do not edit! */ \n\n/*Include headerfiles*/");
		mainHFileFormatter.format("%s", "\n#include <stdio.h>\n#include <avr/io.h>");
		
		includeOriginalHeaderFiles();
	}
	
	public void includeOriginalHeaderFiles(){
		for (File file : new File(originalFileDirectoryAbsolutePath).listFiles()) {
			if (file.getName().endsWith(".h")) {
				mainHFileFormatter.format("%s", "\n#include \"" + file.getName() + "\"");
			}
		}
	}
		
	public void mainHFileDefineMacros(){
		mainHFileFormatter.format("%s", "\n/*Define macros*/");
		mainHFileFormatter.format("%s", "\n#define bit_is_clear(sfr,bit) (!(_SFR_BYTE(sfr) & _BV(bit)))");
	}
	
	public void closeMainHFileFormatter(){
		mainHFileFormatter.close();
	}	
	
	public void writeToMainHFile(){
		mainHFileIncludeHeaders();
		mainHFileDefineMacros();
	}
	
	public void createMainHFile() throws IOException{
		makeMainHFileFormatter();
		writeToMainHFile();
		closeMainHFileFormatter();
	}
	
}
