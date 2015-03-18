
//http://www.codeproject.com/Tips/719878/How-to-Use-OpenCV-with-Java-under-Eclipse-IDE


import org.opencv.core.*;
import org.opencv.highgui.Highgui;        
import org.opencv.highgui.VideoCapture;        
        
public class Capture {
   
	
	public void CaptureImage()
	{
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	VideoCapture camera = new VideoCapture(0);
    	
    	if(!camera.isOpened()){
    		System.out.println("Error");
    	}
    	else {
    		Mat frame = new Mat();
    		
    	    while(true){
    	    	if (camera.read(frame)){
    	    		Highgui.imwrite("camera.jpg", frame);
    	    		break;
    	    	}
    	    }	
    	}
    	camera.release();
	}
    
}