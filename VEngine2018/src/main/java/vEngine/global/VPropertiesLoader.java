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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import vEngine.util.VOrderedProperties;

/**
 * @author Demilichzz
 *
 */
public class VPropertiesLoader {
	protected static Properties global_setting;
	protected static Properties user_setting;
	protected static String path_global = "../VEngine-resources/config.properties";
	protected static String path_user = "../VEngine-resources/user.properties";
		
	/**
	 * 
	 */
	public static void init() {
		// TODO Auto-generated method stub
        global_setting = new VOrderedProperties();
        FileInputStream fis = null;
        try {
        	fis =  new FileInputStream(path_global);
			global_setting.load(new FileInputStream(path_global));
			if(fis!=null)
        	{
        		fis.close();
        	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			global_setting = createDefaultGlobalProperties();
			savePropertiesToFile(global_setting,path_global);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        user_setting = new VOrderedProperties();
        try {
        	fis =  new FileInputStream(path_user);
        	user_setting.load(new FileInputStream(path_user));
			if(fis!=null)
        	{
        		fis.close();
        	}
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
        	user_setting = createDefaultUserProperties();
			savePropertiesToFile(user_setting,path_user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadProperties(String path)
	{
		//FileOutputStream fos = new FileOutputStream(path);
	}
	
	public static void savePropertiesToFile(Properties pps,String path)
	{
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			pps.store(fos, "Global Setting");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 创建默认properties
	 * @return properties
	 */
	public static Properties createDefaultGlobalProperties()
	{
		Properties pps = new VOrderedProperties();
		pps.setProperty("xml_path", "../VEngine-resources/Xml/ui.xml");
		pps.setProperty("image_preload_path", "../VEngine-resources/Pic/Preload");
		pps.setProperty("namedic_json_path","../VEngine-resources/Config/namedic.json");
		return pps;
	}
	
	public static Properties createDefaultUserProperties()
	{
		Properties pps = new VOrderedProperties();
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
	
	public static String getGlobalProperty(String key)
	{
		String value = "";
		value = global_setting.getProperty(key);
		return value;
	}
	
	public static String getUserProperty(String key)
	{
		String value = "";
		value = user_setting.getProperty(key);
		return value;
	}
}
