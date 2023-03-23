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
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import rubensandreoli.imageorganizer.gui.support.SwingUtils;

/**
 * References:
 * <br>
 * https://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html<br>
 * https://stackoverflow.com/questions/36617194/hitting-space-key-in-jtextfield-triggers-key-binding-of-parent-window
 * 
 * @author Rubens A. Andreoli Jr
 */
public class ToolsPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    // <editor-fold defaultstate="collapsed" desc="RENDERER"> 
    private class ListRenderer implements ListCellRenderer<String>{

        private final JLabel label;
        private final Color selectedColor = UIManager.getDefaults().getColor("List.selectionBackground");
        
        public ListRenderer() {
            label = new JLabel();
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
            label.setOpaque(true);
        }
       
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            label.setText(value);
            if(isSelected) {
                label.setBackground(selectedColor);
                label.setForeground(Color.WHITE);
            }else {
                label.setBackground(null);
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    
    }
    // </editor-fold>
    
    private ToolsListener listener;

    public ToolsPanel() { //kept empty beacause of the design tool
        initComponents();
        
        SwingUtils.registerKeyAction(txfImagePos, "LOAD", KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer pos = txfImagePos.getValue();
                if(pos != null) listener.loadImage(pos);  //pos is null if field fails to parse the number; shouldn't happen
                btnNext.requestFocus();
            }
        });
        SwingUtils.registerKeyAction(lstRootFolders, "MOVE_ROOT", KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listTyped(lstRootFolders, false);
            }
        });
        SwingUtils.registerKeyAction(lstSubFolders, "MOVE_SUB", KeyEvent.VK_ENTER, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listTyped(lstSubFolders, true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlLeft = new javax.swing.JPanel();
        txfFolderPath = new javax.swing.JTextField();
        txfNumImages = new javax.swing.JTextField();
        pnlTools = new javax.swing.JPanel();
        lblAbout = new javax.swing.JLabel();
        lblSettings = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        sclFoldersOut = new javax.swing.JScrollPane();
        lstRootFolders = new javax.swing.JList<>();
        sclFoldersIn = new javax.swing.JScrollPane();
        lstSubFolders = new javax.swing.JList<>();
        btnDelete = new javax.swing.JButton();
        pnlRight = new javax.swing.JPanel();
        txfImageName = new javax.swing.JTextField();
        txfImagePos = new rubensandreoli.imageorganizer.gui.support.IntegerField();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWeights = new double[] {0.5, 0.0, 0.5};
        setLayout(layout);

        txfFolderPath.setEditable(false);
        txfFolderPath.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfFolderPath.setToolTipText("Folder path");
        txfFolderPath.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfFolderPath.setEnabled(false);

        txfNumImages.setEditable(false);
        txfNumImages.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfNumImages.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfNumImages.setText("0");
        txfNumImages.setToolTipText("Number of images within the folder");
        txfNumImages.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfNumImages.setEnabled(false);

        javax.swing.GroupLayout pnlLeftLayout = new javax.swing.GroupLayout(pnlLeft);
        pnlLeft.setLayout(pnlLeftLayout);
        pnlLeftLayout.setHorizontalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLeftLayout.createSequentialGroup()
                        .addComponent(txfNumImages, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txfFolderPath, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlLeftLayout.setVerticalGroup(
            pnlLeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLeftLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfFolderPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfNumImages, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        add(pnlLeft, gridBagConstraints);

        lblAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/about.png"))); // NOI18N
        lblAbout.setToolTipText("About");
        lblAbout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAboutMouseClicked(evt);
            }
        });

        lblSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings.png"))); // NOI18N
        lblSettings.setToolTipText("Settings");
        lblSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSettingsMouseClicked(evt);
            }
        });

        btnBack.setText("Back");
        btnBack.setEnabled(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnNext.setText("Next");
        btnNext.setEnabled(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        sclFoldersOut.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstRootFolders.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstRootFolders.setToolTipText("<html>Folders in the same level<hr>\n<b>Double-Click</b>: transfer image to folder<br>\n<b>Right-Click</b>: create new folder<br>\n<b>Shift+Left-Click</b>: go to folder</html>");
        lstRootFolders.setCellRenderer(new ListRenderer());
        lstRootFolders.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lstRootFoldersFocusLost(evt);
            }
        });
        lstRootFolders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstRootFoldersMouseClicked(evt);
            }
        });
        sclFoldersOut.setViewportView(lstRootFolders);

        sclFoldersIn.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstSubFolders.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSubFolders.setToolTipText("<html>Folders inside<hr>\n<b>Double-Click</b>: transfer image to folder<br>\n<b>Right-Click</b>: create new folder<br>\n<b>Shift+Left-Click</b>: go to folder</html>");
        lstSubFolders.setCellRenderer(new ListRenderer());
        lstSubFolders.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                lstSubFoldersFocusLost(evt);
            }
        });
        lstSubFolders.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstSubFoldersMouseClicked(evt);
            }
        });
        sclFoldersIn.setViewportView(lstSubFolders);

        btnDelete.setText("Delete");
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
                .addGap(7, 7, 7)
                .addComponent(sclFoldersOut, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlToolsLayout.createSequentialGroup()
                        .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlToolsLayout.createSequentialGroup()
                        .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSettings)
                            .addComponent(lblAbout))
                        .addGap(39, 39, 39)))
                .addComponent(sclFoldersIn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlToolsLayout.setVerticalGroup(
            pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sclFoldersOut, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(sclFoldersIn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlToolsLayout.createSequentialGroup()
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSettings)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAbout)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)))
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(pnlTools, gridBagConstraints);

        txfImageName.setEditable(false);
        txfImageName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txfImageName.setToolTipText("Image name");
        txfImageName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfImageName.setEnabled(false);

        txfImagePos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfImagePos.setToolTipText("Position of the image within the folder");
        txfImagePos.setEnabled(false);

        javax.swing.GroupLayout pnlRightLayout = new javax.swing.GroupLayout(pnlRight);
        pnlRight.setLayout(pnlRightLayout);
        pnlRightLayout.setHorizontalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txfImageName, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRightLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txfImagePos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlRightLayout.setVerticalGroup(
            pnlRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfImageName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txfImagePos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        add(pnlRight, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void lblAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAboutMouseClicked
        listener.about();
    }//GEN-LAST:event_lblAboutMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        listener.nextImage();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        listener.previousImage();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        listener.deleteImage();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void lstRootFoldersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstRootFoldersMouseClicked
        listClicked(evt, false);
    }//GEN-LAST:event_lstRootFoldersMouseClicked

    private void lstSubFoldersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstSubFoldersMouseClicked
        listClicked(evt, true);
    }//GEN-LAST:event_lstSubFoldersMouseClicked

    private void lblSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSettingsMouseClicked
        listener.settings();
    }//GEN-LAST:event_lblSettingsMouseClicked

    private void lstSubFoldersFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstSubFoldersFocusLost
        deselectList(evt);
    }//GEN-LAST:event_lstSubFoldersFocusLost

    private void lstRootFoldersFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_lstRootFoldersFocusLost
        deselectList(evt);
    }//GEN-LAST:event_lstRootFoldersFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNext;
    private javax.swing.JLabel lblAbout;
    private javax.swing.JLabel lblSettings;
    private javax.swing.JList<String> lstRootFolders;
    private javax.swing.JList<String> lstSubFolders;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPanel pnlTools;
    private javax.swing.JScrollPane sclFoldersIn;
    private javax.swing.JScrollPane sclFoldersOut;
    private javax.swing.JTextField txfFolderPath;
    private javax.swing.JTextField txfImageName;
    private rubensandreoli.imageorganizer.gui.support.IntegerField txfImagePos;
    private javax.swing.JTextField txfNumImages;
    // End of variables declaration//GEN-END:variables

    private void listTyped(JList<String> list, boolean subfolder){
        if(!list.isSelectionEmpty()){
           listener.moveImage(list.getSelectedValue(), subfolder);
        }
    }
    
    private void listClicked(MouseEvent evt, boolean subfolder){
        JList<String> list = (JList<String>)evt.getSource();
	if (evt.getButton() == 1 && evt.getClickCount() == 2 && !list.isSelectionEmpty()){
            listener.moveImage(list.getSelectedValue(), subfolder);
	} else if(evt.getButton() == 3){
            listener.createRelatedFolder(subfolder);
        } else if(evt.getButton() == 1 && evt.isShiftDown() && !list.isSelectionEmpty()){
            listener.loadRelatedFolder(list.getSelectedValue(), subfolder);
        }
    }

    private void fillFolders(Collection<String> folders, JList list){
        DefaultListModel<String> model = new DefaultListModel<>();
        list.setModel(model);
        folders.stream().forEach((folder) -> {
	    model.addElement(folder);
	});
    }
       
    private void deselectList(FocusEvent evt){
        ((JList)evt.getComponent()).clearSelection();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" GETTERS "> 
    boolean isTyping() {
        return txfImagePos.isFocusOwner();
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc=" SETTERS "> 
    void setListener(ToolsListener l){
        listener = l;
    }
        
    void setRootFolders(Collection<String> folders){
	fillFolders(folders, lstRootFolders);
    }
        
    void setSubFolders(Collection<String> folders){
        fillFolders(folders, lstSubFolders);
    }

    void setFolderDetails(String path, int total){
        txfFolderPath.setText(path);
        setImageTotal(total);
    }
  
    void setImageTotal(int amount){
        txfNumImages.setText(String.valueOf(amount));
        txfImagePos.setInterval(0, amount);
    }
    
    void setImageDetails(String name, int pos){
        txfImageName.setText(name);
        txfImagePos.setValue(pos);
    }
    
    void clearImageDetails(){
        txfImagePos.setValue(0);
        txfImageName.setText("");
    }
    
    void setImageButtonsEnabled(boolean b){
        btnNext.setEnabled(b);
	btnBack.setEnabled(b);
	btnDelete.setEnabled(b);
    }
    
    void setImagePositionEnabled(boolean b){
        txfImagePos.setEnabled(b);
    }
    
    void setDeleteEnabled(boolean b){
        btnDelete.setEnabled(b);
    }
    // </editor-fold>

}
