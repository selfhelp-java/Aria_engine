package base;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    private boolean isRunning;
    protected Camera camera;
    protected List<GameObject> gameObjects = new ArrayList<>();
    public Scene(){

    }
     public void init(){

     }

    /**
     * 当前场景开始
     * 所有go启动
     */
    public void start() {
        for (GameObject go : gameObjects) {
            go.start();
        }
        isRunning = true;
    }

    /**
     * 添加go到当前场景
     * @param go
     */
    public void addGameObjectToScene(GameObject go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
        }
    }

    public abstract void update(float dt);




}
