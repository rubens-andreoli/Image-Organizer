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

import rubensandreoli.commons.others.Level;
import rubensandreoli.commons.others.Logger;

public class Shortcut {
    
    public enum Action{NEXT, PREVIOUS, DELETE, MOVE}
    
    public static final String SEPARATOR = ","; //can't use ':' due to 'c:\\'

    public final int key;
    public final Action action;
    public final String description;

    private Shortcut(int key, Action action, String description) {
        this.key = key;
        this.action = action;
//        this.description = description == null? "" : description;
        this.description = description;
    }
        
    public static Shortcut createShortcut(String entry){
        final String[] tokens = entry.split(SEPARATOR);
        try{
            int k = Integer.valueOf(tokens[0].trim());
            Action a = Action.valueOf(tokens[1].trim().toUpperCase());
            String d = null;
            if(tokens.length == 3){
                d = tokens[2].trim();
            }
            return new Shortcut(k, a, d);
        }catch(Exception ex){
            Logger.log.print(Level.CRITICAL, ex);
            return null;
        }
    }
  
}
