import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;


public class OISButton extends JButton{
	private final static int COLOR_R = 100;
	private final static int COLOR_G = 200;
	private final static int COLOR_B = 240;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public OISButton(){
		super();
		setAttr();
	}
	
	public OISButton(String text){
		super(text);
		setAttr();
	}
	
	// Set default attributes for button
	// Font: Arial - Type: Bold - Size: 40
	// Background Color = light blue
	// Foreground Color = White 
	private void setAttr(){
		setBackground(new Color(COLOR_R, COLOR_G, COLOR_B));
		setHorizontalTextPosition(SwingConstants.CENTER);
		setFont(new Font("Arial", Font.BOLD, 40));
		setForeground(Color.WHITE);
	}
}
