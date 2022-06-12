package base;

import org.joml.Vector2f;

/**
 * Transform类
 */
public class Transform {
    public Vector2f position;
    public Vector2f scale;


    /**
     * Transform构造函数
     */
    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f());
    }

    public Transform(Vector2f position, Vector2f scale) {
        init(position, scale);
    }

    public void init(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }
}
