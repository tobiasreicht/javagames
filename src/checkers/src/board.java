package checkers.src;

import java.util.List;
import java.util.ArrayList;

public class board {

    private int[][] board;

    public board() {
        board = new int[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        // Setzen der Startpositionen der Spielsteine
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 1) {
                    if (row < 3) {
                        board[row][col] = 1; // Spieler 1
                    } else if (row > 4) {
                        board[row][col] = 2; // Spieler 2
                    }
                }
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    /**
     * Überprüft, ob ein Zug gültig ist.
     */
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Überprüfe, ob die Zielposition innerhalb des Bretts liegt
        if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
            return false;
        }

        // Überprüfe, ob die Zielposition frei ist
        if (board[toRow][toCol] != 0) {
            return false;
        }

        int player = board[fromRow][fromCol];
        int rowDiff = toRow - fromRow;
        int colDiff = Math.abs(toCol - fromCol);

        // Normale Bewegungen (ein Feld diagonal)
        if (Math.abs(rowDiff) == 1 && colDiff == 1) {
            if (player == 1 || player == 3) { // Spieler 1 oder König von Spieler 1
                if (rowDiff == 1) { // Nur nach unten
                    return true;
                }
            } else if (player == 2 || player == 4) { // Spieler 2 oder König von Spieler 2
                if (rowDiff == -1) { // Nur nach oben
                    return true;
                }
            }
        }

        // Bewegungen für Könige (ein Feld diagonal in alle Richtungen)
        if (player == 3 || player == 4) { // König von Spieler 1 oder Spieler 2
            if (Math.abs(rowDiff) == 1 && colDiff == 1) {
                return true;
            }
        }

        // Schlagen von Gegnersteinen (zwei Felder diagonal)
        if (Math.abs(rowDiff) == 2 && colDiff == 2) {
            int middleRow = (fromRow + toRow) / 2;
            int middleCol = (fromCol + toCol) / 2;
            int middlePiece = board[middleRow][middleCol];

            if (middlePiece != 0 && middlePiece != player && middlePiece != player + 2) {
                return true;
            }
        }

        return false;
    }



    /**
     * Bewegt einen Spielstein von einer Position zu einer anderen.
     */
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        int player = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0; // Alte Position leeren
        board[toRow][toCol] = player; // Neue Position besetzen

        // Überprüfe, ob der Spielstein ein König wird
        if ((player == 1 && toRow == 7) || (player == 2 && toRow == 0)) {
            board[toRow][toCol] = player + 2; // König (Spieler 1: 3, Spieler 2: 4)
            System.out.println("Spielstein wurde in einen König umgewandelt: " + board[toRow][toCol]);
        }

        // Schlagen von Gegnersteinen
        if (Math.abs(toRow - fromRow) == 2) {
            int middleRow = (fromRow + toRow) / 2;
            int middleCol = (fromCol + toCol) / 2;
            board[middleRow][middleCol] = 0; // Gegnerischen Stein entfernen
        }
    }

    public boolean hasMoreJumps(int row, int col) {
        int player = board[row][col];
        int[][] directions = {{-2, -2}, {-2, 2}, {2, -2}, {2, 2}}; // Mögliche Sprungrichtungen

        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (isValidMove(row, col, newRow, newCol)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasPieces(int player) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == player + 2) { // Normaler Stein oder König
                    return true; // Es gibt noch Spielsteine
                }
            }
        }
        return false; // Keine Spielsteine mehr
    }


    public boolean hasValidMoves(int player) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == player + 2) { // Normaler Stein oder König
                    // Überprüfe alle möglichen Züge für diesen Stein
                    int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Alle vier diagonalen Richtungen
                    for (int[] dir : directions) {
                        int newRow = row + dir[0];
                        int newCol = col + dir[1];

                        if (isValidMove(row, col, newRow, newCol)) {
                            return true; // Es gibt mindestens einen gültigen Zug
                        }
                    }
                }
            }
        }
        return false; // Keine gültigen Züge gefunden
    }

    public List<int[]> getPossibleMoves(int row, int col) {
        List<int[]> possibleMoves = new ArrayList<>();
        int player = board[row][col];

        // Mögliche Richtungen für normale Spieler und Könige
        int[][] directions;
        if (player == 1 || player == 3) { // Spieler 1 oder König von Spieler 1
            directions = new int[][]{{1, -1}, {1, 1}}; // Nach unten
        } else if (player == 2 || player == 4) { // Spieler 2 oder König von Spieler 2
            directions = new int[][]{{-1, -1}, {-1, 1}}; // Nach oben
        } else {
            return possibleMoves; // Ungültiger Spieler
        }

        // Füge Richtungen für Könige hinzu (falls zutreffend)
        if (player == 3 || player == 4) { // König
            directions = new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Alle vier Richtungen
        }

        // Überprüfe alle möglichen Züge (einfache Bewegungen und Sprünge)
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            // Einfache Bewegung (ein Feld diagonal)
            if (isValidMove(row, col, newRow, newCol)) {
                possibleMoves.add(new int[]{newRow, newCol});
            }

            // Sprung über Gegnerstein (zwei Felder diagonal)
            int jumpRow = row + 2 * dir[0];
            int jumpCol = col + 2 * dir[1];

            if (isValidMove(row, col, jumpRow, jumpCol)) {
                possibleMoves.add(new int[]{jumpRow, jumpCol});
            }
        }

        return possibleMoves;
    }
}