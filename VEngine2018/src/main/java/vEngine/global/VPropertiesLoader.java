/**
 * @Title: VPropertiesLoader.java
 * @Package vEngine.io
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-5
 * @version V1.0
 */
package vEngine.global;

import static org.lwjgl.glfw.GLFW.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Properties;

import vEngine.util.VOrderedProperties;

/**
 * @author Demilichzz
 *
 */
public class VPropertiesLoader {
	protected static String path = "../VEngine-resources/";
	protected static ArrayList<String> propertiesTypeList;
	protected static LinkedHashMap<String,VOrderedProperties> properties = new LinkedHashMap<String,VOrderedProperties>();
	
	/**
	 * 
	 */
	public static void init() {
		// TODO Auto-generated method stub
		propertiesTypeList = new ArrayList<String>();
		propertiesTypeList.add("Global");
		propertiesTypeList.add("Control");
		propertiesTypeList.add("Debug");
        for(String type:propertiesTypeList)
        {
        	properties.put(type,loadProperties(type));
        }
	}
	
	public static VOrderedProperties loadProperties(String type)
	{
		VOrderedProperties vop = new VOrderedProperties();
		FileInputStream fis = null;
		String fullpath = path+type+".properties";
        try {
        	fis =  new FileInputStream(fullpath);
        	vop.load(new FileInputStream(fullpath));
			if(fis!=null)
        	{
        		fis.close();
        	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			vop = createDefaultProperties(type);
			savePropertiesToFile(vop,fullpath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return vop;
	}
	
	public static void savePropertiesToFile(Properties pps,String path)
	{
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			pps.store(fos, null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * 根据类别创建默认properties
	 * @param type
	 * @return
	 */
	public static VOrderedProperties createDefaultProperties(String type)
	{
		switch(type)
		{
		case "Global":
			return createDefaultGlobalProperties();
		case "Control":
			return createDefaultControlProperties();
		case "Debug":
			return createDebugControlProperties();
		default:
			return null;
		}
	}
	
	/**
	 * 创建默认properties
	 * @return properties
	 */
	public static VOrderedProperties createDefaultGlobalProperties()
	{
		VOrderedProperties pps = new VOrderedProperties();
		pps.setProperty("xml_path", "../VEngine-resources/Xml/ui.xml");
		pps.setProperty("image_preload_path", "../VEngine-resources/Pic/Preload");
		pps.setProperty("namedic_json_path","../VEngine-resources/Config/namedic.json");
		return pps;
	}
	
	public static VOrderedProperties createDefaultControlProperties()
	{
		VOrderedProperties pps = new VOrderedProperties();
		pps.setProperty("KEY_UP", ""+GLFW_KEY_UP);
		pps.setProperty("KEY_DOWN", ""+GLFW_KEY_DOWN);
		pps.setProperty("KEY_LEFT", ""+GLFW_KEY_LEFT);
		pps.setProperty("KEY_RIGHT", ""+GLFW_KEY_RIGHT);
		pps.setProperty("KEY_SKILL1", ""+GLFW_KEY_Q);
		pps.setProperty("KEY_SKILL2", ""+GLFW_KEY_W);
		pps.setProperty("KEY_SKILL3", ""+GLFW_KEY_E);
		pps.setProperty("KEY_SKILL4", ""+GLFW_KEY_R);
		pps.setProperty("KEY_SKILL5", ""+GLFW_KEY_A);
		pps.setProperty("KEY_SKILL6", ""+GLFW_KEY_S);
		pps.setProperty("KEY_SKILL7", ""+GLFW_KEY_D);
		pps.setProperty("KEY_SKILL8", ""+GLFW_KEY_F);
		pps.setProperty("KEY_CANCEL", ""+GLFW_KEY_ESCAPE);
		pps.setProperty("KEY_CONFIRM", ""+GLFW_KEY_ENTER);
		pps.setProperty("KEY_STAT", ""+GLFW_KEY_C);
		pps.setProperty("KEY_INVENTORY", ""+GLFW_KEY_I);
		pps.setProperty("KEY_SKILL", ""+GLFW_KEY_Y);
		pps.setProperty("KEY_MAP", ""+GLFW_KEY_TAB);
		return pps;
	}
	
	public static VOrderedProperties createDebugControlProperties()
	{
		VOrderedProperties pps = new VOrderedProperties();
		pps.setProperty("DEBUG_TOTAL", "0");
		pps.setProperty("DEBUG_KEYBOARD", "0");
		pps.setProperty("DEBUG_MOUSE", "0");
		return pps;
	}
	
	/**
	 * 获取指定属性类别的某个指定参数
	 * @param type	属性类别
	 * @param key	属性参数
	 * @return
	 */
	public static String getProperty(String type,String key)
	{
		String value = "";
		VOrderedProperties vop = properties.get(type);
		if(vop!=null)
		{
			value = vop.getProperty(key);
		}
		return value;
	}
	
	
	/**
	 * 获取指定属性类别对象
	 * @param type	属性类别
	 * @return
	 */
	public static VOrderedProperties getProperty(String type)
	{
		return properties.get(type);
	}
}
