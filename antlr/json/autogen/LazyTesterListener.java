// Generated from LazyTester.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link LazyTesterParser}.
 */
public interface LazyTesterListener extends ParseTreeListener {
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
}