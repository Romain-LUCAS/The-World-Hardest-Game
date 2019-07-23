

public class Trigger extends GameElements {
    public boolean isActivated = false;

    public String action;
    public String variation;



    public Trigger() {
        super();
    }

    public Trigger(double x, double y, int width, int height, boolean isVisible, String action) {
        super(x,y,x + width, y + height,"None");
        this.isVisible = isVisible;
        this.action = action;
    }
    public Trigger(String action){
        isVisible = false;
        this.action = action;

    }

    public void activate() {
        if (!isActivated){
            isActivated = true;
            System.out.println("trigger activated");
        }
    }
    public void deactivate(){
        if (isActivated = true){
            isActivated = false;
        }
    }
}
