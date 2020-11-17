/*
 * Copyright (C) 2020 Rubens A. Andreoli Jr.
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

import java.util.HashMap;

public class ShortcutMap extends HashMap<Integer, Shortcut>{
    private static final long serialVersionUID = 1L;

    public static final String SEPARATOR = ";"; //can't use ':' due to drive, can't be same as Shortcut.SEPARATOR
        
    public void put(String list){
        for (String token : list.split(";")){
            final Shortcut shortcut = Shortcut.createShortcut(token);
            put(shortcut);
        }
    }
    
    public void put(Shortcut shortcut){
        put(shortcut.key, shortcut);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        int i = 0;
        for (Shortcut shortcut : this.values()) {
            sb.append(shortcut.key).append(Shortcut.SEPARATOR).append(shortcut.action);
            final String d = shortcut.description;
            if(d != null){
                sb.append(Shortcut.SEPARATOR).append(shortcut.description);
            }
            if(i != size()-1){
                sb.append(SEPARATOR);
            }
            i++;
        }
        return sb.toString();
    }
}
