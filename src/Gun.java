import java.awt.*;

public class Gun extends GameElements {
    public boolean isObtained = false;
    public int cooldown;


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
    }
}
