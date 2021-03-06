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

import java.util.EventObject;

public class SettingsChangeEvent extends EventObject{
    private static final long serialVersionUID = 1L;

    private final String settingKey;
    private final Object newValue;

    public SettingsChangeEvent(Object source, String settingKey, Object newValue) {
        super(source);
        this.settingKey = settingKey;
        this.newValue = newValue;
    }
    
    public boolean isSetting(String settingKey){
        return this.settingKey.equals(settingKey);
    }

    public String getSettingsKey() {
        return settingKey;
    }

    public Object getNewValue() {
        return newValue;
    }

}