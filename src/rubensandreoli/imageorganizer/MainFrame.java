package rubensandreoli.imageorganizer;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import rubensandreoli.imageorganizer.io.FolderIO;
import rubensandreoli.imageorganizer.io.HistoryIO;

//icon.png: https://all-free-download.com/free-icon/download/free-folder-icons-icons-pack_120784.html

public class MainFrame extends javax.swing.JFrame {

    private static final String PROGRAM_NAME = "ImageOrganizer";
    private static final String PROGRAM_VERSION = "v1.0.0";
    private static final String PROGRAM_YEAR = "2018";
    
    private FolderIO folderIO;
    private HistoryIO historyIO;
    
    private int currentPos = -1;
    
    public MainFrame() {
	initComponents();
	historyIO = new HistoryIO();
	try {
	    historyIO.loadHistory();
	} catch (IOException ex) {
	    showError(ex);
	}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        flcFolder = new javax.swing.JFileChooser();
        pnlImage = new ImagePanel();
        txfImagePos = new javax.swing.JTextField();
        txfImageName = new javax.swing.JTextField();
        txfFolderPath = new javax.swing.JTextField();
        txfNumImages = new javax.swing.JTextField();
        sptImage = new javax.swing.JSeparator();
        pnlTools = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        lblInfo = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        sclFoldersOut = new javax.swing.JScrollPane();
        lstFoldersOut = new javax.swing.JList<>();
        sclFoldersIn = new javax.swing.JScrollPane();
        lstFoldersIn = new javax.swing.JList<>();
        btnDelete = new javax.swing.JButton();

        flcFolder.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photo Organizer");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
        setMinimumSize(new java.awt.Dimension(747, 551));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        pnlImage.setToolTipText("<html>Image preview<hr>Right-Click: load folder<br>Left-Click: move image<br>Mouse-Wheel: zoom</html>");
        pnlImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 317, Short.MAX_VALUE)
        );

        txfImagePos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfImagePos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfImagePos.setText("0");
        txfImagePos.setToolTipText("<html>Image position<hr>Enter: change to typed position</html>");
        txfImagePos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfImagePosKeyReleased(evt);
            }
        });

        txfImageName.setEditable(false);
        txfImageName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfImageName.setToolTipText("Image path");
        txfImageName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfImageName.setEnabled(false);

        txfFolderPath.setEditable(false);
        txfFolderPath.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfFolderPath.setToolTipText("Selected folder path");
        txfFolderPath.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfFolderPath.setEnabled(false);

        txfNumImages.setEditable(false);
        txfNumImages.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfNumImages.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfNumImages.setText("0");
        txfNumImages.setToolTipText("Number of images in selected folder");
        txfNumImages.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfNumImages.setEnabled(false);

        btnBack.setText("Back");
        btnBack.setToolTipText("<html>Previous image<hr>Shortcut: left arrow; mouse button 4</html>");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rubensandreoli/imageorganizer/images/about.png"))); // NOI18N
        lblInfo.setToolTipText("About");
        lblInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInfoMouseClicked(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.setToolTipText("<html>Next image<hr>Shortcut: right arrow; mouse button 5</html>");
        btnNext.setEnabled(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        sclFoldersOut.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstFoldersOut.setModel(new DefaultListModel<String>());
        lstFoldersOut.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstFoldersOut.setToolTipText("<html>Folders in the same level<hr>Double-Click: transfer image to folder<br>Right-Click: create new folder</html>");
        lstFoldersOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstFoldersOutMouseClicked(evt);
            }
        });
        sclFoldersOut.setViewportView(lstFoldersOut);

        sclFoldersIn.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstFoldersIn.setModel(new DefaultListModel<String>());
        lstFoldersIn.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstFoldersIn.setToolTipText("<html>Folders inside<hr>Double-Click: transfer image to folder<br>Right-Click: create new folder</html>");
        lstFoldersIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstFoldersInMouseClicked(evt);
            }
        });
        sclFoldersIn.setViewportView(lstFoldersIn);

        btnDelete.setText("Delete");
        btnDelete.setToolTipText("<html>Delete image<hr>Shortcut: delete key</html>");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlToolsLayout = new javax.swing.GroupLayout(pnlTools);
        pnlTools.setLayout(pnlToolsLayout);
        pnlToolsLayout.setHorizontalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sclFoldersOut, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblInfo)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete))
                .addGap(17, 17, 17)
                .addComponent(sclFoldersIn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlToolsLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBack, btnDelete, btnNext});

        pnlToolsLayout.setVerticalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolsLayout.createSequentialGroup()
                        .addComponent(btnNext)
                        .addGap(7, 7, 7)
                        .addComponent(btnBack)
                        .addGap(17, 17, 17)
                        .addComponent(lblInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete))
                    .addComponent(sclFoldersOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sclFoldersIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sptImage)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfNumImages, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfFolderPath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txfImagePos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfImageName, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sptImage, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txfFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfNumImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txfImageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfImagePos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlTools, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInfoMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON3 && folderIO != null){
	    new SplitterDialog(this, folderIO).setVisible(true);
	    loadImage();
	}else{
	    new AboutDialog(this, PROGRAM_NAME, PROGRAM_VERSION, PROGRAM_YEAR).setVisible(true);
	}
    }//GEN-LAST:event_lblInfoMouseClicked

    private void lstFoldersOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstFoldersOutMouseClicked
        clickList(evt, false);
    }//GEN-LAST:event_lstFoldersOutMouseClicked

    private void lstFoldersInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstFoldersInMouseClicked
        clickList(evt, true);
    }//GEN-LAST:event_lstFoldersInMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if(currentPos >= folderIO.getNumImages()-1) currentPos = 0;
	else currentPos++;
	loadImage();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if(currentPos > 0) currentPos--;
	else currentPos = folderIO.getNumImages()-1;
	loadImage();
    }//GEN-LAST:event_btnBackActionPerformed

    private void pnlImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlImageMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3){
            if(flcFolder.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                changeFolder(flcFolder.getSelectedFile().getAbsolutePath());
            }
        }
    }//GEN-LAST:event_pnlImageMouseClicked

    private void txfImagePosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfImagePosKeyReleased
	if(evt.getKeyCode() == KeyEvent.VK_ENTER){
	    if(folderIO == null){ 
		txfImagePos.setText("0");
	    }else{
		try{
		    int pos = Integer.parseInt(txfImagePos.getText())-1;
		    if(pos <= folderIO.getNumImages()-1 && pos >= 0 && pos != currentPos){
			currentPos = pos;
			loadImage();
		    }
		}catch (NumberFormatException ex){ 
		}finally{
		    txfImagePos.setText(String.valueOf(currentPos+1));
		}
	    }
	}
    }//GEN-LAST:event_txfImagePosKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(folderIO != null){
	    int choice = JOptionPane.showConfirmDialog(
		    this, 
		    "Do you like to permanently delete this image?", 
		    "Delete Image", 
		    JOptionPane.YES_NO_OPTION
	    );
	    if(choice == JOptionPane.YES_OPTION){
		try {
		    folderIO.deleteImage(currentPos);
		    txfNumImages.setText(String.valueOf(folderIO.getNumImages()));
		    loadImage();
		} catch (IOException ex) {
		    showError(ex);
		}
	    }
	}
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
         if(folderIO != null){
	    if(evt.getKeyCode() == KeyEvent.VK_RIGHT) btnNext.doClick();
	    if(evt.getKeyCode() == KeyEvent.VK_LEFT) btnBack.doClick();
	    if(evt.getKeyCode() == KeyEvent.VK_DELETE) btnDelete.doClick();
	 }
    }//GEN-LAST:event_formKeyReleased

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
	try {
	    historyIO.saveHistory();
	} catch (IOException ex) {}
    }//GEN-LAST:event_formWindowClosing

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
//        System.out.println(txfFolderPath.getWidth()+":"+txfImageName.getWidth());
    }//GEN-LAST:event_formComponentResized

    private void changeFolder(String folderPath){
	if(folderIO != null && currentPos > 0){ //add to history if not at initial position
	    historyIO.setFolderHistory(folderIO.getFolderPath(), currentPos);
	}
	pnlImage.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//	setCursor();
	folderIO = new FolderIO(folderPath);
//	setCursor();
	pnlImage.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	txfFolderPath.setText(folderPath);
	txfNumImages.setText(String.valueOf(folderIO.getNumImages()));
	populateList(true);
	populateList(false);
	if(folderIO.getNumImages() > 0){
	    currentPos = historyIO.getFolderHistory(folderPath);
	    loadImage();
	}
    }
  
    private void loadImage(){
	if(folderIO == null) return;
	if(folderIO.getNumImages() == 0){ //all images got deleted
	    ((ImagePanel)pnlImage).clear();
	    txfImagePos.setText("0");
	    txfImageName.setText("");
	}else{
	    if(currentPos >= folderIO.getNumImages()){ //restart position when last image is deleted
		currentPos = 0;
	    }
	    try { //load image
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		((ImagePanel)pnlImage).setImage(folderIO.loadImage(currentPos));
	    } catch (IOException ex) { //clear last image if fail
		showError(ex);
		((ImagePanel)pnlImage).clear();
	    } finally { //fill info even if fail
		txfImageName.setText(folderIO.getImagePath(currentPos));
		historyIO.setFolderHistory(folderIO.getFolderPath(), currentPos);
		txfImagePos.setText(String.valueOf(currentPos+1));
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    }
	}
	requestFocus();
	testButtons();
    }
    
    private void testButtons(){
	btnNext.setEnabled(false);
	btnBack.setEnabled(false);
	btnDelete.setEnabled(false);
	if(folderIO != null){
	    int numImages = folderIO.getNumImages();
	    if(numImages > 1){
		btnNext.setEnabled(true);
		btnBack.setEnabled(true);
		btnDelete.setEnabled(true);
	    }else if(numImages == 1){
		btnDelete.setEnabled(true);
	    }
	}
    }
    
    private void populateList(boolean subFolders){
	if(folderIO == null) return;
	List<String> list = subFolders? folderIO.getSubFolders():folderIO.getRootFolders();
	DefaultListModel<String> model = (DefaultListModel)(
		subFolders? lstFoldersIn.getModel():lstFoldersOut.getModel()
	    );
	
	model.clear();
	list.stream().forEach((folder) -> {
	    model.addElement(folder);
	});
    }
    
    private void clickList(MouseEvent e, boolean subFolders){
	if(folderIO == null) return;
	JList<String> list = (JList<String>)e.getSource();
	if (!list.isSelectionEmpty()){ //check if folder exist
	    if(!folderIO.testFolder(list.getSelectedValue(), subFolders)){ 
		populateList(subFolders);
	    }
	}
	if (e.getButton() == 1 && e.getClickCount() == 2 && !list.isSelectionEmpty()){ //tranfer image
	    try {
		folderIO.tranferImage(currentPos, list.getSelectedValue(), subFolders);
		txfNumImages.setText(String.valueOf(folderIO.getNumImages()));
		loadImage();
	    } catch (IOException ex) {
		showError(ex);
	    }
	}else if(e.getButton() == 3){ //create new folder
	    String newFolderName = JOptionPane.showInputDialog(
		    this, 
		    "New folder name:", 
		    "Create New Folder", 
		    JOptionPane.PLAIN_MESSAGE
	    );
	    if(newFolderName != null){
		try {
		    folderIO.createFolder(newFolderName, subFolders);
		    populateList(subFolders);
		} catch (IOException ex) {
		    showError(ex);
		}
	    }
	}else if(e.getButton() == 4){ 
	    btnBack.doClick();
	}else if(e.getButton() == 5){
	    btnNext.doClick();
	}
    }
    
    private void showError(Exception ex){
	JOptionPane.showMessageDialog(
		this, 
		ex.getMessage(), 
		"Erro!", 
		JOptionPane.WARNING_MESSAGE
	);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNext;
    private javax.swing.JFileChooser flcFolder;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JList<String> lstFoldersIn;
    private javax.swing.JList<String> lstFoldersOut;
    private javax.swing.JPanel pnlImage;
    private javax.swing.JPanel pnlTools;
    private javax.swing.JScrollPane sclFoldersIn;
    private javax.swing.JScrollPane sclFoldersOut;
    private javax.swing.JSeparator sptImage;
    private javax.swing.JTextField txfFolderPath;
    private javax.swing.JTextField txfImageName;
    private javax.swing.JTextField txfImagePos;
    private javax.swing.JTextField txfNumImages;
    // End of variables declaration//GEN-END:variables
}
