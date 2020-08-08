/**
 * @author Demilichzz
 *	纹理类，用于定义在OpenGL中使用的纹理
 * 2013-6-14
 */
package vEngine.display;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import vEngine.global.VMath;
import vEngine.interfaces.VRenderableInterface;

/**
 * @author Demilichzz
 *
 *         2013-6-14
 */
public class VTexture implements VRenderableInterface {
	public static final int TexCoord_LTOPX = 0; // 用于在getTexCoord时表示获取左上顶点的纹理坐标x
	public static final int TexCoord_LTOPY = 1; // 左上y
	public static final int TexCoord_RBOTX = 2; // 右下x
	public static final int TexCoord_RBOTY = 3; // 右下y

	protected String texturePath;
	protected Texture texture = null;
	protected int xcrop = 1;
	protected int ycrop = 1;
	protected int index = -1;

	public VTexture(String path, String name) {
		try {
			texturePath = name;
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		return texturePath;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setCrop(int x, int y) {
		xcrop = x;
		ycrop = y;
		setIndex(x * y - 1);
	}

	/**
	 * 获取纹理是否已设定分割
	 * 
	 * @return
	 */
	public boolean getCrop() {
		return xcrop != 1 || ycrop != 1;
	}

	public int getCropX() {
		return xcrop;
	}

	public int getCropY() {
		return ycrop;
	}

	public void setIndex(int i) {
		index = i;
	}

	public float getTexCoord(int i) {
		float coord = 0.0f;
		if (index < xcrop * ycrop && index != -1) {
			int x = index % xcrop;
			int y = index / xcrop; // 计算索引指向的纹理处于第x列和y行
			switch (i) {
			case TexCoord_LTOPX: {
				coord = x * 1.0f / xcrop;
				break;
			}
			case TexCoord_LTOPY: {
				coord = y * 1.0f / ycrop;
				break;
			}
			case TexCoord_RBOTX: {
				coord = (x + 1) * 1.0f / xcrop;
				break;
			}
			case TexCoord_RBOTY: {
				coord = (y + 1) * 1.0f / ycrop;
				break;
			}
			}
		} else if (index == -1) { // 索引为-1时 返回完整纹理的坐标
			switch (i) {
			case TexCoord_LTOPX: {
				coord = 0;
				break;
			}
			case TexCoord_LTOPY: {
				coord = 0;
				break;
			}
			case TexCoord_RBOTX: {
				coord = 1;
				break;
			}
			case TexCoord_RBOTY: {
				coord = 1;
				break;
			}
			}
		}
		return coord;
	}

	public void drawTexturePart(double x1, double y1, double x2, double y2, float ltx, float lty, float rbx, float rby,
			double scale, Color c) {
		// TODO 绘制纹理的一部分，ltx,lty,rbx,rby为纹理左上及右下的比例坐标
		float iw = getTexture().getWidth(); // 图像实际长宽比例
		float ih = getTexture().getHeight();
		// x2 = x2*scale;
		// y2 = y2*scale;
		x1 = Math.round(x1);
		y1 = Math.round(y1);
		x2 = Math.round(x2);
		y2 = Math.round(y2);

		GL11.glColor4f(c.r, c.g, c.b, c.a);
		// texture.bind();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture().getTextureID());
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(ltx * iw, lty * ih); // 左上
		GL11.glVertex2d(x1, y1);
		GL11.glTexCoord2f(rbx * iw, lty * ih); // 右上
		GL11.glVertex2d(x2, y1);
		GL11.glTexCoord2f(rbx * iw, rby * ih); // 右下
		GL11.glVertex2d(x2, y2);
		GL11.glTexCoord2f(ltx * iw, rby * ih); // 左下
		GL11.glVertex2d(x1, y2);
		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void drawColorPart(double x1, double y1, double x2, double y2, float ltx, float lty, float rbx, float rby,
			double scale, Color c) {
		// TODO 绘制颜色的一部分，ltx,lty,rbx,rby为纹理左上及右下的比例坐标
		float iw = getTexture().getWidth(); // 图像实际长宽比例
		float ih = getTexture().getHeight();
		// x2 = x2*scale;
		// y2 = y2*scale;
		x1 = Math.round(x1);
		y1 = Math.round(y1);
		x2 = Math.round(x2);
		y2 = Math.round(y2);

		GL11.glColor4f(c.r, c.g, c.b, c.a);
		// texture.bind();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(ltx * iw, lty * ih); // 左上
		GL11.glVertex2d(x1, y1);
		GL11.glTexCoord2f(rbx * iw, lty * ih); // 右上
		GL11.glVertex2d(x2, y1);
		GL11.glTexCoord2f(rbx * iw, rby * ih); // 右下
		GL11.glVertex2d(x2, y2);
		GL11.glTexCoord2f(ltx * iw, rby * ih); // 左下
		GL11.glVertex2d(x1, y2);
		GL11.glEnd();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void directDrawTexture(double x, double y, int index, double scale, Color c) {
		// 在游戏区域指定坐标点处绘制纹理，通常用于绘制UI，index为纹理索引，scale为缩放比例
		setIndex(index);
		float ltx = getTexCoord(VTexture.TexCoord_LTOPX);
		float lty = getTexCoord(VTexture.TexCoord_LTOPY);
		float rbx = getTexCoord(VTexture.TexCoord_RBOTX);
		float rby = getTexCoord(VTexture.TexCoord_RBOTY);
		drawTexturePart(x, y, x + getWidth() * scale, y + getHeight() * scale, ltx, lty, rbx, rby, scale, c);

		// GL11.glColor3f(1,1,1);
	}

	public void directDrawColor(double x, double y, int index, double scale, Color c) {
		// TODO 在纹理指定的区域绘制颜色填充
		setIndex(index);
		float ltx = getTexCoord(VTexture.TexCoord_LTOPX);
		float lty = getTexCoord(VTexture.TexCoord_LTOPY);
		float rbx = getTexCoord(VTexture.TexCoord_RBOTX);
		float rby = getTexCoord(VTexture.TexCoord_RBOTY);
		drawColorPart(x, y, x + getWidth() * scale, y + getHeight() * scale, ltx, lty, rbx, rby, scale, c);
	}

	public void directDrawTexture(double x, double y, int index, double scale) {
		directDrawTexture(x, y, index, scale, new Color(255, 255, 255));
	}

	public void directDrawTextureFromCenter(double x, double y, int index, double scale, Color c) {
		if (index == -1) {
			directDrawTexture(x - getWidth() / 2 * scale, y - getHeight() / 2 * scale, index, scale, c);
		}
	}

	public int getWidth() {
		if (index != -1) {
			return texture.getImageWidth() / xcrop;
		} else {
			return texture.getImageWidth();
		}
	}

	public int getHeight() {
		if (index != -1) {
			return texture.getImageHeight() / ycrop;
		} else {
			return texture.getImageHeight();
		}
	}

	public void draw() {
		directDrawTexture(100, 100, -1, 1.0, Color.white);
	}

	/**
	 * 获取纹理的指定坐标点是否为不透明，纹理是分割纹理的场合
	 * 
	 * @param x_diff
	 * @param y_diff
	 * @return
	 */
	public boolean getPointState(double x_diff, double y_diff, int index) {
		// TODO Auto-generated method stub
		int i = (int) Math.floor(x_diff);
		int j = (int) Math.floor(y_diff);
		if (i < 0 || j < 0 || i > getWidth() || j > getHeight()) {
			return false;
		}
		boolean testbreak = false;
		if (texturePath.equals("UI_select_test.png")) {
			testbreak = true;
		}
		byte[] textureData = texture.getTextureData();
		int textureHw = (int) VMath.getTextureHw(texture.getImageHeight(), texture.getImageWidth()); // 获取纹理边长
		int x = index % xcrop;
		int y = index / xcrop; // 计算索引指向的纹理处于第x列和y行
		int hcrop = texture.getImageHeight() / xcrop;
		int wcrop = texture.getImageWidth() / ycrop; // 计算分割纹理的边长值
		int axis_x = x * hcrop + i;
		int axis_y = y * wcrop + j; // 计算指定坐标点在纹理原始图像的坐标
		int pixelindex = (axis_x + axis_y * textureHw) * 4;
		if(textureData[pixelindex]==-1&&textureData[pixelindex+1]==-1&&textureData[pixelindex+2]==-1&&textureData[pixelindex+3]==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
