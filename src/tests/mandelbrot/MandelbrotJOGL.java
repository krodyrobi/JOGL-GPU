package tests.mandelbrot;


import tests.ATestCase;
import tests.Tester;
import utils.FPSCounter;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

public class MandelbrotJOGL extends ATestCase {
    private boolean updateUniformVars = true;
    private int vertexShaderProgram;
    private int fragmentShaderProgram;
    private int shaderprogram;

    private MandelbrotAnimation anim = new MandelbrotAnimation();
    private MandelbrotSetting settings = anim.getSetting();

    //private FPSStat fpsStat = new FPSStat();
    private FPSCounter fpsCounter;


    public MandelbrotJOGL(Tester tester) {
        this.tester = tester;
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        //Remove innate frameRate cap VSync
        gl2.setSwapInterval(0);

        //make fps counter update every 20 frames
        drawable.getAnimator().setUpdateFPSFrames(20, null);

        gl2.glShadeModel(GL2.GL_FLAT);
        try {
            attachShaders(gl2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fpsCounter = new FPSCounter(drawable, 12);
        anim.start();
        System.out.println("Loaded mandelbrot test");
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    private String[] loadShaderSrc(String name){
        StringBuilder sb = new StringBuilder();
        try{
            InputStream is = getClass().getResourceAsStream(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine())!=null){
                sb.append(line);
                sb.append('\n');
            }
            is.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new String[]{sb.toString()};
    }

    private void attachShaders(GL2 gl) throws Exception {
        vertexShaderProgram = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
        fragmentShaderProgram = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);

        String[] vsrc = loadShaderSrc("mandelbrot.vs");
        gl.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
        gl.glCompileShader(vertexShaderProgram);

        String[] fsrc = loadShaderSrc("mandelbrot.fs");
        gl.glShaderSource(fragmentShaderProgram, 1, fsrc, null, 0);
        gl.glCompileShader(fragmentShaderProgram);

        shaderprogram = gl.glCreateProgram();
        gl.glAttachShader(shaderprogram, vertexShaderProgram);
        gl.glAttachShader(shaderprogram, fragmentShaderProgram);
        gl.glLinkProgram(shaderprogram);
        gl.glValidateProgram(shaderprogram);
        IntBuffer intBuffer = IntBuffer.allocate(1);
        gl.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS,intBuffer);
        if (intBuffer.get(0)!=1){
            gl.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH,intBuffer);
            int size = intBuffer.get(0);
            System.err.println("Program link error: ");
            if (size>0){
                ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                gl.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);
                for (byte b:byteBuffer.array()){
                    System.err.print((char)b);
                }
            } else {
                System.out.println("Unknown error");
            }
            System.exit(1);
        }
        gl.glUseProgram(shaderprogram);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();
        GLU glu = new GLU();

        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        gl2.glViewport(0, 0, width, height);
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        //fpsStat.timerStart();
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();

        if (updateUniformVars){
            updateUniformVars(gl2);
        }

        // Reset the current matrix to the "identity"
        gl2.glLoadIdentity();

        // Draw A Quad
        gl2.glBegin(GL2.GL_QUADS);

            gl2.glTexCoord2f(0.0f, 0.0f);
            gl2.glVertex3f(0.0f, 1.0f, 1.0f);
            gl2.glTexCoord2f(1.0f, 0.0f);
            gl2.glVertex3f(1.0f, 1.0f, 1.0f);
            gl2.glTexCoord2f(1.0f, 1.0f);
            gl2.glVertex3f(1.0f, 0.0f, 1.0f);
            gl2.glTexCoord2f(0.0f, 1.0f);
            gl2.glVertex3f(0.0f, 0.0f, 1.0f);

        // Done Drawing The Quad
        gl2.glEnd();

        // Flush all drawing operations to the graphics card
        gl2.glFlush();
        //fpsStat.timerEnd();

        String s = String.format("AVG FPS: %4.1f using %s algorithm iterations", fpsCounter.getAvgFps(), settings.getIterations());
        tester.setTitle(s);
    }

    private void updateUniformVars(GL2 gl) {
        // get memory address of uniform shader variables
        int mandel_x = gl.glGetUniformLocation(shaderprogram, "mandel_x");
        int mandel_y = gl.glGetUniformLocation(shaderprogram, "mandel_y");
        int mandel_width = gl.glGetUniformLocation(shaderprogram, "mandel_width");
        int mandel_height = gl.glGetUniformLocation(shaderprogram, "mandel_height");
        int mandel_iterations = gl.glGetUniformLocation(shaderprogram, "mandel_iterations");

        // set uniform shader variables
        gl.glUniform1f(mandel_x, settings.getX());
        gl.glUniform1f(mandel_y, settings.getY());
        gl.glUniform1f(mandel_width, settings.getWidth());
        gl.glUniform1f(mandel_height, settings.getHeight());
        gl.glUniform1f(mandel_iterations, settings.getIterations());

    }

    public FPSCounter getFpsCounter() {
        return fpsCounter;
    }
}
