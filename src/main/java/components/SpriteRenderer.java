package components;

import base.Transform;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

/**
 * ����go����Ⱦ�ؼ�
 */
public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    //��isDirty����ʾgo����Ⱦ�Ƿ����仯
    private transient boolean isDirty = true;
    // 0 1
    // 0 0
    // 1 1
    // 1 0
//    public SpriteRenderer(Vector4f color){
//        this.color = color;
//        this.sprite = new Sprite(null);
//        this.isDirty = true;
//    }
//
//    public  SpriteRenderer(Sprite sprite){
//        this.sprite = sprite;
//        this.color = new Vector4f(1, 1, 1, 1);
//        this.isDirty = true;
//    }



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

    @Override
    public void imgui() {
        float[] imColor = {color.x, color.y, color.z, color.w};
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            this.isDirty = true;
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
     * �жϵ�ǰgo��Ⱦ�Ƿ�ı�
     * @return
     */
    public boolean isDirty() {
        return this.isDirty;
    }

    /**
     * �����ǰgo����Ⱦ�ı�״̬
     */
    public void setClean() {
        this.isDirty = false;
    }

    /**
     * ���õ�ǰ������
     * @param texture
     */
    public void setTexture(Texture texture) {
        this.sprite.setTexture(texture);
    }
}
