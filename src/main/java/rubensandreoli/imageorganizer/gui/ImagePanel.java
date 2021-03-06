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

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import rubensandreoli.commons.utils.FileUtils;
import rubensandreoli.commons.utils.SwingUtils;
import rubensandreoli.imageorganizer.io.ImageFile;

/** References:
 * <br>
 * https://stackoverflow.com/questions/7357969/how-to-use-java-code-to-open-windows-file-explorer-and-highlight-the-specified-f<br>
 * https://stackoverflow.com/questions/46353663/java-gif-resizing<br>
 * https://stackoverflow.com/questions/20924230/java-draw-a-gif<br>
 * https://coderanch.com/t/559292/java/image-frames-animated-gif-files
 *
 * @author Rubens A. Andreoli Jr.
 */
public class ImagePanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 2L;
    
    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS "> 
    private static final Cursor MOVE_CURSOR = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    private static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Font SCALE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private static final Font INFO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    private static final float SCALE_RATE = 40; //higher = smaller increments
    private static final BufferedImage BROKEN_IMAGE = FileUtils.loadImage("/images/broken_image.png");
    private static final String BROKEN_MSG = "ERRORx";
    private static final int INFO_X = 5;
    private static final int SCALE_X_RECOIL = 75;
    private static final int INFO_LINE_SPACING = 4; //higher = lines are closer
    private static final int TEXT_BOTTOM_PADDING = 1;
    // </editor-fold>
    
    private ImageFile image;
    private float clickX, clickY, xOffset, yOffset, scale;
    private boolean click, showInfo;
    private int fontHeight;
    
    public ImagePanel() {
        super(true); //double buffering
        initComponents();
        addListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void addListeners() { 
        addMouseListener(new MouseAdapter(){
	    @Override
	    public void mousePressed(MouseEvent e) {
                if(click == false && image != null && e.getButton() == 1){
		    if(e.isShiftDown()){
                        image.locateOnDisk();
                    }else{
                        clickX = e.getX()-xOffset;
                        clickY = e.getY()-yOffset;
                        setCursor(MOVE_CURSOR);
                        click = true;
                    }
		}
	    }

            @Override
	    public void mouseReleased(MouseEvent e) {
		if(e.getButton() != 1) return; 
		click = false;
		setCursor(DEFAULT_CURSOR);
	    }

            @Override
	    public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    fit();
                }
            }
	});
	
	addMouseMotionListener(new MouseMotionAdapter(){
	    @Override
	    public void mouseDragged(MouseEvent e) {
		if(click == true){ 
		    xOffset = e.getX()-clickX;
		    yOffset = e.getY()-clickY;
		    repaint();
		}
	    }
	});

	addComponentListener(new ComponentAdapter() {
	    @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                fit();
            }
        });
	
	addMouseWheelListener(new MouseWheelListener(){
	    @Override
	    public void mouseWheelMoved(MouseWheelEvent e) {
		float inc = -e.getWheelRotation()/SCALE_RATE;
		if(scale+inc > 0){
		    scale += inc;
		    repaint();
		}
	    }
	});
        
        SwingUtils.registerKeyAction(this, "ZOOM_IN", KeyEvent.VK_ADD, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scale += 1f/SCALE_RATE;
                repaint();
            }
        });
        
        SwingUtils.registerKeyAction(this, "ZOOM_OUT", KeyEvent.VK_SUBTRACT, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final float inc = 1f/SCALE_RATE;
                if(scale-inc > 0){
                    scale -= inc;
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
//	 g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY); //can't see much difference
	g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        String scaleMsg;
        if(image.isFailed()){
            g2d.drawImage(BROKEN_IMAGE,
                    getWidth()/2 - BROKEN_IMAGE.getWidth()/2, 
                    getHeight()/2 - BROKEN_IMAGE.getHeight()/2,
                    BROKEN_IMAGE.getWidth(),
                    BROKEN_IMAGE.getHeight(),
                    null
            );
            scaleMsg = BROKEN_MSG;
        }else{
            g2d.drawImage(image.getImage(),
                    (int) xOffset,
                    (int) yOffset,
                    (int) (image.getWidth()*scale),
                    (int) (image.getHeight()*scale),
                    this
            );
            scaleMsg = String.format("%.3fx", scale);
        }
        
        g2d.setFont(SCALE_FONT);
        g2d.drawString(scaleMsg, getWidth()-SCALE_X_RECOIL, getHeight() - TEXT_BOTTOM_PADDING);
        
        if(showInfo){
            g2d.setFont(INFO_FONT);
            if(fontHeight == 0) fontHeight = g.getFontMetrics().getHeight() - INFO_LINE_SPACING;
            int infoY = getHeight()- ImageFile.INFO_SIZE*fontHeight - TEXT_BOTTOM_PADDING;
            for (int i = 0; i < ImageFile.INFO_SIZE; i++) {
                g2d.drawString(image.getInfo(i), INFO_X, infoY += fontHeight);  
            }
        }
        
        g2d.dispose();
    }
    
    
    public void setImage(ImageFile image) {
        if(image != null){
            this.image = image;
            fit();
        }else{
            clear();
        }
    }

    private void fit(){
	if(image == null) return; //null if double click or resize when empty
	if(getHeight() < getWidth()){ //scale or reduce to fit
	    scale = getHeight()/(float)image.getHeight();
	}else{
	    scale = getWidth()/(float)image.getWidth();
	}
	xOffset = (getWidth()/2)-((image.getWidth()/2)*scale);
	yOffset = (getHeight()/2)-((image.getHeight()/2)*scale);
	repaint();
    }
    
    public void clear(){
	image = null;
	repaint();
    }

    public void toggleShowInfo() {
        this.showInfo = !this.showInfo;
        repaint();
    }

}
