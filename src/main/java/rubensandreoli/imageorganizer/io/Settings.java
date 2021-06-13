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
package rubensandreoli.imageorganizer.io;

import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import rubensandreoli.commons.others.Configuration;
import rubensandreoli.imageorganizer.io.support.SettingsChangeEvent;
import rubensandreoli.imageorganizer.io.support.SettingsListener;
import rubensandreoli.imageorganizer.io.support.Shortcut;
import rubensandreoli.imageorganizer.io.support.ShortcutMap;

public class Settings {

    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS ">
    public static final String KEY_DEBUG = "debug";
    public static final String KEY_SHOW_HIDDEN = "hidden_folders";
    public static final String KEY_SHOW_ALERT = "delete_alert";
    public static final String KEY_SHORTCUTS = "shortcuts";
    
    public static final boolean DEFAULT_DEBUG = true;
    public static final boolean DEFAULT_SHOW_HIDDEN = false;
    public static final boolean DEFAULT_SHOW_ALERT = true;
    private static final String EMPTY_SHORTCUTS = ""; //used to call overloaded method in the constructor
    // </editor-fold>
    
    private boolean debug;
    private boolean showHidden;
    private boolean showAlert;
    private ShortcutMap shortcutMap;
    private Collection<SettingsListener> listeners = new HashSet<>();

    public Settings() {
        debug = Configuration.values.get(KEY_DEBUG, DEFAULT_DEBUG);
        showHidden = Configuration.values.get(KEY_SHOW_HIDDEN, DEFAULT_SHOW_HIDDEN);
        showAlert = Configuration.values.get(KEY_SHOW_ALERT, DEFAULT_SHOW_ALERT);
        shortcutMap = new ShortcutMap();
        shortcutMap.put(Configuration.values.get(KEY_SHORTCUTS, EMPTY_SHORTCUTS));
        if(shortcutMap.isEmpty()){ //failed loading, default shortcuts
            shortcutMap.put(new Shortcut(KeyEvent.VK_LEFT, Shortcut.Action.PREVIOUS, null));
            shortcutMap.put(new Shortcut(KeyEvent.VK_RIGHT, Shortcut.Action.NEXT, null));
            shortcutMap.put(new Shortcut(KeyEvent.VK_DELETE, Shortcut.Action.DELETE, null));
            shortcutMap.put(new Shortcut(KeyEvent.VK_F1, Shortcut.Action.INFO, null));
            shortcutMap.put(new Shortcut(KeyEvent.VK_F5, Shortcut.Action.REFRESH, null));
        }
    }

    public boolean update(boolean showHidden, boolean showAlert, ShortcutMap shortcutMap){
        boolean changed = false;
        
        if(showHidden != this.showHidden){
            this.showHidden = showHidden;
            Configuration.values.put(KEY_SHOW_HIDDEN, String.valueOf(showHidden));
            changed = true;
            fireSettingsChange(KEY_SHOW_HIDDEN, showHidden);
        }
        
        if(showAlert != this.showAlert){
            this.showAlert = showAlert;
            Configuration.values.put(KEY_SHOW_ALERT, String.valueOf(showAlert));
            changed = true;
            fireSettingsChange(KEY_SHOW_ALERT, showAlert);
        }

        if(shortcutMap != null && (this.shortcutMap.size() != shortcutMap.size() || !this.shortcutMap.equals(shortcutMap))){
            this.shortcutMap.clear();
            this.shortcutMap.putAll(shortcutMap);
            Configuration.values.put(KEY_SHORTCUTS, shortcutMap.toString());
            changed = true;
            fireSettingsChange(KEY_SHORTCUTS, this.shortcutMap);
        }
        
        Configuration.values.save();
        return changed;
    }
    
    public void fireSettingsChange(String settingsKey, Object newValue){
        listeners.forEach(l -> l.settingsChange(new SettingsChangeEvent(this, settingsKey, newValue)));
    }
    
    public boolean containsShortcut(int code) {
         return shortcutMap.containsKey(code);
    }

    // <editor-fold defaultstate="collapsed" desc=" GETTERS "> 
    public boolean isDebug() {
        return debug;
    }
    
    public boolean isShowHidden() {
        return showHidden;
    }
    
    public boolean isShowAlert() {
        return showAlert;
    }

    public Shortcut getShortcut(int code) {
         return shortcutMap.get(code);
    }
    
    public ShortcutMap getShortcutMap() {
        return new ShortcutMap(shortcutMap);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" SETTERS "> 
    public void removeShortcut(String description){
        final Iterator<Map.Entry<Integer, Shortcut>> i = shortcutMap.entrySet().iterator();
        boolean changed = false;
        while(i.hasNext()){
            final Map.Entry<Integer, Shortcut> entry = i.next();
            final String desc = entry.getValue().description;
            if(desc != null && desc.toLowerCase().contains(description.toLowerCase())){
                i.remove();
                changed = true;
            }
        }
        if(changed){
            Configuration.values.put(KEY_SHORTCUTS, shortcutMap.toString());
            fireSettingsChange(KEY_SHORTCUTS, shortcutMap);
            Configuration.values.save();
        }
    }
    
    public boolean addSettingsListener(SettingsListener listener){
        return this.listeners.add(listener);
    }
    
    public boolean removeSettingsListener(SettingsListener listener){
        return this.listeners.remove(listener);
    }
    // </editor-fold>
    
}
