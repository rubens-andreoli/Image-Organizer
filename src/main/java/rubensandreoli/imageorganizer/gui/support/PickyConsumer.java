/*
 * Copyright (C) 2023 Rubens A. Andreoli Jr.
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
package rubensandreoli.imageorganizer.gui.support;

/**
 * This class was created to improve code readability, as it works the same way as a {@code Predicate}.
 * 
 * @see java.util.function.Predicate
 * @param <T> the type of the input to the predicate
 * @author Rubens A. Andreoli Jr.
 */
@FunctionalInterface
public interface PickyConsumer<T> {
    boolean accept(T t);
}
