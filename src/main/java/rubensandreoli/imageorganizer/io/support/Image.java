/*
 * Copyright (C) 2021 Morus
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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import rubensandreoli.commons.utils.FileUtils;

public class Image {

    private BufferedImage image;
    private File file;
    
    private String path;
    private String name;
    private String format;
    private long size;
    private int pos, total;
    private int height, width;
    private String[] info;
    
    public Image(File file, int pos, int total){
        try {
            image = ImageIO.read(file);
            height = image.getHeight();
            width = image.getWidth();
        } catch (IOException ex) {}

        this.file = file;
        this.pos = pos;
        this.total = total;
        path = file.getPath();
        name = FileUtils.getFilename(path);
        format = FileUtils.getExtension(path);
        size = FileUtils.getFileSize(file);
    }

    public String[] getInfo(){
        if(info == null){
            info = new String[5];
            info[0] = "Name: " + name;
            info[1] = "Extension: " + format;
            info[2] = "Dimensions: " + height + "x" + width;
            info[3] = "Size: " + formatFilesize(size);
            info[4] = "Position: " + pos + " of " + total;
        }
        return info;
    }
    
    public static String formatFilesize(long size) { //TODO: move to commons
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return path;
    }
   
}
