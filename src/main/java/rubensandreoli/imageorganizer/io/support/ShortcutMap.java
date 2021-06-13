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
package rubensandreoli.imageorganizer.io.support;

import java.util.HashMap;
import java.util.Iterator;

public class ShortcutMap extends HashMap<Integer, Shortcut>{
    private static final long serialVersionUID = 1L;

    public static final String SEPARATOR = ";"; //can't use ':' due to drive, can't be same as Shortcut.SEPARATOR
        
    public ShortcutMap(){}
   
    public ShortcutMap(ShortcutMap map){
        this.putAll(map);
    }
   
    public void put(String list){
        if(list == null) return;
        for (String token : list.split(SEPARATOR)){
            put(Shortcut.parseShortcut(token));  //continue normally if failed parsing
        }
    }
    
    public void put(Shortcut shortcut){
        if(shortcut == null) return;
        put(shortcut.key, shortcut);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Iterator<Shortcut> it = this.values().iterator();
        while(it.hasNext()){
            sb.append(it.next());
            if(it.hasNext()) sb.append(SEPARATOR);
        }
        return sb.toString();
    }
}
