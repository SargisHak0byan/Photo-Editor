package com.photoeditor;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;

import static org.opencv.core.CvType.CV_8UC3;

public class Camera extends JFrame {

    private JLabel cameraScreen;
    public JButton btnCapture;
    private VideoCapture capture;
    public Mat image;

    public Camera()
    {
        setLayout(null);
        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        btnCapture = new JButton("capture");
        btnCapture.setBounds(cameraScreen.getWidth() / 3, 480, 150, 40);
        add(btnCapture);

        setSize(new Dimension(640, 560));
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void stop(int idx){
        capture.release();
    }
    public void start(int idx)
    {
        capture = new VideoCapture();
        image = new Mat();
        byte[] imageData;

        ImageIcon icon;
        capture.release();
        capture.open(idx);
        while (capture.isOpened()) {
            capture.read(image);

            final MatOfByte buf = new MatOfByte();
            Mat resizeimage = new Mat(480, 640, CV_8UC3);
            Imgproc.resize(image, resizeimage, new Size(640,480));
            boolean imencode = Imgcodecs.imencode(".jpg", resizeimage, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
        }
    }
}
