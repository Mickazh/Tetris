package Pieces;

import java.awt.Color;

public class PieceO extends Piece{

    public PieceO(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.YELLOW);
        getCaseLie().add(new Coordonnees(0, 1));
        getCaseLie().add(new Coordonnees(-1, 1));
        getCaseLie().add(new Coordonnees(-1, 0));
    }

    @Override
    public void tournerDroite(){

    }

    @Override
    public void tournerGauche(){

    }
}
