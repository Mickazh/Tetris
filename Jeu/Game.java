package Jeu;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET= 24 // pas besoin de beaucoup de FPS pour un tetris
    private final double UPS_SET = 2.0; // UPS = update par seconde

    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0/FPS_SET;
        System.out.println(timePerFrame);
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();
        // int frames = 0;
        // long lastCheck = System.currentTimeMillis();
        long lastUpdate = System.currentTimeMillis();
        long nowMillis;

        while(!gamePanel.getAPerdu()){
            now = System.nanoTime();
            nowMillis = System.currentTimeMillis();
            if(now - lastFrame >= timePerFrame){
                gamePanel.repaint();
                lastFrame = now;
                // ++frames;
            }

            
            // if (nowMillis - lastCheck >= 1000){
            //     lastCheck = nowMillis;
            //     frames = 0;
            // }

            if (nowMillis - lastUpdate >= 1000.0/UPS_SET){
                lastUpdate = nowMillis;
                try {
                    gamePanel.deplacerBas();
                } catch (IllegalStateException e) {
                    gamePanel.APerdu();
                }
            }

        }
        System.out.println("Perdu, vous avez nettoyé " + gamePanel.getTerrain().getScore() + " lignes");
        System.exit(0);
        return;

    }
}
