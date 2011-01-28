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
package uk.nhs.cfh.dsp.srth.information.model.impl.om.clinical.entity;

import org.hibernate.annotations.Index;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpression;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.ClinicalExpressionType;
import uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity.BoundClinicalEntity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link BoundClinicalEntity}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 26, 2009 at 6:45:49 PM
 */

@MappedSuperclass
@Embeddable
@DiscriminatorValue("Bound_Clinical_Entity")
//@TypeDef(name = "uuid", typeClass = uk.nhs.cfh.dsp.srth.information.model.impl.orm.HexBasedUUIDUserType.class)
public abstract class AbstractBoundClinicalEntity extends AbstractClinicalEntity implements BoundClinicalEntity {

	/** The expression. */
	private ClinicalExpression expression;
	
	/** The expression type. */
	private ClinicalExpressionType expressionType;
    
    /** The bound expression canonical form. */
    private String boundExpressionCanonicalForm;
	
	/**
	 * Instantiates a new abstract bound clinical entity.
	 */
	public AbstractBoundClinicalEntity(){
		// empty constructor for persistence
	}

	/**
	 * Instantiates a new abstract bound clinical entity.
	 * 
	 * @param name the name
	 * @param instantiationTime the instantiation time
	 * @param clinicallyRelevantTime the clinically relevant time
	 * @param type the type
	 * @param uuid the uuid
	 * @param expression the expression
	 * @param expressionType the expression type
	 */
	public AbstractBoundClinicalEntity(String name, Calendar instantiationTime,
			Calendar clinicallyRelevantTime, Type type, UUID uuid,
			ClinicalExpression expression, ClinicalExpressionType expressionType) {
		super(name, instantiationTime, clinicallyRelevantTime, type, uuid);
		this.expression = expression;
		this.expressionType = expressionType;
	}

    /**
     * Gets the expression.
     * 
     * @return the expression
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ClinicalExpressionImpl.class)
	public ClinicalExpression getExpression() {
		return expression;
	}
	
	/**
	 * Sets the expression.
	 * 
	 * @param expression the new expression
	 */
	public void setExpression(ClinicalExpression expression) {
		this.expression = expression;
	}

    /**
     * Gets the expression type.
     * 
     * @return the expression type
     */
    @Enumerated(EnumType.STRING)
	public ClinicalExpressionType getExpressionType() {
		return expressionType;
	}
	
	/**
	 * Sets the expression type.
	 * 
	 * @param expressionType the new expression type
	 */
	public void setExpressionType(ClinicalExpressionType expressionType) {
		this.expressionType = expressionType;
	}

    /**
     * Gets the bound expression canonical form.
     * 
     * @return the bound expression canonical form
     */
    @Column(name = "expression_canonical_form", columnDefinition = "VARCHAR(2560)")
    @Index(name = "IDX_CANONICAL_FORM")
    public String getBoundExpressionCanonicalForm() {
        return boundExpressionCanonicalForm;
    }

    /**
     * Sets the bound expression canonical form.
     * 
     * @param boundExpressionCanonicalForm the new bound expression canonical form
     */
    public void setBoundExpressionCanonicalForm(String boundExpressionCanonicalForm) {
        this.boundExpressionCanonicalForm = boundExpressionCanonicalForm;
    }
}
