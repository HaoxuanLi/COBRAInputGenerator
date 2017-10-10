import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MakeFileGenerator {
	private String deviceName;
	private String avrFilesOutputDirectoryAbsolutePath;
	private File makeFile;
	private Formatter makeFileFormatter;
	private String avrPath;
	private String avrOutputFilesName;
	private String avrGccPath;
	private String avrincludePath;
	private String avrsizePath;
	private String avrobjdumpPath;
	private String workDirectoryAbsolutePath;
	
	public void readWcetTargetInfo(WCETTargetInfo wcetTargetInfo) {
		this.deviceName = wcetTargetInfo.getDeviceName();
		this.avrFilesOutputDirectoryAbsolutePath = wcetTargetInfo.getAvrFilesOutputDirectoryAbsolutePath();
		this.workDirectoryAbsolutePath = wcetTargetInfo.getWorkDirectoryAbsolutePath();
		this.avrPath = wcetTargetInfo.getAvrPath();
		this.avrGccPath = wcetTargetInfo.getAvrGccPath();
		this.avrincludePath = wcetTargetInfo.getAvrincludePath();
		this.avrsizePath = wcetTargetInfo.getAvrsizePath();
		this.avrobjdumpPath = wcetTargetInfo.getAvrobjdumpPath();
		this.avrOutputFilesName = wcetTargetInfo.getAvrOutputFilesName();
	}
	
	public void makeAvrFilesOutputDirectory() {
		if (!new File(this.avrFilesOutputDirectoryAbsolutePath).exists())
			new File(this.avrFilesOutputDirectoryAbsolutePath).mkdir();
	}
	
	public void makeMakeFileFormatter() throws FileNotFoundException {
		this.makeFile = new File(avrFilesOutputDirectoryAbsolutePath + "\\makeFile");
		this.makeFileFormatter = new Formatter(makeFile);
	}
	
	public void closeMakeFileFormatter(){
		this.makeFileFormatter.close();
	}
	
	public void writeToMakeFile(){
		makeFileFormatter.format("%s", "# Automatically-generated file. Do not edit!");
		makeFileFormatter.format("%s", "\nDEVICE :=" + deviceName + " \nAVRPATH :=" + avrPath + "\nPRJ_NAME :="
				+ avrOutputFilesName
				+ "\nSHELL := cmd.exe\nRM := rm -rf\nUSER_OBJS :=\nLIBS := \nPROJ := \nO_SRCS := \nC_SRCS := \nS_SRCS := \nS_UPPER_SRCS := \nOBJ_SRCS := \nASM_SRCS := \nPREPROCESSING_SRCS := \nOBJS := \nOBJS_AS_ARGS := \nC_DEPS := \nC_DEPS_AS_ARGS := \nEXECUTABLES := \nOUTPUT_FILE_PATH :=\nOUTPUT_FILE_PATH_AS_ARGS :=\nAVR_APP_PATH :=$$$AVR_APP_PATH$$$\nQUOTE :=\"\nADDITIONAL_DEPENDENCIES :=\nC_SRCS :=\nLIB_DEP :=\nLINKER_SCRIPT_DEP :=");
		makeFileFormatter.format("%s",
				"\n\n# Every subdirectory with source files must be described here\n\nSUBDIRS :=  ");

		for (File file : new File(this.avrFilesOutputDirectoryAbsolutePath).listFiles()) {
			if (file.isDirectory()) {
				makeFileFormatter.format("%s", " \\ \n../" + file.getName());
			}					
		}
		
		makeFileFormatter.format("%s",
				"\n\nAdd inputs and outputs from these tool invocations to the build variables C_SRCS += ");

		for (File file : new File(this.workDirectoryAbsolutePath).listFiles()) {
			if (file.getName().endsWith(".c")) {
				makeFileFormatter.format("%s", " \\\n../" + file.getName());
			}
		}

		makeFileFormatter.format("%s", "\n\nOBJS += ");

		for (File file : new File(workDirectoryAbsolutePath).listFiles()) {
			if (file.getName().endsWith(".c")) {
				makeFileFormatter.format("%s", " \\\n" + file.getName().replace(".c", ".o"));
			}
		}
		
		makeFileFormatter.format("%s", "\n\nOBJS_AS_ARGS += ");

		for (File file : new File(workDirectoryAbsolutePath).listFiles()) {
			if (file.getName().endsWith(".c")) {
				makeFileFormatter.format("%s", " \\\n" + file.getName().replace(".c", ".o"));
			}		
		}

		makeFileFormatter.format("%s", "\n\nC_DEPS += ");

		for (File file : new File(workDirectoryAbsolutePath).listFiles()) {
			if (file.getName().endsWith(".c")) {
				makeFileFormatter.format("%s", " \\\n" + file.getName().replace(".c", ".d"));
			}
		}

		makeFileFormatter.format("%s", "\n\nC_DEPS_AS_ARGS +=  ");

		for (File file : new File(workDirectoryAbsolutePath).listFiles()) {
			if (file.getName().endsWith(".c")) {
				makeFileFormatter.format("%s", " \\\n" + file.getName().replace(".c", ".d"));
			}
		}
		
		makeFileFormatter.format("%s", "\n\nOUTPUT_FILE_PATH +=" + avrOutputFilesName + ".elf");
		makeFileFormatter.format("%s", "\n\nOUTPUT_FILE_PATH_AS_ARGS +=" + avrOutputFilesName + ".elf");
		makeFileFormatter.format("%s", "\n./%.o: .././%.c\n\t@echo Building file: $<" + "\n\t$(QUOTE)" + avrGccPath
				+ "$(QUOTE)  -x c -funsigned-char -funsigned-bitfields -DDEBUG -DDEBUG  -I$(QUOTE)" + avrincludePath
				+ "$(QUOTE)  -O1 -ffunction-sections -fdata-sections -fpack-struct -fshort-enums -mrelax -g2 -Wall -mmcu=$(DEVICE)$ -c -std=gnu99 -MD -MP -MF $(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.o)$(QUOTE)   -o $(QUOTE)$@$(QUOTE) $(QUOTE)$<$(QUOTE) \n\t@echo Finished building: $<");
		
		makeFileFormatter.format("%s",
				"\n\nifneq ($(MAKECMDGOALS),clean) \nifneq ($(strip $(C_DEPS)),) \n-include $(C_DEPS) \nendif \nendif");

		makeFileFormatter.format("%s", "\n\n# Build Target .elf .map .lss file");
		makeFileFormatter.format("%s", "\nall: $(OUTPUT_FILE_PATH) $(ADDITIONAL_DEPENDENCIES)");
		makeFileFormatter.format("%s",
				"\n$(OUTPUT_FILE_PATH): $(OBJS) $(USER_OBJS) $(C_SRCS) $(LIB_DEP) $(LINKER_SCRIPT_DEP)\n\t@echo Building target: $@\n\t$(QUOTE)"
						+ avrGccPath
						+ "$(QUOTE) -o$(OUTPUT_FILE_PATH_AS_ARGS) $(OBJS_AS_ARGS) $(USER_OBJS) $(LIBS) -Wl,-Map=$(QUOTE)$(PRJ_NAME).map$(QUOTE) -Wl,--start-group -Wl,-lm  -Wl,--end-group -Wl,--gc-sections -mrelax -mmcu=$(DEVICE)$  \n\t$(QUOTE)" 
						+ avrobjdumpPath + "$(QUOTE) -h -S $(QUOTE)$(PRJ_NAME).elf$(QUOTE) > $(QUOTE)$(PRJ_NAME).lss$(QUOTE)" + "\n\t$(QUOTE)"
						+ avrsizePath + "$(QUOTE) $(QUOTE)$(PRJ_NAME).elf$(QUOTE)");
		makeFileFormatter.format("%s", "\n\n# Clean Files");
		makeFileFormatter.format("%s",
				"\nclean:\n\t-$(RM) $(OBJS_AS_ARGS) $(EXECUTABLES)  \n\t-$(RM) $(C_DEPS_AS_ARGS)   \n\trm -rf $(QUOTE)$(PRJ_NAME).elf$(QUOTE) $(QUOTE)$(PRJ_NAME).a$(QUOTE) $(QUOTE)$(PRJ_NAME).hex$(QUOTE) $(QUOTE)$(PRJ_NAME).lss$(QUOTE) $(QUOTE)$(PRJ_NAME).eep$(QUOTE) $(QUOTE)$(PRJ_NAME).map$(QUOTE) $(QUOTE)$(PRJ_NAME).srec$(QUOTE) $(QUOTE)$(PRJ_NAME).usersignatures$(QUOTE)");

	}
	
	public void createMakeFile() throws FileNotFoundException{
		makeMakeFileFormatter();
		writeToMakeFile();
		closeMakeFileFormatter();
	}
	
	
	
	public MakeFileGenerator(WCETTargetInfo wcetTargetInfo) throws IOException {
		readWcetTargetInfo(wcetTargetInfo);
		if (deviceName.equals("at90can128")) {
			makeAvrFilesOutputDirectory();
			createMakeFile();
		}
	}
}