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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.nhs.cfh.dsp.srth.information.model.om.clinical.entity;

/**
 * A representation of a Laterality attribute within the application.
 * This represents a reversible transform between an internal application
 * representation and the SNOMED CT representation.
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Oct 4, 2009 at 10:14:52 AM
 */

public enum Laterality {

    RIGHT("24028007"),
    LEFT("7771000"),
    BILATERAL("41440002"),
    UNILATERAL_LEFT("419161000"),
    UNILATERAL_RIGHT("419465000"),
    UNDEFINED("182355001");

    /** The id. */
    private String id;

    /**
     * Instantiates a new laterality.
     * 
     * @param id the id
     */
    Laterality(String id){
        this.id = id;
    }

    /**
     * Gets the id.
     * 
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the value.
     * 
     * @return the value
     */
    public String getValue(){

        if("24028007".equalsIgnoreCase(getId()))
        {
            return RIGHT.name();
        }
        else if("7771000".equalsIgnoreCase(getId()))
        {
            return LEFT.name();
        }
        else if("41440002".equalsIgnoreCase(getId()))
        {
            return BILATERAL.name();
        }
        else if("419161000".equalsIgnoreCase(getId()))
        {
            return UNILATERAL_LEFT.name();
        }
        else if("419465000".equalsIgnoreCase(getId()))
        {
            return UNILATERAL_RIGHT.name();
        }
        else
        {
            return UNDEFINED.name();
        }
    }
}
