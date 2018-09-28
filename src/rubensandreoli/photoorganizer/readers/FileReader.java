package rubensandreoli.photoorganizer.readers;
import rubensandreoli.photoorganizer.main.Frame;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileReader {
	private BufferedImage img = null;
	private Image scaledImg;
	
	public FileReader (String photo){
		try {
		    img = ImageIO.read(new File(photo));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		if (img.getHeight() > Frame.getLabelHeight() || img.getHeight() > Frame.getLabelWidth()){
			double reduction;
			if(img.getHeight() >= img.getWidth()){
				reduction = Frame.getLabelHeight()/(double)img.getHeight();
			}else{
				reduction = Frame.getLabelWidth()/(double)img.getWidth();
			}
			double newHeight = img.getHeight()*reduction;
			double newWidth = img.getWidth()*reduction;
			scaledImg = img.getScaledInstance((int)newWidth ,(int)newHeight , Image.SCALE_SMOOTH);
		}else{
			//scaledImg = img;
			double scaling;
			if(img.getHeight() >= img.getWidth()){
				scaling = Frame.getLabelHeight()/(double)img.getHeight();
			}else{
				scaling = Frame.getLabelWidth()/(double)img.getWidth();
			}
			double newHeight = img.getHeight()*scaling;
			double newWidth = img.getWidth()*scaling;
			scaledImg = img.getScaledInstance((int)newWidth ,(int)newHeight , Image.SCALE_SMOOTH);
		}
	}

	public Image getScaledImg() {
		return scaledImg;
	}
	
}
