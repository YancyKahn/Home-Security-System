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
	 * �������
	 * 
	 * @param img
	 * @return img
	 */
	public static Mat detectFace(Mat img) {

		// �������ļ�lbpcascade_frontalface.xml�д���һ������ʶ���������ļ�λ��opencv��װĿ¼��
		CascadeClassifier faceDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_frontalface_alt.xml");
		CascadeClassifier eyeDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml");
		// ��ͼƬ�м������
		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(img, faceDetections);

		Rect[] rects = faceDetections.toArray();
		Random r = new Random();
		if (rects != null && rects.length >= 1) {
			status = 1;
			for (Rect rect : rects) {

				// ������
				Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
						new Scalar(0, 0, 255), 2);

				// ʶ������
				Mat faceROI = new Mat(img, rect);
				MatOfRect eyesDetections = new MatOfRect();
				eyeDetector.detectMultiScale(faceROI, eyesDetections);

				// �����ͼ
				if (eyesDetections.toArray().length > 1) {
					save(img, rect, "F:\\File\\opecv\\test1\\" + r.nextInt(2000) + ".jpg");
				}

			}
		}
		return img;
	}

	/**
	 * ���ۼ��
	 * 
	 * @param img
	 * @return img
	 */
	public static Mat detectEye(Mat img) {

		System.out.println("Running Eye DetectFace ... ");
		CascadeClassifier eyeDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml");

		// ��ͼƬ��ʶ������
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
	 * ���˼��
	 * 
	 * @param img
	 * @return img
	 */
	public static Mat detectPedestrain(Mat img) {

		System.out.println("Running Body DetectFace ... ");
		CascadeClassifier eyeDetector = new CascadeClassifier(
				"F:\\Tools\\opencv-4.0.1\\opencv\\sources\\data\\haarcascades\\haarcascade_upperbody.xml");

		// ��ͼƬ��ʶ������
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
	 * opencv���������н�ͼ������
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
