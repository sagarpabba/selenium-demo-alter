/**
 * created since 2010-8-18
 */
package com.ocr;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * filter  use the tool class
 * 
 * @author Alterhu2020@gmail.com
 * @version $Id: Filter.java,v 0.1 2010-8-18 ÏÂÎç05:59:52 yuezhen Exp $
 */
public class Filter {
    /**
     * remove the black and white function
     * @param image
     * @return
     */
    public static void blackAndWhiteFilter(BufferedImage image) {
        if (image == null) {
            return;
        }

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                image.setRGB(j, i, Tools.pixelConvert(image.getRGB(j, i)));
            }
        }
    }

    /**
     * ¹Âµã¹ýÂËÆ÷
     * @param image
     * @return
     */
    public static void dotFilter(BufferedImage image) {
        if (image == null) {
            return;
        }

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (i > 0 && j > 0 && i < (image.getHeight() - 1) && j < (image.getWidth() - 1)) {
                    if (image.getRGB(j, i) == 0xff000000) {
                        if (image.getRGB(j - 1, i) == 0xffffffff
                            && image.getRGB(j - 1, i - 1) == 0xffffffff
                            && image.getRGB(j, i - 1) == 0xffffffff
                            && image.getRGB(j + 1, i) == 0xffffffff
                            && image.getRGB(j + 1, i + 1) == 0xffffffff
                            && image.getRGB(j, i + 1) == 0xffffffff) {
                            image.setRGB(j, i, 0xffffffff);
                        }
                    }
                }
            }
        }
    }
    
    
    /** 
     * 切割图片(对于数据不重叠有效) 
     * @param img 
     * @return 
     * @throws IOException  
     */  
    public static BufferedImage[] cutter(BufferedImage image) throws IOException  
    {  
        BufferedImage checkCode[] = null;  
        int height = image.getHeight();  
        int width = image.getWidth();  
          
        List<List<Integer>> all = new ArrayList<List<Integer>>();  
        List<Integer> tmp = null;  
          
        //纵向扫描  
        for(int w = 0; w < width - 0; w++)  
        {  
            int h = 0;  
            for(; h < height - 0; h++)  
            {  
                int px = image.getRGB(w, h);  
                  
                //System.out.print(px == -1 ? "-" : "0");  
                //如果遇到非白    色，则记录该y值的值  
                if(px != -1)  
                {  
                    if(tmp == null)  
                    {  
                        tmp = new ArrayList<Integer>();  
                    }  
                      
                    tmp.add(w);  
                    break;  
                }  
            }  
            //如果tmp有记录值，并且上一次扫描中纵向没有任何颜色,那么将记录加入all并截段重计  
            //或者最后一个数据超过边框  
            if((tmp != null && (h == height)) || (w == (width - 1)))  
            {  
                all.add(tmp);  
                tmp = null;  
            }  
            //System.out.println();  
        }  
          
        checkCode = new BufferedImage[all.size()];  
          
        for(int i = 0; i < all.size(); i++)  
        {  
            List<Integer> list = all.get(i);  
              
            if(list == null) continue;  
              
            //开始y轴  
            int yStart = list.get(0);  
            //结束y轴  
            int yEnd = list.get(list.size() - 1);  
              
            System.out.println(yStart + "," + yEnd);  
              
            //截取开始与结束的记录  
            BufferedImage bimg = image.getSubimage(yStart, 0, yEnd - yStart, height);  
              
            checkCode[i] = bimg;  
        }  
          
        return checkCode;  
    }  
}
