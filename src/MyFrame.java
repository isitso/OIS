//package org.ski;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



public class MyFrame extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Boolean done;
    
  /**
  * Launch the application.
  */
   

  /**
  * Create the frame.
  */
    public MyFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(675,650);
        done = false; 
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());
        
        JButton captureButton = new JButton("Capture");
        captureButton.setBackground(new Color(000, 000, 000));
        captureButton.setHorizontalTextPosition(SwingConstants.CENTER);
        captureButton.setFont(new Font("Arial", Font.BOLD, 40));
        captureButton.setForeground(Color.WHITE);
        captureButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
                	
               		Capture takePic = new Capture();
                    	takePic.CaptureImage();
			done = true;
                    	setVisible(false);
                    	return;
                    	
                }//actionPerformed
            });//addActionListener

        
        
        contentPane.add(captureButton,  BorderLayout.SOUTH);
        

        new MyThread().start();
    }
    

 
    VideoCap videoCap = new VideoCap();
 
    public void paint(Graphics g){
        g = contentPane.getGraphics();
        
        
        try {
        	
        g.drawImage(videoCap.getOneFrame(), 0, 0, this);
        
        } 
	catch (Exception e) {
		e.printStackTrace();
	}
    }
 
    class MyThread extends Thread{
        @Override
        public void run() {
            for (;;){
                repaint();
		if( done ){
			System.out.println( "Variable done is true");
			return;
		}//if
                try { 
			Thread.sleep(30);
                } 
		catch (InterruptedException e) {
			e.printStackTrace();
	   	}
            }//for  
        }//run 
    }//My thread
}
