package gui;


import lib.joglutils.model.ModelFactory;
import lib.joglutils.model.ModelLoadException;
import lib.joglutils.model.geometry.Model;
import tests.Tester;
import tests.mandelbrot.MandelbrotJOGL;
import tests.simple.SimpleScene;
import tests.stress.MonkeyTest;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Gui {

    protected JFrame frmUptGpuBenchmark;

    private JTextArea textArea;
    private JTextArea textArea_1;
    private JTextArea textArea_2;
    private JTextArea textArea_3;
    private JButton btnGetSysInfo;

    private boolean ranDxdiag;

    /**
     * Launch the application.
     */


    /**
     * Create the application.
     */
    public Gui() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmUptGpuBenchmark = new JFrame();
        frmUptGpuBenchmark.setTitle("UPT GPU Benchmark");
        frmUptGpuBenchmark.setBounds(100, 100, 557, 300);
        frmUptGpuBenchmark.setResizable(false);
        frmUptGpuBenchmark.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmUptGpuBenchmark.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));


        JPanel panel_1 = new JPanel();
        panel_1.setBounds(215, 31, 323, 219);
        panel_1.setLayout(null);


        JLabel label = new JLabel("Test 1 : ");
        label.setBounds(20, 38, 64, 14);
        panel_1.add(label);

        JLabel label_1 = new JLabel("Test Results");
        label_1.setBounds(10, 5, 88, 14);
        panel_1.add(label_1);

        JLabel label_2 = new JLabel("Test 2 :");
        label_2.setBounds(20, 63, 64, 14);
        panel_1.add(label_2);

        JLabel label_3 = new JLabel("Test 3 :");
        label_3.setBounds(20, 88, 64, 14);
        panel_1.add(label_3);

        textArea = new JTextArea();
        textArea.setBounds(144, 38, 157, 16);
        panel_1.add(textArea);

        textArea_1 = new JTextArea();
        textArea_1.setBounds(144, 63, 157, 16);
        panel_1.add(textArea_1);

        textArea_2 = new JTextArea();
        textArea_2.setBounds(144, 88, 157, 16);
        panel_1.add(textArea_2);

        JLabel label_4 = new JLabel("System Info");
        label_4.setBounds(20, 118, 78, 14);
        panel_1.add(label_4);

        textArea_3 = new JTextArea();
        textArea_3.setBounds(20, 134, 281, 74);
        panel_1.add(textArea_3);

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        frmUptGpuBenchmark.getContentPane().add(panel);

        JButton btnTest1 = new JButton("Start Test");
        btnTest1.setHorizontalAlignment(SwingConstants.LEADING);
        btnTest1.setBounds(57, 81, 76, 23);
        btnTest1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Tester tester = new Tester(Gui.this);

                tester.addTest(new SimpleScene());
                tester.addTest(new MandelbrotJOGL(tester));

                try {
                    String path = new File("").getAbsolutePath();
                    Model model = ModelFactory.createModel(path + "/models/monkey.obj");
                    tester.addTest(new MonkeyTest(tester, model));
                } catch (ModelLoadException ex) {
                    ex.printStackTrace();
                }


                List<String> results = new ArrayList<String>(3);

                tester.run(30000);
            }
        });
        panel.setLayout(null);
        panel.add(btnTest1);



        btnGetSysInfo = new JButton("Get Sys Info");
        btnGetSysInfo.setBounds(57, 134, 104, 23);
        btnGetSysInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ranDxdiag = true;
                    String dir = System.getProperty("java.io.tmpdir");
                    String filePath = dir + "diag.txt";
                    System.out.println(filePath);
                    // Use "dxdiag /t" variant to redirect output to a given file

                    ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","dxdiag","/t",filePath);
                    System.out.println("-- Executing dxdiag command --");
                    Process p = pb.start();
                    p.waitFor();

                    System.out.println(new File(filePath).getAbsolutePath());

                    BufferedReader br = new BufferedReader(new FileReader(filePath));
                    String line;
                    System.out.println(String.format("-- Printing %1$1s info --",filePath));
                    while((line = br.readLine()) != null){
                        line = line.trim();

                        if(line.startsWith("Card name:")
                                || line.startsWith("Current Mode:")
                                || line.startsWith("Display Memory:")
                                || line.startsWith("Operating System:")
                                || line.startsWith("DirectX Version:")
                                || line.startsWith("Processor:")
                                || line.startsWith("Memory:")
                                || line.startsWith("Time of this report:")
                                ){
                            textArea_3.append(line + "\n");
                        }

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(btnGetSysInfo);


        panel.add(panel_1);

        JLabel lblUptGpuBenchmark = new JLabel("UPT GPU Benchmark");
        lblUptGpuBenchmark.setFont(new Font("Modern No. 20", Font.PLAIN, 16));
        lblUptGpuBenchmark.setBounds(167, 11, 151, 18);
        panel.add(lblUptGpuBenchmark);

        frmUptGpuBenchmark.setVisible(true);
    }

    public void setResults(List<String> results) {
        textArea.setText(results.get(0));
        textArea_1.setText(results.get(1));
        textArea_2.setText(results.get(2));

        if(!ranDxdiag) {
            btnGetSysInfo.doClick();
        }


        double r1 = (results.get(0) == null) ? 0 : Double.parseDouble(results.get(0));
        double r2 = (results.get(1) == null) ? 0 : Double.parseDouble(results.get(1));
        double r3 = (results.get(2) == null) ? 0 : Double.parseDouble(results.get(2));


        double result = (r1 + r2 * 100 + r3 * 1000) / 3;


        String output = "";
        output += "Your final score is ";
        output += String.format("%10.1f \n", result);
        output += "Please send the results to krody.robi@gmail.com \n\n";
        output += "ALONG WITH THE SYSTEM SPECS :D.";


        JOptionPane.showMessageDialog(frmUptGpuBenchmark, output);
    }
}