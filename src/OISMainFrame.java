import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OISMainFrame extends JPanel implements ActionListener {
	// Main frame's components
	private final static int MAIN_BUTTION_NUMBERS = 3;
	private final static String[] MAIN_BUTTON_TEXT = { "CAPTURE", "OPEN FILE",
			"ABOUT OIS" };
	private static OISButton[] mainButtons;
	private final static int CAPTURE_BUTTON_ID = 0;
	private final static int OPEN_BUTTON_ID = 1;
	private final static int ABOUT_BUTTON_ID = 0;

	// Generate content of Main Frame
	// Main Frame consists of buttons
	public OISMainFrame() {
		// Create Main Frame
		super();
		setLayout(new GridLayout(MAIN_BUTTION_NUMBERS, 0));
		// mainFrame.setSize(OIS_WIDTH, OIS_HEIGHT);
		mainButtons = new OISButton[MAIN_BUTTION_NUMBERS];

		// Create main buttons
		for (int i = 0; i < MAIN_BUTTION_NUMBERS; i++) {
			mainButtons[i] = new OISButton(MAIN_BUTTON_TEXT[i]);
			add(mainButtons[i]);
			mainButtons[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainButtons[CAPTURE_BUTTON_ID]) {

		} else if (e.getSource() == mainButtons[OPEN_BUTTON_ID]) {

		} else if (e.getSource() == mainButtons[ABOUT_BUTTON_ID]) {
		}
	}
}
