package base;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {


    public LevelEditorScene() {

    }

    /**
     * ��дinit����
     */
    @Override
    public void init(){
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));

        Spritesheet sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");


        GameObject obj = new GameObject("obj1", new Transform(new Vector2f(100,100), new Vector2f(256, 256)));
        obj.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj);
        loadResources();

        GameObject obj2 = new GameObject("obj1", new Transform(new Vector2f(400,100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(10)));
        this.addGameObjectToScene(obj2);


    }

    private void loadResources(){
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/spritesheet.png",new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),16,16,26,0));
    }
    @Override
    public void update(float dt) {


            for(GameObject go : this.gameObjects){
                go.update(dt);
            }

            this.renderer.render();


    }
}
