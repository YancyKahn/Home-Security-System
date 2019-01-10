package edu.tyut.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import edu.tyut.pedestrainDetection.Detection;
import edu.tyut.utils.TypeTrans;

public class Window {
	private JFrame frame;
	private String title;
	private int ctrlVideo;
	private int ctrlSensor;
	private int ctrlSmoke; // 烟雾报警
	private int ctrlith; // 热敏电阻报警
	private String value; // 串口上的数据
	private int ctrlAll;

	private double value1, value2;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		System.out.println("-------------");
		System.out.println("value: " + value);
		this.value = value;
	}

	public Window(String title) throws Exception {
		// TODO Auto-generated constructor stub
		this.title = title;
		ctrlVideo = 0;
		ctrlSensor = 0;
		ctrlSmoke = 0;
		ctrlith = 0;
		frame = new JFrame(title);
		value1 = value2 = 0;
	}

	public void creatFream() {

		try {
			// 抓取抓取视频
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			VideoCapture capture = new VideoCapture(0);
			int height = (int) capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
			int width = (int) capture.get(Videoio.CAP_PROP_FRAME_WIDTH);
			if (height == 0 || width == 0) {
				throw new Exception("camera not found!");
			}
			// ------------------------------------------------------
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			// frame.setLayout(new BorderLayout());
			JPanel panel = new JPanel(new BorderLayout());
			/**
			 * 上方为标签
			 */
			JLabel toplabel = new JLabel("Home Security System", JLabel.CENTER);
			toplabel.setFont(new Font(null, Font.PLAIN, 35));
			panel.add(toplabel, BorderLayout.NORTH);

			/**
			 * 左边为视频
			 */
			Box vBox = Box.createVerticalBox();
			JLabel leftLale = new JLabel("Video", JLabel.CENTER);
			leftLale.setBorder(BorderFactory.createLineBorder(Color.green, 5));
			leftLale.setFont(new Font(null, Font.PLAIN, 25));
			JPanel left = new JPanel(new FlowLayout());
			VideoPanel videoPanel = new VideoPanel();
			videoPanel.setPreferredSize(new Dimension(650, 420));
			videoPanel.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
			// 增加鉴别按钮
			JButton btVedo = new JButton("原始视频");
			JButton btPede = new JButton("行人检测");
			JButton btFace = new JButton("脸部识别");
			JButton btEyes = new JButton("眼睛识别");
			JButton btVideoStatus = new JButton("");
			btVideoStatus.setPreferredSize(new Dimension(40, 20));
			btVedo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					ctrlVideo = 0;
				}
			});
			btPede.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ctrlVideo = 1;
				}
			});

			btFace.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ctrlVideo = 2;
				}
			});

			btEyes.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ctrlVideo = 3;
				}
			});

			vBox.add(leftLale);
			vBox.add(videoPanel);
			JPanel leftbt = new JPanel(new FlowLayout());
			leftbt.add(btVedo);
			leftbt.add(btPede);
			leftbt.add(btFace);
			leftbt.add(btEyes);
			vBox.add(leftbt);
			vBox.add(btVideoStatus);
			left.add(vBox);
			left.setBorder(BorderFactory.createLineBorder(Color.red, 10));
			panel.add(left, BorderLayout.WEST);

			/**
			 * 中间为数据输出
			 */
			JPanel center = new JPanel(new FlowLayout());
			JLabel centerLabel = new JLabel("Display", JLabel.CENTER);
			centerLabel.setFont(new Font(null, Font.PLAIN, 25));
			centerLabel.setBorder(BorderFactory.createLineBorder(Color.green, 5));
			Box vBox2 = Box.createVerticalBox();

			DataWindow centerDisplay = new DataWindow();

			JLabel centerLabel2 = new JLabel("烟雾", JLabel.CENTER);
			JLabel centerLabel3 = new JLabel("红外热敏", JLabel.CENTER);
			// 温度, 湿度
			JButton btTmp = new JButton("温度");
			JButton bthum = new JButton("湿度");
			JButton btsmk = new JButton(""); // 烟雾
			JButton btith = new JButton(""); // 红外热敏
			btsmk.setPreferredSize(new Dimension(40, 20));
			btith.setPreferredSize(new Dimension(40, 20));
			btTmp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					centerDisplay.clear();
					ctrlSensor = 0;
				}
			});
			bthum.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					centerDisplay.clear();
					ctrlSensor = 1;
				}
			});
			JPanel emptyPanel = new JPanel();
			emptyPanel.setPreferredSize(new Dimension(10, 30));
			JPanel emptyPanel2 = new JPanel();
			emptyPanel2.setPreferredSize(new Dimension(10, 30));
			vBox2.add(centerLabel);
			vBox2.add(centerDisplay);
			JPanel centerBt = new JPanel(new FlowLayout());
			centerBt.add(btTmp);
			centerBt.add(bthum);
			vBox2.add(centerBt);
			vBox2.add(emptyPanel);
			vBox2.add(centerLabel2);
			vBox2.add(btsmk);
			vBox2.add(emptyPanel2);
			vBox2.add(centerLabel3);
			vBox2.add(btith);
			center.add(vBox2);
			center.setBorder(BorderFactory.createLineBorder(Color.red, 10));
			panel.add(center, BorderLayout.CENTER);

			/**
			 * 右边为状态栏
			 */
			Box vBox3 = Box.createVerticalBox();
			JPanel emptyPanel3 = new JPanel();
			emptyPanel.setPreferredSize(new Dimension(10, 30));
			JLabel rightLabel = new JLabel("Status", JLabel.CENTER);
			rightLabel.setBorder(BorderFactory.createLineBorder(Color.green, 5));
			rightLabel.setFont(new Font(null, Font.PLAIN, 25));

			JButton btStatus = new JButton("");
			btStatus.setPreferredSize(new Dimension(100, 100));
			JPanel right = new JPanel(new FlowLayout());
			vBox3.add(rightLabel);
			vBox3.add(emptyPanel3);
			vBox3.add(btStatus);
			right.add(vBox3);
			right.setBorder(BorderFactory.createLineBorder(Color.red, 10));
			right.setPreferredSize(new Dimension(200, 100));
			panel.add(right, BorderLayout.EAST);

			/**
			 * 
			 */
			frame.setContentPane(panel);
			// frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.setAlwaysOnTop(true);
			frame.setResizable(false);

			/**
			 * 窗口数据处理, 视频, 温湿度, 热敏, 烟雾, 总状态
			 */
			Mat capImg = new Mat();
			Mat temp = new Mat();
			while (frame.isShowing()) {
				// 视频
				capture.read(capImg);
				Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);
				Mat img = capImg;
				Detection.status = 0;
				if (ctrlVideo == 1) {
					// System.out.println("Running Pedestrain DetectFace ... ");
					img = Detection.detectPedestrain(capImg);
				} else if (ctrlVideo == 2) {
					// System.out.println("Running Face DetectFace ... ");
					img = Detection.detectFace(capImg);
				} else if (ctrlVideo == 3) {
					// System.out.println("Running Eye DetectFace ... ");
					img = Detection.detectEye(capImg);
				}
				if (Detection.status == 1) {
					btVideoStatus.setBackground(Color.RED);
				} else {
					btVideoStatus.setBackground(Color.GREEN);
				}
				videoPanel.mImg = videoPanel.mat2BI(img);
				videoPanel.repaint();

				// 数据
				if (value.substring(8, 10).compareTo("AA") != 0) { // 忽略AA

					long t = System.currentTimeMillis();
					double v = (new Random().nextInt(200)) * 1.0 - 100;
					String type = value.substring(7, 8);
					System.out.println(type);
					System.out.println("ctrl sensor:" + ctrlSensor);
					System.out.println("value: " + value);
					// 温湿度
					if (type.compareTo("3") == 0) {
						if (ctrlSensor == 0) {
							v = TypeTrans.tempaerature(value);
						} else if (ctrlSensor == 1) {
							v = TypeTrans.humidity(value);
						}
						value1 = v;
						if ((Math.abs(value1 - value2) < 10) && v <= 100 && v >= -100 && value1 != value2) {
							centerDisplay.addData(t, v);
						}
						value2 = value1;
					}

					// 烟雾和热敏控制

					if (type.compareTo("7") == 0 && TypeTrans.isSmoke(value)) {
						ctrlSmoke = 1;
					}
					if (type.compareTo("8") == 0 && TypeTrans.isSmoke(value)) {
						ctrlith = 1;
					}
					if (type.compareTo("8") == 0 && TypeTrans.isITH(value) == false) {
						ctrlith = 0;
					}
					if (type.compareTo("7") == 0 && TypeTrans.isSmoke(value) == false) {
						ctrlSmoke = 0;
					}

					if (ctrlSmoke == 1) {
						btsmk.setBackground(Color.RED);
					} else {
						btsmk.setBackground(Color.GREEN);
					}
					if (ctrlith == 1) {
						btith.setBackground(Color.RED);
					} else {
						btith.setBackground(Color.GREEN);
					}
				}
				// 总状态
				// ctrlAll = Detection.status + ctrlSmoke + ctrlith;
				ctrlAll = Detection.status + ctrlith;
				if (ctrlAll == 0 && ctrlSmoke == 0) {
					btStatus.setText("Security");
					btStatus.setBackground(Color.GREEN);
				}
				if (ctrlAll == 1 && ctrlSmoke == 0) {
					btStatus.setText("Warinig");
					btStatus.setBackground(Color.YELLOW);
				}
				if (ctrlAll == 2 || ctrlSmoke == 1) {
					btStatus.setText("Dangerous");
					btStatus.setBackground(Color.RED);
				}
			}
			capture.release();
			frame.dispose();
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public int getCtrlSmoke() {
		return ctrlSmoke;
	}

	public void setCtrlSmoke(int ctrlSmoke) {
		this.ctrlSmoke = ctrlSmoke;
	}

	public int getCtrlith() {
		return ctrlith;
	}

	public void setCtrlith(int ctrlith) {
		this.ctrlith = ctrlith;
	}

	public boolean getStatus() {
		return ctrlAll == 2 || ctrlSmoke == 1;
	}
}
