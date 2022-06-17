package components;

import base.AssetPool;
import base.Component;
import base.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

/**
 * 控制go的渲染控件
 */
public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;

    private transient Transform lastTransform;
    //用isDirty来表示go的渲染是否发生变化
    private transient boolean isDirty = true;
    // 0 1
    // 0 0
    // 1 1
    // 1 0
    public SpriteRenderer(Vector4f color){
        this.color = color;
        this.sprite = new Sprite(null);
        this.isDirty = true;
    }

    public  SpriteRenderer(Sprite sprite){
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
        this.isDirty = true;
    }



    /**
     *
     */
    @Override
    public void start() {
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt){
        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }

    }

    public Vector4f getColor() {
        return color;
    }


    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if (!this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    /**
     * 判断当前go渲染是否改变
     * @return
     */
    public boolean isDirty() {
        return this.isDirty;
    }

    /**
     * 清除当前go的渲染改变状态
     */
    public void setClean() {
        this.isDirty = false;
    }
}
