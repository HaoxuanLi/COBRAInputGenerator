import java.io.File;
import java.io.IOException;
import java.util.*;

public class MakefileGenerator {
	private Properties config;
	private String device;
	private String targetfilepath;
	private String makefilepath;
	private File makefile;
	private Formatter makeformatter;
	private String avrpath;
	private String projectname;
	private String avrgcc;
	private String avrinclude;
	private String avrsize;

	public MakefileGenerator(String directory) throws IOException { // directory: block file path
		
		config = new WCETAnalysis().getConfig("config.properties");
		device = config.getProperty("devicename");

		if (device.equals("at90can128")) {
			targetfilepath = config.getProperty("pathtargetprogram");
			makefilepath = directory + "\\Debug";
			avrpath = config.getProperty("avrpath");
			projectname = directory.substring(directory.lastIndexOf(File.separator) + 1);
			avrgcc = avrpath + config.getProperty("avrgcc");
			avrinclude = avrpath + config.getProperty("avrinclude");
			avrsize = avrpath + config.getProperty("avrsize");

			new File(makefilepath).mkdir();

			makefile = new File(makefilepath + "\\Makefile");
			makeformatter = new Formatter(makefile);
			makeformatter.format("%s", "# Automatically-generated file. Do not edit!");
			makeformatter.format("%s", "\nDEVICE :=" + device + " \nAVRPATH :=" + avrpath + "\nPRJ_NAME :="
					+ projectname
					+ "\nSHELL := cmd.exe\nRM := rm -rf\nUSER_OBJS :=\nLIBS := \nPROJ := \nO_SRCS := \nC_SRCS := \nS_SRCS := \nS_UPPER_SRCS := \nOBJ_SRCS := \nASM_SRCS := \nPREPROCESSING_SRCS := \nOBJS := \nOBJS_AS_ARGS := \nC_DEPS := \nC_DEPS_AS_ARGS := \nEXECUTABLES := \nOUTPUT_FILE_PATH :=\nOUTPUT_FILE_PATH_AS_ARGS :=\nAVR_APP_PATH :=$$$AVR_APP_PATH$$$\nQUOTE :=\"\nADDITIONAL_DEPENDENCIES :=\nC_SRCS :=\nLIB_DEP :=\nLINKER_SCRIPT_DEP :=");
			makeformatter.format("%s",
					"\n\n# Every subdirectory with source files must be described here\n\nSUBDIRS :=  ");

			for (File file : new File(makefilepath).listFiles()) {
				if (file.isDirectory()) {
					makeformatter.format("%s", " \\ \n../" + file.getName());
				}					
			}
			
			makeformatter.format("%s",
					"\n\nAdd inputs and outputs from these tool invocations to the build variables C_SRCS += ");

			for (File file : new File(directory).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n../" + file.getName());
				}
			}
			for (File file : new File(targetfilepath).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n../../../" + file.getName());
				}			
			}

			makeformatter.format("%s", "\n\nOBJS += ");

			for (File file : new File(directory).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".o"));
				}
			}
			for (File file : new File(targetfilepath).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".o"));
				}			
			}
			
			makeformatter.format("%s", "\n\nOBJS_AS_ARGS += ");

			for (File file : new File(directory).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".o"));
				}		
			}
			for (File file : new File(targetfilepath).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".o"));
				}			
			}

			makeformatter.format("%s", "\n\nC_DEPS += ");

			for (File file : new File(directory).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".d"));
				}
			}
			for (File file : new File(targetfilepath).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".d"));
				}			
			}
			makeformatter.format("%s", "\n\nC_DEPS_AS_ARGS +=  ");

			for (File file : new File(directory).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".d"));
				}
			}
			for (File file : new File(targetfilepath).listFiles()) {
				if (file.getName().endsWith(".c")) {
					makeformatter.format("%s", " \\\n" + file.getName().replace(".c", ".d"));
				}			
			}

			makeformatter.format("%s", "\n\nOUTPUT_FILE_PATH +=" + projectname + ".elf");
			makeformatter.format("%s", "\n\nOUTPUT_FILE_PATH_AS_ARGS +=" + projectname + ".elf");
			makeformatter.format("%s", "\n./%.o: .././%.c\n\t@echo Building file: $<" + "\n\t$(QUOTE)" + avrgcc
					+ "$(QUOTE)  -x c -funsigned-char -funsigned-bitfields -DDEBUG -DDEBUG  -I$(QUOTE)" + avrinclude
					+ "$(QUOTE)  -O1 -ffunction-sections -fdata-sections -fpack-struct -fshort-enums -mrelax -g2 -Wall -mmcu=$(DEVICE)$ -c -std=gnu99 -MD -MP -MF $(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.o)$(QUOTE)   -o $(QUOTE)$@$(QUOTE) $(QUOTE)$<$(QUOTE) \n\t@echo Finished building: $<");
			
			makeformatter.format("%s", "\n./%.o: ../../.././%.c\n\t@echo Building file: $<" + "\n\t$(QUOTE)" + avrgcc
					+ "$(QUOTE)  -x c -funsigned-char -funsigned-bitfields -DDEBUG -DDEBUG  -I$(QUOTE)" + avrinclude
					+ "$(QUOTE)  -O1 -ffunction-sections -fdata-sections -fpack-struct -fshort-enums -mrelax -g2 -Wall -mmcu=$(DEVICE)$ -c -std=gnu99 -MD -MP -MF $(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.o)$(QUOTE)   -o $(QUOTE)$@$(QUOTE) $(QUOTE)$<$(QUOTE) \n\t@echo Finished building: $<");
			
			makeformatter.format("%s",
					"\n\nifneq ($(MAKECMDGOALS),clean) \nifneq ($(strip $(C_DEPS)),) \n-include $(C_DEPS) \nendif \nendif");

			makeformatter.format("%s", "\n\n# Build Target .elf file");
			makeformatter.format("%s", "\nall: $(OUTPUT_FILE_PATH) $(ADDITIONAL_DEPENDENCIES)");
			makeformatter.format("%s",
					"\n$(OUTPUT_FILE_PATH): $(OBJS) $(USER_OBJS) $(C_SRCS) $(LIB_DEP) $(LINKER_SCRIPT_DEP)\n\t@echo Building target: $@\n\t$(QUOTE)"
							+ avrgcc
							+ "$(QUOTE) -o$(OUTPUT_FILE_PATH_AS_ARGS) $(OBJS_AS_ARGS) $(USER_OBJS) $(LIBS) -Wl,-Map=$(QUOTE)$(PRJ_NAME).map$(QUOTE) -Wl,--start-group -Wl,-lm  -Wl,--end-group -Wl,--gc-sections -mrelax -mmcu=$(DEVICE)$  \n\t$(QUOTE)"
							+ avrsize + "$(QUOTE) $(QUOTE)$(PRJ_NAME).elf$(QUOTE)");
			makeformatter.format("%s", "\n\n# Clean Files");
			makeformatter.format("%s",
					"\nclean:\n\t-$(RM) $(OBJS_AS_ARGS) $(EXECUTABLES)  \n\t-$(RM) $(C_DEPS_AS_ARGS)   \n\trm -rf $(QUOTE)$(PRJ_NAME).elf$(QUOTE) $(QUOTE)$(PRJ_NAME).a$(QUOTE) $(QUOTE)$(PRJ_NAME).hex$(QUOTE) $(QUOTE)$(PRJ_NAME).lss$(QUOTE) $(QUOTE)$(PRJ_NAME).eep$(QUOTE) $(QUOTE)$(PRJ_NAME).map$(QUOTE) $(QUOTE)$(PRJ_NAME).srec$(QUOTE) $(QUOTE)$(PRJ_NAME).usersignatures$(QUOTE)");

			makeformatter.close();

		}

	}
}