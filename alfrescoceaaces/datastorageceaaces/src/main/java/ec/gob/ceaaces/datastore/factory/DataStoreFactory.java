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
package ec.gob.ceaaces.datastore.factory;

import ec.gob.ceaaces.datastore.DataStore;
import ec.gob.ceaaces.datastore.exception.DataStoreException;
import java.util.Map;

/**
 *
 * @author CEAACES
 * @version 1.0
 */
public interface DataStoreFactory {

    /**
     * 
     * @param parameters
     * @return
     * @throws DataStoreException
     */
    DataStore createDataStore(Map<String, String> parameters) throws DataStoreException;

}
