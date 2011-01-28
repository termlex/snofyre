/**
 * Crown Copyright (C) 2008 - 2011
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.parser;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.RecognizerSharedState;

// TODO: Auto-generated Javadoc
/**
 * An abstract {@link org.antlr.runtime.Lexer} class that contains the high level error handling
 * and other code. This keeps the actual grammar file clean as suggested by the antlr3maven
 * archetype configuration.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 6, 2010 at 1:57:50 PM
 */
public abstract class AbstractSnomedExpressionLexer extends Lexer{
    /**
     * Default constructor for the lexer, when you do not yet know what
     * the character stream to be provided is.
     */
    public AbstractSnomedExpressionLexer() {
    }

    /**
     * Create a new instance of the lexer using the given character stream as
     * the input to lex into tokens.
     *
     * @param input A valid character stream that contains the ruleSrc code you
     *              wish to compile (or lex at least)
     */
    public AbstractSnomedExpressionLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }

    /**
     * Internal constructor for ANTLR - do not use.
     *
     * @param input The character stream we are going to lex
     * @param state The shared state object, shared between all lexer comonents
     */
    public AbstractSnomedExpressionLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
}