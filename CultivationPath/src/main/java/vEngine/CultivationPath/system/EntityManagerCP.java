/**
 * @Title: EntityManagerCP.java
 * @Package vEngine.CultivationPath.system
 * @Description: vEngine.system.EntityManager的子类，在游戏项目中重载以定义游戏内实体管理器的处理
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.CultivationPath.system;

import lombok.Data;
import vEngine.CultivationPath.entities.CPMap;
import vEngine.action.VActionInterface;
import vEngine.controller.VInputListener;
import vEngine.display.VText;
import vEngine.global.Global;
import vEngine.global.VPropertiesLoader;
import vEngine.interfaces.VXMLDataAdapter;
import vEngine.io.VXMLData;
import vEngine.io.XMLIO;
import vEngine.system.EntityManager;
import vEngine.ui.VMouseActionUI;
import vEngine.ui.VUI;
import vEngine.ui.VUIFactory;

/**
 * @author Demilichzz
 *
 */
@Data
public class EntityManagerCP extends EntityManager{
	protected CPMap map;
		
	public EntityManagerCP()
	{
		super();	//设定默认uiparent
	}
	
	protected static volatile EntityManager instance = new EntityManagerCP();

    public static EntityManager getInstance() {
        if (instance == null) {
            synchronized (EntityManagerCP.class) {
                if (instance == null) {
                    instance = new EntityManagerCP();
                }
            }
        }
        return instance;
    }
	
    @Override
	public void init()
	{
		VXMLData ui_xmlData = null;
		try {
			ui_xmlData = XMLIO.loadXML(VPropertiesLoader.getProperty("Global","xml_path"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		VUIFactory ui_factory = new VUIFactory();
		for(VXMLData ui:ui_xmlData.getChildlist())
		{
			ui_factory.setParams(new VXMLDataAdapter(ui));
			ui_factory.creator("VMouseActionUI");
		}
		VUI ui = Global.getUIByID("ui_test");
		ui.addAction(new VActionInterface() {
			@Override
			public void action(String args) {
				// TODO Auto-generated method stub
				if(args.equals("Mouse_Release"))
				{
					System.out.println(ui.toString());
				}	
			}
		});
		
		map = new CPMap("MapTile_test.png",3200,3200);
		this.layer_background.add(map);
	}
    
	/**
	 * 响应键盘及手柄输入监听器的命令
	 * @param commandkey usersetting定义的按键行为关键字
	 * @param commandsts 按键状态
	 */
    @Override
	public void command(String commandkey,int commandsts)
	{
		super.command(commandkey, commandsts);
		if(commandsts==VInputListener.KEY_PRESS||commandsts==VInputListener.KEY_DOWN) {
			switch(commandkey) {
			case "KEY_UP":
				getMap().mapMove(0, -1, 475);
				break;
			case "KEY_DOWN":
				getMap().mapMove(0, 1, 475);
				break;
			case "KEY_LEFT":
				getMap().mapMove(-1, 0, 475);
				break;
			case "KEY_RIGHT":
				getMap().mapMove(1, 0, 475);
				break;
			}
		}
	}
	
	public void draw() {
		super.draw();
	}
	public void update() {
		super.update();
	}
}
