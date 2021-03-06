package base;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import renderer.*;
import scene.LevelEditorScene;
import scene.LevelScene;
import scene.Scene;

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
    private ImGuiLayer imguiLayer;
    public float r,g,b,a;
    private static Scene currentScene;
    private Framebuffer framebuffer;
    private PickingTexture pickingTexture;

    private Window(){
        this.width = 1920;
        this.height = 1080;
        this.title = "Aria";
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
    }

    /**
     * 切换场景
     * @param newScene
     */
    public static void changeScene(int newScene){
        switch(newScene){
            case 0 :
                currentScene = new LevelEditorScene();

                break;
            case 1 :
                currentScene = new LevelScene();

                break;
            default:
                assert false : "unknown scene";
                break;
        }
        currentScene.load();
        currentScene.init();
        currentScene.start();
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

    public static Scene getScene(){
        return get().currentScene;
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
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });
        // 将新建的窗口设置为opengl当前的上下文窗口
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);


        this.framebuffer = new Framebuffer(3840, 2160);
        this.pickingTexture = new PickingTexture(3840, 2160);
        glViewport(0, 0, 3840, 2160);

        this.imguiLayer = new ImGuiLayer(glfwWindow, pickingTexture);
        this.imguiLayer.initImGui();

        Window.changeScene(0);

    }

    /**
     * 循环方法，控制每次渲染时的设置
     */
    public void loop(){
        float beginTime = (float)glfwGetTime();
        float endTime = (float)glfwGetTime();
        float dt = -1.0f;

        Shader defaultShader = AssetPool.getShader("assets/shaders/default.glsl");
        Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");
        while (!glfwWindowShouldClose(glfwWindow)) {
            // 事件池
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);
            pickingTexture.enableWriting();

            glViewport(0, 0, 3840, 2160);
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Renderer.bindShader(pickingShader);
            currentScene.render();


            pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            DebugDraw.beginFrame();

            this.framebuffer.bind();
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            this.framebuffer.bind();
            if(dt>=0) {
                DebugDraw.draw();
                Renderer.bindShader(defaultShader);
                currentScene.update(dt);
                currentScene.render();
            }
            this.framebuffer.unbind();

            this.imguiLayer.update(dt,currentScene);

            glfwSwapBuffers(glfwWindow);
            MouseListener.endFrame();
            //一次loop的时间
            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }

        currentScene.saveExit();

    }

    public static int getWidth() {
        return  get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static Framebuffer getFramebuffer() {
        return get().framebuffer;
    }

    public static float getTargetAspectRatio() {
        return 16.0f / 9.0f;
    }

    public static ImGuiLayer getImguiLayer() {
        return get().imguiLayer;
    }

}
