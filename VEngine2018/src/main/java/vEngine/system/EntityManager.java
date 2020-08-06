/**
 * @Title: GSUpdateProcesser.java
 * @Package vEngine.system
 * @Description: GameState更新处理器，（默认10ms）GS更新时调用
 * @author Demilichzz
 * @date 2020-7-29
 * @version V1.0
 */
package vEngine.system;

import java.util.ArrayList;

import vEngine.entities.VEntity;
import vEngine.global.Global;
import vEngine.ui.VUI;

/**
 * @author Demilichzz
 *
 */
public class EntityManager {
	protected ArrayList<VEntity> layer_background;
	protected ArrayList<VEntity> layer_game_entities;
	protected VUI uiparent;
	
	public EntityManager()
	{
		layer_background = new ArrayList<VEntity>();
		layer_game_entities = new ArrayList<VEntity>();
		VUI uiparent = new VUI(Global.windowX, Global.windowY, "uiparent");
		setUI(uiparent);
	}
	
	public void draw()
	{
		if(layer_background!=null)
		{
			for(VEntity e:layer_background)
			{
				e.draw();
			}
		}
		if(layer_game_entities!=null)
		{
			for(VEntity e:layer_game_entities)
			{
				e.draw();
			}
		}
		if(uiparent!=null)
		{
			uiparent.draw();
		}
	}
	
	public void update()
	{
		if(layer_background!=null)
		{
			for(VEntity e:layer_background)
			{
				e.update();
			}
		}
		if(layer_game_entities!=null)
		{
			for(VEntity e:layer_game_entities)
			{
				e.update();
			}
		}
		if(uiparent!=null)
		{
			uiparent.update();
		}
	}
	
	public void setUI(VUI ui)
	{
		uiparent = ui;
	}
	public VUI getUI()
	{
		return uiparent;
	}
}
