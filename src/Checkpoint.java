import java.awt.*;

public class Checkpoint extends GameElements{
    private final Color CHECKPOINT = new Color(102, 255, 102);

    public Checkpoint(){
        super();
        health = 3;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(CHECKPOINT);
        g.fillRect((int) x, (int) y, (int) (x2 - x), (int) (y2 - y));
    }

    @Override
    public void Collision(Character p, GamePanel gp) {
        if((p.x > x + p.SIZE / 2.0 && p.x < x2 - p.SIZE / 2.0) && (p.y > y + p.SIZE / 2.0 && p.y < y2 - p.SIZE / 2.0)){
            gp.level += 1;
            gp.currentLevel.loadLevelX(gp.level);
            gp.reset();

        }
    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {

    }

    public Checkpoint(int x, int y, int lenght, int width){
        super(x , y,x+lenght,y + width, "NextLvl");
        health = 3;
     }
}

