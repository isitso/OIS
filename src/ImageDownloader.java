import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.InterruptedException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageDownloader{

	/*
	*counter file, cat_picture_urls.txt, and IMAGES_DIRECTORY are in the same 
	*directory as this java file and may need to be changed
	*/

	//IMAGES_DIRECTORY must have the path to where cat_images directory
	//should be stored. 
	static String IMAGES_DIRECTORY = "LadyBug_images/";
	static String COUNTER_FILE = "Counter_file1.txt";
	static int COUNTER = 0;
	static String IMAGE_URLS = "LadyBug_image_urls.txt";
	static String ORGANISM = "ladyBug";

	public static void main(String args[]){

		counterSetup( COUNTER_FILE );
		imageIterater( IMAGE_URLS );
		System.out.println( "Program ending" );

	}//main

 	/*
	when running this program, it will state when it is safe to cancel the 
     	program prematurely(as in before it has reached the end of the IMAGE_URLS
	file. i call the sleep method for 2 seconds meaning nothing is being saved
  	or opened in that time. 
	*/

	public static void imageIterater( String imageURLs ){
		File URLFile = new File( IMAGE_URLS );
		String line = null;
		//this counter is different than the static variable
		int counter = 0;
		
		Scanner scan = new Scanner( System.in );
		try{
			BufferedReader reader = new BufferedReader( 
							new FileReader( URLFile ));
			while(( line = reader.readLine()) != null ){
				counter = counter + 1;
				//change code here ...==0 means even, ==1 means odd
				if( counter > COUNTER && counter  % 2 == 0){
					if( saveImage( line , counter )) {
						System.out.println("saving Cat" 
							+ "_image" + counter);
					}//if
					updateCounter( counter );
					try{

						System.out.println("it is now safe"
						      +" to stop the "
					       	      + "program\n");
						Thread.sleep( 2000 );
					}//try
					catch( InterruptedException error ){
						error.printStackTrace();		
					}
				}//if counter >
			}//while line
			reader.close();
		}//try
		catch( IOException error){
			error.printStackTrace();
		}
	}//imageDownloader
	
	/*
	*this method is called when there is an error making an image because the
	*image is probably corrupted. 
	*/

	public static void deleteImage( String fileToDelete ){
		System.out.println( "Attempting to delete file:\n" + fileToDelete);
		File file = new File( fileToDelete);
		file.delete();

	}//deleteImage

	public static boolean saveImage( String URL , int counter ){
		
		System.out.println("Checking if " + IMAGES_DIRECTORY+ "directory "
				 + " exists in same directory as this java file.");
		Path path = Paths.get( IMAGES_DIRECTORY );
		if( Files.exists( path ) ){
			System.out.println( IMAGES_DIRECTORY +" Directory exists");
			
		}//if
		else{
			System.out.println( IMAGES_DIRECTORY +" directory does not " 
					+ "exist where expected or does not "
					+ "exist." );
			File catImagesDirectory = new File( IMAGES_DIRECTORY );
			catImagesDirectory.mkdir();
			System.out.println( IMAGES_DIRECTORY +" created." );
		}
		
		InputStream is = null;
		FileOutputStream os = null;
		String imageFileName = IMAGES_DIRECTORY + ORGANISM + counter 
						+".jpg"; 	

		try{
			URL url = new URL( URL );
			String fileName = url.getFile();
			System.out.println( fileName );	

			//getting the file type of the file to be downloaded
			String[] items =  fileName.split("\\.");
		
			String fileExtension = items[ items.length -1 ];
			imageFileName = IMAGES_DIRECTORY +  ORGANISM + 
					 counter + "." +  fileExtension ;

			os = new FileOutputStream( imageFileName );
			is = url.openStream();
			byte[] b = new byte[2048];
			int length;
			while( (length = is.read(b)) != -1){
				os.write( b, 0, length );
			}//while
			os.close();
			is.close();
			System.out.println( imageFileName + " has been saved." );	
			
			
			
		}//try
		catch( FileNotFoundException error ){
			System.out.println( "file not found at url:\n" + URL );	
			deleteImage( imageFileName );
			return false;
		}//catch
		catch( MalformedURLException error){
			System.out.println( "malformed URL:\n" + URL );	
			return false;
		}//catch
		catch( IOException error){
			System.out.println( "\n****WARNING*****\n"
					+ "io exception:\n" + URL + "\nImage "
					+ "Cat" + counter + ".jpg is probably "
					+"corrupt. this may be because of internet"
					+ " connectivity problems.\n");	
			deleteImage( imageFileName );
			return false;
		}//catch
		
				
		return true;
		

	}//saveImage 

        /*
        this method updates the counter that is saved in a text file that is named 
         in the COUNTER_FILE field in the beginning of the this program. This 
        counter is used so that when reading from the list of urls, the user can 
        stop this program and then continue on from the last saved url as opposed
        to having to start from the beginning.
        */
	public static void updateCounter( int counter ){
			String line = "";
			String counterString = counter + "";
			try{
				System.out.println( "updating counter on file" );
				FileWriter fw = new FileWriter(
						COUNTER_FILE );
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write( counterString );
				bw.close();
			}
			catch( IOException error){
				error.printStackTrace();
			}
		
	}//update Counter

	/*
	*This method sets up the counter variable needed for continuing where we
	*last left off running this code. It reads the counter from a file named
	*Counter_file.txt, if it does not exist, then it creates it. 
	*/	

	public static void counterSetup( String counterFileString ){
		File counterFile = new File( counterFileString );

		//check if the COUNTER_FILE exists else create it
		if( counterFile.isFile() ){
			System.out.println("found Counter_file.txt");

			//going to read the counter from counter_file
			String line = "";
			try{
				BufferedReader reader = new BufferedReader(
						    new FileReader( counterFile ));
				line = reader.readLine();
				//checks if there is a number or not
				if ( line.equals( "" ) || line.equals(null) ){
					COUNTER = 0;
					System.out.println( "no counter in file. "
							+ "counter set to 0");
					FileWriter fw = new FileWriter(
							counterFile);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write( "0" );
					bw.close();
					reader.close();
				}
				else{
					COUNTER = Integer.parseInt( line );
					System.out.println( "counter found." 
							+" counter is: "+ COUNTER);
					reader.close();
				}
	
			}
			catch( IOException error){
				error.printStackTrace();
			}
				
		}//if counterFile	
		else{
			System.out.println("Counter_file.txt does not exist in " +
 					   "expected directory, creating a new one");

			try{
				counterFile.createNewFile();
				FileWriter fw = new FileWriter(
						counterFile);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write( "0" );
				bw.close();
			}
			catch( IOException error){
				error.printStackTrace();
			}
		}
		
	}//counterSetup
}
