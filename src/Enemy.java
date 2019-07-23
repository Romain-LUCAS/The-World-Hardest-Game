public class Enemy extends GameElements {

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

    //multiple constructors for different enemy type
}

