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

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.DefaultComboBoxModel;
import rubensandreoli.commons.others.Level;
import rubensandreoli.commons.utils.FileUtils;
import rubensandreoli.commons.utils.SwingUtils;
import rubensandreoli.imageorganizer.gui.support.Shortcut;
import rubensandreoli.imageorganizer.gui.support.ShortcutCreationListener;

public class ShortcutCreationDialog extends javax.swing.JDialog {
    private static final long serialVersionUID = 1L;

    private final Component parent;
    private final ShortcutCreationListener listener;
    private final DefaultComboBoxModel<String> model;
    
    public ShortcutCreationDialog(java.awt.Dialog parent, ShortcutCreationListener listener) {
        super(parent, true);

        this.parent = parent;
        this.listener = listener;
        
        model = new DefaultComboBoxModel<>();
        for (Shortcut.Action action : Shortcut.Action.values()) {
            model.addElement(action.name());
        }
        
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblAction = new javax.swing.JLabel();
        cmbAction = new javax.swing.JComboBox<>();
        txfFolder = new javax.swing.JTextField();
        lblKey = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txfKey = new rubensandreoli.commons.swing.KeyField();

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

        txfFolder.setEditable(false);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txfFolder))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addGap(9, 9, 9)
                        .addComponent(btnAdd)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txfFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAction))
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
        final StringBuilder sb = new StringBuilder().append(txfKey.getKey()).append(Shortcut.SEPARATOR);
        final String item = (String)cmbAction.getSelectedItem();
        sb.append(item);
        if(item.equals(Shortcut.Action.MOVE.name())){
            sb.append(Shortcut.SEPARATOR).append(txfFolder.getText());
        }
        
        if(!listener.shortcutCreated(Shortcut.createShortcut(sb.toString()))){
             SwingUtils.showMessageDialog(
                     this, 
                     "This key is already associated with an action.\nPlease, change the key or delete the shortcut\ncontaining it before adding a new one.", 
                     "Duplicated Key", 
                     Level.WARNING, 
                     true);
        }else{
            dispose();
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void cmbActionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbActionItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            final String item = (String)evt.getItem();
            if(item.equals(Shortcut.Action.MOVE.name())){
                final File file = SwingUtils.selectFile(this, SwingUtils.DIRECTORIES_ONLY);
                if(file != null){
                    txfFolder.setText(/*FileUtils.maskPathname(*/file.getPath()/*, 40)*/);
                }else{
                    cmbAction.setSelectedIndex(0);
                }
            }else{
                txfFolder.setText("");
            }
       }
    }//GEN-LAST:event_cmbActionItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cmbAction;
    private javax.swing.JLabel lblAction;
    private javax.swing.JLabel lblKey;
    private javax.swing.JTextField txfFolder;
    private rubensandreoli.commons.swing.KeyField txfKey;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setVisible(boolean b) {
        setLocationRelativeTo(parent);
        super.setVisible(b);
    }
    
}
