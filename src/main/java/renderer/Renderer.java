package renderer;

import base.GameObject;
import components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }


    /**
     * �����go����sprite��compo�������sprite��ӵ���Ⱦ��batch��
     * @param go
     */
    public void add(GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    /**
     * Ѱ���пռ��batch���sprite
     * ���û�У��򴴽�һ���µ�batch
     * @param sprite
     */
    private void add(SpriteRenderer sprite) {
        boolean added = false;
        for (RenderBatch batch : batches) {
            if (batch.hasRoom()) {
                batch.addSprite(sprite);
                added = true;
                break;

            }
        }
        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
        }
    }

    /**
     * �����е�batch��Ⱦ
     */
    public void render() {
        for (RenderBatch batch : batches) {
            batch.render();
        }
    }

}
