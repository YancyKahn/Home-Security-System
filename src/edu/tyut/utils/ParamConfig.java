package edu.tyut.utils;

public class ParamConfig {
	private String serialNumber;// ���ں�
	private int baudRate; // ������
	private int checkoutBit; // У��λ
	private int dataBit; // ����λ
	private int stopBit; // ֹͣλ

	public ParamConfig() {
	}

	/**
	 * ���췽��
	 * 
	 * @param serialNumber
	 *            ���ں�
	 * @param baudRate
	 *            ������
	 * @param checkoutBit
	 *            У��λ
	 * @param dataBit
	 *            ����λ
	 * @param stopBit
	 *            ֹͣλ
	 */
	public ParamConfig(String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit) {
		this.serialNumber = serialNumber;
		this.baudRate = baudRate;
		this.checkoutBit = checkoutBit;
		this.dataBit = dataBit;
		this.stopBit = stopBit;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}

	public int getCheckoutBit() {
		return checkoutBit;
	}

	public void setCheckoutBit(int checkoutBit) {
		this.checkoutBit = checkoutBit;
	}

	public int getDataBit() {
		return dataBit;
	}

	public void setDataBit(int dataBit) {
		this.dataBit = dataBit;
	}

	public int getStopBit() {
		return stopBit;
	}

	public void setStopBit(int stopBit) {
		this.stopBit = stopBit;
	}

}
