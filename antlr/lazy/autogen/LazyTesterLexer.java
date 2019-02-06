// Generated from LazyTester.g4 by ANTLR 4.7.2

package antlr.lazytester.autogen;

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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		NAME=10, LOOP=11, STEPS=12, URL=13, OPERATION=14, HTTP_OPS=15, ASSERTIONS=16, 
		STATUS=17, PARAMS=18, HEADERS=19, BODY=20, STRING=21, INT=22, WS=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"NAME", "LOOP", "STEPS", "URL", "OPERATION", "HTTP_OPS", "ASSERTIONS", 
			"STATUS", "PARAMS", "HEADERS", "BODY", "STRING", "INT", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "','", "':'", "'['", "']'", "'\"ignoreStepFailures\"'", 
			"'\"true\"'", "'\"false\"'", "'\"name\"'", "'\"loop\"'", "'\"steps\"'", 
			"'\"url\"'", "'\"operation\"'", null, "'\"assertions\"'", "'\"status\"'", 
			"'\"params\"'", "'\"headers\"'", "'\"body\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "NAME", "LOOP", 
			"STEPS", "URL", "OPERATION", "HTTP_OPS", "ASSERTIONS", "STATUS", "PARAMS", 
			"HEADERS", "BODY", "STRING", "INT", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u00e7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3"+
		"\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\5\20\u00a2\n\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\6\26\u00d6"+
		"\n\26\r\26\16\26\u00d7\3\26\3\26\3\27\6\27\u00dd\n\27\r\27\16\27\u00de"+
		"\3\30\6\30\u00e2\n\30\r\30\16\30\u00e3\3\30\3\30\2\2\31\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\3\2\5\3\2$$\3\2\62;\5\2\13\f\16\17\"\"\2\u00ec"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\3\61\3\2\2\2\5\63\3\2\2\2\7\65\3\2\2\2\t\67\3\2\2\2\139\3\2\2\2\r;"+
		"\3\2\2\2\17=\3\2\2\2\21R\3\2\2\2\23Y\3\2\2\2\25a\3\2\2\2\27h\3\2\2\2\31"+
		"o\3\2\2\2\33w\3\2\2\2\35}\3\2\2\2\37\u00a1\3\2\2\2!\u00a3\3\2\2\2#\u00b0"+
		"\3\2\2\2%\u00b9\3\2\2\2\'\u00c2\3\2\2\2)\u00cc\3\2\2\2+\u00d3\3\2\2\2"+
		"-\u00dc\3\2\2\2/\u00e1\3\2\2\2\61\62\7}\2\2\62\4\3\2\2\2\63\64\7\177\2"+
		"\2\64\6\3\2\2\2\65\66\7.\2\2\66\b\3\2\2\2\678\7<\2\28\n\3\2\2\29:\7]\2"+
		"\2:\f\3\2\2\2;<\7_\2\2<\16\3\2\2\2=>\7$\2\2>?\7k\2\2?@\7i\2\2@A\7p\2\2"+
		"AB\7q\2\2BC\7t\2\2CD\7g\2\2DE\7U\2\2EF\7v\2\2FG\7g\2\2GH\7r\2\2HI\7H\2"+
		"\2IJ\7c\2\2JK\7k\2\2KL\7n\2\2LM\7w\2\2MN\7t\2\2NO\7g\2\2OP\7u\2\2PQ\7"+
		"$\2\2Q\20\3\2\2\2RS\7$\2\2ST\7v\2\2TU\7t\2\2UV\7w\2\2VW\7g\2\2WX\7$\2"+
		"\2X\22\3\2\2\2YZ\7$\2\2Z[\7h\2\2[\\\7c\2\2\\]\7n\2\2]^\7u\2\2^_\7g\2\2"+
		"_`\7$\2\2`\24\3\2\2\2ab\7$\2\2bc\7p\2\2cd\7c\2\2de\7o\2\2ef\7g\2\2fg\7"+
		"$\2\2g\26\3\2\2\2hi\7$\2\2ij\7n\2\2jk\7q\2\2kl\7q\2\2lm\7r\2\2mn\7$\2"+
		"\2n\30\3\2\2\2op\7$\2\2pq\7u\2\2qr\7v\2\2rs\7g\2\2st\7r\2\2tu\7u\2\2u"+
		"v\7$\2\2v\32\3\2\2\2wx\7$\2\2xy\7w\2\2yz\7t\2\2z{\7n\2\2{|\7$\2\2|\34"+
		"\3\2\2\2}~\7$\2\2~\177\7q\2\2\177\u0080\7r\2\2\u0080\u0081\7g\2\2\u0081"+
		"\u0082\7t\2\2\u0082\u0083\7c\2\2\u0083\u0084\7v\2\2\u0084\u0085\7k\2\2"+
		"\u0085\u0086\7q\2\2\u0086\u0087\7p\2\2\u0087\u0088\7$\2\2\u0088\36\3\2"+
		"\2\2\u0089\u008a\7$\2\2\u008a\u008b\7I\2\2\u008b\u008c\7G\2\2\u008c\u008d"+
		"\7V\2\2\u008d\u00a2\7$\2\2\u008e\u008f\7$\2\2\u008f\u0090\7R\2\2\u0090"+
		"\u0091\7Q\2\2\u0091\u0092\7U\2\2\u0092\u0093\7V\2\2\u0093\u00a2\7$\2\2"+
		"\u0094\u0095\7$\2\2\u0095\u0096\7R\2\2\u0096\u0097\7W\2\2\u0097\u0098"+
		"\7V\2\2\u0098\u00a2\7$\2\2\u0099\u009a\7$\2\2\u009a\u009b\7F\2\2\u009b"+
		"\u009c\7G\2\2\u009c\u009d\7N\2\2\u009d\u009e\7G\2\2\u009e\u009f\7V\2\2"+
		"\u009f\u00a0\7G\2\2\u00a0\u00a2\7$\2\2\u00a1\u0089\3\2\2\2\u00a1\u008e"+
		"\3\2\2\2\u00a1\u0094\3\2\2\2\u00a1\u0099\3\2\2\2\u00a2 \3\2\2\2\u00a3"+
		"\u00a4\7$\2\2\u00a4\u00a5\7c\2\2\u00a5\u00a6\7u\2\2\u00a6\u00a7\7u\2\2"+
		"\u00a7\u00a8\7g\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa\7v\2\2\u00aa\u00ab"+
		"\7k\2\2\u00ab\u00ac\7q\2\2\u00ac\u00ad\7p\2\2\u00ad\u00ae\7u\2\2\u00ae"+
		"\u00af\7$\2\2\u00af\"\3\2\2\2\u00b0\u00b1\7$\2\2\u00b1\u00b2\7u\2\2\u00b2"+
		"\u00b3\7v\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7v\2\2\u00b5\u00b6\7w\2\2"+
		"\u00b6\u00b7\7u\2\2\u00b7\u00b8\7$\2\2\u00b8$\3\2\2\2\u00b9\u00ba\7$\2"+
		"\2\u00ba\u00bb\7r\2\2\u00bb\u00bc\7c\2\2\u00bc\u00bd\7t\2\2\u00bd\u00be"+
		"\7c\2\2\u00be\u00bf\7o\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1\7$\2\2\u00c1"+
		"&\3\2\2\2\u00c2\u00c3\7$\2\2\u00c3\u00c4\7j\2\2\u00c4\u00c5\7g\2\2\u00c5"+
		"\u00c6\7c\2\2\u00c6\u00c7\7f\2\2\u00c7\u00c8\7g\2\2\u00c8\u00c9\7t\2\2"+
		"\u00c9\u00ca\7u\2\2\u00ca\u00cb\7$\2\2\u00cb(\3\2\2\2\u00cc\u00cd\7$\2"+
		"\2\u00cd\u00ce\7d\2\2\u00ce\u00cf\7q\2\2\u00cf\u00d0\7f\2\2\u00d0\u00d1"+
		"\7{\2\2\u00d1\u00d2\7$\2\2\u00d2*\3\2\2\2\u00d3\u00d5\7$\2\2\u00d4\u00d6"+
		"\n\2\2\2\u00d5\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7"+
		"\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\7$\2\2\u00da,\3\2\2\2\u00db"+
		"\u00dd\t\3\2\2\u00dc\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00dc\3\2"+
		"\2\2\u00de\u00df\3\2\2\2\u00df.\3\2\2\2\u00e0\u00e2\t\4\2\2\u00e1\u00e0"+
		"\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4"+
		"\u00e5\3\2\2\2\u00e5\u00e6\b\30\2\2\u00e6\60\3\2\2\2\7\2\u00a1\u00d7\u00de"+
		"\u00e3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}