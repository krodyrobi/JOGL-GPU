import com.jogamp.opengl.util.Animator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Tester {
    private ATestCase testCase;
//    private Ui mainScreen;

    private GLCanvas canvas;
    private Animator animator;


    //TODO make a reference to the main result screen
    public Tester(/*Ui mainScreen,*/ ATestCase testCase) {
//        this.mainScreen = mainScreen;
        this.testCase = testCase;

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);


        canvas = new GLCanvas(caps);
        Frame frame = new Frame("AWT Window Test");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);

        canvas.addGLEventListener(testCase);

        animator = new Animator();
        animator.add(canvas);
        animator.start();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Thread() {
                    @Override
                    public void run() {
                        stopAnimator();
                        System.out.println(testCase.getResult());
                        System.exit(0);
                    }
                }.start();
            }
        });
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
