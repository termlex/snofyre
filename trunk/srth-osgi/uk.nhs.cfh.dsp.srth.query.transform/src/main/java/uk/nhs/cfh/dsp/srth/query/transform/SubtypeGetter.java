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
package uk.nhs.cfh.dsp.srth.query.transform;

import uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint;

import java.util.Set;
import java.util.UUID;

/**
 * An interface for a utility service that returns the UUIDs of the subypes of a given
 * {@link uk.nhs.cfh.dsp.srth.query.model.om.constraint.TerminologyConstraint} from the
 * unique expressions table.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Aug 9, 2010 at 7:10:03 PM
 */
public interface SubtypeGetter {

    Set<UUID> getSubTypeIdsForConstraint(TerminologyConstraint terminologyConstraint);

    boolean isUseProximalPrimitives();

    void setUseProximalPrimitives(boolean useProximalPrimitives);

    boolean isUseDistinctUUIDs();

    void setUseDistinctUUIDs(boolean useDistinctUUIDs);

    boolean isIgnoreTransitiveClosureTable();

    void setIgnoreTransitiveClosureTable(boolean ignoreTransitiveClosureTable);
}
