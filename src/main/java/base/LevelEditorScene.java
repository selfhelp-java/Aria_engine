package base;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {

    private boolean changeScene = false;
    private float timeToChandeScene = 10.0f;
    public LevelEditorScene() {
        System.out.println("Inside level editor scene");
    }

    @Override
    public void update(float dt) {
        System.out.println("run at"+1/dt+"fps");
        if(!changeScene&&KeyListener.isKeyPressed(KeyEvent.VK_SPACE))
        {
            changeScene = true;
        }
        if( changeScene && timeToChandeScene >0){
            timeToChandeScene -= dt;
            Window.get().r -= dt;
            Window.get().g -= dt;
            Window.get().b -= dt;
        }
        else if(changeScene){
            Window.changeScene(1);
        }

    }
}
