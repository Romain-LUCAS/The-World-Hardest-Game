import javax.swing.*;
import java.lang.reflect.GenericArrayType;

public class Main {
    private static Frame frame = new Frame();
    public static GamePanel myPanel;

    public static void main(String[] args) {

        myPanel= new GamePanel();
        frame.add(myPanel);
        frame.setVisible(true);

    }
}
