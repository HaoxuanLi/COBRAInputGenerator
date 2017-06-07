import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class InputsGenerator {
	private String targetfilepath;
	private String blockfile;
	private String targetfile;
	private String annotationfile;
	private File directory;
	private File randominputgenerator;
	private Formatter randomformatter;
	private File inputsh;
	private File inputsc;
	private Formatter inputshformatter;
	private Formatter inputscformatter;
	private Formatter inputslogformatter;
	private List<Declaration> declarationlist = new ArrayList<Declaration>();
	private List<Variable> rightvariablelist = new ArrayList<Variable>();
	private List<Variable> leftvariablelist = new ArrayList<Variable>();
	private String blocklocaldeclaration = "";
	private Set<Declaration> declarationlistfinal = new HashSet<Declaration>();
	private Set<Declaration> annotationlist = new HashSet<Declaration>();
	private List<Declaration> blocklocaldeclarationlist = new ArrayList<Declaration>();
	private List<Declaration> blocklocaldeclarationlistfinal = new ArrayList<Declaration>();
	private Set<String> inputdeclarationlist = new HashSet<String>();
	private String updateInputs = "";
	private String blockinputsdeclaration = "";
	private int startlinenumber = 0;
	private List<StructDeclaration> structdeclarationlist = new ArrayList<StructDeclaration>();
	private InputsGeneratorParser blockparser = new InputsGeneratorParser();
	private InputsGeneratorParser structparser = new InputsGeneratorParser();
	private InputsGeneratorParser fileparser = new InputsGeneratorParser();
	private String structvariablename;
	private String structvariablename_pre;
	private String variablename_pre = "";
	private String arraysize_pre = "";
	private final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int alphabet_cnt = 0;
	private String struct_update_initialization_pre = "";
	private Properties config;
	private String definedconstant = "";
	private String ticflag;
	private String tocflag;
	private Boolean loginputsflag;
	//private Boolean returnflag;

	@SuppressWarnings("resource")
	public InputsGenerator(String targetfilepath, String blockfile, String targetfile, String annotationfile,
			long length, boolean loginputsflag) throws Exception {

		this.config = new WCETAnalysis().getConfig("config.properties");
		this.ticflag = this.config.getProperty("ticflag");
		this.tocflag = this.config.getProperty("tocflag");
		this.targetfilepath = targetfilepath;
		this.blockfile = blockfile;
		this.targetfile = targetfile;
		this.annotationfile = annotationfile;
		this.loginputsflag = loginputsflag;
/*		
 * this.returnflag = false;
*/
		
		// The read the content of the basic block
		String blockcontent = new String(Files.readAllBytes(Paths.get(this.blockfile)));
		// parse the content of the basic block
		this.blockparser = new InputsGeneratorParser(blockcontent);
		// get the list of right(critical) variables of the basic block
		this.rightvariablelist = this.blockparser.getRightVariableList();
		// get the list of left (minor) variables of the basic block
		this.leftvariablelist = this.blockparser.getLeftVariableList();
		// get the list of local declarations in the basic block
		this.blocklocaldeclaration = this.blockparser.getBlockLocalDeclaration();

		InputsGeneratorParser blocklocaldeclarationparser = new InputsGeneratorParser(
				"void benchmarkNode(void)" + this.blocklocaldeclaration);
		this.blocklocaldeclarationlist = blocklocaldeclarationparser.getDeclarationList();

		for (File headerfile : new File(this.targetfilepath).listFiles()) {
			if (headerfile.isFile() && headerfile.getName().matches(".*\\.h$")) {
				// get the list of struts of the complete program
				this.structparser = new InputsGeneratorParser("void benchmarkNode(void)"
						+ new String(Files.readAllBytes(Paths.get(headerfile.getAbsolutePath()))));
				List<StructDeclaration> structdelcarations = this.structparser.getStructDeclarationList();
				
				for (StructDeclaration structdeclaration : structdelcarations) {
					structdeclaration.setTargetFile(headerfile.getAbsolutePath());
				}
				
				this.structdeclarationlist.addAll(structdelcarations);
				// get the defined constant for inputs generation
				BufferedReader headerfilereader = new BufferedReader(new FileReader(headerfile));
				String headerfilereadline = "";
				while ((headerfilereadline = headerfilereader.readLine()) != null) {
					if (headerfilereadline.matches("^#define\\s+\\w*\\s+\\d*")) {
						String headerfilereadline_split[] = headerfilereadline.split("\\s+");
						String definedconstant_temp = headerfilereadline_split[1] + "=" + headerfilereadline_split[2]
								+ "\n";
						this.definedconstant = this.definedconstant.replaceAll(definedconstant_temp, "")
								+ definedconstant_temp;
					}
				}
			}
		}

		// read the content of the complete program
		String filecontent = new String(Files.readAllBytes(Paths.get(this.targetfile)));
		// parse the content of the program
		this.fileparser = new InputsGeneratorParser(filecontent);
		// get the list of declarations of the program
		this.declarationlist = this.fileparser.getDeclarationList();
		
		// get the size if declaration is an array
		declarationArrySizeDetection(this.declarationlist, this.targetfile);
		
		
		// interpret the annotation get the list of the annotations
		annotationListConstruction(this.annotationlist, this.annotationfile);

		// remove the offset of the line number and get the forward declaration
		// of the block inputs
		Matcher m = Pattern.compile(".*r([0-9]+).*").matcher(blockfile);
		if (m.matches()) {
			this.startlinenumber = Integer.parseInt(m.group(1));
		}
		
		this.directory = new File(this.targetfilepath + "\\Inputs\\blockR" + this.startlinenumber);

		/*
		// return block
		if (new File(this.targetfilepath + "\\Inputs\\blockR" + this.startlinenumber + "ReturnBlock").exists()) {
			this.directory = new File(this.targetfilepath + "\\Inputs\\blockR" + this.startlinenumber + "ReturnBlock");
		} else {
			this.directory = new File(this.targetfilepath + "\\Inputs\\blockR" + this.startlinenumber);
		}
		 */
		
		System.out.println("\nStart generating input files for blockR" + this.startlinenumber + "...");

		// remove the offset of the line number for right variables
		removeLineNumberOffset(this.rightvariablelist, this.startlinenumber, 23);

		// get the forward declaration of the right variables for the block
		// function
		forwardDeclarationConstruct(this.rightvariablelist, this.declarationlist, this.inputdeclarationlist,
				this.targetfile, this.startlinenumber);

		// remove the offset of the line number for left variables
		removeLineNumberOffset(this.leftvariablelist, this.startlinenumber, 23);

		// get the forward declaration of the left variables
		forwardDeclarationConstruct(this.leftvariablelist, this.declarationlist, this.inputdeclarationlist,
				this.targetfile, this.startlinenumber);

		// remove variables that are not crucial for the input generator (right
		// variables that are dependent on other right variables)
		refineList(this.rightvariablelist, this.leftvariablelist);

		// construct the final declaration list which consists all the critical
		// declarations for the block
		finalDeclarationListConstruction(this.rightvariablelist, this.declarationlist, this.declarationlistfinal,
				this.startlinenumber);

		// integrate the information in annotation file
		annotationIntegration(this.declarationlistfinal, this.annotationlist);

		// write the update function for the block inputs blockInputUpdate which
		// is used to update the inputs of the basic block
		updateFunctionConstruction(this.declarationlistfinal, this.inputdeclarationlist);

		randominputgenerator = new File("randominputgenerator.py");
		randomformatter = new Formatter(randominputgenerator);

		randomformatter.format("%s", "# Automatically-generated file. Do not edit! \n \n \n");
		randomformatter.format("%s",
				"import numpy as np \nimport re as replace\n\nnp.set_printoptions(threshold=np.inf)\n\n");
		randomformatter.format("%s", this.definedconstant + "\n\n");
		// writing the python file for input generation
		System.out.println("Generating inputs...");

		for (Declaration declaration : this.declarationlistfinal) {
			inputGeneration(declaration, length, this.targetfile);
		}

		
		System.out.println("Generating inputs...");

		// randomformatter.format("%s", "print(i)");
		randomformatter.close();

		// generate inputs.h
		System.out.println("Generating headerfile...");
		headerFileGeneration();

		// generate inputs.c
		System.out.println("Generating sourcefile...");
		sourceFileGeneration();
		
		/*
		// identify return block
		if (returnflag && !this.directory.getName().contains("ReturnBlock")) {
			File newdirectory = new File(this.directory.getAbsolutePath() + "ReturnBlock");
			this.directory.renameTo(newdirectory);
		}
		*/
		
		// Done
		System.out.println("\nInput file generated for blockR" + startlinenumber + "!");
	}

	private void updateFunctionConstruction(Set<Declaration> declarationlistfinal, Set<String> inputdeclarationlist) {

		for (Declaration declaration : declarationlistfinal) {
			String inputvariableorg = declaration.getVariable();
			String inputvariableorg_array = declaration.getVariable().replaceAll("\\[.*", "") + "_block";
			String pointer = inputvariableorg.matches("^\\*.*") ? "&" : "";
			inputvariableorg = inputvariableorg.replaceAll("^\\*", "");

			for (String input : inputdeclarationlist) {
				input = input.replaceAll("^\\*", "");
				inputvariableorg = inputvariableorg.replaceAll("\\b" + input + "\\b", input + "_block");
			}
			if (!declaration.getVariableType().contains("struct") && !declaration.getVariableType().contains("union")) {

				declaration.setVariable(declaration.getVariable().replaceAll("\\[", "_").replaceAll("\\]", "_"));

				if (!declaration.getVariableType().contains("const")) {
					if (!declaration.getIsArray()) {
						// write the update for (non-array) critical variables
						String update = inputvariableorg + " = " + pointer
								+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
										.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i];";

						updateInputsUpdate(update);
					} else {
						// write the update for critical arrays
						this.alphabet_cnt = 0;

						String arraysize[] = declaration.getArraySize().split(",");

						String update = "", update_declaration = "", update_initialization = "",
								update_initialization_left = inputvariableorg_array.replaceAll("^\\*", ""),
								update_initialization_right = declaration.getVariable().replaceAll("\\+", "plus")
										.replaceAll("\\" + "-", "minus").replaceAll("^\\*", "").replaceAll("\\*", "mul")
										.replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
										+ "_inputs[blockInputUpdate_i]",
								update_declaration_loop = "", update_declaration_loopend = "";

						for (int i = 0; i < arraysize.length; i++) {
							String counter = inputvariableorg_array + "_" + this.alphabet.charAt(this.alphabet_cnt);

							update_declaration = update_declaration + "\tint " + counter + ";\n";
							update_declaration_loop = update_declaration_loop + "\tfor(" + counter + "=0;" + counter
									+ "<" + arraysize[i] + ";" + counter + "++){\n";
							update_initialization_left = update_initialization_left + "[" + counter + "]";
							update_initialization_right = update_initialization_right + "[" + counter + "]";
							update_declaration_loopend = update_declaration_loopend + "\t}\n";
							this.alphabet_cnt++;
						}

						update_initialization = "\t\t" + update_initialization_left + "=" + pointer
								+ update_initialization_right + ";\n";
						update = update_declaration + update_declaration_loop + update_initialization
								+ update_declaration_loopend;

						updateInputsUpdate(update);

					}
				}

			} else {

				String variable_temp = "";
				String[] split = declaration.getVariable().replaceAll("\\]", "").split("\\[|\\]");
				variable_temp = split[0] + "_block";

				for (int i = 1; i < split.length; i++) {

					if (split[i].matches("[0-9]*"))
						variable_temp = variable_temp + "[" + split[i] + "]";
					else
						variable_temp = variable_temp + "[" + split[i] + "_block]";

				}
				declaration.setVariable(variable_temp);
			}
		}
	}

	private void annotationListConstruction(Set<Declaration> annotationlist, String annotationfile)
			throws NumberFormatException, IOException {
		@SuppressWarnings("resource")
		BufferedReader annotationreader = new BufferedReader(new FileReader(new File(annotationfile)));
		String annotationline = "";
		String[] annotationsplit;
		while ((annotationline = annotationreader.readLine()) != null) {
			Declaration annotation = new Declaration();
			annotationsplit = annotationline.split("\\s+");
			annotation.setVariableType(annotationsplit[0]);
			annotation.setVariable(annotationsplit[1]);
			annotation.setLineNumber(Integer.parseInt(annotationsplit[2]));
			annotation.setVariableMin(annotationsplit[3]);
			annotation.setVariableMax(annotationsplit[4]);
			annotationlist.add(annotation);
		}
	}

	private void annotationIntegration(Set<Declaration> declarationlist, Set<Declaration> annotationlist) {
		for (Declaration declaration : declarationlist) {
			for (Declaration annotation : annotationlist) {
				if (declaration.getVariable().equals(annotation.getVariable())
						&& declaration.getLineNumber().equals(annotation.getLineNumber())
						&& declaration.getVariableType().replace(" ", "").equals(annotation.getVariableType())) {
					declaration.setVariableMax(annotation.getVariableMax());
					declaration.setVariableMin(annotation.getVariableMin());
				}
			}
		}
	}

	private void finalDeclarationListConstruction(List<Variable> variablelist, List<Declaration> declarationlist,
			Set<Declaration> declarationlistfinal, int startlinenumber) {
		Set<Declaration> removabledeclarationlist = new HashSet<Declaration>();
		for (Variable variable : variablelist) {
			// add all the declarations before the end of the block to the final
			// declaration list

			for (Declaration declaration : declarationlist) {
				if (variable.getVariable().matches(".*\\[.*\\]")) {
					if (variable.getVariable().replaceAll("\\[.*\\]", "").replaceAll("^\\*", "")
							.equals(declaration.getVariable().replaceAll("^\\*", ""))
							&& (declaration.getLineNumber() <= variable.getLineNumber())) {
						// make sure there is no duplications in the final list
						boolean duplicationflag = false;
						for (Declaration dec : declarationlistfinal) {
							if (declaration.getVariableType().equals(dec.getVariableType())
									&& variable.getVariable().equals(dec.getVariable())
									&& (declaration.getLineNumber().equals(dec.getLineNumber())))
								duplicationflag = true;
						}
						if (!duplicationflag) {
							Declaration dec_temp = new Declaration();
							dec_temp.setVariableType(declaration.getVariableType());
							dec_temp.setVariable(variable.getVariable());
							dec_temp.setLineNumber(declaration.getLineNumber());
							dec_temp.setVariableMax(declaration.getVariableMax());
							dec_temp.setVariableMin(declaration.getVariableMin());
							dec_temp.setIsArray(declaration.getIsArray());
							dec_temp.setArraySize(declaration.getArraySize());

							declarationlistfinal.add(dec_temp);

						}
					}
				} else {
					if (variable.getVariable().replaceAll("^\\*", "")
							.equals(declaration.getVariable().replaceAll("^\\*", ""))
							&& (declaration.getLineNumber() <= variable.getLineNumber())) {
						declaration.setIsArray(declaration.getIsArray());
						declaration.setArraySize(declaration.getArraySize());
						declarationlistfinal.add(declaration);
					}
				}
			}
		}

		for (Declaration declaration : declarationlistfinal) {
			// add the declarations within the block to the removable list
			if (declaration.getLineNumber() >= startlinenumber)
				removabledeclarationlist.add(declaration);
			for (Declaration declarationcompare : declarationlistfinal) {
				// add the previous declarations to the removable list only keep
				// the last one before the block
				if (declaration.getVariable().equals(declarationcompare.getVariable())
						&& (declarationcompare.getLineNumber() < declaration.getLineNumber()))
					removabledeclarationlist.add(declarationcompare);
			}
		}

		// remove all the removable declarations from the list, only keep the
		// necessary ones for the block
		declarationlistfinal.removeAll(removabledeclarationlist);
	}

	private void refineList(List<Variable> criticalvariablelist, List<Variable> minorvariablelist) {
		List<Variable> removeablevariablelist = new ArrayList<Variable>();
		for (Variable criticalvariable : criticalvariablelist) {
			for (Variable minorvariable : minorvariablelist) {
				if (criticalvariable.getVariable().equals(minorvariable.getVariable())
						&& (criticalvariable.getLineNumber() > minorvariable.getLineNumber()))
					removeablevariablelist.add(criticalvariable);
			}
		}
		criticalvariablelist.removeAll(removeablevariablelist);
	}
	
	private void declarationArrySizeDetection(List<Declaration> declarationlist,  String targetfile) throws IOException {
		for(Declaration declaration: declarationlist){
			if(declaration.getIsArray()){
				Integer linenumber = declaration.getLineNumber();
				if (linenumber != 0) {
					Stream<String> lines = Files.lines(Paths.get(targetfile));
					String declarationline = lines.skip(linenumber - 1).findFirst().get();
					String declarationline_split[] = declarationline.split("//");
					declarationline = declarationline_split[0];
					String declarationname = declaration.getVariable().replaceAll("^\\*+.*", "");
					
					declarationline =  declaration.getVariableType() + " "
							+ declarationname + declarationline.replaceAll("\\s", "").replaceAll(".*" + declarationname, "").replaceAll("\\][^\\[]*", "\\]")
							+ ";";
					
					String arraysize = declarationline.replaceAll("\\s", "")
							.replaceAll(".*" + declarationname,"")
							.replaceAll("\\],.*", "").replaceAll("(^[^\\[]*\\[)|\\[|\\]|;", " ")
							.replaceAll("^\\s*|\\s*$", "").replaceAll("\\s+", ",");
					
					declaration.setArraySize(arraysize);
				}
			}
			
		}
		
	}

	private void forwardDeclarationConstruct(List<Variable> variablelist, List<Declaration> declarationlist,
			Set<String> inputdeclarationlist, String targetfile, int startlinenumber) throws IOException {
		for (Variable variable : variablelist) {
			String variabletype = "";
			String pointer = "";
			String variablename = variable.getVariable();
			Integer linenumber = 0;
			Boolean isarray = false;
			String arraysize = "";
			for (Declaration declaration : declarationlist) {
				// take the last declaration before the statement
				if (declaration.getLineNumber() <= variable.getLineNumber()
						&& variable.getVariable().replaceAll("(\\[.*\\])+", "").replaceAll("^\\*", "").equals(
								declaration.getVariable().replaceAll("(\\[.*\\])+", "").replaceAll("^\\*", ""))) {
					variabletype = declaration.getVariableType();
					isarray = declaration.getIsArray();
					arraysize = declaration.getArraySize();
					linenumber = declaration.getLineNumber();
					if (declaration.getVariable().matches("^\\*.*"))
						pointer = "*";
				}
			}

			// linenumber < startlinenumber to keep the last declaration before
			// the block
			if (linenumber != 0) {

				String input = variablename.replaceAll("(\\[.*\\])+", "");
				inputdeclarationlist.add(input);

				if (!variabletype.matches("(const)+.*") && linenumber < startlinenumber) {
					if (!isarray && !variablename.matches("^\\*.*")) {
						blockInputsDeclarationUpdate(
								variabletype + " " + pointer + variablename.replaceAll("^\\*", "") + ";");

					} else {
					
						Stream<String> lines = Files.lines(Paths.get(targetfile));
						String constantvariable = lines.skip(linenumber - 1).findFirst().get();
						String constantvariable_split[] = constantvariable.split("//");
						constantvariable = constantvariable_split[0];
						
						constantvariable = variabletype + " "
								+ variablename.replaceAll("^\\*+.*", "").replaceAll("\\[.*\\]", "")
								+ constantvariable.replaceAll("\\s", "")
										.replaceAll(".*"
												+ variablename.replaceAll("^\\*+.*", "").replaceAll("\\[.*\\]", ""), "")
										.replaceAll("\\][^\\[]*", "\\]")
								+ ";";

						if (!constantvariable.replaceAll("\\s*", "")
								.contains(variablename.replaceAll("(\\[.*\\])+", "") + "[")) {
							if (pointer.equals("*")) {
								constantvariable = variabletype + " " + pointer
										+ variablename.replaceAll("(\\[.*\\])+", "").replaceAll("^\\*", "") + ";";
							} else {
								constantvariable = variabletype + " " + variablename + ";";
							}

						} else {
							// set the array size in case the declaration of the
							// right variable is an array;

							String variablename_temp = variablename.replaceAll("\\[.*\\]", "") + constantvariable
									.replaceAll("\\s", "")
									.replaceAll(
											".*" + variablename.replaceAll("^\\*+.*", "").replaceAll("\\[.*\\]", ""),
											"")
									.replaceAll("\\],.*", "\\]").replaceAll(";", "");

							variable.setVariable(variablename_temp);
							System.out.println(variablename_temp);
						}

						if (constantvariable.contains("="))
							constantvariable = constantvariable.replaceAll("=.*", ";");
						if (constantvariable.contains(",")) {
							String[] variablesplit = constantvariable.replaceAll("(\\s*\\[\\s*)", "\\[")
									.replaceAll("(\\s*\\]\\s*)", "\\]").split("\\s|,|;");
							for (String variable_temp : variablesplit) {
								if (variable_temp.replaceAll("(\\[.*\\])+", "")
										.equals(variablename.replaceAll("(\\[.*\\])+", "")))
									constantvariable = variabletype + " " + variable_temp + ";";
							}
							

						}
						System.out.println(constantvariable);
						blockInputsDeclarationUpdate(constantvariable);
					}
				}
			}
		}
	}

	private void removeLineNumberOffset(List<Variable> variablelist, int startlinenumber, int offset) {
		for (Variable variable : variablelist) {
			variable.setLineNumber(variable.getLineNumber() + startlinenumber - offset);
		}
	}

	private void blockInputsDeclarationUpdate(String update) {
		if (!this.blockinputsdeclaration.contains(update))
			this.blockinputsdeclaration = this.blockinputsdeclaration + update + "\n";
	}

	private void updateInputsUpdate(String update) {
		if (!this.updateInputs.contains(update))
			this.updateInputs = this.updateInputs + update + "\n";
	}

	public void inputGeneration(StructDeclaration declaration, long length, String targetfile) throws IOException {
		// in case the struct contains array
		if (declaration.getVariable().contains("[")) {

			String arraysize_ext = declaration.getVariable().replaceAll("\\]\\[", ",").replaceAll("[A-Za-z_\\[\\]]",
					"");
			String variablename_new = declaration.getVariable().replaceAll("\\[.*", "");
			declaration.setIsArray(true);
			declaration.setArraySize(declaration.getArraySize() + "," + arraysize_ext);
			declaration.setVariable(variablename_new);
		}

		String variabletype = declaration.getVariableType();
		String variablename = declaration.getVariable();
		boolean isarray = declaration.getIsArray();
		String arraysize = declaration.getArraySize();
		String variabletypecase = "";
		String storageclassspecifier = "typedef|extern|static|_Thread_local|auto|register";
		String typequalifier = "restrict|volatile|_Atomic";
		boolean issamestructvariable = (this.structvariablename_pre == this.structvariablename);
		boolean issamearraysize = (this.arraysize_pre == arraysize);
		if (!issamestructvariable)
			this.alphabet_cnt = 0;
		this.structvariablename_pre = this.structvariablename;
		variabletypecase = variabletype.replaceAll(storageclassspecifier, "").replaceAll(typequalifier, "")
				.replaceAll("\\b\\s*", "");

		// adjust the update function for struct and union
		if (!declaration.getIsArray()) {
			if (!declaration.getVariableType().matches("(struct.*)|(union.*)")) {
				String update = this.structvariablename + "." + declaration.getVariable() + " = "
						+ declaration.getStructName() + "_"
						+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
								.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
								.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
						+ "_inputs[blockInputUpdate_i];";
				updateInputsUpdate(update);

			} else {
				String update = this.structvariablename + "_block" + "." + declaration.getVariable() + " = "
						+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
								.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
								.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
						+ "_block";
				updateInputsUpdate(update);
			}

		} else {

			String arraysize1[] = declaration.getArraySize().split(",");
			String update = "", update_declaration = "", update_initialization = "",
					update_initialization_left = this.structvariablename,
					update_initialization_right = declaration.getStructName() + "_"
							+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
									.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
									.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
							+ "_inputs[blockInputUpdate_i]",
					update_declaration_loop = "", update_declaration_loopend = "";

			if (!issamestructvariable || !issamearraysize) {
				for (int i = 0; i < arraysize1.length; i++) {

					String counter = this.structvariablename.replaceAll("\\*", "") + "_"
							+ this.alphabet.charAt(this.alphabet_cnt);
					update_declaration = update_declaration + "\tint " + counter + ";\n";
					update_declaration_loop = update_declaration_loop + "\tfor(" + counter + "=0;" + counter + "<"
							+ arraysize1[i] + ";" + counter + "++){\n";
					update_initialization_left = update_initialization_left + "[" + counter + "]";
					update_initialization_right = update_initialization_right + "[" + counter + "]";
					update_declaration_loopend = update_declaration_loopend + "\t}\n";
					this.alphabet_cnt++;
				}
				update_initialization = "\t\t" + update_initialization_left + "." + declaration.getVariable() + "="
						+ update_initialization_right + ";\n";
				update = update_declaration + update_declaration_loop + update_initialization
						+ update_declaration_loopend;

				updateInputsUpdate(update);

			} else {

				update_initialization = struct_update_initialization_pre.replace(
						"." + variablename_pre + "=" + declaration.getStructName() + "_"
								+ variablename_pre.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
										.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i]",
						"." + variablename + "=" + declaration.getStructName() + "_"
								+ variablename.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("^\\*", "").replaceAll("\\*", "mul").replaceAll("\\/", "div")
										.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i]");

				this.updateInputs = this.updateInputs.replace(this.struct_update_initialization_pre,
						this.struct_update_initialization_pre + update_initialization);

			}

			this.arraysize_pre = arraysize;
			this.variablename_pre = variablename;
			this.struct_update_initialization_pre = update_initialization;
		}

		// adjust the forward declaration for struct and union
		if (declaration.getVariableType().matches("(struct.*)|(union.*)"))
			blockInputsDeclarationUpdate(variabletype + " " + declaration.getVariable() + "_block" + ";");

		variablename = declaration.getStructName() + "_" + variablename;

		if (declaration.getVariableMax() == null) {

			switch (variabletypecase) {
			case "char":
				randomInputGeneration(variablename, variabletype, "randint", "0", "255", length, isarray, arraysize);
				break;
			case "unsigned char":
				randomInputGeneration(variablename, variabletype, "randint", "0", "255", length, isarray, arraysize);
				break;
			case "signed char":
				randomInputGeneration(variablename, variabletype, "randint", "-32768", "32767", length, isarray,
						arraysize);
				break;
			case "int":
				randomInputGeneration(variablename, variabletype, "randint", "-32768", "32767", length, isarray,
						arraysize);
				break;
			case "unsigned int":
				randomInputGeneration(variablename, variabletype, "randint", "0", "65535", length, isarray, arraysize);
				break;
			case "short":
				randomInputGeneration(variablename, variabletype, "randint", "-32768", "32767", length, isarray,
						arraysize);
				break;
			case "unsigned short":
				randomInputGeneration(variablename, variabletype, "randint", "0", "65535", length, isarray, arraysize);
				break;
			case "long":
				randomInputGeneration(variablename, variabletype, "randint", "-2147483648", "2147483647", length,
						isarray, arraysize);
				break;
			case "unsigned long":
				randomInputGeneration(variablename, variabletype, "randint", "0", "4294967295", length, isarray,
						arraysize);
				break;
			case "float":
				randomInputGeneration(variablename, variabletype, "uniform", "1.2e-38", "3.4e+38", length, isarray,
						arraysize);
				break;
			case "double":
				randomInputGeneration(variablename, variabletype, "uniform", "1.2e-38", "3.4e+38", length, isarray,
						arraysize);
				break;
			case "long double":
				randomInputGeneration(variablename, variabletype, "uniform", "3.4e-4932", "1.1e+4932", length, isarray,
						arraysize);
				break;

			default:

				if (declaration.getVariableType().matches("(const)+.*")) {
					Stream<String> lines = Files.lines(Paths.get(targetfile));
					String constantvariable = lines.skip(declaration.getLineNumber() - 1).findFirst().get();
					// change the name of the variable to avoid multiple
					// declarations
					constantvariable = constantvariable.replaceAll("\\b" + declaration.getVariable() + "\\b",
							declaration.getVariable() + "_block");
					this.randomformatter.format("%s", "print(\'" + constantvariable + "') \n");
				}

				if (declaration.getVariableType().matches("(struct)+.*")
						| declaration.getVariableType().matches("(union)+.*")) {
					this.structvariablename = declaration.getVariable();
					String[] s_temp = declaration.getVariableType().replaceFirst("struct", "struct ")
							.replaceFirst("union", "union ").split(" ");
					String structname = s_temp[s_temp.length - 1];

					for (StructDeclaration structdeclaration : this.structdeclarationlist) {
						structdeclaration.setArraySize(declaration.getArraySize());
						structdeclaration.setIsArray(declaration.getIsArray());
						if (structdeclaration.getStructName().equals(structname)) {
							inputGeneration(structdeclaration, length, structdeclaration.getTargetFile());
						}
					}
				}
				break;
			}
		} else {
			switch (variabletypecase) {
			case "float":
				randomInputGeneration(variablename, variabletype, "uniform", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			case "double":
				randomInputGeneration(variablename, variabletype, "uniform", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			case "long double":
				randomInputGeneration(variablename, variabletype, "uniform", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			default:
				randomInputGeneration(variablename, variabletype, "randint", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			}
		}

	}

	public void inputGeneration(Declaration declaration, long length, String targetfile) throws IOException {
		// in case the struct contains array
		if (declaration.getVariable().contains("[")) {
			String variablename_new = declaration.getVariable().replaceAll("\\[.*", "");
			declaration.setIsArray(true);
			declaration.setVariable(variablename_new);
		}

		String variabletype = declaration.getVariableType();
		String variablename = declaration.getVariable();
		boolean isarray = declaration.getIsArray();
		String arraysize = declaration.getArraySize();
		String variabletypecase = "";
		String storageclassspecifier = "typedef|extern|static|_Thread_local|auto|register";
		String typequalifier = "restrict|volatile|_Atomic";

		variabletypecase = variabletype.replaceAll(storageclassspecifier, "").replaceAll(typequalifier, "")
				.replaceAll("^\\s*", "");
		if (declaration.getVariableMax() == null) {

			switch (variabletypecase) {

			case "char":
				randomInputGeneration(variablename, variabletype, "randint", "0", "255", length, isarray, arraysize);
				break;
			case "unsigned char":
				randomInputGeneration(variablename, variabletype, "randint", "0", "255", length, isarray, arraysize);
				break;
			case "signed char":
				randomInputGeneration(variablename, variabletype, "randint", "-32768", "32767", length, isarray,
						arraysize);
				break;
			case "int":
				randomInputGeneration(variablename, variabletype, "randint", "-32768", "32767", length, isarray,
						arraysize);
				break;
			case "unsigned int":
				randomInputGeneration(variablename, variabletype, "randint", "0", "65535", length, isarray, arraysize);
				break;
			case "short":
				randomInputGeneration(variablename, variabletype, "randint", "-32768", "32767", length, isarray,
						arraysize);
				break;
			case "unsigned short":
				randomInputGeneration(variablename, variabletype, "randint", "0", "65535", length, isarray, arraysize);
				break;
			case "long":
				randomInputGeneration(variablename, variabletype, "randint", "-2147483648", "2147483647", length,
						isarray, arraysize);
				break;
			case "unsigned long":
				randomInputGeneration(variablename, variabletype, "randint", "0", "4294967295", length, isarray,
						arraysize);
				break;
			case "float":
				randomInputGeneration(variablename, variabletype, "uniform", "1.2e-38", "3.4e+38", length, isarray,
						arraysize);
				break;
			case "double":
				randomInputGeneration(variablename, variabletype, "uniform", "1.2e-38", "3.4e+38", length, isarray,
						arraysize);
				break;
			case "long double":
				randomInputGeneration(variablename, variabletype, "uniform", "3.4e-4932", "1.1e+4932", length, isarray,
						arraysize);
				break;

			default:
				if (declaration.getVariableType().matches("(const)+.*")) {
					Stream<String> lines = Files.lines(Paths.get(targetfile));
					String constantvariable = lines.skip(declaration.getLineNumber() - 1).findFirst().get();
					// change the name of the variable to avoid multiple
					// declarations
					constantvariable = constantvariable.replaceAll("\\b" + declaration.getVariable() + "\\b",
							declaration.getVariable() + "_block");
					this.randomformatter.format("%s", "print(\'" + constantvariable + "') \n");

				}

				if (declaration.getVariableType().matches("(struct)+.*")
						| declaration.getVariableType().matches("(union)+.*")) {
					this.structvariablename = declaration.getVariable();
					for (StructDeclaration structdeclaration : this.structdeclarationlist) {
						String[] s_temp = declaration.getVariableType().split(" ");
						String structname = s_temp[s_temp.length - 1];
						structdeclaration.setArraySize(declaration.getArraySize());
						structdeclaration.setIsArray(declaration.getIsArray());
						if (structdeclaration.getStructName().replaceAll("\\s.*", "").equals(structname)) {
							inputGeneration(structdeclaration, length, structdeclaration.getTargetFile());
						}

					}
				}
				break;
			}
		} else {
			switch (variabletypecase) {
			case "float":
				randomInputGeneration(variablename, variabletype, "uniform", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			case "double":
				randomInputGeneration(variablename, variabletype, "uniform", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			case "long double":
				randomInputGeneration(variablename, variabletype, "uniform", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			default:
				randomInputGeneration(variablename, variabletype, "randint", declaration.getVariableMin(),
						declaration.getVariableMax(), length, isarray, arraysize);
				break;
			}

		}
	}

	public void randomInputGeneration(String variablename, String variabletype, String inputtype, String min,
			String max, long length, boolean isarray, String arraysize) {
		variabletype = variabletype.replaceAll("\\b(register|volatile|extern)\\s+\\b", "");
		String dtype = "dtype=np.int64";
		if (inputtype.equals("uniform"))
			dtype = "";
		variablename = variablename.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus").replaceAll("^\\*", "")
				.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra").replaceAll(",", "com");

		if (!isarray) {
			this.randomformatter.format("%s",
					"temp = str(" + "np.random." + inputtype + "(low=" + min + ",high=" + max + "+1" + ",size=(1,"
							+ length + ")" + "," + dtype + ")).replace(\"[[\",\"{\").replace(\"]]\",\"};\") \n");
			this.randomformatter.format("%s",
					"temp = replace.sub(\"\\s+|\\n\",\",\", temp).replace(\"{,\",\"{\").replace(\",}\",\"}\").replace(\",,\",\",\") \n");
			this.randomformatter.format("%s", "print(\'" + variabletype + " " + variablename + "_inputs[" + length
					+ "] = \' + temp + \';\' " + ") \n");
		} else {
			this.randomformatter.format("%s", "temp = str(" + "np.random." + inputtype + "(low=" + min + ",high=" + max
					+ "+1" + ",size=(" + length + "," + arraysize + ")" + "," + dtype + ")) \n");
			this.randomformatter.format("%s", "temp = replace.sub(\"\\s|\\n\",\",\",temp)\n");
			this.randomformatter.format("%s", "temp = replace.sub(\"\\,+\",\",\",temp)\n");
			this.randomformatter.format("%s",
					"temp = temp.replace(\"[,\",\"[\").replace(\",]\",\"]\").replace(\"[\",\"{\").replace(\"]\",\"}\")\n");
			this.randomformatter.format("%s", "print(\'" + variabletype + " " + variablename + "_inputs[" + length + "]"
					+ "[" + arraysize.replace(",", "][") + "]" + "= \' + temp + \';\' " + ") \n");
		}
	}

	public void headerFileGeneration() throws FileNotFoundException {

		if (!this.directory.exists())
			this.directory.mkdir();

		this.inputsh = new File(this.directory.getAbsolutePath() + "\\inputs.h");
		this.inputshformatter = new Formatter(this.inputsh);

		this.inputshformatter.format("%s", "#ifndef __INPUTS" + " \n");
		this.inputshformatter.format("%s", "#define __INPUTS" + " \n\n");

		// include the main header file
		this.inputshformatter.format("%s", "// include the main header" + " \n");
		this.inputshformatter.format("%s", "#include \"main.h\" " + " \n\n");

		// forward declaration
		this.inputshformatter.format("%s", "// forward declarations" + " \n");
		this.inputshformatter.format("%s", "void blockInputUpdate(int);" + " \n");
		this.inputshformatter.format("%s", "void benchmarkBlock(void);" + " \n\n");

		this.inputshformatter.format("%s", "#endif" + " \n");

		this.inputshformatter.close();
	}

	public void sourceFileGeneration() throws IOException {

		if (!this.directory.exists())
			this.directory.mkdir();

		this.inputsc = new File(this.directory.getAbsolutePath() + "\\inputs.c");
		this.inputscformatter = new Formatter(this.inputsc);
		this.inputslogformatter = new Formatter(
				new BufferedWriter(new FileWriter(this.directory.getAbsolutePath() + "\\inputslog.txt", true)));

		// include header files
		this.inputscformatter.format("%s", "#include \"inputs.h\"\n");
		this.inputscformatter.format("%s", "#include \"" + this.targetfile.replace(".c", ".h") + "\"\n" + " \n");

		// Block inputs
		ProcessBuilder randombuilder = new ProcessBuilder("python", this.randominputgenerator.getAbsolutePath());
		// randombuilder.directory(randominputgenerator);
		// randombuilder.redirectOutput(Redirect.INHERIT);
		// randombuilder.redirectError(Redirect.INHERIT);
		Process process = randombuilder.start();

		scanStream(process, this.inputscformatter, this.loginputsflag);
		this.inputslogformatter.close();

		this.inputscformatter.format("%s", " \n \n \n");

		// forward declare block variables
		// change the variable names in the inputs files to avoid multiple
		// definitions

		for (String input : this.inputdeclarationlist) {
			// String pointer = input.matches("^\\*.*") ? "*" : "";
			input = input.replaceAll("^\\*+", "");
			this.blockinputsdeclaration = this.blockinputsdeclaration.replaceAll("\\b" + input + "\\b",
					input + "_block");
		}

		this.blockinputsdeclaration = this.blockinputsdeclaration.replaceAll("\\b(register|extern)\\s+\\b", "");

		this.inputscformatter.format("%s", "\n" + this.blockinputsdeclaration + " \n");

		// inputUpdate function
		this.inputscformatter.format("%s",
				"\nvoid blockInputUpdate(int blockInputUpdate_i) \n{\n\t" + this.updateInputs + "\n} \n" + " \n");

		// declare global variables for the block

		String[] localtoglobaldeclaration_split = this.blocklocaldeclaration.split("\n");
		String localtoglobaldeclaration_forward = "";

		for (String localtoglobaldeclaration : localtoglobaldeclaration_split) {
			localtoglobaldeclaration = localtoglobaldeclaration.replaceAll("\\b(register|volatile|extern)\\s+\\b", "");

			for (Declaration localdeclaration : this.blocklocaldeclarationlist) {

				if (localtoglobaldeclaration.replaceAll("\\s", "").contains(localdeclaration.getVariable() + "[")) {
					localdeclaration.setIsArray(true);

					localdeclaration.setArraySize(localtoglobaldeclaration.replaceAll("\\s", "")
							.replaceAll(".*" + localdeclaration.getVariable() + "\\[", "").replaceAll("=.*", "")
							.replaceAll("(\\],)|(\\];)|(\\]$)", "").replace("][", ","));

					if (localdeclaration.getArraySize().isEmpty())
						localdeclaration.setArraySize("sizeof(" + localdeclaration.getVariable()
								.replaceAll("\\b" + localdeclaration.getVariable() + "\\b",
										localdeclaration.getVariable() + "_g")
								.replaceAll("\\.\\b" + localdeclaration.getVariable() + "_g" + "\\b",
										"\\." + localdeclaration.getVariable())
								+ ")");

				} else {
					localtoglobaldeclaration = localtoglobaldeclaration.replaceAll("\\b=[^,]*,", ",")
							.replaceAll("\\b=[^;]*;", ";");
				}

				localtoglobaldeclaration_forward = localtoglobaldeclaration_forward
						.replace(localtoglobaldeclaration + "\n", "") + localtoglobaldeclaration + "\n";
			}
		}

		// adjust the global declaration name from the original to _g

		for (Declaration localdeclaration : this.blocklocaldeclarationlist) {

			localtoglobaldeclaration_forward = localtoglobaldeclaration_forward
					.replaceAll("\\b" + localdeclaration.getVariable().replace("[", "\\[").replace("]", "\\]") + "\\b",
							localdeclaration.getVariable().replaceAll("^\\*+", "").replace("[", "\\[").replace("]",
									"\\]") + "_g")
					.replaceAll(
							"\\.\\b" + localdeclaration.getVariable().replace("[", "\\[").replace("]", "\\]") + "_g"
									+ "\\b",
							"\\." + localdeclaration.getVariable().replace("[", "\\[").replace("]", "\\]"));
		}
		this.inputscformatter.format("%s", localtoglobaldeclaration_forward + "\n");

		BufferedReader reader = new BufferedReader(new FileReader(new File(this.blockfile)));

		String localtoglobalassign = "";
		for (Variable leftvariable : this.leftvariablelist) {

			for (Declaration localdeclaration : this.blocklocaldeclarationlist) {
				if (leftvariable.getVariable().replaceAll("\\[.*\\]", "")
						.equals(localdeclaration.getVariable().replaceAll("_block$", ""))) {
					Declaration declaration_temp = new Declaration();
					declaration_temp.setVariable(leftvariable.getVariable());
					declaration_temp.setIsArray(localdeclaration.getIsArray());
					declaration_temp.setArraySize(localdeclaration.getArraySize());
					this.blocklocaldeclarationlistfinal.add(declaration_temp);
				}
			}
		}

		for (Variable rightvariable : this.rightvariablelist) {

			for (Declaration localdeclaration : this.blocklocaldeclarationlist) {
				if (rightvariable.getVariable().replaceAll("\\[.*\\]", "")
						.equals(localdeclaration.getVariable().replaceAll("_block$", ""))) {
					Declaration declaration_temp = new Declaration();
					declaration_temp.setVariable(rightvariable.getVariable());
					declaration_temp.setIsArray(localdeclaration.getIsArray());
					declaration_temp.setArraySize(localdeclaration.getArraySize());
					this.blocklocaldeclarationlistfinal.add(declaration_temp);
				}
			}
		}

		for (Declaration localdeclaration : this.blocklocaldeclarationlistfinal) {

			if (localdeclaration.getIsArray() && !localdeclaration.getVariable()
					.matches(localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "(\\[[0-9]*\\])+")) {
				int alphabet_cnt_local = 0;
				String arraysize[] = localdeclaration.getArraySize().split(",");

				String update = "", update_declaration = "", update_initialization = "",
						update_initialization_left = localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "_g",
						update_initialization_right = localdeclaration.getVariable().replaceAll("\\[.*\\]", "")
								.replaceAll("\\b" + localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "\\b",
										localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "_block")
								.replaceAll(
										"\\.\\b" + localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "_block"
												+ "\\b",
										"\\." + localdeclaration.getVariable().replaceAll("\\[.*\\]", "")),
						update_declaration_loop = "", update_declaration_loopend = "";

				for (int i = 0; i < arraysize.length; i++) {
					String counter = localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "_cnt_"
							+ this.alphabet.charAt(alphabet_cnt_local);

					update_declaration = update_declaration + "\tint " + counter + ";\n";
					update_declaration_loop = update_declaration_loop + "\tfor(" + counter + "=0;" + counter + "<"
							+ arraysize[i] + ";" + counter + "++){\n";
					update_initialization_left = update_initialization_left + "[" + counter + "]";
					update_initialization_right = update_initialization_right + "[" + counter + "]";
					update_declaration_loopend = update_declaration_loopend + "\t}\n";
					alphabet_cnt_local++;
				}

				update_initialization = "\t\t" + update_initialization_left + "=" + update_initialization_right + ";\n";
				update = update_declaration + update_declaration_loop + update_initialization
						+ update_declaration_loopend;

				localtoglobalassign = localtoglobalassign.replace("\n" + update + "\t", "") + "\n" + update + "\t";

			} else {
				String update = localdeclaration.getVariable().replace(
						localdeclaration.getVariable().replaceAll("\\[.*\\]", ""),
						localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "_g")
						+ " = "
						+ localdeclaration.getVariable().replace(
								localdeclaration.getVariable().replaceAll("\\[.*\\]", ""),
								localdeclaration.getVariable().replaceAll("\\[.*\\]", "") + "_block" + ";");

				localtoglobalassign = localtoglobalassign.replace("\n" + update + "\t", "") + "\n" + update + "\t";
			}
		}

		String line = "";
		Boolean conditionstatementflag = false;
		int LN = 1;
		int skipLines = 20;

		while ((line = reader.readLine()) != null) {
			if (LN < skipLines + 3) {
				LN++;
				continue;
			} else if (LN == (skipLines + 3)) {
				this.inputscformatter.format("%s", "void benchmarkBlock(void) \n {\n");
				this.inputscformatter.format("%s", this.ticflag + " \n");
				LN++;
			}
			if (LN < (skipLines + 3)) {
				LN++;
			} else if (LN == (skipLines + 3)) {
				LN++;
			}
			/*
			 * // identify return block if (line.replaceAll(" ",
			 * "").matches("^return(;|\\().*")) { this.returnflag = true; }
			 */
			if (!line.contains(" main(")) {

				for (String input : this.inputdeclarationlist) {
					input = input.replaceAll("\\*", "");
					line = line.replaceAll("\\b" + input + "\\b", input + "_block")
							.replaceAll("\\.\\b" + input + "_block" + "\\b", "\\." + input);
				}
				if (line.matches("}$")) {
					if (conditionstatementflag) {
						this.inputscformatter.format("%s", "{} \n");
					}
					this.inputscformatter.format("%s", this.tocflag + " \n");
					line = localtoglobalassign + "\n}";

				} else {
					conditionstatementflag = false;
				}
				this.inputscformatter.format("%s", line + " \n");

				if (line.replaceAll(" ", "").matches("^(for|if|while)\\(.*")) {
					conditionstatementflag = true;
				}

			} else {
				while (reader.readLine() != null) {
					reader.readLine();
				}
			}

		}
		reader.close();
		this.inputscformatter.close();

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

	public void scanStream(Process process, Formatter formatter, Boolean log) throws IOException {
		Scanner outputmsg = new Scanner(process.getInputStream());
		Scanner errormsg = new Scanner(process.getErrorStream());
		if (log == true) {
			String msg = "";
			this.inputslogformatter.format("%s", "\n\n\n");
			while (outputmsg.hasNextLine()) {
				msg = outputmsg.nextLine();
				// write to the inputs file
				formatter.format("%s", msg + "\n");
				// log the inputs
				this.inputslogformatter.format("%s", msg + "\n");
			}
			outputmsg.close();
			while (errormsg.hasNextLine()) {
				System.out.println(errormsg.nextLine());
			}
			errormsg.close();
		} else {
			while (outputmsg.hasNextLine()) {
				// write to the inputs file
				formatter.format("%s", outputmsg.nextLine() + "\n");
			}
			outputmsg.close();
			while (errormsg.hasNextLine()) {
				System.out.println(errormsg.nextLine());
			}
			errormsg.close();
		}
	}
}