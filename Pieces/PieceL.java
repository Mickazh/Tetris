package Pieces;

import java.awt.Color;

public class PieceL extends Piece{

    public PieceL(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.ORANGE);
        getCaseLie().add(new Coordonnees(0, -1));
        getCaseLie().add(new Coordonnees(-1, 1));
        getCaseLie().add(new Coordonnees(0, 1));
    }
}
