/**
 * @Title: VGameController.java
 * @Package vEngine.controller
 * @Description: 获取控制器的状态并映射到控制器命令
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.controller;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.HashMap;

import vEngine.system.GameState;


/**
 * @author Demilichzz
 *
 */
public class VGameController {
	protected HashMap<String,VInputListener> listenerList;

	private VGameController() {
		listenerList = new HashMap<String,VInputListener>();
		VInputListener l = new VMouseListener(true);
		addListener("Mouse",l);
		l = new VInputListener(GLFW_KEY_UP);
		addListener("Key_"+GLFW_KEY_UP, l);
	}
	private static volatile VGameController instance = new VGameController();

    public static VGameController getInstance() {
        if (instance == null) {
            synchronized (GameState.class) {
                if (instance == null) {
                    instance = new VGameController();
                }
            }
        }
        return instance;
    }
	
	public void addListener(String ID,VInputListener listener)
	{
		listenerList.put(ID,listener);
	}
	public VInputListener getListener(String ID)
	{
		return listenerList.get(ID);
	}

	public void controllerUpdate() {
		for(VInputListener l:listenerList.values()) {
			l.listen();
		}
	}
}
