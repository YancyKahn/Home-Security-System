package edu.tyut.run;

import edu.tyut.UI.Window;
import edu.tyut.utils.ParamConfig;
import edu.tyut.utils.SerialPortUtils;

public class SyncRun {
	static String dataHe;

	/**
	 * 三个线程, 解决UI和串口数据的逻辑冲突,
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		// 创建UI
		Window window = new Window("Home Security System");
		SerialPortUtils serialPort = new SerialPortUtils();
		// 创建串口必要参数接收类并赋值，赋值串口号，波特率，校验位，数据位，停止位
		ParamConfig paramConfig = new ParamConfig("COM5", 115200, 0, 8, 1);

		// 初始化设置,打开串口，开始监听读取串口数据
		serialPort.init(paramConfig);
		// 读数据
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					serialPort.readComm();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// UI
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					window.creatFream();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		// 每一秒钟发送一次数据
		Thread thread3 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					String string = serialPort.getDataHex();
					// System.out.println("getdatahex:" + serialPort.getDataHex());
					window.setValue(string);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		});

		// dangerous 时打开灯光
		Thread thread4 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					System.out.println("status = " + window.getStatus());
					// 检测到danger
					if (window.getStatus()) {
						try {
							serialPort.sendComm("CCEENO090C00000000000000000000FF");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else { // 未检测到
						try {
							serialPort.sendComm("CCEENO090D00000000000000000000FF");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		thread1.start(); // 数据读取
		thread3.start(); // 窗口
		thread2.start(); // 将数据发送到窗口
		thread4.start(); // 将数据发送到串口

	}
}
