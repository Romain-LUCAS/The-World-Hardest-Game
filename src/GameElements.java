import java.awt.*;

public abstract class GameElements {
    public double x;
    public double y;
    public double x2;
    public double y2; // for walls
    public int SIZE;
    public double initX;
    public double initY;
    public double toX;
    public double toY;
    public double absSpeed;
    public int frameCycle;
    public double velx;
    public double vely;
    public boolean isVisible;
    public String movingMethod;
    public boolean isMoving;
    public int health;
    public Trigger trigger;
    public boolean toDiscard = false;
    // used for direction vector
    private double dx;
    private double dy;
    private double hyp;
    private double scalingFactor;

    public String interaction;
    public int lag;




    public GameElements(){
        this.x = 0.0;
        this.y = 0.0;
        this.velx = 0.0;
        this.vely = 0.0;
    }
    public GameElements(double x, double y){
        this.x = x;
        this.y = y;
        this.velx = 0.0;
        this.vely = 0.0;
    }
    public GameElements(double x, double y, double velx, double vely){
        this.x = x;
        this.y = y;
        this.velx = velx;
        this.vely = vely;
    }
    public GameElements(double x1, double y1, double x2, double y2, String interact){
        x = x1;
        y = y1;
        this.x2 = x2;
        this.y2 = y2;
        interaction = interact;
    }

    @Override
    public String toString() {
        return String.format("GameElement pos x = %f,pos y = %f,velx = %f, vely = %f", x, y, velx, vely);
    }
    public void setMotion(String movMethod, double absSpd, double toX, double toY){
        movingMethod = movMethod;
        isMoving = true;
        this.toX = toX;
        this.toY = toY;
        absSpeed = absSpd;
    }

    public void setMotion(String movMethod, double toX, double toY,int frameCycle){
        movingMethod = movMethod;
        isMoving = true;
        this.initX = x;
        this.initY = y;
        this.toX = toX;
        this.toY = toY;
        this.frameCycle = frameCycle;
    }
    public void setMotion(String movMethod, double toX, double toY,int frameCycle, int lag){
        movingMethod = movMethod;
        isMoving = true;
        this.initX = x;
        this.initY = y;
        this.lag = lag;
        this.toX = toX;
        this.toY = toY;
        this.frameCycle = frameCycle;
    }

    public abstract void paint(Graphics g);
    public abstract void Collision(Character p, GamePanel gp);
    public abstract void Collision(Bullet b, GamePanel gp);


    public void move(int t){
        if (t > 0){

            if (movingMethod == "moveLinear"){
            moveLinear();
            }
            if (movingMethod == "moveSinus"){
                moveSinus(t);
            }
        }
        updatePosition();
    }
    public void moveLinear(){
        // Goes toward a point and stops
        if (this.isMoving){
            dx = toX - x;
            dy = toY - y;
            if (velx * dx < 0){
                // Snapping to position that got overshot
                x = toX;
                y = toY;
                velx = 0.0;
                vely = 0.0;
                isMoving = false;
                return;
            }
            hyp = Math.sqrt(dx * dx + dy * dy);
            scalingFactor = absSpeed / 100.0 / hyp;
            velx = (scalingFactor * dx * 100.0);
            vely = (scalingFactor * dy * 100.0);
        }
    }
    public void moveSinus(int t){
        if (this.isMoving){
            dx = toX - initX;
            dy = toY - initY;
            absSpeed = Math.cos( 2*Math.PI*((double) (t)/frameCycle)+1.5*Math.PI) / frameCycle * Math.sqrt(dx*dx + dy*dy)*Math.PI*100;
            setVelVectors();
        }
    }
    public void updatePosition(){
        x += velx / 100.0;
        y += vely / 100.0;
    }
    public void setVelVectors(){
        dx = toX - x;
        dy = toY - y;
        hyp = Math.sqrt(dx * dx + dy * dy);
        scalingFactor = absSpeed / 100.0 / hyp;
        velx = (scalingFactor * dx * 100.0);
        vely = (scalingFactor * dy * 100.0);
    }

    public void doTriggerAction(){
        System.out.println("do trigger action launched");
        switch (trigger.action) {
            case "toDiscard":
                System.out.println("to discard activated");
                toDiscard = true;
                break;
            case"Transform":
                break;

            case "None":
                break;

        }
    }
}
