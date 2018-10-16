package rubensandreoli.imageorganizer.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

public class FolderIO {
	    
    private static final HashSet<String> IMAGE_EXT;
    static {
	IMAGE_EXT = new HashSet<>();
	IMAGE_EXT.add(".jpg");
	IMAGE_EXT.add(".png");
	IMAGE_EXT.add(".bmp");
    }

    public class Image {
	public final String path;
	public final String name;
	public final String extension;
	
	private Image(final String path, final String fullName, final String extension){
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
		int startIndex = fileFullName.lastIndexOf(".");
		if(startIndex == -1) continue;
		String fileExtension = fileFullName.substring(startIndex);
		if(IMAGE_EXT.contains(fileExtension)){
		    images.add(new Image(f.getAbsolutePath(), fileFullName, fileExtension));
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
	List<String> folderList = subFolder? subFolders:rootFolders;
	if(folderList.contains(folderName))
	    throw new IOException("Folder \""+folderName+"\" already exists!");
	
	String path = subFolder? folderPath:rootPath;
	File newDir = new File(path+"\\"+folderName);
	if(newDir.mkdir()) folderList.add(folderName);
	else throw new IOException("Folder \""+newDir.getAbsolutePath()+"\" could not be created!");
    }

    public void tranferImage(int imagePos, String folderName, boolean subFolder) throws IOException{
	tranfer(images.get(imagePos), folderName, subFolder);
	deleteImage(imagePos);
    }
    
    private void tranfer(Image img, String folderName, boolean subFolder) throws IOException{
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
    }

    public boolean testFolder(String folderName, boolean subFolder) {
	String path = subFolder? folderPath:rootPath;
	File folder = new File(path+"\\"+folderName);
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
	else throw new IOException("Image \""+f.getAbsolutePath()+"\" could not be deleted!");
    }
    
    private transient boolean splitting = true;
    
    public void startSplitter(int charPos, JProgressBar progress){
	if(charPos <= 0) return;
	progress.setMaximum(images.size());
	progress.setValue(0);
	splitting = true;
	Iterator<Image> itr = images.iterator();
	new Thread(new Runnable(){
	    @Override
	    public void run() {
		while(itr.hasNext() && splitting){
		    Image i = itr.next();
		    if(i.name.length() > charPos){
			String folderName = i.name.substring(0, charPos);
			if(!(new File(folderPath+"\\"+folderName).isDirectory())){
			    try {
				createFolder(folderName, true);
				subFolders.add(folderName);
			    } catch (IOException ex) {}
			}
			try {
			    tranfer(i, folderName, true);
			    if(new File(i.path).delete()) itr.remove();
			    else throw new IOException("Image \""+i.path+"\" could not be deleted!");
			} catch (IOException ex) {}
		    }
		    progress.setValue(progress.getValue()+1);
		}
	    }
	}).start();
    }
    
    public void stopSplitter(){
	splitting = false;
    }
    
    
    public ArrayList<String> getRootFolders() {return rootFolders;}
    public ArrayList<String> getSubFolders() {return subFolders;}
    public String getImagePath(int imagePos){return images.get(imagePos).path;}
    public int getNumImages(){return images.size();}
    public String getFolderPath() {return folderPath;}

}
