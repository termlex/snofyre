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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint.impl;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.*;
import uk.nhs.cfh.dsp.srth.query.model.om.error.InvalidConstraintValueException;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * An abstract implementation of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint} that
 * uses generics to represent the value of the constraints.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 7, 2009 at 2:49:12 PM
 * <br>.
 */

@Embeddable
@Entity(name = "AbstractConstraint")
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CONSTRAINT_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractConstraint implements Constraint {

	/** The type. */
	private ConstraintType type;
	/** The value. */
	private ConstraintValue<?> value;
	/** The dimension vocabulary. */
	private ConstraintDimensionVocabulary dimensionVocabulary;	
	/** The name. */
	private String name;
    
    /** The id. */
    private long id;
	
	/**
	 * Instantiates a new abstract constraint.
	 */
	public AbstractConstraint() {
        // empty no arguments constructor for persistence
	}

    /**
     * Instantiates a new abstract constraint.
     * 
     * @param type the type
     * @param dimensionVocabulary the dimension vocabulary
     */
    protected AbstractConstraint(ConstraintType type,
                                 ConstraintDimensionVocabulary dimensionVocabulary) {
        this.type = type;
        this.dimensionVocabulary = dimensionVocabulary;
    }

    /**
     * Instantiates a new abstract constraint.
     * 
     * @param type the type
     * @param value the value
     * @param dimensionVocabulary the dimension vocabulary
     */
    protected AbstractConstraint(ConstraintType type, ConstraintValue<?> value,
			ConstraintDimensionVocabulary dimensionVocabulary) {
		this.type = type;
		this.value = value;
		this.dimensionVocabulary = dimensionVocabulary;
	}
	
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
    @Column(name = "name")
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the dimension vocabulary.
	 * 
	 * @return the dimension vocabulary
	 */
    @Enumerated(EnumType.STRING)
	public ConstraintDimensionVocabulary getDimensionVocabulary() {
		return dimensionVocabulary;
	}

	/**
	 * Sets the dimension vocabulary.
	 * 
	 * @param dimensionVocabulary the new dimension vocabulary
	 */
	public void setDimensionVocabulary(
			ConstraintDimensionVocabulary dimensionVocabulary) {
		this.dimensionVocabulary = dimensionVocabulary;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
    @Enumerated(EnumType.STRING)
	public ConstraintType getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(ConstraintType type) {
		this.type = type;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ConstraintValue.class)
	public ConstraintValue<?> getValue() {
		return value;
	}
	
	/**
	 * Sets the value.
	 * 
	 * @param value the new value
	 * 
	 * @throws InvalidConstraintValueException the invalid constraint value exception
	 */
	public void setValue(ConstraintValue<?> value) throws InvalidConstraintValueException{

        // check if value is permitted type
        if(permittedValue(value))
        {
            this.value = value;
        }
        else
        {
            throw new IllegalArgumentException("Value passed is not allowed for constraint type.");
        }
	}

    /**
     * Permitted value.
     * 
     * @param value the value
     * 
     * @return true, if successful
     */
    @Transient
    protected abstract boolean permittedValue(ConstraintValue<?> value);

    /**
     * Gets the id.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }
}
