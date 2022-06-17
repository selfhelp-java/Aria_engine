package components;

import base.Component;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;
    // 0 1
    // 0 0
    // 1 1
    // 1 0
    public SpriteRenderer(Vector4f color){
        this.color = color;
        this.sprite = new Sprite(null);
    }

    public  SpriteRenderer(Sprite sprite){
        this.sprite = sprite;
        this.color = new Vector4f(1, 1, 1, 1);
    }



    @Override
    public void start() {

    }

    @Override
    public void update(float dt){

    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

}