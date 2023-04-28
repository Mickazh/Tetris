package Jeu;

import javax.swing.JFrame;

public class GameWindow {
    public static final int widthPixel = 25, heightPixel = widthPixel;
    private JFrame jFrame;

    public GameWindow(GamePanel gamePanel){
        jFrame = new JFrame();
        jFrame.setSize((Terrain.longueur+15)*widthPixel , (Terrain.hauteur+7)*heightPixel);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

}
