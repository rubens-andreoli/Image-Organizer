package rubensandreoli.photoorganizer.actions;
import rubensandreoli.photoorganizer.main.FillFrame;
import rubensandreoli.photoorganizer.readers.FolderReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JOptionPane;

public class TransferFile {
	public TransferFile(String sourcePath, String destPath, FolderReader filesList){
		File source = new File(sourcePath);
		File dest = new File(destPath);
		
		if (dest.exists() && !dest.isDirectory()){
			int extensionIndex = destPath.lastIndexOf(".");
			destPath = destPath.substring(0, extensionIndex) + "(PO)" + destPath.substring(extensionIndex);
			dest = new File(destPath);
		}
		
		int fileNumber = filesList.getFileNumber();
		filesList.getFilesList().remove(fileNumber);
		
		
		filesList.addNumberOfFiles(-1);
		new FillFrame (filesList);
		
		try {
			Files.copy(source.toPath(), dest.toPath());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: Can't transfer file.");
		}		
		source.delete();
		
	}
}
