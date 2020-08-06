/**
 * @Title: VMouseListener.java
 * @Package vEngine.controller
 * @Description: 通用鼠标监听器
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.controller;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

import java.nio.DoubleBuffer;

import org.lwjglx.BufferUtils;

import vEngine.display.VDisplay;
import vEngine.global.Global;
import vEngine.ui.VMouseActionUI;
import vEngine.ui.VUI;

/**
 * @author Demilichzz
 *
 */
public class VMouseListener extends VInputListener {
	public static final int MOUSE_UP = 0; // 0→0
	public static final int MOUSE_PRESS = 1; // 0→1
	public static final int MOUSE_DOWN = 3; // 1→1
	public static final int MOUSE_RELEASE = 2; // 1→0

	protected DoubleBuffer x, y;
	protected boolean mouseListener = false;
	protected int[] mouseState;

	protected VUI activeui; // 当前激活的ui，包括拖拽状态等响应

	public VMouseListener(boolean b) {
		super();
		setMouseListener(b);
		mouseState = new int[8];
	}

	public void setMouseListener(boolean b) {
		mouseListener = b;
		if (b) {
			x = BufferUtils.createDoubleBuffer(1);
			y = BufferUtils.createDoubleBuffer(1);
		}
	}

	public int getX() {
		return (int) x.get(0);
	}

	public int getY() {
		return (int) y.get(0);
	}

	public void listen() {
		if (mouseListener) {
			glfwGetCursorPos(VDisplay.getInstance().getWindow(), x, y);
			for (int i = 0; i < 8; i++) {
				mouseState[i] = (mouseState[i] % 2) * 2 + glfwGetMouseButton(VDisplay.getInstance().getWindow(), i);
				// 通过mouseState的前次值和当前值计算鼠标按键状态
			}
		}

		if (mouseState[GLFW_MOUSE_BUTTON_LEFT] == MOUSE_PRESS || mouseState[GLFW_MOUSE_BUTTON_LEFT] == MOUSE_RELEASE) {
			// System.out.println("Mouse:" + mouseState[GLFW_MOUSE_BUTTON_LEFT] + "X:" +
			// x.get(0) + " Y:" + y.get(0));
		} else {

		}

		switch(mouseState[GLFW_MOUSE_BUTTON_LEFT])
		{
		case MOUSE_PRESS:
		{
			mouseActionLeftPress();
			break;
		}
		case MOUSE_DOWN:
		{
			mouseActionLeftDown();
			break;
		}
		case MOUSE_RELEASE:
		{
			mouseActionLeftRelease();
			break;
		}
		}
	}

	/**
	 * @param vMouseActionUI
	 */
	public void setActiveUI(VUI ui) {
		// TODO Auto-generated method stub
		this.activeui = ui;
	}
	public void mouseActionLeftPress()
	{
		VUI currentui = Global.getUIparent().getUIbyLoc(getX(), getY()); // 获取鼠标按下位置的最上层UI
		if (currentui != null) {
			if (activeui == null) {
				setActiveUI(currentui);
			}
			if (activeui instanceof VMouseActionUI) {
				((VMouseActionUI) activeui).uiDragPress(getX(), getY()); // 激活UI的拖拽状态,同时将控制器中的当前拖拽ui设为此ui
			} else {
				currentui.action("Mouse_Press");
			}
		}
	}
	public void mouseActionLeftDown()
	{
		if (activeui != null && activeui instanceof VMouseActionUI) {
			((VMouseActionUI) activeui).uiDragMoveTo(getX(), getY());
		}
	}
	public void mouseActionLeftRelease()
	{
		VUI currentui = Global.getUIparent().getUIbyLoc(getX(), getY()); // 获取鼠标按下位置的最上层UI
		if (activeui != null && activeui instanceof VMouseActionUI) {
			((VMouseActionUI) activeui).uiDragRelease(getX(), getY());
		} 
		else if (currentui == activeui) {
			currentui.action("Mouse_Release");
		}
		activeui = null;
	}
}
