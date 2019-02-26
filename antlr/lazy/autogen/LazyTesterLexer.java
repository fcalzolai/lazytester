// Generated from LazyTester.g4 by ANTLR 4.7.2

package com.lloyds.antlr.lazytester.autogen;

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
		T__9=10, T__10=11, STEPS=12, SCENARIOS=13, NAME=14, LOOP=15, URL=16, OPERATION=17, 
		HTTP_OPS=18, ASSERTIONS=19, STATUS=20, PARAMS=21, HEADERS=22, BODY=23, 
		EXTEND=24, IMPORT=25, INT=26, STRING=27, QSTRING=28, WS=29, NUMBER=30;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "STEPS", "SCENARIOS", "NAME", "LOOP", "URL", "OPERATION", 
			"HTTP_OPS", "ASSERTIONS", "STATUS", "PARAMS", "HEADERS", "BODY", "EXTEND", 
			"IMPORT", "INT", "STRING", "QSTRING", "WS", "ESC", "UNICODE", "HEX", 
			"SAFECODEPOINT", "NUMBER", "EXP"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'['", "']'", "';'", "'{'", "'}'", "'ignoreStepFailures'", 
			"'true'", "'false'", "','", "'null'", "'steps'", "'scenarios'", "'name'", 
			"'loop'", "'url'", "'operation'", null, "'assertions'", "'status'", "'params'", 
			"'headers'", "'body'", "'parent'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"STEPS", "SCENARIOS", "NAME", "LOOP", "URL", "OPERATION", "HTTP_OPS", 
			"ASSERTIONS", "STATUS", "PARAMS", "HEADERS", "BODY", "EXTEND", "IMPORT", 
			"INT", "STRING", "QSTRING", "WS", "NUMBER"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2 \u012c\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\5\23\u00b3\n\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\6\32\u00ea\n\32\r\32\16\32\u00eb\3\32\3\32\3\33\6\33\u00f1\n\33"+
		"\r\33\16\33\u00f2\3\34\6\34\u00f6\n\34\r\34\16\34\u00f7\3\35\3\35\6\35"+
		"\u00fc\n\35\r\35\16\35\u00fd\3\35\3\35\3\36\6\36\u0103\n\36\r\36\16\36"+
		"\u0104\3\36\3\36\3\37\3\37\3\37\5\37\u010c\n\37\3 \3 \3 \3 \3 \3 \3!\3"+
		"!\3\"\3\"\3#\5#\u0119\n#\3#\3#\3#\6#\u011e\n#\r#\16#\u011f\5#\u0122\n"+
		"#\3#\5#\u0125\n#\3$\3$\5$\u0129\n$\3$\3$\2\2%\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26"+
		"+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37=\2?\2A\2C\2E G\2\3\2\f\3"+
		"\2==\3\2\62;\6\2/;C\\aac|\3\2$$\5\2\13\f\16\17\"\"\n\2$$\61\61^^ddhhp"+
		"pttvv\5\2\62;CHch\5\2\2!$$^^\4\2--//\4\2GGgg\2\u0134\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63"+
		"\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2E\3\2\2\2\3"+
		"I\3\2\2\2\5K\3\2\2\2\7M\3\2\2\2\tO\3\2\2\2\13Q\3\2\2\2\rS\3\2\2\2\17U"+
		"\3\2\2\2\21h\3\2\2\2\23m\3\2\2\2\25s\3\2\2\2\27u\3\2\2\2\31z\3\2\2\2\33"+
		"\u0080\3\2\2\2\35\u008a\3\2\2\2\37\u008f\3\2\2\2!\u0094\3\2\2\2#\u0098"+
		"\3\2\2\2%\u00b2\3\2\2\2\'\u00b4\3\2\2\2)\u00bf\3\2\2\2+\u00c6\3\2\2\2"+
		"-\u00cd\3\2\2\2/\u00d5\3\2\2\2\61\u00da\3\2\2\2\63\u00e1\3\2\2\2\65\u00f0"+
		"\3\2\2\2\67\u00f5\3\2\2\29\u00f9\3\2\2\2;\u0102\3\2\2\2=\u0108\3\2\2\2"+
		"?\u010d\3\2\2\2A\u0113\3\2\2\2C\u0115\3\2\2\2E\u0118\3\2\2\2G\u0126\3"+
		"\2\2\2IJ\7<\2\2J\4\3\2\2\2KL\7]\2\2L\6\3\2\2\2MN\7_\2\2N\b\3\2\2\2OP\7"+
		"=\2\2P\n\3\2\2\2QR\7}\2\2R\f\3\2\2\2ST\7\177\2\2T\16\3\2\2\2UV\7k\2\2"+
		"VW\7i\2\2WX\7p\2\2XY\7q\2\2YZ\7t\2\2Z[\7g\2\2[\\\7U\2\2\\]\7v\2\2]^\7"+
		"g\2\2^_\7r\2\2_`\7H\2\2`a\7c\2\2ab\7k\2\2bc\7n\2\2cd\7w\2\2de\7t\2\2e"+
		"f\7g\2\2fg\7u\2\2g\20\3\2\2\2hi\7v\2\2ij\7t\2\2jk\7w\2\2kl\7g\2\2l\22"+
		"\3\2\2\2mn\7h\2\2no\7c\2\2op\7n\2\2pq\7u\2\2qr\7g\2\2r\24\3\2\2\2st\7"+
		".\2\2t\26\3\2\2\2uv\7p\2\2vw\7w\2\2wx\7n\2\2xy\7n\2\2y\30\3\2\2\2z{\7"+
		"u\2\2{|\7v\2\2|}\7g\2\2}~\7r\2\2~\177\7u\2\2\177\32\3\2\2\2\u0080\u0081"+
		"\7u\2\2\u0081\u0082\7e\2\2\u0082\u0083\7g\2\2\u0083\u0084\7p\2\2\u0084"+
		"\u0085\7c\2\2\u0085\u0086\7t\2\2\u0086\u0087\7k\2\2\u0087\u0088\7q\2\2"+
		"\u0088\u0089\7u\2\2\u0089\34\3\2\2\2\u008a\u008b\7p\2\2\u008b\u008c\7"+
		"c\2\2\u008c\u008d\7o\2\2\u008d\u008e\7g\2\2\u008e\36\3\2\2\2\u008f\u0090"+
		"\7n\2\2\u0090\u0091\7q\2\2\u0091\u0092\7q\2\2\u0092\u0093\7r\2\2\u0093"+
		" \3\2\2\2\u0094\u0095\7w\2\2\u0095\u0096\7t\2\2\u0096\u0097\7n\2\2\u0097"+
		"\"\3\2\2\2\u0098\u0099\7q\2\2\u0099\u009a\7r\2\2\u009a\u009b\7g\2\2\u009b"+
		"\u009c\7t\2\2\u009c\u009d\7c\2\2\u009d\u009e\7v\2\2\u009e\u009f\7k\2\2"+
		"\u009f\u00a0\7q\2\2\u00a0\u00a1\7p\2\2\u00a1$\3\2\2\2\u00a2\u00a3\7I\2"+
		"\2\u00a3\u00a4\7G\2\2\u00a4\u00b3\7V\2\2\u00a5\u00a6\7R\2\2\u00a6\u00a7"+
		"\7Q\2\2\u00a7\u00a8\7U\2\2\u00a8\u00b3\7V\2\2\u00a9\u00aa\7R\2\2\u00aa"+
		"\u00ab\7W\2\2\u00ab\u00b3\7V\2\2\u00ac\u00ad\7F\2\2\u00ad\u00ae\7G\2\2"+
		"\u00ae\u00af\7N\2\2\u00af\u00b0\7G\2\2\u00b0\u00b1\7V\2\2\u00b1\u00b3"+
		"\7G\2\2\u00b2\u00a2\3\2\2\2\u00b2\u00a5\3\2\2\2\u00b2\u00a9\3\2\2\2\u00b2"+
		"\u00ac\3\2\2\2\u00b3&\3\2\2\2\u00b4\u00b5\7c\2\2\u00b5\u00b6\7u\2\2\u00b6"+
		"\u00b7\7u\2\2\u00b7\u00b8\7g\2\2\u00b8\u00b9\7t\2\2\u00b9\u00ba\7v\2\2"+
		"\u00ba\u00bb\7k\2\2\u00bb\u00bc\7q\2\2\u00bc\u00bd\7p\2\2\u00bd\u00be"+
		"\7u\2\2\u00be(\3\2\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1\7v\2\2\u00c1\u00c2"+
		"\7c\2\2\u00c2\u00c3\7v\2\2\u00c3\u00c4\7w\2\2\u00c4\u00c5\7u\2\2\u00c5"+
		"*\3\2\2\2\u00c6\u00c7\7r\2\2\u00c7\u00c8\7c\2\2\u00c8\u00c9\7t\2\2\u00c9"+
		"\u00ca\7c\2\2\u00ca\u00cb\7o\2\2\u00cb\u00cc\7u\2\2\u00cc,\3\2\2\2\u00cd"+
		"\u00ce\7j\2\2\u00ce\u00cf\7g\2\2\u00cf\u00d0\7c\2\2\u00d0\u00d1\7f\2\2"+
		"\u00d1\u00d2\7g\2\2\u00d2\u00d3\7t\2\2\u00d3\u00d4\7u\2\2\u00d4.\3\2\2"+
		"\2\u00d5\u00d6\7d\2\2\u00d6\u00d7\7q\2\2\u00d7\u00d8\7f\2\2\u00d8\u00d9"+
		"\7{\2\2\u00d9\60\3\2\2\2\u00da\u00db\7r\2\2\u00db\u00dc\7c\2\2\u00dc\u00dd"+
		"\7t\2\2\u00dd\u00de\7g\2\2\u00de\u00df\7p\2\2\u00df\u00e0\7v\2\2\u00e0"+
		"\62\3\2\2\2\u00e1\u00e2\7k\2\2\u00e2\u00e3\7o\2\2\u00e3\u00e4\7r\2\2\u00e4"+
		"\u00e5\7q\2\2\u00e5\u00e6\7t\2\2\u00e6\u00e7\7v\2\2\u00e7\u00e9\3\2\2"+
		"\2\u00e8\u00ea\n\2\2\2\u00e9\u00e8\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00e9"+
		"\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00ee\7=\2\2\u00ee"+
		"\64\3\2\2\2\u00ef\u00f1\t\3\2\2\u00f0\u00ef\3\2\2\2\u00f1\u00f2\3\2\2"+
		"\2\u00f2\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\66\3\2\2\2\u00f4\u00f6"+
		"\t\4\2\2\u00f5\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f7"+
		"\u00f8\3\2\2\2\u00f88\3\2\2\2\u00f9\u00fb\7$\2\2\u00fa\u00fc\n\5\2\2\u00fb"+
		"\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe\3\2"+
		"\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\7$\2\2\u0100:\3\2\2\2\u0101\u0103"+
		"\t\6\2\2\u0102\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0102\3\2\2\2\u0104"+
		"\u0105\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0107\b\36\2\2\u0107<\3\2\2\2"+
		"\u0108\u010b\7^\2\2\u0109\u010c\t\7\2\2\u010a\u010c\5? \2\u010b\u0109"+
		"\3\2\2\2\u010b\u010a\3\2\2\2\u010c>\3\2\2\2\u010d\u010e\7w\2\2\u010e\u010f"+
		"\5A!\2\u010f\u0110\5A!\2\u0110\u0111\5A!\2\u0111\u0112\5A!\2\u0112@\3"+
		"\2\2\2\u0113\u0114\t\b\2\2\u0114B\3\2\2\2\u0115\u0116\n\t\2\2\u0116D\3"+
		"\2\2\2\u0117\u0119\t\n\2\2\u0118\u0117\3\2\2\2\u0118\u0119\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a\u0121\5\65\33\2\u011b\u011d\7\60\2\2\u011c\u011e"+
		"\t\3\2\2\u011d\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u011d\3\2\2\2\u011f"+
		"\u0120\3\2\2\2\u0120\u0122\3\2\2\2\u0121\u011b\3\2\2\2\u0121\u0122\3\2"+
		"\2\2\u0122\u0124\3\2\2\2\u0123\u0125\5G$\2\u0124\u0123\3\2\2\2\u0124\u0125"+
		"\3\2\2\2\u0125F\3\2\2\2\u0126\u0128\t\13\2\2\u0127\u0129\t\n\2\2\u0128"+
		"\u0127\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\5\65"+
		"\33\2\u012bH\3\2\2\2\17\2\u00b2\u00eb\u00f2\u00f7\u00fd\u0104\u010b\u0118"+
		"\u011f\u0121\u0124\u0128\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}