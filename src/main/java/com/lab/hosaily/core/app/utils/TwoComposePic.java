package com.lab.hosaily.core.app.utils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class TwoComposePic {

    /**
     * 将Image图像转换为Shape图形
     *
     * @param img
     * @param
     * @return Image图像的Shape图形表示
     * @author Hexen
     * @throws InterruptedException
     */
    public static Shape getImageShape(Image img) throws InterruptedException {

        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        int width = img.getWidth(null);
        int height = img.getHeight(null);

        // 首先获取图像所有的像素信息
        PixelGrabber pgr = new PixelGrabber(img, 0, 0, -1, -1, true);
        pgr.grabPixels();
        int pixels[] = (int[]) pgr.getPixels();

        // 循环像素
        for (int i = 0; i < pixels.length; i++) {
            // 筛选，将不透明的像素的坐标加入到坐标ArrayList x和y中
            int alpha = (pixels[i] >> 24) & 0xff;
            if (alpha == 0) {
                continue;
            } else {
                x.add(i % width > 0 ? i % width - 1 : 0);
                y.add(i % width == 0 ? (i == 0 ? 0 : i / width - 1) : i / width);
            }
        }

        // 建立图像矩阵并初始化(0为透明,1为不透明)
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = 0;
            }
        }

        // 导入坐标ArrayList中的不透明坐标信息
        for (int c = 0; c < x.size(); c++) {
            matrix[y.get(c)][x.get(c)] = 1;
        }

		/*
		 * 逐一水平"扫描"图像矩阵的每一行，将不透明的像素生成为Rectangle，
		 * 再将每一行的Rectangle通过Area类的rec对象进行合并， 最后形成一个完整的Shape图形
		 */
        Area rec = new Area();
        int temp = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] == 1) {
                    if (temp == 0)
                        temp = j;
                    else if (j == width) {
                        if (temp == 0) {
                            Rectangle rectemp = new Rectangle(j, i, 1, 1);
                            rec.add(new Area(rectemp));
                        } else {
                            Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
                            rec.add(new Area(rectemp));
                            temp = 0;
                        }
                    }
                } else {
                    if (temp != 0) {
                        Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
                        rec.add(new Area(rectemp));
                        temp = 0;
                    }
                }
            }
            temp = 0;
        }
        return rec;
    }

    /**
     * 两图按轮廓合并
     *
     * @param back
     *            背景填充
     * @param logo
     *            logo
     * @param buck
     *            抠图
     * @param out
     *            输出
     */
    public static void composePic(String back, String logo, String buck, String out) {

        try {

            // logo图
            File logoFile = new File(logo);
            Image logoImg = ImageIO.read(logoFile);
            int lw = logoImg.getWidth(null);
            int lh = logoImg.getHeight(null);

            // 按logo图大小压缩
//            ImageUtil.compress(back, out, lw, lh, 1f, true);

            // 背景图
            File backFile = new File(back);
            Image backImg = ImageIO.read(backFile);
            int bw = backImg.getWidth(null);
            int bh = backImg.getHeight(null);
            System.out.println(bw);
            System.out.println(bh);

            // 抠图区
//            Shape shape = getImageShape(ImageIO.read(new File(buck)));

            // 合成
            BufferedImage img = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            img = g2d.getDeviceConfiguration().createCompatibleImage(bw, bh, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = img.createGraphics();
//            g2d.clip(shape);


            g2d.drawImage(backImg, 0, 0, bw, bh, null);
            g2d.drawImage(logoImg, (bw-lw)/2, (bh-lh)*2/3, lw, lh, null);
            g2d.dispose();

            ImageIO.write(img, "png", new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void composePicUrl(String backUrl, String logo, String out) {
        URL bUrl = null;
        InputStream bIs = null;
        try {
            System.out.println("bUrl: " + backUrl);
            System.out.println("logo: " + logo);
            bUrl = new URL(backUrl);
            bIs = bUrl.openStream();
            // logo图
            File logoFile = new File(logo);
            System.out.println("logo: " + logoFile.getName());
            Image logoImg = ImageIO.read(logoFile);
            int lw = logoImg.getWidth(null);
            int lh = logoImg.getHeight(null);

            // 按logo图大小压缩
//            ImageUtil.compress(back, out, lw, lh, 1f, true);

            // 背景图
            Image backImg = ImageIO.read(bIs);
            int bw = backImg.getWidth(null);
            int bh = backImg.getHeight(null);
            System.out.println(bw);
            System.out.println(bh);

            // 抠图区
//            Shape shape = getImageShape(ImageIO.read(new File(buck)));

            // 合成
            BufferedImage img = new BufferedImage(bw, bh, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            img = g2d.getDeviceConfiguration().createCompatibleImage(bw, bh, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = img.createGraphics();
//            g2d.clip(shape);


            g2d.drawImage(backImg, 0, 0, bw, bh, null);
            g2d.drawImage(logoImg, (bw-lw)/2-2, (bh-lh)*2/7-55, lw, lh, null);
            g2d.dispose();

            ImageIO.write(img, "png", new File(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

