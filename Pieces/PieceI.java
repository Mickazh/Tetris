package Pieces;

import java.awt.Color;
import java.util.ArrayList;

public class PieceI extends Piece{
    private int rotation; //
    private int[][][] tableRotation = {{{0, -1}, {0, 1}, {0, 2}}, {{-1, 0}, {1, 0}, {2, 0}}, {{0, -2}, {0, -1}, {0, 1}}, {{-2, 0}, {-1, 0}, {1, 0}}};
    private int[][] tableCentreRotatin = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public PieceI(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.CYAN);
        this.rotation = 0;
        getCaseLie().add(new Coordonnees(0, -1));
        getCaseLie().add(new Coordonnees(0, 1));
        getCaseLie().add(new Coordonnees(0, 2));
    }
    
    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void incrementRotation(){
        ++this.rotation;
    }

    @Override
    public void tournerGauche(){
        if (rotation <= 0){
            rotation = 4;
        }
        int[][] rotations = tableRotation[(rotation-1)%4];
        int[] centreRotations = tableCentreRotatin[rotation%4];
        ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        for (int i = 0; i < rotations.length; ++i){
            int[] uneCase = rotations[i];
            coordonnees.add(new Coordonnees(uneCase[0], uneCase[1]));
        }
        this.setCaseLie(coordonnees);
        Coordonnees centre = this.getCentre();
        this.setCentre(centre.getX()-centreRotations[0], centre.getY()-centreRotations[1]);

        super.wallBounce();
        --rotation;
    }


    @Override
    public void tournerDroite(){
        incrementRotation();
        int[][] rotations = tableRotation[rotation%4]; //rotations = les cases liées après une rotation
        int[] centreRotations = tableCentreRotatin[rotation%4];
        ArrayList<Coordonnees> coordonnees = new ArrayList<>();
        for (int i = 0; i < rotations.length; ++i){
            int[] uneCase = rotations[i];
            coordonnees.add(new Coordonnees(uneCase[0], uneCase[1]));
        }
        this.setCaseLie(coordonnees);
        Coordonnees centre = this.getCentre();
        this.setCentre(centre.getX()+centreRotations[0], centre.getY()+centreRotations[1]);

        super.wallBounce();
    }

    @Override
    public void resetRotation(){
        rotation = 0;
    }
}
