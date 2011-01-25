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
package uk.nhs.cfh.dsp.yasb.indexgenerator.engine;

import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * A generic synonym engine
 * Written by @author jay
 * Created on Jan 12, 2009 at 5:55:20 PM.
 */
public interface SynonymEngine {

	/**
	 * Gets the synonyms.
	 *
	 * @param term the term
	 * @return the synonyms
	 */
	Collection<String> getSynonyms(String term);
}
