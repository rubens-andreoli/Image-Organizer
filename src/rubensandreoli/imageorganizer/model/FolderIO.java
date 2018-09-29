package rubensandreoli.imageorganizer.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public class FolderIO {
    
    private final String folderPath;
    private final String rootPath;
    
    private final ArrayList<String> rootFolders;
    private final ArrayList<String> subFolders;
    private final LinkedList<String> images;

    public FolderIO(final String folderPath){
	rootFolders = new ArrayList<String>();
	subFolders = new ArrayList<String>();
	images = new LinkedList<String>();
	
	this.folderPath = folderPath;
	rootPath = folderPath.substring(0, folderPath.lastIndexOf("\\"));
	
	File folder = new File(folderPath);
	File[] listOfFiles = folder.listFiles();
	for (File f : listOfFiles) {
	    String fileFullName = f.getName();
	    if (!f.isDirectory()) {
		int extensionIndex = fileFullName.lastIndexOf(".");
		String fileExtension = fileFullName.substring(extensionIndex);
		if (fileExtension.equals(".jpg") || fileExtension.equals(".png")){
		    images.add(folder+"\\"+fileFullName);
		}
	    } else {
		subFolders.add(fileFullName);
	    }
	}

	File rootFolder = new File(rootPath);
	listOfFiles = rootFolder.listFiles();
	for (File f : listOfFiles) {
	    if (f.isDirectory()) {
		rootFolders.add(f.getName());
	    }
	}
    }

    public void createFolder(String folderName, boolean subFolder) throws IOException{
	if(folderName == null || folderName.length() <= 0 || folderName.contains("."))
	    throw new IOException("Folder name \""+folderName+"\" not valid!");
	
	List<String> folderList = subFolder? subFolders:rootFolders;
	if(folderList.contains(folderName))
	    throw new IOException("Folder \""+folderName+"\" already exists!");
	
	String path = subFolder? folderPath:rootPath;
	File newDir = new File(path+"\\"+folderName);
	if(newDir.mkdir()) folderList.add(folderName);
	else throw new IOException("Folder \""+newDir.getName()+"\" could not be created!");
    }

    public void tranferImage(int imagePos, String folderName, boolean subFolder) throws IOException{
	String imagePath = images.get(imagePos);
	File source = new File(imagePath);
	
	int firstIndex = imagePath.lastIndexOf("\\");
	String imageName = imagePath.substring(firstIndex);
	String path = subFolder? folderPath+"\\"+folderName : rootPath+"\\"+folderName;
	File dest = new File(path+"\\"+imageName);
	
	if (dest.exists() && !dest.isDirectory()){
	    int extensionIndex = imageName.lastIndexOf(".");
	    imageName = imageName.substring(0, extensionIndex) + "(PO)" + imageName.substring(extensionIndex);
	    dest = new File(path+"\\"+imageName);
	}
	
	try{
	    Files.copy(source.toPath(), dest.toPath());
	}catch(IOException e){
	    throw new IOException("Image could not be copied to destination!\n"+dest.getAbsolutePath());
	}

	if(source.delete()){
	    images.remove(imagePos);
	}else{
	    throw new IOException("Source image \""+imagePath+"\" could not be deleted!");
	}
    }

    public boolean testFolder(String folderName, boolean subFolder) {
	String path = subFolder? folderPath+"\\"+folderName : rootPath+"\\"+folderName;
	File folder = new File(path);
	if(folder.exists() && folder.isDirectory()){ 
	    return true;
	}else{
	    if(subFolder) subFolders.remove(folderName);
	    else rootFolders.remove(folderName);
	    return false;
	}
    }
    
    public BufferedImage loadImage(int imagePos) throws IOException{
	return ImageIO.read(new File(images.get(imagePos)));
    }
    
    public void deleteImage(int imagePos) throws IOException{
	File f = new File(images.get(imagePos));
	if(f.delete()) images.remove(imagePos);
	else throw new IOException("Image \""+images.get(imagePos)+"\" could not be deleted!");
    }
    
    public ArrayList<String> getRootFolders() {return rootFolders;}
    public ArrayList<String> getSubFolders() {return subFolders;}
    public String getImagePath(int imagePos){return images.get(imagePos);}
    public int getNumImages(){return images.size();}
    public String getFolderPath() {return folderPath;}

}
