package rubensandreoli.imageorganizer.model;

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
	       String[] tokens = line.split(";");
	       history.put(tokens[0], Integer.parseInt(tokens[1]));
	    }
	}
    }
    
    public void addHistory(String folder, int pos){
	history.put(folder, pos);
    }
    
    public int getHistory(String folder){
	Integer r = history.get(folder);
	return r == null? 0:r;
    }
    
    public void saveHistory() throws IOException{
	 try (BufferedWriter br = new BufferedWriter(new FileWriter(FILENAME))) {
	     for(Map.Entry<String, Integer> e : history.entrySet()){
		 br.write(e.getKey()+";"+e.getValue()+"\n");
	     }
	 }
    }
    
}
