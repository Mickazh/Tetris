package Jeu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import Input.InputClavier;
import Input.InputSouris;
import Pieces.Coordonnees;
import Pieces.Piece;


public class GamePanel extends JPanel{
    private static final int widthPixel = GameWindow.widthPixel, heightPixel = widthPixel, paddingLeft = 7;
    private boolean aPerdu = false;
    private InputSouris inputSouris;
    private Terrain terrain;

    public GamePanel(){

        terrain = new Terrain();
        inputSouris = new InputSouris(this);
        addKeyListener(new InputClavier(this));
        addMouseListener(inputSouris);
        addMouseMotionListener(inputSouris);
        // for (int i = 0; i < Terrain.longueur; ++i){
        //     Coordonnees co = new Coordonnees(15, i);
        //     Piece pi = Piece.newPiece(TypePiece.X, co);
        //     terrain.terrain.get(15).add(pi);
        // }
        for (char[] linge : terrain.toCharTerrain()) {
            for (char ch : linge) {
                System.out.print(ch + " ");
            }
            System.out.print(System.lineSeparator());
        }
        // terrain.tournerGauchePieceActuelle();
        // System.out.println(terrain.getPieceActuel().coordonneesLieesTournerGauche());
        // System.out.println(terrain.getPieceActuel().coordonneesLieesTournerDroite());
        
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // this.terrain.deplacerBasPieceActuelle();
        // for (int NLigne = 0; NLigne < Terrain.hauteur; ++NLigne){
        //     ArrayList<Piece> ligne = terrain.getTerrain().get(NLigne);
        //         for (int NColonne = 0; NColonne < Terrain.longueur; ++NColonne){
        //             Piece piece = ligne.get(NColonne);
        //             // il faut noter que l'argument x dans JFrame correspond a notre y
        //             if (piece == null){
        //                 g.drawRect(NColonne*15, NLigne*15, 15, 15);
        //             }
        //             else{
        //                 g.fillRect(NColonne*15, NLigne*15, 15, 15);
        //             }
        //         }
        //     }
        paintDropedPiece(g);
        try {
            char[][] terrainChar = terrain.toCharTerrain();
            for (int i = 0; i < Terrain.hauteur; ++i){
                for (int j = 0; j < Terrain.longueur; ++j){
                    if (terrainChar[i][j] == '.'){
                        g.setColor(Color.BLACK);
                        g.drawRect((paddingLeft+j)*widthPixel, (3+i)*heightPixel, widthPixel, heightPixel);
                    }
                    else{
                        if (terrainChar[i][j] == terrain.getPieceActuel().getTypeDePiece().toString().charAt(0)){
                            continue;
                        }
                        Color c = terrain.getTerrain().get(i).get(j).getColor();
                        g.setColor(c);
                        g.fillRect((paddingLeft+j)*widthPixel, (3+i)*heightPixel, widthPixel, heightPixel);
                    }
                }
            }
            int x, y;
            ArrayList<Coordonnees> coords = terrain.getPieceActuel().coordonneesLiees();
            g.setColor(terrain.getPieceActuel().getColor());
            for (Coordonnees coordonnees : coords) {
                x = coordonnees.getX();
                y = coordonnees.getY();
                
                g.fillRect((paddingLeft+y)*widthPixel, (3+x)*heightPixel, widthPixel, heightPixel);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            aPerdu = true;
            return;
        }

        paintProchainePieces(g);
        paintPieceRetenue(g);
        

        
    }

    /**
     * Peindre une piece p, à l'abscisse x et l'ordonné y (à l'inverse de ce qu'on fait dans le projet)
     * @param g Le composant graphique
     * @param p La piece qu'on souhaite peindre
     * @param x L'abscisse
     * @param y L'ordonné 
     */
    private void paintPiece(Graphics g, Piece p, int x, int y){
        try {
            ArrayList<Coordonnees> coords = p.getCaseLie();
            g.setColor(p.getColor());
            g.fillRect((x+p.getCentre().getY())*widthPixel, (y+p.getCentre().getX())*heightPixel, widthPixel, heightPixel);
            int xPiece, yPiece;
            for (Coordonnees coordonnees : coords) {
                xPiece = coordonnees.getX();
                yPiece = coordonnees.getY();
                g.fillRect((x+yPiece)*widthPixel, (y+xPiece)*heightPixel, widthPixel, heightPixel);
            }
        } catch (Exception e) {
            return;
        }
    }

    private void paintProchainePieces(Graphics g){
        ArrayList<Piece> prochainesPieces = new ArrayList<>();
        int nbPiece = terrain.getProchainePiece().size();
        Piece piece;
        for (int i = 0; i < 5; ++i){
            try {
                prochainesPieces.add(terrain.getProchainePiece().get(i));
            } catch (IndexOutOfBoundsException e) {
                prochainesPieces.add(terrain.getProchaineProchainePiece().get(i-nbPiece));
            }
        }
        
        g.setColor(Color.BLACK);
        g.drawRect((paddingLeft+Terrain.longueur)*widthPixel, 3*heightPixel, 6*widthPixel, (Terrain.hauteur-4)*heightPixel);
        for (int i = 0; i < prochainesPieces.size(); ++i) {
            piece = prochainesPieces.get(i);
            paintPiece(g, piece, paddingLeft+Terrain.longueur+2, (5+3*i));
        }
    }

    private void paintPieceRetenue(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect((paddingLeft-6)*widthPixel, 3*heightPixel, 6*widthPixel, 4*heightPixel);
        paintPiece(g, terrain.getPieceRetenu(), paddingLeft-4, 5);
    }

    private void paintDropedPiece(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        int x, y;
        for (Coordonnees coords : terrain.caseLieesFaireTomber()) {
            x = coords.getX();
            y = coords.getY();
            g.fillRect((paddingLeft+y)*widthPixel, (3+x)*heightPixel, widthPixel, heightPixel);
        }
    }

    public void tournerDroite(){
        terrain.tournerDroitePieceActuelle();
    }

    public void tournerGauche(){
        terrain.tournerGauchePieceActuelle();
        // ArrayList<Coordonnees> caseLiees = terrain.getPieceActuel().coordonneesLiees();
        // int x, y;
        // for (Coordonnees coordonnees : caseLiees) {
        //     x = coordonnees.getX();
        //     y = coordonnees.getY();
        //     if (terrain.getTerrain().get(x).get(y) != null){
        //         terrain.trounerDroitePieceActuelle();
        //     }
        // }
    }

    public void deplacerDroite(){
        terrain.deplacerDroitePieceActuelle();
    }

    public void deplacerGauche(){
        terrain.deplacerGauchePieceActuelle();
    }

    public void deplacerBas(){
        if (terrain.peutDescendre()){
            terrain.deplacerBasPieceActuelle();
        }
        else{
            terrain.poseLaPieceActuelle();
            terrain.clearLine();
            
        }
    }

    public void retenirPiece(){
        terrain.echangerPiece();
    }

    public void faireTomber() {
        try {
            terrain.faireTomber();
        } catch (IllegalStateException e) {
            aPerdu = true;
        }
        
    }

    public boolean getAPerdu(){
        return this.aPerdu;
    }

    public void APerdu(){
        this.aPerdu = true;
    }
    public Terrain getTerrain(){
        return terrain;
    }

}
