package rubensandreoli.imageorganizer;


import java.awt.event.KeyEvent;
import rubensandreoli.imageorganizer.io.FolderIO;

public class SplitterDialog extends javax.swing.JDialog {

    private final FolderIO folderIO;
    private int pos;
    
    public SplitterDialog(java.awt.Frame parent, FolderIO folderIO) {
	super(parent);
	this.folderIO = folderIO;
	initComponents();
	txfFolder.setText(folderIO.getFolderPath());
	txfFolder.setCaretPosition(folderIO.getFolderPath().length());
	this.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txfFolder = new javax.swing.JTextField();
        lblPos = new javax.swing.JLabel();
        txfPos = new javax.swing.JTextField();
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

        lblPos.setText("Split at char position:");

        txfPos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txfPos.setText("0");
        txfPos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txfPosKeyReleased(evt);
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
                        .addComponent(txfPos, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txfPos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPos)
                    .addComponent(btnStart)
                    .addComponent(btnStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pgbSplitt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfPosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txfPosKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            testPosition();
        }
    }//GEN-LAST:event_txfPosKeyReleased

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
	testPosition();
	if(pos > 1){
	    toggleButtons(true);
	    folderIO.startSplitter(pos, pgbSplitt);
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
	    pos = Integer.parseInt(txfPos.getText());
	}catch (NumberFormatException ex){
	    pos = 0;
	    txfPos.setText("0");
	}
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnStop;
    private javax.swing.JLabel lblPos;
    private javax.swing.JProgressBar pgbSplitt;
    private javax.swing.JTextField txfFolder;
    private javax.swing.JTextField txfPos;
    // End of variables declaration//GEN-END:variables
}
