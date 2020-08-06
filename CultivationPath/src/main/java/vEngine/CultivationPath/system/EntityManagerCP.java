/**
 * @Title: EntityManagerCP.java
 * @Package vEngine.CultivationPath.system
 * @Description: vEngine.system.EntityManager的子类，在游戏项目中重载以定义游戏内实体管理器的处理
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.CultivationPath.system;

import vEngine.display.VText;
import vEngine.global.Global;
import vEngine.global.VPropertiesLoader;
import vEngine.interfaces.VActionInterface;
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
public class EntityManagerCP extends EntityManager{
	
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
			ui_xmlData = XMLIO.loadXML(VPropertiesLoader.getGlobalProperty("xml_path"));
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
	}
	
	public void draw() {
		super.draw();
	}
	public void update() {
		super.update();
	}
}
