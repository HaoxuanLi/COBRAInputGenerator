import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	private String annonationfile;
	private File randominputgenerator;
	private Formatter randomformatter;
	private File inputsh;
	private File inputsc;
	private static Formatter inputshformatter;
	private static Formatter inputscformatter;
	private List<Declaration> declarationlist = new ArrayList<Declaration>();
	private List<Variable> rightvariablelist = new ArrayList<Variable>();
	private List<Variable> leftvariablelist = new ArrayList<Variable>();
	private List<Variable> removeablevariablelist = new ArrayList<Variable>();
	private Set<Declaration> declarationlistfinal = new HashSet<Declaration>();
	private Set<Declaration> removabledeclarationlist = new HashSet<Declaration>();
	private Set<Declaration> annotationlist = new HashSet<Declaration>();
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

	@SuppressWarnings("resource")
	public InputsGenerator(String directory, String blockfile, String targetfile, String annonationfile, long length)
			throws Exception {

		this.targetfilepath = directory;
		this.blockfile = blockfile;
		this.targetfile = targetfile;
		this.annonationfile = annonationfile;

		// The length of inputs
		String blockcontent = new String(Files.readAllBytes(Paths.get(blockfile)));

		blockparser = new InputsGeneratorParser(blockcontent);
		rightvariablelist = blockparser.getRightVariableList();
		leftvariablelist = blockparser.getLeftVariableList();

		for (File headerfile : new File(targetfilepath).listFiles()) {
			if (headerfile.isFile()) {
				structparser = new InputsGeneratorParser("void benchmarkNode(void)"
						+ new String(Files.readAllBytes(Paths.get(headerfile.getAbsolutePath()))));
				List<StructDeclaration> structdelcarations = structparser.getStructDeclarationList();
				for (StructDeclaration structdeclaration : structdelcarations) {
					structdeclaration.setTargetFile(headerfile.getAbsolutePath());

					// structdeclaration.setIsArray(isarray);
				}
				structdeclarationlist.addAll(structdelcarations);
			}

		}

		String filecontent = new String(Files.readAllBytes(Paths.get(targetfile)));

		fileparser = new InputsGeneratorParser(filecontent);

		declarationlist = fileparser.getDeclarationList();

		// get the annotation

		BufferedReader annonationreader = new BufferedReader(new FileReader(new File(annonationfile)));
		String annonationline = "";
		String[] annonationsplit;

		while ((annonationline = annonationreader.readLine()) != null) {

			Declaration annontation = new Declaration();

			annonationsplit = annonationline.split("\\s+");
			annontation.setVariableType(annonationsplit[0]);
			annontation.setVariable(annonationsplit[1]);
			annontation.setLineNumber(Integer.parseInt(annonationsplit[2]));
			annontation.setVariableMin(annonationsplit[3]);
			annontation.setVariableMax(annonationsplit[4]);

			annotationlist.add(annontation);

		}
		// remove the offset of the line number and get the forward declaration
		// of the block inputs
		Matcher m = Pattern.compile(".*r([0-9]+).*").matcher(blockfile);
		if (m.matches()) {
			startlinenumber = Integer.parseInt(m.group(1));
		}

		System.out.println("\nStart generating input files for blockR" + startlinenumber + "...");

		for (Variable rightvariable : rightvariablelist) {
			// remove the offset of the line number
			rightvariable.setLineNumber(rightvariable.getLineNumber() + startlinenumber - 23);
		}

		// get the forward declaration of the right variables
		for (Variable rightvariable : rightvariablelist) {
			String variabletype = "";
			String pointer = "";
			String variable = rightvariable.getVariable();
			Integer linenumber = 0;
			for (Declaration declaration : declarationlist) {
				// take the last declaration before the statement
				if (declaration.getLineNumber() <= rightvariable.getLineNumber()
						&& rightvariable.getVariable().replaceAll("(\\[.*\\])+", "").replaceAll("\\*", "")
								.equals(declaration.getVariable().replaceAll("\\*", ""))) {
					variabletype = declaration.getVariableType();
					// .replaceFirst("signed", "signed
					// ").replaceFirst("unsigned", "unsigned
					// ").replaceFirst("long", "long ").replaceFirst("struct",
					// "struct ").replaceFirst("union", "union
					// ").replaceAll("\\s+", " ");
					linenumber = declaration.getLineNumber();
					if (declaration.getVariable().contains("*"))
						pointer = "*";
				}
			}

			// linenumber < startlinenumber to keep the last declaration before
			// the block
			if (linenumber != 0) {
				String input = rightvariable.getVariable().replaceAll("(\\[.*\\])+", "");
				inputdeclarationlist.add(input);
				if (!variabletype.matches("(const)+.*") && linenumber < startlinenumber) {
					if (!variable.matches(".*(\\[.*\\])+") && !variable.matches("\\*.*")) {
						blockinputsdeclaration = variabletype + " " + variable + ";\n"
								+ blockinputsdeclaration.replace(variabletype + " " + variable + ";\n", "");
					} else {
						Stream<String> lines = Files.lines(Paths.get(targetfile));
						String constantvariable = lines.skip(linenumber - 1).findFirst().get();
						String constantvariable_split[] = constantvariable.split("//");
						constantvariable = constantvariable_split[0];
						if (!constantvariable.contains("[")) {
							constantvariable = variabletype + " " + variable + ";";
						} else {
							// set the array size in case the declaration of the
							// right variable is an array
							rightvariable.setIsArray(true);
							
							rightvariable.setArraySize(constantvariable.replaceAll("\\s", "").replaceAll("(^[^\\[]*\\[)|\\[|\\]|;", " ")
									.replaceAll("^\\s*|\\s*$", "").replaceAll("\\s+", ","));
							rightvariable.setVariable(rightvariable.getVariable().replaceAll("\\[.*\\]", "") + constantvariable.replaceAll("^[^\\[]*", "").replaceAll("\\s*", "").replaceAll(";", ""));
							
						}

						if (constantvariable.contains("="))
							constantvariable = constantvariable.replaceAll("=.*", ";");
						if (constantvariable.contains(",")) {
							String[] variablesplit;
							variablesplit = constantvariable.replaceAll("(\\s*\\[\\s*)", "\\[")
									.replaceAll("(\\s*\\]\\s*)", "\\]").split("\\s|,|;");
							for (String variable_temp : variablesplit) {
								if (variable_temp.replaceAll("(\\[.*\\])+", "")
										.equals(variable.replaceAll("(\\[.*\\])+", "")))
									constantvariable = variabletype + " " + variable_temp + ";";
							}
						}
						blockinputsdeclaration = constantvariable + "\n"
								+ blockinputsdeclaration.replace(constantvariable + "\n", "");
					}
				}
			}
		}

		for (Variable leftvariable : leftvariablelist) {
			leftvariable.setLineNumber(leftvariable.getLineNumber() + startlinenumber - 23);
		}

		// get the forward declaration of the left variables
		for (Variable leftvariable : leftvariablelist) {
			String variabletype = "";
			String pointer = "";
			String variable = leftvariable.getVariable();
			Integer linenumber = 0;
			for (Declaration declaration : declarationlist) {
				if (declaration.getLineNumber() <= leftvariable.getLineNumber()
						&& leftvariable.getVariable().replaceAll("(\\[.*\\])+", "").replaceAll("\\*", "")
								.equals(declaration.getVariable().replaceAll("\\*", ""))) {
					variabletype = declaration.getVariableType();
					linenumber = declaration.getLineNumber();
					if (declaration.getVariable().contains("*"))
						pointer = "*";
				}
			}
			if (linenumber != 0) {
				String input = leftvariable.getVariable().replaceAll("(\\[.*\\])+", "");
				inputdeclarationlist.add(input);
				if (!variabletype.matches("(const)+.*") && linenumber < startlinenumber) {
					if (!variable.matches(".*(\\[.*\\])+")) {
						blockinputsdeclaration = variabletype + " " + pointer + variable + ";\n"
								+ blockinputsdeclaration.replace(variabletype + " " + pointer + variable + ";\n", "");
					} else {
						Stream<String> lines = Files.lines(Paths.get(targetfile));
						String constantvariable = lines.skip(linenumber - 1).findFirst().get();
						if (constantvariable.contains("="))
							constantvariable = constantvariable.replaceAll("=.*", ";");
						if (constantvariable.contains(",")) {
							String[] variablesplit;
							variablesplit = constantvariable.replaceAll("(\\s*\\[\\s*)", "\\[")
									.replaceAll("(\\s*\\]\\s*)", "\\]").split("\\s|,|;");
							for (String variable_temp : variablesplit) {
								if (variable_temp.replaceAll("(\\[.*\\])+", "")
										.equals(variable.replaceAll("(\\[.*\\])+", "")))
									constantvariable = variabletype + " " + variable_temp + ";";
							}
						}
						blockinputsdeclaration = constantvariable + "\n"
								+ blockinputsdeclaration.replace(constantvariable + "\n", "");
					}
				}
			}
		}

		for (Variable rightvariable : rightvariablelist) {
			for (Variable leftvariable : leftvariablelist) {
				if (rightvariable.getVariable().equals(leftvariable.getVariable())
						&& (rightvariable.getLineNumber() > leftvariable.getLineNumber()))
					removeablevariablelist.add(rightvariable);
			}
		}

		rightvariablelist.removeAll(removeablevariablelist);

		for (Variable rightvariable : rightvariablelist) {
			// add all the declarations before the end of the block to the final
			// declaration list
			for (Declaration declaration : declarationlist) {
				if (rightvariable.getVariable().matches(".*\\[.*\\]")) {
					if (rightvariable.getVariable().replaceAll("\\[.*\\]", "").replaceAll("\\*", "")
							.equals(declaration.getVariable().replaceAll("\\*", ""))
							&& (declaration.getLineNumber() <= rightvariable.getLineNumber())) {
						// make sure there is no duplications in the final list
						boolean duplicationflag = false;
						for (Declaration dec : declarationlistfinal) {
							if (declaration.getVariableType().equals(dec.getVariableType())
									&& rightvariable.getVariable().equals(dec.getVariable())
									&& (declaration.getLineNumber().equals(dec.getLineNumber())))
								duplicationflag = true;
						}

						if (!duplicationflag) {
							Declaration dec_temp = new Declaration();
							dec_temp.setVariableType(declaration.getVariableType());
							dec_temp.setVariable(rightvariable.getVariable());
							dec_temp.setLineNumber(declaration.getLineNumber());
							dec_temp.setVariableMax(declaration.getVariableMax());
							dec_temp.setVariableMin(declaration.getVariableMin());
							dec_temp.setIsArray(rightvariable.getIsArray());
							dec_temp.setArraySize(rightvariable.getArraySize());
							declarationlistfinal.add(dec_temp);
						}
					}
				} else {
					if (rightvariable.getVariable().replaceAll("\\*", "")
							.equals(declaration.getVariable().replaceAll("\\*", ""))
							&& (declaration.getLineNumber() <= rightvariable.getLineNumber())) {
						declaration.setIsArray(rightvariable.getIsArray());
						declaration.setArraySize(rightvariable.getArraySize());
						declarationlistfinal.add(declaration);
					}
				}
			}
		}

		for (Declaration x : declarationlistfinal) {
			// add the declarations within the block to the removable list
			if (x.getLineNumber() > startlinenumber)
				removabledeclarationlist.add(x);
			for (Declaration i : declarationlistfinal) {

				// add the previous declarations to the removable list only keep
				// the
				// last one before the block
				if (x.getVariable().equals(i.getVariable()) && (i.getLineNumber() < x.getLineNumber()))
					removabledeclarationlist.add(i);
			}
		}

		// remove all the removable declarations from the list, only keep the
		// necessary ones for the block
		declarationlistfinal.removeAll(removabledeclarationlist);

		// use annontation
		for (Declaration dec : declarationlistfinal) {
			for (Declaration anno : annotationlist) {
				if (dec.getVariable().equals(anno.getVariable()) && dec.getLineNumber().equals(anno.getLineNumber())
						&& dec.getVariableType().equals(anno.getVariableType())) {
					dec.setVariableMax(anno.getVariableMax());
					dec.setVariableMin(anno.getVariableMin());
				}
			}
		}

		// generate the block inputs
		for (Declaration a : declarationlistfinal) {			
			String inputvariableorg = a.getVariable();
			String inputvariableorg_array = a.getVariable().replaceAll("\\[.*", "") + "_block";
			
			for (String input : inputdeclarationlist) {
				String pointer = input.contains("*") ? "*" : "";
				input = input.replaceAll("\\*", "");
				inputvariableorg = inputvariableorg.replaceAll("\\b" + input + "\\b", pointer + input + "_block");
			}
			if (!a.getVariableType().contains("struct") && !a.getVariableType().contains("union")) {
				a.setVariable(a.getVariable().replaceAll("\\[", "_").replaceAll("\\]", "_"));
				if (!a.getVariableType().contains("const")) {
					
					if (!a.getIsArray()) {
						updateInputs = updateInputs
								.replace(inputvariableorg + " = "
										+ a.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
												.replaceAll("\\*", "mul").replaceAll("\\/", "div")
												.replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
										+ "_inputs[blockInputUpdate_i]; \n", "")
								+ inputvariableorg + " = "
								+ a.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
										.replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i]; \n\t";
					} else {
						alphabet_cnt = 0;
						
						String arraysize[] = a.getArraySize().split(",");
						
						String update = "", update_declaration = "", update_initialization = "",
								update_initialization_left = inputvariableorg_array.replaceAll("\\*", ""),
								update_initialization_right = a.getVariable().replaceAll("\\+", "plus")
										.replaceAll("\\" + "-", "minus").replaceAll("\\*", "mul")
										.replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra").replaceAll(",", "com")
										+ "_inputs[blockInputUpdate_i]",
								update_declaration_loop = "", update_declaration_loopend = "";

						for (int i = 0; i < arraysize.length; i++) {
							String counter = inputvariableorg_array + "_"
									+ alphabet.charAt(alphabet_cnt);
							
							update_declaration = update_declaration + "\tint " + counter + ";\n";
							update_declaration_loop = update_declaration_loop + "\tfor(" + counter + "=0;" + counter
									+ "<" + arraysize[i] + ";" + counter + "++){\n";
							update_initialization_left = update_initialization_left + "[" + counter + "]";
							update_initialization_right = update_initialization_right + "[" + counter + "]";
							update_declaration_loopend = update_declaration_loopend + "\t}\n";
							alphabet_cnt++;
						}

						update_initialization = "\t\t" + update_initialization_left + "=" + update_initialization_right
								+ ";\n";
						update = update_declaration + update_declaration_loop + update_initialization
								+ update_declaration_loopend;

						updateInputs = updateInputs.replace("\n" + update + "\t", "") + "\n" + update + "\t";
					
					}
				}

			} else {

				String variable_temp = "";
				String[] split = a.getVariable().replaceAll("\\]", "").split("\\[|\\]");
				variable_temp = split[0] + "_block";

				for (int i = 1; i < split.length; i++) {

					if (split[i].matches("[0-9]*"))
						variable_temp = variable_temp + "[" + split[i] + "]";
					else
						variable_temp = variable_temp + "[" + split[i] + "_block]";

				}
				a.setVariable(variable_temp);
			}
		}

		randominputgenerator = new File("randominputgenerator.py");
		randomformatter = new Formatter(randominputgenerator);

		randomformatter.format("%s", "# Automatically-generated file. Do not edit! \n \n \n");
		randomformatter.format("%s",
				"import numpy as np \nimport re as replace\n\nnp.set_printoptions(threshold=np.inf)\n\n");

		// writing the python file for input generation
		System.out.println("Generating inputs...");
		for (Declaration declaration : declarationlistfinal) {
			inputGeneration(declaration, length, targetfile);
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

		// Done
		System.out.println("\nInput file generated for blockR" + startlinenumber + "!");

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
		String typequalifier = "const|restrict|volatile|_Atomic";
		boolean issamestructvariable = (structvariablename_pre == structvariablename);
		boolean issamearraysize = (arraysize_pre == arraysize);
		if (!issamestructvariable)
			alphabet_cnt = 0;
		structvariablename_pre = structvariablename;
		variabletypecase = variabletype.replaceAll(storageclassspecifier, "").replaceAll(typequalifier, "")
				.replaceAll("\\b\\s*", "");
		
		// adjust the update function for struct and union
		if (!declaration.getIsArray()) {
			if (!declaration.getVariableType().matches("(struct.*)|(union.*)")) {
				updateInputs = updateInputs
						.replace(structvariablename + "." + declaration.getVariable() + " = "
								+ declaration.getStructName() + "_"
								+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
										.replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i]; \n", "")
						+ structvariablename + "." + declaration.getVariable() + " = " + declaration.getStructName()
						+ "_"
						+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
								.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
								.replaceAll(",", "com")
						+ "_inputs[blockInputUpdate_i]; \n\t";
			} else {

				updateInputs = updateInputs
						.replace(structvariablename + "_block" + "." + declaration.getVariable() + " = "
								+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
										.replaceAll(",", "com")
								+ "_block" + "\n", "")
						+ structvariablename + "_block" + "." + declaration.getVariable() + " = "
						+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
								.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
								.replaceAll(",", "com")
						+ "_block" + "\n\t";
				}
			
		} else {

			String arraysize1[] = declaration.getArraySize().split(",");
			String update = "", update_declaration = "", update_initialization = "",
					update_initialization_left = structvariablename,
					update_initialization_right = declaration.getStructName() + "_"
							+ declaration.getVariable().replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
									.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
									.replaceAll(",", "com")
							+ "_inputs[blockInputUpdate_i]",
					update_declaration_loop = "", update_declaration_loopend = "";
			
			if (!issamestructvariable || !issamearraysize) {
				for (int i = 0; i < arraysize1.length; i++) {
					
					String counter = structvariablename.replaceAll("\\*", "") + "_" + alphabet.charAt(alphabet_cnt);
					System.out.println(counter);
					update_declaration = update_declaration + "\tint " + counter + ";\n";
					update_declaration_loop = update_declaration_loop + "\tfor(" + counter + "=0;" + counter + "<"
							+ arraysize1[i] + ";" + counter + "++){\n";
					update_initialization_left = update_initialization_left + "[" + counter + "]";
					update_initialization_right = update_initialization_right + "[" + counter + "]";
					update_declaration_loopend = update_declaration_loopend + "\t}\n";
					alphabet_cnt++;
				}

				update_initialization = "\t\t" + update_initialization_left + "." + declaration.getVariable() + "="
						+ update_initialization_right + ";\n";
				update = update_declaration + update_declaration_loop + update_initialization
						+ update_declaration_loopend;

				updateInputs = updateInputs.replace("\n" + update + "\t", "") + "\n" + update + "\t";

			} else {

				update_initialization = struct_update_initialization_pre.replace(
						"." + variablename_pre + "=" + declaration.getStructName() + "_"
								+ variablename_pre.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
										.replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i]",
						"." + variablename + "=" + declaration.getStructName() + "_"
								+ variablename.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus")
										.replaceAll("\\*", "mul").replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra")
										.replaceAll(",", "com")
								+ "_inputs[blockInputUpdate_i]");

				updateInputs = updateInputs.replace(struct_update_initialization_pre,
						struct_update_initialization_pre + update_initialization);

			}

			arraysize_pre = arraysize;
			variablename_pre = variablename;
			struct_update_initialization_pre = update_initialization;
		}


		// adjust the forward declaration for struct and union
		if (declaration.getVariableType().matches("(struct.*)|(union.*)")) {
			// System.out.println(variabletype);
			blockinputsdeclaration = variabletype + " " + declaration.getVariable() + "_block" + ";\n"
					+ blockinputsdeclaration.replace(variabletype + " " + declaration.getVariable() + "_block" + ";\n",
							"");

		}

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
					randomformatter.format("%s", "print(\'" + constantvariable + "') \n");
				}

				if (declaration.getVariableType().matches("(struct)+.*")
						| declaration.getVariableType().matches("(union)+.*")) {
					structvariablename = declaration.getVariable();
					String[] s_temp = declaration.getVariableType().replaceFirst("struct", "struct ")
							.replaceFirst("union", "union ").split(" ");
					String structname = s_temp[s_temp.length - 1];

					for (StructDeclaration structdeclaration : structdeclarationlist) {
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
		String typequalifier = "const|restrict|volatile|_Atomic";
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
					randomformatter.format("%s", "print(\'" + constantvariable + "') \n");

				}

				if (declaration.getVariableType().matches("(struct)+.*")
						| declaration.getVariableType().matches("(union)+.*")) {
					structvariablename = declaration.getVariable();
					for (StructDeclaration structdeclaration : structdeclarationlist) {
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
		
		String dtype = "dtype=np.int64";
		if(inputtype.equals("uniform"))
			dtype = "";
		variablename = variablename.replaceAll("\\+", "plus").replaceAll("\\" + "-", "minus").replaceAll("\\*", "mul")
				.replaceAll("\\/", "div").replaceAll("\\(|\\)", "bra").replaceAll(",", "com");

		if (!isarray) {
			randomformatter.format("%s", "temp = str(" + "np.random." + inputtype + "(low=" + min + ",high=" + max
					+ "+1" + ",size=(1," + length + ")" + "," + dtype + ")).replace(\"[[\",\"{\").replace(\"]]\",\"};\") \n");
			randomformatter.format("%s",
					"temp = replace.sub(\"\\s+|\\n\",\",\", temp).replace(\"{,\",\"{\").replace(\",}\",\"}\").replace(\",,\",\",\") \n");
			randomformatter.format("%s", "print(\'" + variabletype + " " + variablename + "_inputs[" + length
					+ "] = \' + temp + \';\' " + ") \n");
		} else {
			randomformatter.format("%s", "temp = str(" + "np.random." + inputtype + "(low=" + min + ",high=" + max
					+ "+1" + ",size=(" + length + "," + arraysize + ")" + "," + dtype + ")) \n");
			randomformatter.format("%s", "temp = replace.sub(\"\\s|\\n\",\",\",temp)\n");
			randomformatter.format("%s", "temp = replace.sub(\"\\,+\",\",\",temp)\n");
			randomformatter.format("%s",
					"temp = temp.replace(\"[,\",\"[\").replace(\",]\",\"]\").replace(\"[\",\"{\").replace(\"]\",\"}\")\n");
			randomformatter.format("%s", "print(\'" + variabletype + " " + variablename + "_inputs[" + length + "]"
					+ "[" + arraysize.replace(",", "][") + "]" + "= \' + temp + \';\' " + ") \n");
		}
	}

	public void headerFileGeneration() throws FileNotFoundException {

		new File(targetfilepath + "\\Inputs\\blockR" + startlinenumber).mkdir();
		inputsh = new File(targetfilepath + "\\Inputs\\blockR" + startlinenumber + "\\inputs.h");
		inputshformatter = new Formatter(inputsh);

		inputshformatter.format("%s", "#ifndef __INPUTS" + " \n");
		inputshformatter.format("%s", "#define __INPUTS" + " \n\n");

		// include the main header file
		inputshformatter.format("%s", "// include the main header" + " \n");
		inputshformatter.format("%s", "#include \"main.h\" " + " \n\n");

		// forward declaration
		inputshformatter.format("%s", "// forward declarations" + " \n");
		inputshformatter.format("%s", "void blockInputUpdate(int);" + " \n");
		inputshformatter.format("%s", "void benchmarkBlock(void);" + " \n\n");

		inputshformatter.format("%s", "#endif" + " \n");

		inputshformatter.close();
	}

	public void sourceFileGeneration() throws IOException {

		new File(targetfilepath + "\\Inputs\\blockR" + startlinenumber).mkdir();
		inputsc = new File(targetfilepath + "\\Inputs\\blockR" + startlinenumber + "\\inputs.c");
		inputscformatter = new Formatter(inputsc);

		// include header files
		inputscformatter.format("%s", "#include \"inputs.h\"\n" + " \n");

		// Block inputs
		ProcessBuilder randombuilder = new ProcessBuilder("python", randominputgenerator.getAbsolutePath());
		// randombuilder.directory(randominputgenerator);
		// randombuilder.redirectOutput(Redirect.INHERIT);
		// randombuilder.redirectError(Redirect.INHERIT);
		Process process = randombuilder.start();

		scanStream(process);
		inputscformatter.format("%s", " \n \n \n");

		// forward declare block variables
		// change the variable names in the inputs files to avoid multiple
		// definitions

		for (String input : inputdeclarationlist) {
			input = input.replaceAll("\\*", "");
			blockinputsdeclaration = blockinputsdeclaration.replaceAll("\\b" + input + "\\b", input + "_block");
		}

		inputscformatter.format("%s", "\n" + blockinputsdeclaration + " \n");

		// inputUpdate function
		inputscformatter.format("%s",
				"\nvoid blockInputUpdate(int blockInputUpdate_i) \n{\n\t" + updateInputs + "\n} \n" + " \n");

		BufferedReader reader = new BufferedReader(new FileReader(new File(blockfile)));

		String line;
		int LN = 1;
		int skipLines = 20;

		while ((line = reader.readLine()) != null) {
			if (LN++ <= skipLines) {
				continue;
			}
			if (!line.contains(" main(")) {

				for (String input : inputdeclarationlist) {
					input = input.replaceAll("\\*", "");
					line = line.replaceAll("\\b" + input + "\\b", input + "_block")
							.replaceAll("\\.\\b" + input + "_block" + "\\b", "\\." + input);
				}
				inputscformatter.format("%s", line + " \n");
			} else {
				while (reader.readLine() != null) {
					reader.readLine();
				}
			}
		}
		reader.close();
		inputscformatter.close();

	}

	public static void scanStream(Process process) {
		Scanner outputmsg = new Scanner(process.getInputStream());
		Scanner errormsg = new Scanner(process.getErrorStream());

		while (outputmsg.hasNextLine()) {
			inputscformatter.format("%s", outputmsg.nextLine() + "\n");
		}
		outputmsg.close();
		while (errormsg.hasNextLine()) {
			System.out.println(errormsg.nextLine());
		}
		errormsg.close();
	}
}