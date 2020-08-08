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
import java.util.LinkedHashMap;

import vEngine.io.JsonIO;
import vEngine.system.GameState;
import vEngine.util.VStringUtil;

/**
 * @author Demilichzz
 *
 */
public class NameDic {
	protected static HashMap<String,String> namedic;
	protected static HashMap<String,String> paramList;
	
	public static void init()
	{
		namedic = JsonIO.loadJson(VPropertiesLoader.getProperty("Global","namedic_json_path"));
		paramList = new HashMap<String,String>();
	}
	
	public static void addParamList(String[] params)
	{
		for(int i=0;i<params.length;i++)
		{
			paramList.put(params[i],params[i]);
		}
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
	
	public static String getNamedicValue(String key,String[]args)
	{
		String text = getNamedicValue(key);
		LinkedHashMap<String,String> replaceArray = new LinkedHashMap<String,String>();
		for(int i=0;i<text.length()-1;i++)
		{
			String start = text.substring(i,i+1);
			if(start.equals("#"))
			{
				String end;
				int j;
				for(j=i+1;j<text.length();j++) {
					end = text.substring(j,j+1);
					if(end.equals("#"))
					{
						break;
					}
				}
				String tempString = text.substring(i+1,j);	//获取判断是否需要替换的参数字符串
				if(paramList.get(tempString)!=null)
				{
					String replaceString = GameState.getInstance().getGameData().getNameParam(tempString);
					if(replaceString==null)
					{
						replaceString="DataNotFound:"+tempString;
					}
					replaceArray.put("#"+tempString+"#", replaceString);	//将参数字符串替换为获取的替换字符串
				}
				else if(VStringUtil.isNumeric(tempString)) {	//如果是纯数字
					int index = Integer.parseInt(tempString);
					replaceArray.put("#"+tempString+"#",""+args[index-1]);	//将参数字符串替换为参数数组值
				}
				else
				{
					i=j-1;
				}
			}
		}
		for(String k:replaceArray.keySet())
		{
			String v = replaceArray.get(k);
			text=text.replace(k, v);
		}
		return text;
	}
}
