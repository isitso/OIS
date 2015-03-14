

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/*TODO 
 edit organismIdentification to include all changes that run() has. 
 make sure the default image in organismIdentification() is the same extension as 
 the captured image. 
 */

public class OrganismDetection {

	static int COUNTER = 1;

	static String IMAGE_DIRECTORY = "C:/Users/Kiara/Desktop/human";
	static String FINISHED_DIRECTORY = "appData/";

	/*
	 * This method does the same as the method above except it does not receive
	 * a file name string. Instead, it automatically runs on a predetermined
	 * image file that would presumably be the captured image that is always
	 * saved with the same name.
	 */
	public static String organismIdentification(String path) {
		//Get the file extention of the photo
		String[] items =  path.split("\\.");
		String fileExtension = items[ items.length -1 ];


		String destination = FINISHED_DIRECTORY + "detected" + COUNTER + "." + fileExtension;
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		CascadeClassifier humanDetector = new CascadeClassifier(
				"C:/Users/Kiara/workspace/OISKiara/appdata/humanFace.xml");
		CascadeClassifier catDetector = new CascadeClassifier(
				"C:/Users/Kiara/workspace/OISKiara/appdata/haarcascade_frontalcatface_extended.xml");
		CascadeClassifier ladyBugDetector = new CascadeClassifier(
				"C:/Users/Kiara/workspace/OISKiara/appdata/LadyBugIdentifier.xml");
		CascadeClassifier daisyDetector = new CascadeClassifier(
				"C:/Users/Kiara/workspace/OISKiara/appdata/daisies.xml");
		String organism = "";
		int numberOfDetections = 0;
		double biggestArea = 0;

		// image that will be read
		Mat image = Highgui.imread(path);

		MatOfRect humanDetections = new MatOfRect();
		MatOfRect catDetections = new MatOfRect();
		MatOfRect ladyBugDetections = new MatOfRect();
		MatOfRect daisyDetections = new MatOfRect();

		ladyBugDetector.detectMultiScale(image, ladyBugDetections, 1.1, 70, 0,new Size(50, 50), new Size());
		humanDetector.detectMultiScale(image, humanDetections, 1.1, 50, 0, new Size(50, 50), new Size());
		daisyDetector.detectMultiScale(image, daisyDetections, 1.1, 15, 0, new Size(50, 50), new Size());
		catDetector.detectMultiScale(image, catDetections, 1.1, 90, 0, new Size(50, 50), new Size());
	
		/*
		 * get the number of organisms detected and add them together to check
		 * if there is more than one organism detected.
		 */

		numberOfDetections = humanDetections.toArray().length
				+ ladyBugDetections.toArray().length
				+ catDetections.toArray().length
				+ daisyDetections.toArray().length;

		/*
		 * if the number of detections is more than one, then the program tries
		 * to find the detection with the biggest bounding box and assumes that
		 * it is the biggest.
		 */
		if(numberOfDetections > 0)
		{
			COUNTER++;
		}

		if (numberOfDetections > 1) {

			double biggestCat = getBiggestArea(catDetections.toArray());
			double biggestHuman = getBiggestArea(humanDetections.toArray());
			double biggestLadyBug = getBiggestArea(ladyBugDetections.toArray());
			double biggestDaisy = getBiggestArea(daisyDetections.toArray());

			/*
			 * NOTE error may come from above if any of the arrays being zero is
			 * a problem.
			 */

			if (biggestCat > biggestHuman && biggestCat > biggestLadyBug && biggestCat > biggestDaisy) {

				biggestArea = biggestCat;
				organism = "cat";

				Mat editedImage = drawImage(image, catDetections.toArray(), biggestCat, organism);
				Highgui.imwrite(destination, editedImage);
				return organism;
			}// if
			else if (biggestHuman > biggestCat
					&& biggestHuman > biggestLadyBug
					&& biggestHuman > biggestDaisy) {

				biggestArea = biggestHuman;
				organism = "HummingBird";

				Mat editedImage = drawImage(image, humanDetections.toArray(),
						biggestHuman, organism);

				Highgui.imwrite(destination, editedImage);
				return organism;
			}// else if
			else if (biggestLadyBug > biggestCat
					&& biggestLadyBug > biggestHuman
					&& biggestLadyBug > biggestDaisy) {

				biggestArea = biggestLadyBug;
				organism = "ladyBug";
				System.out.println("LADYBUG detector drew something");
				Mat editedImage = drawImage(image, ladyBugDetections.toArray(),
						biggestLadyBug, organism);
				Highgui.imwrite(destination, editedImage);
				return organism;
			}// else if
			else if (biggestDaisy > biggestCat
					&& biggestDaisy > biggestHuman
					&& biggestDaisy > biggestLadyBug) {

				biggestArea = biggestDaisy;
				organism = "daisy";

				Mat editedImage = drawImage(image, ladyBugDetections.toArray(),
						biggestLadyBug, organism);
				Highgui.imwrite(destination, editedImage);
				return organism;
			}// else if
			else {

				// of the existing areas, they were all the same size
				return "";
			}// else
		}// if
		else if (numberOfDetections == 1) {

			if (catDetections.toArray().length == 1) {
				double biggest = getBiggestArea(catDetections.toArray());
				organism = "Cat";
				Mat editedImage = drawImage(image, catDetections.toArray(), biggest, organism);

				Highgui.imwrite(destination, editedImage);
				return organism;

			}// if

			else if (ladyBugDetections.toArray().length == 1) {
				double biggest = getBiggestArea(ladyBugDetections.toArray());
				organism = "ladyBug";
				Mat editedImage = drawImage(image, ladyBugDetections.toArray(), biggest, organism);

				Highgui.imwrite(destination, editedImage);
				return organism;

			}// if

			else if (humanDetections.toArray().length == 1) {
				double biggest = getBiggestArea(humanDetections.toArray());
				organism = "Human";
				Mat editedImage = drawImage(image, humanDetections.toArray(),
						biggest, organism);

				Highgui.imwrite(destination, editedImage);
				return organism;

			}// if
			
			else if (daisyDetections.toArray().length == 1) {
				double biggest = getBiggestArea(daisyDetections.toArray());
				organism = "daisy";
				Mat editedImage = drawImage(image, humanDetections.toArray(),
						biggest, organism);

				Highgui.imwrite(destination, editedImage);
				return organism;

			}// if

		}// else if
		/*
		 * return organism which is "", GUI should return an error since the
		 * only reason this happens is because there are no organisms detected.
		 */
		else {
			return "";
		}// else
		
		return "Organism detector worked!";
	}// run method

	/*
	 * Draws a rectangle around the given image and returns the new image
	 */
	public static Mat drawImage(Mat image, Rect[] array, double biggest, String organism) {
		//RBG to set the color of the bounding box
		int r = 0, g = 0, b = 0;
		
		//Gives a unique color to each organism picture
		if(organism.equalsIgnoreCase("human"))
		{
			 r = 255;
		}else if(organism.equalsIgnoreCase("ladybug"))
		{
			b = 255;
		}else if(organism.equalsIgnoreCase("cat"))
		{
			g = 255;
		}else if(organism.equalsIgnoreCase("daisy"))
		{
			r = 255;
			b = 255;
		}
		
		for (Rect rect : array) {
			if (rect.area() == biggest) {
				Core.rectangle(image, new Point(rect.x, rect.y), new Point(
						rect.x + rect.width, rect.y + rect.height), new Scalar(
						r, g, b));

			}// if
		}// for
		return image;
	}// public

	/*
	 * given an array of Rects, it gets the biggest array's area and returns it
	 */
	public static double getBiggestArea(Rect[] arrayOfRect) {
		double biggestArea = 0.0;
		for (Rect rect : arrayOfRect) {
			if (rect.area() > biggestArea) {
				biggestArea = rect.area();
			}// if
		}// for
		return biggestArea;
	}// biggest area
	
	public static void testImages()
	{
		File folder = new File(IMAGE_DIRECTORY);
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> files = new ArrayList<File>();
		String filename;

		/*
		 * Use so that we only need to deal with .jpg files
		 */
		for(int index = listOfFiles.length - 1; index >= 0; index--)
		{

			filename = listOfFiles[index].getName();
			
			if(filename.contains(".jpg"))
			{
				//Add to the list
				files.add(listOfFiles[index]);
			}
		}
		
		String directory;

		//Do detection on images
		for(File file : files)
		{
			try
			{
				directory = file.getCanonicalPath();
				organismIdentification(directory);
			} catch (IOException error) 
			{
				error.printStackTrace();
			}
		}
	}
	
	public static void main(String args[])
	{
		OrganismDetection.testImages();
	}

}// OrganismDetection