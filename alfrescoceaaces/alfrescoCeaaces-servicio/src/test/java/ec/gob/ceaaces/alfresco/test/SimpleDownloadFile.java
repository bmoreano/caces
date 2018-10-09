/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.gob.ceaaces.alfresco.test;

import ec.gob.ceaaces.alfresco.ServicioAlfresco;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import ec.gob.ceaaces.datastore.impl.AlfrescoDataStore;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class SimpleDownloadFile {

    private static final Logger LOG = Logger.getLogger(SimpleDownloadFile.class.getSimpleName());

    public SimpleDownloadFile() {

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testBody() {
        LOG.info("Inicio Test...");
        ServicioAlfresco sa = new ServicioAlfresco();
        try {
            LOG.info("Inicio Descarga..");
            Properties properties = new Properties();
            InputStream inputStream = sa.downloadFileByPath("/mauricio/alfresco.properties");
            LOG.info("Fin Descarga..");
            properties.load(inputStream);
            LOG.info("User.." + properties.getProperty("user"));

        } catch (DataStoreException ex) {
            Logger.getLogger(SimpleDownloadFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleDownloadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
