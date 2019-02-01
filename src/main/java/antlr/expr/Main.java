package antlr.expr;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Main {

    public static void main(String[] args) {
        CharStream cs = CharStreams.fromString("3+5");
        ExprLexer lexer = new ExprLexer(cs);  //instantiate a lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens
        ExprParser parser = new ExprParser(tokens);  //parse the tokens

        ParseTree tree = parser.expr();
        ExprBaseListener listener = new ExprBaseListener();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener,tree);
    }
}
