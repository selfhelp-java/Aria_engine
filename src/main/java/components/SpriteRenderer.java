package components;

import base.Component;

public class SpriteRenderer extends Component {

    private boolean first = false;
    @Override
    public void start() {
        System.out.println("I am starting");
    }

    @Override
    public void update(float dt){
        if(!first){
            System.out.println("I am updating");
            first = true;
        }
    }
}
