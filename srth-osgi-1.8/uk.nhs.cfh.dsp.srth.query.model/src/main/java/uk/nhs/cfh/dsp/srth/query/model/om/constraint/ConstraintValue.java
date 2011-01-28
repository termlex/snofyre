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
package uk.nhs.cfh.dsp.srth.query.model.om.constraint;

import org.hibernate.annotations.Type;
import uk.nhs.cfh.dsp.snomed.expression.model.Expression;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A class that uses generics to represent values of a {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.Constraint}.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jun 20, 2009 at 11:44:46 AM
 * <br>
 *
 * @param <V> the
 */
@Embeddable
@Entity(name = "ConstraintValue")
@Table(name = "CONSTRAINT_VALUES")
public class ConstraintValue<V> implements Serializable{
	
	/** The value. */
	private V value;
    
    /** The id. */
    private long id;
	
	/**
	 * Instantiates a new constraint value.
	 */
	public ConstraintValue() {
		
	}
	
	/**
	 * Instantiates a new constraint value.
	 * 
	 * @param value the value
	 */
	public ConstraintValue(V value) {
		this.value = value;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
    @Type(type = "blob")
    @Column(name = "value", columnDefinition = "BLOB")
	public V getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param v the new value
	 */
	public void setValue(V v) {
		this.value = v;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    @Transient
    public int hashCode() {
        return toString().hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    @Transient
	public String toString() {

		if(getValue() != null)
		{
			if(value instanceof String ||
					value instanceof Number )
			{
				return String.valueOf(value);
			}
			else if (value instanceof Calendar)
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				// convert to xml rendering
				Calendar cal = (Calendar) value;
				String calString = "";
				calString = sdf.format(cal.getTime());
				sdf.applyPattern("hh:mm:ss");
				calString = calString+"T"+sdf.format(cal.getTime());
				
				return calString;
			}
            else if(value instanceof Expression)
            {
                return ((Expression)value).getCanonicalStringForm();
            }
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}

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
