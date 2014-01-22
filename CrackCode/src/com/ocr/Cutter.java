package com.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 切割器，用于第一次切割验证码
 * 
 * @author yuezhen
 * @version $Id: Cutter.java,v 0.1 2010-7-13 下午03:08:05 yuezhen Exp $
 */
public class Cutter {
    public static void main(String[] args) throws IOException {
        BufferedImage image = null;
        BufferedImage checkCode[] =null;
        try {
            image = ImageIO.read(new File("Images/code1.png"));
        } catch (IOException e) {
            System.out.println("can't open checkCode");
        }

        checkCode=Filter.cutter(image);

        for (int i = 0; i < checkCode.length; i++) {
            try {
                ImageIO.write(checkCode[i], "BMP", new File("C:\\passcode\\" + (i+1)+"test" + ".bmp"));
            } catch (IOException e) {
                System.out.println("can't open checkCode");
            }
        }
    }
}
