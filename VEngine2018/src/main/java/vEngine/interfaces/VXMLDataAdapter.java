/**
 * @Title: VUIDataAdapter.java
 * @Package vEngine.ui
 * @Description: UI用XML数据适配器
 * @author Demilichzz
 * @date 2020-7-31
 * @version V1.0
 */
package vEngine.interfaces;

import vEngine.io.VXMLData;

/**
 * @author Demilichzz
 *
 */
public class VXMLDataAdapter implements VUIDataInterface{
	protected VXMLData data;
	
	public VXMLDataAdapter(VXMLData data)
	{
		this.data = data;
	}	

	/**
	 *根据传入参数获取XML参数值
	 */
	@Override
	public String getUIAttrib(String attrib) {
		// TODO Auto-generated method stub
		for(VXMLData child:data.getChildlist())
		{
			if(child.getName().equals(attrib))
			{
				return child.getValue();
			}
		}
		return null;
	}
}
