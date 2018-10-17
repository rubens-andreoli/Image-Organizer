package rubensandreoli.imageorganizer;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import rubensandreoli.imageorganizer.io.FolderIO;
import rubensandreoli.imageorganizer.io.Progressable;

public class SplitterDialog extends javax.swing.JDialog {

    private final FolderIO folderIO;
    private int charAt;
    private Progressable progress;
    
    public SplitterDialog(JFrame parent, FolderIO folderIO) {
	super(parent);
	this.folderIO = folderIO;
	initComponents();
	txfFolder.setText(folderIO.getFolderPath());
	txfFolder.setCaretPosition(folderIO.getFolderPath().length());
	progress = new Progressable(){
	    public @Override void setMaximum(int max) {pgbSplitt.setMaximum(max);}
	    public @Override void setValue(int value) {pgbSplitt.setValue(value);}
	    public @Override void reset() {pgbSplitt.setValue(0);}
	    public @Override void increase() {pgbSplitt.setValue(pgbSplitt.getValue()+1);}
	    public @Override void done() {toggleButtons(false);}
	};
	this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txfFolder = new javax.swing.JTextField();
        lblPos = new javax.swing.JLabel();
        txfCharAt = new javax.swing.JTextField();
        pgbSplitt = new javax.swing.JProgressBar();
        btnStart = new javax.swing.JButton();
        btnStop = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Splitter");
        setModalityType(java.awt.Dialog.ModalityType.DOCUMENT_MODAL);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txfFolder.setEditable(false);

        lblPos.setText("Split at position:");

        txfCharAt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfCharAt.setText("0");
        txfCharAt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfCharAtKeyReleased(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnStop.setText("Stop");
        btnStop.setEnabled(false);
        btnStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txfFolder, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txfCharAt, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStop))
                    .addComponent(pgbSplitt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txfFolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfCharAt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPos)
                    .addComponent(btnStart)
                    .addComponent(btnStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pgbSplitt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfCharAtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfCharAtKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            testPosition();
        }
    }//GEN-LAST:event_txfCharAtKeyReleased

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
	testPosition();
	if(charAt > 1){
	    toggleButtons(true);
	    folderIO.startSplitter(charAt, progress);
	}
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopActionPerformed
	toggleButtons(false);
	folderIO.stopSplitter();
    }//GEN-LAST:event_btnStopActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        toggleButtons(false);
	folderIO.stopSplitter();
    }//GEN-LAST:event_formWindowClosing

    private void toggleButtons(boolean splitting){
	btnStart.setEnabled(!splitting);
	btnStop.setEnabled(splitting);
    }
    
    private void testPosition(){
	try{
	    charAt = Integer.parseInt(txfCharAt.getText());
	}catch (NumberFormatException ex){
	    charAt = 0;
	    txfCharAt.setText("0");
	}
    }
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel lblPos;
    private javax.swing.JProgressBar pgbSplitt;
    private javax.swing.JTextField txfCharAt;
    private javax.swing.JTextField txfFolder;
    // End of variables declaration//GEN-END:variables
}
