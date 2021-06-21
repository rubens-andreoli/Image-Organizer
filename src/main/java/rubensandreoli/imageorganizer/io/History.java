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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class History {
    
    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS ">
    private static final String FILENAME = "history.log";
    private static final String SEPARATOR = ";";
    // </editor-fold>
    
    private final File file = new File(FILENAME);
    private final Map<String, Integer> history = new HashMap<>();

    public void load() throws IOException{
	if(!file.exists() || file.isDirectory()){
	    file.createNewFile(); //preemptive warning, failure here means it won't be able to save
            return;
	}
	try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
	    String line;
	    while((line = br.readLine()) != null){
	       final String[] tokens = line.split(SEPARATOR);
	       history.put(tokens[0], Integer.parseInt(tokens[1]));
	    }
	}
    }
    
    public boolean save(){
	try(BufferedWriter br = new BufferedWriter(new FileWriter(FILENAME))){
	    for(Map.Entry<String, Integer> e : history.entrySet()){
               final StringBuilder sb = new StringBuilder(e.getKey()).append(SEPARATOR).append(e.getValue());
	       br.write(sb.toString());
               br.newLine();
	    }
            return true;
	}catch(IOException ex){ //if can't save, probably can't log
            return false;
        }
    }
    
    public void addEntry(String folder, int pos){
        if(pos > 0) history.put(folder, pos);
        else history.remove(folder);
    }

    public int getPosition(String folder){
	final Integer pos = history.get(folder);
	return pos == null ? 0 : pos;
    }
    
}
