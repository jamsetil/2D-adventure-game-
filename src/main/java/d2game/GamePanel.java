package d2game;

import d2game.entity.Player;
import d2game.object.SuperObject;
import d2game.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Screen settings
    final int originalTileSize=16;
    final int scale =3;
    public final int tileSize = originalTileSize*scale;//48x48
    public final int maxScreenCol=16;
    public final int maxScreenRow=12;
    public final int screenWidth = tileSize*maxScreenCol;//768px
    public final int screenHeight = tileSize*maxScreenRow;//576px
    //world settings
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;





    //SYSTEM SETTINGS
    TileManager tileM=new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter =new AssetSetter(this);
    Thread gameThread;
    public UI ui = new UI(this);



    //FPS
    int FPS=60;

    //ENTITY AND OBJECT
    public Player player =new Player(this,keyH);
    public SuperObject[] obj=new SuperObject[10];


    //SOUND


    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        startGameThread();
    }

    public void setUpGame(){
        aSetter.setObject();
        playMusic(0);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime=System.nanoTime();
        double drawInterval=1000000000/FPS;
        double delta=0;
        long currenttime;
        while (gameThread!=null){
            currenttime=System.nanoTime();
            delta+=(currenttime-lastTime)/drawInterval;
            lastTime=currenttime;
            if (delta>=1){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){

        player.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //Tile
        tileM.draw(g2);

        //Object
        for (int i = 0;i<obj.length;i++){
            if (obj[i]!=null){
                obj[i].draw(g2,this);
            }
        }

        //Player
        player.draw(g2);

        //UI
        ui.draw(g2);

        g2.dispose();


    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public  void stopMusic(){
        music.stop();
    }

    //Sound Effect
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
