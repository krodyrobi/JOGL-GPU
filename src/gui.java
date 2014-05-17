/**
 * Created by Bodecrysis on 11/05/2014.
 */
import com.jogamp.opengl.util.Animator;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;



public class gui {


    private IFrame frame;
    private JButton b1,b2,b3;
    private JPanel panel;
    private JLabel label;
    private JTextField t1,t2,t3,t4;


    public gui() {
        setgui();
    }

    public void setgui()  {

        frame = new IFrame();
        frame.setVisible(true);
        frame.setSize(800,800);

        panel = new JPanel();
        b1 = new JButton ("Run test 1");

        b2 = new JButton ("Run test 2 ");

        b3 = new JButton("run test 3");
        t1 = new JTextField("Test1 result");
        t2 = new JTextField("Test2 result");
        t3 = new JTextField("Test3 result");

        panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
//        layout.setHorizontalGroup(
//                layout.createSequentialGroup()
//                        .addComponent(b1)
//                        .addComponent(b2)
//                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                .addComponent(b3)
//                                .addComponent(t1))
//        );
//        layout.setVerticalGroup(
//                layout.createSequentialGroup()
//                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                                .addComponent(b1)
//                                .addComponent(b2)
//                                .addComponent(b3))
//                        .addComponent(t1)
//        );
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                   .addComponent(b1)
                   .addComponent(t1))
                .addGroup(layout.createParallelGroup()
                        .addComponent(b2)
                        .addComponent(t2))
                .addGroup(layout.createParallelGroup()
                        .addComponent(b3)
                        .addComponent(t3)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(b1)
                                .addComponent(b2)
                                .addComponent(b3))
                        .addGroup(layout.createParallelGroup()
                        .addComponent(t1)
                        .addComponent(t2)
                        .addComponent(t3)));



        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(t1);
        panel.add(t2);
        panel.add(t3);


        b1.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);


        GLCanvas canvas = new GLCanvas(caps);
        Frame frame1 = new Frame("AWT Window Test");
        frame1.setSize(300, 300);
        frame1.add(canvas);
        frame1.setVisible(true);

        frame1.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                Animator animator1 = new Animator();
                t1.setText("" + animator1.getTotalFPS()/animator1.getTotalFPSDuration());
            }
        });

        canvas.addGLEventListener(new SimpleScene());

        Animator animator = new Animator();
        animator.add(canvas);
        animator.start();
        animator.getTotalFPS();
        animator.getTotalFPSDuration();



                    }


                });
        b2.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e){

                    }


                });

        frame.add(panel);

        frame.pack();

        JOptionPane.showMessageDialog(null, "GPU Benchmark to see the true potential of your GPU");



    }

}