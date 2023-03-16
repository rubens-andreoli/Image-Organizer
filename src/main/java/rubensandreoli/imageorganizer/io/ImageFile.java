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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import rubensandreoli.imageorganizer.io.support.FileUtils;

public class ImageFile {

    public static int INFO_SIZE = 5;

    private final String path, name, extension, size;
    private String dimensions, position;
    private int height, width;
    private Image image;
    private boolean failed;
    
    private ImageFile(File file){
        path = file.getPath();
        
        String[] t = FileUtils.splitFilename(file);
        name = t[0];
        extension = t[1];
        size = FileUtils.getFormattedFileSize(file);
    }
    
    public void locateOnDisk() {
        FileUtils.locateOnDisk(path);
    }
    
    //<editor-fold defaultstate="collapsed" desc="GETTERS">
    private boolean isGIF(){
        return extension.equals(".gif");
    }
    
    public String getInfo(int i) {
        switch(i){
            case 0: return "Name: " + name;
            case 1: return "Extension: " + extension;
            case 2: return "Dimensions: " + dimensions;
            case 3: return "Size: " + size;
            case 4: return "Position: " + position;
            default: return "";
        }
    }
    
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
    
    private boolean isLoaded(){
        return (image != null || failed);
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="SETTERS">
    private void setImage(Image image){
        this.image = image;
    }
    
    private void setPosition(int pos, int total){
        position = ++pos + " of " + total;
    }
    
    private void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        dimensions = (width==0? "?":width) + "x" + (height==0? "?":height);
    }
    
    private void setFailed(){
        setDimensions(0, 0);
        failed = true;
    }
    // </editor-fold>

    static ImageFile load(File file, int pos, int total){
        ImageFile fImage = new ImageFile(file);
        fImage.setPosition(pos, total);
        
        if(fImage.isGIF()){
            try{
                ImageIcon iImage = new ImageIcon(file.getPath());
                Image i = iImage.getImage();
                if(i != null){
                    fImage.setImage(i);
                    fImage.setDimensions(iImage.getIconWidth(), iImage.getIconHeight());
                }else{
                    fImage.setFailed();
//                    Logger.log.print(Level.WARNING, new IOException("failed loading gif"));
                }
            }catch (SecurityException ex) {//if can't access
                fImage.setFailed();
//                Logger.log.print(Level.WARNING, ex);
            }
        }else{
            try {
                BufferedImage bImage = ImageIO.read(file);
                if(bImage != null){
                    fImage.setImage(bImage);
                    fImage.setDimensions(bImage.getWidth(), bImage.getHeight());
                }else{
                    fImage.setFailed();
//                    Logger.log.print(Level.WARNING, new IOException("unsupported image codification"));
                }
            } catch (IOException ex) { //can't read or failure reading
                fImage.setFailed();
//                Logger.log.print(Level.WARNING, ex);
            }
        }
        return fImage;
    }

}
