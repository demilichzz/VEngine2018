/**
 * @Title: VTextTest.java
 * @Package vEngine.display
 * @Description: TODO
 * @author Demilichzz
 * @date 2020-7-28
 * @version V1.0
 */
package vEngine.display;

import java.awt.Font;

import org.lwjgl.opengl.GL11;

import lombok.Data;
import vEngine.interfaces.VRenderableInterface;

/**
 * @author Demilichzz
 *
 */
@Data
public class VTextTest implements VRenderableInterface {
    protected final String text;
    
	public VTextTest(String text)
	{
		this.text=text;
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		//texture.bind();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
		
		

	    GL11.glBindTexture(GL11.GL_TEXTURE_2D,0);
	}

}
