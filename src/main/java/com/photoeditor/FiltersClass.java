package com.photoeditor;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.*;

public class FiltersClass {
    
    public BufferedImage blurImage(BufferedImage BI)
    {
        Kernel k = new Kernel(3, 3, new float[]{1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3),
                                                1f/(3*3),1f/(3*3),1f/(3*3)});
        ConvolveOp op = new ConvolveOp(k);
         return op.filter(BI, null);
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
}
