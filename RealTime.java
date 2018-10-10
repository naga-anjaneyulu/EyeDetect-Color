package OpenCV.EyeDetection;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Hello world!
 *
 */
public class RealTime 
{
	static JFrame frame;
	static JLabel label;
   static ImageIcon icon;

    public static void main( String[] args )
    {   RealTime obj = new RealTime();
    	nu.pattern.OpenCV.loadShared();
    	
    	CascadeClassifier eyeClassfier = new CascadeClassifier("C:/Users/anji/Desktop/SpringWork/EyeDetection/haarcascade_eye.xml");
    	VideoCapture video = new VideoCapture();
    	video.open(0);
    	if (video.isOpened()) {	
    			while (true) {		
    				Mat eyeframe = new Mat();
    				boolean yes = video.read(eyeframe);
    				if(yes){
    				MatOfRect eyes = new MatOfRect();
    				eyeClassfier.detectMultiScale(eyeframe, eyes);
    				for (Rect rect : eyes.toArray()) {
    					Imgproc.putText(eyeframe, "eye", new Point(rect.x,rect.y-5), 1,
    							2, new Scalar(34,38,255));				
    					Imgproc.rectangle(eyeframe, new Point(rect.x, rect.y), 
    							new Point(rect.x + rect.width, rect.y + rect.height),
    							new Scalar(200, 200, 100),2);
    					Mat coloredEye = eyeframe.submat(rect); 
    					Imgproc.cvtColor(coloredEye, coloredEye, Imgproc.COLOR_RGB2BGR,3);
    					Image img = obj.mat2Image(eyeframe);
    					obj.display(img);
    					
              }
            }
    	} }
    }
    
    public void createFrame() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(800, 700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void display(Image img) {
		
		if (frame == null)
			createFrame();
		if (label != null)
			frame.remove(label);
		icon = new ImageIcon(img);
		label = new JLabel();
		label.setIcon(icon);
		frame.add(label);
		frame.revalidate();
}
	
  public BufferedImage mat2Image(Mat camFeed) {
	
		MatOfByte byteObj = new MatOfByte();
		Imgcodecs.imencode(".jpg", camFeed, byteObj);
		byte[] byteArray = byteObj.toArray();
		BufferedImage img = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			img = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return img;
}
}
