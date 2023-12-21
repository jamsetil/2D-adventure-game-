package d2game;



import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setVisible(true);
        window.pack();
        gamePanel.setUpGame();
        window.setLocationRelativeTo(null);



    }
}