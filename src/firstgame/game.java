package firstgame;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;
import java.util.ArrayList;
import java.util.List;

public class game extends BasicGame {
    private float x, y, speed;
    private Image cinimini;
    private TiledMap map;
    private List<Enemy> enemies;
    private Image enemyImage;
    private int enemyCount = 0; // Anzahl der Gegner
    private boolean gameOver = false;

    private float cameraX, cameraY;  // Kamera-Offset

    public game(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.cinimini = new Image("firstgame/ghost.png");
        this.x = gameContainer.getWidth() / 2;
        this.y = gameContainer.getHeight() / 2;
        this.speed = 0.2f;
        map = new TiledMap("firstgame/ownmap.tmx");
        this.enemyImage = new Image("firstgame/enemy.png");

        enemies = new ArrayList<>();
        for (int i = 0; i < enemyCount; i++) {
            // Generiere zufällige Positionen für Gegner
            float enemyX = (float) (Math.random() * (map.getWidth() * map.getTileWidth()));
            float enemyY = (float) (Math.random() * (map.getHeight() * map.getTileHeight()));

            // Stelle sicher, dass der Zombie nicht auf dem Spieler startet
            while (isOverlappingWithPlayer(enemyX, enemyY)) {
                enemyX = (float) (Math.random() * (map.getWidth() * map.getTileWidth()));
                enemyY = (float) (Math.random() * (map.getHeight() * map.getTileHeight()));
            }

            enemies.add(new Enemy(enemyX, enemyY, enemyImage, map));
        }

        // Kamera beginnt in der Mitte des Spiels
        cameraX = gameContainer.getWidth() / 2;
        cameraY = gameContainer.getHeight() / 2;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if (gameOver) {
            return; // Spiel beenden, wenn GameOver
        }

        Input input = gameContainer.getInput();
        float moveAmount = speed * delta;

        float newX = x;
        float newY = y;

        if (input.isKeyDown(Input.KEY_D)) newX += moveAmount;
        if (input.isKeyDown(Input.KEY_A)) newX -= moveAmount;
        if (input.isKeyDown(Input.KEY_S)) newY += moveAmount;
        if (input.isKeyDown(Input.KEY_W)) newY -= moveAmount;
        if (input.isKeyPressed(Input.KEY_E)) {
            newX = (float) (Math.random() * (gameContainer.getWidth() - cinimini.getWidth()));
            newY = (float) (Math.random() * (gameContainer.getHeight() - cinimini.getHeight()));
        }

        if (!isBlocked(newX, newY)) {
            x = newX;
            y = newY;
        }

        // Gegner bewegen sich in Richtung des Spielers
        for (Enemy enemy : enemies) {
            enemy.moveTowards(x, y, delta);

            // Prüfen, ob ein Gegner den Spieler berührt
            if (isCollidingWithEnemy(enemy)) {
                gameOver = true; // Spiel beenden
            }
        }

        // Update Kamera-Position basierend auf dem Spieler
        cameraX = x - gameContainer.getWidth() / 2;
        cameraY = y - gameContainer.getHeight() / 2;

        // Verhindere, dass die Kamera über den Rand der Map hinausgeht
        cameraX = Math.max(0, Math.min(cameraX, map.getWidth() * map.getTileWidth() - gameContainer.getWidth()));
        cameraY = Math.max(0, Math.min(cameraY, map.getHeight() * map.getTileHeight() - gameContainer.getHeight()));
    }

    private boolean isBlocked(float newX, float newY) {
        int wallLayer = map.getLayerIndex("wand");
        if (wallLayer == -1) return false;

        int tileWidth = map.getTileWidth();
        int tileHeight = map.getTileHeight();

        float avatarWidth = cinimini.getWidth();
        float avatarHeight = cinimini.getHeight();

        int startTileX = (int) newX / tileWidth;
        int startTileY = (int) newY / tileHeight;
        int endTileX = (int) (newX + avatarWidth) / tileWidth;
        int endTileY = (int) (newY + avatarHeight) / tileHeight;

        for (int tileX = startTileX; tileX <= endTileX; tileX++) {
            for (int tileY = startTileY; tileY <= endTileY; tileY++) {
                int tileId = map.getTileId(tileX, tileY, wallLayer);
                if (tileId != 0) return true;
            }
        }
        return false;
    }

    // Methode zur Kollisionserkennung zwischen Spieler und Gegner
    private boolean isCollidingWithEnemy(Enemy enemy) {
        float playerWidth = cinimini.getWidth();
        float playerHeight = cinimini.getHeight();

        float enemyX = enemy.getX();
        float enemyY = enemy.getY();
        float enemyWidth = enemy.getImage().getWidth();
        float enemyHeight = enemy.getImage().getHeight();

        // Prüfe, ob sich die Rechtecke des Spielers und des Gegners überschneiden
        return x < enemyX + enemyWidth &&
                x + playerWidth > enemyX &&
                y < enemyY + enemyHeight &&
                y + playerHeight > enemyY;
    }


    // Methode zur Überprüfung, ob sich der Gegner mit dem Spieler überschneidet
    private boolean isOverlappingWithPlayer(float enemyX, float enemyY) {
        float playerWidth = cinimini.getWidth();
        float playerHeight = cinimini.getHeight();

        float enemyWidth = enemyImage.getWidth();
        float enemyHeight = enemyImage.getHeight();

        // Überprüfe, ob sich die Rechtecke des Spielers und des Gegners überschneiden
        return enemyX < x + playerWidth &&
                enemyX + enemyWidth > x &&
                enemyY < y + playerHeight &&
                enemyY + enemyHeight > y;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        if (gameOver) {
            graphics.drawString("Game Over!", gameContainer.getWidth() / 2 - 50, gameContainer.getHeight() / 2);
            return;
        }

        // Render die Karte an der richtigen Position, basierend auf der Kamera
        map.render(-Math.round(cameraX), -Math.round(cameraY));

        // Zeichne den Spieler (unter Berücksichtigung des Kamera-Offsets)
        graphics.drawImage(cinimini, x - cameraX, y - cameraY);
        graphics.drawString("escape the zombie🧟", 500, 50);

        // Zeichne Gegner (unter Berücksichtigung des Kamera-Offsets)
        for (Enemy enemy : enemies) {
            enemy.render(graphics, cameraX, cameraY);  // Kamera-Offset an Gegner übergeben
        }
    }
}
