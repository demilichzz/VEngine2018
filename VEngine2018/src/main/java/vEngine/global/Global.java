/**
 * @author Demilichzz
 *
 *         2013-6-8
 */
package vEngine.global;

/**
 * @author Demilichzz
 *
 *         2013-6-8
 */
public class Global {
    public static final int keydelay = 200;
    public static final int FPS = 60;
    public static final int windowX = 1280;
    public static final int windowY = 800;
    public static int unitid = 0;

    public static long getTime() { //使用lwjgl获取当前时间(毫秒)
        return System.nanoTime();
    }

    /**
     * @return
     */
    public static int generateUnitID() {
        // TODO Auto-generated method stub
        unitid++;
        return unitid;
    }
}
