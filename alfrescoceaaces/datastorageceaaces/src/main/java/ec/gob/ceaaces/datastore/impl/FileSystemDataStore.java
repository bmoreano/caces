/*
 * Copyright (C) 2015 rlopez
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author rlopez
 * @version 1.0
 */
public class FileSystemDataStore implements DataStore {

    private static final Logger LOG = Logger.getLogger(FileSystemDataStore.class.getName());

    /**
     *
     * @return @throws DataStoreException
     */
    public static DataStore createInstance() throws DataStoreException {
        return new FileSystemDataStore();
    }

    @Override
    public String uploadFile(String relativePath, String nameFile, InputStream inputStream) throws DataStoreException, DataStoreAlreadyExistsException {
        try {
            byte[] contenidoArchivo = IOUtils.toByteArray(inputStream);
            return this.uploadFile(relativePath, nameFile, contenidoArchivo);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new DataStoreException("Error al leer el archivo");
        }
    }

    @Override
    public String uploadFile(String relativePath, String fileName, byte[] arrayFile) throws DataStoreException, DataStoreAlreadyExistsException {
        try {
            StringBuilder path = new StringBuilder();
            path.append(relativePath).append(File.separatorChar).append(fileName);
            File file = new File(path.toString());
            if (file.exists()) {
                throw new DataStoreAlreadyExistsException(String.format("El archivo %s ya se encuentra registrado", fileName));
            }
            IOUtils.write(arrayFile, new FileOutputStream(file));
            return path.toString();
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new DataStoreException("Ocurrio un erro al intentar carga el archivo");
        }
    }

    @Override
    public InputStream downloadFileByPath(String absolutePath) throws DataStoreException {
        try {
            return new FileInputStream(new File(absolutePath));
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new DataStoreException();
        }
    }

    @Override
    public InputStream downloadFileById(String id) throws DataStoreException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createPath(String absolutePath) throws DataStoreException {
        try {
            FileUtils.forceMkdir(new File(absolutePath));
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            throw new DataStoreException();
        }
    }

}
