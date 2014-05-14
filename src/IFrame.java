/**
 * Created by Bodecrysis on 11/05/2014.
 */
/*Background image frame Module*/

import com.jogamp.openal.ALFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class IFrame extends JFrame
{

    public IFrame()  {
     //   JFrame with background image.
        setTitle("GPU Benchmark");
        setSize(800,800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        try {
            setContentPane(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/Images/gpu.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new FlowLayout());



//
    }

}