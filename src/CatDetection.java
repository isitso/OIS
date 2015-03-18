

import java.util.ArrayList;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;       
import org.opencv.objdetect.CascadeClassifier;

public class CatDetection{
	
	   //faceDetector.detectMultiScale( image, faceDetections, 1.1, 20, 0, new Size( 30, 30 ), new Size()); cat
      //faceDetector.detectMultiScale( image, faceDetections, 1.1, 70, 0, new Size( 40, 40 ), new Size()); ladybug
	  ////faceDetector.detectMultiScale( image, faceDetections, 1.1, 90, 0, new Size( 50, 50 ), new Size()); hummingbird
	  //haarcascade_frontalcatface_extended.xml
	  static String EXTENSION = ".jpg";
	  static int COUNTER = 0;
	  static String XML_AUTHOR = "Daniel";

	  //Use this if a filename was given for ladybug identification
	  public static void organismIdentification(String filename)
	  {
		  System.out.println("FILENAME: " + filename); //TODO only used for testing. should be deleted in final product. 
		  System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		  System.out.println("\nRunning Detection");//TODO only used for testing. should be deleted in final product. 
		  CascadeClassifier faceDetector = new CascadeClassifier("appdata/haarcascade_frontalcatface_extended.xml");
		  Mat image = Highgui.imread(filename);
		    
		  MatOfRect faceDetections = new MatOfRect();
		  faceDetector.detectMultiScale( image, faceDetections, 1.1, 20, 0, new Size( 30, 30 ), new Size());
		
		
		  // Draw a bounding box around each face.
		  for (Rect rect : faceDetections.toArray()) 
		  {
			  Core.rectangle(image, 
					  			new Point(rect.x, rect.y), 
					  		    new Point(rect.x + rect.width, rect.y + rect.height),
		                        new Scalar(0, 255, 0));
		    }
		
		 // Save the visualized detection.
		 Integer amountDetected = faceDetections.toArray().length;
		    
		 //The filename of the cat photo
		 String organismFileName;
		    
		 if(amountDetected == null || amountDetected == 0) //Do this if no cats were detected
		 {
		    organismFileName = "";
		    System.out.println("The Detector didn't find any organisms");//TODO only used for testing. should be deleted in final product. 
		 }else //Do this if a cat was detected
		 {
		    organismFileName = "appdata/Organism.jpg";
//			System.out.println(String.format("Writing %s", organismFileName)); //TODO only used for testing. should be deleted in final product. 
			Highgui.imwrite(organismFileName, image);
			System.out.println(String.format("Detected %s faces", faceDetections.toArray().length)); //TODO only used for testing. should be deleted in final product. 
			System.out.println( "The Detector found " + amountDetected + " organisms!"); //TODO only used for testing. should be deleted in final product. 
		 }
		    
		    //Returns the cat filename (null if no cat was detected, and a filename if a cat was detected)		    
	}
	  
	  
	  //If there is no String for the file name
	  public static String run(){
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    System.out.println("\nRunning Detection");
	    CascadeClassifier faceDetector = new CascadeClassifier("LadyBugIdentifier.xml");

	    Mat image = Highgui.imread("Cat64.jpg");
		    
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale( image, faceDetections, 1.1, 80, 0, new Size( 55, 55 ), new Size()); //40-50 is a good minNeighbor

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
		    	Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(102, 0, 51));
		}
		
		// Save the visualized detection.
		String filename = "appdata/Organism.jpg";
		System.out.println(String.format("Writing %s", filename)); //TODO only used for testing. should be deleted in final product. 
		Highgui.imwrite(filename, image);
		System.out.println(String.format("Detected %s faces", faceDetections.toArray().length)); //TODO only used for testing. should be deleted in final product. 

	    System.out.println(); //TODO only used for testing. should be deleted in final product. 
	    System.out.println("Done"); //TODO only used for testing. should be deleted in final product. 
	
	    return "Organism detector worked!";
	  }//run method

}//CatFaceDetector

