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
	
	public void init()
	{
		VXMLData ui_xmlData = null;
		try {
			ui_xmlData = XMLIO.loadXML(VPropertiesLoader.getPropertie("xml_path"));
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
	}
	
	public void draw() {
		super.draw();
	}
}