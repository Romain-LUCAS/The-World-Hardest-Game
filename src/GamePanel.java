import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*public enum ElementType {
    Gun,
    Hazard
}*/

public class GamePanel extends JPanel {

    private double targetX;
    private double targetY;

    public Character bob = new Character(0, 0);
    private Handler2 keyHandler = new Handler2();
    public Timer time = new Timer(17, new Handler1());
    public int level = 2;
    public Level currentLevel = new Level(level);
    int t = 0;


    public boolean isUp;
    public boolean isDown;
    public boolean isRight;
    public boolean isLeft;
    public boolean isFiring;


    public GamePanel() {
        addKeyListener(keyHandler);
        addMouseListener(new MouseClass());
        setFocusable(true);
        requestFocusInWindow();
        time.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < currentLevel.activeElements.size(); i++) {
            GameElements elem = currentLevel.activeElements.get(i);
            elem.paint(g);
            }
        bob.paint(g);
    }



    public void checkCollision(Character p, GameElements e) {
        e.Collision(p, this);
    }

    public void checkBulletCollision(Bullet b, GameElements e){
        if (e.getClass().getName().equals("Wall")) {
            // we imagine a graph with point (0,0) the bullet
            double x1 = e.x - b.x;
            double y1 = e.y - b.y;
            double x2 = e.x2 - b.x;
            double y2 = e.y2 - b.y;
            double dx = e.x2 - b.x;
            double dy = e.y2 - b.y;
            double c = -(dy * x2 - dx * y2);


            if ((x1 - b.SIZE / 2.0) * (x2 + b.SIZE / 2.0) < 0 && (y1 - b.SIZE / 2.0) * (y2 + b.SIZE / 2.0) < 0) {
                //closest point calculated
                if (dx == 0.0) {
                    if (x2 > -b.SIZE / 2.0 && x2 < b.SIZE / 2.0) {
                        removeActiveElements(b);
                    }
                } else if (dy == 0.0) {
                    if (y2 > -b.SIZE / 2.0 && y2 < b.SIZE / 2.0) {
                        b = null;
                        removeActiveElements(b);
                    }
                } else {
                    double cx = (-c / dx) / (dy / dx + dx / dy);
                    double cy = -dx / dy * cx;
                    double u = Methods.distanceBetweenPoints(cx, cy, 0, 0);
                    System.out.println(u);
                    if (u < Math.sqrt(b.SIZE*b.SIZE/2)) {
                        removeActiveElements(b);
                    }
                }
            }
        } else if (e.getClass().getName().equals("Enemy")) {
            if ((Math.abs((e.x - b.x)) < ((b.SIZE + e.SIZE) / 2.0) && (Math.abs((e.y - b.y)) < ((b.SIZE + e.SIZE) / 2.0)))) {
                System.out.println("enemy collision");
                System.out.println(currentLevel.activeElements.indexOf(e));
                removeActiveElements(e);
                removeActiveElements(b);
            }
        }
    }
    public void checkTriggers(GameElements e){
        if (e.getClass().getName().equals("Trigger")){
            Trigger t = (Trigger) e;
            if (t.isActivated){
                currentLevel.loadEventX(t.action);
                removeActiveElements(t);
            }
        }else{
            if (e.trigger != null && e.trigger.isActivated) {
                e.doTriggerAction();
            }
        }
    }
    public void checkOoB(GameElements e){
    }

    public void checkToDiscard(GameElements e){
        if (e.toDiscard){
            currentLevel.activeElements.remove(currentLevel.activeElements.indexOf(e));
        }
    }

    public double distanceBetweenLine(double dx, double dy, double x1, double y1) {
        double c = -(dx * x1 + dy * y1);
        return (Math.abs(dx * x1 + dy * y1 + c) / Math.sqrt(dx * dx + dy * dy));
    }

    public void pushAway(Character p) {
        p.x -= p.velx / 100;
        p.y -= p.vely / 100;
        p.velx = -1.0 * p.velx;
        p.vely = -1.0 * p.vely;
    }

    public void reset() {
        bob.x = bob.SIZE / 2;
        bob.y = bob.SIZE / 2;
        bob.velx = 0;
        bob.vely = 0;
        t = 0;
    }
    public void removeActiveElements(GameElements e){
        try {
            currentLevel.activeElements.remove(currentLevel.activeElements.indexOf(e));
        }catch (IndexOutOfBoundsException ev){
            System.out.println("Exception handled");
        }
    }

    public void entityChecks(GameElements e){
        checkTriggers(e);
        checkOoB(e);
        checkToDiscard(e);
    }

    private class Handler1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == time) {
                t++;

                bob.x += bob.velx / 100.0;
                bob.y += bob.vely / 100.0;
                bob.move(isUp, isDown, isLeft, isRight);
                bob.lowerCD();
                if (isFiring && bob.internalCD == 0 && bob.equipedGun != null){
                    Point cursor = MouseInfo.getPointerInfo().getLocation();
                    targetX = cursor.getX() - 8;
                    targetY = cursor.getY() - 31;
                    Bullet b = bob.shotABullet(targetX,targetY);
                    currentLevel.activeElements.add(b);
                    currentLevel.projectiles.add(b);
                }
                for (int i = 0; i < currentLevel.projectiles.size(); i++) {
                    Bullet b = currentLevel.projectiles.get(i);
                    for (int j = 0; j < currentLevel.activeElements.size(); j++) {
                        checkBulletCollision(b, currentLevel.activeElements.get(j));
                    }
                }
                for (int i = 0; i < currentLevel.activeElements.size(); i++) {
                    GameElements elem = currentLevel.activeElements.get(i);
                    elem.move(t - elem.lag);
                    checkCollision(bob, elem);
                    entityChecks(elem);

                }
                repaint();
            }
        }
    }

    private class Handler2 implements KeyListener {


        @Override
        public void keyPressed(KeyEvent e) {
            char keyChar = e.getKeyChar();
            String keyString = "" + keyChar;
            if (keyString.equals("w")) {
                isUp = true;
            }
            if (keyString.equals("s")) {
                isDown = true;
            }
            if (keyString.equals("a")) {
                isLeft = true;
            }
            if (keyString.equals("d")) {
                isRight = true;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            char keyChar = e.getKeyChar();
            String keyString = "" + keyChar;
            if (keyString.equals("w")) {
                isUp = false;
            }
            if (keyString.equals("s")) {
                isDown = false;
            }
            if (keyString.equals("a")) {
                isLeft = false;
            }
            if (keyString.equals("d")) {
                isRight = false;
            }
        }
    }
    private class MouseClass extends MouseAdapter{

        @Override
        public void mousePressed(MouseEvent e) {
            isFiring = true;
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            isFiring = false;
        }
    }
}

