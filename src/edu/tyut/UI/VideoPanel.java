package edu.tyut.UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.opencv.core.Mat;

public class VideoPanel extends JPanel {
	public BufferedImage mImg;

	public BufferedImage mat2BI(Mat mat) {
		int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
		byte[] data = new byte[dataSize];
		mat.get(0, 0, data);

		int type = mat.channels() == 1 ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_3BYTE_BGR;
		if (type == BufferedImage.TYPE_3BYTE_BGR) {
			for (int i = 0; i < dataSize; i += 3) {
				byte blue = data[i + 0];
				data[i + 0] = data[i + 2];
				data[i + 2] = blue;
			}
		}
		BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
		image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);

		return image;
	}

	@Override
	public void paint(Graphics g) {
		if (mImg != null) {
			g.drawImage(mImg, 0, 0, mImg.getWidth(), mImg.getHeight(), this);
		}
	}
}
