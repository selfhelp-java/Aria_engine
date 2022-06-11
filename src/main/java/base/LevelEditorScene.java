package base;


import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import renderer.Shader;

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
             0.5f, -0.5f, 0.0f,     1.0f, 0.0f, 0.0f, 1.0f,
            -0.5f,  0.5f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f,
             0.5f,  0.5f, 0.0f,     0.0f, 0.0f, 1.0f, 1.0f,
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f,
    };
    private  int[] elementArray ={
            2,1,0,
            0,1,3
    };

    private int vaoID, vboID, eboID;
    private Shader defaultshader;
    public LevelEditorScene() {
        Shader testShader = new Shader("assets/shaders/default.glsl");
    }

    /**
     * ��дinit����
     */
    @Override
    public void init(){
        defaultshader = new Shader("assets/shaders/default.glsl");
        defaultshader.compile();
        //���ز�����vertexshader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        //��shader���ݵ�GPU
        glShaderSource(vertexID, vertexShaderSrc);
        glCompileShader(vertexID);

        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERRPR:\n\tfail");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            assert false : "";
        }

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        //��shader���ݵ�GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        glCompileShader(fragmentID);

        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if(success == GL_FALSE) {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERRPR:\n\tfail");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            assert false : "";
        }

        //link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERRPR:\n\tfail");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }

        //����VAO,VBO,EBO
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);
        //����һ��float buffer��vertex
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        //����VBO����vertex buffer�ϴ���ȥ
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        //����indices
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);

    }

    @Override
    public void update(float dt) {
            defaultshader.use();
            glBindVertexArray(vaoID);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);

            glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

            //unbind
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);

            glBindVertexArray(0);
            defaultshader.detach();


    }
}
