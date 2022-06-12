package base;

import renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Renderer renderer = new Renderer();
    private boolean isRunning;
    protected Camera camera;
    protected List<GameObject> gameObjects = new ArrayList<>();
    public Scene(){

    }
     public void init(){

     }

    /**
     * ��ǰ������ʼ
     * ����go����
     */
    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
            this.renderer.add(go);
        }
        isRunning = true;
    }

    /**
     * ���go����ǰ����
     * @param go
     */
    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
            this.renderer.add(go);
        }
    }

    public abstract void update(float dt);

    /**
     * ���س����������
     * @return
     */
    public Camera camera()
    {
        return this.camera;
    }


}
