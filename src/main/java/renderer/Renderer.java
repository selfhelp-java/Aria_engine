package renderer;

import base.GameObject;
import components.SpriteRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer  {
    private final int MAX_BATCH_SIZE = 1000;
    /**
     * ���ǵ�һϵ�е�batch
     * just like
     * renderer->batches->batch->render
     */
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
            if (batch.hasRoom() && batch.zIndex() == sprite.gameObject.getzIndex()) {
                Texture tex = sprite.getTexture();
                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.gameObject.getzIndex());
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(sprite);
            Collections.sort(batches);
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
