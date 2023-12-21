package d2game.entity;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Entity {

    public int worldX,worldY;// player's position
    public int speed;
    public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2;
    public String direction;

    public int spriteCounter=0;
    public int spriteNum=2;
    public Rectangle solidArea;
    public boolean collisionOn=false;
    public int solidAreaDeafultX,solidAreaDefaultY;
}
