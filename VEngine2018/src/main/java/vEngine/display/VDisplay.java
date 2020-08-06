/**
 * @author Demilichzz 显示类，使用lwjgl提供的api实现绘制游戏场景 2013-6-8
 */
package vEngine.display;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.nio.IntBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import vEngine.global.Fontconst;
import lombok.Data;
import vEngine.global.Debug;
import vEngine.global.Global;
import vEngine.system.GameState;
import vEngine.system.VEngine;

/**
 * @author Demilichzz
 *
 *         2013-6-8
 */
@Data
public class VDisplay {
    protected VEngine ve;
    public float x = 0;
    public float y = 0;
    public float z = 0;
    public float rotationx = 0;
    public float rotationy = 0;
    public float rotationz = 0;
    // public VUI testui;
    public VCamera newCamera; // 摄像机对象
    public VLightControl lightcontroller; // 光照控制对象

    private long window;

    // public UnicodeFont font;

    // singleton define
    private VDisplay() {
        newCamera = new VCamera();
        lightcontroller = new VLightControl();
        Debug.DebugSimpleMessage("Display初始化完成");
    }

    private static volatile VDisplay instance = new VDisplay();

    public static VDisplay getInstance() {
        if (instance == null) {
            synchronized (VDisplay.class) {
                if (instance == null) {
                    instance = new VDisplay();
                }
            }
        }
        return instance;
    }

    public void initGL() {
        try {
            /*
             * Display.setDisplayMode(new DisplayMode(Global.windowX, Global.windowY));
             * //Display.setTitle("FPS:60"); PixelFormat pf = new PixelFormat(0,8,8);
             * Display.create(pf); Display.setLocation(150, 10);
             */
            // Display.setVSyncEnabled(true);
            GLFWErrorCallback.createPrint(System.err).set();
            if (!glfwInit()) {
                throw new IllegalStateException("Unable to initialize GLFW");
            }
            // Configure GLFW
            glfwDefaultWindowHints(); // optional, the current window hints are already the default
            glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after
                                                                // creation
            glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE); // the window will be not
                                                                  // resizable
            window = glfwCreateWindow(Global.windowX, Global.windowY, "Hello World!", NULL, NULL);
            if (window == NULL) {
                throw new RuntimeException("Failed to create the GLFW window");
            }
            // Get the thread stack and push a new frame
            try (MemoryStack stack = stackPush()) {
                IntBuffer pWidth = stack.mallocInt(1); // int*
                IntBuffer pHeight = stack.mallocInt(1); // int*

                // Get the window size passed to glfwCreateWindow
                glfwGetWindowSize(window, pWidth, pHeight);

                // Get the resolution of the primary monitor
                GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

                // Center the window
                glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2);
            } // the stack frame is popped automatically

            // Make the OpenGL context current
            glfwMakeContextCurrent(window);
            
            
            
            // Enable v-sync
            glfwSwapInterval(1);
            glfwSetWindowPos(window, 150, 50);
            glfwShowWindow(window);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        
        renderPrepare();

		glEnable(GL_BLEND);							//启用混合
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);      
		glShadeModel(GL_SMOOTH);        
		glDisable(GL_DEPTH_TEST);		//关闭深度测试
		glDisable(GL_STENCIL_TEST);  	//关闭模板测试

		glViewport(0,0,Global.windowX, Global.windowY);				//设置视口
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Global.windowX, Global.windowY, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glClearStencil(0);

    }

    /**   
     * @Title: checkWindow   
     * @Description: isCloseRequested
     * @param: @return      
     * @return: boolean      
     * @throws   
     */
    public boolean checkWindow() {
        // Debug.DebugSimpleMessage("" + GLFW.glfwWindowShouldClose(window));
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroyWindow() {
        GLFW.glfwDestroyWindow(window);
    }

    public void setWindowTitle(String title) {
        GLFW.glfwSetWindowTitle(window, title);
    }

    public void renderPrepare() {
        // TODO 渲染预处理，以保证渲染机制正确运行
        // GL11.glClearStencil(0);
        // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT |
        // GL11.GL_STENCIL_BUFFER_BIT);
        // Fontconst.loadGlyphs();
        // font = Fontconst.getFont("font_default");
        // font.drawString(1280, 800, "test",Color.black);
        // GL11.glEnable(GL11.GL_TEXTURE_2D);
        // Display.update();
        GL.createCapabilities();
    	glEnable(GL_TEXTURE_2D);
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        // while ( !glfwWindowShouldClose(window) ) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); // clear the
                                                                                    // framebuffer
        
        Fontconst.loadGlyphs();
		//UnicodeFont font = Fontconst.getFont("font_default");
		//font.drawString(1280, 800, "test",Color.black);

        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
        // }
    }

    public void render() {
        // TODO 在每帧游戏窗体更新时渲染当前游戏场景
        // Debug.DebugSimpleMessage("VDisplay.Render");
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); // clear the
                                                            // framebuffer
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        Fontconst.loadGlyphs();
        GameState gs = GameState.getInstance();
        gs.drawScene();
        lightcontroller.processLighting();
        
        glfwSwapBuffers(window); // swap the color buffers
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
        // Fontconst.loadGlyphs();
    }
}
