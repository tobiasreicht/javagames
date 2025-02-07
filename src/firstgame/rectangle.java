package firstgame;

import org.newdawn.slick.*;
import org.newdawn.slick.Input;

import org.newdawn.slick.tests.AnimationTest;

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
        this.cinimini = new Image("firstgame/cinimini.png"); // Lade das Bild
        this.x = 100;
        this.y = 100;
        this.speed = 5.0f;

    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

        Input input = gameContainer.getInput();

        if (input.isKeyDown(Input.KEY_D)) {
            this.x += (float) delta / this.speed;
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


    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.drawImage(this.cinimini, this.x, this.y);
        graphics.drawString("welcome to cinimini runner", 500, 50);
    }
}

