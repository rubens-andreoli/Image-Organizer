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
package rubensandreoli.imageorganizer.gui.support;

import rubensandreoli.imageorganizer.io.Logger;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import rubensandreoli.imageorganizer.io.Logger.Level;

/**
 * References:
 * https://stackoverflow.com/questions/53419438/create-a-java-application-that-keeps-your-computer-from-going-idle-without-movin<br>
 * https://stackoverflow.com/questions/7814089/how-to-schedule-a-periodic-task-in-java
 * 
 * @author Rubens A. Andreoli Jr.
 */
public final class SwingUtils {
    
    public static final int FILES_ONLY = 0;
    public static final int DIRECTORIES_ONLY = 1;
    public static final int FILES_AND_DIRECTORIES = 2;
    
    private static JFileChooser chooser;
    
    private SwingUtils(){}
    
    public static File selectFile(Component parent, int mode){
        return selectFile(null, parent, mode);
    }
    
    public static File selectFile(File folder, Component parent, int mode){
        getChooser(mode);
        if(folder != null) chooser.setCurrentDirectory(folder);
        if(chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION){
            return chooser.getSelectedFile();
        }
        return null;
    }
    
    public static synchronized JFileChooser getChooser(int mode){
        if(chooser == null) chooser = new JFileChooser();
        chooser.setFileSelectionMode(mode);
        return chooser;
    }
    
    @SuppressWarnings("unchecked")
    public static void setDropTarget(Component c, PickyConsumer<File> consumer){
        c.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    final List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        if(consumer.accept(file)) break;
                    }
                } catch (UnsupportedFlavorException | IOException ex) {
                    Logger.log.print(Level.ERROR, "drag and drop failed", ex);
                }
            }
        });
    }
    
    public static void removeDropTarget(Component c){
        c.setDropTarget(null);
    }
    
    public static void showMessageDialog(Component parent, String msg, String title, Level lvl, boolean beep){
        int type;
        switch(lvl){
            case WARNING:
                type = JOptionPane.WARNING_MESSAGE;
                break;
            case ERROR:
            case CRITICAL:
            case SEVERE:
                type = JOptionPane.ERROR_MESSAGE;
                break;
            default:
                type = JOptionPane.INFORMATION_MESSAGE;
        }
        if(beep) beep();
        JOptionPane.showMessageDialog(parent, msg, title, type);
    }
    
    public static void beep(){
        Toolkit.getDefaultToolkit().beep();
    }
    
    public static void showMessageDialog(Component parent, Exception ex, Level lvl, boolean beep){
        showMessageDialog(parent, ex.getMessage(), "An exception has occurred!", lvl, beep);
    }
    
    public static void registerKeyAction(JComponent c, String name, int key, AbstractAction action){
        c.getActionMap().put(name, action);
        c.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(key, 0), name);
    }
    
    public static ImageIcon getIcon(String url) {
        try {
            return new ImageIcon(SwingUtils.class.getClassLoader().getResource("images/" + url));
        } catch (NullPointerException ex) {
            return new ImageIcon();
        }
    }
    
    public static BufferedImage getImage(String url){
        try {
            return ImageIO.read(SwingUtils.class.getResource("/images/"+url));
        } catch (IOException ex) {
            return null;
        }
    }
    
    public static void addClickableLink(Component c, final String url){
        c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String os = System.getProperty("os.name").toLowerCase();
                Runtime runtime = Runtime.getRuntime();
                IOException exception = null;

                if(os.contains("win")){
                    try { runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
                    } catch (IOException ex) {exception = ex;}
                }else if(os.contains("mac")){
                    try { runtime.exec("open " + url);
                    } catch (IOException ex) {exception = ex;}
                }else if(os.contains("nix") || os.contains("nux")){
                    try { runtime.exec("xdg-open " + url);
                    } catch (IOException ex) {exception = ex;}
                }

                if(exception != null){
                   //TODO: copy link to clipboard, and warn user
                }
            }
        });
    }
    
}
