package edu.tyut.utils;

public class TypeTrans {
	public static int base16 = 16;
	public static int base10 = 10;

	/**
	 * HexToDec
	 * 
	 * @param hex
	 * @return dec
	 */
	public static int HexToDec(String hex) {
		int dec = 0;
		int t = 1;
		for (int i = hex.length() - 1; i >= 0; i--) {
			if (hex.charAt(i) <= '9' && hex.charAt(i) >= '0') {
				dec += (hex.charAt(i) - '0') * t;
			} else {
				dec += (hex.charAt(i) - 'A' + 10) * t;
			}
			t *= base16;
		}
		return dec;
	}

	/**
	 * 温度转化
	 * 
	 * @param hex
	 * @return 温度
	 */
	public static double tempaerature(String hex) {
		int t = HexToDec(hex.substring(10, 14));
		return t * 1.0 / 100;

	}

	/**
	 * 湿度转化
	 * 
	 * @param hex
	 * @return 湿度
	 */
	public static double humidity(String hex) {
		int h = HexToDec(hex.substring(14, 18));
		return h * 1.0 / 100;
	}

	public static Boolean isITH(String hex) {
		return hex.substring(11, 12).compareTo("1") == 0;
	}

	public static Boolean isSmoke(String hex) {
		return hex.substring(11, 12).compareTo("1") == 0;
	}

	/**
	 * Hex字符串转byte
	 * 
	 * @param inHex
	 *            待转换的Hex字符串
	 * @return 转换后的byte
	 */
	public static byte hexToByte(String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static byte[] hexToByteArray(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * 数组转换成十六进制字符串
	 * 
	 * @param byte[]
	 * @return HexString
	 */
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

}
