import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/*	OISGUI: There will be mainView and resultView
 * 	mainView contains main buttons that let user capture, open image, and about the app
 * 	resultView contains information of the organism: info, images, videos, and resource links
 * 	resultView will need to use OeISData object to get data from text file
 */
public class OISGUI extends JFrame implements ActionListener {
	// Application's attributes
	private final static String OIS_TITLE = "ORGANISM IDENTIFICATION SYSTEM - OIS";
	private final static int OIS_WIDTH = 540;
	private final static int OIS_HEIGHT = 1000;
	private final static String TOP_IMAGE_PATH = "/appdata/top.jpg";
	private final static String BOTTOM_IMAGE_PATH = "/appdata/bottom.jpg";
	private final static String LEFT_IMAGE_PATH = "/appdata/left.png";
	private final static String RIGHT_IMAGE_PATH = "/appdata/right.jpg";
	private final static String APP_PATH = System.getProperty("user.dir").replace('\\', '/');
	private JLabel topLabel, bottomLabel, leftLabel, rightLabel;
	private ImageIcon topImage, bottomImage, leftImage, rightImage;
	
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
	private OISButton resultButton, resourceButtons[], videoButtons[];
	private JEditorPane infoPane, imagePane;
	private JPanel resultView, resourcePane, videoPane;
	private HTMLEditorKit[] kits;
	private StyleSheet[] styleSheets;
	private Document[] docs;
	private JTabbedPane tabbedPane;
	private JScrollPane[] scrollPanes;
	private OISData data;
	
	// Number of tabs
	public static final int TABBED_PANE_NUMBERS = 4;
	public static final int INFO_TAB_ID = 0;
	public static final int IMAGE_TAB_ID = 1;
	public static final int VIDEO_TAB_ID = 2;
	public static final int RESOURCE_TAB_ID = 3;
	Image img;
	public OISGUI() {
		// Create app frame
		super();
		setTitle(OIS_TITLE);
		setSize(OIS_WIDTH, OIS_HEIGHT);
		setLayout(new BorderLayout());
		
		// Get top, bottom, left, right image and put it around mainView1
		// Get current path; http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
		try{
		topImage = new ImageIcon(APP_PATH + TOP_IMAGE_PATH);
		bottomImage = new ImageIcon(APP_PATH + BOTTOM_IMAGE_PATH);
		leftImage = new ImageIcon(APP_PATH + LEFT_IMAGE_PATH);
		rightImage = new ImageIcon(APP_PATH + RIGHT_IMAGE_PATH);
		
		// Create 4 labels and set icon using respective images
		topLabel = new JLabel(topImage);
		//topLabel.setLayout(new BorderLayout());
		topLabel.setPreferredSize(new Dimension(WIDTH, 80));
		topLabel.setIcon(topImage);
		bottomLabel = new JLabel(bottomImage);
		bottomLabel.setPreferredSize(new Dimension(WIDTH, 80));
		//bottomLabel.setIcon(bottomImage);
		leftLabel = new JLabel(leftImage);
		leftLabel.setPreferredSize(new Dimension(20, HEIGHT- 160));
		rightLabel = new JLabel(rightImage);
		rightLabel.setPreferredSize(new Dimension(20, HEIGHT- 160));
		
		add(topLabel, BorderLayout.NORTH);
		add(bottomLabel, BorderLayout.SOUTH);
		add(leftLabel, BorderLayout.WEST);
		add(rightLabel, BorderLayout.EAST);
		prepareMainView();
		add(mainView, BorderLayout.CENTER);
		setVisible(true);
		}
		catch (Exception e){
			e.printStackTrace();
		}
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
		resultView.setBorder(BorderFactory.createEmptyBorder());
		data = new OISData(oName);
		
		// Tabbed pane
		tabbedPane = new JTabbedPane();
		// Information pane
		scrollPanes = new JScrollPane[TABBED_PANE_NUMBERS];
		prepareInfoPane(oName);
		// for (int i = 2; i < TABBED_PANE_NUMBERS; i++)
		// scrollPanes[i] = new JScrollPane(new JPanel());
		for (int i = 0; i < TABBED_PANE_NUMBERS; i++) {
			tabbedPane.add(TABBED_PANE_CAPTION[i], scrollPanes[i]);
		}
		resultButton = new OISButton("BACK");
		resultView.add(tabbedPane, BorderLayout.CENTER);
		resultView.add(resultButton, BorderLayout.SOUTH);
		// resultView button
		resultButton.addActionListener(this);

	}

	// Handle button click event
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainButtons[CAPTURE_BUTTON_ID]) {
			System.out.println("Hello from button "
					+ MAIN_BUTTON_TEXT[CAPTURE_BUTTON_ID]);
			if (isInternetReachable()){
				prepareResultView("cat");
				remove(mainView);
				add(resultView, BorderLayout.CENTER);
				validate();
				repaint();
			} else {
				JOptionPane.showMessageDialog(this, "Internet Connection Required",
						"Cannot access internet", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == mainButtons[OPEN_BUTTON_ID]) {
			System.out.println("Hello from button "
					+ MAIN_BUTTON_TEXT[OPEN_BUTTON_ID]);
		} else if (e.getSource() == mainButtons[ABOUT_BUTTON_ID]) {
			System.out.println("Hello from button "
					+ MAIN_BUTTON_TEXT[ABOUT_BUTTON_ID]);
		} else if (e.getSource() == resultButton) {
			System.out.println("Hello from resultView buttotn");
			remove(resultView);
			add(mainView);
			validate();
			repaint();
		}
	}

	// Info panes: info, images, video, resources
	public void prepareInfoPane(String oName) {
		infoPane = new JEditorPane();
		//infoPane.setBackground(Color.BLACK);
		infoPane.setEditable(false);
		kits = new HTMLEditorKit[2];
		styleSheets = new StyleSheet[2];
		docs = new Document[2];
		for (int i = 0; i < 2; i++) {
			kits[i] = new HTMLEditorKit();
			// add some styles to the html
			styleSheets[i] = kits[INFO_TAB_ID].getStyleSheet();
			styleSheets[i]
					.addRule("body {font-size: 14px;color:#000; font-family:times; margin: 4px; }");
			styleSheets[i].addRule("h2 {color: #D8D8D8; background-color: #00000; }");
	        styleSheets[i].addRule("div {background-color: #00000; border-style: solid; border-color: #00000;}");
			styleSheets[i].addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
			// create a document, set it on the jeditorpane, then add the html
			docs[i] = kits[i].createDefaultDocument();
		}
		infoPane.setEditorKit(kits[INFO_TAB_ID]);

		String photoName = oName + ".jpg";

		// Information part to be displayed in 1st tabbed pane
		// create some simple html as a string
		String htmlString = "<html>\n"
				+ "<head><link rel='stylesheet' href='style.css'>\n"
				+ "<script src='script.js' type='text/javascript'></script>\n</head>\n"
				+ "<body>\n" + "<div align='center'><img src='file:" + photoName + "' alt='"
				+ photoName + "' height='177' width='250'></div>" + "<p>" + data.getInfo()
				+ "</p>" + "<br><br>" + "</body>\n</html>";

		infoPane.setDocument(docs[INFO_TAB_ID]);
		infoPane.setText(htmlString);
		infoPane.setCaretPosition(0);		// force display info page from the beginning instead of being scrolled down
		
		// Image pane
		// create some simple html as a string
		htmlString = "<html>\n"
				+ "<head><link rel='stylesheet' href='style.css'>\n"
				+ "<script src='script.js' type='text/javascript'></script>\n</head>\n"
				+ "<body>\n" + "<table align='center'>\n";

		if (data.getImageLinks() != null) {
			int index = 0;
			for (int i = 0; i < data.getImageLinks().size(); i++) {
				if (i == 0 || (i % 2) == 0) {
					htmlString += "<tr><td>\n" + "<img src='"
							+ data.getImageLinks().get(i) + "' alt='"
							+ data.getImageLinks().get(i)
							+ "' height='177' width='220'>" + "</td>\n";
				} else {
					htmlString += "<td>\n" + "<img src='"
							+ data.getImageLinks().get(i) + "' alt='"
							+ data.getImageLinks().get(i)
							+ "' height='177' width='220'>" + "</td></tr>\n";
				}
				index = i;
			}

			if ((index % 2) != 0) {
				htmlString += "</tr>\n";
			}
		}
		htmlString += "</table>\n" + "</body>\n</html>";

		imagePane = new JEditorPane();
		imagePane.setBackground(Color.BLACK);
		imagePane.setEditable(false);
		imagePane.setEditorKit(kits[IMAGE_TAB_ID]);
		imagePane.setDocument(docs[IMAGE_TAB_ID]);
		imagePane.setText(htmlString);
		imagePane.setCaretPosition(0);		// force display image pane from the beginning
		
		scrollPanes[INFO_TAB_ID] = new JScrollPane(infoPane);
		scrollPanes[IMAGE_TAB_ID] = new JScrollPane(imagePane);
		scrollPanes[RESOURCE_TAB_ID] = new JScrollPane(prepareResourcePane());
		scrollPanes[VIDEO_TAB_ID] = new JScrollPane(prepareVideoPane());
	}

	// Resource pane consists of buttons, which will open default web browser
	// with the url
	public JPanel prepareResourcePane() {
		resourcePane = new JPanel();
		ArrayList<String> list = data.getResourceLinks();
		if (list != null) {
			resourcePane.setLayout(new GridLayout(list.size(), 1));
			resourceButtons = new OISButton[list.size()];

			for (int i = 0; i < list.size(); i++) {
				resourceButtons[i] = new OISButton(list.get(i));
				resourceButtons[i].setUrl(list.get(i));
				resourceButtons[i].setFont(new Font("Arial", Font.BOLD, 12));
				resourceButtons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						OISButton b = (OISButton) event.getSource();
						try {
							openWebpage(new URI(b.getUrl()));

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				resourcePane.add(resourceButtons[i]);
			}
		}
		return resourcePane;
	}

	// Video pane consists of buttons
	// open url using default web browser
	public JPanel prepareVideoPane() {
		ArrayList<String> list = data.getVideoLinks();
		videoPane = new JPanel();
		videoButtons = new OISButton[list.size()];

		if (list != null) {
			videoPane.setLayout(new GridLayout(list.size(), 1));

			for (int i = 0; i < list.size(); i++) {
				videoButtons[i] = new OISButton("Video " + (i + 1));
				videoButtons[i].setUrl(list.get(i));
				videoButtons[i].setFont(new Font("Arial", Font.BOLD, 20));
				videoButtons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent event) {
						OISButton b = (OISButton) event.getSource();
						try {
							openWebpage(new URI(b.getUrl()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				videoPane.add(videoButtons[i]);
			}
		}
		return videoPane;
	}

	// Get about info from file and put into a panel
	public JPanel prepareAboutPane(){
		OISData about= new OISData("about");
		String aboutInfo = about.getInfo();
		
		return new JPanel();
	}
	// http://stackoverflow.com/questions/10967451/open-a-link-in-browser-with-java-button
	// open Default Web Browser with a specified URL
	public static void openWebpage(URI url) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop()
				: null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(url);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// http://stackoverflow.com/questions/1139547/detect-internet-connection-using-java
	// Check if there is internet connection
	public static boolean isInternetReachable() {
		try {
			// make a URL to a known source
			URL url = new URL("http://www.google.com");

			// open a connection to that source
			HttpURLConnection urlConnect = (HttpURLConnection) url
					.openConnection();

			// trying to retrieve data from the source. If there
			// is no connection, this line will fail
			Object objData = urlConnect.getContent();

		} catch (UnknownHostException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
}
