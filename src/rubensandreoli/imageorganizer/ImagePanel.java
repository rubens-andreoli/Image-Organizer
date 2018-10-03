package rubensandreoli.imageorganizer;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
    
    private static final Cursor MOVE_CURSOR = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    private static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Font SCALE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private static final float SCALE_RATE = 40; //higher = smaller increments
    
    private BufferedImage image;
    private float clickX, clickY, xOffset, yOffset, scale;
    private boolean click;

    public ImagePanel(){
	this.addMouseListener(new MouseListener(){
	    @Override
	    public void mousePressed(MouseEvent e) {
		if(click == false && image != null){
		    clickX = e.getX()-xOffset;
		    clickY = e.getY()-yOffset;
		    setCursor(MOVE_CURSOR);
		    click = true;
		}
	    }

	    @Override
	    public void mouseReleased(MouseEvent e) {
		 click = false;
		 setCursor(DEFAULT_CURSOR);
	    }

	    public @Override void mouseClicked(MouseEvent e) {}
	    public @Override void mouseEntered(MouseEvent e) {}
	    public @Override void mouseExited(MouseEvent e) {}
	});
	
	this.addMouseMotionListener(new MouseMotionListener(){
	    @Override
	    public void mouseDragged(MouseEvent e) {
		xOffset = e.getX()-clickX;
		yOffset = e.getY()-clickY;
		repaint();
	    }

	    public @Override void mouseMoved(MouseEvent e) {}
	});

	this.addComponentListener(new ComponentAdapter() {
	    @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ajustImage();
            }
        });
	
	this.addMouseWheelListener(new MouseWheelListener(){
	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		float inc = -e.getWheelRotation()/SCALE_RATE;
		if(scale+inc > 0){
		    scale += inc;
		    repaint();
		}
	    }
	});
    }
    
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	if(image == null) return;
	Graphics2D g2d = (Graphics2D) g;
	// g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.drawImage(image, 
		(int) xOffset, 
		(int) yOffset,
		(int) (image.getWidth()*scale),
		(int) (image.getHeight()*scale),
		null
	);
	g2d.setFont(SCALE_FONT);
	g2d.drawString(String.format("%.3fx", scale), getWidth()-75, getHeight());
	g2d.dispose();
    }

    public void setImage(BufferedImage image) {
	this.image = image;
	ajustImage();
    }
    
    private void ajustImage(){
	if(image == null) return;
	if(getHeight() < getWidth()){ //scale to fit
	    scale = getHeight()/(float)image.getHeight();
	}else{
	    scale = getWidth()/(float)image.getWidth();
	}
//	if(image.getHeight() > getHeight() || image.getWidth() > getWidth()){ //reduce to fit
//	    if(getHeight() < getWidth()){
//		scale = getHeight()/(float)image.getHeight();
//	    }else{
//		scale = getWidth()/(float)image.getWidth();
//	    }
//	}else{
//	    scale = 1;
//	}
	xOffset = (getWidth()/2)-((image.getWidth()/2)*scale);
	yOffset = (getHeight()/2)-((image.getHeight()/2)*scale);
	repaint();
    }
    
    public void clear(){
	image = null;
	repaint();
    }

}
