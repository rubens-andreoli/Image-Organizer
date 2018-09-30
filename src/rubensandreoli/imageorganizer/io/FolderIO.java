package rubensandreoli.imageorganizer.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public class FolderIO {
	    
    private static final String FOLDER_NAME_REGEX = "^(\\w+\\.?)*\\w+$";
    private static final HashSet<String> IMAGE_EXT;
    static  {
	IMAGE_EXT = new HashSet<>();
	IMAGE_EXT.add(".jpg");
	IMAGE_EXT.add(".png");
	IMAGE_EXT.add(".bmp");
    }
    
    public class Image{
	public final String path;
	public final String name;
	public final String extension;
	
	private Image(String path, String fullName, String extension){
	    this.path = path;
	    this.extension = extension;
	    name = fullName.substring(0, fullName.length()-4);
	}
    }
    
    private final String folderPath;
    private final String rootPath;
    
    private final ArrayList<String> rootFolders;
    private final ArrayList<String> subFolders;
    private final LinkedList<Image> images;

    public FolderIO(final String folderPath){
	rootFolders = new ArrayList<String>();
	subFolders = new ArrayList<String>();
	images = new LinkedList<Image>();
	
	this.folderPath = folderPath;
	rootPath = folderPath.substring(0, folderPath.lastIndexOf("\\"));
	
	File folder = new File(folderPath);
	File[] listOfFiles = folder.listFiles();
	for (File f : listOfFiles) {
	    String fileFullName = f.getName();
	    if (!f.isDirectory()) {
		String fileExtension = fileFullName.substring(fileFullName.lastIndexOf("."));
		if(IMAGE_EXT.contains(fileExtension)){
		    images.add(new Image(f.getAbsolutePath(), f.getName(), fileExtension));
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
	if(!folderName.matches(FOLDER_NAME_REGEX))
	    throw new IOException("Folder name \""+folderName+"\" not valid!");
	
	List<String> folderList = subFolder? subFolders:rootFolders;
	if(folderList.contains(folderName))
	    throw new IOException("Folder \""+folderName+"\" already exists!");
	
	String path = (subFolder? folderPath:rootPath);
	File newDir = new File(path+"\\"+folderName);
	if(newDir.mkdir()) folderList.add(folderName);
	else throw new IOException("Folder \""+newDir.getName()+"\" could not be created!");
    }

    public void tranferImage(int imagePos, String folderName, boolean subFolder) throws IOException{
	Image img = images.get(imagePos);
	File source = new File(img.path);
	
	String path = subFolder? folderPath:rootPath;
	File dest = new File(String.format("%s\\%s\\%s%s", path, folderName, img.name, img.extension));
	
	for(int i=1; dest.exists(); i++){
	    dest = new File(String.format("%s\\%s\\%s (%d)%s", path, folderName, img.name, i, img.extension));
	}
	
	try{
	    Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
	}catch(IOException e){
	    throw new IOException("Image could not be copied to destination!\n"+dest.getAbsolutePath(), e);
	}

	if(source.delete()){
	    images.remove(imagePos);
	}else{
	    throw new IOException("Source image \""+img.path+"\" could not be deleted!");
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
	return ImageIO.read(new File(images.get(imagePos).path));
    }
    
    public void deleteImage(int imagePos) throws IOException{
	File f = new File(images.get(imagePos).path);
	if(f.delete()) images.remove(imagePos);
	else throw new IOException("Image \""+images.get(imagePos).path+"\" could not be deleted!");
    }
    
    public ArrayList<String> getRootFolders() {return rootFolders;}
    public ArrayList<String> getSubFolders() {return subFolders;}
    public String getImagePath(int imagePos){return images.get(imagePos).path;}
    public int getNumImages(){return images.size();}
    public String getFolderPath() {return folderPath;}

}
