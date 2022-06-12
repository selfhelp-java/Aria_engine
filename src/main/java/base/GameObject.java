package base;

import java.util.ArrayList;
import java.util.List;

/**
 * go��
 */
public class GameObject {
    public String name;
    private List<Component> components;
    public Transform transform;
    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
    }

    public GameObject(String name, Transform transform) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
    }

    /**
     * ��õ�ǰgo��component
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
     * �Ƴ���ǰgo��component
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
     * ���component
     * @param c
     */
    public void addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
    }

    /**
     * ���е�compo����
     * @param dt
     */
    public void update(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    /**
     * ���е�component��ʼ��ת
     */
    public void start() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }
}
