/**
 * Created by Bodecrysis on 11/05/2014.
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;


public class gui {

    protected JFrame frmUptGpuBenchmark;

    /**
     * Launch the application.
     */


    /**
     * Create the application.
     */
    public gui() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmUptGpuBenchmark = new JFrame();
        frmUptGpuBenchmark.setTitle("UPT GPU Benchmark");
        frmUptGpuBenchmark.setBounds(100, 100, 627, 300);
        frmUptGpuBenchmark.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmUptGpuBenchmark.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        frmUptGpuBenchmark.getContentPane().add(panel);

        JButton btnTest1 = new JButton("Test 1");
        btnTest1.setHorizontalAlignment(SwingConstants.LEADING);
        btnTest1.setBounds(57, 81, 76, 23);
        btnTest1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.setLayout(null);
        panel.add(btnTest1);

        JButton btnTest2 = new JButton("Test 2");
        btnTest2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });
        btnTest2.setBounds(190, 81, 76, 23);
        panel.add(btnTest2);

        JButton btnTest3 = new JButton("Test  3");
        btnTest3.setBounds(309, 81, 89, 23);
        btnTest3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(btnTest3);

        JButton btnGetSysInfo = new JButton("Get Sys Info");
        btnGetSysInfo.setBounds(54, 209, 104, 23);
        btnGetSysInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });
        panel.add(btnGetSysInfo);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


            }
        });
        btnSubmit.setBounds(333, 209, 65, 23);
        panel.add(btnSubmit);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(436, 31, 165, 219);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel label = new JLabel("Test 1 : ");
        label.setBounds(20, 38, 40, 14);
        panel_1.add(label);

        JLabel label_1 = new JLabel("Test Results");
        label_1.setBounds(10, 5, 59, 14);
        panel_1.add(label_1);

        JLabel label_2 = new JLabel("Test 2 :");
        label_2.setBounds(20, 63, 37, 14);
        panel_1.add(label_2);

        JLabel label_3 = new JLabel("Test 3 :");
        label_3.setBounds(20, 88, 37, 14);
        panel_1.add(label_3);

        JTextArea textArea = new JTextArea();
        textArea.setBounds(65, 38, 59, 16);
        panel_1.add(textArea);

        JTextArea textArea_1 = new JTextArea();
        textArea_1.setBounds(67, 63, 57, 16);
        panel_1.add(textArea_1);

        JTextArea textArea_2 = new JTextArea();
        textArea_2.setBounds(67, 88, 57, 16);
        panel_1.add(textArea_2);

        JLabel label_4 = new JLabel("System Info");
        label_4.setBounds(20, 118, 58, 14);
        panel_1.add(label_4);

        JTextArea textArea_3 = new JTextArea();
        textArea_3.setBounds(20, 143, 135, 65);
        panel_1.add(textArea_3);

        JLabel lblUptGpuBenchmark = new JLabel("UPT GPU Benchmark");
        lblUptGpuBenchmark.setFont(new Font("Modern No. 20", Font.PLAIN, 16));
        lblUptGpuBenchmark.setBounds(180, 11, 151, 18);
        panel.add(lblUptGpuBenchmark);

    }
}