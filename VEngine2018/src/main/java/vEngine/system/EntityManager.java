/**
 * @Title: GSUpdateProcesser.java
 * @Package vEngine.system
 * @Description: GameState更新处理器，（默认10ms）GS更新时调用
 * @author Demilichzz
 * @date 2020-7-29
 * @version V1.0
 */
package vEngine.system;

import java.util.ArrayList;

import vEngine.controller.VGameController;
import vEngine.controller.VInputListener;
import vEngine.entities.VEntity;
import vEngine.global.Global;
import vEngine.ui.VUI;

/**
 * @author Demilichzz
 *
 */
public class EntityManager {
	protected ArrayList<VEntity> layer_background;
	protected ArrayList<VEntity> layer_game_entities;
	protected VUI uiparent;
	
	public EntityManager()
	{
		layer_background = new ArrayList<VEntity>();
		layer_game_entities = new ArrayList<VEntity>();
		VUI uiparent = new VUI(Global.windowX, Global.windowY, "uiparent");
		setUI(uiparent);
	}
	
	protected static volatile EntityManager instance = new EntityManager();

    public static EntityManager getInstance() {
        if (instance == null) {
            synchronized (EntityManager.class) {
                if (instance == null) {
                    instance = new EntityManager();
                }
            }
        }
        return instance;
    }
	
	public void draw()
	{
		if(layer_background!=null)
		{
			for(VEntity e:layer_background)
			{
				e.draw();
			}
		}
		if(layer_game_entities!=null)
		{
			for(VEntity e:layer_game_entities)
			{
				e.draw();
			}
		}
		if(uiparent!=null)
		{
			uiparent.draw();
		}
	}
	
	public void update()
	{
		if(layer_background!=null)
		{
			for(VEntity e:layer_background)
			{
				e.update();
			}
		}
		if(layer_game_entities!=null)
		{
			for(VEntity e:layer_game_entities)
			{
				e.update();
			}
		}
		if(uiparent!=null)
		{
			uiparent.update();
		}
	}
	
	
	/**
	 * 响应键盘及手柄输入监听器的命令
	 * @param commandkey usersetting定义的按键行为关键字
	 * @param commandsts 按键状态
	 */
	public void command(String commandkey,int commandsts)
	{
		switch(commandsts)
		{
		case VInputListener.KEY_PRESS:
			//System.out.println(commandkey+":press");
			break;
		case VInputListener.KEY_RELEASE:
			//System.out.println(commandkey+":release");
			break;
		}
	}
	
	public void setUI(VUI ui)
	{
		uiparent = ui;
	}
	public VUI getUI()
	{
		return uiparent;
	}

	/**
	 * 初始化实体管理器，需要将实体管理器设定至GameState后才能进行初始化UI，因此不能在构造函数内直接调用
	 * 在子项目中重载以初始化实体管理器
	 */
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
