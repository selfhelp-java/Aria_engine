package base;

import org.joml.Vector2f;

/**
 * Transform类
 */
public class Transform {
    public Vector2f position;
    public Vector2f scale;
    public float rotation = 0.0f;


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

    /**
     * 复制一份transform并返回它
     * @return
     */
    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    /**
     * 将这个transform的信息复制到另一个transform中
     * @param to
     */
    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    /**
     * 判断两个transform是否相同
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transform)) return false;

        Transform t = (Transform)o;
        return t.position.equals(this.position) && t.scale.equals(this.scale);
    }
}
