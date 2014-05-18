package tests;

import com.jogamp.opengl.util.Animator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tester {
    private ATestCase testCase;

    private GLCanvas canvas;
    private Animator animator;
    private Frame frame;

    public Tester() {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);


        canvas = new GLCanvas(caps);
        frame = new Frame("JOGL GPU BENCHMARK");
        frame.setSize(500, 500);
        frame.add(canvas);
        frame.setVisible(true);

        animator = new Animator();
        animator.add(canvas);
        animator.start();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        stopAnimator();
                        System.exit(0);
                    }
                }.start();
            }
        });
    }

    public void setTitle(String s) {
        frame.setTitle(s);
    }

    private void stopAnimator() {
        animator.stop();
    }

    public void changeTestCase(ATestCase newTest) {
        canvas.removeGLEventListener(testCase);
        canvas.addGLEventListener(newTest);
        testCase = newTest;
    }
}
