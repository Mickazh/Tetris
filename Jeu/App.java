package Jeu;



public class App {
    public static void main(String[] args) throws Exception {
        // HashMap<Integer, Runnable> commandes = new HashMap<>();
        // commandes.put(null, null);

        //-------------- Pour tester les pieces ------------

        // Coordonnees centre = new Coordonnees(5, 5);
        // Piece p = Piece.newPiece(TypePiece.T, centre);
        // Terrain t = new Terrain(p);
        // t.terrain.get(5).set(5, p);
        // t.poseLaPieceActuelle();
        
        // t.genereNouvellesPieces();
        // System.out.println(t.toStringTerrain());
        // for (char[] linge : t.toCharTerrain()) {
        //     for (char c : linge) {
        //         System.out.print(c + " ");
        //     }
        //     System.out.print(System.lineSeparator());
        // }
        Game game = new Game();
    }

}
