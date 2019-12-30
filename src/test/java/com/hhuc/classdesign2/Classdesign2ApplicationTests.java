package com.hhuc.classdesign2;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@SpringBootTest
class Classdesign2ApplicationTests {

    @Test
    void contextLoads() {


        //最后获取到的视频的图片的路径
        String videoPicture="";
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        try {
			 /*
            获取视频文件
            */
            FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(System.getProperty("user.dir")+"\\src\\main\\resources\\static\\video\\video1.mp4");
            fFmpegFrameGrabber.start();

            //获取视频总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
            System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);

            while (flag <= ftp) {
                frame = fFmpegFrameGrabber.grabImage();
				/*
				对视频的第五帧进行处理
				 */
                if (frame != null && flag==1) {
                    //文件绝对路径+名字
                    String fileName =System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\img1.jpg";

                    //文件储存对象
                    File outPut = new File(fileName);
                    //创建BufferedImage对象
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage bufferedImage = converter.getBufferedImage(frame);
                    ImageIO.write(bufferedImage, "jpg", outPut);

                    //视频第五帧图的路径
                    String savedUrl = outPut.getName();
                    break;
                }
                flag++;
            }
            fFmpegFrameGrabber.stop();
            fFmpegFrameGrabber.close();
        } catch (Exception E) {
            E.printStackTrace();
        }

    }

}
