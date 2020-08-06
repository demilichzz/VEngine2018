/**
 * @Title: VInputListener.java
 * @Package vEngine.controller
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.controller;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

import org.lwjglx.BufferUtils;

import vEngine.display.VDisplay;
import vEngine.interfaces.VActionInterface;

/**
 * @author Demilichzz
 *
 */
public class VInputListener {
	protected ArrayList<Integer> bindKeyList;
	protected int[] bindMouseList;
	protected VActionInterface action;

	public VInputListener() {
		bindKeyList = new ArrayList<Integer>();
	}

	public VInputListener(int key) {
		this();
		addBindKey(key);	//默认绑定按键
		VGameController.getInstance().addListener("Key_"+key, this);	//自动将初始化的按键监听器加入到监听器列表
	}

	public void addBindKey(int key) {
		bindKeyList.add(key);
	}

	public void addAction(VActionInterface action) {
		this.action = action;
	}

	public void listen() {
		if (action != null) {
			for (Integer key : bindKeyList) {
				if (glfwGetKey(VDisplay.getInstance().getWindow(), key) == GLFW_PRESS) {
					action.action("input_key_press");
				}
			}
		}
	}
}
