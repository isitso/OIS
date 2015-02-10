import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

public class OISGUI extends JFrame implements ActionListener {
	// Application's attributes
	private final static String OIS_TITLE = "ORGANISM IDENTIFICATION SYSTEM - OIS";
	private final static int OIS_WIDTH = 500;
	private final static int OIS_HEIGHT = 700;

	// Main frame's components
	private final static int MAIN_BUTTION_NUMBERS = 3;
	private final static String[] MAIN_BUTTON_TEXT = { "CAPTURE", "OPEN FILE",
			"ABOUT OIS" };
	private final static int CAPTURE_BUTTON_ID = 0;
	private final static int OPEN_BUTTON_ID = 1;
	private final static int ABOUT_BUTTON_ID = 2;
	private JPanel mainView;
	private OISButton[] mainButtons;

	// Result frame's components
	private final static String[] TABBED_PANE_CAPTION = { "INFO", "IMAGES",
			"VIDEOS", "RESOURCES" };
	private OISButton resultButton;
	private JEditorPane infoPane;
	private JPanel resultView;
	private HTMLEditorKit kit;
	private StyleSheet styleSheet;
	private Document doc;
	private JTabbedPane tabbedPane;
	private JScrollPane[] scrollPanes;

	// Number of tabs
	public static final int TABBED_PANE_NUMBERS = 4;
	public static final int INFO_TAB_ID = 0;
	public static final int IMAGE_TAB_ID = 1;
	public static final int VIDEO_TAB_ID = 2;
	public static final int RESOURCE_TAB_ID = 4;

	public static final String INFO_START_TOKEN = "$Info";
	public static final String IMAGE_START_TOKEN = "$Image";
	public static final String VIDEO_START_TOKEN = "$Video";
	public static final String RESOURCE_START_TOKEN = "$Resource";
	public static final String PART_END_TOKEN = "$";

	public OISGUI() {
		// Create app frame
		super();
		setTitle(OIS_TITLE);
		setSize(OIS_WIDTH, OIS_HEIGHT);
		prepareMainView();
		add(mainView);
		setVisible(true);
	}

	// Generate content of Main Frame
	// Main Frame consists of buttons
	public void prepareMainView() {
		// Create Main Frame
		mainView = new JPanel();
		mainView.setLayout(new GridLayout(MAIN_BUTTION_NUMBERS, 0));
		mainButtons = new OISButton[MAIN_BUTTION_NUMBERS];

		// Create main buttons
		for (int i = 0; i < MAIN_BUTTION_NUMBERS; i++) {
			mainButtons[i] = new OISButton(MAIN_BUTTON_TEXT[i]);
			mainView.add(mainButtons[i]);
			mainButtons[i].addActionListener(this);
		}
	}

	// Generate content of Result Frame
	// Result Frame will have multiple tab panes
	private void prepareResultView(String oName) {
		resultView = new JPanel();
		resultView.setLayout(new BorderLayout());

		// Tabbed pane
		tabbedPane = new JTabbedPane();

		// Information pane
		scrollPanes = new JScrollPane[TABBED_PANE_NUMBERS];
		prepareInfoPane(oName);
		scrollPanes[INFO_TAB_ID] = new JScrollPane(infoPane);
		for (int i = 1; i < TABBED_PANE_NUMBERS; i++)
			scrollPanes[i] = new JScrollPane(new JPanel());
		for (int i = 0; i < TABBED_PANE_NUMBERS; i++) {
			tabbedPane.add(TABBED_PANE_CAPTION[i], scrollPanes[i]);
		}
		resultButton = new OISButton("BACK");
		resultView.add(tabbedPane, BorderLayout.CENTER);
		resultView.add(resultButton, BorderLayout.SOUTH);
		
		// resultView button
		resultButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainButtons[CAPTURE_BUTTON_ID]) {
			System.out.println("Hello from button "
					+ MAIN_BUTTON_TEXT[CAPTURE_BUTTON_ID]);
			prepareResultView("cat");
			remove(mainView);
			add(resultView);
			validate();
			repaint();

		} else if (e.getSource() == mainButtons[OPEN_BUTTON_ID]) {
			System.out.println("Hello from button "
					+ MAIN_BUTTON_TEXT[OPEN_BUTTON_ID]);
		} else if (e.getSource() == mainButtons[ABOUT_BUTTON_ID]) {
			System.out.println("Hello from button "
					+ MAIN_BUTTON_TEXT[ABOUT_BUTTON_ID]);
		} else if (e.getSource() == resultButton){
			System.out.println("Hello from resultView buttotn");
			remove(resultView);
			add(mainView);
			validate();
			repaint();
		}
	}

	// Read txt file and return a String contains everything thing inside that
	// file
	public static String readFile(String fileName) {
		String str = "", line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null)
				str += line + "\n";
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	// Extract info part from a String that starts with $Info and ends when
	// another $ is found or end of file
	public String getInfo(String str) {
		ArrayList<String> lines = getPart(str, INFO_START_TOKEN, PART_END_TOKEN);
		String result = "";
		for (String line : lines)
			if (line.isEmpty()) {
				result += "<br>";
			} else {
				if (line.startsWith("#"))
					line = "<h2>" + line.substring(1) + "</h2>";
				result += line;
			}
		return result;
	}

	// Extract image links from a String that starts with $Image and ends when
	// another $ is found or end of file
	public ArrayList<String> getImgLinks(String str) {
		return getPart(str, IMAGE_START_TOKEN, PART_END_TOKEN);
	}

	// Extract video links from a String that starts with $Videos and ends when
	// another $ is found or end of file
	public ArrayList<String> getVideoLinks(String str) {
		return getPart(str, VIDEO_START_TOKEN, PART_END_TOKEN);
	}

	// Extrace resource links from a String that starts with $Resource and ends
	// when other $ is found or end of file
	public ArrayList<String> getResources(String str) {
		return getPart(str, RESOURCE_START_TOKEN, PART_END_TOKEN);
	}

	public ArrayList<String> getPart(String str, String startToken,
			String endToken) {
		// splits into multiple lines and store into array
		String[] lines = str.split("\n");
		ArrayList<String> result = new ArrayList<String>();
		boolean isFound = false;
		for (String line : lines) {
			// find the token
			line = line.trim();
			if (!isFound && !line.isEmpty()) {
				if (line.startsWith(startToken))
					isFound = true;
			} else { // find an end token, break the loop and return the result
				if (line.startsWith(endToken))
					break;
				else {
					if (!line.trim().isEmpty())
						result.add(line);
				}
			}
		}
		return result;
	}

	public void prepareInfoPane(String oName) {
		infoPane = new JEditorPane();
		infoPane.setEditable(false);
		kit = new HTMLEditorKit();
		infoPane.setEditorKit(kit);
		// add some styles to the html
		styleSheet = kit.getStyleSheet();
		styleSheet
				.addRule("body {color:#000; font-family:times; margin: 4px; }");
		styleSheet.addRule("h1 {color: blue;}");
		styleSheet.addRule("h2 {color: #ff0000;}");
		styleSheet
				.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

		String fileName = oName + ".txt";
		String photoName = oName + ".jpg";

		String str = readFile(fileName);
		String info = getInfo(str);
		// create some simple html as a string
		String htmlString = "<html>\n"
				+ "<head><link rel='stylesheet' href='style.css'>\n"
				+ "<script src='script.js' type='text/javascript'></script>\n</head>\n"
				+ "<body>\n" + "<img src='file:" + photoName + "' alt='"
				+ photoName + "' height='177' width='248'>" + "<p>" + info
				+ "</p>" + "<br><br>" + "</body>\n";

		// create a document, set it on the jeditorpane, then add the html
		doc = kit.createDefaultDocument();

		infoPane.setDocument(doc);
		infoPane.setText(htmlString);
	}
}
