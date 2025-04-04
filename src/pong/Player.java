package pong;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class Player {
        private Rectangle player;
        private int points;

    public Player(int x, int y, int width, int height) {
        player = new Rectangle(x, y, width, height);
        this.points = 0;
    }

    public boolean intersects(Shape shape) {
        return this.player.intersects(shape);
    }

    public Rectangle getShape() {
        return player;
    }

    public int getPoints() {
        return points;
    }

    public void addPoint() {
        this.points = points + 1;
    }
}

