package d2game.entity;

import d2game.GamePanel;
import d2game.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;//where player will be drawn
    public final int screenY;
    public int hasKey=0;


    public Player(GamePanel gp,KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        screenX=gp.screenWidth/2-(gp.tileSize/2);
        screenY=gp.screenHeight/2-(gp.tileSize/2);
        setDefaultValues();
        getPlayerImage();
        solidArea = new Rectangle(8,16,32,32);
        solidAreaDeafultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
    }

    public void setDefaultValues(){
        worldX=gp.tileSize*23-(gp.tileSize/4);//centering player position on map
        worldY=gp.tileSize*21-(gp.tileSize/4);
        speed=4;
        direction="down";
    }

    public void getPlayerImage(){
        try{
            up1= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2= ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2= ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2= ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
       if (keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.upPressed){
           if (keyH.upPressed){
               direction="up";

           } else if (keyH.downPressed) {
               direction="down";

           } else if (keyH.leftPressed) {
               direction="left";

           } else if (keyH.rightPressed){
               direction="right";

           }

           //CHECK TILE COLLISION
           collisionOn=false;
           gp.collisionChecker.checkTile(this);

           //CHECK OBJECT COLLISION
           int objectIndex = gp.collisionChecker.checkObject(this,true);
           pickUpObject(objectIndex);


           //IF COLLISION IS FALSE, PLAYER CAN MOVE
           if (collisionOn==false){
               switch (direction){

                   case "up":
                       worldY-=speed;
                       break;
                   case "down":
                       worldY+=speed;
                       break;
                   case "right":
                       worldX+=speed;
                       break;
                   case "left":
                       worldX-=speed;
                       break;
               }
           }
           spriteCounter++;
           if (spriteCounter>10){
               if (spriteNum==1){
                   spriteNum=2;
               }
               else if(spriteNum==2){
                   spriteNum=1;
               }
               spriteCounter=0;
           }
       }

    }

    public void pickUpObject(int index){

        if (index!=999){
            String objectName = gp.obj[index].name;
            switch (objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[index]=null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey >0){
                        gp.ui.showMessage("You opened the door!");
                        gp.playSE(3);
                        gp.obj[index]=null;
                        hasKey--;
                    }else gp.ui.showMessage("You need a key!");
                    break;
                case "Boots":
                    gp.ui.showMessage("Speed up!");
                    gp.playSE(2);
                    speed+=1;
                    gp.obj[index]=null;
                    break;
                case "Chest":
                    gp.ui.gameFinished=true;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }
        }
    }


    public void draw(Graphics g2){
//        g2.setColor(Color.white);
//        g2.fillRect(x,y,gp.tileSize,gp.tileSize);



        BufferedImage image = null;
        switch (direction){
            case "up":
                if(spriteNum==1){
                image=up1;
                }
                if(spriteNum==2){
                image=up2;
                }
                break;
            case "down":
                if (spriteNum==1){
                    image=down1;
                }
                if (spriteNum==2){
                    image=down2;
                }
                break;
            case "left":
                if (spriteNum==1){
                    image=left1;
                }
                if (spriteNum==2){
                    image=left2;
                }
                break;
            case "right":
                if (spriteNum==1){
                    image=right1;
                }
                if (spriteNum==2){
                    image=right2;
                }
                break;
        }
        g2.drawImage(image,screenX,screenY,gp.tileSize, gp.tileSize,null);
    }
}
