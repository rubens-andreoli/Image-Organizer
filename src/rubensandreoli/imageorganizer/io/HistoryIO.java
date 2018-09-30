package rubensandreoli.imageorganizer.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HistoryIO {
    
    private static final String FILENAME = "history.dat";
    private static final String SPLIT = ";";
    
    private final File file;
    private final HashMap<String, Integer> history;
    
    public HistoryIO(){
	file = new File(FILENAME);
	history = new HashMap<>();
    }

    public void loadHistory() throws IOException{
	if(!file.exists() || file.isDirectory()){
	    file.createNewFile();
	}
	try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
	    String line;
	    while ((line = br.readLine()) != null) {
	       String[] tokens = line.split(SPLIT);
	       history.put(tokens[0], Integer.parseInt(tokens[1]));
	    }
	}
    }
    
    public void setFolderHistory(String folder, int pos){
	history.put(folder, pos);
    }
    
    public int getFolderHistory(String folder){
	Integer pos = history.get(folder);
	if(pos == null){
	    history.put(folder, 0);
	    pos = 0;
	} 
	return pos;
    }
    
    public void saveHistory() throws IOException{
	 try (BufferedWriter br = new BufferedWriter(new FileWriter(FILENAME))) {
	     for(Map.Entry<String, Integer> e : history.entrySet()){
		if(e.getValue() != 0) br.write(e.getKey()+SPLIT+e.getValue()+"\n");
	     }
	 }
    }
    
}
