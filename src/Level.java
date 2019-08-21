import java.util.ArrayList;

public class Level {

    public ArrayList<GameElements> activeElements = new ArrayList<>();
    public ArrayList<Bullet> projectiles = new ArrayList<>();
    public int number;

    public Level(){

    }
    public Level(int lvl){
        number = lvl;
        loadLevelX(lvl);

    }
    public void loadLevelX(int lvl){
        if (lvl == 0){
            loadLevel0();
        }else if (lvl == 1){
            loadLevel1();
        }
        else if (lvl == 2){
            loadLevel2();
        }
    }
    public void loadEventX(String event){
        switch (event){
            case "event2_1":
                loadEvent2_1();
                break;

        }
    }
    public void loadLevel0(){
        activeElements.clear();
        Enemy enemy1 = new Enemy(84,32);
        Enemy enemy2 = new Enemy(400,150);
        Wall wall1 = new Wall(200,200,200,700,"None");

        enemy1.setMotion("moveLinear",150.0,300.0,400.0);
        enemy2.setMotion("moveSinus",600,600,300);

        activeElements.add(enemy1);
        activeElements.add(enemy2);
        activeElements.add(wall1);
    }
    public void loadLevel1(){
        activeElements.clear();
        //walls

        Wall wall1 = new Wall(0,0,0,800);
        Wall wall2 = new Wall(0,800,800,800);
        Wall wall3 = new Wall(0,0,800,0);
        Wall wall4 = new Wall(800,0,800,800);
        Wall wall5 = new Wall(250,0,250,600);
        Wall wall6 = new Wall(550,800,550,200);

        //enemies

        Enemy enemy1 = new Enemy(300,250);
        enemy1.setMotion("moveSinus", 500,250,54, 10);

        Enemy enemy2 = new Enemy(300,350);
        enemy2.setMotion("moveSinus", 500,350,60, 5);

        Enemy enemy3 = new Enemy(300,450);
        enemy3.setMotion("moveSinus", 500,450,53, 0);


        Enemy enemy4 = new Enemy(300,550);
        enemy4.setMotion("moveSinus", 500,550,58, 15);
        //hazard

        Hazard hazard1 = new Hazard(25,500,200,30,"Slow");
        Hazard hazard2 = new Hazard(450,700,100,100,"Kill");
        Hazard hazard3 = new Hazard(310,390,150,30,"Slow");
        Hazard hazard4 = new Hazard(530,350,20,140,"Kill");
        Hazard hazard5 = new Hazard(260,275,50,50,"Kill");
        Hazard hazard6 = new Hazard(400,480,150,20,"Kill");


        Checkpoint checkpoint1 = new Checkpoint(700,700,100,100);
        //add
        activeElements.add(wall1);
        activeElements.add(wall2);
        activeElements.add(wall3);
        activeElements.add(wall4);
        activeElements.add(wall5);
        activeElements.add(wall6);
        activeElements.add(enemy1);
        activeElements.add(enemy2);
        activeElements.add(enemy3);
        activeElements.add(enemy4);
        activeElements.add(hazard1);
        activeElements.add(hazard2);
        activeElements.add(hazard3);
        activeElements.add(hazard4);
        activeElements.add(hazard5);
        activeElements.add(hazard6);
        activeElements.add(checkpoint1);
    }
    public void loadLevel2(){
        activeElements.clear();
        Wall wall1 = new Wall(0,0,0,800);
        Wall wall2 = new Wall(0,800,800,800);
        Wall wall3 = new Wall(0,0,800,0);
        Wall wall4 = new Wall(800,0,800,800);


        Wall wall5 = new Wall(350,350,350,450);
        Wall wall6 = new Wall(350,350,450,350);
        Wall wall7 = new Wall(350,450,450,450);
        Wall wall8 = new Wall(450,350,450,450);
        Trigger trigger1 = new Trigger(394,294,15,15,false,"event2_1");
        Checkpoint checkpoint1 = new Checkpoint(351,351,99,99);




        Enemy enemy1 = new Enemy(700,700);

        Gun plasmaGun = new Gun(400,300);

        activeElements.add(wall1);
        activeElements.add(wall2);
        activeElements.add(wall3);
        activeElements.add(wall4);
        activeElements.add(wall5);
        activeElements.add(wall6);
        activeElements.add(wall7);
        activeElements.add(wall8);
        activeElements.add(trigger1);
        activeElements.add(checkpoint1);
        activeElements.add(enemy1);
        activeElements.add(plasmaGun);
    }

    public void loadEvent2_1(){
        Enemy enemy2 = new Enemy(200,600);
        enemy2.setMotion("moveLinear",200,400.0,400.0);
        activeElements.add(enemy2);

    }
}
