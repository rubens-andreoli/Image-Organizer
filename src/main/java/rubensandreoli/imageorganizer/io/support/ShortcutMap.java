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
package rubensandreoli.imageorganizer.io.support;

import java.util.HashMap;
import java.util.Iterator;

/**
 * References:
 * <br>
 * https://stackoverflow.com/questions/668952/the-simplest-way-to-comma-delimit-a-list
 * 
 * @author Rubens A. Andreoli Jr
 */
public class ShortcutMap extends HashMap<Integer, Shortcut>{
    private static final long serialVersionUID = 1L;

    public static final String SEPARATOR = ";"; //can't use ':' due to drive, can't be same as Shortcut.SEPARATOR
        
    public ShortcutMap(){}
   
    public ShortcutMap(ShortcutMap map){
        this.putAll(map);
    }
   
    public void put(String list){
        for (String token : list.split(SEPARATOR)){
            Shortcut s = Shortcut.parseShortcut(token);
            if(s != null) put(s);  //continue normally if failed parsing one
        }
    }
    
    public void put(Shortcut shortcut){
        put(shortcut.key, shortcut);
    }

    @Override
    public String toString() {
        Iterator<Shortcut> it = this.values().iterator();
        StringBuilder sb;
        if (it.hasNext()) {
            sb = new StringBuilder(it.next().toString());
            while(it.hasNext()) sb.append(SEPARATOR).append(it.next());
        }else{
            sb = new StringBuilder();
        }
        return sb.toString();
    }
}
