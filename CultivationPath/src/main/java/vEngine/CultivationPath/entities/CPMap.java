/**
 * @Title: CPMap.java
 * @Package vEngine.CultivationPath.entities
 * @Description: 地图类
 * @author Demilichzz
 * @date 2020-8-8
 * @version V1.0
 */
package vEngine.CultivationPath.entities;

import vEngine.display.VTexture;
import vEngine.entities.VEntity;
import vEngine.global.Global;
import vEngine.global.Imageconst;
import vEngine.global.VMath;
import vEngine.system.GameState;

/**
 * @author Demilichzz
 *
 */
public class CPMap extends VEntity {
	protected VTexture mapTile = null;
	protected double tile_height; // 像素单位tile宽高
	protected double tile_width;
	protected double axis_x; // 屏幕中心的地图坐标
	protected double axis_y;
	protected double map_height; // 地图宽高，像素单位，32的倍数
	protected double map_width;
	protected int x_tile; // 地图宽高tile数
	protected int y_tile;

	protected int[][] map;

	public CPMap(String texture, double w, double h) {
		setTexture(texture);
		map_height = h;
		map_width = w;
		x_tile = (int) Math.ceil(map_width / tile_width);
		y_tile = (int) Math.ceil(map_height / tile_height);
		map = new int[x_tile][y_tile];
		for (int i = 0; i < x_tile; i++) {
			for (int j = 0; j < y_tile; j++) {
				map[i][j] = VMath.GetRandomInt(0, 1);
			}
		}
		axis_x = 1000.5;
		axis_y = 1000.5;
	}

	public void setTexture(String texture) {
		this.mapTile = Imageconst.GetImageByName(texture);
		tile_height = mapTile.getHeight();
		tile_width = mapTile.getWidth();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		for (int i = 0; i < x_tile; i++) {
			for (int j = 0; j < y_tile; j++) {
				double realX = i * tile_width - axis_x + Global.windowX/2; // tile坐标-地图坐标偏移计算tile真实坐标
				double realY = j * tile_height - axis_y + Global.windowY/2;
				if (realX >= -tile_height && realX <= Global.windowX + tile_height && realY >= -tile_width
						&& realY <= Global.windowY + tile_width) {	// 判断tile在屏幕范围内
					mapTile.directDrawTexture(realX, realY, map[i][j], 1);
				}
			}
		}
	}
	
	/**
	 * 移动地图
	 * @param x	X轴方向
	 * @param y Y轴方向
	 * @param speed 移动速度 像素/秒
	 */
	public void mapMove(int x,int y,double speed)
	{
		double movedist = speed/1000*GameState.getInstance().getMSecond();
		axis_x = axis_x + x * movedist;
		axis_y = axis_y + y * movedist;
	}
}
