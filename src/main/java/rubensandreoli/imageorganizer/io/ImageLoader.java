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

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * References:
 * <br>
 * https://stackoverflow.com/questions/23772102/lru-cache-in-java-with-generics-and-o1-operations
 * https://www.baeldung.com/java-lru-cache
 * https://javaconceptoftheday.com/synchronize-arraylist-hashset-and-hashmap-in-java/#:~:text=How%20To%20Synchronize%20HashSet%20In,backed%20by%20the%20specified%20set.
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class ImageLoader {

    //<editor-fold defaultstate="collapsed" desc="LOADER">
    private static class Loader{
        
//        private final int imagePos;
        private final Future<ImageFile> future;

        public Loader(ExecutorService e, File file, int pos) {
//            imagePos = pos+1;
            future = e.submit(() -> ImageFile.load(file, pos));
        }

        public ImageFile get() throws InterruptedException, ExecutionException{
            return future.get();
        }

//        @Override
//        public String toString() {//TODO: remove
//            return ""+imagePos;
//        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="CACHE">  
    private static class Cache extends LinkedHashMap<File, Loader> {

        private final int size;
        
        public Cache(int size){
            super(size, 0.75f, true);
            this.size = size;
        }

        @Override
        public boolean removeEldestEntry(Map.Entry eldest) { //invoked by put and putAll after insert
            return size() > size;
        }
	  
//        public void print(){ //TODO: remove
//            System.out.print("[");
//            for(Loader l : this.values()){
//                System.out.print(l+", ");
//            }
//            System.out.println("]");
//        }
        
    }
    //</editor-fold>
    
    private static final int IMAGE_CACHE_SIZE = 6;
    private static final int THREAD_COUNT = 3;
    
    private final ImageFolder imageFolder;
    private final Cache imageCache;
    private final ExecutorService executor;
    
    public ImageLoader(ImageFolder imageFolder){
        executor = Executors.newFixedThreadPool(THREAD_COUNT);
        imageCache = new Cache(IMAGE_CACHE_SIZE);
        
        this.imageFolder = imageFolder;
    }
    
    public ImageFile loadImage(int pos){
        File file = imageFolder.getImage(pos);
        
        Loader loader;
        if(imageCache.containsKey(file)){
//            System.out.println("Main <cached> ["+file+"]");
            loader = imageCache.get(file);
        }else{
//            System.out.println("Main <new> ["+file+"]");
            loader = new Loader(executor, file, pos);
            imageCache.put(file, loader);
        }

        loadAround(pos);
        
//        imageCache.print();
        try {
            return loader.get(); //will wait here if not loaded
        } catch (InterruptedException | ExecutionException ex) {
            return null; //TODO: handle exception
        }
    }

    private void loadAround(int pos){
        loadImageAt(fixPosition(pos+1));
        loadImageAt(fixPosition(pos-1));
    }
	
    private void loadImageAt(int pos){
            File file = imageFolder.getImage(pos);
            if(!imageCache.containsKey(file)){
            Loader loader = new Loader(executor, file, pos);
            imageCache.put(file, loader);
        }
    }

    private int fixPosition(int pos){
        int total = imageFolder.getNumImages()-1;
        return (pos == -1)? total : (pos > total)? pos-total-1 : pos;
    }
    
    public void shutdown(){
        executor.shutdownNow();
    }

}
