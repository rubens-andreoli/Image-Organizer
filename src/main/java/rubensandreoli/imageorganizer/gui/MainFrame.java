/*
 * Copyright (C) 2023 Rubens A. Andreoli Jr.
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
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import rubensandreoli.imageorganizer.io.ImageLoader;
import rubensandreoli.imageorganizer.io.Logger;
import rubensandreoli.imageorganizer.io.Logger.Level;
import rubensandreoli.imageorganizer.gui.support.SwingUtils;
import rubensandreoli.imageorganizer.io.ImageFolder;
import rubensandreoli.imageorganizer.io.ImageFile;
import rubensandreoli.imageorganizer.io.Settings;
import rubensandreoli.imageorganizer.io.support.Shortcut;
import rubensandreoli.imageorganizer.io.History;

/**
 * References:
 * <br>
 * http://www.java2s.com/Tutorial/Java/0240__Swing/SettingtheLocationofaToolTip.htm<br>
 * https://stackoverflow.com/questions/7065309/jsplitpane-set-resizable-false/54458846#54458846<br>
 * https://stackoverflow.com/questions/14426472/detecting-when-jsplitpane-divider-is-being-dragged-not-component-being-resized<br>
 * https://stackoverflow.com/questions/8090506/let-swing-jsplitpane-support-one-touch-resize-but-not-drag<br>
 * https://stackoverflow.com/questions/42203189/mouse-cursor-wont-change-when-rolling-over-objects
 * https://stackoverflow.com/questions/8830642/cursor-icon-does-not-change-after-triggering-setcursor-method
 *
 * @author Rubens A. Andreoli Jr.
 */
public class MainFrame extends javax.swing.JFrame implements ToolsListener{
    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="ACTION">
    @FunctionalInterface
    private static interface Action {
        void run() throws Exception;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="STATIC FIELDS">
    private static final String PROGRAM_NAME = "Image Organizer";
    private static final String PROGRAM_VERSION = "v1.1.0";
    private static final String PROGRAM_YEAR = "2021";
    private static final String PROGRAM_ICON = "icon.png";

    private static final String IMAGE_PANEL_TOOTIP = "<html>\nImage preview<hr>  \n<b>Double-Click:</b> fit to panel<br> \n<b>Shift+Left-Click:</b> "
            + "locate on disk<br>\n<b>Right-Click:</b> choose folder<br>  \n<b>Drag-and-Drop:</b> load folder\n</html>";
    private static final String DELETE_ALERT_TITLE = "Delete Image";
    private static final String DELETE_ALERT_MSG = "Are you sure that you want to permanently delete the current image?";
    private static final String REMOVE_ALERT_TITLE = "Delete Image";
    private static final String REMOVE_ALERT_MSG = "Are you sure that you want to delete the current image?";
    private static final String FOLDER_FAIL_TITLE = "Shortcut Failed";
    private static final String FOLDER_FAIL_MSG_MASK = "Folder \"%s\" couldn't be found, all associated shortcuts were removed!";
    private static final String FOLDER_NAME_TITLE = "Folder Creation";
    private static final String FOLDER_NAME_MSG = "Type the new folder name:";
    private static final String UNSUPPORTED_TITLE = "Unsupported Operation";
    private static final String UNSUPPORTED_MSG = "<html>Currently, we do not support \"delete to trash\" "
            + "operations in your system.<br>If you wish to proceed, the current image, "
            + "as well as any other deleted<br>image during this session, will be <b>permanently deleted</b>.</html>";
    
    private static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    // </editor-fold>

    private final Settings settings;
    private final History history;
    private ImageFolder currentFolder; //it is null only before the loading first folder
    private ImageLoader currentLoader;
    private int currentPos = -1;
    private boolean deleteAgreed, toolsHidden, locked;

    public MainFrame() {
        settings = new Settings();
        settings.setListener(evt -> {
            if(currentFolder != null && evt.isSetting(Settings.KEY_SHOW_HIDDEN)){
                loadFolder(currentFolder.getPath());
            }
        });
        Logger.log.setVerbose(settings.isDebug());
        
        history = new History();
	try {
	    history.load();
	} catch (IOException ex) {
	    showException(ex); //TODO: better warning; cannot store and read history file
	}
        
        initComponents();
        initListeners();
        initSplitPane();
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
        setIconImage(SwingUtils.getIcon(PROGRAM_ICON).getImage());
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(695, 300));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        pnlSplit.setResizeWeight(1.0);
        pnlSplit.setOneTouchExpandable(true);

        pnlImage.setBackground(new java.awt.Color(255, 255, 255));
        pnlImage.setToolTipText(IMAGE_PANEL_TOOTIP);

        javax.swing.GroupLayout pnlImageLayout = new javax.swing.GroupLayout(pnlImage);
        pnlImage.setLayout(pnlImageLayout);
        pnlImageLayout.setHorizontalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );
        pnlImageLayout.setVerticalGroup(
            pnlImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
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
            .addComponent(pnlSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(currentLoader != null) currentLoader.shutdown();
        history.save();
        settings.save();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rubensandreoli.imageorganizer.gui.ImagePanel pnlImage;
    private javax.swing.JSplitPane pnlSplit;
    private rubensandreoli.imageorganizer.gui.ToolsPanel pnlTools;
    // End of variables declaration//GEN-END:variables

    //<editor-fold defaultstate="collapsed" desc="INITIALIZATION">
    private void initListeners(){
        SwingUtils.setDropTarget(pnlImage, file -> {
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
                    File file = SwingUtils.selectFile((currentFolder == null? null:currentFolder.getFolder()), pnlSplit, SwingUtils.DIRECTORIES_ONLY);
                    if(file != null) MainFrame.this.loadFolder(file.getPath());
                }
            }
        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(evt -> {
            //without isActive() shortcut will work even from a dialog
            if(currentFolder != null && evt.getID() == KeyEvent.KEY_RELEASED && isActive() && !pnlTools.isTyping()){
                int code = evt.getExtendedKeyCode();
                if(settings.containsShortcut(code)){
                    Shortcut shortcut = settings.getShortcut(code);
                    switch(shortcut.action){
                        case NEXT:
                            nextImage();
                            break;
                        case PREVIOUS:
                            previousImage();
                            break;
                        case DELETE:
                            deleteImage();
                            break;
                        case REFRESH:
                            loadFolder(currentFolder.getPath());
                            break;
                        case INFO:
                            pnlImage.toggleShowDetails();
                            break;
                        case MOVE:
                            moveImage(shortcut.description);
                            break;
                    }
                }else if(evt.isControlDown() && code == KeyEvent.VK_Z){
                    undoMove();
                }
            }
            return false;
        });
    }
    
    private void initSplitPane(){
        //enable=false & onetouch=true: disables cursor changes of subcomponents, improper solution
        BasicSplitPaneDivider divider = ((BasicSplitPaneUI) pnlSplit.getUI()).getDivider();
        divider.setEnabled(false); //disables dragging cursor only
        divider.removeMouseListener(divider.getMouseListeners()[0]);//disables dragging, not working
        pnlSplit.removeMouseListener(pnlSplit.getMouseListeners()[0]); //possible solution
        
        JButton btnUp = (JButton)divider.getComponent(0);
        JButton btnDown = (JButton)divider.getComponent(1);
        btnUp.addActionListener((e) -> {
            toolsHidden = false;
            setTitle(PROGRAM_NAME);
        });
        btnDown.addActionListener((e) -> {
            toolsHidden = true;
            if(currentFolder != null && !currentFolder.isEmpty()) {
                setTitle(PROGRAM_NAME + " ["+(currentPos+1)+" : "+currentFolder.getNumImages()+"]");
            }
        });

        addComponentListener(new ComponentAdapter() { //fixes restore after the frame is resized while minimized 
            @Override
            public void componentResized(ComponentEvent evt) {
                pnlSplit.setLastDividerLocation(pnlSplit.getHeight() - pnlTools.getHeight());
            }
        });
        btnUp.setVisible(false);
        //button listeners sometimes are not called because expand/minimize behaviour is triggered not by the buttons
        //not sure if after changing the disable drag solution it's still the case, but this approach seems fine
        pnlTools.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                btnDown.setVisible(pnlTools.getHeight() != 0);
                btnUp.setVisible(pnlTools.getHeight() == 0);
            } 
        });
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ACTIONS">
    private void loadFolder(String folderPath){
        if(locked) return;
        if(currentFolder != null){
            history.addEntry(currentFolder.getPath(), currentPos);
            currentLoader.shutdown();
        }
        currentFolder = new ImageFolder(folderPath);
        currentLoader = new ImageLoader(currentFolder);

        final boolean showHidden = settings.isShowHidden(); //settings can be changed while loading
        startActionOnNewThread(() -> {
//            System.out.println("loadFolder <start>"); //TODO: remove debug
//            Thread.sleep(5000); //TODO: remove debug
            currentFolder.load(showHidden); //ui is locked, folder shouldn't change
        }, () -> {
            int numImages = currentFolder.getNumImages();
            pnlTools.setFolderDetails(folderPath, numImages);
            fillRelatedFolders();

            int historyPos = history.getPosition(folderPath);
            currentPos = (historyPos >= numImages)? 0:historyPos; //old history with more files than actual folder

//            System.out.println("loadFolder <end>"); //TODO: remove debug
            loadImage(false);
        }, ex -> {
            showException((Exception) ex.getCause());  //TODO: better warning; exception while loading folder
        }, true, false);
    }
    
    @Override
    public void nextImage() {
        if(locked) return;
        if(currentPos >= currentFolder.getNumImages()-1) currentPos = 0;
	else currentPos++;
	loadImage(true);
    }

    @Override
    public void previousImage() {
        if(locked) return;
        if(currentPos > 0) currentPos--;
	else currentPos = currentFolder.getNumImages()-1;
	loadImage(true);
    }
    
    @Override
    public void loadImage(int pos) {
        if(locked) return;
        currentPos = pos;
        loadImage(true);
    }
   
    private void loadImage(boolean lock){
        if(lock && locked) return;
        final int numImages = currentFolder.getNumImages();
        if(numImages == 0){ //no images or all images got deleted
            pnlImage.clear();
            pnlTools.clearImageDetails();
            this.setTitle(PROGRAM_NAME);
            setLocked(false);
            toggleImageButtons(numImages);
	}else{
            startActionOnNewThread(() -> {
//                System.out.println("loadImage #"+(currentPos+1)+" <started>"); //TODO: remove debug
//                Thread.sleep(2000); //TODO: remove debug
                currentLoader.loadImage(currentPos);//ui is locked, loader and position shouldn't change
            }, () -> {
                ImageFile image = currentLoader.getImage();
                
                pnlImage.setImage(image);
                pnlTools.setImageDetails(image.getFilename(), currentPos+1); //0 indexed; for user readability
                if(toolsHidden) MainFrame.this.setTitle(PROGRAM_NAME + " ["+(currentPos+1)+" : "+currentFolder.getNumImages()+"]");
                        
                history.addEntry(currentFolder.getPath(), currentPos);
                
                toggleImageButtons(numImages);
//                System.out.println("loadImage #"+(currentPos+1)+" <ended>"); //TODO: remove debug
            }, ex -> {
                showException((Exception) ex.getCause()); //TODO: better warning; exception while loading image
            }, lock, true);
	}
    }
    
    @Override
    public void loadRelatedFolder(String folderName, boolean subfolder) {
        if(currentFolder.checkRelatedFolder(folderName, subfolder)){ //can check if folder exists while locked but loading will be stopped
            loadFolder(currentFolder.buildRelatedFolderPath(folderName, subfolder));
        }else{
            fillRelatedFolder(subfolder);
        }
    }
    
    @Override
    public void createRelatedFolder(boolean subfolder) { //can create new folder while locked
        if(currentFolder == null) return;
        final String folderName = JOptionPane.showInputDialog(this, FOLDER_NAME_MSG, FOLDER_NAME_TITLE, JOptionPane.PLAIN_MESSAGE);
        if(folderName != null && !folderName.isBlank()){
            startActionOnNewThread(() -> {
                currentFolder.createRelatedFolder(folderName, subfolder); //ui is locked, folder shouldn't change
            }, () -> {
                fillRelatedFolder(subfolder);
            }, ex -> {
                showException((Exception) ex.getCause()); //TODO: better warning; exception while creating folder
            }, false, false);
        }
    }
 
    private void moveImage(String folderPath){ //shortcuts move
        if(locked || currentFolder.isEmpty()) return;
        if(ImageFolder.checkFolder(folderPath)){
            startActionOnNewThread(() -> {
               currentFolder.moveImageTo(currentPos, folderPath); //ui is locked, folder and position shouldn't change
//               Thread.sleep(2000); //TODO: remove debug
            }, () -> {
                imageRemoved(false);
            }, ex -> {
                showException((Exception) ex.getCause()); //TODO: better warning; failed to move image
            }, true, false);
        }else{
            settings.removeShortcuts(folderPath); //removes shortcuts if folder couldn't be found
            SwingUtils.showMessageDialog(this, String.format(FOLDER_FAIL_MSG_MASK, folderPath), FOLDER_FAIL_TITLE, Level.INFO, true);
        }
    }
    
    @Override
    public void moveImage(String folderName, boolean subfolder) { //toolsPanel move
        if(locked || currentFolder.isEmpty()) return;
        if(currentFolder.checkRelatedFolder(folderName, subfolder)){
            startActionOnNewThread(() -> {
               currentFolder.moveImageTo(currentPos, folderName, subfolder); //ui is locked, folder and position shouldn't change
//               Thread.sleep(2000); //TODO: remove debug
            }, () -> {
                imageRemoved(false);
            }, ex -> {
                showException((Exception) ex.getCause()); //TODO: better warning; failed to move image
            }, true, false);
        }else{
           fillRelatedFolder(subfolder);
        }
    }
    
    private void undoMove(){
        if(locked) return;
        if(!currentFolder.hasMoveCached()){
            SwingUtils.beep();
            return;
        }
        startActionOnNewThread(() -> {
            currentFolder.undoLastMove(); //ui is locked, folder shouldn't change while working
//            Thread.sleep(2000); //TODO: remove debug
        }, () -> {
            loadImage(false);
        }, ex -> {
            showException((Exception) ex.getCause());//TODO: better warning; failed to move image back
        }, true, false);
    }
      
    @Override
    public void deleteImage() {
        if(locked || currentFolder.isEmpty()) return;
        if(deleteAgreed){ //show warning if cannot be recycled only once
            if(!settings.isShowAlert() || JOptionPane.showConfirmDialog(this, DELETE_ALERT_MSG, DELETE_ALERT_TITLE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                delete();
            }
        }else{
            if(!settings.isShowAlert() || JOptionPane.showConfirmDialog(this, REMOVE_ALERT_MSG, REMOVE_ALERT_TITLE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                startActionOnNewThread(() -> {
                    currentFolder.removeImage(currentPos);
                }, () -> {
                    imageRemoved(false);
                }, ex -> {
                    Exception cause = (Exception) ex.getCause();
                    if(cause instanceof UnsupportedOperationException){
                        Logger.log.print(Level.WARNING, cause);
                        if(JOptionPane.showConfirmDialog(this, UNSUPPORTED_MSG, UNSUPPORTED_TITLE, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
                            deleteAgreed = true;
                            delete();
                        }
                    }else{
                        showException(cause); //TODO: better warning; failed to remove image
                    }
                }, true, false);
            }
        }
    }
    
    private void delete(){
        startActionOnNewThread(() -> {
            currentFolder.deleteImage(currentPos);
        }, () -> {
            imageRemoved(false);
        }, ex -> {
            showException((Exception) ex.getCause()); //TODO: better warning; failed to delete image
        }, true, false);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="HELPERS">
    private void setLocked(boolean locked) {
        this.locked = locked;
        if(locked){
            setCursor(WAIT_CURSOR);
            pnlImage.setCursor(WAIT_CURSOR); //image panel has own cursor after clicking it; need to be changed from here after
        }else{
            setCursor(DEFAULT_CURSOR);
            pnlImage.setCursor(DEFAULT_CURSOR);
        }
        if(!toolsHidden){ //there is no need to disable if hidden
            pnlTools.setImageButtonsEnabled(!locked);
            pnlTools.setImagePositionEnabled(!locked);
        }
    }
            
    private void toggleImageButtons(int numImages){
        pnlTools.setImageButtonsEnabled(false);
        if(numImages > 1){
            pnlTools.setImageButtonsEnabled(true);
        }else if(numImages == 1){
            pnlTools.setDeleteEnabled(true);
        }
    }
    
    private void startActionOnNewThread(final Action action, Runnable done, Consumer<Exception> failed, boolean lock, boolean unlock){
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                action.run();
                return null;
            }

            @Override
            protected void done() {
                try {
                    this.get();
                    if(unlock) setLocked(false);
                    done.run();
                } catch (InterruptedException ex) {
                    setLocked(false);
                    System.out.println("worker interrupted"); //TODO: better warning
                } catch (ExecutionException ex) {
                    setLocked(false);
                    failed.accept(ex);
                }
            }
        };
        if(lock) setLocked(true);
        worker.execute();
    }
    
    private void fillRelatedFolders(){
        pnlTools.setRootFolders(currentFolder.getParentFolders());
        pnlTools.setSubFolders(currentFolder.getSubFolders());
    }

    private void fillRelatedFolder(boolean subfolder){
        if(subfolder){
            pnlTools.setSubFolders(currentFolder.getSubFolders());
        }else{
            pnlTools.setRootFolders(currentFolder.getParentFolders());
        }
    }
  
    private void imageRemoved(boolean lock){
        int size = currentFolder.getNumImages();
    	pnlTools.setImageTotal(size);
        if(currentPos >= size){
            currentPos = 0;
        }
        loadImage(lock);
    }

    @Override
    public void settings() {
        new SettingsDialog(this, settings).setVisible(true);
    }

    private void showException(Exception ex){
        SwingUtils.showMessageDialog(this, ex, Level.ERROR, true);
    }

    @Override
    public void about() {
        new AboutDialog(this, new AboutDialog.ProgramDetails(PROGRAM_NAME, null, PROGRAM_VERSION, PROGRAM_YEAR))
                .addAtribution("Program icon", "Iconshock", "https://www.iconshock.com/")
                .addAtribution("About icon", "Gregor Cresnar", "https://www.flaticon.com/authors/gregor-cresnar")
                .addAtribution("Settings icons", "Vectors Market", "https://www.flaticon.com/authors/vectors-market")
                .addAtribution("Broken image icon", "Google", "https://www.flaticon.com/authors/google")
                .setVisible(true);
    }
    //</editor-fold>

}
