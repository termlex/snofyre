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
package uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.impl;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.parser.SnomedExpressionLexer;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.parser.SnomedExpressionParser;
import uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.parser.SnomedExpressionTree;
import uk.nhs.cfh.dsp.snomed.dao.TerminologyConceptDAO;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.impl.AbstractExpressionWithFocusConcepts;

// TODO: Auto-generated Javadoc
/**
 * A concrete implementation of an {@link uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter}.
 *
 * @scr.component immediate=“true”
 * @scr.service
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 8, 2010 at 11:34:35 AM
 */
public class ExpressionCompositionalGrammarConverterImpl implements ExpressionCompositionalGrammarConverter {

    /** The terminology concept dao. */
    private TerminologyConceptDAO terminologyConceptDAO;
    
    /** The logger. */
    private static Log logger = LogFactory.getLog(ExpressionCompositionalGrammarConverterImpl.class);

    /**
     * Instantiates a new expression compositional grammar converter impl.
     *
     * @param terminologyConceptDAO the terminology concept dao
     */
    public ExpressionCompositionalGrammarConverterImpl(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }

    /**
     * empty constructor for IOC.
     */
    public ExpressionCompositionalGrammarConverterImpl() {
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter#getNormalForm(java.lang.String)
     */
    public NormalFormExpression getNormalForm(String input) {
        return (NormalFormExpression) getAbstractExpressionWithFocusConcepts(input, false);
    }

    /**
     * Gets the abstract expression with focus concepts.
     *
     * @param input the input
     * @param asCloseToUserForm the as close to user form
     * @return the abstract expression with focus concepts
     */
    private AbstractExpressionWithFocusConcepts getAbstractExpressionWithFocusConcepts(String input, boolean asCloseToUserForm){

        if (input != null)
        {
            // create lexer with input
            SnomedExpressionLexer lexer = new SnomedExpressionLexer(new ANTLRStringStream(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SnomedExpressionParser parser = new SnomedExpressionParser(tokens);
            SnomedExpressionParser.expression_return parserReturn = null;
            try
            {
                parserReturn = parser.expression();
                // Pick up the generic tree
                //
                CommonTree t = (CommonTree)parserReturn.getTree();
                if (logger.isDebugEnabled()) {
                    logger.debug("t.toStringTree() = " + t.toStringTree());
                }

                CommonTreeNodeStream nodes = new CommonTreeNodeStream(t);
                nodes.setTokenStream(tokens);
                // pass nodes  to
                SnomedExpressionTree treeWalker = new SnomedExpressionTree(nodes, terminologyConceptDAO);
                treeWalker.setGetCloseToUserForm(asCloseToUserForm);
                return treeWalker.expression();
            }
            catch (RecognitionException e) {
                logger.warn("Error parsing string passed. Nested exception is : " + e.fillInStackTrace());
                return null;
            }
        }
        else
        {
            throw new IllegalArgumentException("Input string passed can not be null.");
        }
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter#getCloseToUserForm(java.lang.String)
     */
    public CloseToUserExpression getCloseToUserForm(String input) {
        return (CloseToUserExpression) getAbstractExpressionWithFocusConcepts(input, true);
    }

    /* (non-Javadoc)
     * @see uk.nhs.cfh.dsp.snomed.converters.compositionalgrammar.ExpressionCompositionalGrammarConverter#getCompositionalForm(uk.nhs.cfh.dsp.snomed.expression.model.Expression)
     */
    public String getCompositionalForm(Expression expression) {
        if(expression instanceof CloseToUserExpression)
        {
            CloseToUserExpression closeToUserExpression = (CloseToUserExpression) expression;
            return closeToUserExpression.getCompositionalGrammarForm();
        }
        else if(expression instanceof NormalFormExpression)
        {
            NormalFormExpression normalFormExpression = (NormalFormExpression) expression;
            return normalFormExpression.getCanonicalStringForm();
        }
        else
        {
            throw new IllegalArgumentException("Expression passed needs to be either a Close to user expression or" +
                    "a normal form expression.");
        }
    }

    /**
     * Sets the terminology concept dao.
     *
     * @param terminologyConceptDAO the new terminology concept dao
     */
    public synchronized void setTerminologyConceptDAO(TerminologyConceptDAO terminologyConceptDAO) {
        this.terminologyConceptDAO = terminologyConceptDAO;
    }
}
