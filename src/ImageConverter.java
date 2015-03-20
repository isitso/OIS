import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class ImageConverter{

	//Current number assignment of the image
	int imageCounter = 0;
	
	
	//Directory of .jpg/.gif ladybug image
	static String IMAGE_DIRECTORY = "LadyBug_images/";
	
	//This needs to be the directory of your Haar Training/training/rawData 
	//file so it can be accessed by the necessary software
	static String BMP_IMAGE_DIRECTORY = "Positives/";
	
	//Change organism to appropriate one at will
	static String ORGANISM = "ladyBug";
	
	
	/*
	 * Use this to convert the images to .bmp files and store them in the 
		rawData file mentioned above
	 */
	public void convertImage()
	{
	
		/*
		 * Gets all of the files from the lady bug folder and stores
		 * them in folder variable
		 */
		
		File folder = new File(IMAGE_DIRECTORY);
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> files = new ArrayList<File>();
		String filename;

		/*
		 * Use so that we only need to deal with .jpg and .gif files,
		 * put them into an array list because I don't like the size 
		restrictions of an array
		 */
		for(int index = listOfFiles.length - 1; index >= 0; index--)
		{

			filename = listOfFiles[index].getName();
			
			if(filename.contains(".jpg") || filename.contains(".gif"))
			{
				//Add to the list
				files.add(listOfFiles[index]);
			}
		}
		
		//See the amount of files
		System.out.println("Amount of files ==> " + files.size());
		//See all of the files
		Collections.sort(files);
		//Change the .jpg/.gif to a .bmp and store in rawData folder
		for(File file : files)
		{
			filename = file.getName();
			try{
			
				//New file name for the .bmp image
				String newFile = BMP_IMAGE_DIRECTORY + ORGANISM + 
							imageCounter + ".bmp";
				File bmpFile = new File(newFile);
				//For testing
				System.out.println("\nOLD FILE ==> " + filename);
				System.out.println("NEW FILE ==> " + 
							bmpFile.getName());
			 


				//Read in .jpg/.gif file and, change it into a .bmp
				//file and store it in the positive/rawData section
				BufferedImage image = ImageIO.read(file);  
				ImageIO.write(image, "bmp", bmpFile);
			
				imageCounter++;
			
			}
			catch(IOException error)
			{
				System.err.println("There was a conversion error" 
						+ " on image# " + imageCounter);
				error.printStackTrace();
			}
			catch(IllegalArgumentException error)
			{
				System.err.println("There was a conversion error" 
						+ " on image# " + imageCounter);
				error.printStackTrace();
				
			}
	
		}
	}

	public static void main(String args[])
	{
		ImageConverter convert = new ImageConverter();
		convert.convertImage();
	}
	
}


