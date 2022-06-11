package base;


import Util.Time;
import components.SpriteRenderer;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import renderer.Shader;
import renderer.Texture;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";
    private  int vertexID, fragmentID, shaderProgram;

    private  float[] vertexArray ={
            //position              //color
             100f,    0f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f,     1, 1,
               0f,  100f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f,     0, 0,
             100f,  100f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f,     1, 0,
               0f,     0f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f,     0, 1
    };
    private  int[] elementArray ={
            2,1,0,
            0,1,3
    };

    private int vaoID, vboID, eboID;
    private Shader defaultShader;
    private Texture testTexture;

    GameObject testObj;

    public LevelEditorScene() {
        Shader testShader = new Shader("assets/shaders/default.glsl");
    }

    /**
     * 重写init方法
     */
    @Override
    public void init(){
        System.out.println("creating");
        this.testObj = new GameObject("test obj");
        this.testObj.addComponent(new SpriteRenderer());
        this.addGameObjectToScene(this.testObj);
        this.camera = new Camera(new Vector2f(-200, -300));
        defaultShader = new Shader("assets/shaders/default.glsl");
        defaultShader.compile();
        this.testTexture = new Texture("assets/images/testImage.png");


        //生成VAO,VBO,EBO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        //创建一个float buffer给vertex
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        //创建VBO并把vertex buffer上传进去
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        //创建indices
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize +uvSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (positionSize + colorSize)*Float.BYTES);
        glEnableVertexAttribArray(2);

    }

    @Override
    public void update(float dt) {
            defaultShader.use();
            defaultShader.uploadTexture("TEX_SAMPLER", 0);
            glActiveTexture(GL_TEXTURE0);
            testTexture.bind();
            defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
            defaultShader.uploadMat4f("uView", camera.getViewMatrix());
            defaultShader.uploadFloat("uTime", Time.getTime());
            glBindVertexArray(vaoID);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

            //unbind
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);

            glBindVertexArray(0);
            defaultShader.detach();

            for(GameObject go : this.gameObjects){
                go.update(dt);
            }


    }
}
