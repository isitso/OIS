

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Entry {
	
	 public static JFrame theFrame;
	 public JPanel inputs;
	 public JPanel organismInfo;
	 public JPanel solve;
	 JTextField text1 = new JTextField();
	 public static  String fileName;
	 public static  String photoName;



	  
	  public JFrame createGUI(){

	        theFrame = new JFrame ("Organism Identification System (OIS)");
	        theFrame.setLayout(new BorderLayout() );

	        userInputs();
	        solveIt();


	        theFrame.add(this.inputs, BorderLayout.NORTH);
	        theFrame.add(this.solve, BorderLayout.SOUTH);

		        
	        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        theFrame.pack();
	        theFrame.setSize(400, 200);
	        theFrame.setVisible(true);
	        theFrame.setResizable(false);
	        return theFrame;

	    }

	  
	    public JPanel userInputs(){

	        this.inputs = new JPanel();
	        this.inputs.setLayout(new GridLayout(2, 2));
	        this.inputs.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createEmptyBorder(20, 10, 20, 90)));
	        this.inputs.setBackground(Color.decode("#000000"));
	        this.inputs.setOpaque(true);
	        JLabel input1 = new  JLabel("Enter the name of the Organism : ", SwingConstants.LEFT);
	        input1.setForeground(Color.white);
	        this.inputs.add(input1);
	        this.inputs.add(text1);	
	        return this.inputs;
	    }
	    
	    
	    
	    
	    
	    public JPanel solveIt(){

	        this.solve = new JPanel();
	        this.solve.setLayout(new GridLayout(1, 1));
	        this.solve.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
	        this.solve.setBackground(Color.decode("#000000"));
	        this.solve.setOpaque(true);

	        JButton solverButton = new JButton("Search");
	        solverButton.addActionListener(new ActionListener(){
	                @Override
	                public void actionPerformed(ActionEvent event) {
	                	
	                	 
	                	 setFileName(text1.getText()+".txt");
	                	 setPhotoName(text1.getText()+".jpg");
	                	 theFrame.setVisible(false);
	                	 
	                	 
	                	 TabPane a = new TabPane();
	                	 a.Result();

	                	 
	                }
	            });

	         
	        this.solve.add(solverButton);

	         
	        return this.solve;
	    }
 
	    
	    public static String getFileName() {
			return fileName;
		}


		public static void setFileName(String fileName) {
			Entry.fileName = fileName;
		}


		
		
		public static String getPhotoName() {
			return photoName;
		}


		public static void setPhotoName(String photoName) {
			Entry.photoName = photoName;
		}

		
		

		public static void main(String[] args){
	        @SuppressWarnings("unused")
	        Entry g = new Entry();
	        g.createGUI();
	        
	       
	    }
	    

}
