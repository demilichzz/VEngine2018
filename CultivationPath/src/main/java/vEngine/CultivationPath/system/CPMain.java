/**
 * @Title: CPMain.java
 * @Package vEngine.CultivationPath.system
 * @Description: vEngine.system.VEngine子类，在游戏项目中重载以定义初始化处理
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.CultivationPath.system;

import vEngine.controller.VGameController;
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

	private static volatile VEngine instance = new CPMain();

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

	public void initResource() {
		super.initResource();
		EntityManagerCP em = new EntityManagerCP();
		GameState.getInstance().setEntityManager(em);
		em.init();
		
		// VDisplay.getInstance().renderPrepare();
		// GameState.getInstance().Render();
	}

}
