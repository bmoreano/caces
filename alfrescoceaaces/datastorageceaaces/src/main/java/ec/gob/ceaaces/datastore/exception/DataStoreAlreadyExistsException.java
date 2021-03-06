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
package ec.gob.ceaaces.datastore.exception;

import java.io.Serializable;

/**
 *
 * @author rlopez
 * @version 1.0
 */
public class DataStoreAlreadyExistsException extends Exception implements Serializable {

    private static final long serialVersionUID = 8413056314164882946L;

    /**
     *
     */
    public DataStoreAlreadyExistsException() {
        super();
    }

    /**
     *
     * @param message
     */
    public DataStoreAlreadyExistsException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public DataStoreAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param cause
     */
    public DataStoreAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DataStoreAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
