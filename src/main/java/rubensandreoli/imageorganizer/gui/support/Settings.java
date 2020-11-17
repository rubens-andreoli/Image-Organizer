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
    
    private boolean debug;
    private boolean showHidden;
    private boolean showAlert;
    private ShortcutMap shortcuts;

    public Settings() {
        debug = Configuration.values.get("debug", true);
        showHidden = Configuration.values.get("hidden_folders", false);
        showAlert = Configuration.values.get("delete_alert", true);
        shortcuts = new ShortcutMap();
        shortcuts.put(Configuration.values.get("shortcuts", KeyEvent.VK_RIGHT+",NEXT;"+KeyEvent.VK_LEFT+",PREVIOUS;"+KeyEvent.VK_DELETE+",DELETE"));
    }

    public Settings(boolean showHidden, boolean showAlert, ShortcutMap shortcuts) {
        debug = true;
        this.showHidden = showHidden;
        this.showAlert = showAlert;
        this.shortcuts = shortcuts;
    }

    public boolean update(Settings newSettings){
        return this.update(newSettings.isShowHidden(), newSettings.isShowAlert(), newSettings.getShortcuts(false));
    }

    public boolean update(boolean showHidden, boolean showAlert, ShortcutMap shortcuts){
        boolean changed = false;
        if(showHidden != this.showHidden){
            this.showHidden = showHidden;
            Configuration.values.put("hidden_folders", String.valueOf(showHidden));
            changed = true;
        }
        if(showAlert != this.showAlert){
            this.showAlert = showAlert;
            Configuration.values.put("delete_alert", String.valueOf(showAlert));
            changed = true;
        }
        if(shortcuts != null && (this.shortcuts.size() != shortcuts.size() || !this.shortcuts.equals(shortcuts))){
            this.shortcuts.clear();
            this.shortcuts.putAll(shortcuts);
            Configuration.values.put("shortcuts", shortcuts.toString());
            changed = true;
        }
        Configuration.values.save();
        return changed;
    }

    public void setShowHidden(boolean b) { //TODO: reload folders lists
        showHidden = b;
    }

    public void setShowAlert(boolean b) {
        showAlert = b;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isShowHidden() {
        return showHidden;
    }

    public boolean isShowAlert() {
        return showAlert;
    }

    public boolean containsShortcut(int code) {
         return shortcuts.containsKey(code);
    }
    
    public void addShortcut(Shortcut shortcut){
        shortcuts.put(shortcut.key, shortcut);
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

    public void removeShortcut(Shortcut shortcut) {
        shortcuts.remove(shortcut.key);
    }

}
