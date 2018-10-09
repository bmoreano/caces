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
import ec.gob.ceaaces.datastore.exception.DataStoreAlreadyExistsException;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisContentAlreadyExistsException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.Tika;

/**
 *
 * @author CEAACES
 */
public class AlfrescoDataStore implements DataStore, Serializable {

    private static final Logger LOG = Logger.getLogger(AlfrescoDataStore.class.getSimpleName());

    private static final long serialVersionUID = 3187444972285448219L;

    private Session session;

    private String username;

    private String atomPubUrl;

    private String password;

    /**
     *
     */
    protected AlfrescoDataStore() {
        LOG.info("Creating empty contructor");
    }

    /**
     *
     * @param username
     * @param passwd
     * @param host
     * @param port
     * @throws DataStoreException
     */
    protected AlfrescoDataStore(String username, String passwd, String host, String port) throws DataStoreException {
        LOG.info("Setting up enviroment for interactive with alfresco server");
        this.atomPubUrl = this.factoryAtomPubUrl(host, port);
        this.username = username;
        this.password = passwd;
        this.session = this.getInstanceSession();
    }

    /**
     * Get the AtomPubUrl for alfresco server.
     *
     * @param host name of the machine.
     * @param port port where the server is setting up.
     * @return the url cmis
     */
    private String factoryAtomPubUrl(String host, String port) {
        String string = "http://%s:%s/alfresco/cmisatom";
        string = String.format(string, host, port);
        LOG.log(Level.INFO, "Setting up the url: {0}", string);
        return string;
    }

    /**
     * Get one instance of Session.
     *
     * @return session of alfresco server.
     * @throws DataStoreException throws when fail the connection.
     */
    private Session getInstanceSession() throws DataStoreException {
        try {
            if (session == null) {
                SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
                Map<String, String> parameter = new HashMap<>();
                parameter.put(SessionParameter.USER, username);
                parameter.put(SessionParameter.PASSWORD, password);
                parameter.put(SessionParameter.ATOMPUB_URL, atomPubUrl);
                parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
                List<Repository> repositoryList = sessionFactory.getRepositories(parameter);
                session = repositoryList.get(0).createSession();
                LOG.log(Level.INFO, "Repository Name: {0}", session.getRepositoryInfo().getName());
                LOG.log(Level.INFO, "Repository ID: {0}", session.getRepositoryInfo().getId());
                LOG.log(Level.INFO, "CMIS Version: {0}", session.getRepositoryInfo().getCmisVersion());
            }
            return session;
        } catch (CmisConnectionException e) {
            LOG.log(Level.SEVERE, null, e);
            throw new DataStoreException("Fail connection to Alfresco Server");
        }
    }

    /**
     *
     * @param path
     * @param inputStream
     * @return
     * @throws DataStoreException
     */
    @Override
    public String uploadFile(String path, String nameFile, InputStream inputStream) throws DataStoreException, DataStoreAlreadyExistsException {
        try {
            LOG.info("Casting inputStream to byte array");
            byte[] content = IOUtils.toByteArray(inputStream);
            return this.uploadFile(path, nameFile, content);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new DataStoreException(ex.getMessage());
        }
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
    public String uploadFile(String path, String fileName, byte[] arrayFile) throws DataStoreException, DataStoreAlreadyExistsException {
        LOG.info("Uploading file");

        LOG.log(Level.INFO, "Absolute path: {0}", path);
        LOG.log(Level.INFO, "Filename: {0}", fileName);
        LOG.log(Level.INFO, "Size file: {0}", arrayFile.length);
        LOG.log(Level.INFO, "Checksum of file is {0}", this.calculateChecksum(arrayFile));

        try {
            Map<String, Object> props = new HashMap<>();
            Document document;
            // Add the object type ID if it wasn't already
            if (props.get(PropertyIds.OBJECT_TYPE_ID) == null) {
                props.put(PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());
            }
            // Add the name if it wasn't already
            if (props.get(PropertyIds.NAME) == null) {
                props.put(PropertyIds.NAME, fileName);
            }
            props.put(PropertyIds.IS_IMMUTABLE, Boolean.TRUE);
            //Determinating the mime type of file.
            String mimeType = new Tika().detect(arrayFile);
            ContentStream contentStream = this.getInstanceSession().getObjectFactory().
                    createContentStream(
                            fileName,
                            arrayFile.length,
                            mimeType,
                            new ByteArrayInputStream(arrayFile)
                    );

            Folder parentFolder = (Folder) this.getInstanceSession().getObjectByPath(path);

            document = parentFolder.createDocument(props, contentStream, VersioningState.MAJOR);

            LOG.log(Level.INFO, "Created new document: {0}", document.getId());

            return document.getId();

        } catch (CmisContentAlreadyExistsException ccaee) {
            LOG.log(Level.SEVERE, "Document already exists: {0}", fileName);
            LOG.log(Level.SEVERE, ccaee.getMessage(), ccaee);
            throw new DataStoreAlreadyExistsException(String.format("El archivo {0} ya se encuentra registrado", fileName));
        } catch (CmisObjectNotFoundException confe) {
            LOG.log(Level.SEVERE, "Path: {0} doesn't exist", path);
            LOG.log(Level.SEVERE, confe.getMessage(), confe);
            throw new DataStoreException(String.format("Path: {0} doesn't exist", path));
        } finally {
            session.clear();
        }
    }

    /**
     *
     * @param absolutePath
     * @return
     * @throws DataStoreException
     */
    @Override
    public InputStream downloadFileByPath(String absolutePath) throws DataStoreException {
        try {
            LOG.log(Level.INFO, "Trying to download the file by absolute path {0}", absolutePath);
            Document document = (Document) this.getInstanceSession().getObjectByPath(absolutePath);
            ContentStream contentStream = document.getContentStream();
            return contentStream.getStream();
        } catch (CmisObjectNotFoundException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new DataStoreException("Not found document");
        }
    }

    /**
     *
     * @param id
     * @return
     * @throws DataStoreException
     */
    @Override
    public InputStream downloadFileById(String id) throws DataStoreException {
        try {
            LOG.log(Level.INFO, "Trying to download the file by id object {0}", id);
            Document document = (Document) this.getInstanceSession().getObject(id);
            ContentStream contentStream = document.getContentStream();
            return contentStream.getStream();
        } catch (CmisObjectNotFoundException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            throw new DataStoreException("Not found document");
        }
    }

    @Override
    public void createPath(String absolutePath) throws DataStoreException {
        StringBuilder createPath = new StringBuilder();
        String parent = "";
        for (StringTokenizer stringTokenizer = new StringTokenizer(absolutePath, "/"); stringTokenizer.hasMoreTokens();) {
            String token = stringTokenizer.nextToken();
            if (StringUtils.isNotBlank(token)) {
                createPath.append("/");
                createPath.append(token);
                Folder f = null;
                try {
                    f = (Folder) this.getInstanceSession().getObjectByPath(createPath.toString());
                    parent = f.getPath();
                    LOG.log(Level.INFO, "Evaluando directorio {0} => OK", f.getPath());
                } catch (CmisObjectNotFoundException e) {
                    LOG.log(Level.INFO, "Creando directorio {0}", createPath);
                    f = (Folder) this.getInstanceSession().getObjectByPath(parent);
                    Map<String, Object> props = new HashMap<>();
                    props.put(PropertyIds.NAME, token);
                    props.put(PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_FOLDER.value());
                    this.getInstanceSession().createFolder(props, f, null, null, null);
                    parent = createPath.toString();
                }
            }

        }
    }

    /**
     *
     * @param file
     * @return
     */
    private Long calculateChecksum(byte[] file) {
        Checksum checksum = new CRC32();
        // update the current checksum with the specified array of bytes
        checksum.update(file, 0, file.length);
        // get the current checksum value
        long checksumValue = checksum.getValue();
        return checksumValue;
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
        return new AlfrescoDataStore(username, passwd, host, port);
    }

}
