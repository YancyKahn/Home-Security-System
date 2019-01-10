package edu.tyut.run;

import edu.tyut.UI.Window;
import edu.tyut.utils.ParamConfig;
import edu.tyut.utils.SerialPortUtils;

public class SyncRun {
	static String dataHe;

	/**
	 * �����߳�, ���UI�ʹ������ݵ��߼���ͻ,
	 * 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		// ����UI
		Window window = new Window("Home Security System");
		SerialPortUtils serialPort = new SerialPortUtils();
		// �������ڱ�Ҫ���������ಢ��ֵ����ֵ���ںţ������ʣ�У��λ������λ��ֹͣλ
		ParamConfig paramConfig = new ParamConfig("COM5", 115200, 0, 8, 1);

		// ��ʼ������,�򿪴��ڣ���ʼ������ȡ��������
		serialPort.init(paramConfig);
		// ������
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
		// ÿһ���ӷ���һ������
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

		// dangerous ʱ�򿪵ƹ�
		Thread thread4 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					System.out.println("status = " + window.getStatus());
					// ��⵽danger
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

					} else { // δ��⵽
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
		thread1.start(); // ���ݶ�ȡ
		thread3.start(); // ����
		thread2.start(); // �����ݷ��͵�����
		thread4.start(); // �����ݷ��͵�����

	}
}
