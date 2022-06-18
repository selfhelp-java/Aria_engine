package base;

import components.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * go类
 */
public class GameObject {
    private static int ID_COUNTER = 0;
    private int uid = -1;
    public String name;
    private List<Component> components;
    public Transform transform;
    private int zIndex;


    public GameObject(String name, Transform transform, int zIndex) {
        this.name = name;
        this.zIndex = zIndex;
        this.components = new ArrayList<>();
        this.transform = transform;
        this.uid = ID_COUNTER++;
    }

    /**
     * 获得当前go的component
     * @param componentClass
     * @param <T>
     * @return
     */
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    /**
     * 移除当前go的component
     * @param componentClass
     * @param <T>
     */
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    /**
     * 添加component
     * @param c
     */
    public void addComponent(Component c) {
        c.generateId();
        this.components.add(c);
        c.gameObject = this;
    }

    public List<Component> getAllComponents() {
        return this.components;
    }

    /**
     * 所有的compo更新
     * @param dt
     */
    public void update(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    /**
     * 所有的component开始运转
     */
    public void start() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public void imgui() {
        for (Component c : components) {
                c.imgui();
        }
    }

    public int uid() {
        return this.uid;
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }
}
