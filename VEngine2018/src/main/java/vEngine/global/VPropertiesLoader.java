/**
 * @Title: VPropertiesLoader.java
 * @Package vEngine.io
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-5
 * @version V1.0
 */
package vEngine.global;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Demilichzz
 *
 */
public class VPropertiesLoader {
	protected static Properties global_setting;
	protected static String path = "../VEngine-resources/config.properties";
		
	/**
	 * 
	 */
	public static void init() {
		// TODO Auto-generated method stub
        global_setting = new Properties();
        FileInputStream fis = null;
        try {
        	fis =  new FileInputStream(path);
			global_setting.load(new FileInputStream(path));
			if(fis!=null)
        	{
        		fis.close();
        	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			global_setting = createDefaultProperties();
			savePropertiesToFile(global_setting);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadProperties(Properties pps)
	{
		//FileOutputStream fos = new FileOutputStream(path);
	}
	
	public static void savePropertiesToFile(Properties pps)
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
	public static Properties createDefaultProperties()
	{
		Properties pps = new Properties();
		pps.setProperty("xml_path", "../VEngine-resources/Xml/ui.xml");
		pps.setProperty("image_preload_path", "../VEngine-resources/Pic/Preload");
		return pps;
	}
	
	public static String getPropertie(String key)
	{
		String value = "";
		value = global_setting.getProperty(key);
		return value;
	}
}
