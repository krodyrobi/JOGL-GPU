
import gui.*;
import java.awt.*;

public class Main {
    public static void main(String args[]) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Gui gui = new Gui();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}