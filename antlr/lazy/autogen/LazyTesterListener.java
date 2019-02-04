// Generated from LazyTester.g4 by ANTLR 4.7.2
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
	 * Enter a parse tree produced by {@link LazyTesterParser#scenario}.
	 * @param ctx the parse tree
	 */
	void enterScenario(LazyTesterParser.ScenarioContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#scenario}.
	 * @param ctx the parse tree
	 */
	void exitScenario(LazyTesterParser.ScenarioContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#scenario_name}.
	 * @param ctx the parse tree
	 */
	void enterScenario_name(LazyTesterParser.Scenario_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#scenario_name}.
	 * @param ctx the parse tree
	 */
	void exitScenario_name(LazyTesterParser.Scenario_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#steps_def}.
	 * @param ctx the parse tree
	 */
	void enterSteps_def(LazyTesterParser.Steps_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#steps_def}.
	 * @param ctx the parse tree
	 */
	void exitSteps_def(LazyTesterParser.Steps_defContext ctx);
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
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#url_def}.
	 * @param ctx the parse tree
	 */
	void enterUrl_def(LazyTesterParser.Url_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#url_def}.
	 * @param ctx the parse tree
	 */
	void exitUrl_def(LazyTesterParser.Url_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#operation}.
	 * @param ctx the parse tree
	 */
	void enterOperation(LazyTesterParser.OperationContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#operation}.
	 * @param ctx the parse tree
	 */
	void exitOperation(LazyTesterParser.OperationContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#assertions_def}.
	 * @param ctx the parse tree
	 */
	void enterAssertions_def(LazyTesterParser.Assertions_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#assertions_def}.
	 * @param ctx the parse tree
	 */
	void exitAssertions_def(LazyTesterParser.Assertions_defContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#assertions}.
	 * @param ctx the parse tree
	 */
	void enterAssertions(LazyTesterParser.AssertionsContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#assertions}.
	 * @param ctx the parse tree
	 */
	void exitAssertions(LazyTesterParser.AssertionsContext ctx);
	/**
	 * Enter a parse tree produced by {@link LazyTesterParser#loop_def}.
	 * @param ctx the parse tree
	 */
	void enterLoop_def(LazyTesterParser.Loop_defContext ctx);
	/**
	 * Exit a parse tree produced by {@link LazyTesterParser#loop_def}.
	 * @param ctx the parse tree
	 */
	void exitLoop_def(LazyTesterParser.Loop_defContext ctx);
}