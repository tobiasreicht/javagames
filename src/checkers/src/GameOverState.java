package checkers.src   ;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {

    private int stateID;
    private String message;

    public GameOverState(int stateID) {
        this.stateID = stateID;
    }

    @Override
    public int getID() {
        return stateID;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        // Initialisierung des Spielende-Bildschirms
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        // Zeichne die Nachricht und die Optionen
        g.setColor(org.newdawn.slick.Color.white);
        g.drawString(message, 100, 100);
        g.drawString("1. Neustart", 100, 150);
        g.drawString("2. Beenden", 100, 200);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();

        // Überprüfe die Eingabe des Spielers
        if (input.isKeyPressed(Input.KEY_1)) {
            // Neustart
            game.enterState(1); // Wechsle zurück zum Hauptspielzustand
            ((GameState) game.getState(1)).restartGame(); // Setze das Spiel zurück
        } else if (input.isKeyPressed(Input.KEY_2)) {
            // Beenden
            container.exit();
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}