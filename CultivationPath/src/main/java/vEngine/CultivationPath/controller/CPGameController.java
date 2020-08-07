/**
 * @Title: CPGameController.java
 * @Package vEngine.CultivationPath.controller
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-6
 * @version V1.0
 */
package vEngine.CultivationPath.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import vEngine.action.VActionInterface;
import vEngine.controller.VGameController;
import vEngine.controller.VInputListener;
import vEngine.controller.VMouseListener;
import vEngine.global.VPropertiesLoader;
import vEngine.system.GameState;
import vEngine.util.VOrderedProperties;

/**
 * @author Demilichzz
 *
 */
public class CPGameController extends VGameController {
	protected CPGameController() {
		super();
	}

	protected static volatile VGameController instance = new CPGameController();

	public static VGameController getInstance() {
		if (instance == null) {
			synchronized (VGameController.class) {
				if (instance == null) {
					instance = new CPGameController();
				}
			}
		}
		return instance;
	}

	@Override
	public void init() {
		initKeyboardController();
	}

	/**
	 * 加载键盘设定的user_settings.properties初始化键盘监听器
	 */
	public void initKeyboardController() {
		// TODO Auto-generated method stub
		VOrderedProperties user_setting = VPropertiesLoader.getProperty("Control");
		Iterator iterator = user_setting.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			int value = Integer.parseInt(user_setting.getProperty(key));
			System.out.println(key + "=" + value);
			VInputListener vil = new VInputListener(key, value);
		}
		// ----------定义键盘监听器action----------
		VInputListener vil = GameState.getInstance().getGameController().getListener("KEY_UP");
		vil.addAction(new VActionInterface() {
			@Override
			public void action(String args) {
				// TODO Auto-generated method stub
				switch (args) {
				case "KEY_PRESS":
					System.out.println(vil.getKey());
					break;
				}
			}
		});
	}
}
