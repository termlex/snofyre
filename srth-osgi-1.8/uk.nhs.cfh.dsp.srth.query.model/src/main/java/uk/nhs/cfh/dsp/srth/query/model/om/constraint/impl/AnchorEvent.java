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

import org.jdom.Element;
import uk.nhs.cfh.dsp.srth.query.model.om.QueryExpression;
import uk.nhs.cfh.dsp.srth.query.model.om.impl.AbstractQueryExpression;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * A class that represents the notion of anchor events in complicated temporal
 * clinical queries that reference {@link uk.nhs.cfh.dsp.srth.query.model.om.QueryComponentExpression} within a query.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Jul 9, 2009 at 1:57:59 PM
 * <br>
 */

@Embeddable
@Entity(name = "AnchorEvent")
@Table(name = "ANCHOR_EVENTS")
public class AnchorEvent {

    /** The expression. */
    private QueryExpression expression;

    /** The date. */
    private Calendar date;
    
    /** The id. */
    private long id;

    /**
     * Instantiates a new anchor event.
     *
     * @param expression the expression
     */
    public AnchorEvent(QueryExpression expression) {
        this.expression = expression;
    }

    /**
     * Instantiates a new anchor event.
     *
     * @param date the date
     */
    public AnchorEvent(Calendar date){
        this.date = date;
    }

    /**
     * Instantiates a new anchor event.
     */
    public AnchorEvent(){
        // set date to time now
        this.date = Calendar.getInstance();
    }

    /**
     * Gets the expression.
     *
     * @return the expression
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = AbstractQueryExpression.class)
    public QueryExpression getExpression() {
        return expression;
    }

    /**
     * Sets the expression.
     *
     * @param expression the new expression
     */
    public void setExpression(QueryExpression expression) {
        this.expression = expression;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    @Column(name = "date", columnDefinition = "date")
    public Calendar getDate() {
        return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Gets the as xml element.
     *
     * @return the as xml element
     */
    @Transient
    public Element getAsXMLElement(){

        Element element = new Element("Anchor_Element");

        Calendar cal = getDate();
        // set type based on expression or date
        if(cal != null)
        {
            element.setAttribute("type", cal.getClass().getSimpleName());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // convert to xml rendering
            String calString = "";
            calString = sdf.format(cal.getTime());
            sdf.applyPattern("hh:mm:ss");
            calString = calString+"T"+sdf.format(cal.getTime());
            element.setAttribute("value", calString);

        }
        else
        {
            element.setAttribute("type", getExpression().getClass().getSimpleName());
            // add id of expression as value
            element.setAttribute("value", String.valueOf(getExpression().getUUID()));
        }

        return element;
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
