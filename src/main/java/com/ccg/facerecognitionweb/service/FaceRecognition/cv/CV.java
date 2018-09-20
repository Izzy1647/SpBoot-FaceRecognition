package com.ccg.facerecognitionweb.service.FaceRecognition.cv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;


public class CV {
    public static double calcArea(Rect rect) {
        return rect.width * rect.height;
    }

    public static Boolean cutPic(String location) {
        System.load("D:\\opencv\\build\\java\\x64\\opencv_java320.dll");
        CascadeClassifier faceDetector = new CascadeClassifier("D:\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
        Mat image = Imgcodecs.imread(location);

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("检测到%s张脸", faceDetections.toArray().length));
        if (faceDetections.toArray().length == 0) {
            return false;
        }
        Rect maxRect = new Rect(0, 0, 0, 0);
        for (Rect rect : faceDetections.toArray()) {
            if (calcArea(maxRect) < calcArea(rect)) {
                maxRect = rect;
            }
            //给脸上面画矩形
            //Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }
        if (calcArea(maxRect) > 0) {
            //创建人脸拷贝区域
            Mat roi_img = new Mat(image, maxRect);
            //创建临时的人脸拷贝图形
            Mat tmp_img = new Mat();
            //人脸拷贝
            roi_img.copyTo(tmp_img);
            // 保存最大的1张脸
            Imgcodecs.imwrite(location, tmp_img);
        }
        return true;
    }
}
