package checkers.src;

public class Piece {

    private int player;
    private boolean isKing;

    public Piece(int player) {
        this.player = player;
        this.isKing = false;
    }

    public int getPlayer() {
        return player;
    }

    public boolean isKing() {
        return isKing;
    }

    public void makeKing() {
        isKing = true;
    }

    // Weitere Methoden für die Bewegung und Umwandlung
}