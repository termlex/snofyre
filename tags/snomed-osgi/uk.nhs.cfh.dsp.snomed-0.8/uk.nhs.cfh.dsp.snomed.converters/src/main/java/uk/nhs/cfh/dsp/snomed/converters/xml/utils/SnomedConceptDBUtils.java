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
package uk.nhs.cfh.dsp.snomed.converters.xml.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * A class that implements static utility methods for accessing snomed concepts from the database
 * Written by @author jay
 * Created on Dec 17, 2008 at 10:04:33 AM.
 */
public class SnomedConceptDBUtils {

	/** The logger. */
	private static Log logger = LogFactory.getLog(SnomedConceptDBUtils.class);

	/**
	 * Gets the fully specified name.
	 *
	 * @param connection the connection
	 * @param conceptID the concept id
	 * @return the fully specified name
	 */
	public static String getFullySpecifiedName(Connection connection, String conceptID){

		String fullySpecifiedName = "";
		try
		{
			PreparedStatement getFullySpecifiedNameStatement = connection.prepareStatement("" +
					"SELECT FULLYSPECIFIEDNAME FROM SNOMED.CONCEPT " +
					"WHERE CONCEPTID = ? ");
			getFullySpecifiedNameStatement.setString(1, conceptID);
			ResultSet rs = getFullySpecifiedNameStatement.executeQuery();
			while(rs.next())
			{
				// render in piped format
				fullySpecifiedName = conceptID+" |"+rs.getString("FULLYSPECIFIEDNAME")+"|";
			}

			// close statement and result set
			rs.close();
			getFullySpecifiedNameStatement.close();

		} catch (SQLException e) {
			logger.warn(e.fillInStackTrace());
		}

		return fullySpecifiedName;
	}

	/**
	 * Gets the all concept i ds.
	 *
	 * @param connection the connection
	 * @return the all concept i ds
	 */
	public static Set<String> getAllConceptIDs(Connection connection){

		Set<String> conceptIDs = new HashSet<String>();

		try
		{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("" +
					"SELECT CONCEPTID FROM SNOMED.CONCEPT");
			while(rs.next())
			{
				String conceptID = rs.getString("CONCEPTID");
				// add to set
				conceptIDs.add(conceptID);
			}

		} catch (SQLException e) {
			logger.warn(e.fillInStackTrace());
		}

		return conceptIDs;
	}

}