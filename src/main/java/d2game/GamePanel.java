package d2game;

import d2game.entity.Player;
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
    public final int worldWidth=tileSize*maxWorldCol;
    public final int worldHeight=tileSize*maxScreenRow;
    Thread gameThread;

    KeyHandler keyH = new KeyHandler();
    int FPS=60;

    public Player player =new Player(this,keyH);
    TileManager tileM=new TileManager(this);

    GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        startGameThread();
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
        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();

    }
}
