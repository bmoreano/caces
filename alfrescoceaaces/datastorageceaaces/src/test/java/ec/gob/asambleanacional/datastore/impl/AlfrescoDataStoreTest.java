/*
 * Copyright (C) 2014 CEAACES
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ec.gob.asambleanacional.datastore.impl;

import ec.gob.ceaaces.datastore.DataStore;
import ec.gob.ceaaces.datastore.commons.DataStoreParameter;
import ec.gob.ceaaces.datastore.commons.enumeration.EnumDataStoreType;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import ec.gob.ceaaces.datastore.factory.DataStoreFactory;
import ec.gob.ceaaces.datastore.factory.impl.DataStoreFactoryImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author CEAACES
 */
public class AlfrescoDataStoreTest {

    private static final Logger LOG = Logger.getLogger(AlfrescoDataStoreTest.class.getName());

    private String username;

    private String passwd;

    private String host;

    private String port;

    @Before
    public void setUp() {
        this.username = "admin";
        this.passwd = "ibmlnx";
        this.host = "localhost";
        this.port = "8080";
    }

    @After
    public void tearDown() {
        this.username = "";
        this.passwd = "";
        this.host = "";
        this.port = "";
    }

    /**
     * Test of setup method, of class DataStoreFactory.
     */
    @Ignore
    public void testSetupAlfresco() {
        LOG.info("Testing <setup method> from Alfresco Data Source ");
        try {
            DataStoreFactory dataStoreFactory = DataStoreFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<>();
            parameter.put(DataStoreParameter.HOST, host);
            parameter.put(DataStoreParameter.PORT, port);
            parameter.put(DataStoreParameter.PASSWORD, passwd);
            parameter.put(DataStoreParameter.USER, username);
            parameter.put(DataStoreParameter.TYPE, EnumDataStoreType.ALFRESCO.name());
            DataStore instance = dataStoreFactory.createDataStore(parameter);
            assertNull(null);
        } catch (DataStoreException ex) {
            //LOG.log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

    @Ignore
    public void testCreateFolder() {
        LOG.info("Testing <createFolder> method ");
        try {
            DataStoreFactory dataStoreFactory = DataStoreFactoryImpl.newInstance();
            Map<String, String> parameter = new HashMap<>();
            parameter.put(DataStoreParameter.HOST, host);
            parameter.put(DataStoreParameter.PORT, port);
            parameter.put(DataStoreParameter.PASSWORD, passwd);
            parameter.put(DataStoreParameter.USER, username);
            parameter.put(DataStoreParameter.TYPE, EnumDataStoreType.ALFRESCO.name());
            DataStore instance = dataStoreFactory.createDataStore(parameter);
            instance.createPath("/GIIES/CargaMasiva/1059/Contratos/2014/1/13");
            assertNull(null);
        } catch (DataStoreException ex) {
            //LOG.log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
    }

}
