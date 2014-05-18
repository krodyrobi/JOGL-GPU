package tests;

import java.util.TimerTask;


public class ScheduledTest extends Thread {


    private Tester tester;
    private ATestCase test;
    private long endTime;

    public ScheduledTest(Tester tester, ATestCase test, int miliSecPerTest) {
        this.test = test;
        this.tester = tester;


        long startTime = System.currentTimeMillis();
        endTime = startTime + miliSecPerTest;
    }

    public void run() {
        System.out.println("running " + System.currentTimeMillis() );

        tester.changeTestCase(test);

        while (System.currentTimeMillis() < endTime) {
            // Still within time threshHold, wait a little longer
            try {
                Thread.sleep(500L);  // Sleep 1/2 second
            } catch (InterruptedException e) {
                // Someone woke us up during sleep, that's OK
            }
        }

        System.out.println( "FINISHED " + System.currentTimeMillis() );
    }

    public String getResult() {
        return test.getResult();
    }
}
