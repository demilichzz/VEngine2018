package test;

import vEngine.display.VDisplay;
import vEngine.system.GameState;

public class VEngineTest {

    public static void main(String[] args) {
        /*if (System.getProperty("org.lwjgl.librarypath") == null) {
            Path path = Paths.get("native");
            String librarypath = path.toAbsolutePath().toString();
            System.setProperty("org.lwjgl.librarypath", librarypath);
        }
        else
        {
        	System.out.println(System.getProperty("org.lwjgl.librarypath"));
        }*/

        // GameState.getInstance().getGsRefreshTime();
        VDisplay display = VDisplay.getInstance();
        display.initGL();
        display.renderPrepare();
        while (true) {
            display.render();
        }
    }
}
