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

import java.awt.event.KeyEvent;
import rubensandreoli.commons.others.Configuration;

public class Settings {
    
    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS ">
    private static final String KEY_DEBUG = "debug";
    private static final boolean DEFAULT_DEBUG = true;
    private static final String KEY_SHOW_HIDDEN = "hidden_folders";
    private static final boolean DEFAULT_SHOW_HIDDEN = false;
    private static final String KEY_SHOW_ALERT = "delete_alert";
    private static final boolean DEFAULT_SHOW_ALERT = true;
    private static final String KEY_SHORTCUTS = "shortcuts";
    private static final String DEFAULT_SHORTCUTS = 
            KeyEvent.VK_RIGHT+Shortcut.SEPARATOR+"NEXT"+ShortcutMap.SEPARATOR+
            KeyEvent.VK_LEFT+Shortcut.SEPARATOR+"PREVIOUS"+ShortcutMap.SEPARATOR+
            KeyEvent.VK_DELETE+Shortcut.SEPARATOR+"DELETE";
    // </editor-fold>
    
    private boolean debug;
    private boolean showHidden;
    private boolean showAlert;
    private ShortcutMap shortcuts;

    public Settings() {
        debug = Configuration.values.get(KEY_DEBUG, DEFAULT_DEBUG);
        showHidden = Configuration.values.get(KEY_SHOW_HIDDEN, DEFAULT_SHOW_HIDDEN);
        showAlert = Configuration.values.get(KEY_SHOW_ALERT, DEFAULT_SHOW_ALERT);
        shortcuts = new ShortcutMap();
        shortcuts.put(Configuration.values.get(KEY_SHORTCUTS, DEFAULT_SHORTCUTS));
    }

    public Settings(boolean showHidden, boolean showAlert, ShortcutMap shortcuts) {
        debug = true;
        this.showHidden = showHidden;
        this.showAlert = showAlert;
        this.shortcuts = shortcuts;
    }

    public boolean update(Settings newSettings){
        return update(newSettings.isShowHidden(), newSettings.isShowAlert(), newSettings.getShortcuts(false));
    }

    public boolean update(boolean showHidden, boolean showAlert, ShortcutMap shortcuts){
        boolean changed = false;
        if(showHidden != this.showHidden){
            this.showHidden = showHidden;
            Configuration.values.put(KEY_SHOW_HIDDEN, String.valueOf(showHidden));
            changed = true;
        }
        if(showAlert != this.showAlert){
            this.showAlert = showAlert;
            Configuration.values.put(KEY_SHOW_ALERT, String.valueOf(showAlert));
            changed = true;
        }
        if(shortcuts != null && (this.shortcuts.size() != shortcuts.size() || !this.shortcuts.equals(shortcuts))){
            this.shortcuts.clear();
            this.shortcuts.putAll(shortcuts);
            Configuration.values.put(KEY_SHORTCUTS, shortcuts.toString());
            changed = true;
        }
        Configuration.values.save();
        return changed;
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
    
    public ShortcutMap getShortcuts(boolean copy) {
        if(copy){
            ShortcutMap sm = new ShortcutMap();
            sm.putAll(shortcuts);
            return sm;
        } else return shortcuts;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" SETTERS "> 
    public void setShowHidden(boolean b) { //TODO: reload folders lists
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
    // </editor-fold>

}
