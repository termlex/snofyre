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
/**
 * 
 */
package uk.nhs.cfh.dsp.yasb.indexgenerator.filter;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import uk.nhs.cfh.dsp.yasb.indexgenerator.engine.SynonymEngine;

import java.io.IOException;
import java.util.Collection;
import java.util.Stack;


// TODO: Auto-generated Javadoc
/**
 * A custom filter to handle synonyms in SNOMED based on the example in 'Lucene in Action'
 * Written by @author jay
 * Created on Jan 12, 2009 at 5:52:10 PM.
 */
public class SynonymFilter extends TokenFilter {

	/** The Constant TOKEN_TYPE_SYNONYM. */
	public static final String TOKEN_TYPE_SYNONYM = "SYNONYM";
	
	/** The synonymn stack. */
	private Stack<Token> synonymnStack;
	
	/** The synonym engine. */
	private SynonymEngine synonymEngine;
	
	/**
	 * Instantiates a new synonym filter.
	 *
	 * @param input the input
	 * @param synonymEngine the synonym engine
	 */
	public SynonymFilter(TokenStream input, SynonymEngine synonymEngine) {
		super(input);
		this.synonymEngine = synonymEngine;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.TokenStream#next()
	 */
	public Token next() throws IOException{
		if(synonymnStack.size()>0)
		{
			return synonymnStack.pop();
		}
		
		Token token = input.next();
		if(token == null)
		{
			return null;
		}
		
		addAliasToStack(token);
		
		return token;
	}

	/**
	 * Adds the alias to stack.
	 *
	 * @param token the token
	 */
	private void addAliasToStack(Token token) {
		
		// get synonyms for token text
		Collection<String> synonyms = synonymEngine.getSynonyms(new String(token.termBuffer()));
		
		if(synonyms == null)
		{
			return ;
		}
		
		for(String synonym : synonyms)
		{
			// lucene java docs advise using a constructor that uses null text, hence modified from original example
			Token synToken = new Token(token.startOffset(), token.endOffset(), TOKEN_TYPE_SYNONYM);
			// now set text
			synToken.setTermText(synonym);
			// set incerment position
			synToken.setPositionIncrement(0);
			// add to stack
			synonymnStack.add(synToken);
		}
	}
}
