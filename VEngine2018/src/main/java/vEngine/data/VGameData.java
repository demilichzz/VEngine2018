/**
 * @Title: VGameData.java
 * @Package vEngine.data
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-8
 * @version V1.0
 */
package vEngine.data;

/**
 * @author Demilichzz
 *
 */
public class VGameData {

	protected VGameData() {
	}

	public void init() {
		
	}
	
	protected static volatile VGameData instance = new VGameData();

	public static VGameData getInstance() {
		if (instance == null) {
			synchronized (VGameData.class) {
				if (instance == null) {
					instance = new VGameData();
				}
			}
		}
		return instance;
	}

	public String getNameParam(String param) {
		String value = null;
		if (param.equals("Charname")) {
			value = "测试用角色名";
		}
		return value;
	}
}
