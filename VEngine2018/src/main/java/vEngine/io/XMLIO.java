/**
 * @Title: XMLIO.java
 * @Package vEngine.io
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-7-31
 * @version V1.0
 */
package vEngine.io;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Demilichzz
 *
 */
public class XMLIO {
	public static void main(String[] args) {
		try {
			loadXML("../VEngine-resources/Xml/ui.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static VXMLData loadXML(String path) throws Exception {
		// 1.创建事件解析器
		SAXReader sr = new SAXReader();
		// 2.读取XML文件并创建文本容器
		Document doc = sr.read(path);
		// 3.获取XML根节点
		Element root = doc.getRootElement();
		// 4.遍历根节点
		VXMLData vNode = getElementValue(root);
		return vNode;
	}

	public static VXMLData getElementValue(Element e) {
		// 5.获取根节点的属性值
		List<Attribute> list = e.attributes();
		Iterator<Element> it2 = e.elementIterator();
		VXMLData vNode = new VXMLData(e.getName(),"",false);
		for (Attribute a : list) {
			vNode.addChild(new VXMLData(a.getName(),a.getText(),true));
		}
		// 6.通过遍历获取id对应的属性值
		while (it2.hasNext()) {
			Element next = it2.next();
			String name2 = next.getName();
			String text2 = "";
			if(next.elementIterator().hasNext())	//element包含子节点
			{
				VXMLData subElement = getElementValue(next);	//递归获取子节点数据
				vNode.addChild(subElement);
			}
			else
			{
				vNode.addChild(new VXMLData(next.getName(),next.getText(),false));
			}
		}
		return vNode;
	}
	
	
}
