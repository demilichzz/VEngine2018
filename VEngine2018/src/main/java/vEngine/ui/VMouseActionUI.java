/**
 * @author Demilichzz
 *	进行鼠标响应的UI类
 * 2013-11-9
 */
package vEngine.ui;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;

import vEngine.action.VActionInterface;
import vEngine.controller.VGameController;
import vEngine.controller.VMouseListener;

/**
 * @author Demilichzz
 *
 * 2013-11-9
 */
public class VMouseActionUI extends VUI{
	protected boolean pressstate = false;	//表示该UI是否被鼠标点击选中
	protected int activeoffx = 0;
	protected int activeoffy = 0;	//鼠标点击点与UI坐标的偏移
	protected boolean allowdrag = false;
	protected boolean holdstate = true;		//是否允许悬停计数增加
	protected int holdtime = 0;
	protected int holdlimit = 100;	//默认悬停触发延迟1秒
	
	public VMouseActionUI(int width, int height, String ID) {
		super(width,height,ID);
	}
	public VMouseActionUI(String str, String ID){
		super(str,ID);
	}
	
	public void setDragState(boolean b){
		// TODO 设置UI是否可拖动
		allowdrag = b;
	}
	public boolean getAllowdrag()
	{
		return allowdrag;
	}
	public void action(String args) {
		// TODO 参数为调用者类别字符串
		if (enable) {
			if(getImage()!=null&&getImage().getCrop())	//如果UI图像纹理设定了分割，设定默认表示模式
			{
				if (args.equals("Mouse_Press"))
				{
					setTextureIndex(1);
				}
				else if(args.equals("Mouse_Release")||args.equals("Mouse_HoldRelease"))
				{
					setColor(255,255,255,255);
					setTextureIndex(0);
				}
				else if(args.equals("Mouse_Hold"))
				{
					setColor(200,255,200,255);
				}
			}
			
			if (action != null) {
				action.action(args);
			}
		}
	}
	public void uiHoldInc(){
		// TODO 在游戏更新时，若鼠标持续悬停在UI上，则增加UI的悬停计时直到设置的悬停时间
		if(holdstate){
			holdtime = holdtime+1;
			if(holdtime>=holdlimit){
				holdstate = false;
				if(action!=null){
					action.action("Hold");
				}
			}
		}
	}
	public void uiHoldRelease(){
		// TODO 鼠标不再悬停于此UI上
		holdtime = 0;
		holdstate = true;
		if(action!=null){
			action.action("Release");
		}
	}
	/**
	 * @param x
	 * @param y
	 */
	public void uiDragPress(int x, int y) {
		// TODO 点击UI时调用，切换UI是否被拖拽的状态并根据点击位置设置偏移
		if(enable){
			if(!pressstate&&allowdrag){
				pressstate = true;
				// ((VMouseListener) VGameController.getInstance().getListener("Mouse")).setActiveUI(this);
				activeoffx = (int)this.GetX()-x;
				activeoffy = (int)this.GetY()-y;
			}
		}
	}
	public void uiDragMoveTo(int x,int y){
		// TODO 将UI拖拽至指定位置，参数x,y为拖拽时的鼠标坐标
		if(enable){
			if(pressstate&&allowdrag){
				this.setLoc(x+activeoffx, y+activeoffy);
			}
		}
	}
	public void uiDragRelease(int x,int y){
		// TODO 释放UI拖拽
		if(enable){
			if(pressstate&&allowdrag){
				pressstate = false;
				activeoffx = 0;
				activeoffy = 0;
			}
		}
	}
}
