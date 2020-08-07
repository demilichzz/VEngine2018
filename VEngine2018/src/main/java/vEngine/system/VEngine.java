package vEngine.system;

import vEngine.controller.VGameController;
import vEngine.data.VGameData;
import vEngine.display.VDisplay;
import vEngine.global.Fontconst;
import vEngine.global.Imageconst;
import vEngine.global.NameDic;
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

	protected static volatile VEngine instance = new VEngine();

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
		NameDic.init();
		Fontconst.Init();
		VDisplay.getInstance().initGL(); // 初始化OpenGL参数
		Imageconst.Init(); // 初始化资源，字体
		GameState.getInstance().Init();
		// VDisplay.getInstance().renderPrepare();
		GameState.getInstance().setEntityManager(this.getEntityManager());
		this.getEntityManager().init();
		GameState.getInstance().setGameController(this.getGameController());
		this.getGameController().init();
		GameState.getInstance().setGameData(this.getGameData());
		this.getGameData().init();
		GameState.getInstance().Render();
	}
	
	
	/**
	 * 获取更新实体管理器，在子项目中重载以定义子项目使用的更新实体管理器
	 * @return
	 */
	public EntityManager getEntityManager()
	{
		return EntityManager.getInstance();
	}
	
	/**
	 * 获取游戏控制器，在子项目中重载以定义子项目使用的控制器
	 * @return
	 */
	public VGameController getGameController()
	{
		return VGameController.getInstance();
	}
	
	/**
	 * 获取游戏数据对象，在子项目中重载以定义子项目使用的游戏数据对象
	 * @return
	 */
	private VGameData getGameData() {
		// TODO Auto-generated method stub
		return VGameData.getInstance();
	}
}
