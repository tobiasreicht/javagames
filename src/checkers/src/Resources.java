package checkers.src;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Resources {

    private Image piece1;
    private Image piece2;
    private Image king1;
    private Image king2;

    public Resources() throws SlickException {
        // Laden der Bilder
        piece1 = new Image("checkers/res/piece1.png");
        piece2 = new Image("checkers/res/piece2.png");
        king2 = new Image("checkers/res/king1.png");
        king1 = new Image("checkers/res/king2.png");
    }

    public Image getPieceImage(int player, boolean isKing) {
        if (player == 1 || player == 3) { // Spieler 1 oder König von Spieler 1
            return isKing ? king1 : piece1;
        } else if (player == 2 || player == 4) { // Spieler 2 oder König von Spieler 2
            return isKing ? king2 : piece2;
        } else {
            // Fallback: Gib ein Standardbild zurück (z. B. piece1)
            System.err.println("Ungültiger Spielerwert: " + player);
            return piece1;
        }
    }
}