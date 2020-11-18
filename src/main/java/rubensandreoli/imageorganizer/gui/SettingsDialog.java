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
import java.awt.Frame;
import java.awt.event.ActionEvent;
import rubensandreoli.commons.others.Logger;
import rubensandreoli.commons.others.PickConsumer;
import rubensandreoli.imageorganizer.io.Settings;
import rubensandreoli.imageorganizer.gui.support.Shortcut;

/** References:
 * https://stackoverflow.com/questions/291115/java-swing-using-jscrollpane-and-having-it-scroll-back-to-top
 * https://stackoverflow.com/questions/13510641/add-controls-vertically-instead-of-horizontally-using-flow-layout
 * 
 * @author Rubens A. Andreoli Jr.
 */
public class SettingsDialog extends javax.swing.JDialog implements PickConsumer<Shortcut> {
    private static final long serialVersionUID = 1L;

    private final Settings curSettings;
    private Settings newSettings;
    
    
    public SettingsDialog(Frame parent, Settings settings) {
        super(parent, true);
        initComponents();
        
        setLocationRelativeTo(parent);
        setIconImage(parent.getIconImage());
        
        curSettings = settings;
        fillSettings();
        
        Logger.log.setEnabled(settings.isDebug());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sclShortcuts = new javax.swing.JScrollPane();
        pnlShortcuts = new javax.swing.JPanel();
        btnAddShotcut = new javax.swing.JButton();
        chbHidden = new javax.swing.JCheckBox();
        chbAlert = new javax.swing.JCheckBox();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Settings");
        setResizable(false);

        sclShortcuts.setBorder(javax.swing.BorderFactory.createTitledBorder("Shortcuts"));
        sclShortcuts.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sclShortcuts.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        pnlShortcuts.setMaximumSize(new java.awt.Dimension(228, 32767));
        pnlShortcuts.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pnlShortcutsPropertyChange(evt);
            }
        });
        pnlShortcuts.setLayout(new javax.swing.BoxLayout(pnlShortcuts, javax.swing.BoxLayout.Y_AXIS));
        sclShortcuts.setViewportView(pnlShortcuts);

        btnAddShotcut.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAddShotcut.setText("+");
        btnAddShotcut.setToolTipText("adiciona um campo de reiceta");
        btnAddShotcut.setActionCommand("plusIncome");
        btnAddShotcut.setFocusable(false);
        btnAddShotcut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddShotcutActionPerformed(evt);
            }
        });

        chbHidden.setText("Show hidden folders");
        chbHidden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbHiddenActionPerformed(evt);
            }
        });

        chbAlert.setSelected(true);
        chbAlert.setText("Show warning when deleting");
        chbAlert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbAlertActionPerformed(evt);
            }
        });

        btnSave.setText("OK");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings_big.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sclShortcuts)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddShotcut)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chbAlert)
                            .addComponent(chbHidden))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chbHidden)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chbAlert))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sclShortcuts, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddShotcut)
                    .addComponent(btnSave)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddShotcutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddShotcutActionPerformed
        new ShortcutCreationDialog(this, this).setVisible(true);
    }//GEN-LAST:event_btnAddShotcutActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
         curSettings.update(newSettings);
         dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void chbHiddenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbHiddenActionPerformed
        newSettings.setShowHidden(chbHidden.isSelected());
    }//GEN-LAST:event_chbHiddenActionPerformed

    private void chbAlertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbAlertActionPerformed
        newSettings.setShowAlert(chbAlert.isSelected());
    }//GEN-LAST:event_chbAlertActionPerformed

    private void pnlShortcutsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pnlShortcutsPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlShortcutsPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddShotcut;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox chbAlert;
    private javax.swing.JCheckBox chbHidden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnlShortcuts;
    private javax.swing.JScrollPane sclShortcuts;
    // End of variables declaration//GEN-END:variables

    private void fillSettings(){
        newSettings = new Settings(curSettings.isShowHidden(), curSettings.isShowAlert(), curSettings.getShortcutMap(true));
        chbHidden.setSelected(curSettings.isShowHidden());
        chbAlert.setSelected(curSettings.isShowAlert());
        pnlShortcuts.removeAll();
        curSettings.getShortcutMap(false).values().forEach(s -> listShortcut(s));
    }
    
    private void listShortcut(Shortcut shortcut){
        pnlShortcuts.add(new ShortcutPanel(shortcut, e -> deleteShortcut(e, shortcut)));
        pnlShortcuts.validate();
    }

    private void deleteShortcut(ActionEvent e, Shortcut shortcut) {
        newSettings.removeShortcut(shortcut);
        pnlShortcuts.remove(((Component) e.getSource()).getParent());
        pnlShortcuts.repaint(); //remove from panel
        pnlShortcuts.validate(); //update others to ocupy space
        sclShortcuts.getVerticalScrollBar().setValue(0);
    }

    @Override
    public boolean accept(Shortcut shortcut) {
        if(!newSettings.containsShortcut(shortcut.key)){
            newSettings.addShortcut(shortcut);
            listShortcut(shortcut);
            sclShortcuts.validate();
            return true;
        }
        return false;
    }
 
}
