import java.awt.*;

public class Trigger extends GameElements {
    public boolean isActivated = false;

    public String action;
    public String variation;
    private final Color TRIGGER = new Color(75,50,0);



    public Trigger() {
        super();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(TRIGGER);
        if (isVisible){
            g.fillRect((int) x, (int) y, (int) (x2 - x), (int) (y2 - y));
        }
    }

    @Override
    public void Collision(Character p, GamePanel gp) {
        if ((p.x > x - p.SIZE / 2.0 && p.x < x2 + p.SIZE / 2.0) && (p.y > y - p.SIZE / 2.0 && p.y < y2 + p.SIZE / 2.0)){
            activate();
        }
    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {
    }

    public Trigger(double x, double y, int width, int height, boolean isVisible, String action) {
        super(x,y,x + width, y + height,"None");
        this.isVisible = isVisible;
        this.action = action;
    }
    public Trigger(String action){
        isVisible = false;
        this.action = action;

    }

    public void activate() {
        if (!isActivated){
            isActivated = true;
            System.out.println("trigger activated");
        }
    }
    public void deactivate(){
        if (isActivated = true){
            isActivated = false;
        }
    }
}
