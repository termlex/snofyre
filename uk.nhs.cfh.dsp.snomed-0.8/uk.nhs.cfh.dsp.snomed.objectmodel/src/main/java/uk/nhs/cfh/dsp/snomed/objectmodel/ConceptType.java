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
package uk.nhs.cfh.dsp.snomed.objectmodel;

/**
 * A list of allowed concept and attribute types. This is just a proxy to eliminate the use of conceptIds elsewhere.
 * Still does not remove the use of 'magic numbers', which are contained in the enumeration! We really need to use
 * generics and the MRCM to populate this list!
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 10, 2010 at 12:24:18 AM
 */

public enum ConceptType {

    FINDING_WEC, PROCEDURE_WEC, FINDING, PROCEDURE, OBSERVABLE_ENTITY,
    SPECIMEN, BODY_STRUCTURE, DISEASE, EVALUATION_PROCEDURE, EVENT,
    SURGICAL_PROCEDURE, PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT, SITUATION_WEC,
    DRUG_DEVICE_COMBO_PRODUCT, DEFINITELY_NOT_PRESENT, KNOWN_ABSENT,
    ATTRIBUTE_FINDING_CONTEXT, ATTRIBUTE_TEMPORAL_CONTEXT, ATTRIBUTE_PROCEDURE_CONTEXT,
    ATTRIBUTE_SUBJECT_RELATIONSHIP_CONTEXT, ATTRIBUTE_ASSOCIATED_FINDING, ATTRIBUTE_ASSOCIATED_PROCEDURE,
    ATTRIBUTE_EPISODICITY, ATTRIBUTE_COURSE, ATTRIBUTE_SEVERITIES, ATTRIBUTE_ASSOCIATED_MORPHOLOGY,
    ATTRIBUTE_FINDING_SITE, ATTRIBUTE_PRIORITY, ATTRIBUTE_METHOD, ATTRIBUTE_DIRECT_PROCEDURE_SITE,
    ATTRIBUTE_INDIRECT_PROCEDURE_SITE, ATTRIBUTE_PROCEDURE_SITE, ATTRIBUTE_USING_DEVICE, ATTRIBUTE_USING_ACCESS_DEVICE,
    ATTRIBUTE_USING_ENERGY, ATTRIBUTE_USING_SUBSTANCE, ATTRIBUTE_VIA,  ATTRIBUTE_ACCESS, ATTRIBUTE_SURGICAL_APPROACH,
    ATTRIBUTE_LATERALITY, ATTRIBUTE_IS_A, ATTRIBUTE_DIRECT_SUBSTANCE,
    UNKNOWN;

    /**
     * returns the concept id associated with the concept type. These values are currently hard coded!!
     * @return  the concept ID
     */
    public String getID(){
                            /*
        Note, it would be naive to check if the concept is subsumed by '123037004 |Body Structure|.
        This infact inclues a whole range of human, non-human, normal, abnormal anatomy, etc.
        The most appropriate high level is '91723000' |Anatomical Structure|.
         */

        switch (this)
        {
            case FINDING_WEC: return "413350009";
            case PROCEDURE: return "71388002";
            case SPECIMEN: return "123038009";
            case BODY_STRUCTURE: return "91723000";
            case FINDING: return "404684003";
            case DRUG_DEVICE_COMBO_PRODUCT: return "411115002";
            case DISEASE: return "64572001";
            case EVALUATION_PROCEDURE: return "386053000";
            case EVENT: return "272379006";
            case PHARMACEUTICAL_OR_BIOLOGICAL_PRODUCT: return "373873005";
            case PROCEDURE_WEC: return "129125009";
            case SITUATION_WEC: return "243796009";
            case SURGICAL_PROCEDURE: return "387713003";
            case OBSERVABLE_ENTITY: return "363787002";
            case ATTRIBUTE_FINDING_CONTEXT: return "408729009";
            case ATTRIBUTE_TEMPORAL_CONTEXT: return "408731000";
            case ATTRIBUTE_PROCEDURE_CONTEXT: return "408730004";
            case ATTRIBUTE_SUBJECT_RELATIONSHIP_CONTEXT: return "408732007";
            case ATTRIBUTE_ASSOCIATED_FINDING: return "246090004";
            case ATTRIBUTE_ASSOCIATED_PROCEDURE: return "363589002";
            case ATTRIBUTE_EPISODICITY: return "246456000";
            case ATTRIBUTE_COURSE: return "263502005";
            case ATTRIBUTE_SEVERITIES: return "246112005";
            case ATTRIBUTE_ASSOCIATED_MORPHOLOGY: return "116676008";
            case ATTRIBUTE_FINDING_SITE: return "363698007";
            case ATTRIBUTE_PRIORITY: return "260870009";
            case ATTRIBUTE_METHOD: return "260686004";
            case ATTRIBUTE_DIRECT_PROCEDURE_SITE: return "405813007";
            case ATTRIBUTE_INDIRECT_PROCEDURE_SITE: return "405814001";
            case ATTRIBUTE_PROCEDURE_SITE: return "363704007";
            case ATTRIBUTE_ACCESS: return "260507000";
            case ATTRIBUTE_SURGICAL_APPROACH: return "424876005";
            case ATTRIBUTE_USING_DEVICE: return "424226004";
            case ATTRIBUTE_USING_ACCESS_DEVICE: return "425391005";
            case ATTRIBUTE_USING_ENERGY: return "424244007";
            case ATTRIBUTE_USING_SUBSTANCE: return "424361007";
            case ATTRIBUTE_VIA: return "424876005";
            case ATTRIBUTE_LATERALITY: return "272741003";
            case ATTRIBUTE_IS_A: return "116680003";
            case ATTRIBUTE_DIRECT_SUBSTANCE: return "363701004";
            case DEFINITELY_NOT_PRESENT: return "410594000";
            case KNOWN_ABSENT: return "410516002";

            default: return UNKNOWN.toString();
        }
    }

    /**
     * checks if this {@link uk.nhs.cfh.dsp.snomed.objectmodel.ConceptType} is an Attribute
     * @return the truth value
     */
    public boolean isAttribute(){
        return (this.toString().indexOf("ATTRIBUTE") > -1);
    }
}
