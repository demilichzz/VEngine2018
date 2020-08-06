/**
 * @Title: JsonIO.java
 * @Package vEngine.io
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-8-6
 * @version V1.0
 */
package vEngine.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

/**
 * @author Demilichzz
 *
 */
public class JsonIO {

	/**
	 * @param propertie
	 * @return
	 */
	public static HashMap<String, String> loadJson(String path) {
		// TODO Auto-generated method stub
		HashMap<String, String> namedic = new HashMap<String, String>();
		try {
			String input = loadFileToString(path);
			JSONObject json = new JSONObject(input);
			Iterator iterator = json.keys();
			String key, value;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				value = json.getString(key);
				namedic.put(key, value);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return namedic;
	}

	public static String loadFileToString(String path) throws Exception {
		File file = new File(path);// 定义一个file对象，用来初始化FileReader
		InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GB2312");
		BufferedReader bReader = new BufferedReader(isr);// new一个BufferedReader对象，将文件内容读取到缓存
		StringBuilder sb = new StringBuilder();// 定义一个字符串缓存，将字符串存放缓存中
		String s = "";
		while ((s = bReader.readLine()) != null) {// 逐行读取文件内容，不读取换行符和末尾的空格
			sb.append(s + "\n");// 将读取的字符串添加换行符后累加存放在缓存中
			// System.out.println(s);
		}
		bReader.close();
		String str = sb.toString();
		return str;
	}
}
