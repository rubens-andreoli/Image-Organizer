package rubensandreoli.photoorganizer.main;

import rubensandreoli.photoorganizer.readers.FileReader;
import rubensandreoli.photoorganizer.readers.FolderReader;

import javax.swing.ImageIcon;

public class FillFrame{
	private FolderReader folderInfo;
	
	public FillFrame(FolderReader folderInfo){
		this.folderInfo = folderInfo;
		FileReader fileReader = new FileReader(folderInfo.getFilesList().get(folderInfo.getFileNumber()));
		ImageIcon resizedImage = new ImageIcon(fileReader.getScaledImg()); 
		Frame.setLabelIcon(resizedImage);
		int firstIndex = folderInfo.getFilesList().get(folderInfo.getFileNumber()).lastIndexOf("\\")+1;
		String fileName = folderInfo.getFilesList().get(folderInfo.getFileNumber()).substring(firstIndex);
		Frame.setFileNameField(fileName);
		Frame.setCounterField(Integer.toString(folderInfo.getNumberOfFiles()));
		Frame.setFileNumberField(Integer.toString(folderInfo.getFileNumber()+1));
	}
	
	public void fillListOut(){
		Frame.modelOut.clear();
	    for(String subRootFolder : folderInfo.getRootFoldersList()){
			Frame.modelOut.addElement(subRootFolder);
		}
	}
	
	public void fillListIn(){
		Frame.modelIn.clear();
	    for(String subFolder : folderInfo.getFoldersList()){
			Frame.modelIn.addElement(subFolder);
		}
	}
}
