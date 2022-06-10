package base;

import Util.Time;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * window类，包含窗口的创建和运行
 * @arthur: xh
 *
 */
public class Window {
    private int width, height;
    private String title;
    //使用private static控制始终只有一个窗口对象
    private static Window window = null;
    private long glfwWindow;
    public float r,g,b,a;
    private static  Scene currentScene;
    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Aria";
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
    }

    public static void changeScene(int newScene){
        switch(newScene){
            case 0 :
                currentScene = new LevelEditorScene();
                //init
                break;
            case 1 :
                currentScene = new LevelScene();
                break;
            default:
                assert false : "unknown scene";
                break;
        }
    }
    /**
     * 获得当前窗口
     * @return 当前窗口
     */
    public static Window get(){
        if(Window.window==null){
            Window.window = new Window();
        }

        return Window.window;
    }

    /**
     * 窗口运行函数
     * 包含一个初始化方法和循环方法
     */
    public void run(){
        System.out.println("hello");

        init();
        loop();

        // 释放内存
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /**
     * 窗口初始化
     */
    public void init(){
        // 设置错误返回
        GLFWErrorCallback.createPrint(System.err).set();

        // 初始化glfw
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // 设置glfw窗口
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // 创建窗口
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        // 将新建的窗口设置为opengl当前的上下文窗口
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
        Window.changeScene(0);

    }

    /**
     * 循环方法，控制每次渲染时的设置
     */
    public void loop(){
        float beginTime = Time.getTime();
        float endTime = Time.getTime();
        float dt = -1.0f;
        while (!glfwWindowShouldClose(glfwWindow)) {
            // 事件池
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);
            if(dt>=0) {
                currentScene.update(dt);
            }
            glfwSwapBuffers(glfwWindow);

            //一次loop的时间
            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

    }

}
