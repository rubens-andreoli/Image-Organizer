/*
 * Copyright (C) 2020 Rubens A. Andreoli Jr.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package rubensandreoli.imageorganizer.io.support;

/**
 * Checked version of the exception {@code NumberFormatException}, {@code ClassCastException},
 * or any other cast/conversion operation.
 * 
 * @see java.lang.NumberFormatException
 * @see java.lang.ClassCastException
 * @author Rubens A. Andreoli Jr.
 */
public class CastException extends Exception{
    private static final long serialVersionUID = 1L;

    public CastException() {}

    public CastException(String message) {
        super(message);
    }

    public CastException(String message, Throwable cause) {
        super(message, cause);
    }

    public CastException(Throwable cause) {
        super(cause);
    }

}
