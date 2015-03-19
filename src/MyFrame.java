//package org.ski;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.opencv.highgui.Highgui;



public class MyFrame extends JLabel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    
  /**
  * Launch the application.
  */
   

  /**
  * Create the frame.
  */BufferedImage img;
    public MyFrame() {
       setPreferredSize(new Dimension(380, 380));
       videoCap = new VideoCap();
    }
    
    public boolean capture(String fileName){
    	return videoCap.capture(fileName);
    }
 
    VideoCap videoCap;
 
    public void release(){
    	if (videoCap != null)
    		videoCap.release();
    }
    
    public void update(){
    	if (videoCap == null)
        	videoCap = new VideoCap();
        
        try {
        setIcon(new ImageIcon(videoCap.getOneFrame()));
        repaint();
        } catch (Exception e) {e.printStackTrace();}
    }
    /*
    public void paint(Graphics g){
    	super.paint(g);
    	System.out.println("Painting of MyFrame");
        g = getGraphics();
        if (videoCap == null)
        	videoCap = new VideoCap();
        
        try {
        g.drawImage(videoCap.getOneFrame(), 0, 0, this);
        
        } catch (Exception e) {e.printStackTrace();}
    }
    */
 
}