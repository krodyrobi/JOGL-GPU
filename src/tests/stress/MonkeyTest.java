package tests.stress;

import com.jogamp.opengl.util.Animator;
import lib.joglutils.model.DisplayListRenderer;
import lib.joglutils.model.ModelFactory;
import lib.joglutils.model.ModelLoadException;
import lib.joglutils.model.geometry.Model;
import lib.joglutils.model.iModel3DRenderer;
import tests.ATestCase;
import tests.Tester;
import utils.FPSCounter;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MonkeyTest extends ATestCase {
    private  GLCanvas canvas;
    private  Frame frame;
    private  Animator animator;

    //private ObjImpScene scene;
    private FPSCounter fpsCounter;
    private Model model;
    private GLU glu = new GLU();
    private iModel3DRenderer modelRenderer;

    private float[] lightAmbient = {0.3f, 0.3f, 0.3f, 1.0f};
    private float[] lightDiffuse = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f};

    public MonkeyTest(Tester tester) {
        this.tester = tester;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();


        modelRenderer = DisplayListRenderer.getInstance();

        try {
            model = ModelFactory.createModel("src/models/monkey.obj");

            model.centerModelOnPosition(true);
            model.setUseTexture(true);
            model.setRenderModelBounds(false);
            model.setRenderObjectBounds(false);
            model.setUnitizeSize(true);

            float radius = model.getBounds().getRadius();
        } catch (ModelLoadException e) {
            e.printStackTrace();
        }


        // Set the light
        float lightPosition[] = { 0, 50000000, 0, 1.0f };
        float[] model_ambient = {0.5f, 0.5f, 0.5f, 1.0f};

        gl2.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, model_ambient, 0);
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightDiffuse, 0);
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightAmbient, 0);
        gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightSpecular, 0);

        gl2.glEnable(GL2.GL_LIGHT0);
        gl2.glEnable(GL2.GL_LIGHTING);
        gl2.glEnable(GL2.GL_NORMALIZE);

        //gl2.glEnable(GL2.GL_CULL_FACE);
        gl2.glShadeModel(GL2.GL_SMOOTH);
        gl2.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl2.glClearDepth(1.0f);
        gl2.glEnable(GL2.GL_DEPTH_TEST);
        gl2.glDepthFunc(GL2.GL_LEQUAL);
        gl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glPushMatrix();

        //Remove innate frameRate cap VSync
        gl2.setSwapInterval(0);

        //make fps counter update every 20 frames
        drawable.getAnimator().setUpdateFPSFrames(20, null);
        fpsCounter = new FPSCounter(drawable, 12);
        System.out.println("Loaded monkey test");
    }


    public void dispose(GLAutoDrawable drawable) {

    }

    public void display(GLAutoDrawable drawable) {
        update(drawable);
        render(drawable);
    }

    private void render(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        glu.gluLookAt(0,0,10, 0,0,0, 0,1,0);


        // Draw the scene (by default, the lighting, material and textures
        // are enabled/disabled within the renderer for the model)
        gl.glPushMatrix();
            // Render the model
            modelRenderer.render(gl, model);
        gl.glPopMatrix();

        gl.glFlush();

        //fpsCounter.draw();
        result = "" + fpsCounter.getAvgFps();
        frame.setTitle("FPS: " + fpsCounter.getAvgFps() + " tri: " + model.getMesh(0).faces.length);
    }

    private void update(GLAutoDrawable drawable) {

    }

    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;

        final float h = (float) width / (float) height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -50, 50);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
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



        System.out.println("Simplescene");


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
