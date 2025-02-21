package firstgame;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;
import java.util.ArrayList;
import java.util.List;

class Enemy {
    private float x, y;
    private float speed = 0.1f;
    private Image image;
    private TiledMap map;

    public Enemy(float x, float y, Image image, TiledMap map) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.map = map;
    }

    public void moveTowards(float targetX, float targetY, int delta) {
        float dx = targetX - x;
        float dy = targetY - y;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1) {
            float newX = x + (dx / distance) * speed * delta;
            float newY = y + (dy / distance) * speed * delta;

            if (!isBlocked(newX, newY)) {
                x = newX;
                y = newY;
            }
        }
    }

    private boolean isBlocked(float newX, float newY) {
        int wallLayer = map.getLayerIndex("wand");
        if (wallLayer == -1) return false;

        int tileWidth = map.getTileWidth();
        int tileHeight = map.getTileHeight();

        int tileX = (int) newX / tileWidth;
        int tileY = (int) newY / tileHeight;

        int tileId = map.getTileId(tileX, tileY, wallLayer);
        return tileId != 0;
    }

    public void render(Graphics graphics, float cameraX, float cameraY) {
        // Render Gegner unter Berücksichtigung des Kamera-Offsets
        graphics.drawImage(image, x - cameraX, y - cameraY);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }
}


