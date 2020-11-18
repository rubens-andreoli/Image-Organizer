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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import rubensandreoli.commons.others.CachedFile;
import rubensandreoli.commons.utils.FileUtils;

/** References:
 * https://www.journaldev.com/861/java-copy-file
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class ImageFolder {
	   
    private final File folder;
    private final File root;
    
    private final Collection<String> rootFolders;
    private final Collection<String> subFolders;
    private final List<File> images;

    public ImageFolder(String folderPath){
        this(folderPath, false);
    }
    
    public ImageFolder(String folderPath, boolean showHidden){
	rootFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	subFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	images = new LinkedList<>();
	
	folder = new File(folderPath);
	root = folder.getParentFile();
	
        FileUtils.visitChildren(folder, FileUtils.FILES_AND_DIRECTORIES, showHidden, f -> {
            if(f.isDirectory()) {
                subFolders.add(f.getName());
            }else{
                final CachedFile cf = new CachedFile(f);
                if(FileUtils.IMAGES_EXT.contains(cf.getExtension())) images.add(cf);
            }
        });
        FileUtils.visitChildren(root, FileUtils.DIRECTORIES_ONLY, showHidden, f -> rootFolders.add(f.getName()));
    }
 
    public void createFolder(String folderName, boolean subfolder) throws IOException{
        final Collection<String> list = (subfolder? subFolders:rootFolders);
        final File newFolder = new File((subfolder? folder:root), folderName);
        
        if(list.contains(folderName)) throw new IOException("Folder \""+newFolder.getPath()+"\" already exists!");
        if(FileUtils.createFolder(newFolder) != null)  list.add(folderName);
        else throw new IOException("Folder \""+newFolder.getPath()+"\" could not be created!");
    }
    
    public boolean checkFolder(String folder) {
        return new File(folder).isDirectory();
    }
    
    public boolean checkFolder(String folderName, boolean subfolder) {
        final File newFolder = new File((subfolder? folder:root), folderName);
	if(newFolder.isDirectory()){ 
	    return true;
	}else{
	    if(subfolder) subFolders.remove(folderName);
	    else rootFolders.remove(folderName);
	    return false;
	}
    }
        
    public BufferedImage loadImage(int imagePos) throws IOException{
	return ImageIO.read(images.get(imagePos));
    }
    
    public void transferImageTo(int imagePos, String folderName, boolean subfolder) throws IOException{
        transferImageTo(imagePos, new File((subfolder? folder:root), folderName).getPath());
    }

    public void transferImageTo(int imagePos, String folder) throws IOException{
        if(FileUtils.moveFileTo(images.get(imagePos), folder)){
            images.remove(imagePos);
        }else{
            throw new IOException("Image could not be moved to destination!\n"+folder);
        }
    }
    
    public void deleteImage(int imagePos) throws IOException{
	final File image = images.get(imagePos);
        if(FileUtils.deleteFile(image)) images.remove(imagePos);
	else throw new IOException("Image \""+image.getPath()+"\" could not be deleted!");
    }

    // <editor-fold defaultstate="collapsed" desc=" GETTERS "> 
    public Collection<String> getRootFolders() {
        return rootFolders;
    }
    
    public Collection<String> getSubFolders() {
        return subFolders;
    }
    
    public String getImagePath(int imagePos){
        return images.get(imagePos).getPath();
    }
    
    public int getNumImages(){
        return images.size();
    }
    
    public String getFolderPath() {
        return folder.getPath();
    }
    
    public String getRootPath(){
        return root.getPath();
    }
    
    public String buildFolderPath(String folderName, boolean subfolder){
        return (new File((subfolder? folder:root), folderName)).getPath();
    }
    // </editor-fold>
    
}
