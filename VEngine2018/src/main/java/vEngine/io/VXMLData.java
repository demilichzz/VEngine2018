/**
 * 文件名称：VStringNode.java
 * 类路径：xml
 * 描述：TODO 字符串节点类,用于实现从同一类型实体获取单个String或一个String数组的数据结构
 * 			  自顶向下单向树状结构
 * 作者：Demilichzz
 * 时间：2012-4-21上午08:58:09
 * 版本：Ver 1.0
 */
package vEngine.io;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Demilichzz
 *
 */
public class VXMLData {
	@Getter
	@Setter
	protected String name;
	@Getter
	@Setter
	protected String value;
	@Getter
	@Setter
	protected boolean isAttribute;

	protected ArrayList<VXMLData> childlist; // 子节点

	public VXMLData(String name, String value, boolean isAttribute) {
		this.name = name;
		this.value = value;
		this.isAttribute = isAttribute;
		childlist = new ArrayList<VXMLData>();
	}

	public void addChildren(ArrayList<VXMLData> children) {
		// TODO 添加子节点列表
		this.childlist.addAll(children);
	}

	public void addChild(VXMLData n) {
		// TODO 添加单个子节点
		if (n != null) {
			this.childlist.add(n);
		}
	}

	public ArrayList<VXMLData> getChildlist() {
		// TODO 获取子节点数组
		return childlist;
	}

	public boolean haveChildren() {
		// TODO 获取节点是否尚有子节点
		if (childlist != null) {
			if (childlist.size() > 0) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Debug用输出节点信息
	 */
	public void print() {
		System.out.println(getName() + "=" + getValue());
		for(VXMLData n:getChildlist())
		{
			n.print();
		}
	}
}
