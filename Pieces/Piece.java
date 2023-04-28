package Pieces;

import java.util.ArrayList;
import java.awt.Color;

import Jeu.Terrain;

public class Piece {

    private TypePiece typeDePiece;
    private Coordonnees centre;
    private ArrayList<Coordonnees> caseLie;
    private Color couleur;
    private boolean peutEchanger;


    public Piece(TypePiece typeDePiece, Coordonnees centre, Color c) {
        this.typeDePiece = typeDePiece;
        this.centre = centre;
        this.caseLie = new ArrayList<>();
        this.couleur = c;
        this.peutEchanger = true;
        // this.determineCaseLie();
    }

    public static Piece newPiece(TypePiece typeDePiece, Coordonnees centre, Color c) {
        switch (typeDePiece) {
            case O:
                return new PieceO(typeDePiece, centre);
            case I:
                return new PieceI(typeDePiece, centre);
            case S:
                return new PieceS(typeDePiece, centre);
            case Z:
                return new PieceZ(typeDePiece, centre);
            case L:
                return new PieceL(typeDePiece, centre);
            case J:
                return new PieceJ(typeDePiece, centre);
            case T:
                return new PieceT(typeDePiece, centre);
            case X:
                return new PieceX(typeDePiece, centre, c);
            default:
                return null;
        }
    }

    public void setCentre(int x, int y){
        this.centre.setX(x);
        this.centre.setY(y);
    }

    // private void setCaseLieO(){
        // caseLie.add(new Coordonnees(0, 1));
        // caseLie.add(new Coordonnees(1, 1));
        // caseLie.add(new Coordonnees(1, 0));
    // }

    // private void setCaseLieI(){
        // caseLie.add(new Coordonnees(0, 1));
        // caseLie.add(new Coordonnees(0, 2));
        // caseLie.add(new Coordonnees(0, 3));
    // }

    // private void setCaseLieS(){
        // caseLie.add(new Coordonnees(-1, 0));
        // caseLie.add(new Coordonnees(1, 1));
        // caseLie.add(new Coordonnees(0, -1));
    // }

    // private void setCaseLieZ(){
        // caseLie.add(new Coordonnees(-1, 0));
        // caseLie.add(new Coordonnees(-1, -1));
        // caseLie.add(new Coordonnees(0, 1));
    // }

    // private void setCaseLieL(){
        // caseLie.add(new Coordonnees(0, -1));
        // caseLie.add(new Coordonnees(-1, 1));
        // caseLie.add(new Coordonnees(0, 1));
    // }

    // private void setCaseLieJ(){
        // caseLie.add(new Coordonnees(0, -1));
        // caseLie.add(new Coordonnees(-1, -1));
        // caseLie.add(new Coordonnees(0, 1)); 
    // }

    // private void setCaseLieT(){
        // caseLie.add(new Coordonnees(0, -1));
        // caseLie.add(new Coordonnees(-1, 0));
        // caseLie.add(new Coordonnees(0, 1));
    // }

    // private void determineCaseLie(){
    //     switch (typeDePiece) {
    //         case O:
    //             setCaseLieO();
    //             break;
    //         case I:
    //             setCaseLieI();
    //             break;
    //         case S:
    //             setCaseLieS();
    //             break;
    //         case Z:
    //             setCaseLieZ();
    //             break;
    //         case L:
    //             setCaseLieL();
    //             break;
    //         case J:
    //             setCaseLieJ();
    //             break;
    //         case T:
    //             setCaseLieT();
    //             break;
    //         default:
    //             break;
    //     }
    // }

    @Override
    public String toString() {
        return "Piece " + typeDePiece + ", centre=" + centre;
    }

    public Color getColor(){
        return this.couleur;
    }

    public TypePiece getTypeDePiece() {
        return typeDePiece;
    }

    public void tournerDroite(){
        int x, y;
        for (int i = 0; i < this.caseLie.size(); ++i) {
            Coordonnees coordonneesTemp = this.caseLie.get(i);
            x = coordonneesTemp.getX();
            y = coordonneesTemp.getY();
            coordonneesTemp.setX(y);
            coordonneesTemp.setY(-x);
        }
        wallBounce();
    }

    // public boolean peutTrournerGauche(){
    //     ArrayList<Coordonnees> coords = this.coordonneesLiees();
    //     int x, y;
    //     for (Coordonnees coord : coords) {
    //         x = coord.getX();
    //         y = coord.getY();
    //         if(x < )
    //     }
    // }

    public void tournerGauche(){
        int x, y;
        for (int i = 0; i < this.caseLie.size(); ++i) {
            Coordonnees coordonneesTemp = this.caseLie.get(i);
            x = coordonneesTemp.getX();
            y = coordonneesTemp.getY();
            coordonneesTemp.setX(-y);
            coordonneesTemp.setY(x);
        }
        wallBounce();
    }

    /**
     * Fonction qui permet de corriger les pieces si elles sont contre un mur
     */
    public void wallBounce(){
        ArrayList<Coordonnees> coordsLies = this.coordonneesLiees();
        int y;
        for (Coordonnees coordonnees : coordsLies) {
            y = coordonnees.getY();
            if (y < 0){
                this.droite();
                return;
            }
            else if (y > Terrain.longueur-1){
                this.gauche();
                return;
            }
        }
    }


    public void descendre(){
        this.centre.plus1X();
    }

    public void monter(){
        this.centre.moins1X();
    }

    public void gauche(){
        this.centre.moins1Y();
        this.wallBounce();
    }

    public void droite(){
        this.centre.plus1Y();
        this.wallBounce();
    }

    /**
     * 
     * @return Fonction permettant d'obtenir les coordonnees des cases liees a la piece
     */
    public ArrayList<Coordonnees> coordonneesLiees(){
        ArrayList<Coordonnees> coordonnes = new ArrayList<>();
        coordonnes.add(this.centre);
        int x, y, xCentre = this.centre.getX(), yCentre = this.centre.getY();
        for (Coordonnees coord : this.caseLie) {
            x = coord.getY();
            y = -coord.getX();
            x += xCentre;
            y += yCentre;
            coordonnes.add(new Coordonnees(x, y));
        }
        return coordonnes;
    }

    // public ArrayList<Coordonnees> coordonneesLieesTournerGauche(){
    //     ArrayList<Coordonnees> coordonnes = new ArrayList<>();
    //     coordonnes.add(this.centre);
    //     int x, y, xCentre = this.centre.getX(), yCentre = this.centre.getY();
    //     for (Coordonnees coord : this.caseLie) {
    //         x = -coord.getY();
    //         y = coord.getX();
    //         x = xCentre + x;
    //         y = yCentre + y;
    //         coordonnes.add(new Coordonnees(x, y));
    //     }
    //     return coordonnes;
    // }

    public ArrayList<Coordonnees> coordonneesLieesTournerGauche(){
        // ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        // coordonnees.add(centre);
        // int x, y, xCentre = this.centre.getX(), yCentre = this.centre.getY(), tmpX;
        // for (Coordonnees coord : this.caseLie){
        //     x = coord.getX();
        //     y = coord.getY();
        //     System.out.print("("+x+", "+y+") ->");
        //     tmpX = x;
        //     x = -y;
        //     y = tmpX;
        //     System.out.print("("+x+", "+y+")\n");
        //     x = xCentre + x;
        //     y = yCentre + y;
        //     // System.out.println(new Coordonnees(x, y));
        //     coordonnees.add(new Coordonnees(x, y));
        // }
        // return coordonnees;
        ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        tournerGauche();
        coordonnees = coordonneesLiees();
        tournerDroite();
        return coordonnees;
    }


    public ArrayList<Coordonnees> coordonneesLieesTournerDroite(){
        // ArrayList<Coordonnees> coordonnes = new ArrayList<>();
        // coordonnes.add(this.centre);
        // int x, y, xCentre = this.centre.getX(), yCentre = this.centre.getY();
        // for (Coordonnees coord : this.caseLie) {
        //     x = coord.getY();
        //     y = -coord.getX();
        //     x += xCentre;
        //     y += yCentre;
        //     coordonnes.add(new Coordonnees(x, y));
        // }
        // return coordonnes;
        ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        tournerDroite();
        coordonnees = coordonneesLiees();
        tournerGauche();
        return coordonnees;
    }


    public ArrayList<Coordonnees> getCaseLie() {
        return caseLie;
    }

    public Coordonnees getCentre() {
        return centre;
    }

    public boolean getPeutEchanger() {
        return this.peutEchanger;
    }

    public void nePeutPlusEchanger() {
        this.peutEchanger = false;
    }

    public void peutEchanger() {
        this.peutEchanger = true;
    }

    public void setCaseLie(ArrayList<Coordonnees> coords){
        this.caseLie = coords;
    }

    public void resetRotation() {} // ne fait rien pour les pieces qui ne sont pas des I
}
