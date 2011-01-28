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
package uk.nhs.cfh.dsp.srth.distribution;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * A class that verifies that the connection to the MySQL database that Sappheiros uses.
 *
 * <br>Version : @#VersionNumber#@
 * <br>Written by @author Jay Kola
 * <br>Created on Nov 15, 2010 at 10:00:14 AM
 */
public class DBConnectionVerifier {

    private static Logger logger = Logger.getLogger(DBConnectionVerifier.class.getName());
    private String portNumber = "3306";
    private String schemaName = "fakedata2";
    private String userName ;
    private char[] pwd;
    private String hostName = "localhost";

    public DBConnectionVerifier() {
        try
        {
            logger.addHandler(new FileHandler(System.getProperty("java.io.tmpdir")+
                    System.getProperty("file.separator")+"configurator.log", true));
        }
        catch (IOException e) {
            logger.warning("Nested exception is : " + e.fillInStackTrace().getMessage());
        }
    }

    public boolean verifyConnection(String userName, char[] pwd){
        setUserName(userName);
        setPwd(pwd);
        // call verifyConnection() method
        return verifyConnection();
    }

    public boolean verifyConnection(){

        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setDatabaseName(schemaName);
        ds.setUser(userName);
        ds.setPassword(String.valueOf(pwd));
        ds.setURL("jdbc:mysql://"+hostName+":"+portNumber+"/"+schemaName);

        try
        {
            Connection connection = ds.getConnection();
            PreparedStatement st1 = connection.prepareStatement("SELECT COUNT(*) FROM SNOMED.CONCEPT");
            PreparedStatement st2 = connection.prepareStatement("DROP TABLE IF EXISTS SNOFYRE_TEST");
            PreparedStatement st3 = connection.prepareStatement( "CREATE TABLE SNOFYRE_TEST ("
                    + "id INT UNSIGNED NOT NULL AUTO_INCREMENT,"
                    + "PRIMARY KEY (id),"
                    + "name CHAR(8), category VARCHAR(36))");
            PreparedStatement st4 = connection.prepareStatement("INSERT INTO SNOFYRE_TEST (name, category) VALUES (? , ?)");

            // execute prepared statements
            if(st1.execute())
            {
                logger.info("Successfully executed statement against SNOMED CT database.");
            }

            if(st2.execute())
            {
                logger.info("Successfully executed drop statement against fakedata2 database.");
            }

            if(st3.execute())
            {
                logger.info("Successfully executed create table statement against fakedata2 database.");
            }

            for(int i=0; i<10; i++)
            {
                // update values in st4 and execute statement
                st4.setString(1, String.valueOf(i));
                st4.setString(2, UUID.randomUUID().toString());

                // execute st4
                st4.executeUpdate();
            }
            logger.info("Successfully executed update statement against fakedata2 database.");

            return true;
        }
        catch (SQLException e) {
            logger.warning("Error creating connection. Nested exception is : " + e.fillInStackTrace().getMessage());
            return false;
        }
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPwd(char[] pwd) {
        this.pwd = pwd;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
