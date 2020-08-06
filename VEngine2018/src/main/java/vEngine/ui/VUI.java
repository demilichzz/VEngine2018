/**
 * 文件名称：VUI.java
 * 类路径：ui
 * 描述：TODO UI基类，主要功能为用户界面的绘制部分，操作响应通常在子类中实现
 * 作者：Demilichzz
 * 时间：2011-11-4下午01:56:14
 * 版本：Ver 1.0
 */
package vEngine.ui;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import vEngine.display.VText;
import vEngine.display.VTexture;
import vEngine.entities.VArea;
import vEngine.entities.VEntity;
import vEngine.global.Global;
import vEngine.global.Imageconst;
import vEngine.interfaces.VActionInterface;

/**
 * @author Demilichzz
 * 
 */
public class VUI extends VEntity implements VActionInterface {
	// -----------UI类型常量------------------------
	public static final int UITYPE_DEFAULT = 0; // 默认UI
	public static final int UITYPE_MOUSE = 1; // 鼠标操作型UI
	public static final int MENU_RESPONSE_LR = 0;	//菜单响应模式为左右键
	public static final int MENU_RESPONSE_UD = 1;	//菜单响应模式为上下键
	public static final int MENU_RESPONSE_LRUD = 2;	//菜单响应模式为上下左右键
	// -----------常规参数-------------------------------
	public VTexture image; // UI图像
	protected double transparency = 1.0;	//透明度
	protected Color uicolor = new Color(255,255,255,255);
	// protected String imagestr; // UI图像在Imageconst中对应字符串,即图像文件名
	// 现已不需要此变量,直接调用image.toString();
	protected double x, y; // UI左上角坐标基于父UI坐标的偏移
	//protected VUIText text;
	protected double scale=1.0;	//UI的图形缩放比例，UI区域会随着缩放比例的变化改变
	protected HashMap<Integer,VText> textlist=new HashMap<Integer,VText>();	//UI包含的文本列表
	public boolean visible; // 可视性
	public boolean enable; // 可交互性
	public String uiID; // 在UIManager中的索引
	public VArea area; // UI区域,用于显示UI边框及处理鼠标事件
	protected boolean autoupdate=true;		//是否自动应用updateUI函数更新
	// ------------Lua脚本行为------------------------------
	protected VActionInterface action;			//常规行为脚本绑定
	// ------------UI十字链表结构----------------------------
	public VUI parent; // 父UI的引用
	public VUI firstChild; // 最底层子UI,即子UI链表始端
	public VUI nextUI; // 上层UI,即同层UI链表下一个
	public VUI previousUI; // 下层UI,即同层UI链表上一个

	public VUI() {

	}

	public VUI(int width, int height, String ID) {
		// TODO 通过宽高定义制定大小的图像资源为空的UI
		image = null;
		x = 0;
		y = 0;
		scale = 1.0;
		visible = true;
		enable = true;
		uiID = ID;
		area = new VArea(0, 0, width, height);
		area.boldborder = false;
	}

	public VUI(String str, String ID) {
		// TODO 通过图像文件名建立UI的构造函数
		image = Imageconst.GetImageByName(str);
		x = 0;
		y = 0;
		scale = 1.0;
		visible = true;
		enable = true;
		uiID = ID;
		if(image!=null){
			area = new VArea(0, 0, image.getWidth(), image.getHeight());
			area.boldborder = false;
		}
		else{
			area = new VArea(0, 0,16,16);
			area.boldborder = false;
		}
	}

	public void setAreaSize(int width,int height){
		if(image==null){
			area = new VArea(0, 0, width, height);
			area.boldborder = false;
			area.setLoc(getRealX(), getRealY());
		}
	}
	
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		// TODO 设置可见性时,可交互性与其同步,并递归设置子UI
		this.visible = visible;
		this.enable = visible;
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			child.setVisible(visible);
		}
	}

	public boolean getEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		// TODO 递归设置子UI
		this.enable = enable;
		if(enable){		//如果设置为真，还要检查是否可视
			this.enable = visible;
		}
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			child.setEnable(enable);
		}
	}
	public void setTransparency(double d){
		// TODO 设置UI的透明度
		transparency = d;
		uicolor = new Color(255,255,255,(int)(255*d));
		if (textlist.size()>0){
			for(VText tt :textlist.values()){
				tt.setTransparency(d);
			}
		}
	}
	public void setColor(int r,int g,int b,int a){
		// TODO 设置UI的颜色
		uicolor = new Color(r,g,b,a);
	}
	public void setParent(VUI p) {
		// TODO 设置UI的父UI,同时将其插入父UI的子UI链表末尾
		this.removeUIRelation();	//先将UI本身从树形结构中移除
		boolean b = false;
		if(p.getUIByID(uiID)!=null){
			b=true;
		}
		if (p != null) {
			parent = p;
			VUI child; // 临时UI child为父节点的第一个子节点
			if (parent.firstChild != null) {
				for (child = parent.firstChild; child.nextUI != null; child = child.nextUI)
					;// 通过child的链表获取最后一个节点
				child.nextUI = this; // 设父节点的子链表末尾为当前节点
				this.previousUI = child;
			} 
			else { // 父UI无子节点
				parent.firstChild = this;
			}
			this.setLoc(x, y);	//父UI改变时需按当前偏移值重设area的绝对位置
		}
		if(b){
			p.getUIByID(uiID).removeUIRelation();//如果已存在同索引则删除原有UI
		}
	}

	public void setParentByID(String ID) {
		// TODO 通过uiID设置父UI
		setParent(Global.getUIByID(ID));
	}

	/*
	 * public void swapUI(VUI ui_a, VUI ui_b) { // TODO 交换某个父UI的子UI链表中两个UI的顺序 if
	 * (ui_a.parent != ui_b.parent) {
	 * Debug.DebugSimpleMessage("要交换的UI的父UI不同,无法交换"); } else { VUI temp_a_p =
	 * ui_a.previousUI; VUI temp_a_n = ui_a.nextUI; VUI temp_b_p =
	 * ui_b.previousUI; VUI temp_b_n = ui_b.nextUI; if (temp_a_p == null) { //
	 * ui_a是firstChild ui_a.parent.firstChild = ui_b; } else if (temp_b_p ==
	 * null) { ui_b.parent.firstChild = ui_a; } ui_a.previousUI = temp_b_p;
	 * ui_a.nextUI = temp_b_n; ui_b.previousUI = temp_a_p; ui_b.nextUI =
	 * temp_a_n; if (ui_a.nextUI == ui_a || ui_b.previousUI == ui_b) {
	 * ui_a.nextUI = ui_b; ui_b.previousUI = ui_a; } else if (ui_a.previousUI ==
	 * ui_a || ui_b.nextUI == ui_b) { ui_a.previousUI = ui_b; ui_b.nextUI =
	 * ui_a; } } }
	 */
	public void insertUI(VUI tarui, int i) {
		// TODO 将当前UI插入到目标UI同级的前/后方(显示下/上层)
		if (i > 0) { // 插入前方
			this.nextUI = tarui;
			this.previousUI = tarui.previousUI;
			if (tarui.previousUI != null) {
				tarui.previousUI.nextUI = this;
			}
			else{
				tarui.parent.firstChild = this;
			}
			tarui.previousUI = this;
			this.parent = tarui.parent;
		} 
		else if (i < 0) { // 插入后方
			this.previousUI = tarui;
			this.nextUI = tarui.nextUI;
			if (tarui.nextUI != null) {
				tarui.nextUI.previousUI = this;
			}
			tarui.nextUI = this;
			this.parent = tarui.parent;
		}
	}

	public void removeUIRelation() {
		// TODO 将UI从UI十字链表结构关系移除,并不删除UI对象
		if (previousUI != null) {
			previousUI.nextUI = nextUI;
		}
		else {
			if(parent!=null){
				parent.firstChild = nextUI;
			}
		}
		if (nextUI != null) {
			nextUI.previousUI = previousUI;
		}
		previousUI = null;
		nextUI = null;
		parent = null;
	}

	public void sortUI(int i) {
		// TODO 为UI调整在链表中顺序,i为正则上浮,为负则下沉
		if (i > 0) {
			if (this.nextUI != null) {
				VUI t = this.nextUI;
				this.removeUIRelation();
				this.insertUI(t, -1);
			}
		} else if (i < 0) {
			if (this.previousUI != null) {
				VUI t = this.previousUI;
				this.removeUIRelation();
				this.insertUI(t, 1);
			}
		}
	}
	public int getHeight(){
		return (int) ((area.h-area.y));
	}
	public int getWidth(){
		return (int) ((area.w-area.x));
	}
	public boolean getPointState(int x, int y) {
		// TODO 获取目标坐标是否在此UI范围内
		return area.ifvalid(x, y);
	}

	public VUI getUIByID(String ID) {
		// TODO 通过ID查找当前UI的子UI
		VUI temp = null;
		if (this.uiID.equals(ID)) {
			return this;
		}
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			if (child.uiID.equals(ID)) {
				temp = child;
				break;
			} 
			else {
				temp = child.getUIByID(ID);
				if (temp != null) {
					break;
				}
			}
		}
		return temp;
	}
	public VUI getUIBycontainID(String ID) {
		// TODO 通过ID包含的字符串查找当前UI的子UI
		VUI temp = null;
		if (this.uiID.contains(ID)) {
			return this;
		}
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			if (child.uiID.contains(ID)) {
				temp = child;
				break;
			} 
			else {
				temp = child.getUIBycontainID(ID);
				if (temp != null) {
					break;
				}
			}
		}
		return temp;
	}
	/**
	 * 设置uiID,不会影响链表结构,注意!!不要将两个UI设为同一ID
	 * @param ID
	 */
	public void setID(String ID) {
		// TODO 
		this.uiID = ID;
	}

	public void setLoc(double x, double y) {
		// TODO 设置UI坐标,当UI坐标可能有改动时,需重设area的绝对坐标并递归设置子UI
		this.x = x;
		this.y = y;
		area.setLoc(getRealX(), getRealY());
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			child.setLoc(child.GetX(),child.GetY());	//更新子UI的区域绝对坐标
		}
		/*if(text!=null){
			if(text.xmax!=0){
				text.setTypesetting(this);
			}
		}*/
	}
	public void setScale(double scale){
		this.scale = scale;
		area = new VArea(0, 0, (int)((area.w-area.x)*scale), (int)((area.h-area.y)*scale));
		area.setLoc(getRealX(), getRealY());
		for(VText vt : textlist.values()){
			vt.setLoc(vt.getX()*scale, vt.getY()*scale);
			vt.restrictScale(scale);
		}
	}
	public void setRealLoc(double x,double y){
		// TODO 设置UI绝对坐标,并转化为相对坐标存储
		if(parent!=null){
			this.x = x - parent.getRealX();
			this.y = y - parent.getRealY();
		}
		else{
			this.x = x;
			this.y = y;
		}
		area.setLoc(x, y);
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			child.setLoc(child.GetX(),child.GetY());	//更新子UI的区域绝对坐标
		}
	}

	public void moveLoc(double x, double y) {
		// TODO 移动UI坐标
		setLoc(this.x + x, this.y + y);
	}

	public double GetX() {
		// TODO 获取x坐标偏移
		return x;
	}

	public double GetY() {
		// TODO 获取Y坐标偏移
		return y;
	}
	public double getRealX(){
		// TODO 获取实际x坐标,即屏幕定位坐标
		if(parent!=null){
			return parent.getRealX()+x;
		}
		else{
			return x;
		}
	}
	public double getRealY(){
		if(parent!=null){
			return parent.getRealY()+y;
		}
		else{
			return y;
		}
	}

	public void setUIState(boolean b) {
		// TODO 设置UI是否可用,同时设置可见性
		enable = b;
		visible = b;
	}
	public void setImage(String ad) {
		// TODO 重设UI图像并重设区域
		image = Imageconst.GetImageByName(ad);
		area = new VArea(0, 0, (int)(image.getWidth()*scale), (int)(image.getHeight()*scale));
		area.boldborder = false;
		area.setLoc(getRealX(), getRealY());
	}

	public void drawUI() {
		// TODO 绘制UI
		if (visible) {
			drawUIImage();
			drawUIText();
			drawUIBorder();
			drawSubUI();
		}
	}
	protected void drawUIImage(){
		if (image != null) {	//绘制UI图像
			image.directDrawTexture(getRealX(), getRealY(), -1,scale,uicolor);
		}
		else{
			//Imageconst.nullpic.directDrawTexture(getRealX(), getRealY(), -1,scale);
		}
	}
	protected void drawUIText(){
		if (textlist.size()>0){
			for(VText tt :textlist.values()){
				if(tt.getVisible()){
					tt.drawText();
				}
			}
		}
	}
	protected void drawSubUI(){
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			child.drawUI();	//遍历子UI绘制
		}
	}
	protected void drawUIBorder(){
		if (VArea.drawArea) {
			area.drawMe();	//绘制UI边界线
		}
	}
	public void addAction(VActionInterface action) {
		// TODO 为UI关联在Lua脚本中实现的行为
		this.action = action;
	}
	public void removeAction(VActionInterface action) {
		// TODO 移除UI关联的行为
		this.action = null;
	}
	public void addText(String text)
	{
		VText t = new VText(text);
		addText(t);
	}
	public void addText(VText text){
		int size = textlist.size();
		addText(text,size);
	}
	public void addText(VText text,int index){
		int size = textlist.size();
		textlist.put(index, text);
		text.setParentUI(this);
	}
	public void removeText(int index){
		if(textlist.size()>index){
			textlist.remove(index);
		}
	}
	public void clearText(){
		textlist = new HashMap<Integer,VText>();
	}
	public VText getText(int index){
		return textlist.get(index);
	}
	public void action(String args) {
		// TODO 参数为调用者类别字符串
		if (enable) {
			if (!args.equals("MouseEvent")) {
				if (action != null) {
					action.action("");
				}
			}
		}
	}
	public void setActionVisible(boolean b){
		// TODO 设置UI为可见的同时,执行关联的可视化触发脚本行为
		if(b){
			this.setVisible(true);
			if(action!=null){
				action.action("VisibleTrue");
			}
		}
		else{
			this.setVisible(false);
			if(action!=null){
				action.action("VisibleFalse");
			}
		}
	}

	public VUI getMAUIbyLoc(int x, int y) {
		// TODO 通过坐标点获取最上层UI，仅对VMouseActionUI类型有效
		VUI temp = null;
		if (this.getPointState(x, y)) {
			temp = this;
			if (!this.visible) {	//若此UI不可视，则不再往下遍历并返回null
				temp = null;
			}
			if (!(this instanceof VMouseActionUI)) {	//若非VMouseActionUI
				temp = null;
			}
		}
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			if (child.getMAUIbyLoc(x, y) != null) {
				temp = child.getMAUIbyLoc(x, y);
			}
		}
		return temp;
	}
	public VUI getUIbyLoc(int x,int y){
		// TODO 通过坐标点获取最上层可视且有效的UI
		VUI temp = null;
		if (this.getPointState(x, y)) {
			temp = this;
			if (!enable) {
				temp = null;
			}
		}
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			if (child.getUIbyLoc(x, y) != null) {
				temp = child.getUIbyLoc(x, y);
			}
		}
		return temp;
	}

	public void updateUI(){
		// TODO 更新UI显示内容,在每次GS更新时调用保证显示与实际数据同步,在继承VUI的需要的子类中重载
		//		并用于实现子UI中的UI效果
		// 递归更新子UI
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			if(child.autoupdate){
				child.updateUI();
			}
		}
		for(VText text:textlist.values()){
			text.expire();
		}
	}
	/**
	 * @param keystate
	 */
	public void uiKeyAction(int[] keystate) {
		// TODO 在GS中获取按键状态，并递归执行UI定义的键盘行为
		// 递归对子UI执行键盘行为
		for (VUI child = firstChild; child != null; child = child.nextUI) {
			if(child.enable){
				child.uiKeyAction(keystate);
			}
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		updateUI();
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		drawUI();
	}
	
//	public void uiLock(int time){
//		// TODO 在指定时间段内禁用当前UI
//		if(enable){
//			setEnable(false);
//			VTimer tm = new VTimer(time,time,false,new VLuaAction(){
//				@Override
//				public void action() {
//					// TODO Auto-generated method stub
//					setEnable(true);
//				}		
//			});
//			tm.timerStart(VTimer.TIMER_STAGE);
//		}
//	}
}
