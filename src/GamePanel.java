import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.MouseInfo.getPointerInfo;

/*public enum ElementType {
    Gun,
    Hazard
}*/

public class GamePanel extends JPanel {


    private final Color PLAYERCOLOR = Color.red;
    private final Color ENEMYCOLOR = Color.blue;
    private final Color WALLCOLOR = Color.darkGray;
    private final Color MUD = new Color(153, 102, 0);
    private final Color WATER = new Color(51, 204, 255);
    private final Color PLASMA = new Color(30, 255, 40);
    private final Color CHECKPOINT = new Color(102, 255, 102);
    private final Color TRIGGER = new Color(75,50,0);
    private BufferedImage plasmaGunImage = null;
    private BufferedImage plasmaGunImage2 = null;
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
        bufferImages();
        requestFocusInWindow();
        time.start();
    }
    private void bufferImages(){
        try{
            plasmaGunImage =  ImageIO.read(getClass().getClassLoader().getResource("plasmaGun.png"));
        } catch(IOException e){
            System.out.println("There was an IO exception");
        }
        int w = plasmaGunImage.getWidth();
        int h = plasmaGunImage.getHeight();
        plasmaGunImage2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(0.125, 0.125);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        plasmaGunImage2 = scaleOp.filter(plasmaGunImage, plasmaGunImage2);
    }

    public int paintCase(Object gameElement) {
        String type = gameElement.getClass().getName();
        if (type.equals("Enemy")) {
            return 1;
        }
        if (type.equals("Wall")) {
            return 2;
        }
        if (type.equals("Hazard")) {
            return 3;
        }
        if (type.equals("Checkpoint")) {
            return 4;
        }
        if (type.equals("Gun")) {
            return 5;
        }
        if (type.equals("Bullet")) {
            return 6;
        }
        if (type.equals("Trigger")) {
            return 7;
        }


        System.out.println("problem, the object to print was not identified");
        return 9;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < currentLevel.activeElements.size(); i++) {
            GameElements elem = currentLevel.activeElements.get(i);
            switch (paintCase(elem)) {
                case 1:
                    g.setColor(ENEMYCOLOR);
                    g.fillRect((int) (elem.x - elem.SIZE / 2), (int) (elem.y - elem.SIZE / 2), elem.SIZE, elem.SIZE);
                    break;
                case 2:
                    if (elem.interaction.equals("Stop")) {
                        g.setColor(WALLCOLOR);
                    } else if (elem.interaction.equals("Kill")) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(WALLCOLOR);
                    }
                    g.drawLine((int) elem.x, (int) elem.y, (int) elem.x2, (int) elem.y2);
                    break;
                case 3:
                    if (elem.interaction.equals("Slow"))
                        g.setColor(MUD);
                    else if (elem.interaction.equals("Kill")) {
                        g.setColor(WATER);
                    } else {
                        g.setColor(Color.black);
                    }
                    g.fillRect((int) elem.x, (int) elem.y, (int) (elem.x2 - elem.x), (int) (elem.y2 - elem.y));
                    break;
                case 4:
                    g.setColor(CHECKPOINT);
                    g.fillRect((int) elem.x, (int) elem.y, (int) (elem.x2 - elem.x), (int) (elem.y2 - elem.y));
                    break;
                case 5:
                    g.drawImage(plasmaGunImage2,(int) elem.x - elem.SIZE / 2, (int) elem.y - elem.SIZE / 2,null);
                    break;
                case 6:
                    g.setColor(PLASMA);
                    g.fillRect((int) (elem.x - elem.SIZE / 2), (int) (elem.y - elem.SIZE / 2), elem.SIZE, elem.SIZE);
                    break;
                case 7 :
                    g.setColor(TRIGGER);
                    if (elem.isVisible){
                        g.fillRect((int) elem.x, (int) elem.y, (int) (elem.x2 - elem.x), (int) (elem.y2 - elem.y));
                    }
                    break;
                case 9:
                    break;

            }
        }
        g.setColor(PLAYERCOLOR);
        g.fillRect((int) (bob.x - bob.SIZE / 2), (int) (bob.y - bob.SIZE / 2), bob.SIZE, bob.SIZE);
    }

    public void checkCollision(Character p, GameElements e) {

        if (e.getClass().getName().equals("Wall")) {
            // we imagine a graph with point (0,0) the character
            double x1 = e.x - p.x;
            double y1 = e.y - p.y;
            double x2 = e.x2 - p.x;
            double y2 = e.y2 - p.y;
            double dx = e.x2 - e.x;
            double dy = e.y2 - e.y;
            double c = -(dy * x2 - dx * y2);

            if ((x1 - p.SIZE / 2.0) * (x2 + p.SIZE / 2.0) < 0 && (y1 - p.SIZE / 2.0) * (y2 + p.SIZE / 2.0) < 0) {
                //closest point calculated
                if (dx == 0.0) {
                    if (x2 > -p.SIZE / 2.0 && x2 < p.SIZE / 2.0) {
                        pushAway(p);
                    }
                } else if (dy == 0.0) {
                    if (y2 > -p.SIZE / 2.0 && y2 < p.SIZE / 2.0) {
                        pushAway(p);
                    }
                } else {
                    double cx = (-c / dx) / (dy / dx + dx / dy);
                    double cy = -dx / dy * cx;
                    double u = distanceBetweenPoints(cx, cy, 0, 0);
                    if (u < Math.sqrt(p.SIZE * p.SIZE / 2)) {
                        pushAway(p);
                    }
                }
            }
        } else if (e.getClass().getName().equals("Enemy")) {
            if ((Math.abs((e.x - p.x)) < ((p.SIZE + e.SIZE) / 2.0) && (Math.abs((e.y - p.y)) < ((p.SIZE + e.SIZE) / 2.0)))) {
                currentLevel.loadLevelX(level);
                reset();
            }
        } else if (e.getClass().getName().equals("Gun")) {
            if ((Math.abs((e.x - p.x)) < ((p.SIZE + e.SIZE) / 4.0) && (Math.abs((e.y - p.y)) < ((p.SIZE + e.SIZE) / 4.0)))) {
                p.inventory.add((Gun) e);
                p.equipedGun = (Gun) e;
                currentLevel.activeElements.remove(e);
            }
        } else {
            boolean isIn = (p.x > e.x - p.SIZE / 2.0 && p.x < e.x2 + p.SIZE / 2.0) && (p.y > e.y - p.SIZE / 2.0 && p.y < e.y2 + p.SIZE / 2.0);
            if (e.getClass().getName().equals("Hazard") && isIn) {
                if (e.interaction.equals("Kill")) {
                    currentLevel.loadLevelX(level);
                    reset();
                } else if (e.interaction.equals("Slow")) {
                    p.velx /= 1.5;
                    p.vely /= 1.5;
                }
            }else  if (e.getClass().getName().equals("Trigger") && isIn) {
                Trigger t = (Trigger) e;
                t.activate();
            } else if (e.getClass().getName().equals("Checkpoint")) {
                isIn = (p.x > e.x + p.SIZE / 2.0 && p.x < e.x2 - p.SIZE / 2.0) && (p.y > e.y + p.SIZE / 2.0 && p.y < e.y2 - p.SIZE / 2.0);
                if (isIn) {
                    level += 1;
                    currentLevel.loadLevelX(level);
                    reset();
                }
            }
        }
    }
    public void checkBulletCollision(GameElements b, GameElements e){
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
                    double u = distanceBetweenPoints(cx, cy, 0, 0);
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
        }
    }
    public void checkOoB(GameElements e){
    }

    public void checkToDiscard(GameElements e){
        if (e.toDiscard == true){
            currentLevel.activeElements.remove(currentLevel.activeElements.indexOf(e));
        }
    }


    public double distanceBetweenLine(double dx, double dy, double x1, double y1) {
        double c = -(dx * x1 + dy * y1);
        return (Math.abs(dx * x1 + dy * y1 + c) / Math.sqrt(dx * dx + dy * dy));
    }

    public double distanceBetweenPoints(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        return Math.sqrt((dx * dx) + (dy * dy));
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
        checkToDiscard(e);
        checkTriggers(e);
        checkOoB(e);

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
                    currentLevel.activeElements.add(bob.shotABullet(targetX,targetY));
                }

                for (int i = 0; i < currentLevel.activeElements.size(); i++) {
                    GameElements elem = currentLevel.activeElements.get(i);
                    entityChecks(elem);
                    elem.move(t - elem.lag);
                    checkCollision(bob, elem);
                    if (elem.getClass().getName().equals("Bullet")){
                        for (int j = 0; j < currentLevel.activeElements.size(); j++) {
                            checkBulletCollision(elem,currentLevel.activeElements.get(j));
                        }
                    }
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

