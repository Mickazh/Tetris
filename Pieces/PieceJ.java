package Pieces;

import java.awt.Color;

public class PieceJ extends Piece{

    public PieceJ(TypePiece typeDePiece, Coordonnees centre) {
        super(typeDePiece, centre, Color.BLUE);
        getCaseLie().add(new Coordonnees(0, -1));
        getCaseLie().add(new Coordonnees(-1, -1));
        getCaseLie().add(new Coordonnees(0, 1)); 
    }
    
}
