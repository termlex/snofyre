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
package uk.nhs.cfh.dsp.yasb.indexgenerator.analyser;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import uk.nhs.cfh.dsp.yasb.indexgenerator.engine.SynonymEngine;
import uk.nhs.cfh.dsp.yasb.indexgenerator.filter.SynonymFilter;

import java.io.Reader;

// TODO: Auto-generated Javadoc
/**
 * A custom analyser for SNOMED synonyms based on 'Lucene in Action' example
 * Written by @author jay
 * Created on Jan 12, 2009 at 6:13:00 PM.
 */
public class SynonymAnalyser extends Analyzer {

	/** The synonym engine. */
	private SynonymEngine synonymEngine;
	
	/**
	 * Instantiates a new synonym analyser.
	 *
	 * @param synonymEngine the synonym engine
	 */
	public SynonymAnalyser(SynonymEngine synonymEngine) {
		this.synonymEngine = synonymEngine;
	}

	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Analyzer#tokenStream(java.lang.String, java.io.Reader)
	 */
	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {

		TokenStream stream = new SynonymFilter(new StopFilter(new LowerCaseFilter(new StandardFilter(new StandardTokenizer(reader))),
				StandardAnalyzer.STOP_WORDS),
				synonymEngine);
		
		return stream;
	}

}
