package com.lloyds.errorListener;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

import static java.lang.String.format;

public class ExceptionThrowerErrorListener implements ANTLRErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new RuntimeException("Syntax error - line " + line + ":" + charPositionInLine + " " + msg);
    }

    @Override
    public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
        throw new RuntimeException("ReportAmbiguity");
    }

    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        throw new RuntimeException(format("ReportAttemptingFullContext " +
                "dfa[%s] " +
                "startIndex[%d], " +
                "stopIndex[%d] " +
                "conflicting[%s] " +
                "configs[%s]", dfa, startIndex, stopIndex, conflictingAlts, configs));
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        throw new RuntimeException("ReportContextSensitivity");
    }
}