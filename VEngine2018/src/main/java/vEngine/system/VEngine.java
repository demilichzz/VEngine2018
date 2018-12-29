package vEngine.system;

import vEngine.system.driver.Simulator;

/**
 * @author Demilichzz
 * 
 *         2013-6-8
 */

public class VEngine {
    public static VEngine newgame;

    public VEngine() {
        //Init();
    }

    public static void main(String[] args) {
        //new VEngine().start();
        newgame = new VEngine();
        //GlobalEvent.initEvent();
        newgame.gameloop();
    }

    public void Init() {
        // TODO 初始化游戏状态和仿真器以运行游戏状态更新与渲染逻辑 
        //s = new Simulator(this);
        //editframe = new JEditPanelFrame();
        //mcontroller = new VMouseController();
        //kcontroller = new VKeyController();
        //renderer = new VDisplay(this);
        //gs.Init();
    }

    private void gameloop() {
        // TODO Auto-generated method stub
        new Thread(Simulator.getInstance()).start();
    }

    public void initResource() {
        //Imageconst.Init();
        //Fontconst.Init();
        //CharacterConst.Init();
    }
}
