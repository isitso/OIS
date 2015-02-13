import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

// This class hold data from text file
// Such as: information, image links, video links, resource links
// Each part begins with '$'
// To use this class: need to have a valid organism name (oName),
// 					call update() to let it read text file again,
//					or use constructor with String parameter

public class OISData {
	public static final String INFO_START_TOKEN = "$Info";
	public static final String IMAGE_START_TOKEN = "$Image";
	public static final String VIDEO_START_TOKEN = "$Video";
	public static final String RESOURCE_START_TOKEN = "$Resource";
	public static final String PART_END_TOKEN = "$";
	
	private String info, oName;
	private ArrayList<String> imageLinks,
							  videoLinks,
							  resourceLinks;
	public OISData(){}
	
	// Set organism name and read text file
	// Text file name = organism name + "txt"
	public OISData(String name){
		oName = name;
		readFile(oName + ".txt");
	}
	
	// Update content of the class using readFile() & oName - organism name
	public void update(){
		readFile(oName + "txt");
	}
	// Read txt file and return a String contains everything thing inside that
	// file
	private void readFile(String fileName) {
		String str = "", line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null)
				str += line + "\n";
			br.close();
			
			// divide information into parts that can be used later
			info = getInfo(str);
			imageLinks = getPart(str, IMAGE_START_TOKEN, PART_END_TOKEN);
			videoLinks = getPart(str, VIDEO_START_TOKEN, PART_END_TOKEN);
			resourceLinks = getPart(str, RESOURCE_START_TOKEN, PART_END_TOKEN);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Extract info part from a String that starts with $Info and ends when
	// another $ is found or end of file
	private String getInfo(String str) {
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

	// Extract a part of String using start & end tokens
	private ArrayList<String> getPart(String str, String startToken,
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
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public ArrayList<String> getImageLinks() {
		return imageLinks;
	}
	public void setImageLinks(ArrayList<String> imageLinks) {
		this.imageLinks = imageLinks;
	}
	public ArrayList<String> getVideoLinks() {
		return videoLinks;
	}
	public void setVideoLinks(ArrayList<String> videoLinks) {
		this.videoLinks = videoLinks;
	}
	public ArrayList<String> getResourceLinks() {
		return resourceLinks;
	}
	public void setResourceLinks(ArrayList<String> resourceLinks) {
		this.resourceLinks = resourceLinks;
	}
	public String getoName() {
		return oName;
	}
	public void setoName(String oName) {
		this.oName = oName;
	}
}
