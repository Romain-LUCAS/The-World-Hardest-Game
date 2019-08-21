import java.awt.*;

public class Enemy extends GameElements {
    private final Color ENEMYCOLOR = Color.blue;

    public Enemy() {
        super();
    }

    public Enemy(int x, int y) {
        super(x, y);
        SIZE = 30;
    }

    public Enemy(int x, int y, int velx, int vely) {
        super(x, y, velx, vely);
        SIZE = 30;
    }
    @Override
    public void paint(Graphics g) {
        g.setColor(ENEMYCOLOR);
        g.fillRect((int) (x - SIZE / 2), (int) (y - SIZE / 2), SIZE, SIZE);
    }

    @Override
    public void Collision(Character p, GamePanel gp) {
        if ((Math.abs((x - p.x)) < ((p.SIZE + SIZE) / 2.0) && (Math.abs((y - p.y)) < ((p.SIZE + SIZE) / 2.0)))) {
            gp.currentLevel.loadLevelX(gp.level);
            gp.reset();
        }
    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {

    }
}

