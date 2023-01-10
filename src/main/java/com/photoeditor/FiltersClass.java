package com.photoeditor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

public class FiltersClass {
    
    public BufferedImage blurImage(BufferedImage BI)
    {
        Kernel k = new Kernel(3, 3, new float[]{1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3)});
        ConvolveOp op = new ConvolveOp(k);
         return op.filter(BI, null);
    }

    public  BufferedImage grayImage(BufferedImage BI){

        BufferedImage result = new BufferedImage(
                BI.getWidth(),
                BI.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(BI, 0, 0, Color.WHITE, null);

        for (int i = 0; i < result.getHeight(); i++) {
            for (int j = 0; j < result.getWidth(); j++) {
                Color c = new Color(result.getRGB(j, i));
                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);
                Color newColor = new Color(
                        red + green + blue,
                        red + green + blue,
                        red + green + blue);
                result.setRGB(j, i, newColor.getRGB());
            }
        }

        return result;
    }

    public BufferedImage lightenImage(BufferedImage BI)
    {
        RescaleOp op = new RescaleOp(2f, 0, null);
        return op.filter(BI, null);
    }
    public BufferedImage darkenImage(BufferedImage BI)
    {
        RescaleOp op = new RescaleOp(.5f, 0, null);
        return op.filter(BI, null);
    }
    public BufferedImage invertImage(BufferedImage BI)
    {
        for (int x = 0; x < BI.getWidth(); x++) {
            for (int y = 0; y < BI.getHeight(); y++) {
                int rgba = BI.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                BI.setRGB(x, y, col.getRGB());
            }
        }
        return BI;
    }

    public BufferedImage mirrorImage(BufferedImage BI){
        BufferedImage mimg = new BufferedImage(
                BI.getWidth(), BI.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < mimg.getHeight(); y++) {
            for (int lx = 0, rx = BI.getWidth() - 1; lx < BI.getWidth(); lx++, rx--) {
                int p = BI.getRGB(lx, y);
                mimg.setRGB(rx, y, p);
            }
        }
        return mimg;
    }

    public BufferedImage sepiaImage(BufferedImage BI) {
        int width = BI.getWidth();
        int height = BI.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = BI.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int R = (p >> 16) & 0xff;
                int G = (p >> 8) & 0xff;
                int B = p & 0xff;

                int newRed = (int)(0.393 * R + 0.769 * G
                        + 0.189 * B);
                int newGreen = (int)(0.349 * R + 0.686 * G
                        + 0.168 * B);
                int newBlue = (int)(0.272 * R + 0.534 * G
                        + 0.131 * B);

                if (newRed > 255)
                    R = 255;
                else
                    R = newRed;

                if (newGreen > 255)
                    G = 255;
                else
                    G = newGreen;

                if (newBlue > 255)
                    B = 255;
                else
                    B = newBlue;

                p = (a << 24) | (R << 16) | (G << 8) | B;

                BI.setRGB(x, y, p);
            }
        }
        return  BI;
    }

    public BufferedImage WaterMark(BufferedImage BI,String watermark, int x, int y){
        BufferedImage temp = new BufferedImage(
                BI.getWidth(), BI.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        Graphics graphics = temp.getGraphics();
        graphics.drawImage(BI, 0, 0, null);

        graphics.setFont(new Font("Serif", Font.PLAIN, 80));
        graphics.setColor(new Color(255, 0, 0, 40));

        graphics.drawString(watermark, x, y);

        graphics.dispose();
        return temp;
    }
}
