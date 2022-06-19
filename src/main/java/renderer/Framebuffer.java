package renderer;

import static org.lwjgl.opengl.GL30.*;

/**
 * ֡����
 * ����д����ɫֵ����ɫ���塢����д�������Ϣ����Ȼ�����������Ǹ���һЩ���������ض�Ƭ�ε�ģ�建�塣��Щ��������������֡����(Framebuffer)�������������ڴ��С�OpenGL�������Ƕ��������Լ���֡���壬Ҳ����˵�����ܹ����������Լ�����ɫ���壬��������Ȼ����ģ�建�塣
 *
 * ����Ŀǰ���������в���������Ĭ��֡�������Ⱦ�����Ͻ��еġ�Ĭ�ϵ�֡���������㴴�����ڵ�ʱ�����ɺ����õģ�GLFW������������Щ�������������Լ���֡���壬���Ǿ��ܹ��и��෽ʽ����Ⱦ�ˡ�
 */
public class Framebuffer {
    private int fboID = 0;
    private Texture texture = null;

    public Framebuffer(int width, int height) {
        // Generate framebuffer
        fboID = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);

        // Create the texture to render the data to, and attach it to our framebuffer
        this.texture = new Texture(width, height);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
                this.texture.getId(), 0);

        // Create renderbuffer store the depth info
        int rboID = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, rboID);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, width, height);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, rboID);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            assert false : "Error: Framebuffer is not complete";
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, fboID);
    }

    public void unbind() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public int getFboID() {
        return fboID;
    }

    public int getTextureId() {
        return texture.getId();
    }
}
