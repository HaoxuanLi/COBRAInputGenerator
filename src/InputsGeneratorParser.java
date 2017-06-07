import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.*;

class Variable {
	private String variable;
	private Integer lineNumber;
//	private boolean isarray = false;
//	private String arraysize;

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

/*	public boolean getIsArray() {
		return isarray;
	}

	public void setIsArray(boolean isarray) {
		this.isarray = isarray;
	}

	public String getArraySize() {
		return arraysize;
	}

	public void setArraySize(String arraysize) {
		this.arraysize = arraysize;
	}
	*/
}

class Declaration {
	private String variableType;
	private String variable;
	private Integer lineNumber;
	private String variablemax;
	private String variablemin;
	private boolean isarray;
	private String arraysize;

	public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getVariableMax() {
		return variablemax;
	}

	public void setVariableMax(String variablemax) {
		this.variablemax = variablemax;
	}

	public String getVariableMin() {
		return variablemin;
	}

	public void setVariableMin(String variablemin) {
		this.variablemin = variablemin;
	}

	public boolean getIsArray() {
		return isarray;
	}

	public void setIsArray(boolean isarray) {
		this.isarray = isarray;
	}

	public String getArraySize() {
		return arraysize;
	}

	public void setArraySize(String arraysize) {
		this.arraysize = arraysize;
	}
}

class StructDeclaration {
	private String structname;
	private String variableType;
	private String variable;
	private Integer lineNumber;
	private String variablemax;
	private String variablemin;
	private String targetfile;
	private boolean isarray;
	private String arraysize;

	public String getStructName() {
		return structname;
	}

	public void setStructName(String structname) {
		this.structname = structname;
	}

	public String getVariableType() {
		return variableType;
	}

	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}

	public String getVariable() {
		return variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getVariableMax() {
		return variablemax;
	}

	public void setVariableMax(String variablemax) {
		this.variablemax = variablemax;
	}

	public String getVariableMin() {
		return variablemin;
	}

	public void setVariableMin(String variablemin) {
		this.variablemin = variablemin;
	}

	public String getTargetFile() {
		return targetfile;
	}

	public void setTargetFile(String targetfile) {
		this.targetfile = targetfile;
	}

	public boolean getIsArray() {
		return isarray;
	}

	public void setIsArray(boolean isarray) {
		this.isarray = isarray;
	}

	public String getArraySize() {
		return arraysize;
	}

	public void setArraySize(String arraysize) {
		this.arraysize = arraysize;
	}
}

public class InputsGeneratorParser {

	List<Variable> leftVariableList = new ArrayList<Variable>();
	List<Variable> rightVariableList = new ArrayList<Variable>();
	List<Declaration> declarationList = new ArrayList<Declaration>();
	List<StructDeclaration> structdeclarationList = new ArrayList<StructDeclaration>();
	String blocklocaldeclaration = "";
	int lineNumber = 0;
	String type = "";
	String pointer = "";
	String structname = "";
	Boolean isarray = false;

	InputsGeneratorParser() {

	}

	InputsGeneratorParser(String code) {
		CharStream charStream = new ANTLRInputStream(code);
		CLexer lexer = new CLexer(charStream);
		lexer.removeErrorListeners();
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CParser parser = new CParser(tokens);
		parser.removeErrorListeners();

		ParseTree tree = parser.benchmarkNodeFunction();
		BenchmarkNodeFunctionListener listener = new BenchmarkNodeFunctionListener();

		ParseTreeWalker walker = new ParseTreeWalker();

		walker.walk(listener, tree);

	}

	void ParserAssignment(String code) {
		// Get lexer
		CharStream charStream = new ANTLRInputStream(code);
		CLexer lexer = new CLexer(charStream);
		lexer.removeErrorListeners();
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// Pass the tokens to the parser
		CParser parser = new CParser(tokens);
		parser.removeErrorListeners();

		// Specify the entry point
		// CParser.AssignmentTreeContext AssignmentTreeContext =
		// parser.assignmentTree();
		ParseTree tree = parser.assignmentTree();

		// Walk it and attach the listener
		ParseTreeWalker walker = new ParseTreeWalker();
		AssignmentTreeListener listener = new AssignmentTreeListener();

		walker.walk(listener, tree);
	}

	void ParserRelationalExpressoin(String code) {
		// Get lexer
		CharStream charStream = new ANTLRInputStream(code);
		CLexer lexer = new CLexer(charStream);
		lexer.removeErrorListeners();
		// Get a list of matched tokens
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		// Pass the tokens to the parser
		CParser parser = new CParser(tokens);
		parser.removeErrorListeners();

		// Specify the entry point
		// CParser.AssignmentTreeContext AssignmentTreeContext =
		// parser.assignmentTree();
		ParseTree tree = parser.relationalExpressionTree();

		// Walk it and attach the listener
		ParseTreeWalker walker = new ParseTreeWalker();
		RelationalExpressionTreeListener listener = new RelationalExpressionTreeListener();

		walker.walk(listener, tree);

	}

	class BenchmarkNodeFunctionListener extends CBaseListener {

		@Override
		public void enterDeclaration(CParser.DeclarationContext ctx) {
			String blocklocaldeclaration_temp = "";
			String initdeclaratorlist = "";
			type = "";
			for (CParser.DeclarationSpecifierContext declarationSpecifier : ctx.declarationSpecifiers()
					.declarationSpecifier()) {
				type = type + " " + declarationSpecifier.getText();
			}
			type = type.replaceFirst("struct", "struct ").replaceFirst("union", "union ").replaceAll("^\\s*", "");

			if (ctx.initDeclaratorList() != null)
				initdeclaratorlist = ctx.initDeclaratorList().getText();
			if (ctx.getText().contains(";")) {
				if (!ctx.getText().contains("(")) {
					blocklocaldeclaration_temp = type + " " + initdeclaratorlist + ";" + "\n";
					blocklocaldeclaration = blocklocaldeclaration + blocklocaldeclaration_temp;
				} else if (ctx.getText().contains("=")) {
					blocklocaldeclaration_temp = type + " " + initdeclaratorlist + ";" + "\n";
					blocklocaldeclaration = blocklocaldeclaration + blocklocaldeclaration_temp;
				}
				// System.out.println(blocklocaldeclaration_temp);
			}

		}

		@Override
		public void enterParameterDeclaration(CParser.ParameterDeclarationContext ctx) {
			type = "";
			if (ctx.declarationSpecifiers() != null) {
				for (CParser.DeclarationSpecifierContext declarationSpecifier : ctx.declarationSpecifiers()
						.declarationSpecifier()) {
					type = type + " " + declarationSpecifier.getText();
				}
				type = type.replaceFirst("struct", "struct ").replaceFirst("union", "union ").replaceAll("^\\s*", "");
			}
		}

		@Override
		public void enterDeclarator(CParser.DeclaratorContext ctx) {
			pointer = "";
			if (ctx.pointer() != null) {
				pointer = "*";
			}

		}
		
		@Override
		public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
			if(ctx.getText().replaceAll(" ","").matches("^\\w*\\[.*\\]"))
				isarray = true;
			
			if (ctx.Identifier() != null) {
				Declaration declaration = new Declaration();
				declaration.setVariableType(type);
				declaration.setVariable(pointer + ctx.getText());
				declaration.setLineNumber(ctx.start.getLine());
				declaration.setIsArray(isarray);
				declarationList.add(declaration);
				isarray = false;
			}

		}

		@Override
		public void enterEqualityExpression(CParser.EqualityExpressionContext ctx) {
			if (ctx.getChildCount() == 3) {
				String expression = "[LineNumber: " + ctx.start.getLine() + "]" + ctx.getText();
				// System.out.println(expression);
				ParserRelationalExpressoin(expression);
			}
		}

		@Override
		public void enterRelationalExpression(CParser.RelationalExpressionContext ctx) {
			if (ctx.getChildCount() == 3) {
				String expression = "[LineNumber: " + ctx.start.getLine() + "]" + ctx.getText();
				// System.out.println(expression);
				ParserRelationalExpressoin(expression);
			}
		}

		@Override
		public void enterBlockItem(CParser.BlockItemContext ctx) {
			// if (ctx.getChildCount() == 3) {

			try {
				if (!ctx.getText().contains("{")) {
					int a = ctx.start.getStartIndex();
					int b = ctx.stop.getStopIndex();
					Interval interval = new Interval(a, b);
					ParserAssignment(
							"[LineNumber: " + ctx.start.getLine() + "]" + ctx.start.getInputStream().getText(interval));
				}
			} catch (Exception e) {
			}
			// }
		}

		@Override
		public void enterStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {
			if (ctx.getText().contains("{") && ctx.Identifier() != null)
				structname = ctx.Identifier().getText();
			if (ctx.Identifier() == null)
				System.out.println(
						"Struct declaration without struct name is not  impelemented, inputs file may be wrong!");
		}

		@Override
		public void enterSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) {
			type = ctx.getText();
			// System.out.println(type);

		}

		@Override
		public void enterStructDeclarator(CParser.StructDeclaratorContext ctx) {
			StructDeclaration structdeclaration = new StructDeclaration();
			structdeclaration.setStructName(structname);
			structdeclaration.setVariableType(type);
			structdeclaration.setVariable(ctx.getText());
			structdeclaration.setLineNumber(ctx.start.getLine());
			structdeclarationList.add(structdeclaration);
			// System.out.println(type + ctx.getText());
		}

	}

	// List<Data> list = new ArrayList<Data>();

	class AssignmentTreeListener extends CBaseListener {

		@Override
		public void enterLineNumber(CParser.LineNumberContext ctx) {
			lineNumber = Integer.parseInt(ctx.getText());
			// System.out.println(lineNumber);
		}

		@Override
		public void enterRightExpression(CParser.RightExpressionContext ctx) {
			pointer = "";
			if (ctx.pointer() != null) {
				pointer = "*";
			}
		}

		@Override
		public void enterRightVariable(CParser.RightVariableContext ctx) {
			if (ctx.Identifier() != null) {
				Variable rightVariable = new Variable();
				rightVariable.setVariable(pointer + ctx.getText());
				rightVariable.setLineNumber(lineNumber);
				rightVariableList.add(rightVariable);
				// System.out.print("right: " + ctx.getText() + lineNumber +
				// "\n");
			}
		}

		@Override
		public void enterLeftExpression(CParser.LeftExpressionContext ctx) {
			pointer = "";
			if (ctx.pointer() != null) {
				pointer = "*";
			}
		}

		@Override
		public void enterLeftVariable(CParser.LeftVariableContext ctx) {
			if (ctx.Identifier() != null) {
				Variable leftVariable = new Variable();
				leftVariable.setVariable(pointer + ctx.getText());
				leftVariable.setLineNumber(lineNumber);
				leftVariableList.add(leftVariable);
				// System.out.print("left: "+ctx.getText() + lineNumber +"\n");
			}
		}

	}

	class RelationalExpressionTreeListener extends CBaseListener {
		@Override
		public void enterLineNumber(CParser.LineNumberContext ctx) {
			lineNumber = Integer.parseInt(ctx.getText());
		}

		@Override
		public void enterRightVariable(CParser.RightVariableContext ctx) {
			if (ctx.Identifier() != null) {
				Variable rightVariable = new Variable();
				rightVariable.setVariable(ctx.getText());
				rightVariable.setLineNumber(lineNumber);
				rightVariableList.add(rightVariable);
			}

		}

	}

	public void getContext() {

	}

	public static class ThrowingErrorListener extends BaseErrorListener {
		public static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

		@Override
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
				String msg, RecognitionException e) {
			if (e == null) {
				// e is null when the parser was able to recover in line without
				// exiting the surrounding rule.
				// Use InlineRecognitionException subclass to identify this
				// recovery mode.
				e = new InlineRecognitionException(msg, recognizer, ((Parser) recognizer).getInputStream(),
						((Parser) recognizer).getContext(), (Token) offendingSymbol);

			}

			ParserRuleContext syntaxerrorctx = (ParserRuleContext) e.getCtx();

			while (syntaxerrorctx.getChildCount() != 0) {
				syntaxerrorctx.removeLastChild();
			}
		}

		@SuppressWarnings("serial")
		public class InlineRecognitionException extends RecognitionException {

			public InlineRecognitionException(String message, Recognizer<?, ?> recognizer, IntStream input,
					ParserRuleContext ctx, Token offendingToken) {
				super(message, recognizer, input, ctx);
				this.setOffendingToken(offendingToken);
			}
		}

	}

	public List<Variable> getLeftVariableList() {
		return leftVariableList;

	}

	public void setLeftVariableList() {

	}

	public List<Variable> getRightVariableList() {
		return rightVariableList;

	}

	public void setRightVariableList() {

	}

	public List<Declaration> getDeclarationList() {
		return declarationList;

	}

	public void setDeclarationList() {

	}

	public List<StructDeclaration> getStructDeclarationList() {
		return structdeclarationList;

	}

	public void setStructDeclarationList() {

	}

	public String getBlockLocalDeclaration() {
		return blocklocaldeclaration;

	}

	public void setBlockLocalDeclaration() {

	}
}
