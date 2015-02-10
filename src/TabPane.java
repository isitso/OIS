

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;


public class TabPane extends Entry{
	// Number of tabs
	public static final int TABBED_PANE_NUMBERS = 4;
	public static final String INFO_START_TOKEN = "$Info";
	public static final String IMAGE_START_TOKEN = "$Image";
	public static final String VIDEO_START_TOKEN = "$Video";
	public static final String RESOURCE_START_TOKEN = "$Resource";
	public static final String PART_END_TOKEN = "$";
	
	
	public JPanel videos;
	public JPanel resources;
	public static String str;
	
	
    public JPanel videosJP(){

        this.videos = new JPanel();
        this.videos.setLayout(new GridLayout(3, 1));
        this.videos.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        this.videos.setOpaque(true);

        JButton solverButton1 = new JButton("Video 1");
        solverButton1.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
 
                	try {
                		
						openWebpage(new URL(getVideoLinks(str).get(0)));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
                }
            });

        
        JButton solverButton2 = new JButton("Video 2");
        solverButton2.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
 
                	try {
                		
						openWebpage(new URL(getVideoLinks(str).get(1)));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

                }
            });
         
        
        JButton solverButton3 = new JButton("Video 3");
        solverButton3.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
                	try {
                		
						openWebpage(new URL(getVideoLinks(str).get(2)));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

                	 
                }
            });
        
        
        
        
        videos.add(solverButton1);
        videos.add(solverButton2);
        videos.add(solverButton3);



         
        return this.videos;
    }


    public JPanel resourcesJP(){

        this.resources = new JPanel();
        this.resources.setLayout(new GridLayout(3, 1));
        this.resources.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        this.resources.setOpaque(true);

        JButton solverButton1 = new JButton(getResources(str).get(0));
        solverButton1.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
 
                	try {
                		
						openWebpage(new URL(getResources(str).get(0)));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
                }
            });

        
        JButton solverButton2 = new JButton(getResources(str).get(1));
        solverButton2.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
 
                	try {
                		
						openWebpage(new URL(getResources(str).get(1)));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

                }
            });
         
        
        JButton solverButton3 = new JButton(getResources(str).get(2));
        solverButton3.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent event) {
                	try {
                		
						openWebpage(new URL(getResources(str).get(2)));
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

                	 
                }
            });
        
        
        
        
        resources.add(solverButton1);
        resources.add(solverButton2);
        resources.add(solverButton3);



         
        return this.resources;
    }


	
	

	public void Result()
	{

		// Main window
		
		JFrame frame = new JFrame("Organism Identification System (OIS)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(520, 800);

		// create jeditorpane
        JEditorPane jEditorPane = new JEditorPane();
        
        // make it read-only
        jEditorPane.setEditable(false);
        // create a scrollpane; modify its attributes as desired
        JScrollPane scrollPane = new JScrollPane(jEditorPane);
        
        // add an html editor kit
        HTMLEditorKit kit = new HTMLEditorKit();
        jEditorPane.setEditorKit(kit);
        
        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
        styleSheet.addRule("h1 {color: blue;}");
        styleSheet.addRule("h2 {color: #ff0000;}");
        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
        styleSheet.addRule("img {float: left; position: absolute; left: 220px; top: 200px;}");      
        
        String fileName = getFileName();
        String photoName = getPhotoName();
        
		str = readFile(fileName);
		String info = getInfo(str);
        // create some simple html as a string
        String htmlString = "<html>\n"
        				  + "<head><link rel='stylesheet' href='style.css'>\n"
        				  + "<script src='script.js' type='text/javascript'></script>\n</head>\n"
                          + "<body>\n"
        				  + "<img src='file:"+photoName+"' alt='"+photoName+"' height='177' width='248'>"
                          + "<p>" + info + "</p>"
                          + "<br><br>"
                          + "</body>\n";
        
        // create a document, set it on the jeditorpane, then add the html
        Document doc = kit.createDefaultDocument();
        
        jEditorPane.setDocument(doc);
        jEditorPane.setText(htmlString);        
        
		// create jeditorpane for photos
        JEditorPane jEditorPane2 = new JEditorPane();
        
        // make it read-only
        jEditorPane2.setEditable(false);
        // create a scrollpane; modify its attributes as desired
        JScrollPane scrollPane2 = new JScrollPane(jEditorPane2);
        
        // add an html editor kit
        HTMLEditorKit kit2 = new HTMLEditorKit();
        jEditorPane2.setEditorKit(kit2);
       
        // add some styles to the html
        StyleSheet styleSheet2 = kit2.getStyleSheet();
        styleSheet2.addRule("body {color:#000; font-family:times; margin: 4px; }");
        styleSheet2.addRule("h1 {color: blue;}");
        styleSheet2.addRule("h2 {color: #ff0000;}");
        styleSheet2.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
        styleSheet2.addRule("img {float: left; position: absolute; left: 220px; top: 200px;}");

        

		
        // create some simple html as a string
        int count = 0;
        ArrayList<String> imgLinks = getImgLinks(str);
        String htmlString2 = "<html>\n"
        				  + "<head><link rel='stylesheet' href='style.css'>\n"
        				  + "<script src='script.js' type='text/javascript'></script>\n</head>\n"
                          + "<body>\n"
                          + "<table>\n";
        for (count = 0; count < imgLinks.size(); count++){
        	if ((count %2) == 0){
            	htmlString2 += "<tr><td>\n"
      				  + "<img src='"+getImgLinks(str).get(count)+"' alt='"+getImgLinks(str).get(count)+"' height='177' width='220'></td>";
        	}
        	else {
            	htmlString2 += "<td>\n"
      				  + "<img src='"+getImgLinks(str).get(count)+"' alt='"+getImgLinks(str).get(count)+"' height='177' width='220'></td></tr>";
        	}
        }
        if ((count % 2) == 1)
        	htmlString2 += "</tr>";
                          /*+ "<tr><td>\n"
        				  + "<img src='"+getImgLinks(str).get(0)+"' alt='"+getImgLinks(str).get(0)+"' height='177' width='220'>"
        				  + "</td>\n"
        				  + "<td>\n"
        				  + "<img src='"+getImgLinks(str).get(1)+"' alt='"+getImgLinks(str).get(1)+"' height='177' width='220'>"
        				  + "</td></tr>\n"
        				  
                         + "<tr><td>\n"
	                     + "<img src='"+getImgLinks(str).get(2)+"' alt='"+getImgLinks(str).get(2)+"' height='177' width='220'>"
	                     + "</td>\n"
	                     + "<td>\n"
	                     + "<img src='"+getImgLinks(str).get(3)+"' alt='"+getImgLinks(str).get(3)+"' height='177' width='220'>"
	                     + "</td></tr>\n"
	                     
	                     
                        + "<tr><td>\n"
	                    + "<img src='"+getImgLinks(str).get(4)+"' alt='"+getImgLinks(str).get(4)+"' height='177' width='220'>"
	                    + "</td>\n"
	                    + "<td>\n"
	                    + "<img src='"+getImgLinks(str).get(5)+"' alt='"+getImgLinks(str).get(5)+"' height='177' width='220'>"
	                    + "</td></tr>\n"
                          */
        
                          htmlString2+= "</table>\n"
                          + "</body>\n";
        
        // create a document, set it on the jeditorpane, then add the html
        Document doc2 = kit2.createDefaultDocument();
        
        jEditorPane2.setDocument(doc2);
        jEditorPane2.setText(htmlString2);

        
        
        videosJP();
        resourcesJP();
   
        
        

		// Tabbed pane which hold all the tabs
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Child tabs 
		JPanel[] panes = new JPanel[TABBED_PANE_NUMBERS];
		for (int i = 0; i < TABBED_PANE_NUMBERS; i++){
			panes[i] = new JPanel();
			panes[i].add(new JLabel("This is a label in tabbed pane #" + i));
		}
		tabbedPane.addTab("Info", scrollPane);
		tabbedPane.addTab("Photos", scrollPane2);
		tabbedPane.addTab("Videos", this.videos);
		tabbedPane.addTab("Resources", this.resources);

	
		
        
		// Put tabbed pane into main window
		frame.getContentPane().add(tabbedPane);
		//frame.pack();
		frame.setVisible(true);
	
	}
	
	// Read txt file and return a String contains everything thing inside that file
	public static String readFile(String fileName){
		  String str = "", line = "";
		  try {
			  BufferedReader br = new BufferedReader(new FileReader(fileName));
			  while ((line = br.readLine()) != null)
				  str +=  line + "\n";
		  } catch (Exception e){
			  e.printStackTrace();
		  }
		  
		  
		  return str;
	  }
	
	// Extract info part from a String that starts with $Info and ends when another $ is found or end of file
	public String getInfo(String str){
		ArrayList<String> lines = getPart(str, INFO_START_TOKEN, PART_END_TOKEN);
		String result = "";
		for (String line: lines)
			if (line.isEmpty()){
				result += "<br>";
			}else {
				if (line.startsWith("#"))
					line = "<h2>" + line.substring(1) + "</h2>";
				result += line;
			}
		return result;
	}
	
	// Extract image links from a String that starts with $Image and ends when another $ is found or end of file
	public ArrayList<String> getImgLinks(String str) {
		return getPart(str, IMAGE_START_TOKEN, PART_END_TOKEN);
	}
	
	// Extract video links from a String that starts with $Videos and ends when another $ is found or end of file
	public ArrayList<String> getVideoLinks(String str){
		return getPart(str, VIDEO_START_TOKEN, PART_END_TOKEN);
	}
	
	// Extrace resource links from a String that starts with $Resource and ends when other $ is found or end of file
	public ArrayList<String> getResources(String str){
		return getPart(str, RESOURCE_START_TOKEN, PART_END_TOKEN);
	}
	
	public ArrayList<String> getPart(String str, String startToken, String endToken){
		// splits into multiple lines and store into array
		String[] lines = str.split("\n");
		ArrayList<String> result = new ArrayList<String>();
		boolean isFound = false;
		for (String line: lines){
			// find the token
			line = line.trim();
			if (!isFound && !line.isEmpty()){
				if (line.startsWith(startToken))
					isFound = true;	
			}
			else{ // find an end token, break the loop and return the result
				if (line.startsWith(endToken))
					break;
				else{
					if (!line.trim().isEmpty())
						result.add(line);
				}
			}
		}
		return result;
	}
	
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
}
