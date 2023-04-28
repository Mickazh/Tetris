package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Jeu.GamePanel;

public class InputClavier implements KeyListener{
    private GamePanel gamePanel;

    public InputClavier(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }


    @Override
    public void keyTyped(KeyEvent e){
        
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.tournerGauche();
                break;
            case KeyEvent.VK_X:
                gamePanel.tournerDroite();
                break;
            case KeyEvent.VK_LEFT:
                gamePanel.deplacerGauche();
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.deplacerDroite();
                break;
            case KeyEvent.VK_DOWN:
                gamePanel.deplacerBas();
                break;
            case KeyEvent.VK_SHIFT:
                gamePanel.retenirPiece();
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.faireTomber();
            default:
                break;
        }
    }
}
