/**
 * @Title: CPMain.java
 * @Package vEngine.CultivationPath.system
 * @Description: vEngine.system.VEngine子类，在游戏项目中重载以定义初始化处理
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.CultivationPath.system;

import static org.lwjgl.glfw.GLFW.*;

import vEngine.CultivationPath.controller.CPGameController;
import vEngine.action.VActionInterface;
import vEngine.controller.VGameController;
import vEngine.controller.VInputListener;
import vEngine.global.NameDic;
import vEngine.system.EntityManager;
import vEngine.system.GameState;
import vEngine.system.VEngine;

/**
 * @author Demilichzz
 *
 */
public class CPMain extends VEngine {

	public CPMain() {
		// Init();
	}

	protected static volatile VEngine instance = new CPMain();

	public static VEngine getInstance() {
		if (instance == null) {
			// synchronized (Simulator.class) {
			// if (instance == null) {
			instance = new CPMain();
			// }
			// }
		}
		return instance;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getInstance().gameloop();
	}

	@Override
	public void initResource() {
		super.initResource();
		// initController();
		// VDisplay.getInstance().renderPrepare();
		// GameState.getInstance().Render();
		NameDic.addParamList(new String[] {"Charname","Classtype"});
		//System.out.println(NameDic.getNamedicValue("dialog_param_test", new String[]{"100","200"}));
	}

	@Override
	public EntityManager getEntityManager()
	{
		return EntityManagerCP.getInstance();
	}
	
	@Override
	public VGameController getGameController()
	{
		return CPGameController.getInstance();
	}
}
