public class Bullet extends GameElements {

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






}