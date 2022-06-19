package base;

import org.joml.Vector2f;

/**
 * Transform��
 */
public class Transform {
    public Vector2f position;
    public Vector2f scale;
    public float rotation = 0.0f;


    /**
     * Transform���캯��
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
     * ����һ��transform��������
     * @return
     */
    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    /**
     * �����transform����Ϣ���Ƶ���һ��transform��
     * @param to
     */
    public void copy(Transform to) {
        to.position.set(this.position);
        to.scale.set(this.scale);
    }

    /**
     * �ж�����transform�Ƿ���ͬ
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
