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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Consumer;

/** 
 * References:
 * <br>
 * https://www.journaldev.com/861/java-copy-file<br>
 * https://stackoverflow.com/questions/222463/is-it-possible-with-java-to-delete-to-the-recycle-bin
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class ImageFolder {
    
    private static final int FILES_ONLY = 0;
    private static final int DIRECTORIES_ONLY = 1;
    private static final int FILES_AND_DIRECTORIES = 2;
    private static final int FILEPATH_MAX_LENGTH = 255;
    private static final String FILENAME_INVALID_CHARS_REGEX = "[\\/\\\\:\\*?\\\"<\\>|]";
    private static final String EXTENSION_INVALID_CHARS_REGEX = "[^a-z-A-Z\\.]";
    private static final String EXTENSION_REGEX = "^.[a-z]{3,}$";
    private static final String FOLDER_INVALID_CHARS_REGEX = "[*?\"<>|]";
    
    private final File folder;
    private final File root;
    
    private final Collection<String> rootFolders;
    private final Collection<String> subFolders;
    private final List<File> images;
    
    public ImageFolder(String folderPath, boolean showHidden){
	rootFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	subFolders = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
	images = new LinkedList<>();
	
	folder = new File(folderPath);
	root = folder.getParentFile();
	
        listChildren(folder, FILES_AND_DIRECTORIES, showHidden, f -> {
            if(f.isDirectory()) {
                subFolders.add(f.getName());
            }else{
                if(ImageFile.isImage(f)) images.add(f);
            }
        });
        listChildren(root, DIRECTORIES_ONLY, showHidden, f -> rootFolders.add(f.getName()));
    }
 
    private static void listChildren(File root, int mode, boolean showHidden, Consumer<File> consumer){
        if(!root.isDirectory()) return;
        File[] listOfFiles = root.listFiles();
        switch(mode){
            case FILES_ONLY:
                for (File file : listOfFiles) {
                    if (file.isFile() && (showHidden || !file.isHidden())) {
                        consumer.accept(file);
                    }
                }
                break;
            case DIRECTORIES_ONLY:
                for (File file : listOfFiles) {
                    if (file.isDirectory() && (showHidden || !file.isHidden())) {
                        consumer.accept(file);
                    }
                }
                break;
            case FILES_AND_DIRECTORIES:
                for (File file : listOfFiles) {
                    if (showHidden || !file.isHidden()) {
                        consumer.accept(file);
                    }
                }
                break;
        }
    }
 
    public void createRelatedFolder(String folderName, boolean subfolder) throws IOException{
        Collection<String> list = (subfolder? subFolders:rootFolders);
        File newFolder = new File((subfolder? folder:root), folderName);
        
        if(list.contains(folderName)) throw new IOException("Folder \""+newFolder.getPath()+"\" already exists!");
        if(createFolder(newFolder))  list.add(folderName);
        else throw new IOException("Folder \""+newFolder.getPath()+"\" could not be created!");
    }
    
    private static boolean createFolder(File root){
        boolean created = false;
        if(!root.isDirectory()){
            try{
                created = root.mkdirs();
            }catch(SecurityException ex){}
        }
        return created;
    }
    
    public boolean checkRelatedFolder(String folderName, boolean subfolder) {
        File newFolder = new File((subfolder? folder:root), folderName);
	if(newFolder.isDirectory()){ 
	    return true;
	}else{
	    if(subfolder) subFolders.remove(folderName);
	    else rootFolders.remove(folderName);
	    return false;
	}
    }
    
    public ImageFile loadImage(int imagePos) {
	return ImageFile.build(images.get(imagePos), imagePos, images.size());
    }
    
    public void transferImageTo(int imagePos, String folderName, boolean subfolder) throws IOException{
        transferImageTo(imagePos, buildRelatedFolderPath(folderName, subfolder));
    }

    public void transferImageTo(int imagePos, String folder) throws IOException{
        File file = images.get(imagePos);
        if(file.getParent().equals(folder))
            throw new IOException("Move destination is the same as the origin!");
        if(moveFileTo(file, folder)){
            images.remove(imagePos);
        }else{
            throw new IOException("Image could not be moved to destination!\n"+folder);
        }
    }
    
    private static boolean moveFileTo(File file, String folder){
        boolean moved = false;
        File tempDest = new File(folder, file.getName());
        try{
            moved = file.renameTo(tempDest);
            if(!moved){ //costly method only if failed above
                tempDest = createValidFile(folder, file.getPath());
                moved = file.renameTo(tempDest);
            }
        }catch(Exception ex){}
        
        return moved;
    }
    
    private static File createValidFile(String folder, String file){
        return createValidFile(folder, getFilename(file), getExtension(file));
    }
    
    private static File createValidFile(String folder, String filename, String extension){
        folder = folder.replaceAll(FOLDER_INVALID_CHARS_REGEX, "");
        return createValidFile(new File(folder), filename, extension);
    }
    
    private static String getFilename(String pathname){
        return getFilename(pathname, true);
    }

    private static String getFilename(String pathname, boolean normalize){
        String name = getName(pathname);
        final int extIndex = name.lastIndexOf(".");
        if(extIndex != -1) name = name.substring(0, extIndex);
        if(normalize) name = name.replaceAll(FILENAME_INVALID_CHARS_REGEX, "");
        return name;
    }
    
    private static String getName(String pathname){
        return new File(pathname).getName();
    }
    
    private static String getExtension(String pathname, String defaultValue){
        final String name = getName(pathname);
        String ext = defaultValue;
        final int extIndex = name.lastIndexOf(".");
        if(extIndex != -1){
            String tmpExt = name.substring(extIndex);
            boolean empty = false;
            while(!tmpExt.matches(EXTENSION_REGEX)){
                if(tmpExt.isEmpty()){
                    empty = true;
                    break;
                }
                tmpExt = tmpExt.substring(0, tmpExt.length()-1);
            }
            if(!empty) ext = tmpExt;
        }
        return ext;
    }
    
    private static String getExtension(String pathname){
        return getExtension(pathname, "");
    }
    
    private static File createValidFile(File folder, String filename, String extension){
        //FIX INVALID CHARACTERS
        filename = filename.replaceAll(FILENAME_INVALID_CHARS_REGEX, "");
        extension = extension.replaceAll(EXTENSION_INVALID_CHARS_REGEX, "");
        File file = new File(folder, new StringBuilder(filename).append(extension).toString());

        //FIX FILEPATH LENGTH
        if(file.getAbsolutePath().length() > FILEPATH_MAX_LENGTH){
            int toRemove = file.getAbsolutePath().length() - FILEPATH_MAX_LENGTH;
            if(filename.length() > toRemove){
                filename = filename.substring(0, filename.length()-toRemove);
                file = new File(folder.getPath(), new StringBuilder(filename).append(extension).toString());
            }else{
                return null;
            }
        }

        //FIX DUPLICATED NAME
        for(int n=1; file.exists(); n++){
            file = new File(folder.getPath(), new StringBuilder(filename)
                            .append(" (").append(String.valueOf(n)).append(")")
                            .append(extension).toString());
        }
        return file;
    }

    public String buildRelatedFolderPath(String folderName, boolean subfolder){
        return new File((subfolder? folder:root), folderName).getPath();
    }
    
    public void removeImage(int imagePos) throws UnsupportedOperationException, IOException {
        File image = images.get(imagePos);
        if(removeFile(image)) images.remove(imagePos);
        else throw new IOException("Image \""+image.getPath()+"\" could not be deleted!\nIt may be in use by another program or it no longer exists.");
    }
    
    private static boolean removeFile(File file) throws UnsupportedOperationException {
        if(Desktop.isDesktopSupported()){
            final Desktop d = Desktop.getDesktop();
            if(d.isSupported(Desktop.Action.MOVE_TO_TRASH)){
                try{
                    return d.moveToTrash(file);
                }catch(IllegalArgumentException|SecurityException ex){ //file not found or no access
                    return false;
                }
            }else{
                throw new UnsupportedOperationException("move to trash action not supported");
            }
        }else{
            throw new UnsupportedOperationException("current platform doesn't support desktop class");
        }
    }
    
    public void deleteImage(int imagePos) throws IOException {
	File image = images.get(imagePos);
        if(deleteFile(image)) images.remove(imagePos);
        else throw new IOException("Image \""+image.getPath()+"\" could not be deleted!\nIt may be in use by another program or it no longer exists.");
    }
    
    private static boolean deleteFile(File file){
        boolean removed = false;
        try{
            removed = file.delete();
        }catch(SecurityException ex){}
        return removed;
    }

    // <editor-fold defaultstate="collapsed" desc=" GETTERS "> 
    public Collection<String> getRootFolders() {
        return rootFolders;
    }
    
    public Collection<String> getSubFolders() {
        return subFolders;
    }
    
    public int getNumImages(){
        return images.size();
    }
    
    public String getFolderPath() {
        return folder.getPath();
    }
    // </editor-fold>
         
    public static boolean checkFolder(String folder) {
        return new File(folder).isDirectory();
    }
    
}
