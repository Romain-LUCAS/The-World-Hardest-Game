import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Character {
    public double x;
    public double y;
    public double velx;
    public double vely;
    public final int SIZE = 50;
    public double slope;
    public double internalCD = 0;

    public ArrayList<Gun> inventory = new ArrayList<Gun>();
    public Gun equipedGun = null;
    private double maxVelx;
    private double maxVely;


    public Character(){
        x = 0.0;
        y = 0.0;
        velx = 0.0;
        vely = 0.0;
        replace();


    }
    public Character(double x, double y){
        this.x = x;
        this.y = y;
        velx = 0.0;
        vely = 0.0;
        replace();

    }

    @Override
    public String toString() {
        return String.format("Character pos x = %f,pos y = %f,velx = %f, vely = %f", x, y, velx, vely);
    }
    public void move(boolean up,boolean down,boolean left, boolean right){
        //System.out.println("up " + up +" down " + down+" left " + left+ " right " +right);
        if (up){
            if (!(left || right)){
                vely -= 50.0;
            } else{
                vely -= Math.sqrt(1250);
            }
        }

        if (down){
            if (!(left || right)){
                vely += 50.0;
            } else{
                vely += Math.sqrt(1250);
            }
        }

        if (left){
            if (!(up || down)){
                velx -= 50.0;
            } else {
                velx -= Math.sqrt(1250);
            }
        }

        if (right){
            if (!(up || down)){
                velx += 50.0;
            } else{
                velx += Math.sqrt(1250);
            }
        }
        capSpeed();
        slowDown(up,down,left,right);

    }
    public void capSpeed(){
        if (velx >= 400.0){
            velx = 400.0;
        }
        if (velx <= -400.0){
            velx = -400.0;
        }
        if (vely >= 400.0){
            vely = 400.0;
        }
        if (vely <= -400.0){
            vely = -400.0;
        }
        double absSpeed = Math.sqrt(velx*velx+vely*vely);
        if (absSpeed > 400 && velx != 0.0 && vely != 0.0){
            slope = (double) Math.abs(vely) /  (double) Math.abs(velx);
            maxVelx = Math.sqrt(160178.0 /((1.0+slope*slope)));
            maxVely = Math.sqrt(160178.0 - maxVelx*maxVelx);
            velx = velx / Math.abs(velx) * maxVelx ;
            vely = vely / Math.abs(vely) * maxVely ;
        }
    }

    public void slowDown(boolean up, boolean down, boolean left, boolean right){
        if ((velx > 0.0 && !(right && !left)) || (velx < 0.0 && !(left && !right))){
            velx -= velx / Math.sqrt(velx*velx + vely*vely) * 25.0;
        }
        if ((vely > 0.0 && !(down && !up)) || (vely < 0.0 && !(up && !down))){
            vely -= vely/Math.sqrt(velx*velx + vely*vely) * 25.0;
        }
        if (velx>-25.0 && velx<25){
            velx = 0.0;
        }
        if (vely>-25.0 && vely<25){
            vely = 0.0;
        }
    }
    public Bullet shotABullet(double x, double y){
        internalCD = equipedGun.cooldown;
        return new Bullet(this.x,this.y,x,y);
    }
    public void replace(){
        if (x-SIZE/2 < 0){
            x = SIZE/2;
        }
        if (y-SIZE/2 < 0){
            y = SIZE/2;
        }
    }
    public void lowerCD(){
        if (internalCD > 0){
            internalCD--;
        }
    }
}

