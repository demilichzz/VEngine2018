/**
 * @Title: VUIFactory.java
 * @Package vEngine.ui
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-7-30
 * @version V1.0
 */
package vEngine.ui;

import lombok.Data;
import vEngine.global.Global;
import vEngine.global.NameDic;
import vEngine.interfaces.VUIDataInterface;

/**
 * @author Demilichzz
 *
 */
@Data
public class VUIFactory implements VUIAbstractFactory{

	protected String imagestr; //图像文件名
	protected double x, y; // UI左上角坐标基于父UI坐标的偏移
	protected double transparency = 1.0;	//透明度
	protected double scale=1.0;	//UI的图形缩放比例，UI区域会随着缩放比例的变化改变
	//protected HashMap<Integer,VText> textlist=new HashMap<Integer,VText>();	//UI包含的文本列表
	protected boolean visible; // 可视性
	protected boolean enable; // 可交互性
	protected String uiID; // 在UIManager中的索引
	protected boolean autoupdate=true;		//是否自动应用updateUI函数更新
	protected String parent; //父UI
	protected String text; //UI文本
	
	public VUIFactory()
	{
		setDefaultParams();
	}

	public VUIFactory clone()
	{
		VUIFactory f = new VUIFactory();
		f.imagestr=this.imagestr;
		f.x=this.x;
		f.y=this.y;
		f.transparency=this.transparency;
		f.scale=this.scale;
		f.visible=this.visible;
		f.enable=this.enable;
		f.parent=this.parent;
		f.text=this.text;
		return f;
	}
	

	/**
	 * 设定UI参数默认值
	 */
	public void setDefaultParams() {
		// TODO Auto-generated method stub
		imagestr = "nullpic.png";
		x = 0;
		y = 0;
		scale = 1.0;
		visible = true;
		enable = true;
		parent = "uiparent";
		text = null;
	}
	
	public void setParams(VUIDataInterface data)
	{
		setDefaultParams();
		if(data.getUIAttrib("id")!=null)
		{
			uiID=data.getUIAttrib("id");
			if(data.getUIAttrib("imagestr")!=null)
			{
				imagestr=data.getUIAttrib("imagestr");
			}
			if(data.getUIAttrib("locX")!=null)
			{
				x=Double.parseDouble(data.getUIAttrib("locX"));
			}
			if(data.getUIAttrib("locY")!=null)
			{
				y=Double.parseDouble(data.getUIAttrib("locY"));
			}
			if(data.getUIAttrib("transparency")!=null)
			{
				transparency=Double.parseDouble(data.getUIAttrib("transparency"));
			}
			if(data.getUIAttrib("scale")!=null)
			{
				scale=Double.parseDouble(data.getUIAttrib("scale"));
			}
			if(data.getUIAttrib("visible")!=null)
			{
				visible=Boolean.parseBoolean(data.getUIAttrib("visible"));
			}
			if(data.getUIAttrib("enable")!=null)
			{
				enable=Boolean.parseBoolean(data.getUIAttrib("enable"));
			}
			if(data.getUIAttrib("parent")!=null)
			{
				parent=data.getUIAttrib("parent");
			}
			if(data.getUIAttrib("text")!=null)
			{
				text=data.getUIAttrib("text");
			}
		}	
	}


	@Override
	public VUI creator(String type) {
		// TODO Auto-generated method stub
		VUI ui;
		if(type.equals("VMouseActionUI"))
		{
			ui = new VMouseActionUI(imagestr,uiID);
		}
		else
		{
			ui = new VUI(imagestr,uiID);
		}
		ui.setLoc(x, y);
		ui.setTransparency(transparency);
		ui.setScale(scale);
		ui.setVisible(visible);
		ui.setEnable(enable);
		ui.setParentByID(parent);
		if(text!=null&&!text.equals(""))
		{
			ui.addText(NameDic.getNamedicValue(text));
		}
		return ui;
	}

}
