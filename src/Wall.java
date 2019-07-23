public class Wall extends GameElements {


    public Wall(){
        super();
    }
    public Wall(double x, double y, double x2, double y2){
    super(x,y);
    this.x2 = x2;
    this.y2 = y2;
    interaction = "None";
    }
    public Wall(double x1, double y1, double x2, double y2,String interact) {
        super(x1, y1, x2, y2, interact);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
