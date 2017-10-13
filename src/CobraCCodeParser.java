import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.*;

import VariableClasses.Declaration;
import VariableClasses.StructDeclaration;
import VariableClasses.Variable;

public class CobraCCodeParser {

	private List<Variable> leftVariableList = new ArrayList<Variable>();
	private List<Variable> rightVariableList = new ArrayList<Variable>();
	private List<Declaration> declarationList = new ArrayList<Declaration>();
	private List<StructDeclaration> structdeclarationList = new ArrayList<StructDeclaration>();
	private String blockLocalDeclaration = "";
	private int lineNumber = 0;
	private String type = "";
	private String pointer = "";
	private String arraySize = "";
	private Boolean isArray = false;
	private String forLoopCounterName;
	
	CobraCCodeParser() {

	}

	CobraCCodeParser(String code) {
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
					blockLocalDeclaration = blockLocalDeclaration + blocklocaldeclaration_temp;
				} else if (ctx.getText().contains("=")) {
					blocklocaldeclaration_temp = type + " " + initdeclaratorlist + ";" + "\n";
					blockLocalDeclaration = blockLocalDeclaration + blocklocaldeclaration_temp;
				}
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
			if (ctx.pointer() != null && ctx.pointer().getText().equals("*")) {
				pointer = "*";
			}
		}

		@Override
		public void enterDirectDeclarator(CParser.DirectDeclaratorContext ctx) {
			if (ctx.getText().replaceAll(" ", "").matches("^\\w*\\[.*\\]")) {
				isArray = true;
				arraySize = ctx.getText().replaceFirst("[^\\[]*\\[", "").replaceAll("\\]$", "").replaceAll("\\]\\[",
						",");
			}

			if (ctx.Identifier() != null) {
				Declaration declaration = new Declaration();
				declaration.setDeclarationType(type);
				declaration.setDeclarationName(pointer + ctx.getText().replaceAll("\\s+", "").replaceAll("\\[.*", ""));
				declaration.setLineNumber(ctx.start.getLine());
				declaration.setIsArray(isArray);
				declaration.setArraySize(arraySize);
				declaration.setIsForLoopCounter(false);
				declarationList.add(declaration);
				isArray = false;
				arraySize = "";
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
				} else {
					int a = ctx.start.getStartIndex();
					int b = ctx.stop.getStopIndex();
					Interval interval = new Interval(a, b);
					ParserAssignment("[LineNumber: " + ctx.start.getLine() + "]" + ctx.start.getInputStream()
							.getText(interval).replaceAll("\\n", "").replaceAll("\\{.*", ""));
				}
			} catch (Exception e) {
			}
			// }
		}

		@Override
		public void enterStructOrUnionSpecifier(CParser.StructOrUnionSpecifierContext ctx) {
			if (ctx.getText().contains("{") && ctx.Identifier() != null) {
				StructDeclaration structDeclaration = new StructDeclaration();
				String structDelcarationType = ctx.structOrUnion().getText();
				String structDeclarationName = ctx.Identifier().getText();
				structDeclaration.setDeclarationType(structDelcarationType);
				structDeclaration.setDeclarationName(structDeclarationName);
				structdeclarationList.add(structDeclaration);

			}
			if (ctx.Identifier() == null)
				System.out.println(
						"Struct declaration without struct name is not  impelemented, inputs file may be wrong!");
		}

		@Override
		public void enterSpecifierQualifierList(CParser.SpecifierQualifierListContext ctx) {
			type = ctx.getText();
		}

		@Override
		public void enterStructDeclarator(CParser.StructDeclaratorContext ctx) {
			// System.out.print("\n"+ctx.getText()+"\n");
			Declaration nonStructComponent = new Declaration();
			Boolean isArray_Local = false;
			String arraySize_Local = "";
			nonStructComponent.setDeclarationName(ctx.getText().replaceAll("\\s+", "").replaceAll("\\[.*", ""));
			nonStructComponent.setDeclarationType(type);

			if (ctx.getText().replaceAll(" ", "").matches("^\\w*\\[.*\\]")) {
				isArray_Local = true;
				arraySize_Local = ctx.getText().replaceFirst("[^\\[]*\\[", "").replaceAll("\\]$", "")
						.replaceAll("\\]\\[", ",");
			}
			nonStructComponent.setIsArray(isArray_Local);
			nonStructComponent.setArraySize(arraySize_Local);

			structdeclarationList.get(structdeclarationList.size() - 1).addNonStructComponentToList(nonStructComponent);
		}

		@Override
		public void enterForIterationPrefix(CParser.ForIterationPrefixContext ctx) {
			String declarationName = ctx.getParent().getText().replaceAll("\\s", "").replaceAll(";.*|for\\(", "")
					.replaceAll("=.*", "");

			for (int i = declarationList.size() - 1; i >= 0; i--) {
				if (declarationList.get(i).getDeclarationName().equals(declarationName)) {
					declarationList.get(i).setIsForLoopCounter(true);
					break;
				}
			}
		}

	}

	// List<Data> list = new ArrayList<Data>();

	class AssignmentTreeListener extends CBaseListener {
		private Boolean isSizeofOrAlignof = false;

		@Override
		public void enterLineNumber(CParser.LineNumberContext ctx) {
			lineNumber = Integer.parseInt(ctx.getText());
			// System.out.println(lineNumber);
		}

		@Override
		public void enterRightExpression(CParser.RightExpressionContext ctx) {
			pointer = "";
			if (ctx.pointer() != null && ctx.pointer().getText().equals("*")) {
				pointer = "*";
			}
		}

		@Override
		public void enterForIterationPrefix(CParser.ForIterationPrefixContext ctx) {
			forLoopCounterName = ctx.getParent().getText().replaceAll("\\s", "").replaceAll(";.*|for\\(", "")
					.replaceAll("=.*", "");
		}
		
		@Override
		public void enterRightVariable(CParser.RightVariableContext ctx) {

			if (ctx.Sizeof() != null || ctx.Alignof() != null && ctx.typeName() == null) {
				isSizeofOrAlignof = true;
			}

			if (ctx.Identifier() != null) {
				if (!isSizeofOrAlignof) {
					Variable rightVariable = new Variable();
					rightVariable.setVariableName(ctx.getText());
					rightVariable.setLineNumber(lineNumber);
					Boolean isForLoopCounter = ctx.getText().equals(forLoopCounterName);
					rightVariable.setIsForLoopCounter(isForLoopCounter);
					rightVariableList.add(rightVariable);
				} else {
					isSizeofOrAlignof = false;
				}
			}
		}

		@Override
		public void enterLeftExpression(CParser.LeftExpressionContext ctx) {
			pointer = "";
			if (ctx.pointer() != null && ctx.pointer().getText().equals("*")) {
				pointer = "*";
			}
		}

		@Override
		public void enterLeftVariable(CParser.LeftVariableContext ctx) {
			if (ctx.Identifier() != null) {
				Variable leftVariable = new Variable();
				leftVariable.setVariableName(pointer + ctx.getText());
				leftVariable.setLineNumber(lineNumber);
				leftVariableList.add(leftVariable);
			}
		}
	}

	class RelationalExpressionTreeListener extends CBaseListener {
		private Boolean isSizeofOrAlignof = false;

		@Override
		public void enterLineNumber(CParser.LineNumberContext ctx) {
			lineNumber = Integer.parseInt(ctx.getText());
		}

		@Override
		public void enterRightVariable(CParser.RightVariableContext ctx) {

			if (ctx.Sizeof() != null || ctx.Alignof() != null && ctx.typeName() == null) {
				isSizeofOrAlignof = true;
			}

			if (ctx.Identifier() != null) {
				if (!isSizeofOrAlignof) {
					Variable rightVariable = new Variable();
					rightVariable.setVariableName(ctx.getText());
					rightVariable.setLineNumber(lineNumber);
					Boolean isForLoopCounter = ctx.getText().equals(forLoopCounterName);
					rightVariable.setIsForLoopCounter(isForLoopCounter);
					rightVariableList.add(rightVariable);
				} else {
					isSizeofOrAlignof = false;
				}
			}
		}
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
		return blockLocalDeclaration;

	}

	public void setBlockLocalDeclaration() {

	}
}
