package tests;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

public abstract class ATestCase implements GLEventListener, Benchmark {
    protected String result;
    public Tester tester;

    public String getResult() {
        return result;
    }

    public abstract void init(GLAutoDrawable glAutoDrawable);
    public abstract void dispose(GLAutoDrawable glAutoDrawable);
    public abstract void display(GLAutoDrawable glAutoDrawable);
    public abstract void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height);
    public abstract void run(Tester tester);
    public abstract void dispose();
}
