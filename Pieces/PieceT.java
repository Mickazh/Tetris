package Pieces;

import java.awt.Color;

public class PieceT extends Piece{

    public PieceT(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.MAGENTA);
        getCaseLie().add(new Coordonnees(0, -1));
        getCaseLie().add(new Coordonnees(-1, 0));
        getCaseLie().add(new Coordonnees(0, 1));
    }
    /**
     * . T .
     * T T T
     * . . .
     */
    
}
