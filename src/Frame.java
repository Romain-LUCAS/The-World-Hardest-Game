import javax.swing.*;

public class Frame extends JFrame {

    private final int FRAMESIZE = 817;

    public Frame(){
        super("The World Hardest Game");
        setSize(FRAMESIZE,FRAMESIZE+23);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
