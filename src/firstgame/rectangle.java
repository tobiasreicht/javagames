package firstgame;

import org.newdawn.slick.*;
import org.newdawn.slick.Input;

public class rectangle extends BasicGame {
    private float x;
    private float y;
    private float speed;
    private Image cinimini;

    public rectangle(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        // Passe den Pfad zum Bild entsprechend an, falls es in einem anderen Ordner liegt
        this.cinimini = new Image("firstgame/Crazy-Square.png");
        this.x = 100;
        this.y = 100;
        this.speed = 2.0f;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_D)) {
            this.x +=  (float) delta / this.speed;
        }
        if (input.isKeyDown(Input.KEY_S)) {
            this.y += (float) delta / this.speed;
        }
        if (input.isKeyDown(Input.KEY_A)) {
            this.x -= (float) delta / this.speed;
        }
        if (input.isKeyDown(Input.KEY_W)) {
            this.y -= (float) delta / this.speed;
        }


        // Teleportation: Bei Tastendruck E wird der Spieler zufällig über den Screen teleportiert
        if (input.isKeyPressed(Input.KEY_E)) {
            // Setze x auf einen zufälligen Wert zwischen 0 und (Screenbreite - Bildbreite)
            this.x = (float)(Math.random() * (gameContainer.getWidth() - cinimini.getWidth()));
            this.y = (float)(Math.random() * (gameContainer.getHeight() - cinimini.getHeight()));
        }

        // Begrenzungen mit GameRules anwenden (zum Beispiel für die horizontale Grenze)
        this.x = gamerules.enforceHorizontalBoundary(gameContainer, this.x, this.cinimini);

        // Optionale vertikale Begrenzung (falls gewünscht)
        this.y = gamerules.enforceVerticalBoundary(gameContainer, this.y, this.cinimini);


        this.x = gamerules.enforceHorizontalBoundary(gameContainer, this.x, this.cinimini);
        this.y = gamerules.enforceVerticalBoundary(gameContainer, this.y, this.cinimini);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawImage(this.cinimini, this.x, this.y);
        graphics.drawString("welcome to cinimini runner", 500, 50);
    }
}
