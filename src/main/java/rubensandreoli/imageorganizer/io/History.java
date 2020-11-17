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
    
    private static final String FILENAME = "history.dat";
    private static final String SEPARATOR = ";";
    
    private final File file;
    private final Map<String, Integer> history;
    
    public History(){
	file = new File(FILENAME);
	history = new HashMap<>();
    }

    public void load() throws IOException{
	if(!file.exists() || file.isDirectory()){
	    file.createNewFile();
	}
	try(BufferedReader br = new BufferedReader(new FileReader(FILENAME))){
	    String line;
	    while((line = br.readLine()) != null){
	       String[] tokens = line.split(SEPARATOR);
	       history.put(tokens[0], Integer.parseInt(tokens[1]));
	    }
	}
    }
    
    public void save() throws IOException{
	try(BufferedWriter br = new BufferedWriter(new FileWriter(FILENAME))){
	    for(Map.Entry<String, Integer> e : history.entrySet()){
	       if(e.getValue() != 0) br.write(String.format("%s%s%d\n", e.getKey(), SEPARATOR, e.getValue()));
	    }
	}
    }
    
    public void addEntry(String folder, int pos){
	history.put(folder, pos);
    }
    
    public boolean cotains(String folder){
        Integer pos = history.get(folder);
	return pos != null;
    }
    
    public int getPosition(String folder){
	final Integer pos = history.get(folder);
	return pos == null ? 0 : pos;
    }

    public void removeEntry(String folderPath) {
        history.remove(folderPath);
    }
    
}
