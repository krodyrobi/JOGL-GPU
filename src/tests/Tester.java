package tests;

import gui.Gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Tester {
    private List<ATestCase> tests;
    private int index;
    private List<String> results;
    private Gui gui;

    public Tester(Gui gui) {
        tests = new ArrayList<ATestCase>();
        results = new ArrayList<String>();
        index = -1;
        this.gui = gui;
    }

    public void addTest(ATestCase test) {
        tests.add(test);
    }

    public void run(final int delay) {
//        getNextTest().run(this);

        (new Thread() {
            @Override
            public void run() {
                for( ATestCase test: tests) {
                    ATestCase curTest = getNextTest();
                    if(curTest != null) {
                        curTest.run(Tester.this);
                        try {
                            Thread.sleep(delay);
                            results.add(curTest.getResult() + "");
                            test.dispose();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                gui.setResults(results);
            }
        }).start();
    }


    private ATestCase getNextTest() {
        if(++index < tests.size())
            return tests.get(index);


        return null;
    }

    public List<String> getResults() {
        return results;
    }
}
