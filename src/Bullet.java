import java.awt.*;

public class Bullet extends GameElements {
    private final Color PLASMA = new Color(30, 255, 40);
    public Bullet(){
        super();
    }
    public Bullet(double x, double y){
        super(x,y);
        SIZE = 10;
    }
    public Bullet(double x, double y,double toX, double toY){
        super(x,y);
        this.velx = velx;
        this.vely = vely;
        this.toX = toX;
        this.toY = toY;
        absSpeed = 500;
        setVelVectors();

        SIZE = 10;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(PLASMA);
        g.fillRect((int) (x - SIZE / 2), (int) (y - SIZE / 2), SIZE, SIZE);

    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {

    }

    @Override
    public void Collision(Character p, GamePanel gp) {

    }


}