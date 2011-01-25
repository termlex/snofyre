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
 * An enumerated value set representing the status values of a
 * {@link SnomedConcept} or a
 * {@link SnomedDescription}
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Dec 17, 2009 at 10:58:31 AM
 */

public enum ComponentStatus {

    CURRENT, RETIRED, DUPLICATE, OUTDATED, AMBIGUOUS, ERRONEOUS, LIMITED, INAPPROPRIATE,
    CONCEPT_NON_CURRENT, MOVED_ELSEWHERE, PENDING_MOVE, UNKNOWN;

    private int id;

    // the identifierMethod
    public int toInt() {
        return id;
    }

    // the valueOfMethod
    public  static ComponentStatus fromInt(int value) {
        return ComponentStatus.valueOf(value);
    }

    public static ComponentStatus valueOf(int id){

        switch (id)
        {
            case 0 : return CURRENT;            
            case 1: return RETIRED;
            case 2: return DUPLICATE;
            case 3: return OUTDATED;
            case 4: return AMBIGUOUS;
            case 5: return ERRONEOUS;
            case 6: return LIMITED;
            case 7: return INAPPROPRIATE;
            case 8: return CONCEPT_NON_CURRENT;
            case 10: return MOVED_ELSEWHERE;
            case 11: return PENDING_MOVE;
            default: return UNKNOWN;
        }
    }
}
