package com.topplus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Base64;

/**
 * Hog
 *
 * @author PengXJ
 * @version 2.0
 * @date 2020/3/31 10:09
 **/
public class Hog {

    private static int GRAYBIT = 2;     //GRAYBIT=4;用12位的int表示灰度值，前4位表示red,中间4们表示green,后面4位表示blue


    /**

     * 求三维的灰度直方图

     * @throws IOException
     * @throws MalformedURLException

     */
    public static void main(String[] args)  {
        /*double[] data5 = getHistgram2("http://pic15.nipic.com/20110713/2328079_172740212177_2.jpg");
        ImageVector.print(data5);
        double[] data1 = getHistgram2("http://imgup01.sj88.com/2018-07/04/09/15306691026479_3.jpg");
        ImageVector.print(data1);*/
        //double[] data2 = getHistgram2("http://res.eqh5.com/o_1cjacked6nsv1m4du77esr1mr4u.jpg");
        double[] data2 = getHistgram2("D:/Desktop/微信图片_20200331101500.png");
        print(data2);
//      double[] data3 = getHistgram2("http://res.eqh5.com/o_1cgqee47bfb966fmf8j472559.jpg");
//      ImageVector.print(data3);
//      double[] data4 = getHistgram2("http://res.eqh5.com/o_1ci40kmlv1c7b16ob1imfk961kjae.png");
//      print(data4);
//      double[] data6 = getHistgram2("http://res.eqh5.com/o_1ci40kmlv1c7b16ob1imfk961kjae.png");
//      print(data6);
    }

    public static void print(double[] data){
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        for(int i=0; i<data.length; i++){
            sb.append(i+"|"+data[i]+" ");
            sb2.append( Double.valueOf(data[i])+",");
        }
//      System.out.println(sb);
        System.out.println(sb2);

        System.out.println( convertArrayToBase64(data));
    }

    public static final String convertArrayToBase64(double[] array) {
        final int capacity = 8 * array.length;
        final ByteBuffer bb = ByteBuffer.allocate(capacity);
        for (int i = 0; i < array.length; i++) {
            bb.putDouble(array[i]);
        }
        bb.rewind();
        final ByteBuffer encodedBB = Base64.getEncoder().encode(bb);
        return new String(encodedBB.array());
    }

    private static BufferedImage readImg(String url)  {
        try {
            FileInputStream in = new FileInputStream(new File("D:\\Desktop\\微信图片_20200331101500.png"));
            //return ImageIO.read(new URL(url).openStream());
            return ImageIO.read(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double[][] getHistgram(String srcPath) {

        BufferedImage img = readImg(srcPath);

        return getHistogram(img);

    }

    /**

     * hist[0][]red的直方图，hist[1][]green的直方图，hist[2][]blue的直方图

     * @param img 要获取直方图的图像

     * @return 返回r,g,b的三维直方图

     */

    public static double[][] getHistogram(BufferedImage img) {

        int w = img.getWidth();

        int h = img.getHeight();

        double[][] hist = new double[3][256];

        int r, g, b;

        int pix[] = new int[w*h];

        pix = img.getRGB(0, 0, w, h, pix, 0, w);

        for(int i=0; i<w*h; i++) {

            r = pix[i]>>16 & 0xff;

            g = pix[i]>>8 & 0xff;

            b = pix[i] & 0xff;

            /*hr[r] ++;

            hg[g] ++;

            hb[b] ++;*/

            hist[0][r] ++;

            hist[1][g] ++;

            hist[2][b] ++;

        }

        for(int j=0; j<256; j++) {

            for(int i=0; i<3; i++) {

                hist[i][j] = hist[i][j]/(w*h);

                //System.out.println(hist[i][j] + "  ");

            }

        }

        return hist;

    }

    /**

     * 求一维的灰度直方图

     * @param srcPath

     * @return

     */

    public static double[] getHistgram2(String srcPath) {

        BufferedImage img = readImg(srcPath);

        return getHistogram2(img);

    }

    /**

     * 求一维的灰度直方图

     * @param img

     * @return

     */


    public static double[] getHistogram2(BufferedImage img) {

        int w = img.getWidth();

        int h = img.getHeight();

        int series = (int) Math.pow(2, GRAYBIT);    //GRAYBIT=4;用12位的int表示灰度值，前4位表示red,中间4们表示green,后面4位表示blue

        int greyScope = 256/series;

        double[] hist = new double[series*series*series];

        int r, g, b, index;

        int pix[] = new int[w*h];

        pix = img.getRGB(0, 0, w, h, pix, 0, w);

        for(int i=0; i<w*h; i++) {

            r = pix[i]>>16 & 0xff;

            r = r/greyScope;

            g = pix[i]>>8 & 0xff;

            g = g/greyScope;

            b = pix[i] & 0xff;

            b = b/greyScope;

            index = r<<(2*GRAYBIT) | g<<GRAYBIT | b;

            hist[index] ++;

        }

        for(int i=0; i<hist.length; i++) {

            hist[i] = hist[i]/(w*h);

            //System.out.println(hist[i] + "  ");

        }

        return hist;

    }
}
