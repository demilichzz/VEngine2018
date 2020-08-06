/**
 * @Title: CPGameController.java
 * @Package vEngine.CultivationPath.controller
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-6
 * @version V1.0
 */
package vEngine.CultivationPath.controller;

import java.util.HashMap;

import vEngine.controller.VGameController;
import vEngine.controller.VInputListener;
import vEngine.controller.VMouseListener;
import vEngine.system.GameState;

/**
 * @author Demilichzz
 *
 */
public class CPGameController extends VGameController{
	protected CPGameController() {
		super();
		System.out.println("CPGameController");
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
}
