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
package ec.gob.ceaaces.datastore.impl;

import ec.gob.ceaaces.datastore.DataStore;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CEAACES
 */
public class DSpaceDataStore implements DataStore, Serializable {

    /**
     *
     */
    private static final Logger LOG = Logger.getLogger(DSpaceDataStore.class.getSimpleName());
    /**
     *
     */
    private static final long serialVersionUID = -4259544167553520069L;

    private String username;

    private String password;

    private String url;

    protected DSpaceDataStore() {
        LOG.info("Creating empty constructor");
    }

    /**
     *
     * @param username
     * @param passwd
     * @param host
     * @param port
     * @throws DataStoreException
     */
    protected DSpaceDataStore(String username, String passwd, String host, String port) throws DataStoreException {
        this.username = username;
        this.password = passwd;
        this.url = this.factoryUrlServiceDocument(host, port);
    }

    /**
     *
     * @param host
     * @param port
     * @return
     */
    private String factoryUrlServiceDocument(String host, String port) {
        LOG.info("Creating url service document");
        String serviceDocument = "http://%s:%s/sword/servicedocument";
        serviceDocument = String.format(serviceDocument, host, port);
        LOG.log(Level.INFO, "Complete url : {0}", serviceDocument);
        return serviceDocument;
    }

    /**
     *
     * @param absotulePath
     * @param nameFile
     * @param inputStream
     * @return
     * @throws DataStoreException
     */
    @Override
    public String uploadFile(String absotulePath, String nameFile, InputStream inputStream) throws DataStoreException {
        //TODO: Aqui realizar las modificaciones, para la implementación de DSPACE
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     *
     * @param path
     * @param fileName
     * @param arrayFile
     * @return
     * @throws DataStoreException
     */
    @Override
    public String uploadFile(String path, String fileName, byte[] arrayFile) throws DataStoreException {
        //TODO: Aqui realizar las modificaciones, para la implementación de DSPACE
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     *
     * @param absolutePath
     * @return
     * @throws DataStoreException
     */
    @Override
    public InputStream downloadFileByPath(String absolutePath) throws DataStoreException {
        //TODO: Aqui realizar las modificaciones, para la implementación de DSPACE
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public InputStream downloadFileById(String id) throws DataStoreException {
        //TODO: Aqui realizar las modificaciones, para la implementación de DSPACE
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    /**
     *
     * @param username
     * @param passwd
     * @param host
     * @param port
     * @return
     * @throws DataStoreException
     */
    public static DataStore createInstance(String username, String passwd, String host, String port) throws DataStoreException {
        return new DSpaceDataStore(username, passwd, host, port);
    }

    @Override
    public void createPath(String absolutePath) throws DataStoreException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
