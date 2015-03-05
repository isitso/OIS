import java.util.ArrayList;
import java.util.Arrays;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/*TODO 
edit organismIdentification to include all changes that run() has. 
make sure the default image in organismIdentification() is the same extension as 
the captured image. 
*/

public class OrganismDetection{
	
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
		  Mat image = Imgcodecs.imread(filename);
		    
		  MatOfRect faceDetections = new MatOfRect();
		  faceDetector.detectMultiScale( image, faceDetections, 1.1, 20, 0, new Size( 30, 30 ), new Size());
		
		
		  // Draw a bounding box around each face.
		  for (Rect rect : faceDetections.toArray()) 
		  {
			  Imgproc.rectangle(image, 
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
			Imgcodecs.imwrite(organismFileName, image);
			System.out.println(String.format("Detected %s faces", faceDetections.toArray().length)); //TODO only used for testing. should be deleted in final product. 
			System.out.println( "The Detector found " + amountDetected + " organisms!"); //TODO only used for testing. should be deleted in final product. 
		 }
		    
		    //Returns the cat filename (null if no cat was detected, and a filename if a cat was detected)		    
	}
	  
	  /*
          This method does the same as the method above except it does not receive
          a file name string. Instead, it automatically runs on a predetermined 
          image file that would presumably be the captured image that is always 
          saved with the same name.
          */
	  public static String organismIdentification(){
	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	    System.out.println("\nRunning Detection");

	    CascadeClassifier hummingBirdDetector = new CascadeClassifier(
                                                        "hummingBird.xml");
            CascadeClassifier catDetector = new CascadeClassifier( 
                                       "haarcascade_frontalcatface_extended.xml" );
            CascadeCalssifier ladyBugDetector = new CascadeClassifier( 
                                                         "LadyBugIdentifier.xml" );
            String organism = "";
            int numberOfDetections = 0;
            double biggestArea = 0;

            //image that will be read
	    Mat image = Imgcodecs.imread("Cat64.jpg");
		    
     	    MatOfRect hummingBirdDetections = new MatOfRect();
     	    MatOfRect catDetections = new MatOfRect();
     	    MatOfRect ladyBugDetections = new MatOfRect();
	    hummingBirdDetector.detectMultiScale( image, hummingBirdDetections, 
                                                  1.1, 70, 0, new Size( 40, 40 ), 
                                                  new Size());
            catDetector.detectMultiScale( image, catDetections , 1.1, 20, 0, 
                                          new Size( 30, 30 ), new Size()); 
            ladyBugDetector.detectMultiScale( image, ladyBugDetections , 1.1, 80, 
                                              0, new Size( 55, 55 ), new Size());

            /* 
            get the number of organisms detected and add them together to check
            if there is more than one organism detected. 
            */
 
            numberOfDetections =  hummingBirdDetections.toArray().length  
                                   + ladyBugDetections.toArray().length  
                                   + catDetections.toArray().length ;

            //TODO: this is for testing/debugging delete this when ready.
            System.out.println( "Total number of detections: " + 
                                                              numberOfDetections );
            /*
            if the number of detections is more than one, then the program tries to
            find the detection with the biggest bounding box and assumes that it 
            is the biggest. 
            */

            if( numberOfDetections > 1){

              //TODO: this is for testing/debugging delete this when ready.
              System.out.println( "Doing if(NumberOfDetections > 1) " ); 
	      
              double biggestCat = getBiggestArea( catDetections.toArray() );
              double biggestHummingBird = getBiggestArea( 
                                                 hummingBirdDetections.toArray() );
              double biggestLadyBug = getBiggestArea(ladyBugDetections.toArray() );
 
              
              //TODO: this is for testing/debugging delete this when ready.
              System.out.println( "\nBiggestCat area: " + biggestCat ); 
              System.out.println( "BiggestHummingBird area: " + 
                                                              biggestHummingBird );
              System.out.println( "BiggestLadyBug area: " + biggestLadyBug ); 

              /* TODO 
              error may come from above if any of the arrays being zero is a 
              problem. 
              */

              if( biggestCat > biggestHummingBird && biggestCat > biggestLadyBug ){

                //TODO: this is for testing/debugging delete this when ready.
                System.out.println( "Cat has the biggest bounding box" ); 

                biggestArea = biggestCat;    
                organism = "cat";
                Mat editedImage = drawImage( image, catDetections, biggestCat  );
                Imgcodecs.imwrite( fileName, editedImage );
                return organism;
              }//if
              else if( biggestHummingBird > biggestCat && biggestHummingBird > 
                                                                   biggestLadyBug){

                //TODO: this is for testing/debugging delete this when ready.
                System.out.println( "Hummming bird has the biggest bounding box" );

                biggestArea = biggestHummingBird;    
                organism = "HummingBird";
                Mat editedImage = drawImage( image, hummingBirdDetections, 
                                             biggestHummingBird  );

                Imgcodecs.imwrite( fileName, editedImage );
                return organism;
              }//else if
              else if(biggestLadyBug > biggestCat && biggestLadyBug > 
                                                              biggestHummingBird ){

                //TODO: this is for testing/debugging delete this when ready.
                System.out.println( "Lady bug has the biggest bounding box" ); 

                biggestArea = biggestLadyBug;    
                organism = "ladyBug";
                Mat editedImage = drawImage( image, ladyBugDetections, 
                                            biggestLadyBug );
                Imgcodecs.imwrite( fileName, editedImage );
                return organism;
              }//else if
              else{
                
                //TODO: this is for testing/debugging delete this when ready.
                System.out.println( "1 or more bounding boxes of equal size" ); 

                //of the existing areas, they were all the same size 
                return "";
              }//else
            }//if
            else if( numberOfDetections == 1){

                //TODO: this is for testing/debugging delete this when ready.
                System.out.println( "Doing else if( numberOfDetections == 1" ); 

              if( catDetections.toArray().length == 1 ){
                double biggest = getBiggestArea( catDetections.toArray() );
                organism = "Cat";
                Mat editedImage = drawImage( image, catDetections, biggest );
                Imgcodecs.imwrite( fileName, editedImage );
                return organism;
                
              }//if
 
              else if( ladyBugDetections.toArray().length == 1 ){
                double biggest = getBiggestArea( ladyBugDetections.toArray() );
                organism = "ladyBug";
                Mat editedImage = drawImage( image, ladyBugDetections, biggest );
                Imgcodecs.imwrite( fileName, editedImage );
                return organism;
                
              }//if

              else if( hummingBirdDetections.toArray().length == 1 ){
                double biggest = getBiggestArea( hummingBirdDetections.toArray() );
                organism = "hummingBird";
                Mat editedImage = drawImage( image, hummingBirdDetections, 
                                                                         biggest );
                Imgcodecs.imwrite( fileName, editedImage );
                return organism;
                
              }//if
       
              else {

                //TODO: this is for testing/debugging delete this when ready.
                System.our.println( "Something strange happened in else if(     " +
                                  "numberOfDetections == 1). There is a logic   " +
                                  "error.  the else if statements checks if the " +
                                  "number of detections is equal to 1. There    " +
                                  "are 3 if statements within that check to see " +
                                  "if the array length is 1. None of those were " +
                                  "true. ");
              }//else

            }//else if
            /*
            return organism which is "", GUI should return an error since the only
            reason this happens is because there are no organisms detected.
            */
            else {
              return "";
            }//else


	    return "Organism detector worked!";
	  }//run method
      
            
          /*
          Draws a rectangle around the given image and returns the new image 
          */
          public static Mat drawImage( Mat image, Rect[] array, double biggest){
            for( Rect rect : array ){
              if( rect.area() == biggest ){
                Imgproc.rectangle( image, new Point( rect.x, rect.y), 
                                  new Point( rect.x + rect.width, rect.y 
                                  + rect.height), new Scalar( 255, 0, 0 ));
              }//if 
            }//for
            return image;
          }//public

          /* 
          given an array of Rects, it gets the biggest array's area and returns it 
          */
          public static double getBiggestArea( Rect[] arrayOfRect ){
            double biggestArea = 0.0;
            for( Rect rect : arrayOfRect ){
              if( rect.area() > biggestArea ){
                biggestArea = rect.area(); 
              }//if
            }//for 
            return biggestArea;
          }//biggest area

}//CatFaceDetector

