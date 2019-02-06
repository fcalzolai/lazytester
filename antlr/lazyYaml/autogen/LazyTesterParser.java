// Generated from LazyTester.g4 by ANTLR 4.7.2

package antlr.lazytester.autogen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LazyTesterParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, NAME=5, IMPORT=6, ELEM=7, STRING=8, WS=9;
	public static final int
		RULE_scenario_file = 0, RULE_paths = 1, RULE_steps = 2, RULE_step_def = 3, 
		RULE_step_name = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"scenario_file", "paths", "steps", "step_def", "step_name"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "','", "':'", "'name'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "NAME", "IMPORT", "ELEM", "STRING", "WS"
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

	@Override
	public String getGrammarFileName() { return "LazyTester.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public LazyTesterParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class Scenario_fileContext extends ParserRuleContext {
		public StepsContext steps() {
			return getRuleContext(StepsContext.class,0);
		}
		public TerminalNode IMPORT() { return getToken(LazyTesterParser.IMPORT, 0); }
		public PathsContext paths() {
			return getRuleContext(PathsContext.class,0);
		}
		public Scenario_fileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scenario_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).enterScenario_file(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).exitScenario_file(this);
		}
	}

	public final Scenario_fileContext scenario_file() throws RecognitionException {
		Scenario_fileContext _localctx = new Scenario_fileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_scenario_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IMPORT) {
				{
				setState(10);
				match(IMPORT);
				setState(11);
				paths();
				}
			}

			setState(14);
			steps();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathsContext extends ParserRuleContext {
		public List<TerminalNode> ELEM() { return getTokens(LazyTesterParser.ELEM); }
		public TerminalNode ELEM(int i) {
			return getToken(LazyTesterParser.ELEM, i);
		}
		public PathsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paths; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).enterPaths(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).exitPaths(this);
		}
	}

	public final PathsContext paths() throws RecognitionException {
		PathsContext _localctx = new PathsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_paths);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ELEM) {
				{
				{
				setState(16);
				match(ELEM);
				}
				}
				setState(21);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StepsContext extends ParserRuleContext {
		public Step_defContext step_def() {
			return getRuleContext(Step_defContext.class,0);
		}
		public List<StepsContext> steps() {
			return getRuleContexts(StepsContext.class);
		}
		public StepsContext steps(int i) {
			return getRuleContext(StepsContext.class,i);
		}
		public StepsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_steps; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).enterSteps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).exitSteps(this);
		}
	}

	public final StepsContext steps() throws RecognitionException {
		StepsContext _localctx = new StepsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_steps);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			match(T__0);
			setState(23);
			step_def();
			setState(24);
			match(T__1);
			setState(29);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(25);
					match(T__2);
					setState(26);
					steps();
					}
					} 
				}
				setState(31);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Step_defContext extends ParserRuleContext {
		public Step_nameContext step_name() {
			return getRuleContext(Step_nameContext.class,0);
		}
		public Step_defContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_step_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).enterStep_def(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).exitStep_def(this);
		}
	}

	public final Step_defContext step_def() throws RecognitionException {
		Step_defContext _localctx = new Step_defContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_step_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(32);
			step_name();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Step_nameContext extends ParserRuleContext {
		public TerminalNode NAME() { return getToken(LazyTesterParser.NAME, 0); }
		public TerminalNode STRING() { return getToken(LazyTesterParser.STRING, 0); }
		public Step_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_step_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).enterStep_name(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof LazyTesterListener ) ((LazyTesterListener)listener).exitStep_name(this);
		}
	}

	public final Step_nameContext step_name() throws RecognitionException {
		Step_nameContext _localctx = new Step_nameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_step_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(NAME);
			setState(35);
			match(T__3);
			setState(36);
			match(STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13)\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\5\2\17\n\2\3\2\3\2\3\3\7\3\24\n\3\f"+
		"\3\16\3\27\13\3\3\4\3\4\3\4\3\4\3\4\7\4\36\n\4\f\4\16\4!\13\4\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\2\2\7\2\4\6\b\n\2\2\2&\2\16\3\2\2\2\4\25\3\2\2\2"+
		"\6\30\3\2\2\2\b\"\3\2\2\2\n$\3\2\2\2\f\r\7\b\2\2\r\17\5\4\3\2\16\f\3\2"+
		"\2\2\16\17\3\2\2\2\17\20\3\2\2\2\20\21\5\6\4\2\21\3\3\2\2\2\22\24\7\t"+
		"\2\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2\2\25\26\3\2\2\2\26\5\3\2"+
		"\2\2\27\25\3\2\2\2\30\31\7\3\2\2\31\32\5\b\5\2\32\37\7\4\2\2\33\34\7\5"+
		"\2\2\34\36\5\6\4\2\35\33\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2"+
		" \7\3\2\2\2!\37\3\2\2\2\"#\5\n\6\2#\t\3\2\2\2$%\7\7\2\2%&\7\6\2\2&\'\7"+
		"\n\2\2\'\13\3\2\2\2\5\16\25\37";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}