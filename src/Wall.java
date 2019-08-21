import java.awt.*;

public class Wall extends GameElements {
    private final Color WALLCOLOR = Color.darkGray;

    public Wall() {
        super();

    }

    public Wall(double x, double y, double x2, double y2) {
        super(x, y);
        this.x2 = x2;
        this.y2 = y2;
        interaction = "None";
    }

    public Wall(double x1, double y1, double x2, double y2, String interact) {
        super(x1, y1, x2, y2, interact);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void paint(Graphics g) {
        if (interaction.equals("Stop")) {
            g.setColor(WALLCOLOR);
        } else if (interaction.equals("Kill")) {
            g.setColor(Color.red);
        } else {
            g.setColor(WALLCOLOR);
        }
        g.drawLine((int) x, (int) y, (int) x2, (int) y2);
    }


    @Override
    public void Collision(Character p, GamePanel gp) {
        if (isColliding(p)) {
            System.out.println("collision");
            p.pushBack();
        }
    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {

    }


    public boolean isColliding(Character p) {
        double x1 = x - p.x;
        double y1 = y - p.y;
        double x2 = this.x2 - p.x;
        double y2 = this.y2 - p.y;
        double dx = x2 - x1;
        double dy = y2 - y1;
        double c = -(dy * x2 - dx * y2);

        if ((x1 - p.SIZE / 2.0) * (x2 + p.SIZE / 2.0) < 0 && (y1 - p.SIZE / 2.0) * (y2 + p.SIZE / 2.0) < 0) {

            //closest point calculated
            if (dx == 0.0) {
                return (x2 > -p.SIZE / 2.0 && x2 < p.SIZE / 2.0);
            } else if (dy == 0.0) {
                return (y2 > -p.SIZE / 2.0 && y2 < p.SIZE / 2.0);
            } else {
                double cx = (-c / dx) / (dy / dx + dx / dy);
                double cy = -dx / dy * cx;
                double u = Methods.distanceBetweenPoints(cx, cy, 0, 0);
                return (u < Math.sqrt(p.SIZE * p.SIZE / 2));
            }
        } return false;
    }
}


