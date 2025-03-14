package checkers.src;

public class GameLogic {

    private checkers.src.board board;

    public GameLogic(board board) {
        this.board = board;
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Überprüfung, ob der Zug gültig ist
        return true; // Platzhalter
    }

    public boolean isGameOver() {
        // Überprüfung, ob das Spiel beendet ist
        return false; // Platzhalter
    }

    // Weitere Methoden für die Spielregeln
}