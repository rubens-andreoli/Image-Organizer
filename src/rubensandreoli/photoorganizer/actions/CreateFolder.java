package rubensandreoli.photoorganizer.actions;

import rubensandreoli.photoorganizer.readers.FolderReader;

import java.io.File;

import javax.swing.JOptionPane;

public class CreateFolder {
	private String folder;
	private FolderReader folderInfo;
	
	public CreateFolder(String folder, FolderReader folderInfo){
		this.folder = folder;
		this.folderInfo = folderInfo;
	}
	
	public Boolean folderIn(){
		String newFolderName = (String)JOptionPane.showInputDialog(
				null,
                "New folder name:",
                "Create New Folder",
                JOptionPane.PLAIN_MESSAGE);

		if ((newFolderName != null) 
				&& (newFolderName.length() > 0) 
				&& !newFolderName.contains(".") 
				&& !folderInfo.getFoldersList().contains(newFolderName)){
			File newDir = new File(folder+"\\"+newFolderName);
			newDir.mkdir();
			folderInfo.addFoldersList(newFolderName);
			return true;
		}
		
		return false;
	}
	
	public Boolean folderOut(){
		String newFolderName = (String)JOptionPane.showInputDialog(
				null,
                "New folder name:",
                "Create New Folder",
                JOptionPane.PLAIN_MESSAGE);

		if ((newFolderName != null) 
				&& (newFolderName.length() > 0) 
				&& !newFolderName.contains(".") 
				&& !folderInfo.getRootFoldersList().contains(newFolderName)){
			int lastIndex = folder.lastIndexOf("\\");
			String rootFolder = folder.substring(0, lastIndex);
			File newDir = new File(rootFolder+"\\"+newFolderName);
			newDir.mkdir();
			folderInfo.addRootFoldersList(newFolderName);
			return true;
		}
		
		return false;
	}
	
}
