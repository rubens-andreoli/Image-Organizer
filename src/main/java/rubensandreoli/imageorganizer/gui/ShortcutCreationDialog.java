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

import java.awt.Dialog;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import rubensandreoli.commons.others.Level;
import rubensandreoli.commons.others.PickConsumer;
import rubensandreoli.commons.utils.SwingUtils;
import rubensandreoli.imageorganizer.gui.support.Shortcut;

public class ShortcutCreationDialog extends javax.swing.JDialog {
    private static final long serialVersionUID = 1L;

    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS "> 
    private static final String EMPTY_TITLE = "Empty Key";
    private static final String EMPTY_MSG = "Please, type a key to create the shortcut.";
    private static final String DUPLICATED_TITLE = "Duplicated Key";
    private static final String DUPLICATED_MSG = "This key is already associated with an action.\nPlease, change the key or delete the shortcut\ncontaining it before adding a new one.";
    private static final int MAX_FOLDER_LENGTH = 42;
    // </editor-fold>
    
    private final PickConsumer<Shortcut> listener;
    private final DefaultComboBoxModel<String> model;
    
    public ShortcutCreationDialog(Dialog parent, PickConsumer<Shortcut> listener) {
        super(parent, true);

        this.listener = listener;
        
        model = new DefaultComboBoxModel<>();
        for (Shortcut.Action action : Shortcut.Action.values()) {
            model.addElement(action.name());
        }
        
        initComponents();
        txfFolder.setLenght(MAX_FOLDER_LENGTH);
        txfFolder.setDragEnabled(false);
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAction = new javax.swing.JLabel();
        cmbAction = new javax.swing.JComboBox<>();
        lblKey = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txfKey = new rubensandreoli.commons.swing.KeyField();
        txfFolder = new rubensandreoli.commons.swing.PathField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create shortcut:");
        setResizable(false);

        lblAction.setLabelFor(cmbAction);
        lblAction.setText("Action:");

        cmbAction.setModel(model);
        cmbAction.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbActionItemStateChanged(evt);
            }
        });

        lblKey.setText("Key:");

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAction)
                    .addComponent(lblKey))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbAction, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txfKey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 115, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addGap(9, 9, 9)
                        .addComponent(btnAdd))
                    .addComponent(txfFolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAction)
                    .addComponent(txfFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblKey)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCancel)
                        .addComponent(btnAdd)
                        .addComponent(txfKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if(txfKey.getText().isBlank()){
            SwingUtils.showMessageDialog(this, EMPTY_MSG, EMPTY_TITLE, Level.WARNING, true);
            return;
        }
        
        if(!listener.accept(new Shortcut(txfKey.getKey(), Shortcut.Action.valueOf((String)cmbAction.getSelectedItem()), txfFolder.getText()))){
            SwingUtils.showMessageDialog(this, DUPLICATED_MSG, DUPLICATED_TITLE, Level.WARNING, true);
        }else{
            dispose();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void cmbActionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbActionItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if(((String)evt.getItem()).equals(Shortcut.Action.MOVE.name())){
                final File file = SwingUtils.selectFile(this, SwingUtils.DIRECTORIES_ONLY);
                if(file != null){
                    txfFolder.setText(file);
                    txfFolder.setToolTipText(file.getPath());
                }else{
                    cmbAction.setSelectedIndex(0);
                }
            }else{
                txfFolder.setText("");
                txfFolder.setToolTipText(null);
            }
       }
    }//GEN-LAST:event_cmbActionItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cmbAction;
    private javax.swing.JLabel lblAction;
    private javax.swing.JLabel lblKey;
    private rubensandreoli.commons.swing.PathField txfFolder;
    private rubensandreoli.commons.swing.KeyField txfKey;
    // End of variables declaration//GEN-END:variables
 
}
