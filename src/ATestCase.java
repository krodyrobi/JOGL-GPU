import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

abstract class ATestCase implements GLEventListener, Benchmark {
    protected String result;

    public String getResult() {
        return result;
    }

    public abstract void init(GLAutoDrawable glAutoDrawable);
    public abstract void dispose(GLAutoDrawable glAutoDrawable);
    public abstract void display(GLAutoDrawable glAutoDrawable);
    public abstract void reshape(GLAutoDrawable glAutoDrawable, int i, int i2, int i3, int i4);
}
