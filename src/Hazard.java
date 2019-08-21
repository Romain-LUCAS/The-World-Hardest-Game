import java.awt.*;

public class Hazard extends GameElements {
    private final Color MUD = new Color(153, 102, 0);
    private final Color WATER = new Color(51, 204, 255);

    public Hazard(){
        super();
    }

    @Override
    public void paint(Graphics g) {
        if (interaction.equals("Slow"))
            g.setColor(MUD);
        else if (interaction.equals("Kill")) {
            g.setColor(WATER);
        } else {
            g.setColor(Color.black);
        }
        g.fillRect((int) x, (int) y, (int) (x2 - x), (int) (y2 - y));
    }

    @Override
    public void Collision(Character p, GamePanel gp) {
        if ((p.x > x - p.SIZE / 2.0 && p.x < x2 + p.SIZE / 2.0) && (p.y > y - p.SIZE / 2.0 && p.y < y2 + p.SIZE / 2.0)){
            if (interaction.equals("Kill")) {
                gp.currentLevel.loadLevelX(gp.level);
                gp.reset();
            } else if (interaction.equals("Slow")) {
                p.velx /= 1.5;
                p.vely /= 1.5;
            }
        }
    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {

    }

    public Hazard(double x1, double y1, int width, int height,String interact){
        super(x1, y1, x1 + width, y1 + height, interact);
    }

}
