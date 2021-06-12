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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

/** References:
 * https://stackoverflow.com/questions/7065309/jsplitpane-set-resizable-false/54458846#54458846
 */
public class ImagePanel extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;
    
    // <editor-fold defaultstate="collapsed" desc=" STATIC FIELDS "> 
    private static final Cursor MOVE_CURSOR = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    private static final Cursor DEFAULT_CURSOR = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    private static final Font SCALE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 18);
    private static final Font INFO_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    private static final float SCALE_RATE = 40; //higher = smaller increments
    // </editor-fold>
    
    private BufferedImage image;
    private String[] info; //information about the image to be displayed
    private float clickX, clickY, xOffset, yOffset, scale;
    private boolean click, showInfo;
    
    public ImagePanel() {
        initComponents();
        addListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setToolTipText("<html>Image preview<hr>  Double-Click: fit to panel<br> Right-Click: choose folder<br>  Drag-and-Drop: folder</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void addListeners() {
       
        addMouseListener(new MouseAdapter(){
	    @Override
	    public void mousePressed(MouseEvent e) {
                if(click == false && image != null && e.getButton() == 1){
		    clickX = e.getX()-xOffset;
		    clickY = e.getY()-yOffset;
		    setCursor(MOVE_CURSOR);
		    click = true;
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
                    resize();
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
                resize();
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
        
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ADD){
                    scale += 1f/SCALE_RATE;
                    repaint();
                }else if(e.getKeyCode() == KeyEvent.VK_SUBTRACT){
                    final float inc = 1f/SCALE_RATE;
                    if(scale-inc > 0){
                        scale -= inc;
                        repaint();
                    }
                }
                return false; //true to handle only here.
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
        if(showInfo && info != null){
            g2d.setFont(INFO_FONT);
            int fontHeight = g.getFontMetrics().getHeight()-4; //FIX: do only once
            int y = getHeight()- info.length*fontHeight-1;
            for (String line : info)
                g2d.drawString(line, 5, y += fontHeight);
        }
	g2d.dispose();
    }
    
    public void setImage(BufferedImage image, String...info) {
	this.image = image;
        this.info = info;
	resize();
    }
    
    private void resize(){
	if(image == null) return;
	if(getHeight() < getWidth()){ //scale or reduce to fit
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
        info = null;
	repaint();
    }

    public void toggleShowInfo() {
        this.showInfo = !this.showInfo;
        if(image != null) repaint();
    }
 
}
