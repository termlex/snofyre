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

import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;

// TODO: Auto-generated Javadoc
/**
 * An abstract {@link org.antlr.runtime.Parser} class that contains the high level error handling
 * and other code. This keeps the actual grammar file clean as suggested by the antlr3maven
 * archetype configuration.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 6, 2010 at 1:52:33 PM
 */
public abstract class AbstractSnomedExpressionParser extends Parser{
    /**
     * Create a new parser instance, pre-supplying the input token stream.
     *
     * @param input The stream of tokens that will be pulled from the lexer
     */
    protected AbstractSnomedExpressionParser(TokenStream input) {
        super(input);
    }

    /**
     * Create a new parser instance, pre-supplying the input token stream
     * and the shared state.
     *
     * This is only used when a grammar is imported into another grammar, but
     * we must supply this constructor to satisfy the super class contract.
     *
     * @param input The stream of tokesn that will be pulled from the lexer
     * @param state The shared state object created by an interconnectd grammar
     */
    protected AbstractSnomedExpressionParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }


    /**
     * Creates the error/warning message that we need to show users/IDEs when
     * ANTLR has found a parsing error, has recovered from it and is now
     * telling us that a parsing exception occurred.
     *
     * @param tokenNames token names as known by ANTLR (which we ignore)
     * @param e The exception that was thrown
     */
    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {

        // This is just a place holder that shows how to override this method
        //
        super.displayRecognitionError(tokenNames, e);
    }
}
