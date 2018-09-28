package rubensandreoli.photoorganizer.main;

import rubensandreoli.photoorganizer.actions.CreateFolder;
import rubensandreoli.photoorganizer.actions.TransferFile;
import rubensandreoli.photoorganizer.readers.FolderReader;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Frame extends JFrame {

	private static final long serialVersionUID = -6369458451386755963L;
	private static JLabel label = new JLabel("");
	private String folder;
	private FolderReader folderInfo;
	private static JTextField fileNameField;
	protected static DefaultListModel<String> modelIn = new DefaultListModel<String>();
	protected static DefaultListModel<String> modelOut = new DefaultListModel<String>();
	private JList<String> jListIn = new JList<String>(modelIn);
	private JList<String> jListOut = new JList<String>(modelOut);
	private static JTextField counterField;
	private static JTextField fileNumberField;
	private JTextField folderNameField;

	public Frame() {
		setResizable(false);
		JPanel contentPane;
		setTitle("Photo Organizer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1240, 1024);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(64, 64, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(Color.WHITE);
		label.setBounds(10, 11, 1214, 822);
		contentPane.add(label);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(572, 897, 89, 23);
		contentPane.add(btnBack);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBounds(572, 863, 89, 23);
		contentPane.add(btnNext);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 844, 1214, 8);
		contentPane.add(separator);
		
		fileNameField = new JTextField();
		fileNameField.setHorizontalAlignment(SwingConstants.CENTER);
		fileNameField.setFont(new Font("Tahoma", Font.BOLD, 11));
		fileNameField.setEditable(false);
		fileNameField.setBounds(964, 864, 260, 20);
		contentPane.add(fileNameField);
		fileNameField.setColumns(10);
		
		JScrollPane scrollPaneOuter = new JScrollPane();
		scrollPaneOuter.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneOuter.setBounds(289, 863, 260, 122);
		contentPane.add(scrollPaneOuter);
		
		scrollPaneOuter.setViewportView(jListOut);
		
		JScrollPane scrollPaneIn = new JScrollPane();
		scrollPaneIn.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneIn.setBounds(684, 863, 260, 122);
		contentPane.add(scrollPaneIn);
		
		scrollPaneIn.setViewportView(jListIn);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(572, 962, 89, 23);
		contentPane.add(btnDelete);
		
		counterField = new JTextField();
		counterField.setFont(new Font("Tahoma", Font.BOLD, 11));
		counterField.setHorizontalAlignment(SwingConstants.CENTER);
		counterField.setEditable(false);
		counterField.setBounds(10, 898, 75, 20);
		contentPane.add(counterField);
		counterField.setColumns(10);
		
		fileNumberField = new JTextField();
		fileNumberField.setFont(new Font("Tahoma", Font.BOLD, 11));
		fileNumberField.setHorizontalAlignment(SwingConstants.CENTER);
		fileNumberField.setBounds(1149, 898, 75, 20);
		contentPane.add(fileNumberField);
		fileNumberField.setColumns(10);
		
		folderNameField = new JTextField();
		folderNameField.setFont(new Font("Tahoma", Font.BOLD, 11));
		folderNameField.setEditable(false);
		folderNameField.setBounds(10, 864, 260, 20);
		contentPane.add(folderNameField);
		folderNameField.setColumns(10);


		label.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked (MouseEvent e){
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK){
					JFileChooser fileChooserFolder = new JFileChooser();
					fileChooserFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnFolderVal = fileChooserFolder.showOpenDialog(getParent()); 
					if(returnFolderVal == JFileChooser.APPROVE_OPTION){
						folder =  fileChooserFolder.getSelectedFile().getAbsolutePath();
						folderInfo = new FolderReader(folder);
						FillFrame fillFrame = new FillFrame(folderInfo);
						fillFrame.fillListIn();
						fillFrame.fillListOut();
						folderNameField.setText(folder);
					}
				}
				if (e.getModifiers() == MouseEvent.BUTTON1_MASK){


				}
			}
		});
		
		btnNext.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				if (folderInfo.getFileNumber()+1 < folderInfo.getNumberOfFiles()){
					folderInfo.addFileNumber(1);
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					new FillFrame(folderInfo);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		
		btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				if (folderInfo.getFileNumber() > 0){
					folderInfo.addFileNumber(-1);
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					new FillFrame(folderInfo);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		
		jListIn.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evt){
		        @SuppressWarnings("unchecked")
				JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2 && !list.isSelectionEmpty()){
		        	int firstIndex = folderInfo.getFilesList().get(folderInfo.getFileNumber()).lastIndexOf("\\");
					String fileName = folderInfo.getFilesList().get(folderInfo.getFileNumber()).substring(firstIndex);
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					new TransferFile(folder+fileName, folder+"\\"+list.getSelectedValue()+fileName, folderInfo);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		        }
		        if(evt.getModifiers() == MouseEvent.BUTTON3_MASK && folder != null){
		        	CreateFolder newFolderIn = new CreateFolder(folder, folderInfo);
		        	if(newFolderIn.folderIn()){
		        		FillFrame fillFrame = new FillFrame(folderInfo);
		        	    fillFrame.fillListIn();
		        	}
		        }
		    }
		});
		
		jListOut.addMouseListener(new MouseAdapter(){
		    public void mouseClicked(MouseEvent evt){
		        @SuppressWarnings("unchecked")
				JList<String> list = (JList<String>)evt.getSource();
		        if (evt.getClickCount() == 2 && !list.isSelectionEmpty()){
		        	int firstIndex = folderInfo.getFilesList().get(folderInfo.getFileNumber()).lastIndexOf("\\");
					String fileName = folderInfo.getFilesList().get(folderInfo.getFileNumber()).substring(firstIndex);
		        	int lastIndex = folder.lastIndexOf("\\");
		            String rootFolder = folder.substring(0, lastIndex);
		            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		            new TransferFile(folder+fileName, rootFolder+"\\"+list.getSelectedValue()+fileName, folderInfo);
		            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		        }
		        if(evt.getModifiers() == MouseEvent.BUTTON3_MASK && folder != null){
		        	CreateFolder newFolderOut = new CreateFolder(folder, folderInfo);
		        	if(newFolderOut.folderOut()){
		        		FillFrame fillFrame = new FillFrame(folderInfo);
		        	    fillFrame.fillListOut();
		        	}
		        }
		    }
		});
		
		fileNumberField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){ 
					if (folder != null){
						folderInfo.setFileNumber(Integer.parseInt(fileNumberField.getText())-1);
						new FillFrame(folderInfo);
		           }else{
		        	   fileNumberField.setText(null);
		           }
		        }
			}
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		});
		
	}

	public static int getLabelHeight() {
		return label.getHeight();
	}

	public static int getLabelWidth() {
		return label.getWidth();
	}
	
	public static void setFileNameField(String fileName){
		fileNameField.setText(fileName);
	}
	
	public static void setCounterField(String numberOfFiles){
		counterField.setText(numberOfFiles);
	}
	
	public static void setFileNumberField(String fileNumber){
		fileNumberField.setText(fileNumber);
	}
	
	public static void setLabelIcon(ImageIcon resizedImage){
		label.setIcon(resizedImage);
	}

}
