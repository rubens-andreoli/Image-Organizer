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
package rubensandreoli.imageorganizer.io.support;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Consumer;
import rubensandreoli.imageorganizer.io.Logger;
import rubensandreoli.imageorganizer.io.Logger.Level;

public class FileUtils {
    
    public static final int FILES_ONLY = 0;
    public static final int DIRECTORIES_ONLY = 1;
    public static final int FILES_AND_DIRECTORIES = 2;
    
    public static final int FILEPATH_MAX_LENGTH = 255;
    
    public static final Comparator<File> FILENAME_COMPARATOR = (f1, f2) -> String.CASE_INSENSITIVE_ORDER.compare(f1.getPath(), f2.getPath());
    
    private static final String[] SIZE_UNITS = new String[] { "B", "KB", "MB", "GB", "TB" };
    
    public static final HashSet<String> IMAGES_EXT = new HashSet<>();
    static {
	IMAGES_EXT.add(".jpg");
        IMAGES_EXT.add(".jpeg");
	IMAGES_EXT.add(".bmp");
	IMAGES_EXT.add(".png");
        IMAGES_EXT.add(".gif");
    } 
    
    
    private static Desktop desktop;
    static{
        if(Desktop.isDesktopSupported()){
            desktop = Desktop.getDesktop();
        }
    }
    
    private FileUtils(){}
    
    public static void visitFolderFiles(File root, int mode, boolean showHidden, Consumer<File> consumer){
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
    
    public static boolean createFolder(File folder){
        if(!folder.isDirectory()){
            try{
                return folder.mkdirs();
            }catch(SecurityException ex){}
        }
        return false;
    }

    public static File moveFileTo(File file, String folder){
        File tempDest = new File(folder, file.getName());
        
        //FILEPATH LENGTH
        int tempLength = tempDest.getPath().length();
        if(tempLength > FILEPATH_MAX_LENGTH){
            String t[] = FileUtils.splitFilename(file);
            int toRemove = FILEPATH_MAX_LENGTH - tempLength;
            int nameLength = t[0].length();
            if(nameLength > toRemove){
                t[0] = t[0].substring(0, nameLength - toRemove);
                tempDest = new File(folder, (t[0] + t[1]));
            }else{
                return null;
            }
        }
        
        //DUPLICATED NAME
        for(int n=1; tempDest.exists(); n++){
            String t[] = FileUtils.splitFilename(file);
            tempDest = new File(folder, (t[0]+" ("+n+")"+t[1]));
        }
        
        try{
            if(file.renameTo(tempDest)){
                return tempDest;
            }
        }catch(Exception ex){}
        return null;
    }

    public static boolean removeFile(File file) throws UnsupportedOperationException {
        if(desktop == null){
            throw new UnsupportedOperationException("move to trash action not supported");
        }else{
            try{
                return desktop.moveToTrash(file);
            }catch(IllegalArgumentException|SecurityException ex){ //file not found, no access, or no trash support.
                return false;
            }
        }
    }
    
    public static boolean deleteFile(File file){
        try{
            return file.delete();
        }catch(SecurityException ex){
            return false;
        }
    }
    
    public static String[] splitFilename(File file){
        String filename = file.getName();
        int extIndex = filename.lastIndexOf(".");
        if(extIndex != -1) {
            String[] tokens = new String[2];
            tokens[0] = filename.substring(0, extIndex);
            tokens[1] = filename.substring(extIndex);
            return tokens;
        }
        return new String[]{filename, ""};
    }
    
    public static boolean isImage(File file){
        return IMAGES_EXT.contains(splitFilename(file)[1].toLowerCase());
    }
    
    public static String getFormattedFileSize(File file){
        return formatFilesize(getFileSize(file));
    }
    
    private static long getFileSize(File file){
        try{
            return file.length();
        }catch(SecurityException ex){
            return 0L;
        }
    }
    
    private static String formatFilesize(long size) {
        if(size <= 0) return "0";
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + SIZE_UNITS[digitGroups];
    }
    
    public static void locateOnDisk(String path) throws UnsupportedOperationException {
        if(desktop == null){
            throw new UnsupportedOperationException("locate on disk action not supported");
        }else{
            try{
                desktop.browseFileDirectory(new File(path));
            }catch(Exception exD){
                Logger.log.print(Level.INFO, "failed to use desktop browse file method", exD);
                String os = System.getProperty("os.name").toLowerCase();
                Runtime runtime = Runtime.getRuntime();
                IOException exception = null;

                if(os.contains("win")){
                    try { runtime.exec("explorer.exe /select," + path);
                    } catch (IOException ex) {exception = ex;}
                }else if(os.contains("mac")){
                    try { runtime.exec("open -R "+path); //not tested
                    } catch (IOException ex) {exception = ex;}
                }else if(os.contains("nix") || os.contains("nux")){
                    try { runtime.exec("nautilus --select " + path); //not tested
                    } catch (IOException ex) {exception = ex;}
                }

                if(exception != null){
                   Logger.log.print(Level.ERROR, exception);
                }
            }
        }
    }
    
}
