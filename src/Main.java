import tests.ATestCase;
import tests.Tester;
import tests.mandelbrot.MandelbrotJOGL;
import tests.simple.SimpleScene;
import tests.stress.MonkeyTest;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        Tester tester = new Tester();


        List<ATestCase> testCases = new ArrayList<ATestCase>();
        testCases.add(new SimpleScene());
        testCases.add(new MandelbrotJOGL(tester));
        testCases.add(new MonkeyTest(tester));

        //for(ATestCase test: testCases) {

        //}

        tester.changeTestCase(testCases.get(1));
    }
}