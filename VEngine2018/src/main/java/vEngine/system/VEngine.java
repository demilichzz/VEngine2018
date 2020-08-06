package vEngine.system;

import vEngine.controller.VGameController;
import vEngine.display.VDisplay;
import vEngine.global.Fontconst;
import vEngine.global.Imageconst;
import vEngine.global.VPropertiesLoader;
import vEngine.system.driver.Simulator;

/**
 * @author Demilichzz
 * 
 *         2013-6-8
 */

public class VEngine {
	public static VEngine newgame;

	public VEngine() {
		// Init();
	}

	private static volatile VEngine instance = new VEngine();

	public static VEngine getInstance() {
		if (instance == null) {
			// synchronized (Simulator.class) {
			// if (instance == null) {
			instance = new VEngine();
			// }
			// }
		}
		return instance;
	}

	public static void main(String[] args) {
		// new VEngine().start();
		// GlobalEvent.initEvent();
		getInstance().gameloop();
	}

	public void gameloop() {
		// TODO Auto-generated method stub
		// getInstance().initResource();
		new Thread(Simulator.getInstance(this)).start();
	}

	public void initResource() {
		VPropertiesLoader.init();
		Fontconst.Init();
		VDisplay.getInstance().initGL(); // 初始化OpenGL参数
		Imageconst.Init(); // 初始化资源，字体
		GameState.getInstance().Init();
		// VDisplay.getInstance().renderPrepare();
		GameState.getInstance().Render();
		GameState.getInstance().setGameController(VGameController.getInstance());
	}
}
