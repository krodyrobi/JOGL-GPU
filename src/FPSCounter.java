import com.jogamp.opengl.util.awt.TextRenderer;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;


public class FPSCounter {
    // Placement constants
    public static final int UPPER_LEFT = 1;
    public static final int UPPER_RIGHT = 2;
    public static final int LOWER_LEFT = 3;
    public static final int LOWER_RIGHT = 4;

    private int textLocation = LOWER_RIGHT;
    private GLAutoDrawable drawable;
    private TextRenderer renderer;
    private DecimalFormat format = new DecimalFormat("#####.00");
    private int fpsMagnitude;
    private int fpsWidth;
    private int fpsHeight;
    private int fpsOffset;

    private double avgFps;


    public FPSCounter(GLAutoDrawable drawable, int textSize) throws GLException {
        this(drawable, new Font("SansSerif", Font.BOLD, textSize));
    }


    public FPSCounter(GLAutoDrawable drawable, Font font) throws GLException {
        this(drawable, font, true, true);
    }

    public FPSCounter(GLAutoDrawable drawable, Font font, boolean antialiased,
                      boolean useFractionalMetrics) throws GLException {
        this.drawable = drawable;
        renderer = new TextRenderer(font, antialiased, useFractionalMetrics);
    }

    public int getTextLocation() {
        return textLocation;
    }


    public void setTextLocation(int textLocation) {
        if (textLocation < UPPER_LEFT || textLocation > LOWER_RIGHT) {
            throw new IllegalArgumentException("textLocation");
        }
        this.textLocation = textLocation;
    }


    public void setColor(float r, float g, float b, float a) throws GLException {
        renderer.setColor(r, g, b, a);
    }


    public void draw() {
        double fps = drawable.getAnimator().getLastFPS();
        avgFps = (((avgFps == 0.f) ? fps : avgFps) + fps ) / 2;
        recomputeFPSSize((float) fps);

        String fpsText = "FPS: " + format.format(fps);

        renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
        int x = 0;
        int y = 0;
        switch (textLocation) {
            case UPPER_LEFT:
                x = fpsOffset;
                y = drawable.getHeight() - fpsHeight - fpsOffset;
                break;

            case UPPER_RIGHT:
                x = drawable.getWidth() - fpsWidth - fpsOffset;
                y = drawable.getHeight() - fpsHeight - fpsOffset;
                break;

            case LOWER_LEFT:
                x = fpsOffset;
                y = fpsOffset;
                break;

            case LOWER_RIGHT:
                x = drawable.getWidth() - fpsWidth - fpsOffset;
                y = fpsOffset;
                break;
        }
        renderer.draw(fpsText, x, y);
        renderer.endRendering();

    }

    private void recomputeFPSSize(float fps) {
        String fpsText;
        int fpsMagnitude;
        if (fps >= 10000) {
            fpsText = "10000.00";
            fpsMagnitude = 5;
        } else if (fps >= 1000) {
            fpsText = "1000.00";
            fpsMagnitude = 4;
        } else if (fps >= 100) {
            fpsText = "100.00";
            fpsMagnitude = 3;
        } else if (fps >= 10) {
            fpsText = "10.00";
            fpsMagnitude = 2;
        } else {
            fpsText = "9.00";
            fpsMagnitude = 1;
        }

        if (fpsMagnitude > this.fpsMagnitude) {
            Rectangle2D bounds = renderer.getBounds("FPS: " + fpsText);
            fpsWidth = (int) bounds.getWidth();
            fpsHeight = (int) bounds.getHeight();
            fpsOffset = (int) (fpsHeight * 0.5f);

            this.fpsMagnitude = fpsMagnitude;
        }
    }

    public double getAvgFps() {
        return avgFps;
    }
}
