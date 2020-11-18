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
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class ToolsPanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    private ToolsListener listener;

    // <editor-fold defaultstate="collapsed" desc=" RENDERER "> 
    private class ListRenderer implements ListCellRenderer<String>{

        private final JLabel label = new JLabel();
        
        public ListRenderer() {
            label.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
            label.setOpaque(true);
        }
       
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            label.setText(value);
            if(isSelected) {
                label.setBackground(UIManager.getDefaults().getColor("List.selectionBackground"));
                label.setForeground(Color.WHITE);
            }else {
                label.setBackground(null);
                label.setForeground(Color.BLACK);
            }
            return label;
        }
    
    }
    // </editor-fold>
    
    public ToolsPanel() {
        initComponents();
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
        jLabel1 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        sclFoldersOut = new javax.swing.JScrollPane();
        lstRootFolders = new javax.swing.JList<>();
        sclFoldersIn = new javax.swing.JScrollPane();
        lstSubFolders = new javax.swing.JList<>();
        btnDelete = new javax.swing.JButton();
        pnlRight = new javax.swing.JPanel();
        txfImageName = new javax.swing.JTextField();
        txfImagePos = new rubensandreoli.commons.swing.NumberField();

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
        txfNumImages.setToolTipText("Total number of images within the folder");
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings.png"))); // NOI18N
        jLabel1.setToolTipText("Settings");
        jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jLabel1.setMinimumSize(new java.awt.Dimension(16, 16));
        jLabel1.setPreferredSize(new java.awt.Dimension(16, 16));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
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
        lstRootFolders.setToolTipText("<html>Folders in the same level<hr>\nDouble-Click: transfer image to folder<br>\nRight-Click: create new folder<br>\nMiddle-Click: go to folder</html>");
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
        lstRootFolders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstRootFoldersKeyReleased(evt);
            }
        });
        sclFoldersOut.setViewportView(lstRootFolders);

        sclFoldersIn.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        lstSubFolders.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSubFolders.setToolTipText("<html>Folders inside<hr>\nDouble-Click: transfer image to folder<br>\nRight-Click: create new folder<br>\nMiddle-Click: go to folder</html>");
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
        lstSubFolders.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstSubFoldersKeyReleased(evt);
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
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        txfImageName.setToolTipText("Image path");
        txfImageName.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txfImageName.setEnabled(false);

        txfImagePos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfImagePos.setToolTipText("Position of the image within the folder");
        txfImagePos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txfImagePosKeyPressed(evt);
            }
        });

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
        if(listener != null) listener.about();
    }//GEN-LAST:event_lblAboutMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if(listener != null) listener.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if(listener != null) listener.previous();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(listener != null) listener.delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txfImagePosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfImagePosKeyPressed
        if(listener == null) return;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            listener.load(txfImagePos.getInt());
        }
    }//GEN-LAST:event_txfImagePosKeyPressed

    private void lstRootFoldersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstRootFoldersMouseClicked
        listClicked(evt, false);
    }//GEN-LAST:event_lstRootFoldersMouseClicked

    private void lstSubFoldersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstSubFoldersMouseClicked
        listClicked(evt, true);
    }//GEN-LAST:event_lstSubFoldersMouseClicked

    private void lstSubFoldersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstSubFoldersKeyReleased
        listKey(evt, true);
    }//GEN-LAST:event_lstSubFoldersKeyReleased

    private void lstRootFoldersKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstRootFoldersKeyReleased
        listKey(evt, false);
    }//GEN-LAST:event_lstRootFoldersKeyReleased

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if(listener != null) listener.settings();
    }//GEN-LAST:event_jLabel1MouseClicked

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAbout;
    private javax.swing.JList<String> lstRootFolders;
    private javax.swing.JList<String> lstSubFolders;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPanel pnlTools;
    private javax.swing.JScrollPane sclFoldersIn;
    private javax.swing.JScrollPane sclFoldersOut;
    private javax.swing.JTextField txfFolderPath;
    private javax.swing.JTextField txfImageName;
    private rubensandreoli.commons.swing.NumberField txfImagePos;
    private javax.swing.JTextField txfNumImages;
    // End of variables declaration//GEN-END:variables

    private void listKey(KeyEvent evt, boolean subfolder){
         if(listener == null) return;
         final JList<String> list = (JList<String>)evt.getSource();
         if(evt.getKeyCode() == KeyEvent.VK_ENTER && !list.isSelectionEmpty()){
             listener.move(list.getSelectedValue(), subfolder);
         }
    }
    
    private void listClicked(MouseEvent evt, boolean subfolder){
        if(listener == null) return;
        final JList<String> list = (JList<String>)evt.getSource();
	if (evt.getButton() == 1 && evt.getClickCount() == 2 && !list.isSelectionEmpty()){
            listener.move(list.getSelectedValue(), subfolder);
	} else if(evt.getButton() == 3){
            listener.createFolder(subfolder);
        } else if(evt.getButton() == 2 && !list.isSelectionEmpty()){
            listener.loadFolder(list.getSelectedValue(), subfolder);
        }
    }

    private void fillFolders(Collection<String> folders, JList list){
        final DefaultListModel<String> model = new DefaultListModel<>();
        list.setModel(model);
        folders.stream().forEach((folder) -> {
	    model.addElement(folder);
	});
    }
       
    private void deselectList(FocusEvent evt){
        ((JList)evt.getComponent()).clearSelection();
    }
  
    // <editor-fold defaultstate="collapsed" desc=" SETTERS "> 
    public void setListener(ToolsListener l){
        listener = l;
    }
        
    public void setRootFolders(Collection<String> folders){
	fillFolders(folders, lstRootFolders);
    }
        
    public void setSubFolders(Collection<String> folders){
        fillFolders(folders, lstSubFolders);
    }
        
    public void setFolderPath(String path){
        txfFolderPath.setText(path);
    }
        
    public void setImageTotal(int amount){
        txfNumImages.setText(String.valueOf(amount));
        txfImagePos.setMaxValue(amount);
    }
       
    public void setImagePosition(int pos){
        txfImagePos.setInt(pos);
    }
       
    public void setImageName(String name){
        txfImageName.setText(name);
    }
    
    public void setButtonsEnabled(boolean b){
        btnNext.setEnabled(b);
	btnBack.setEnabled(b);
	btnDelete.setEnabled(b);
    }
        
    public void setDeleteEnabled(boolean b){
        btnDelete.setEnabled(b);
    }
    // </editor-fold>

}
