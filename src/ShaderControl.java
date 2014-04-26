import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;


//TODO SEE IF NEEDED
class ShaderControl {
    private int shaderprogram;
    public String[] vsrc;
    public String[] fsrc;

    public void init(GL gl) {
        try {
            attachShaders(gl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] loadShader(String name) {
        StringBuilder sb = new StringBuilder();

        try {
            InputStream is = getClass().getResourceAsStream(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Shader is " + sb.toString());
        return new String[]
                {sb.toString()};
    }

    private void attachShaders(GL gl) throws Exception {
        GL2 gl2 = gl.getGL2();
        int vertexShaderProgram = gl2.glCreateShader(GL2.GL_VERTEX_SHADER);
        int fragmentShaderProgram = gl2.glCreateShader(GL2.GL_FRAGMENT_SHADER);


        gl2.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
        gl2.glCompileShader(vertexShaderProgram);
        gl2.glShaderSource(fragmentShaderProgram, 1, fsrc, null, 0);
        gl2.glCompileShader(fragmentShaderProgram);
        shaderprogram = gl2.glCreateProgram();

        gl2.glAttachShader(shaderprogram, vertexShaderProgram);
        gl2.glAttachShader(shaderprogram, fragmentShaderProgram);
        gl2.glLinkProgram(shaderprogram);
        gl2.glValidateProgram(shaderprogram);

        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl2.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS, intBuffer);

        if (intBuffer.get(0) != 1) {
            gl2.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH, intBuffer);
            int size = intBuffer.get(0);
            System.err.println("Program link error: ");

            if (size > 0) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl2.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);

                for (byte b : byteBuffer.array()) {
                    System.err.print((char) b);
                }

            } else {
                System.out.println("Unknown");
            }

            System.exit(1);
        }
    }

    public int useShader(GL gl) {
        gl.getGL2().glUseProgram(shaderprogram);
        return shaderprogram;
    }

    public void dontUseShader(GL gl) {
        gl.getGL2().glUseProgram(0);
    }
}
