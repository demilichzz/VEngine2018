package VEngine.VEngine2018;

import java.nio.file.Path;
import java.nio.file.Paths;

import vEngine.display.*;

public class VEngineTest {

	public static void main(String[] args) {
		if (System.getProperty("org.lwjgl.librarypath") == null) {
		    Path path = Paths.get("native");
		    String librarypath = path.toAbsolutePath().toString();
		    System.setProperty("org.lwjgl.librarypath", librarypath);
		}
		else
		{
			System.out.println(System.getProperty("org.lwjgl.librarypath"));
		}
		
		VDisplay display = VDisplay.getInstance();
		display.initGL();
		display.renderPrepare();
		while(true)
		{
			display.render();
		}
	}
}
