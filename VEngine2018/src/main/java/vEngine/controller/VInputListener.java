/**
 * @Title: VInputListener.java
 * @Package vEngine.controller
 * @Description: 基本输入监听器
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.controller;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import org.lwjglx.BufferUtils;

import lombok.Getter;
import vEngine.action.VActionInterface;
import vEngine.display.VDisplay;
import vEngine.system.GameState;

/**
 * @author Demilichzz
 *
 */
public class VInputListener {
	protected int bindKey=-1;
	protected int[] bindMouseList;
	protected VActionInterface action;
	@Getter
	protected String key;
	protected int keyState=KEY_UP;
	public static final int KEY_UP = 0; // 0→0
	public static final int KEY_PRESS = 1; // 0→1
	public static final int KEY_DOWN = 3; // 1→1
	public static final int KEY_RELEASE = 2; // 1→0

	public VInputListener() {
		//bindKeyList = new HashMap<Integer,Integer>();
	}

	public VInputListener(int key) {
		this(""+key,key);
	}	
	
	/**
	 * 以按键编码及按键索引初始化按键监听器
	 * @param keyCode GLFW按键编码
	 * @param key 按键索引
	 */
	public VInputListener(String key,int keyCode)
	{
		this();
		this.key = key;
		addBindKey(keyCode);
		GameState.getInstance().getGameController().addListener(key, this);
	}

	public void addBindKey(int key) {
		bindKey=key;
	}
	public void removeBindKey(int key)
	{
		bindKey=-1;
	}

	public void addAction(VActionInterface action) {
		this.action = action;
	}

	public void listen() {
		int current_mouseState = glfwGetKey(VDisplay.getInstance().getWindow(), bindKey);
		keyState = (keyState % 2) * 2 + current_mouseState;
		if (keyState != KEY_UP) {
			GameState.getInstance().getEntityManager().command(key, keyState);
		}
	}
}
