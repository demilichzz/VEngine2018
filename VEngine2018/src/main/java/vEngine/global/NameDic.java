/**
 * @Title: NameDic.java
 * @Package vEngine.global
 * @Description: 文本词典，用于管理游戏中出现的文本内容
 * @author Demilichzz
 * @date 2020-8-6
 * @version V1.0
 */
package vEngine.global;

import java.util.HashMap;

import vEngine.io.JsonIO;

/**
 * @author Demilichzz
 *
 */
public class NameDic {
	protected static HashMap<String,String> namedic;
	
	public static void init()
	{
		namedic = JsonIO.loadJson(VPropertiesLoader.getPropertie("namedic_json_path"));
	}
	
	public static String getNamedicValue(String key)
	{
		if(namedic.get(key)!=null)
		{
			return namedic.get(key);
		}
		else
		{
			return "NamedicKey:"+key;
		}
	}
}
