package com.test.util;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.opencv.opencv_core.IplImage;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


@Slf4j
public class WaterMarkUtils {

    /**
     * 水印字体
     */
    private static final Font FONT = new Font("谢宝林烤肉拌饭", Font.BOLD, 40);
    /**
     * 透明度
     */
    private static final AlphaComposite COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    /**
     * 水印之间的间隔
     */
    private static final int X_MOVE = 300;
    /**
     * 水印之间的间隔
     */
    private static final int Y_MOVE = 140;

    /**
     * 图片打水印(文字)
     *
     * @param srcImgPath       源文件地址
     * @param waterMarkContent 水印内容
     * @param outImgPath       输出文件的地址
     */
    public static void markWithContentByImage(String srcImgPath, String waterMarkContent, String outImgPath) {

        try {
            Color markContentColor = new Color(180, 180, 180);
            // 读取原图片信息
            File srcFile = new File(srcImgPath);
            File outFile = new File(outImgPath);
            BufferedImage srcImg = ImageIO.read(srcFile);
            // 图片宽、高
            int imgWidth = srcImg.getWidth();
            int imgHeight = srcImg.getHeight();
            // 图片缓存
            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
            // 创建绘图工具
            Graphics2D graphics = bufImg.createGraphics();
            // 画入原始图像
            graphics.drawImage(srcImg, 0, 0, imgWidth, imgHeight, null);
            // 设置水印颜色
            createText(markContentColor, waterMarkContent, bufImg, imgWidth, imgHeight, graphics);
            // 输出图片
            try (FileOutputStream fos = new FileOutputStream(outFile);) {
                ImageIO.write(bufImg, "jpg", fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 视频打水印(文字)
     *
     * @param inputPath        源文件地址
     * @param waterMarkContent 水印内容
     * @param outputPath       输出文件的地址
     */
    public static void markWithContentByVideo(String inputPath, String waterMarkContent, String outputPath) {
        long l = System.currentTimeMillis();
        File file = new File(inputPath);
        // 抓取视频资源
        Frame frame;
        try (FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file)) {
            log.info("文件名-->>" + outputPath);
            frameGrabber.start();
            try (FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputPath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels())) {
                recorder.setFormat("mp4");
                recorder.setSampleRate(frameGrabber.getSampleRate());
                recorder.setFrameRate(frameGrabber.getFrameRate());
                recorder.setTimestamp(frameGrabber.getTimestamp());
                recorder.setVideoBitrate(frameGrabber.getVideoBitrate());
                recorder.setVideoCodec(frameGrabber.getVideoCodec());
                recorder.start();
                Color markContentColor = new Color(180, 180, 180);
                while (true) {
                    frame = frameGrabber.grabFrame();
                    if (frame == null) {
                        log.info("视频处理完成");
                        break;
                    }
                    //判断图片帧
                    if (frame.image != null) {
                        IplImage iplImage = Java2DFrameUtils.toIplImage(frame);
                        BufferedImage bufImg = Java2DFrameUtils.toBufferedImage(iplImage);
                        int imgWidth = iplImage.width();
                        int imgHeight = iplImage.height();
                        // 加水印
                        Graphics2D graphics = bufImg.createGraphics();
                        // 创建绘图工具
                        createText(markContentColor, waterMarkContent, bufImg, imgWidth, imgHeight, graphics);

                        Frame newFrame = Java2DFrameUtils.toFrame(bufImg);
                        recorder.record(newFrame);
                    }
                    //设置音频
                    if (frame.samples != null) {
                        recorder.recordSamples(frame.sampleRate, frame.audioChannels, frame.samples);
                    }
                }
                recorder.stop();
                recorder.release();
                frameGrabber.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createText(Color markContentColor, String waterMarkContent, BufferedImage bufImg, int imgWidth, int imgHeight, Graphics2D graphics) {
        // 设置水印颜色
        graphics.setColor(markContentColor);
        // 设置水印透明度
        graphics.setComposite(COMPOSITE);
        // 设置倾斜角度
        graphics.rotate(Math.toRadians(-35), (double) bufImg.getWidth() / 2,
                (double) bufImg.getHeight() / 2);
        // 设置水印字体
        graphics.setFont(FONT);
        // 消除java.awt.Font字体的锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int xCoordinate = -imgWidth / 2;
        int yCoordinate;
        // 字体长度
        int markWidth = FONT.getSize() * getTextLength(waterMarkContent);
        // 字体高度
        int markHeight = FONT.getSize();
        // 循环添加水印
        double d = 1.5;
        while (xCoordinate < imgWidth * d) {
            yCoordinate = -imgHeight / 2;
            while (yCoordinate < imgHeight * d) {
                graphics.drawString(waterMarkContent, xCoordinate, yCoordinate);
                yCoordinate += markHeight + Y_MOVE;
            }
            xCoordinate += markWidth + X_MOVE;
        }
        // 释放画图工具
        graphics.dispose();
    }


    /**
     * 计算水印文本长度
     * 1、中文长度即文本长度 2、英文长度为文本长度二分之一
     *
     * @param text 文字
     * @return int
     */
    public static int getTextLength(String text) {
        // 水印文字长度
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }


    public static void main(String[] args) {
        long time = new Date().getTime();

        markWithContentByImage("D:\\lvchao\\video\\8531713.jpg",
                "谢宝林烤肉拌饭","D:\\lvchao\\video\\16914791355041.png");
//        markWithContentByVideo("D:\\lvchao\\video\\aaaaa.mp4",
//                "谢宝林烤肉拌饭", "D:\\lvchao\\video\\bbbbbbb.mp4");
        long time2 = new Date().getTime();
        System.out.println(time2 -time);
    }
}
