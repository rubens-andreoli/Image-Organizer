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
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import rubensandreoli.imageorganizer.io.support.FileUtils;
import static rubensandreoli.imageorganizer.io.support.FileUtils.DIRECTORIES_ONLY;
import static rubensandreoli.imageorganizer.io.support.FileUtils.FILES_AND_DIRECTORIES;

/**
 * References:
 * <br>
 * https://stackoverflow.com/questions/1099300/whats-the-difference-between-getpath-getabsolutepath-and-getcanonicalpath
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class ImageFolder {
    
    private static final int MOVE_CACHE_SIZE = 10;
    
    //<editor-fold defaultstate="collapsed" desc="CACHE">
    private static class Cache extends Stack<MoveAction>{
        
        private final int size;
        
        public Cache(int size){
            this.size = size;
        }
        
        @Override
        public MoveAction push(MoveAction item) {
            if (this.size() == size) {
                this.removeElementAt(0);
            }
            return super.push(item);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="MOVE ACTION">
    private static class MoveAction{
        private final File image;
        private final int pos;

        public MoveAction(File image, int pos) {
            this.image = image;
            this.pos = pos;
        }

    }
    //</editor-fold>
    
    private final File folder;
    private final File parent;
    
    private final Collection<String> parentFolders;
    private final Collection<String> subFolders;
    private final List<File> images;
    
    private final Cache moveCache;
    
    public ImageFolder(File folder){
        moveCache = new Cache(MOVE_CACHE_SIZE);
        parentFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	subFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	images = new LinkedList<>();
	
        this.folder = folder;
        parent = folder.getParentFile();
    }
    
    public ImageFolder(String folder){
	this(new File(folder));
    }
    
    public void load(boolean hidden){
        FileUtils.visitFolderFiles(folder, FILES_AND_DIRECTORIES, hidden, f -> {
            if(f.isDirectory()) {
                subFolders.add(f.getName());
            }else{
                if(FileUtils.isImage(f)) images.add(f);
            }
        });
        if(parent != null){
            parentFolders.add(".");
            FileUtils.visitFolderFiles(parent, DIRECTORIES_ONLY, hidden, f -> {
                if(!f.equals(folder)) parentFolders.add(f.getName());
            });
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="FOLDER MANIPULATION">
    public void createRelatedFolder(String folderName, boolean subfolder) throws IOException{
        File newFolder = buildRelatedFolder(folderName, subfolder);
        if(checkRelatedFolder(newFolder.getPath(), subfolder)) throw new IOException("Folder \""+newFolder.getPath()+"\" already exists!");
        
        if(FileUtils.createFolder(newFolder))  (subfolder? subFolders:parentFolders).add(newFolder.getName());
        else throw new IOException("Folder \""+newFolder.getPath()+"\" could not be created!");
    }
    
    private File buildRelatedFolder(String folderName, boolean subfolder){
        return new File((subfolder? folder:parent), folderName);
    }
    
    public String buildRelatedFolderPath(String folderName, boolean subfolder){
        //TODO: alternative: ends with '.' remove last two
        try {
            return buildRelatedFolder(folderName, subfolder).getCanonicalPath();
        } catch (IOException ex) {
            return buildRelatedFolder(folderName, subfolder).getPath();
        }
    }
    
    public static boolean checkFolder(String folder) {
        return new File(folder).isDirectory();
    }
        
    public boolean checkRelatedFolder(String folderName, boolean subfolder) {
        File folder = buildRelatedFolder(folderName, subfolder);
        if(folder.isDirectory()){ 
	    return true;
	}else{
	    if(subfolder) subFolders.remove(folderName);
	    else parentFolders.remove(folderName);
	    return false;
	}
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="IMAGE MANIPULATION">
    public void moveImageTo(int imagePos, String folderName, boolean subfolder) throws IOException{
        moveImageTo(imagePos, buildRelatedFolderPath(folderName, subfolder));
    }

    public void moveImageTo(int imagePos, String folder) throws IOException{
        if(this.folder.getPath().equals(folder)) throw new IOException("Move destination is the same as the origin!");
        File dest = FileUtils.moveFileTo(images.get(imagePos), folder);
        if(dest != null){
            images.remove(imagePos);
            moveCache.push(new MoveAction(dest, imagePos));
        }else{
            throw new IOException("Image could not be moved to destination!\n"+folder);
        }
    }

    public boolean undoLastMove() throws IOException{
        if(moveCache.isEmpty()) return false;
        MoveAction ma = moveCache.pop();
        if(!ma.image.isFile()) throw new IOException("Moved image could not be found!\n"+ma.image);
        File dest = FileUtils.moveFileTo(ma.image, folder.getPath());
        if(dest == null){
            throw new IOException("Moved image could not be restored!\n"+folder);
        }else{
            images.add(ma.pos, dest);
            return true;
        }
    }
    
    public void removeImage(int imagePos) throws UnsupportedOperationException, IOException {
        File image = images.get(imagePos);
        if(FileUtils.removeFile(image)) images.remove(imagePos);
        else throw new IOException("Image \""+image.getPath()+"\" could not be deleted!\nIt may be in use by another program or it no longer exists.");
    }
    
    public void deleteImage(int imagePos) throws IOException {
	File image = images.get(imagePos);
        if(FileUtils.deleteFile(image)) images.remove(imagePos);
        else throw new IOException("Image \""+image.getPath()+"\" could not be deleted!\nIt may be in use by another program or it no longer exists.");
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GETTERS"> 
    public Collection<String> getParentFolders() {
        return parentFolders;
    }
    
    public Collection<String> getSubFolders() {
        return subFolders;
    }
    
    File getImage(int pos){
        return images.get(pos);
    }
    
    public int getNumImages(){
        return images.size();
    }
    
    public boolean isEmpty(){
        return images.isEmpty();
    }
    
    public String getPath(){
        return folder.getPath();
    }
    
    public File getFolder(){
        return folder;
    }
    
    public boolean hasMoveCached(){
        return !moveCache.isEmpty();
    }
    // </editor-fold>
}
