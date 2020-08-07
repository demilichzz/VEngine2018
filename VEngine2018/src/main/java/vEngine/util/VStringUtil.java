/**
 * @Title: VStringUtil.java
 * @Package vEngine.util
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-8
 * @version V1.0
 */
package vEngine.util;

/**
 * @author Demilichzz
 *
 */
public class VStringUtil {
	
	
	/**
	 * check字符串是否为纯数字
	 * @param str	字符串
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
