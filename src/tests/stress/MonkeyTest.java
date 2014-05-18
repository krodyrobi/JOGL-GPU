package tests.stress;

import com.jogamp.common.nio.Buffers;
import lib.joglutils.model.ModelFactory;
import lib.joglutils.model.ModelLoadException;
import lib.joglutils.model.geometry.Mesh;
import lib.joglutils.model.geometry.Model;
import lib.joglutils.model.geometry.Vec4;
import tests.ATestCase;
import tests.Tester;
import utils.FPSCounter;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class MonkeyTest extends ATestCase {

    //private ObjImpScene scene;
    private FPSCounter fpsCounter;
    private Model model;
    private GLU glu = new GLU();
    //private iModel3DRenderer modelRenderer;

    private float[] lightAmbient = {0.3f, 0.3f, 0.3f, 1.0f};
    private float[] lightDiffuse = {0.5f, 0.5f, 0.5f, 1.0f};
    private float[] lightSpecular = {0.5f, 0.5f, 0.5f, 1.0f};

    private int[] aiVertexBufferIndices = new int[] {-1};

    public MonkeyTest(Tester tester) {
        this.tester = tester;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GL2 gl2 = gl.getGL2();


        //modelRenderer = DisplayListRenderer.getInstance();

        try {
            model = ModelFactory.createModel("src/models/monkey2.obj");

            model.centerModelOnPosition(true);
            model.setUseTexture(true);
            model.setRenderModelBounds(false);
            model.setRenderObjectBounds(false);
            model.setUnitizeSize(true);

            float radius = model.getBounds().getRadius();
        } catch (ModelLoadException e) {
            e.printStackTrace();
        }

        aiVertexBufferIndices = createAndFillVertexBuffer(gl2, model.getMesh(0));

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

        gl2.glColorMaterial( GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE );
        gl2.glEnable( GL2.GL_COLOR_MATERIAL );

        gl2.glEnable(GL2.GL_CULL_FACE);
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

        // draw all quads in vertex buffer
        gl.glBindBuffer( GL.GL_ARRAY_BUFFER, aiVertexBufferIndices[0] );
        gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
        gl.glEnableClientState( GL2.GL_COLOR_ARRAY );
        gl.glVertexPointer( 3, GL.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 0 );
        gl.glColorPointer( 3, GL.GL_FLOAT, 6 * Buffers.SIZEOF_FLOAT, 3 * Buffers.SIZEOF_FLOAT );
        gl.glPolygonMode( GL.GL_FRONT, GL2.GL_FILL );
        gl.glDrawArrays( GL2.GL_TRIANGLES, 0, model.getMesh(0).vertices.length );

        //fpsCounter.draw();
        result = "" + fpsCounter.getAvgFps();
        tester.setTitle("FPS: " + fpsCounter.getAvgFps() + " tri: " + model.getMesh(0).faces.length);
    }
    private void update(GLAutoDrawable drawable) {

    }
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;

        final float h = (float)width / (float)height;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -50, 50);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private int [] createAndFillVertexBuffer( GL2 gl2, Mesh mesh ) {


        // create vertex buffer object if needed
        if( aiVertexBufferIndices[0] == -1 ) {
            // check for VBO support
            if(    !gl2.isFunctionAvailable( "glGenBuffers" )
                    || !gl2.isFunctionAvailable( "glBindBuffer" )
                    || !gl2.isFunctionAvailable( "glBufferData" )
                    || !gl2.isFunctionAvailable( "glDeleteBuffers" ) ) {
                System.out.println("ERROR");
            }

            gl2.glGenBuffers( 1, aiVertexBufferIndices, 0 );

            // create vertex buffer data store without initial copy
            gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, aiVertexBufferIndices[0] );
            gl2.glBufferData( GL.GL_ARRAY_BUFFER,
                    mesh.numOfVerts * 3 * Buffers.SIZEOF_FLOAT * 2,
                    null,
                    GL2.GL_DYNAMIC_DRAW );
        }

        // map the buffer and write vertex and color data directly into it
        gl2.glBindBuffer( GL.GL_ARRAY_BUFFER, aiVertexBufferIndices[0] );
        ByteBuffer bytebuffer = gl2.glMapBuffer( GL.GL_ARRAY_BUFFER, GL2.GL_WRITE_ONLY );
        FloatBuffer floatbuffer = bytebuffer.order( ByteOrder.nativeOrder() ).asFloatBuffer();

        for(int i=0; i<mesh.numOfVerts; i++) {
            Vec4 vert = mesh.vertices[i];

            floatbuffer.put(vert.x);
            floatbuffer.put(vert.y);
            floatbuffer.put(vert.z);

            floatbuffer.put(0.5f);
            floatbuffer.put(0.5f);
            floatbuffer.put(0.5f);
        }

        gl2.glUnmapBuffer( GL.GL_ARRAY_BUFFER );

        return aiVertexBufferIndices;
    }
}
