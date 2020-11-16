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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import javax.imageio.ImageIO;
import rubensandreoli.commons.others.CachedFile;
import rubensandreoli.commons.utils.FileUtils;

public class ImageFolder {
	   
    private final String folderPath;
    private final String rootPath;
    
    private final Collection<String> rootFolders;
    private final Collection<String> subFolders;
    private final List<CachedFile> images;

    public ImageFolder(String folderPath){
        this(folderPath, false);
    }
    
    public ImageFolder(String folderPath, boolean showHidden){
	rootFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	subFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	images = new LinkedList<>();
	
	this.folderPath = folderPath;
	rootPath = FileUtils.getParent(folderPath);
	
	final File folder = new File(folderPath);
	File[] listOfFiles = folder.listFiles();
	for (File f : listOfFiles) {
	    if(f.isDirectory() && (showHidden || !f.isHidden())){
                subFolders.add(f.getName());
            }else{
                final CachedFile cf = new CachedFile(f);
                if(FileUtils.IMAGES_EXT.contains(cf.getExtension())){
                    images.add(cf);
                }
            }
	}

	final File rootFolder = new File(rootPath);
	listOfFiles = rootFolder.listFiles();
	for (File f : listOfFiles) {
	    if (f.isDirectory() && (showHidden || !f.isHidden())) {
		rootFolders.add(f.getName());
	    }
	}
//        rootFolders.add(".");
    }

    public void createFolder(String folderName, boolean subfolder) throws IOException{
	final Collection<String> folderList = subfolder? subFolders:rootFolders;
	if(folderList.contains(folderName)){
	    throw new IOException("Folder \""+folderName+"\" already exists!");
        }
	final String path = subfolder? folderPath:rootPath;
        final File folder = new File(path + "\\" + folderName);
	if(folder.mkdir()) folderList.add(folderName);
	else throw new IOException("Folder \""+folder.getAbsolutePath()+"\" could not be created!");
    }
    
    public boolean testFolder(String folderName, boolean subfolder) {
	final String path = subfolder? folderPath:rootPath;
        final File folder = new File(path + "\\" + folderName);
	if(folder.exists() && folder.isDirectory()){ 
	    return true;
	}else{
	    if(subfolder) subFolders.remove(folderName);
	    else rootFolders.remove(folderName);
	    return false;
	}
    }

    public void transferImage(int imagePos, String folder) throws IOException{
       transfer(images.get(imagePos), folder);
       deleteImage(imagePos);
    }
    
    public void transferImage(int imagePos, String folderName, boolean subfolder) throws IOException{
	final String folder = (subfolder? folderPath:rootPath) + "\\" +folderName;
        transfer(images.get(imagePos), folder);
	deleteImage(imagePos);
    }
    
    //TODO: change to Commons FileUtils
    private void transfer(CachedFile source, String folder) throws IOException{
	File dest = new File(String.format("%s\\%s%s", folder, source.getFilename(), source.getExtension()));
	
	for(int i=1; dest.exists(); i++){
	    dest = new File(String.format("%s\\%s (%d)%s", folder, source.getFilename(), i, source.getExtension()));
	}
	
	try{
	    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
	}catch(IOException e){
	    throw new IOException("Image could not be copied to destination!\n"+dest.getAbsolutePath(), e);
	}
    }
    
    public void deleteImage(int imagePos) throws IOException{
	final File image = images.get(imagePos);
	if(image.delete()) images.remove(imagePos);
	else throw new IOException("Image \""+image.getAbsolutePath()+"\" could not be deleted!");
    }
    
    public BufferedImage loadImage(int imagePos) throws IOException{
	return ImageIO.read(images.get(imagePos));
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
        return folderPath;
    }
    
    public String getRootPath(){
        return rootPath;
    }
    // </editor-fold>

}
