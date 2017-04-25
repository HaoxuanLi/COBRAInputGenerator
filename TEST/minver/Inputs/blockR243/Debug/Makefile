# Automatically-generated file. Do not edit!
DEVICE :=at90can128 
AVRPATH :=C:\Program Files (x86)\Atmel\Studio\7.0
PRJ_NAME :=blockR243
SHELL := cmd.exe
RM := rm -rf
USER_OBJS :=
LIBS := 
PROJ := 
O_SRCS := 
C_SRCS := 
S_SRCS := 
S_UPPER_SRCS := 
OBJ_SRCS := 
ASM_SRCS := 
PREPROCESSING_SRCS := 
OBJS := 
OBJS_AS_ARGS := 
C_DEPS := 
C_DEPS_AS_ARGS := 
EXECUTABLES := 
OUTPUT_FILE_PATH :=
OUTPUT_FILE_PATH_AS_ARGS :=
AVR_APP_PATH :=$$$AVR_APP_PATH$$$
QUOTE :="
ADDITIONAL_DEPENDENCIES :=
C_SRCS :=
LIB_DEP :=
LINKER_SCRIPT_DEP :=

# Every subdirectory with source files must be described here

SUBDIRS :=  

Add inputs and outputs from these tool invocations to the build variables C_SRCS +=  \
../inputs.c \
../main.c \
../../../minver.c

OBJS +=  \
inputs.o \
main.o \
minver.o

OBJS_AS_ARGS +=  \
inputs.o \
main.o \
minver.o

C_DEPS +=  \
inputs.d \
main.d \
minver.d

C_DEPS_AS_ARGS +=   \
inputs.d \
main.d \
minver.d

OUTPUT_FILE_PATH +=blockR243.elf

OUTPUT_FILE_PATH_AS_ARGS +=blockR243.elf
./%.o: .././%.c
	@echo Building file: $<
	$(QUOTE)C:\Program Files (x86)\Atmel\Studio\7.0\toolchain\avr8\avr8-gnu-toolchain\bin\avr-gcc.exe$(QUOTE)  -x c -funsigned-char -funsigned-bitfields -DDEBUG -DDEBUG  -I$(QUOTE)C:\Program Files (x86)\Atmel\Studio\7.0\Packs\atmel\ATmega_DFP\1.0.106\include$(QUOTE)  -O1 -ffunction-sections -fdata-sections -fpack-struct -fshort-enums -mrelax -g2 -Wall -mmcu=$(DEVICE)$ -c -std=gnu99 -MD -MP -MF $(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.o)$(QUOTE)   -o $(QUOTE)$@$(QUOTE) $(QUOTE)$<$(QUOTE) 
	@echo Finished building: $<
./%.o: ../../.././%.c
	@echo Building file: $<
	$(QUOTE)C:\Program Files (x86)\Atmel\Studio\7.0\toolchain\avr8\avr8-gnu-toolchain\bin\avr-gcc.exe$(QUOTE)  -x c -funsigned-char -funsigned-bitfields -DDEBUG -DDEBUG  -I$(QUOTE)C:\Program Files (x86)\Atmel\Studio\7.0\Packs\atmel\ATmega_DFP\1.0.106\include$(QUOTE)  -O1 -ffunction-sections -fdata-sections -fpack-struct -fshort-enums -mrelax -g2 -Wall -mmcu=$(DEVICE)$ -c -std=gnu99 -MD -MP -MF $(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.d)$(QUOTE) -MT$(QUOTE)$(@:%.o=%.o)$(QUOTE)   -o $(QUOTE)$@$(QUOTE) $(QUOTE)$<$(QUOTE) 
	@echo Finished building: $<

ifneq ($(MAKECMDGOALS),clean) 
ifneq ($(strip $(C_DEPS)),) 
-include $(C_DEPS) 
endif 
endif

# Build Target .elf file
all: $(OUTPUT_FILE_PATH) $(ADDITIONAL_DEPENDENCIES)
$(OUTPUT_FILE_PATH): $(OBJS) $(USER_OBJS) $(C_SRCS) $(LIB_DEP) $(LINKER_SCRIPT_DEP)
	@echo Building target: $@
	$(QUOTE)C:\Program Files (x86)\Atmel\Studio\7.0\toolchain\avr8\avr8-gnu-toolchain\bin\avr-gcc.exe$(QUOTE) -o$(OUTPUT_FILE_PATH_AS_ARGS) $(OBJS_AS_ARGS) $(USER_OBJS) $(LIBS) -Wl,-Map=$(QUOTE)$(PRJ_NAME).map$(QUOTE) -Wl,--start-group -Wl,-lm  -Wl,--end-group -Wl,--gc-sections -mrelax -mmcu=$(DEVICE)$  
	$(QUOTE)C:\Program Files (x86)\Atmel\Studio\7.0\toolchain\avr8\avr8-gnu-toolchain\bin\avr-size.exe$(QUOTE) $(QUOTE)$(PRJ_NAME).elf$(QUOTE)

# Clean Files
clean:
	-$(RM) $(OBJS_AS_ARGS) $(EXECUTABLES)  
	-$(RM) $(C_DEPS_AS_ARGS)   
	rm -rf $(QUOTE)$(PRJ_NAME).elf$(QUOTE) $(QUOTE)$(PRJ_NAME).a$(QUOTE) $(QUOTE)$(PRJ_NAME).hex$(QUOTE) $(QUOTE)$(PRJ_NAME).lss$(QUOTE) $(QUOTE)$(PRJ_NAME).eep$(QUOTE) $(QUOTE)$(PRJ_NAME).map$(QUOTE) $(QUOTE)$(PRJ_NAME).srec$(QUOTE) $(QUOTE)$(PRJ_NAME).usersignatures$(QUOTE)