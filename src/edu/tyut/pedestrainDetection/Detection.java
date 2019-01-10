package edu.tyut.pedestrainDetection;

import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class Detection {
	public static int status;

	public int getStatus() {
		int result = status;
		status = 0;
		return result;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 人脸检测
	 * 
	 * @param img
	 * @return img
	 */
	public static Mat detectFace(Mat img) {

		// 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器，该文件位于opencv安装目录中
		CascadeClassifier faceDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
		CascadeClassifier eyeDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml");
		// 在图片中检测人脸
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(img, faceDetections);

		Rect[] rects = faceDetections.toArray();
		Random r = new Random();
		if (rects != null && rects.length >= 1) {
			status = 1;
			for (Rect rect : rects) {

				// 画矩形
				Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(0, 0, 255), 2);

				// 识别人眼
				Mat faceROI = new Mat(img, rect);
				MatOfRect eyesDetections = new MatOfRect();
				eyeDetector.detectMultiScale(faceROI, eyesDetections);

				// 保存截图
				if (eyesDetections.toArray().length > 1) {
					save(img, rect, "F:\\File\\opecv\\test1\\" + r.nextInt(2000) + ".jpg");
				}

			}
		}
		return img;
	}

	/**
	 * 人眼检测
	 * 
	 * @param img
	 * @return img
	 */
	public static Mat detectEye(Mat img) {

		System.out.println("Running Eye DetectFace ... ");
		CascadeClassifier eyeDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml");

		// 在图片中识别人眼
		MatOfRect eyeDetections = new MatOfRect();
		eyeDetector.detectMultiScale(img, eyeDetections);

		Rect[] rects = eyeDetections.toArray();

		if (rects != null && rects.length >= 1) {
			status = 1;
			for (Rect rect : rects) {
				Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(0, 0, 255), 2);
			}
		}
		return img;
	}

	/**
	 * 行人检测
	 * 
	 * @param img
	 * @return img
	 */
	public static Mat detectPedestrain(Mat img) {

		System.out.println("Running Body DetectFace ... ");
		CascadeClassifier eyeDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_upperbody.xml");

		// 在图片中识别行人
		MatOfRect eyeDetections = new MatOfRect();
		eyeDetector.detectMultiScale(img, eyeDetections);

		Rect[] rects = eyeDetections.toArray();

		if (rects != null && rects.length >= 1) {
			status = 1;
			for (Rect rect : rects) {
				Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(0, 0, 255), 2);
			}
		}
		return img;
	}

	/**
	 * opencv将人脸进行截图并保存
	 * 
	 * @param img
	 */
	private static void save(Mat img, Rect rect, String outFile) {
		Mat sub = img.submat(rect);
		Mat mat = new Mat();
		Size size = new Size(300, 300);
		Imgproc.resize(sub, mat, size);
		Imgcodecs.imwrite(outFile, mat);
	}

}
