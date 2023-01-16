package com.photoeditor;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

        btnCapture = new JButton("Capture");
        btnCapture.setForeground(Color.BLACK);
        btnCapture.setBackground(Color.GRAY);
        Border line = new LineBorder(Color.BLACK);
        Border margin = new EmptyBorder(5, 15, 5, 15);
        Border compound = new CompoundBorder(line, margin);
        btnCapture.setBorder(compound);
        btnCapture.setBounds(0, cameraScreen.getHeight(), cameraScreen.getWidth(), 40);
        add(btnCapture);

        setSize(new Dimension(640, 555));
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                capture.release();
            }
        });
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
            Mat resizeimage = image;
            Imgproc.resize(resizeimage,resizeimage,new Size(640,480));
            boolean imencode = Imgcodecs.imencode(".jpg", resizeimage, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
        }
    }
}
