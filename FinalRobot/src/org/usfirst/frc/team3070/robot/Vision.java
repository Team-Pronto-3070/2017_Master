package org.usfirst.frc.team3070.robot;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Vision extends Thread{
	private GripPipeline pipeline;
	private int lineLocationX = 0;
	
	public synchronized double getLineLocationX() {
		return (lineLocationX/320.0);
	}

	private synchronized void setLineLocationX(int lineLocationX) {
		this.lineLocationX = lineLocationX;
	}

	public Vision(){
		pipeline = new GripPipeline();
	}
	
	public void run()
	{
		// Get the UsbCamera from CameraServer
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		// Set the resolution
		camera.setResolution(320, 240);
		camera.setExposureManual(1);
		// Get a CvSink. This will capture Mats from the camera
		CvSink cvSink = CameraServer.getInstance().getVideo();
		// Setup a CvSource. This will send images back to the Dashboard
		CvSource outputStream = CameraServer.getInstance().putVideo("Rectangle", 320, 240);
		//Sets exposure (DON'T USE THIS)
		//Capture.set(20, -1);

		// Mats are very memory expensive. Lets reuse this Mat.
		Mat mat = new Mat();
		// This cannot be 'true'. The program will never exit if it is. This
		// lets the robot stop this thread when restarting robot code or
		// deploying.
		while (!interrupted()) {
			// Tell the CvSink to grab a frame from the camera and put it
			// in the source mat.  If there is an error notify the output.
			if (cvSink.grabFrame(mat) == 0) {
				// Send the output the error.
				outputStream.notifyError(cvSink.getError());
				// skip the rest of the current iteration
				//System.out.println("Skipping this frame");
				continue;
			}
			//System.out.println(mat);
			pipeline.process(mat);
			// Put a rectangle on the image
			ArrayList<MatOfPoint> test = pipeline.filterContoursOutput();
			//System.out.println("Size: " + test.size());
			if(test.size() == 2){
				int x1 = 0;
				int x2 = 0;
				int width1 = 0;
				int width2 = 0;
				for(int i = 0; i < test.size(); i++)
				{
					Rect rec = Imgproc.boundingRect(test.get(i));
					
					
					Imgproc.rectangle(mat, rec.br(), rec.tl(), new Scalar(255, 255, 255), 5);
					if(i == 0){
						x2 = rec.x;
						width2 = rec.width;
					}
					x1 = rec.x;
					width1 = rec.width;
					//System.out.println("Rectangle height:" + rec.height);
					//System.out.println("Rectangle width:" + rec.width);
				}
				int smaller = 0;
				boolean isRight = true;
				int right = 0;
				if(x1 < x2){
					isRight = true;
					smaller = (x1 + width1);
				}else{
					smaller = (x2 + width2);
					isRight = false;
				}
				if(isRight){
					right = x2;
				}else{
					right = x1;
				}
				int place = Math.abs((smaller + right)/2);
				setLineLocationX(place);
				//System.out.println("Line location: " + place);
				Imgproc.rectangle(mat, new Point(place, 100), new Point(place, 140), new Scalar(255, 255, 255), 5);
			}
			else
			{
				setLineLocationX(0);
			}
			Imgproc.rectangle(mat, new Point(157, 117), new Point(163, 123), new Scalar(0, 0, 255), 4);
			outputStream.putFrame( mat);
		}
	}
}
