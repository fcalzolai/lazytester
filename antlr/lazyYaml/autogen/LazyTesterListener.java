// Generated from LazyTester.g4 by ANTLR 4.7.2

package antlr.lazytester.autogen;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LazyTesterParser}.
 */
public interface LazyTesterListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#scenario_file}.
	 * @param ctx the parse tree
	 */
	void enterScenario_file(LazyTesterParser.Scenario_fileContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#scenario_file}.
	 * @param ctx the parse tree
	 */
	void exitScenario_file(LazyTesterParser.Scenario_fileContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#paths}.
	 * @param ctx the parse tree
	 */
	void enterPaths(LazyTesterParser.PathsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#paths}.
	 * @param ctx the parse tree
	 */
	void exitPaths(LazyTesterParser.PathsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#steps}.
	 * @param ctx the parse tree
	 */
	void enterSteps(LazyTesterParser.StepsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#steps}.
	 * @param ctx the parse tree
	 */
	void exitSteps(LazyTesterParser.StepsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#step_def}.
	 * @param ctx the parse tree
	 */
	void enterStep_def(LazyTesterParser.Step_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#step_def}.
	 * @param ctx the parse tree
	 */
	void exitStep_def(LazyTesterParser.Step_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#step_name}.
	 * @param ctx the parse tree
	 */
	void enterStep_name(LazyTesterParser.Step_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#step_name}.
	 * @param ctx the parse tree
	 */
	void exitStep_name(LazyTesterParser.Step_nameContext ctx);
}