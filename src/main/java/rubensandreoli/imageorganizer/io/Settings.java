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
import rubensandreoli.imageorganizer.gui.support.Shortcut;
import rubensandreoli.imageorganizer.gui.support.ShortcutMap;

public class Settings {

    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS ">
    public static final String KEY_DEBUG = "debug";
    public static final String KEY_SHOW_HIDDEN = "hidden_folders";
    public static final String KEY_SHOW_ALERT = "delete_alert";
    public static final String KEY_SHORTCUTS = "shortcuts";
    
    public static final boolean DEFAULT_DEBUG = true;
    public static final boolean DEFAULT_SHOW_HIDDEN = false;
    public static final boolean DEFAULT_SHOW_ALERT = true;
    public static final String DEFAULT_SHORTCUTS = 
            KeyEvent.VK_RIGHT+Shortcut.SEPARATOR+"NEXT"+ShortcutMap.SEPARATOR+
            KeyEvent.VK_LEFT+Shortcut.SEPARATOR+"PREVIOUS"+ShortcutMap.SEPARATOR+
            KeyEvent.VK_DELETE+Shortcut.SEPARATOR+"DELETE";
    // </editor-fold>
    
    private boolean debug;
    private boolean showHidden;
    private boolean showAlert;
    private ShortcutMap shortcuts;
    private Collection<SettingsListener> listeners;

    public Settings() {
        shortcuts = new ShortcutMap();
        listeners = new HashSet<>();
        
        debug = Configuration.values.get(KEY_DEBUG, DEFAULT_DEBUG);
        showHidden = Configuration.values.get(KEY_SHOW_HIDDEN, DEFAULT_SHOW_HIDDEN);
        showAlert = Configuration.values.get(KEY_SHOW_ALERT, DEFAULT_SHOW_ALERT);
        shortcuts.put(Configuration.values.get(KEY_SHORTCUTS, DEFAULT_SHORTCUTS));
    }

    public Settings(boolean showHidden, boolean showAlert, ShortcutMap shortcuts) {
        debug = true;
        this.showHidden = showHidden;
        this.showAlert = showAlert;
        this.shortcuts = shortcuts;
    }

    public boolean update(Settings newSettings){
        return update(newSettings.isShowHidden(), newSettings.isShowAlert(), newSettings.getShortcutMap(false));
    }

    public boolean update(boolean showHidden, boolean showAlert, ShortcutMap shortcuts){
        boolean changed = false;
        
        if(showHidden != this.showHidden){
            this.showHidden = showHidden;
            Configuration.values.put(KEY_SHOW_HIDDEN, String.valueOf(showHidden));
            changed = true;
            fireSettingsChanged(KEY_SHOW_HIDDEN, showHidden);
        }
        
        if(showAlert != this.showAlert){
            this.showAlert = showAlert;
            Configuration.values.put(KEY_SHOW_ALERT, String.valueOf(showAlert));
            changed = true;
            fireSettingsChanged(KEY_SHOW_ALERT, showAlert);
        }
        
        if(shortcuts != null && (this.shortcuts.size() != shortcuts.size() || !this.shortcuts.equals(shortcuts))){
            this.shortcuts.clear();
            this.shortcuts.putAll(shortcuts);
            Configuration.values.put(KEY_SHORTCUTS, shortcuts.toString());
            changed = true;
            fireSettingsChanged(KEY_SHORTCUTS, this.shortcuts);
        }
        
        Configuration.values.save();
        return changed;
    }
    
    public void fireSettingsChanged(String settingsKey, Object newValue){
        listeners.forEach(l -> l.settingsChange(new SettingsChangeEvent(this, settingsKey, newValue)));
    }
    
    public boolean containsShortcut(int code) {
         return shortcuts.containsKey(code);
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
         return shortcuts.get(code);
    }
    
    public ShortcutMap getShortcutMap(boolean copy) {
        if(copy){
            final ShortcutMap sm = new ShortcutMap();
            sm.putAll(shortcuts);
            return sm;
        } else return shortcuts;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" SETTERS "> 
    public void setShowHidden(boolean b) {
        showHidden = b;
    }

    public void setShowAlert(boolean b) {
        showAlert = b;
    }
        
    public void addShortcut(Shortcut shortcut){
        shortcuts.put(shortcut.key, shortcut);
    }
    
    public void removeShortcut(Shortcut shortcut) {
        shortcuts.remove(shortcut.key);
    }
    
    public void removeShortcut(String description){
        final Iterator<Map.Entry<Integer, Shortcut>> i = shortcuts.entrySet().iterator();
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
            Configuration.values.put(KEY_SHORTCUTS, shortcuts.toString());
            fireSettingsChanged(KEY_SHORTCUTS, this.shortcuts);
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
