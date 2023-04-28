package Jeu;
import Pieces.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class Terrain {
    public static final int longueur = 10, hauteur = 20;
    private ArrayList<ArrayList<Piece>> terrain;
    private ArrayList<Piece> prochainePiece, prochaineProchainePiece;
    private Piece pieceActuel, pieceRetenu;
    private int score;

    public Terrain(){
        terrain = new ArrayList<ArrayList<Piece>>();
        for (int i = 0; i < hauteur; ++i){
            terrain.add(i, new ArrayList<>());
            for (int j = 0; j < longueur; ++j){
                terrain.get(i).add(j, null);
            }
        }
        score = 0;
        prochainePiece = giveNouvellesPieces();
        prochaineProchainePiece = giveNouvellesPieces();
        changerPieceActuelle();
        // pieceActuel = prochainePiece.get(0);
        // prochainePiece.remove(0);
        // pieceActuel.setCentre(1, 4);
        // System.out.println(pieceActuel.coordonneesLieesTournerGauche());
        // System.out.println(pieceActuel.coordonneesLieesTournerDroite());
    }

    private ArrayList<Piece> giveNouvellesPieces() {
        TypePiece[] types = TypePiece.values();
        ArrayList<Piece> prochainePieces = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < types.length-1; ++i){ //permet de melanger les pieces (sans la derniere, qui est une piece particuliere)
            int rdmIndextoSwap = rand.nextInt(types.length-1);
            TypePiece tmp = types[rdmIndextoSwap];
            types[rdmIndextoSwap] = types[i];
            types[i] = tmp;
        }
        for (int i = 0; i < types.length-1; ++i){
            Coordonnees centre = new Coordonnees(0, 0);
            Piece p = Piece.newPiece(types[i], centre, null);
            prochainePieces.add(p);
        }
        return prochainePieces;
    }

    public ArrayList<Piece> getProchainePiece() {
        return prochainePiece;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("prochainePiece=");
        for (Piece piece : prochainePiece) {
            sb.append(piece.toString());
        }
        return sb.toString();
    }

    public String toStringTerrain(){
        StringBuilder sb = new StringBuilder();
        for (ArrayList<Piece> ligne: this.terrain) {
            for (Piece piece : ligne) {
                if (piece == null){
                    sb.append(". ");
                }
                else{
                    sb.append(piece.getTypeDePiece() + " ");
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public char[][] toCharTerrain(){
        char[][] str = new char[20][11];
        for (int i = 0; i < hauteur; ++i){
            for (int j = 0; j < longueur; ++j){
                if (this.terrain.get(i).get(j) == null){
                    str[i][j] = '.';
                }
                else if (this.terrain.get(i).get(j).getTypeDePiece() == TypePiece.X){
                    str[i][j] = 'X';
                }
            }
            str[i][10] = '\n';
        }
        try {
            for (Coordonnees coord : this.pieceActuel.coordonneesLiees()) {
                int x = coord.getX(), y = coord.getY();
                str[x][y] = pieceActuel.getTypeDePiece().toString().charAt(0);
            }
        } catch (NullPointerException e) {
            
        }
        return str;
    }

    public boolean peutDeplacerGauche() {
        return peutDeplacerDroiteGauche(false);
    }

    public boolean peutDeplacerDroite() {
        return peutDeplacerDroiteGauche(true);
    }
    
    /**
     * Fonction pour éviter de répéter deux fois le même code en changeant une seule ligne
     * @param droite permet d'indiquer si on cherche à savoir si on déplace à droite ou à gauche
     * @return
     */
    private boolean peutDeplacerDroiteGauche(boolean droite){
        ArrayList<Coordonnees> coords = this.pieceActuel.coordonneesLiees();
        int x, y;
        try {
            for (Coordonnees coordonnees : coords) {
                x = coordonnees.getX();
                y = coordonnees.getY();
                if (droite){
                    y = y + 1;
                }
                else{
                    y = y - 1;
                }
                if (y < 0 || y >= hauteur){
                    return false;
                }
                if (terrain.get(x).get(y) != null){
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        
        return true;
    }
    
    public boolean peutDescendre(){
        return peutDescendre(this.pieceActuel);
    }

    public boolean peutDescendre(Piece p){
        ArrayList<Coordonnees> coords = p.coordonneesLiees();
        int x, y;
        for (Coordonnees coord : coords) {
            x = coord.getX();
            y = coord.getY();
            x += 1;
            if (x >= Terrain.hauteur){
                return false;
            }
            if (terrain.get(x).get(y) != null){
                return false;
            }
        }
        return true;
    }

    public boolean peutTrournerGauche(){
        ArrayList<Coordonnees> coords = this.pieceActuel.coordonneesLiees();
        int x, y;
        // ArrayList<Coordonnees> coordLie = this.pieceActuel.
        try {
            for (Coordonnees coord : coords) {
                x = coord.getX();
                y = coord.getY();
                if (terrain.get(x).get(y) != null){
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        
        return true;
    }

    public void poseLaPieceActuelle(){
        ArrayList<Coordonnees> casesLiees = this.pieceActuel.coordonneesLiees();
        int x, y;
        for (Coordonnees coordonnees : casesLiees) {
            x = coordonnees.getX();
            y = coordonnees.getY();
            this.terrain.get(x).set(y, Piece.newPiece(TypePiece.X, new Coordonnees(x, y), this.pieceActuel.getColor()));
        }
        if (prochainePiece.size() == 0){
            this.prochainePiece = this.prochaineProchainePiece;
            this.prochaineProchainePiece = giveNouvellesPieces();
        }
        changerPieceActuelle();
        try {
            this.pieceRetenu.peutEchanger();
        } catch (NullPointerException e) {
            return;
        }
    }

    public void changerPieceActuelle(){
        this.pieceActuel = prochainePiece.get(0);
        this.prochainePiece.remove(0);
        this.pieceActuel.setCentre(0, 4);
        ArrayList<Coordonnees> coordonnees = this.pieceActuel.coordonneesLiees();
        int x, y;
        for (Coordonnees coords : coordonnees) {
            x = coords.getX()+1;
            y = coords.getY();
            if (terrain.get(x).get(y) != null){
                throw new IllegalStateException();
            }
        }
        if (peutDescendre())
            deplacerBasPieceActuelle();
        tournerGauchePieceActuelle();
    }

    public void clearLine(){
        boolean completeLine;
        for (int i = 0; i < hauteur; ++i){
            completeLine = true;
            for (int j = 0; j < longueur; ++j){
                if (terrain.get(i).get(j) == null){
                    completeLine = false;
                }
            }
            if (completeLine){
                ++score;
                terrain.remove(i);
                ArrayList<Piece> nouvelleLigne = new ArrayList<>();
                for (int k = 0; k < longueur; ++k){
                    nouvelleLigne.add(k, null);
                }
                terrain.add(0, nouvelleLigne);
            }
        }
    }

    public void tournerDroitePieceActuelle(){
        ArrayList<Coordonnees> coordonnees = this.pieceActuel.coordonneesLieesTournerDroite();
        int x, y;
        for (Coordonnees coords : coordonnees) {
            x = coords.getX();
            y = coords.getY();
            if (x >= hauteur){
                this.pieceActuel.getCentre().moins1X();
                tournerDroitePieceActuelle();
                return;
            }
            if (terrain.get(x).get(y) != null){
                return;
            }
            
        }
        this.pieceActuel.tournerDroite();
    }

    public void tournerGauchePieceActuelle(){
        ArrayList<Coordonnees> coordonnees = this.pieceActuel.coordonneesLieesTournerGauche();
        int x, y;
        for (Coordonnees coords : coordonnees) {
            x = coords.getX();
            y = coords.getY();
            if (x >= hauteur){
                this.pieceActuel.getCentre().moins1X();
                tournerGauchePieceActuelle();
                return;
            }
            if (terrain.get(x).get(y) != null){
                return;
            }
        }
        this.pieceActuel.tournerGauche();
    }

    public void deplacerDroitePieceActuelle(){
        if (peutDeplacerDroite()){
            this.pieceActuel.droite();
        }
    }

    public void deplacerGauchePieceActuelle(){
        if (peutDeplacerGauche()){
            this.pieceActuel.gauche();
        }
    }

    public void deplacerBasPieceActuelle(){
        this.pieceActuel.descendre();
    }

    public ArrayList<ArrayList<Piece>> getTerrain(){
        return terrain;
    }

    public ArrayList<Piece> getProchaineProchainePiece() {
        return prochaineProchainePiece;
    }

    public Piece getPieceActuel() {
        return pieceActuel;
    }

    public Piece getPieceRetenu(){
        return this.pieceRetenu;
    }

    public int getScore(){
        return this.score;
    }
    
    public void echangerPiece(){
        if (this.pieceRetenu == null){
            this.pieceActuel.setCaseLie(Piece.newPiece(this.pieceActuel.getTypeDePiece(), null, null).getCaseLie());
            this.pieceRetenu = pieceActuel;
            this.pieceRetenu.setCentre(0, 0);
            this.pieceRetenu.nePeutPlusEchanger();
            this.pieceRetenu.resetRotation();
            changerPieceActuelle();
            return;
        }
        // if (!this.pieceRetenu.getPeutEchanger()){
        //     return;
        // }
        this.pieceActuel.setCaseLie(Piece.newPiece(this.pieceActuel.getTypeDePiece(), null, null).getCaseLie());
        Piece tmp = this.pieceRetenu;
        this.pieceRetenu = this.pieceActuel;
        this.pieceRetenu.nePeutPlusEchanger();
        this.pieceRetenu.setCentre(0, 0);
        this.pieceActuel = tmp;
        this.pieceActuel.setCentre(0, 4);
        this.pieceActuel.resetRotation();
        this.pieceActuel.tournerGauche();
        if (peutDescendre())
            deplacerBasPieceActuelle();
    }

    public void faireTomber() {
        while (this.peutDescendre()) {
            deplacerBasPieceActuelle();
        }
        poseLaPieceActuelle();
        clearLine();
    }

    public ArrayList<Coordonnees> caseLieesFaireTomber(){
        int x = pieceActuel.getCentre().getX(), y = pieceActuel.getCentre().getY();
        Piece pieceTmp = Piece.newPiece(pieceActuel.getTypeDePiece(), new Coordonnees(x, y), Color.LIGHT_GRAY);
        pieceTmp.setCaseLie(pieceActuel.getCaseLie());
        while (peutDescendre(pieceTmp)) {
            pieceTmp.getCentre().plus1X();
        }
        return pieceTmp.coordonneesLiees();
    }
}
