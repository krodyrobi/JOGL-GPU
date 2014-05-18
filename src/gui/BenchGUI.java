package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;

/**
 * Created by Bodecrysis on 13/05/2014.
 */
public class BenchGUI {
    private JButton FPSTestButton;
    private JPanel panel1;
    private JButton tessellationButton;
    private JButton fractalButton;
    private JButton GPUInfo;
    private JButton submit;
    private JTextArea UPTGPUBenchmarkTextArea;

    public BenchGUI() {

        fractalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tessellationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        FPSTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        GPUInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
