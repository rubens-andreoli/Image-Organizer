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

import rubensandreoli.imageorganizer.io.Logger;
import rubensandreoli.imageorganizer.io.Logger.Level;

public class Shortcut {
    
    public enum Action{NEXT, PREVIOUS, DELETE, MOVE, REFRESH, INFO}
    
    public static final String SEPARATOR = ","; //can't use ':' due to drive path

    public final int key;
    public final Action action;
    public final String description;

    public Shortcut(int key, Action action, String description) {
        this.key = key;
        this.action = action;
        this.description = description;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(String.valueOf(key)).append(SEPARATOR).append(action);
        if(description != null && !description.isBlank()) sb.append(SEPARATOR).append(description); // null if not move action
        return sb.toString();
    }
        
    public static Shortcut parseShortcut(String entry){
        String[] tokens = entry.split(SEPARATOR);
        try{
            int k = Integer.parseInt(tokens[0].trim());
            Action a = Action.valueOf(tokens[1].trim().toUpperCase());
            String d = null;
            if(tokens.length == 3){
                d = tokens[2].trim();
            }else if(a == Action.MOVE){ //may happen if user edited the file
                throw new NullPointerException("move action with no folder associated");
            }
            return new Shortcut(k, a, d);
        }catch(IllegalArgumentException| NullPointerException ex){
            Logger.log.print(Level.WARNING, ex);
            return null;
        }
    }
    
}
