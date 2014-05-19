package tests.simple;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.Animator;
import tests.ATestCase;
import tests.Tester;
import utils.FPSCounter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Arc2D;


public class SimpleScene extends ATestCase implements GLEventListener {

    private GLCanvas canvas;
    private Frame frame;
    private Animator animator;

    private double theta = 0;
    private double s = 0;
    private double c = 0;
    private FPSCounter fpsCounter;

    public SimpleScene() {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        update();
        render(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();

        //Remove innate frameRate cap VSync
        gl2.setSwapInterval(0);

        //make fps counter update every 20 frames
        drawable.getAnimator().setUpdateFPSFrames(20, null);
        fpsCounter = new FPSCounter(drawable, 12);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
    }

    private void update() {
        theta += 0.01;
        s = Math.sin(theta);
        c = Math.cos(theta);
    }

    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();


        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // draw a triangle filling the window
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1, 0, 0);
        gl.glVertex2d(-c, -c);
        gl.glColor3f(0, 1, 0);
        gl.glVertex2d(0, c);
        gl.glColor3f(0, 0, 1);
        gl.glVertex2d(s, -s);
        gl.glEnd();

        fpsCounter.draw();
        result = String.valueOf( fpsCounter.getAvgFps());

        gl.glFlush();
    }



    public void run(final Tester tester) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);

        canvas = new GLCanvas(caps);
        frame = new Frame("JOGL GPU BENCHMARK");
        frame.setSize(500, 500);
        frame.add(canvas);

        animator = new Animator();
        animator.add(canvas);

        canvas.addGLEventListener(this);
        frame.setVisible(true);
        animator.start();


        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }.start();
            }
        });
    }

    public void dispose() {
        new Thread( new Runnable() {
            @Override
            public void run() {
                animator.stop();
                frame.dispose();
            }
        }).start();
    }
}