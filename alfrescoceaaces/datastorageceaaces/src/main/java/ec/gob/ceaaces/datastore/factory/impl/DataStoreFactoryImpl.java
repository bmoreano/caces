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
package ec.gob.ceaaces.datastore.factory.impl;

import ec.gob.ceaaces.datastore.DataStore;
import ec.gob.ceaaces.datastore.commons.DataStoreParameter;
import ec.gob.ceaaces.datastore.commons.enumeration.EnumDataStoreType;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import ec.gob.ceaaces.datastore.factory.DataStoreFactory;
import ec.gob.ceaaces.datastore.impl.AlfrescoDataStore;
import ec.gob.ceaaces.datastore.impl.DSpaceDataStore;
import ec.gob.ceaaces.datastore.impl.FileSystemDataStore;
import java.util.Map;

/**
 *
 * @author CEAACES
 */
public class DataStoreFactoryImpl implements DataStoreFactory {

    private String username;
    private String password;
    private String host;
    private String port;
    private String type;

    /**
     *
     */
    protected DataStoreFactoryImpl() {
    }

    /**
     *
     * @return
     */
    public static DataStoreFactoryImpl newInstance() {
        return new DataStoreFactoryImpl();
    }

    /**
     *
     * @param parameters
     * @return
     * @throws DataStoreException
     */
    @Override
    public DataStore createDataStore(Map<String, String> parameters) throws DataStoreException {
        DataStore dataStore;
        type = parameters.get(DataStoreParameter.TYPE);
        if (EnumDataStoreType.ALFRESCO.equals(EnumDataStoreType.valueOf(type))) {
            this.getInformacion(parameters);
            dataStore = AlfrescoDataStore.createInstance(username, password, host, port);
        } else if (EnumDataStoreType.DSPACE.equals(EnumDataStoreType.valueOf(type))) {
            this.getInformacion(parameters);
            dataStore = DSpaceDataStore.createInstance(username, password, host, port);
        } else if (EnumDataStoreType.FILE_SYSTEM.equals(EnumDataStoreType.valueOf(type))) {
            dataStore = FileSystemDataStore.createInstance();
        } else {
            throw new DataStoreException();
        }
        return dataStore;
    }

    /**
     *
     * @param parameters
     */
    private void getInformacion(Map<String, String> parameters) {
        username = parameters.get(DataStoreParameter.USER);
        password = parameters.get(DataStoreParameter.PASSWORD);
        host = parameters.get(DataStoreParameter.HOST);
        port = parameters.get(DataStoreParameter.PORT);
    }

}
