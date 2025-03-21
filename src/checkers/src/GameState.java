package checkers.src;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;
import java.util.ArrayList;

public class GameState extends BasicGameState {

    private int stateID;
    private board gameBoard;
    private Resources resources;
    private int selectedRow = -1, selectedCol = -1;
    private int currentPlayer = 2; // 1 für Spieler 1 (schwarz), 2 für Spieler 2 (weiß)

    public GameState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // Initialisierung des Spielzustands
        gameBoard = new board(); // Spielbrett initialisieren
        resources = new Resources(); // Ressourcen laden
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // Rendern des Spielbretts und der Spielsteine
        renderBoard(g);
        renderPieces(g);

        // Rendern der Auswahl (gelber Rahmen um den ausgewählten Spielstein)
        if (selectedRow != -1 && selectedCol != -1) {
            g.setColor(org.newdawn.slick.Color.yellow);
            g.setLineWidth(3); // Dicke des Rahmens
            g.drawRect(selectedCol * 128, selectedRow * 128, 128, 128);

            // Rendern der möglichen Züge (grüne Rahmen um die möglichen Felder)
            renderPossibleMoves(g, selectedRow, selectedCol);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        // Aktualisierung der Spielzustände
        handleInput(container.getInput(), game);
    }

    private void renderBoard(Graphics g) {
        try {
            Image boardImage = new Image("checkers/res/board.png");
            boardImage.draw(0, 0); // Zeichne das Spielbrett
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private void renderPieces(Graphics g) {
        int[][] board = gameBoard.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != 0) {
                    Image pieceImage = resources.getPieceImage(board[row][col], isKing(row, col));
                    pieceImage.draw(col * 128, row * 128); // Verdoppelte Position
                }
            }
        }
    }

    private void renderPossibleMoves(Graphics g, int selectedRow, int selectedCol) {
        List<int[]> possibleMoves = gameBoard.getPossibleMoves(selectedRow, selectedCol);

        g.setColor(org.newdawn.slick.Color.green); // Farbe für die Markierung
        g.setLineWidth(3); // Dicke des Rahmens

        for (int[] move : possibleMoves) {
            int row = move[0];
            int col = move[1];
            g.drawRect(col * 128, row * 128, 128, 128); // Zeichne einen Rahmen um das Feld
        }
    }

    private boolean isKing(int row, int col) {
        int piece = gameBoard.getBoard()[row][col];
        return piece == 3 || piece == 4; // 3: König von Spieler 1, 4: König von Spieler 2
    }

    private void handleInput(Input input, StateBasedGame game) {
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            int mouseX = input.getMouseX();
            int mouseY = input.getMouseY();

            // Berechne die Zeile und Spalte basierend auf der Mausposition
            int row = mouseY / 128; // Verdoppelte Feldgröße
            int col = mouseX / 128; // Verdoppelte Feldgröße

            if (selectedRow == -1 && selectedCol == -1) {
                // Wähle einen Spielstein aus
                if (gameBoard.getBoard()[row][col] == currentPlayer || gameBoard.getBoard()[row][col] == currentPlayer + 2) {
                    selectedRow = row;
                    selectedCol = col;
                }
            } else {
                // Bewege den ausgewählten Spielstein
                if (gameBoard.isValidMove(selectedRow, selectedCol, row, col)) {
                    gameBoard.movePiece(selectedRow, selectedCol, row, col);

                    // Überprüfe, ob das Spiel vorbei ist
                    checkGameOver(game);

                    // Wechsle den Spieler nach jedem Zug
                    selectedRow = -1;
                    selectedCol = -1;
                    currentPlayer = (currentPlayer == 1) ? 2 : 1; // Spieler wechseln
                } else {
                    selectedRow = -1;
                    selectedCol = -1;
                }
            }
        }
    }

    private void checkGameOver(StateBasedGame game) {
        boolean player1HasMoves = gameBoard.hasValidMoves(1);
        boolean player2HasMoves = gameBoard.hasValidMoves(2);

        boolean player1HasPieces = gameBoard.hasPieces(1);
        boolean player2HasPieces = gameBoard.hasPieces(2);

        if (!player1HasMoves || !player1HasPieces) {
            ((GameOverState) game.getState(2)).setMessage("Spieler 2 gewinnt!");
            game.enterState(2); // Wechsle zum Spielende-Bildschirm
        } else if (!player2HasMoves || !player2HasPieces) {
            ((GameOverState) game.getState(2)).setMessage("Spieler 1 gewinnt!");
            game.enterState(2); // Wechsle zum Spielende-Bildschirm
        }
    }

    public void restartGame() {
        // Zurücksetzen des Spielbretts und des Spielzustands
        gameBoard = new board(); // Neues Spielbrett erstellen
        selectedRow = -1;
        selectedCol = -1;
        currentPlayer = 1; // Spieler 1 beginnt
    }
}