package Pieces;

import java.awt.Color;

public class PieceS extends Piece{

    public PieceS(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.GREEN);
        getCaseLie().add(new Coordonnees(-1, 0));
        getCaseLie().add(new Coordonnees(-1, 1));
        getCaseLie().add(new Coordonnees(0, -1));
    }
    /**
     * . S S
     * S S .
     * . . .
     */
    
}
