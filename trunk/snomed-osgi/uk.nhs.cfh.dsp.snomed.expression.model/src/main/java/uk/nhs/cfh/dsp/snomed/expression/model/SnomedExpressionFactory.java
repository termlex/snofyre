package uk.nhs.cfh.dsp.snomed.expression.model;

import uk.nhs.cfh.dsp.snomed.objectmodel.SnomedConcept;

import java.util.Collection;
import java.util.UUID;

/**
 * A factory that creates {@link Expression} objects.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on 06/03/2011 at 23:33
 */
public interface SnomedExpressionFactory {

     /**
     * A factory method that returns a {@link CloseToUserExpression}
     * @param concept the factory that gets wrapped in the expression as focus concept
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression(SnomedConcept concept);

    /**
     * A factory method that returns a {@link CloseToUserExpression}
     * @param focusConcepts the collection of concepts that are the focus concepts of this expression
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression(Collection<SnomedConcept> focusConcepts);

    /**
     * A factory method that returns a {@link CloseToUserExpression}
     * @param focusConcepts focusConcepts the collection of concepts that are the focus concepts of this expression
     * @param uuid the uuid of this expression
     * @return the close to user expression
     */
    public CloseToUserExpression getCloseToUserExpression(Collection<SnomedConcept> focusConcepts, UUID uuid);

    /**
     * A factory method that returns a {@link UnionExpression}
     * @param containedExpressions the expression that make up this union object
     * @param uuid the uuid of this expression
     * @return the union expression
     */
    public UnionExpression getUnionExpression(Collection<Expression> containedExpressions, UUID uuid);

    /**
     * A factory method that returns a {@link UnionExpression}
     * @param containedExpression the expression that make up this union object
     * @return the union expression
     */
    public UnionExpression getUnionExpression(Collection<Expression> containedExpression);

    /**
     * A factory method that returns a {@link IntersectionExpression}
     * @param containedExpressions the expression that make up this intersection object
     * @param uuid
     * @return the intersection expression
     */
    public IntersectionExpression getIntersectionExpression(Collection<Expression> containedExpressions, UUID uuid);

    /**
     * A factory method that returns a {@link IntersectionExpression}
     * @param containedExpression the expression that make up this intersection object
     * @return the intersection expression
     */
    public IntersectionExpression getIntersectionExpression(Collection<Expression> containedExpression);

    /**
     * A factory method that returns a {@link NormalFormExpression}
     * @param concept the concept that is the focus concept of this normal form expression
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression(SnomedConcept concept);

    /**
     * A factory method that returns a {@link NormalFormExpression}
     * @param focusConcepts the collection of concepts that are the focus concepts of this expression
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression(Collection<SnomedConcept> focusConcepts);

    /**
     * A factory method that returns a {@link NormalFormExpression}
     * @param focusConcepts the collection of concepts that are the focus concepts of this expression
     * @param uuid the uuid of this expression
     * @return the normal form expression
     */
    public NormalFormExpression getNormalFormExpression(Collection<SnomedConcept> focusConcepts, UUID uuid);
}
