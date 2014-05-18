package tests;

import com.jogamp.opengl.util.Animator;
import tests.simple.SimpleScene;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class Tester {
    private ATestCase testCase;
    private List<ATestCase> tests;

    private GLCanvas canvas;
    private Animator animator;
    private Frame frame;

    public Tester() {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        tests = new ArrayList<ATestCase>();

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
        //animator.stop();
        canvas.removeGLEventListener(testCase);
        canvas.addGLEventListener(newTest);
        testCase = newTest;
        //animator.start();
    }

    public void addTestCase(ATestCase test) {
        tests.add(test);
    }

    public List<String> run(int miliSecPerTest) throws InterruptedException {
        List<String> results = new ArrayList<String>(tests.size());

        /*
        for(ATestCase test: tests) {
            Thread t = new ScheduledTest(this, test);

            long startTime = System.currentTimeMillis();
            long endTime = startTime + miliSecPerTest;

            t.start();

            while (System.currentTimeMillis() < endTime) {
                // Still within time threshHold, wait a little longer
                try {
                    Thread.sleep(500L);  // Sleep 1/2 second
                } catch (InterruptedException e) {
                    // Someone woke us up during sleep, that's OK
                }
            }

            t.interrupt();  // Tell the thread to stop
            t.join();       // Wait for the thread to cleanup and finish

            results.add(((ScheduledTest) t).getResult());
        }*/

        for(ATestCase test: tests) {
            Thread t = new ScheduledTest(this, test, miliSecPerTest);
            //changeTestCase(test);

            t.start();
            t.join();

            results.add(test.getResult());
        }

        frame.dispose();
        return results;
    }
}
