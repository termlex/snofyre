package uk.nhs.cfh.dsp.snomed.expression.model.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.nhs.cfh.dsp.snomed.expression.model.*;
import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

/**
 * A concrete implementation of a {@link SnomedExpressionFactory}.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 06/03/2011 at 23:34
 */
public class SnomedExpressionFactoryImpl implements SnomedExpressionFactory {

    private static Log logger = LogFactory.getLog(SnomedExpressionFactoryImpl.class);

    /**
     * A factory method that returns a {@link CloseToUserExpression}
     * @param concept the factory that gets wrapped in the expression as focus concept
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression(SnomedConcept concept){
        return getCloseToUserExpression(Collections.singleton(concept));
    }

    /**
     * A factory method that returns a {@link CloseToUserExpression}
     * @param focusConcepts the collection of concepts that are the focus concepts of this expression
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression(Collection<SnomedConcept> focusConcepts){
        return getCloseToUserExpression(focusConcepts, UUID.randomUUID());
    }

    /**
     * A factory method that returns a {@link CloseToUserExpression}
     * @param focusConcepts focusConcepts the collection of concepts that are the focus concepts of this expression
     * @param uuid the uuid of this expression
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression(Collection<SnomedConcept> focusConcepts, UUID uuid){
        return new CloseToUserExpressionImpl(new HashSet<SnomedConcept>(focusConcepts), uuid);
    }

    /**
     * A factory method that returns a {@link UnionExpression}
     * @param containedExpressions the expression that make up this union object
     * @param uuid the uuid of this expression
     * @return the union expression
     */
    public UnionExpression getUnionExpression(Collection<Expression> containedExpressions, UUID uuid){
        return new UnionExpressionImpl(uuid, containedExpressions);
    }

    /**
     * A factory method that returns a {@link UnionExpression}
     * @param containedExpression the expression that make up this union object
     * @return the union expression
     */
    public UnionExpression getUnionExpression(Collection<Expression> containedExpression){
        return getUnionExpression(containedExpression, UUID.randomUUID());
    }

    /**
     * A factory method that returns a {@link IntersectionExpression}
     * @param containedExpressions the expression that make up this intersection object
     * @param uuid
     * @return the intersection expression
     */
    public IntersectionExpression getIntersectionExpression(Collection<Expression> containedExpressions, UUID uuid){
        return new IntersectionExpressionImpl(uuid, containedExpressions);
    }

    /**
     * A factory method that returns a {@link IntersectionExpression}
     * @param containedExpression the expression that make up this intersection object
     * @return the intersection expression
     */
    public IntersectionExpression getIntersectionExpression(Collection<Expression> containedExpression){
        return getIntersectionExpression(containedExpression, UUID.randomUUID());
    }

    /**
     * A factory method that returns a {@link NormalFormExpression}
     * @param concept the concept that is the focus concept of this normal form expression
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression(SnomedConcept concept){
        return getNormalFormExpression(Collections.singleton(concept));
    }

    /**
     * A factory method that returns a {@link NormalFormExpression}
     * @param focusConcepts the collection of concepts that are the focus concepts of this expression
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression(Collection<SnomedConcept> focusConcepts){
        return getNormalFormExpression(focusConcepts, UUID.randomUUID());
    }

    /**
     * A factory method that returns a {@link NormalFormExpression}
     * @param focusConcepts the collection of concepts that are the focus concepts of this expression
     * @param uuid the uuid of this expression
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression(Collection<SnomedConcept> focusConcepts, UUID uuid){
        return new NormalFormExpressionImpl(uuid, focusConcepts);
    }
}
