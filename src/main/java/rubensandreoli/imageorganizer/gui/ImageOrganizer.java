/*
 * Copyright (C) 2020 Rubens A. Andreoli Jr.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package rubensandreoli.imageorganizer.gui;

import rubensandreoli.imageorganizer.gui.support.ToolsListener;
import java.awt.Cursor;
import static java.awt.Frame.WAIT_CURSOR;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import rubensandreoli.commons.others.Level;
import rubensandreoli.commons.swing.AboutDialog;
import rubensandreoli.commons.utils.FileUtils;
import rubensandreoli.commons.utils.SwingUtils;
import rubensandreoli.imageorganizer.gui.support.Settings;
import rubensandreoli.imageorganizer.gui.support.Shortcut;
import rubensandreoli.imageorganizer.io.History;
import rubensandreoli.imageorganizer.io.ImageFolder;

/** References:
 * http://www.java2s.com/Tutorial/Java/0240__Swing/SettingtheLocationofaToolTip.htm
 *
 * @author Rubens A. Andreoli Jr.
 */
public class ImageOrganizer extends javax.swing.JFrame implements ToolsListener{
    private static final long serialVersionUID = 1L;

    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS "> 
    private static final String PROGRAM_NAME = "Image Organizer";
    private static final String PROGRAM_VERSION = "v1.0.0";
    private static final String PROGRAM_YEAR = "2018";
    private static final String PROGRAM_ICON = "images/icon.png";
    // </editor-fold>

    private ImageFolder imageFolder;
    private final History history;
    private final Settings settings;
    private int currentPos = -1;
    
    public ImageOrganizer() {
        initComponents();
        
        // <editor-fold defaultstate="collapsed" desc=" LOAD "> 
        settings = new Settings();
        history = new History();
	try {
	    history.load();
	} catch (IOException ex) {
	    showException(ex);
	}        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" LISTENERS "> 
        SwingUtils.addDroppable(pnlImage, file -> {
            if(file.isDirectory()){
                loadFolder(file.getPath());
                return true;
            }
            return false;
        });
        
        pnlImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if(evt.getButton() == MouseEvent.BUTTON3){
                    final File file = SwingUtils.selectFile(pnlSplit, SwingUtils.DIRECTORIES_ONLY);
                    if(file != null) loadFolder(file.getAbsolutePath());
                }
            }
        });
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(evt -> {
            if(imageFolder != null && isActive()){
                final int code = evt.getKeyCode();
                if(settings.containsShortcut(code) && evt.paramString().startsWith("KEY_PRESSED")){
                    final Shortcut shortcut = settings.getShortcut(code);
                    switch(shortcut.action){
                        case NEXT:
                            next();
                            break;
                        case PREVIOUS:
                            previous();
                            break;
                        case DELETE:
                            delete();
                            break;
                        case MOVE:
                            move(shortcut.description);
                            break;
                    }
                }
            }
            return false;
        });
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc=" SPLITPANE ">
        final BasicSplitPaneDivider divider = ((BasicSplitPaneUI) pnlSplit.getUI()).getDivider();
        divider.setEnabled(false); //disable dragging cursor
        divider.removeMouseListener(divider.getMouseListeners()[0]);//disable dragging
        final JButton btnUp = (JButton)divider.getComponent(0);
        final JButton btnDown = (JButton)divider.getComponent(1);

        addComponentListener(new ComponentAdapter() { //fix restore after minimized drag
            @Override
            public void componentResized(ComponentEvent evt) {
                pnlSplit.setLastDividerLocation(pnlSplit.getHeight() - pnlTools.getHeight());
            }
        });
        btnUp.setVisible(false);
        final ActionListener listener = evt -> {
            btnDown.setVisible(btnUp.isVisible());
            btnUp.setVisible(!btnUp.isVisible());
        };
        btnUp.addActionListener(listener);
        btnDown.addActionListener(listener);        
        // </editor-fold>

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSplit = new javax.swing.JSplitPane();
        pnlImage = new rubensandreoli.imageorganizer.gui.ImagePanel();
        pnlTools = new rubensandreoli.imageorganizer.gui.ToolsPanel();
        pnlTools.setListener(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(PROGRAM_NAME);
        setIconImage(FileUtils.loadIcon(PROGRAM_ICON).getImage());
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(695, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlSplit.setDividerSize(10);
        pnlSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        pnlSplit.setResizeWeight(1.0);
        pnlSplit.setOneTouchExpandable(true);

        pnlImage.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 326, Short.MAX_VALUE)
        );

        pnlSplit.setLeftComponent(pnlImage);
        pnlSplit.setRightComponent(pnlTools);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlSplit)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
	    history.save();
	} catch (IOException ex) {}
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rubensandreoli.imageorganizer.gui.ImagePanel pnlImage;
    private javax.swing.JSplitPane pnlSplit;
    private rubensandreoli.imageorganizer.gui.ToolsPanel pnlTools;
    // End of variables declaration//GEN-END:variables

    private void loadFolder(String folderPath){
        if(imageFolder != null){
            if(currentPos > 0){ //add to history if not at initial position
                history.addEntry(imageFolder.getFolderPath(), currentPos);
            }else if(history.cotains(folderPath)){ //remove if contains and new position is 0
                history.removeEntry(folderPath);
            }
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        imageFolder = new ImageFolder(folderPath, settings.isShowHidden());
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        pnlTools.setFolderPath(folderPath);
        pnlTools.fillRootFolders(imageFolder.getRootFolders());
        pnlTools.fillSubFolders(imageFolder.getSubFolders());
        pnlTools.setImageTotal(imageFolder.getNumImages());
        
        currentPos = history.getPosition(folderPath);
        loadImage();
    }

    private void loadImage(){
	if(imageFolder == null) return;
        
        final int numImages = imageFolder.getNumImages();
        if(numImages == 0){ //no images or all images got deleted
            pnlImage.clear();
            pnlTools.setImagePosition(0);
            pnlTools.setImageName("");
	}else{
	    try {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		pnlImage.setImage(imageFolder.loadImage(currentPos));
	    } catch (IOException ex) { //clear last image if fail to load
		pnlImage.clear();
		showException(ex);
	    } finally { //fill info even if failed loading
		pnlTools.setImageName(imageFolder.getImagePath(currentPos));
		pnlTools.setImagePosition(currentPos+1);
		history.addEntry(imageFolder.getFolderPath(), currentPos);
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    }
	}
    
        pnlTools.setButtonsEnabled(false);
        if(numImages > 1){
            pnlTools.setButtonsEnabled(true);
        }else if(numImages == 1){
            pnlTools.setDeleteEnabled(true);
        }
    }
    
    private void showException(Exception ex){
        SwingUtils.showMessageDialog(this, ex, Level.WARNING, true);
    }
     
    @Override
    public void about() {
        new AboutDialog(this, PROGRAM_NAME, PROGRAM_VERSION, PROGRAM_YEAR)
                .addAtribution("Program icon", "Iconshock", "https://www.iconshock.com/")
                .addAtribution("About icon", "Gregor Cresnar", "https://www.flaticon.com/authors/gregor-cresnar")
                .addAtribution("Settings icons", "Vectors Market", "https://www.flaticon.com/authors/vectors-market")
                .setVisible(true);
    }
 
    @Override
    public void next() {
        if(currentPos >= imageFolder.getNumImages()-1) currentPos = 0;
	else currentPos++;
	loadImage();
    }

    @Override
    public void previous() {
        if(currentPos > 0) currentPos--;
	else currentPos = imageFolder.getNumImages()-1;
	loadImage();
    }

    @Override
    public void position(int pos) {
        if(imageFolder == null){ 
            pnlTools.setImagePosition(0);
        }else{
           currentPos = Math.max(0, pos-1);
           loadImage();
        }
    }


    @Override
    public void delete() {
        int choice = JOptionPane.YES_OPTION;
        if(settings.isShowAlert()){
            choice = JOptionPane.showConfirmDialog(
                    this, 
                    "Do you like to permanently delete this image?", 
                    "Delete Image", 
                    JOptionPane.YES_NO_OPTION
            );
        }
        if(choice == JOptionPane.YES_OPTION){
            try {
                imageFolder.deleteImage(currentPos);
                imageRemoved();
            } catch (IOException ex) {
                showException(ex);
            }
        }
    }
    
    public void move(String folder){
        try {
            imageFolder.transferImage(currentPos, folder);
            imageRemoved();
        } catch (IOException ex) {
            showException(ex);
        }
    }

    @Override
    public void move(String folderName, boolean subfolder) {
        try {
            imageFolder.transferImage(currentPos, folderName, subfolder);
            imageRemoved();
        } catch (IOException ex) {
            showException(ex);
        }
    }
    
    private void imageRemoved(){
        final int size = imageFolder.getNumImages();
    	pnlTools.setImageTotal(size);
        if(currentPos >= size){
            currentPos = 0;
        }
        loadImage();    
    }

    @Override
    public void create(boolean subfolder) {
        if(imageFolder == null) return;
        String folderName = JOptionPane.showInputDialog(
                this, 
                "New folder name:", 
                "Create New Folder", 
                JOptionPane.PLAIN_MESSAGE
        );
        if(folderName != null){
            try {
                imageFolder.createFolder(folderName, subfolder);
                fillFolders(subfolder);
            } catch (IOException ex) {
                showException(ex);
            }
        } 
    }

    @Override
    public void load(String folderName, boolean subfolder) {
        final String path = subfolder ? 
                imageFolder.getFolderPath()+FileUtils.SEPARATOR+folderName : imageFolder.getRootPath()+FileUtils.SEPARATOR+folderName;
        if(!imageFolder.testFolder(folderName, subfolder)){ 
            fillFolders(subfolder);
        }
        loadFolder(path);
    }
    
    private void fillFolders(boolean subfolder){
        if(subfolder){
            pnlTools.fillSubFolders(imageFolder.getSubFolders());
        }else{
            pnlTools.fillRootFolders(imageFolder.getRootFolders());
        }
    }

    @Override
    public void settings() {
        new SettingsDialog(this, true, settings).setVisible(true);
    }

}
