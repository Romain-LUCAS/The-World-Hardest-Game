public class Hazard extends GameElements {
        // slow

    public Hazard(){
        super();
    }
    public Hazard(double x1, double y1, int width, int height,String interact){
        super(x1, y1, x1 + width, y1 + height, interact);
    }

}
