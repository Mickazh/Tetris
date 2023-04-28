package Pieces;

import java.awt.Color;

public class PieceZ extends Piece{

    public PieceZ(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.RED);
        getCaseLie().add(new Coordonnees(-1, 0));
        getCaseLie().add(new Coordonnees(-1, -1));
        getCaseLie().add(new Coordonnees(0, 1));
    }
    
}
