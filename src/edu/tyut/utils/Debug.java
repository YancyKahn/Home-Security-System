package edu.tyut.utils;

public class Debug {
	public static void main(String[] args) throws Exception {
		// 实例化串口操作类对象
		SerialPortUtils serialPort = new SerialPortUtils();
		// 创建串口必要参数接收类并赋值，赋值串口号，波特率，校验位，数据位，停止位
		ParamConfig paramConfig = new ParamConfig("COM5", 115200, 0, 8, 1);
		// 初始化设置,打开串口，开始监听读取串口数据
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
		// 调用串口操作类的sendComm方法发送数据到串口
		// serialPort.sendComm("CCEENO0A0109000000000000000000FF");
		serialPort.sendComm("CCEENO090C00000000000000000000FF");
		// serialPort.sendComm("CCEENO090500000000000000000000FF");

		// 关闭串口
		serialPort.closeSerialPort();
	}
}
