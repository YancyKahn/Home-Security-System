package edu.tyut.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * ���ڲ��������� ����һ�������²��������ڸô��ڴ���ǰ�������ã� �������ںţ������ʣ�����/��������ƣ�����λ����ֹͣλ����żУ�顣
 */
// ע�����ڲ�����һ��Ҫ�̳�SerialPortEventListener
public class SerialPortUtils implements SerialPortEventListener {
	// ���ϵͳ�п��õ�ͨѶ�˿���
	private CommPortIdentifier commPortId;
	// ö������
	private Enumeration<CommPortIdentifier> portList;
	// RS232����
	private SerialPort serialPort;
	// ������
	private InputStream inputStream;
	// �����
	private OutputStream outputStream;
	// ���洮�ڷ�����Ϣ
	private String data;
	// ���洮�ڷ�����Ϣʮ������
	private String dataHex;

	public void setDataHex(String dataHex) {
		this.dataHex = dataHex;
	}

	/**
	 * ��ʼ������ @author LinWenLi @date 2018��7��21������3:44:16 @Description: TODO @param:
	 * paramConfig ��Ŵ������ӱ�Ҫ�����Ķ��󣨻����·���������룩 @return: void @throws
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void init(ParamConfig paramConfig) throws Exception {
		// ��ȡϵͳ�����е�ͨѶ�˿�
		portList = CommPortIdentifier.getPortIdentifiers();
		// ��¼�Ƿ���ָ������
		boolean isExsist = false;
		// ѭ��ͨѶ�˿�
		while (portList.hasMoreElements()) {
			commPortId = portList.nextElement();
			// �ж��Ƿ��Ǵ���
			if (commPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				// �Ƚϴ��������Ƿ���ָ������
				if (paramConfig.getSerialNumber().equals(commPortId.getName())) {
					// ���ڴ���
					isExsist = true;
					// �򿪴���
					try {
						// open:��Ӧ�ó�����������������������ʱ�ȴ��ĺ�������
						serialPort = (SerialPort) commPortId.open(Object.class.getSimpleName(), 2000);
						// ���ô��ڼ���
						serialPort.addEventListener(this);
						// ���ô�������ʱ����Ч(�ɼ���)
						serialPort.notifyOnDataAvailable(true);
						// ���ô���ͨѶ����:�����ʣ�����λ��ֹͣλ,У�鷽ʽ
						serialPort.setSerialPortParams(paramConfig.getBaudRate(), paramConfig.getDataBit(),
								paramConfig.getStopBit(), paramConfig.getCheckoutBit());
					} catch (PortInUseException e) {
						throw new Exception("�˿ڱ�ռ��");
					} catch (TooManyListenersException e) {
						throw new Exception("����������");
					} catch (UnsupportedCommOperationException e) {
						throw new Exception("��֧�ֵ�COMM�˿ڲ����쳣");
					}
					// ����ѭ��
					break;
				}
			}
		}
		// �������ڸô������׳��쳣
		if (!isExsist) {
			try {
				throw new Exception("�����ڸô��ڣ�");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ʵ�ֽӿ�SerialPortEventListener�еķ��� ��ȡ�Ӵ����н��յ�����
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI: // ͨѶ�ж�
		case SerialPortEvent.OE: // ��λ����
		case SerialPortEvent.FE: // ֡����
		case SerialPortEvent.PE: // ��żУ�����
		case SerialPortEvent.CD: // �ز����
		case SerialPortEvent.CTS: // �������
		case SerialPortEvent.DSR: // �����豸׼����
		case SerialPortEvent.RI: // �������
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // ��������������
			break;
		case SerialPortEvent.DATA_AVAILABLE: // �����ݵ���
			// ���ö�ȡ���ݵķ���
			try {
				readComm();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ��ȡ���ڷ�����Ϣ
	 * 
	 * @author LinWenLi
	 * @date 2018��7��21������3:43:04
	 * @return: void
	 *
	 */
	public String readComm() throws Exception {
		try {
			inputStream = serialPort.getInputStream();
			// ͨ�������������available������ȡ�����ֽڳ���
			byte[] readBuffer = new byte[inputStream.available()];
			// ����·�϶�ȡ������
			int len = 0;
			while ((len = inputStream.read(readBuffer)) != -1) {
				// ֱ�ӻ�ȡ��������
				data = new String(readBuffer, 0, len).trim();
				// ת��Ϊʮ������
				dataHex = TypeTrans.bytesToHexString(readBuffer);
				// System.out.println("data:" + data);
				// System.out.println("dataHex:" + dataHex);// ��ȡ���ÿ�������
				// System.out.println("temperature:" + TypeTrans.tempaerature(dataHex));
				// System.out.println("humidity:" + TypeTrans.humidity(dataHex) + "%");
				inputStream.close();
				inputStream = null;
				break;
			}
		} catch (IOException e) {
			throw new Exception("��ȡ��������ʱ����IO�쳣");
		}
		return dataHex;
	}

	/**
	 * ������Ϣ������ @author LinWenLi @date 2018��7��21������3:45:22 @param: data @return:
	 * void @throws
	 * 
	 * 
	 */
	public void sendComm(String data) throws Exception {
		byte[] writerBuffer = null;

		try {
			writerBuffer = TypeTrans.hexToByteArray(data);
			System.out.println(writerBuffer);
		} catch (NumberFormatException e) {
			throw new Exception("�����ʽ����");
		}
		try {
			outputStream = serialPort.getOutputStream();
			outputStream.write(writerBuffer);
			outputStream.flush();
		} catch (NullPointerException e) {
			throw new Exception("�Ҳ������ڡ�");
		} catch (IOException e) {
			throw new Exception("������Ϣ������ʱ����IO�쳣");
		}
	}

	/**
	 * �رմ��� @author LinWenLi @date 2018��7��21������3:45:43 @Description:
	 * �رմ��� @param: @return: void @throws
	 * 
	 * @throws Exception
	 */
	public void closeSerialPort() throws Exception {
		if (serialPort != null) {
			serialPort.notifyOnDataAvailable(false);
			serialPort.removeEventListener();
			if (inputStream != null) {
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					throw new Exception("�ر�������ʱ����IO�쳣");
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
					outputStream = null;
				} catch (IOException e) {
					throw new Exception("�ر������ʱ����IO�쳣");
				}
			}
			serialPort.close();
			serialPort = null;
		}
	}

	/**
	 * ʮ�����ƴ��ڷ���ֵ��ȡ
	 */
	public String getDataHex() {
		String result = dataHex;
		// �ÿ�ִ�н��
		dataHex = null;
		// ����ִ�н��
		return result;
	}

	/**
	 * ���ڷ���ֵ��ȡ
	 */
	public String getData() {
		String result = data;
		// �ÿ�ִ�н��
		data = null;
		// ����ִ�н��
		return result;
	}

}
