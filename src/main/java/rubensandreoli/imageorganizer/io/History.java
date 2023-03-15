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
    
    private static final String FILENAME = "history.log";
    private static final String SEPARATOR = ";";
    
    private final File file = new File(FILENAME);
    private final Map<String, Integer> history = new HashMap<>();

    public void load() throws IOException{
	if(!file.exists() || file.isDirectory()){
	    file.createNewFile(); //preemptive warning, failure here means it won't be able to save
	}
	try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
	    String line;
	    while((line = br.readLine()) != null){
	       String[] tokens = line.split(SEPARATOR);
	       history.put(tokens[0], Integer.parseInt(tokens[1]));
	    }
	}
    }
    
    public boolean save(){
	try(BufferedWriter br = new BufferedWriter(new FileWriter(FILENAME))){
	    for(Map.Entry<String, Integer> e : history.entrySet()){
	       br.write(e.getKey()+SEPARATOR+e.getValue());
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
	Integer pos = history.get(folder);
	return pos == null ? 0 : pos;
    }
    
}
