package edu.tyut.utils;

public class Debug {
	public static void main(String[] args) throws Exception {
		// ʵ�������ڲ��������
		SerialPortUtils serialPort = new SerialPortUtils();
		// �������ڱ�Ҫ���������ಢ��ֵ����ֵ���ںţ������ʣ�У��λ������λ��ֹͣλ
		ParamConfig paramConfig = new ParamConfig("COM5", 115200, 0, 8, 1);
		// ��ʼ������,�򿪴��ڣ���ʼ������ȡ��������
		serialPort.init(paramConfig);

		// Thread thread2 = new Thread(new Runnable() {
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// try {
		// serialPort.sendComm("");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// });
		// thread1.start();
		// thread2.start();
		//
		// serialPort.sendComm("CCEENO09DD0c000000000000000000FF");
		serialPort.readComm();
		// ���ô��ڲ������sendComm�����������ݵ�����
		// serialPort.sendComm("CCEENO0A0109000000000000000000FF");
		serialPort.sendComm("CCEENO090C00000000000000000000FF");
		// serialPort.sendComm("CCEENO090500000000000000000000FF");

		// �رմ���
		serialPort.closeSerialPort();
	}
}
