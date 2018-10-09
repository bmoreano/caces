/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.ceaaces.alfresco;

import ec.gob.ceaaces.datastore.DataStore;
import ec.gob.ceaaces.datastore.exception.DataStoreAlreadyExistsException;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import ec.gob.ceaaces.datastore.impl.FileSystemDataStore;
import ec.gob.ceaaces.recursos.servicio.ParametroSistemaFacade;
import java.io.InputStream;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.EJB;

/**
 *
 * @author rlopez
 */
@Stateless
@LocalBean
public class ServicioAlfresco {

    @EJB(lookup = "java:global/recursosCeaaces-servicio/ParametroSistemaFacade!ec.gob.ceaaces.recursos.servicio.ParametroSistemaFacade")
    private ParametroSistemaFacade parametroSistemaFacade;

    private DataStore store;

    /**
     *
     * @param relativePath
     * @param nameFile
     * @param inputStream
     * @return
     * @throws DataStoreException
     * @throws
     * ec.gob.ceaaces.datastore.exception.DataStoreAlreadyExistsException
     */
    public String uploadFile(String relativePath, String nameFile, InputStream inputStream) throws DataStoreException, DataStoreAlreadyExistsException {
        iniciarAlfrescoDataStore();
        return store.uploadFile(relativePath, nameFile, inputStream);
    }

    /**
     *
     * @param relativePath
     * @param fileName
     * @param arrayFile
     * @return
     * @throws DataStoreException
     */
    public String uploadFile(String relativePath, String fileName, byte[] arrayFile) throws DataStoreException, DataStoreAlreadyExistsException {
        iniciarAlfrescoDataStore();
        return store.uploadFile(relativePath, fileName, arrayFile);
    }

    /**
     *
     * @param absolutePath
     * @return
     * @throws DataStoreException
     */
    public InputStream downloadFileByPath(String absolutePath) throws DataStoreException {
        iniciarAlfrescoDataStore();
        return store.downloadFileByPath(absolutePath);
    }

    /**
     *
     * @param id
     * @return
     * @throws DataStoreException
     */
    public InputStream downloadFileById(String id) throws DataStoreException {
        iniciarAlfrescoDataStore();
        return store.downloadFileById(id);
    }

    /**
     *
     * @param absolutePath
     * @throws DataStoreException
     */
    public void createPath(String absolutePath) throws DataStoreException {
        iniciarAlfrescoDataStore();
        store.createPath(absolutePath);
    }

    /**
     *
     * @throws DataStoreException
     */
    private void iniciarAlfrescoDataStore() throws DataStoreException {
        String username = this.parametroSistemaFacade.obtenerParametroSistemaPorKey(AlfrescoParameter.ALFRESCO_USER).getValue();
        String passwd = this.parametroSistemaFacade.obtenerParametroSistemaPorKey(AlfrescoParameter.ALFRESO_PASSWD).getValue();
        String host = this.parametroSistemaFacade.obtenerParametroSistemaPorKey(AlfrescoParameter.ALFRESO_HOST).getValue();
        String port = this.parametroSistemaFacade.obtenerParametroSistemaPorKey(AlfrescoParameter.ALFRESCO_PORT).getValue();
        //store = AlfrescoDataStore.createInstance(username, passwd, host, port);
        store = FileSystemDataStore.createInstance();

    }

}
