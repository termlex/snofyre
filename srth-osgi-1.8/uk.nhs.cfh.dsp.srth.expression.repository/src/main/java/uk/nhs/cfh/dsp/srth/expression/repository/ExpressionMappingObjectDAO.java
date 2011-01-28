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
package uk.nhs.cfh.dsp.srth.expression.repository;

import uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * An interface specification for a DAO object that manages
 * {@link uk.nhs.cfh.dsp.srth.expression.repository.om.ExpressionMappingObject}s
 * 
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Feb 11, 2010 at 11:01:36 AM
 */
public interface ExpressionMappingObjectDAO {

    /**
     * Save.
     *
     * @param object the object
     */
    void save(ExpressionMappingObject object);

    /**
     * Delete.
     *
     * @param object the object
     */
    void delete(ExpressionMappingObject object);

    /**
     * Find all.
     *
     * @return the list
     */
    List<ExpressionMappingObject> findAll();

    /**
     * Find using ctu id.
     *
     * @param uuid the uuid
     * @return the expression mapping object
     */
    ExpressionMappingObject findUsingCTUId(UUID uuid);

    /**
     * Find using nfe id.
     *
     * @param uuid the uuid
     * @return the expression mapping object
     */
    ExpressionMappingObject findUsingNFEId(UUID uuid);

    /**
     * Find using snfe id.
     *
     * @param uuid the uuid
     * @return the expression mapping object
     */
    ExpressionMappingObject findUsingSNFEId(UUID uuid);

    /**
     * Find using ctu compositional grammar form.
     *
     * @param cgForm the cg form
     * @return the expression mapping object
     */
    ExpressionMappingObject findUsingCTUCompositionalGrammarForm(String cgForm);

    /**
     * Find using nfe compositional grammar form.
     *
     * @param cgForm the cg form
     * @return the expression mapping object
     */
    ExpressionMappingObject findUsingNFECompositionalGrammarForm(String cgForm);

    /**
     * Find using snfe compositional grammar form.
     *
     * @param cgForm the cg form
     * @return the expression mapping object
     */
    ExpressionMappingObject findUsingSNFECompositionalGrammarForm(String cgForm);

    /**
     * Delete all.
     */
    void deleteAll();

    /**
     * Return all as lite objects.
     *
     * @return the list
     */
    List<ExpressionMappingObject> returnAllAsLiteObjects();

    /**
     * Return all distinct lite objects.
     *
     * @return the list
     */
    List<ExpressionMappingObject> returnAllDistinctLiteObjects();

    /**
     * Return all matching distinct lite objects.
     *
     * @param proximalPrimitive the proximal primitive
     * @return the list
     */
    List<ExpressionMappingObject> returnAllMatchingDistinctLiteObjects(String proximalPrimitive);

    /**
     * Gets the cTU ids using nfef ids.
     *
     * @param nfeUUIDs the nfe uui ds
     * @return the cTU ids using nfef ids
     */
    List<UUID> getCTUIdsUsingNFEFIds(Collection<UUID> nfeUUIDs);
}
