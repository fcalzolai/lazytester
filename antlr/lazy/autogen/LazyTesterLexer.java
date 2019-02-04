// Generated from LazyTester.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LazyTesterLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, NAME=7, LOOP=8, STEPS=9, 
		URL=10, OPERATION=11, HTTP_OPS=12, ASSERTIONS=13, STATUS=14, STRING=15, 
		INT=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "NAME", "LOOP", "STEPS", 
			"URL", "OPERATION", "HTTP_OPS", "ASSERTIONS", "STATUS", "STRING", "INT", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "','", "':'", "'['", "']'", "'\"name\"'", "'\"loop\"'", 
			"'\"steps\"'", "'\"url\"'", "'\"operation\"'", null, "'\"assertions\"'", 
			"'\"status\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "NAME", "LOOP", "STEPS", "URL", 
			"OPERATION", "HTTP_OPS", "ASSERTIONS", "STATUS", "STRING", "INT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LazyTesterLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LazyTester.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23\u009d\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\rr\n\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\20\3\20\6\20\u008c\n\20\r\20\16\20\u008d\3\20\3\20\3"+
		"\21\6\21\u0093\n\21\r\21\16\21\u0094\3\22\6\22\u0098\n\22\r\22\16\22\u0099"+
		"\3\22\3\22\2\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23\3\2\5\3\2$$\3\2\62;\5\2\13\f\16\17\"\"\2"+
		"\u00a2\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"+
		"\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3"+
		"\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2"+
		"\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7)\3\2\2\2\t+\3\2\2\2\13-\3\2\2\2\r"+
		"/\3\2\2\2\17\61\3\2\2\2\218\3\2\2\2\23?\3\2\2\2\25G\3\2\2\2\27M\3\2\2"+
		"\2\31q\3\2\2\2\33s\3\2\2\2\35\u0080\3\2\2\2\37\u0089\3\2\2\2!\u0092\3"+
		"\2\2\2#\u0097\3\2\2\2%&\7}\2\2&\4\3\2\2\2\'(\7\177\2\2(\6\3\2\2\2)*\7"+
		".\2\2*\b\3\2\2\2+,\7<\2\2,\n\3\2\2\2-.\7]\2\2.\f\3\2\2\2/\60\7_\2\2\60"+
		"\16\3\2\2\2\61\62\7$\2\2\62\63\7p\2\2\63\64\7c\2\2\64\65\7o\2\2\65\66"+
		"\7g\2\2\66\67\7$\2\2\67\20\3\2\2\289\7$\2\29:\7n\2\2:;\7q\2\2;<\7q\2\2"+
		"<=\7r\2\2=>\7$\2\2>\22\3\2\2\2?@\7$\2\2@A\7u\2\2AB\7v\2\2BC\7g\2\2CD\7"+
		"r\2\2DE\7u\2\2EF\7$\2\2F\24\3\2\2\2GH\7$\2\2HI\7w\2\2IJ\7t\2\2JK\7n\2"+
		"\2KL\7$\2\2L\26\3\2\2\2MN\7$\2\2NO\7q\2\2OP\7r\2\2PQ\7g\2\2QR\7t\2\2R"+
		"S\7c\2\2ST\7v\2\2TU\7k\2\2UV\7q\2\2VW\7p\2\2WX\7$\2\2X\30\3\2\2\2YZ\7"+
		"$\2\2Z[\7I\2\2[\\\7G\2\2\\]\7V\2\2]r\7$\2\2^_\7$\2\2_`\7R\2\2`a\7Q\2\2"+
		"ab\7U\2\2bc\7V\2\2cr\7$\2\2de\7$\2\2ef\7R\2\2fg\7W\2\2gh\7V\2\2hr\7$\2"+
		"\2ij\7$\2\2jk\7F\2\2kl\7G\2\2lm\7N\2\2mn\7G\2\2no\7V\2\2op\7G\2\2pr\7"+
		"$\2\2qY\3\2\2\2q^\3\2\2\2qd\3\2\2\2qi\3\2\2\2r\32\3\2\2\2st\7$\2\2tu\7"+
		"c\2\2uv\7u\2\2vw\7u\2\2wx\7g\2\2xy\7t\2\2yz\7v\2\2z{\7k\2\2{|\7q\2\2|"+
		"}\7p\2\2}~\7u\2\2~\177\7$\2\2\177\34\3\2\2\2\u0080\u0081\7$\2\2\u0081"+
		"\u0082\7u\2\2\u0082\u0083\7v\2\2\u0083\u0084\7c\2\2\u0084\u0085\7v\2\2"+
		"\u0085\u0086\7w\2\2\u0086\u0087\7u\2\2\u0087\u0088\7$\2\2\u0088\36\3\2"+
		"\2\2\u0089\u008b\7$\2\2\u008a\u008c\n\2\2\2\u008b\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u008f\3\2"+
		"\2\2\u008f\u0090\7$\2\2\u0090 \3\2\2\2\u0091\u0093\t\3\2\2\u0092\u0091"+
		"\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2\2\2\u0095"+
		"\"\3\2\2\2\u0096\u0098\t\4\2\2\u0097\u0096\3\2\2\2\u0098\u0099\3\2\2\2"+
		"\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c"+
		"\b\22\2\2\u009c$\3\2\2\2\7\2q\u008d\u0094\u0099\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}