import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import VariableClasses.Declaration;

public class OriginalFileModifier {
	private String originalFileAbsolutePath;
	private String workDirectoryAbsolutePath;
	private String programName;
	private String stepNumber;
	private Formatter targetfileformatter;
	private BufferedReader targetfilefilereader;
	private BufferedReader mapfilereader;
	private int blockStartLineNumber;
	private int blockEndLineNumber;
	private int processorRegisterLength;
	private String framePointerValue;
	private String stackPointerValueOfOriginalCode;
	private List<Declaration> declarationListFinal = new ArrayList<Declaration>();
	private List<Declaration> initilizationlist = new ArrayList<Declaration>();

	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.programName = wcetTargetInfo.getProgramName();
		this.originalFileAbsolutePath = wcetTargetInfo.getOriginalFileAbsolutePath();
		this.stepNumber = wcetTargetInfo.getStepNumber();
		this.framePointerValue = wcetTargetInfo.getFramePointerValue();
		this.stackPointerValueOfOriginalCode = wcetTargetInfo.getStackPointerValueOfOriginalCode();
		this.processorRegisterLength = wcetTargetInfo.getProcessorRegisterLength();
		this.blockStartLineNumber = wcetTargetInfo.getBlockStartLineNumber();
		this.blockEndLineNumber = wcetTargetInfo.getBlockEndLineNumber();
	}
	
	public OriginalFileModifier( WCETTargetInfo wcetTargetInfo) throws IOException {
		readWcetTargetInfo(wcetTargetInfo);

		this.targetfilefilereader = new BufferedReader(new FileReader(this.originalFileAbsolutePath));
		
		this.targetfileformatter = new Formatter(new File(this.workDirectoryAbsolutePath + "\\" + this.programName +".c"));
		
		if(this.stepNumber!="1"){
			for (File mapfile : new File(this.workDirectoryAbsolutePath + "\\Debug").listFiles()) {
				if (mapfile.getName().matches(".*\\.map$"))
					this.mapfilereader = new BufferedReader(new FileReader(mapfile));
			}
		}

	
		
	
		
		int i = 1;
		String targetfilereaderline = "";
		this.targetfilefilereader = new BufferedReader(new FileReader(this.originalFileAbsolutePath));
		
		while (((targetfilereaderline = this.targetfilefilereader.readLine())!= null) &&(i<this.blockStartLineNumber)) {
			
			for(Declaration declaration:this.declarationListFinal){				
				if( i>=declaration.getLineNumber()&& targetfilereaderline.replaceAll("\\s", "").matches(".*\\b"+declaration.getDeclarationName().replace("*", "")+"\\b=.*")){// && (targetfilereaderline.replaceAll("\\s", " ").matches(".*\\b"+declaration.getVariableName()+"\\b=.*")||targetfilereaderline.replaceAll("\\s", " ").matches(".*\\b"+declaration.getVariableName().replace("*", "")+"\\b=.*"))){	
						Declaration initialization = new Declaration();
						initialization.setDeclarationName(declaration.getDeclarationName());
						initialization.setLineNumber(i);
						
						this.initilizationlist.add(initialization);
					}
			}
			i++;
		}
		
		
		this.targetfilefilereader.close(); 
		i=0;
		this.targetfilefilereader = new BufferedReader(new FileReader(this.originalFileAbsolutePath));
		while ((targetfilereaderline = this.targetfilefilereader.readLine()) != null) {
			i++;
		//	if(targetfilereaderline.contains("="))
		//		System.out.println(targetfilereaderline);

			String register_address_r14r15 = "";
			String register_address_org = "";
			String framepointer_address = "";
			String stackpointer_address_org = "";
			String stack_address_org = "";
			String mapfilereaderline = "";

			
			if (i == this.blockStartLineNumber) {

				switch (this.stepNumber) {

				case "1":
					break;

				case "2":
					while ((mapfilereaderline = this.mapfilereader.readLine()) != null) {
						if (mapfilereaderline.matches("\\s*0x\\w*\\s*register_address_r14r15$")) {
							String register_address_r14r15_temp = mapfilereaderline
									.replaceAll("register_address_r14r15$", "").replaceAll("\\s", "");
							register_address_r14r15 = register_address_r14r15_temp.substring(
									register_address_r14r15_temp.length() - 4, register_address_r14r15_temp.length());
						}
						if (mapfilereaderline.matches("\\s*0x\\w*\\s*stackpointer_address_org$")) {
							String stackpointer_address_org_temp = mapfilereaderline
									.replaceAll("stackpointer_address_org$", "").replaceAll("\\s", "");
							stackpointer_address_org = stackpointer_address_org_temp.substring(
									stackpointer_address_org_temp.length() - 4, stackpointer_address_org_temp.length());
						}
					}
					this.targetfileformatter.format("%s", "\tasm(\n");
					this.targetfileformatter.format("%s", "\t/*store values in r14 and r15*/\n");
					this.targetfileformatter.format("%s", "\t\"sts	0x" + register_address_r14r15 + ", r14\\n\\t\"\n");
					this.targetfileformatter.format("%s",
							"\t\"sts	0x" + Integer.toHexString(Integer.parseInt(register_address_r14r15, 16) + 1)
									+ ", r15\\n\\t\"\n");

					this.targetfileformatter.format("%s", "\t/*store stack pointer to stackpointer_address_org*/\n");
					this.targetfileformatter.format("%s", "\t\"in	r14, 0x3d\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"in	r15, 0x3e\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"sts	0x" + stackpointer_address_org + ", r14\\n\\t\"\n");
					this.targetfileformatter.format("%s",
							"\t\"sts	0x" + Integer.toHexString(Integer.parseInt(stackpointer_address_org, 16) + 1)
									+ ", r15\\n\\t\"\n");

					this.targetfileformatter.format("%s", "\t/*set the values in r14 and r15 back*/\n");
					this.targetfileformatter.format("%s", "\t\"lds	r14, 0x" + register_address_r14r15 + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r14, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_r14r15, 16) + 1) + "\\n\\t\"\n");

					this.targetfileformatter.format("%s", "\t);\n");
					break;

				case "3":
				case "4":
				case "5":
					
					while ((mapfilereaderline = this.mapfilereader.readLine()) != null) {

						if (mapfilereaderline.matches("\\s*0x\\w*\\s*register_address_org$")) {
							String register_address_org_temp = mapfilereaderline.replaceAll("register_address_org$", "")
									.replaceAll("\\s", "");
							register_address_org = register_address_org_temp.substring(
									register_address_org_temp.length() - 4, register_address_org_temp.length());
						}
						if (mapfilereaderline.matches("\\s*0x\\w*\\s*stackpointer_address_org$")) {
							String stackpointer_address_org_temp = mapfilereaderline
									.replaceAll("stackpointer_address_org$", "").replaceAll("\\s", "");
							stackpointer_address_org = stackpointer_address_org_temp.substring(
									stackpointer_address_org_temp.length() - 4, stackpointer_address_org_temp.length());
						}
						if (mapfilereaderline.matches("\\s*0x\\w*\\s*stack_address_org$")) {
							String stack_address_org_temp = mapfilereaderline.replaceAll("stack_address_org$", "")
									.replaceAll("\\s", "");
							stack_address_org = stack_address_org_temp.substring(stack_address_org_temp.length() - 4,
									stack_address_org_temp.length());
						}
						if (mapfilereaderline.matches("\\s*0x\\w*\\s*framepointer_address$")) {
							String framepointer_address_temp = mapfilereaderline.replaceAll("framepointer_address$", "")
									.replaceAll("\\s", "");
							framepointer_address = framepointer_address_temp.substring(
									framepointer_address_temp.length() - 4, framepointer_address_temp.length());
						}
					}
					this.targetfileformatter.format("%s", "\tasm(\n");
					this.targetfileformatter.format("%s",
							"\t/*store register values of the original code to register_address_org*/\n");
					for (int r = 0; r < this.processorRegisterLength; r++) {
						this.targetfileformatter.format("%s",
								"\t\"sts	0x" + Integer.toHexString(Integer.parseInt(register_address_org, 16) + r)
										+ ", r" + r + "\\n\\t\"\n");
					}
					this.targetfileformatter.format("%s", "\t/*store stack pointer to stackpointer_address_org*/\n");
					this.targetfileformatter.format("%s", "\t\"in	r14, 0x3d\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"in	r15, 0x3e\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"sts	0x" + stackpointer_address_org + ", r14\\n\\t\"\n");
					this.targetfileformatter.format("%s",
							"\t\"sts	0x" + Integer.toHexString(Integer.parseInt(stackpointer_address_org, 16) + 1)
									+ ", r15\\n\\t\"\n");

					this.targetfileformatter.format("%s", "\t/*store the stack to stack_address_org*/\n");
					this.targetfileformatter.format("%s", "\t\"ldi	r24, 0x00\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"ldi	r25, 0x00\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r26, 0x" + stackpointer_address_org + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r27, 0x"
							+ Integer.toHexString(Integer.parseInt(stackpointer_address_org, 16) + 1) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s",
							"\t\"ldi	r30, 0x" + stack_address_org.substring(2, 4) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s",
							"\t\"ldi	r31, 0x" + stack_address_org.substring(0, 2) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"ld     r14, X+\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"st     Z+,	r14\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"adiw	r24, 0x01\\n\\t\"\n");
					this.targetfileformatter.format("%s",
							"\t\"cpi	r24, 0x" + (Integer.parseInt(this.framePointerValue,16)-Integer.parseInt(this.stackPointerValueOfOriginalCode,16)+1) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"brne	.-10\\n\\t\"\n");

					this.targetfileformatter.format("%s", "\t/*restore the values in registers*/\n");
					this.targetfileformatter.format("%s", "\t\"lds	r14, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 14) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r15, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 15) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r24, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 24) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r25, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 25) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r26, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 26) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r27, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 27) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r30, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 30) + "\\n\\t\"\n");
					this.targetfileformatter.format("%s", "\t\"lds	r31, 0x"
							+ Integer.toHexString(Integer.parseInt(register_address_org, 16) + 31) + "\\n\\t\"\n");

					this.targetfileformatter.format("%s", "\t);\n");

					this.targetfileformatter.format("%s", "\tasm(\"Tic_flag:\");\n");
					break;
				}
			}
			/* add flags to initializations
			for(Declaration initialization: this.initilizationlist ){
				if(i==initialization.getLineNumber())
					targetfileformatter.format("%s", "\tasm(\"" + initialization.getVariableName().replaceAll("\\*", "pointer_") + "_InitializationStart:\");\n");	
				if(i==(initialization.getLineNumber()+1))
					targetfileformatter.format("%s", "\tasm(\"" + initialization.getVariableName().replaceAll("\\*", "pointer_") + "_InitializationEnd:\");\n");
			}
			*/
			if ((i == this.blockEndLineNumber +1 ) && (this.stepNumber == "3" || this.stepNumber == "4"|| this.stepNumber == "5")) {
				this.targetfileformatter.format("%s", "\tasm(\"Toc_flag:\");\n");
			}
			
			
			this.targetfileformatter.format("%s", targetfilereaderline + "\n");

		}
		this.targetfileformatter.close();
		this.targetfilefilereader.close();
	}
}