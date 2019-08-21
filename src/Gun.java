import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gun extends GameElements {
    public boolean isObtained = false;
    public int cooldown;
    private BufferedImage plasmaGunImage = null;
    private BufferedImage plasmaGunImage2 = null;


    public Gun(){
        super();
        SIZE = 70;
        cooldown = 20;
    }

    public Gun(double x,double y){
        this.x = x;
        this.y = y;
        SIZE = 70;
        cooldown = 20;
        bufferImages();
    }
    private void bufferImages(){
        try{
            plasmaGunImage =  ImageIO.read(getClass().getClassLoader().getResource("plasmaGun.png"));
        } catch(IOException e){
            System.out.println("There was an IO exception");
        }
        int w = plasmaGunImage.getWidth();
        int h = plasmaGunImage.getHeight();
        plasmaGunImage2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(0.125, 0.125);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        plasmaGunImage2 = scaleOp.filter(plasmaGunImage, plasmaGunImage2);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(plasmaGunImage2,(int) x - SIZE / 2, (int) y - SIZE / 2,null);
    }

    @Override
    public void Collision(Character p, GamePanel gp) {
        if ((Math.abs((x - p.x)) < ((p.SIZE + SIZE) / 4.0) && (Math.abs((y - p.y)) < ((p.SIZE + SIZE) / 4.0)))) {
            p.inventory.add(this);
            p.equipedGun = this;
            gp.currentLevel.activeElements.remove(this);
        }
    }

    @Override
    public void Collision(Bullet b, GamePanel gp) {

    }
}
