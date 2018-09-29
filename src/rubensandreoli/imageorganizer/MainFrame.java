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
import rubensandreoli.imageorganizer.model.FolderIO;
import rubensandreoli.imageorganizer.model.HistoryIO;

//icon - https://all-free-download.com/free-icon/download/free-folder-icons-icons-pack_120784.html

public class MainFrame extends javax.swing.JFrame {

    private static final String PROGRAM_NAME = "PhotoOrganizer";
    private static final String PROGRAM_VERSION = "v1.0.0";
    private static final String PROGRAM_YEAR = "2018";
    
    private FolderIO folderIO;
    private DefaultListModel<String> modelIn;
    private DefaultListModel<String> modelOut;
    private HistoryIO historyIO;
    
    private int currentPos = -1;
    
    public MainFrame() {
	modelIn = new DefaultListModel<String>();
	modelOut = new DefaultListModel<String>();
	initComponents();
	historyIO = new HistoryIO();
	try {
	    historyIO.loadHistory();
	} catch (IOException ex) {
	    //TODO: no history file
	}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        flcFolder = new javax.swing.JFileChooser();
        pnlImage = new ImagePanel();
        sclFoldersOut = new javax.swing.JScrollPane();
        lstFoldersOut = new javax.swing.JList<>();
        sclFoldersIn = new javax.swing.JScrollPane();
        lstFoldersIn = new javax.swing.JList<>();
        txfImagePos = new javax.swing.JTextField();
        txfImageName = new javax.swing.JTextField();
        txfFolderPath = new javax.swing.JTextField();
        txfNumImages = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblInfo = new javax.swing.JLabel();
        sptImage = new javax.swing.JSeparator();

        flcFolder.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Photo Organizer");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("images/icon.png")));
        setMinimumSize(new java.awt.Dimension(747, 551));
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

        pnlImage.setToolTipText("");
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
            .addGap(0, 324, Short.MAX_VALUE)
        );

        sclFoldersOut.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstFoldersOut.setModel(modelOut);
        lstFoldersOut.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstFoldersOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstFoldersOutMouseClicked(evt);
            }
        });
        sclFoldersOut.setViewportView(lstFoldersOut);

        sclFoldersIn.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstFoldersIn.setModel(modelIn);
        lstFoldersIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstFoldersInMouseClicked(evt);
            }
        });
        sclFoldersIn.setViewportView(lstFoldersIn);

        txfImagePos.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfImagePos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfImagePos.setText("0");
        txfImagePos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfImagePosKeyReleased(evt);
            }
        });

        txfImageName.setEditable(false);
        txfImageName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfImageName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfImageName.setEnabled(false);

        txfFolderPath.setEditable(false);
        txfFolderPath.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfFolderPath.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfFolderPath.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txfImageName, org.jdesktop.beansbinding.ELProperty.create("${maximumSize}"), txfFolderPath, org.jdesktop.beansbinding.BeanProperty.create("maximumSize"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txfImageName, org.jdesktop.beansbinding.ELProperty.create("${minimumSize}"), txfFolderPath, org.jdesktop.beansbinding.BeanProperty.create("minimumSize"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, txfImageName, org.jdesktop.beansbinding.ELProperty.create("${preferredSize}"), txfFolderPath, org.jdesktop.beansbinding.BeanProperty.create("preferredSize"));
        bindingGroup.addBinding(binding);

        txfNumImages.setEditable(false);
        txfNumImages.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfNumImages.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfNumImages.setText("0");
        txfNumImages.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfNumImages.setEnabled(false);

        btnNext.setText("Next");
        btnNext.setEnabled(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/rubensandreoli/imageorganizer/images/about.png"))); // NOI18N
        lblInfo.setToolTipText("about");
        lblInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInfoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sptImage)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfNumImages, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfFolderPath))
                        .addGap(18, 18, 18)
                        .addComponent(sclFoldersOut, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                    .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(lblInfo)))
                        .addGap(18, 18, 18)
                        .addComponent(sclFoldersIn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfImagePos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfImageName))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sptImage, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txfFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfNumImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sclFoldersOut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txfImageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfImagePos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnNext)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnBack)
                            .addGap(18, 18, 18)
                            .addComponent(lblInfo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDelete))
                        .addComponent(sclFoldersIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInfoMouseClicked
        new AboutDialog(this, PROGRAM_NAME, PROGRAM_VERSION, PROGRAM_YEAR).setVisible(true);
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
        if (evt.getModifiers() == MouseEvent.BUTTON3_MASK){
            if(flcFolder.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                changeFolder(flcFolder.getSelectedFile().getAbsolutePath());
            }
        }
    }//GEN-LAST:event_pnlImageMouseClicked

    private void txfImagePosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfImagePosKeyReleased
	if(evt.getKeyCode() == KeyEvent.VK_ENTER){
	    if(folderIO == null){ 
		txfImagePos.setText(currentPos+1+"");
		return;
	    }
	    try{
		int pos = Integer.parseInt(txfImagePos.getText()) - 1;
		if(pos > folderIO.getNumImages()-1) throw new NumberFormatException();
		currentPos = pos;
		loadImage();
	    }catch (NumberFormatException ex){
		txfImagePos.setText(currentPos+1+"");
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
		    txfNumImages.setText(folderIO.getNumImages()+"");
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
	} catch (IOException ex) {
	    //TODO: history not saved
	}
    }//GEN-LAST:event_formWindowClosing

    private void changeFolder(String folderPath){
	if(folderIO != null && currentPos > 0){
	    historyIO.addHistory(folderIO.getFolderPath(), currentPos);
	}
	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	folderIO = new FolderIO(folderPath);
	setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	txfFolderPath.setText(folderPath);
	txfNumImages.setText(folderIO.getNumImages()+"");
	populateList(true);
	populateList(false);
	if(folderIO.getNumImages() > 0){
	    currentPos = historyIO.getHistory(folderPath);
	    loadImage();
	}
    }
  
    private void loadImage(){
	if(folderIO == null) return;
	try {
	    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    ((ImagePanel)pnlImage).setImage(folderIO.loadImage(currentPos));
	    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    txfImagePos.setText(currentPos+1+"");
	    txfImageName.setText(folderIO.getImagePath(currentPos));
	    testButtons();
	    historyIO.addHistory(folderIO.getFolderPath(), currentPos);
	    this.requestFocus();
	} catch (IOException ex) {
	    showError(ex);
	}
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
	List<String> list = subFolders? folderIO.getSubFolders():folderIO.getRootFolders();
	DefaultListModel<String> model = subFolders? modelIn:modelOut;
	model.clear();
	list.stream().forEach((folder) -> {
	    model.addElement(folder);
	});
    }
    
    private void clickList(MouseEvent e, boolean subFolders){
	if(folderIO == null) return;
	JList<String> list = (JList<String>)e.getSource();
	if (!list.isSelectionEmpty()){
	    if(!folderIO.testFolder(list.getSelectedValue(), subFolders)) populateList(subFolders);
	}
	if (e.getClickCount() == 2 && !list.isSelectionEmpty()){
	    try {
		folderIO.tranferImage(currentPos, list.getSelectedValue(), subFolders);
		txfNumImages.setText(folderIO.getNumImages()+"");
		loadImage();
	    } catch (IOException ex) {
		showError(ex);
	    }
	}
	if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
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
    private javax.swing.JScrollPane sclFoldersIn;
    private javax.swing.JScrollPane sclFoldersOut;
    private javax.swing.JSeparator sptImage;
    private javax.swing.JTextField txfFolderPath;
    private javax.swing.JTextField txfImageName;
    private javax.swing.JTextField txfImagePos;
    private javax.swing.JTextField txfNumImages;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
