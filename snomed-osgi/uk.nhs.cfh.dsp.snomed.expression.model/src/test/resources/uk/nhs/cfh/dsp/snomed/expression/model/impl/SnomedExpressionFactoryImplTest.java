package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import junit.framework.TestCase;
import uk.nhs.cfh.dsp.snomed.expression.model.CloseToUserExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.NormalFormExpression;
import uk.nhs.cfh.dsp.snomed.expression.model.SnomedExpressionFactory;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;
import uk.nhs.cfh.dsp.snomed.objectmodel.impl.SnomedConceptImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

/**
 * A test class for {@link SnomedExpressionFactoryImpl}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 08/03/2011 at 22:14
 */
public class SnomedExpressionFactoryImplTest extends TestCase {

    private SnomedConcept concept;
    private SnomedExpressionFactory expressionFactory;
    private Collection<SnomedConcept> concepts;

    public void setUp() throws Exception {
        concept = new SnomedConceptImpl("12345", "Test_Concept");
        assertNotNull("Concept can not be null", concept);
        expressionFactory = new SnomedExpressionFactoryImpl();
        assertNotNull("Expression factory can not be null", expressionFactory);
        concepts = new HashSet<SnomedConcept>(2);
        SnomedConcept concept2 = new SnomedConceptImpl("9999", "Test_Concept_2");
        concepts.add(concept);
        concepts.add(concept2);
        assertEquals("Concepts collection must contain 2 entries", 2, concepts.size());
    }

    public void testGetCloseToUserExpression() throws Exception {
        CloseToUserExpression ctu = expressionFactory.getCloseToUserExpression(concept);
        assertNotNull("CTU can not be null", ctu);
        assertNotNull("CTU must have a UUID", ctu.getUuid());
        assertEquals("CTU must have a focus concept with id = " + concept.getConceptID(), concept.getConceptID(), ctu.getSingletonFocusConcept().getConceptID());
    }

    public void testGetCloseToUserExpression2() throws Exception {
        CloseToUserExpression ctu = expressionFactory.getCloseToUserExpression(Collections.singleton(concept));
        assertNotNull("CTU can not be null", ctu);
        assertNotNull("CTU must have a UUID", ctu.getUuid());
        assertEquals("CTU must have a focus concept with id = "+concept.getConceptID(), concept.getConceptID(), ctu.getSingletonFocusConcept().getConceptID());
    }

    public void testGetCloseToUserExpression3() throws Exception {

        UUID uuid = UUID.randomUUID();
        CloseToUserExpression ctu = expressionFactory.getCloseToUserExpression(Collections.singleton(concept), uuid);
        assertNotNull("CTU can not be null", ctu);
        assertNotNull("CTU must have a UUID", ctu.getUuid());
        assertEquals("CTU must have a focus concept with id = "+concept.getConceptID(), concept.getConceptID(), ctu.getSingletonFocusConcept().getConceptID());
        assertEquals("UUID must be : "+uuid.toString(), uuid, ctu.getUuid());
    }

    public void testGetNormalFormExpression() throws Exception {
        NormalFormExpression nfe = expressionFactory.getNormalFormExpression(concept);
        assertNotNull("NFE can not be null", nfe);
        assertNotNull("NFE must have a UUID", nfe.getUuid());
        assertEquals("NFE must have a focus concept with id = "+concept.getConceptID(), concept.getConceptID(), nfe.getSingletonFocusConcept().getConceptID());
    }

    public void testGetNormalFormExpression2() throws Exception {
        NormalFormExpression nfe = expressionFactory.getNormalFormExpression(Collections.singleton(concept));
        assertNotNull("NFE can not be null", nfe);
        assertNotNull("NFE must have a UUID", nfe.getUuid());
        assertEquals("NFE must have a focus concept with id = "+concept.getConceptID(), concept.getConceptID(), nfe.getSingletonFocusConcept().getConceptID());
    }

    public void testGetNormalFormExpression3() throws Exception {

        UUID uuid = UUID.randomUUID();
        NormalFormExpression nfe = expressionFactory.getNormalFormExpression(Collections.singleton(concept), uuid);
        assertNotNull("NFE can not be null", nfe);
        assertNotNull("NFE must have a UUID", nfe.getUuid());
        assertEquals("NFE must have a focus concept with id = "+concept.getConceptID(), concept.getConceptID(), nfe.getSingletonFocusConcept().getConceptID());
        assertEquals("UUID must be : "+uuid.toString(), uuid, nfe.getUuid());
    }
}
