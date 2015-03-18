import java.awt.image.BufferedImage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class Mat2Image {
    Mat mat = new Mat();
    BufferedImage img;
    byte[] dat;
    public Mat2Image() {
    }
    public Mat2Image(Mat mat) {
      getSpace(mat);
    
    }
    public void getSpace(Mat mat) {
        this.mat = mat;
        int w = mat.cols(), h = mat.rows();
        if (dat == null || dat.length != w * h * 3)
            dat = new byte[w * h * 3];
        if (img == null || img.getWidth() != w || img.getHeight() != h
            || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
                img = new BufferedImage(w, h, 
                            BufferedImage.TYPE_3BYTE_BGR);
        }
    
    

        public BufferedImage matToBufferedImage(Mat matrix) {  
        	int cols = matrix.cols();  
        	int rows = matrix.rows();  
        	int elemSize = (int)matrix.elemSize();  
    	    byte[] data = new byte[cols * rows * elemSize];  
    	    int type;  
    	    matrix.get(0, 0, data);  
    	    switch (matrix.channels()) {  
    	    	case 1:  
    	    		type = BufferedImage.TYPE_BYTE_GRAY;  
    	    		break;  
        		case 3:  
        			type = BufferedImage.TYPE_3BYTE_BGR;  
        			// bgr to rgb  
        			byte b;  
        			for(int i=0; i<data.length; i=i+3) {  
        				b = data[i];  
        				data[i] = data[i+2];  
        				data[i+2] = b;  
        			}  
        			break;  
        		default:  
        			return null;  
        	}  
    	    BufferedImage image2 = new BufferedImage(cols, rows, type);  
    	    image2.getRaster().setDataElements(0, 0, cols, rows, data);  
    	    return image2;  
        }  
        
    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}