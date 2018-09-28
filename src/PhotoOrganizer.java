import java.awt.EventQueue;
import javax.swing.JOptionPane;

public class PhotoOrganizer {
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "ERROR: Data Scrambler Exception.");
				}
			}
		});
	}
	
}
