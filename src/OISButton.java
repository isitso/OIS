import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.SwingConstants;

/*	Customed JButton: Default Color and Font
 * 					Has String variable to hold the url,
 * 					which is used to open web browser
 */

public class OISButton extends JButton{
	private final static int COLOR_R = 000;
	private final static int COLOR_G = 000;
	private final static int COLOR_B = 000;
	
	private String url;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
