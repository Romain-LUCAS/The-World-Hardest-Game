public class Checkpoint extends GameElements{

    public Checkpoint(){
        super();
        health = 3;
    }

    public Checkpoint(int x, int y, int lenght, int width){
        super(x , y,x+lenght,y + width, "NextLvl");
        health = 3;
     }
}

