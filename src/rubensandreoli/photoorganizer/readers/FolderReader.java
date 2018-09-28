package rubensandreoli.photoorganizer.readers;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class FolderReader {
	private LinkedList<String> filesList = new LinkedList<String>();
	private int numberOfFiles;
	private int fileNumber;
	private ArrayList<String> foldersList = new ArrayList<String>();
	private ArrayList<String> rootFoldersList = new ArrayList<String>();
	
	public FolderReader(String folder){
		File folderFile = new File(folder);
		File[] listOfFiles = folderFile.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if(!listOfFiles[i].isDirectory()){
				String fileFullName = listOfFiles[i].getName();
				int extensionIndex = fileFullName.lastIndexOf(".");
				String fileExtension = fileFullName.substring(extensionIndex);
			
				if (fileExtension.equals(".jpg") || fileExtension.equals(".png")){
					filesList.add(folder+"\\"+fileFullName);
					numberOfFiles++;
				}
			}else{
				foldersList.add(listOfFiles[i].getName());
			}
		}
		
		int lastIndex = folder.lastIndexOf("\\");
		String rootFolder = folder.substring(0,lastIndex);
		File rootFolderFile = new File(rootFolder);
		File[] listOfFolders = rootFolderFile.listFiles();
		for (int i = 0; i < listOfFolders.length; i++) {
			if (listOfFolders[i].isDirectory()){
				rootFoldersList.add(listOfFolders[i].getName());
			}
		}
		
	}

	public ArrayList<String> getFoldersList() {
		return foldersList;
	}

	public ArrayList<String> getRootFoldersList() {
		return rootFoldersList;
	}

	public int getNumberOfFiles() {
		return numberOfFiles;
	}

	public LinkedList<String> getFilesList() {
		return filesList;
	}
	
	public int getFileNumber() {
		return fileNumber;
	}

	public void addFileNumber(int add) {
		this.fileNumber += add;
	}
	
	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}
	
	public void addNumberOfFiles(int add) {
		this.numberOfFiles += add;
	}
	
	public void addRootFoldersList(String newFolder){
		rootFoldersList.add(newFolder);
	}
	
	public void addFoldersList(String newFolder){
		foldersList.add(newFolder);
	}

}
