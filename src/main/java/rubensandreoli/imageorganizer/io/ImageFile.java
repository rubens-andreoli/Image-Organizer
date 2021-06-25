/*
 * Copyright (C) 2021 Rubens A. Andreoli Jr.
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
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import rubensandreoli.commons.others.Level;
import rubensandreoli.commons.others.Logger;
import rubensandreoli.commons.utils.FileUtils;

/**
 * References:
 * <br>
 * https://stackoverflow.com/questions/43783824/java-not-able-to-open-finder-window-having-space-in-path<br>
 * https://unix.stackexchange.com/questions/364997/open-a-directory-in-the-default-file-manager-and-select-a-file
 * 
 * @author Rubens A. Andreoli Jr
 */
public class ImageFile {

    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS ">
    private static final int NAME_INDEX = 0;
    private static final int EXTENSION_INDEX = 1;
    private static final int DIMENSIONS_INDEX = 2;
    private static final int SIZE_INDEX = 3;
    private static final int POSITION_INDEX = 4;
    public static final int INFO_SIZE = 5;
    public static final HashSet<String> IMAGES_EXT = new HashSet<>();
    static {
	IMAGES_EXT.add(".jpg");
        IMAGES_EXT.add(".jpeg");
	IMAGES_EXT.add(".bmp");
	IMAGES_EXT.add(".png");
        IMAGES_EXT.add(".gif");
//        IMAGES_EXT.add(".webp");
    } 
    // </editor-fold>
    
    private Image image;

    private final String path;
    private int height, width;
    private boolean failed;
    
    private final String[] info;
    
    private ImageFile(File file){
        path = file.getPath();
        info = new String[INFO_SIZE];
        info[NAME_INDEX] = "Name: " + FileUtils.getFilename(path);
        info[EXTENSION_INDEX] = "Extension: " + FileUtils.getExtension(path); 
        info[SIZE_INDEX] = "Size: " + FileUtils.getFormattedFileSize(file);
    }
    
    public void locateOnDisk(){
        try{
            Desktop.getDesktop().browseFileDirectory(new File(path));
        }catch(Exception exD){
            Logger.log.print(Level.INFO, "failed to use desktop browse file method", exD);
            final String os = System.getProperty("os.name").toLowerCase();
            final Runtime runtime = Runtime.getRuntime();
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

    // <editor-fold defaultstate="collapsed" desc=" SETTERS ">
    private void setImage(Image image){
        this.image = image;
    }
    
    private void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        info[DIMENSIONS_INDEX] = "Dimensions: " + (width==0? "?":width) + "x" + (height==0? "?":height);
    }
    
    private void setPosition(int pos, int total){
        info[POSITION_INDEX] = "Position: " + ++pos + " of " + total;
    }
    
    private void setFailed(){
        setDimensions(0, 0);
        failed = true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" GETTERS ">
    public String getPath() {
        return path;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Image getImage() {
        return image;
    }

    public boolean isFailed() {
        return failed;
    }

    public String getInfo(int i) {
        return info[i];
    }
    // </editor-fold>

    public static ImageFile build(File file, int pos, int total){
        final ImageFile image = new ImageFile(file);
        image.setPosition(pos, total);
        if(FileUtils.getExtension(file.getPath()).endsWith(".gif")){
            try{
                final ImageIcon iconImage = new ImageIcon(file.getPath());
                final Image i = iconImage.getImage();
                if(i != null){
                    image.setImage(i);
                    image.setDimensions(iconImage.getIconWidth(), iconImage.getIconHeight());
                }else{
                    image.setFailed();
                    Logger.log.print(Level.WARNING, new IOException("failed loading gif"));
                }
            }catch (SecurityException ex) {//if can't access
                image.setFailed();
                Logger.log.print(Level.WARNING, ex);
            }
        }else{
            try {
                final BufferedImage bImage = ImageIO.read(file);
                if(bImage != null){
                    image.setImage(bImage);
                    image.setDimensions(bImage.getWidth(), bImage.getHeight());
                }else{
                    image.setFailed();
                    Logger.log.print(Level.WARNING, new IOException("unsupported image codification"));
                }
            } catch (IOException ex) { //can't read or failure reading
                image.setFailed();
                Logger.log.print(Level.WARNING, ex);
            }
        }
        return image;
    }
    
    public static boolean isImage(File file){
        return IMAGES_EXT.contains(FileUtils.getExtension(file.getPath()));
    }
    
}
