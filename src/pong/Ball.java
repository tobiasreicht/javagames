package pong;
import java.util.Random;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;


public class Ball {
    private Circle ball;
    private float speed;
    private float angle;

    public Ball(int x, int y) {
        Random r = new Random();
        this.ball = new Circle(x, y, PongGame.BALL_RADIUS);
        this.speed = 0;

        if (r.nextBoolean()) {
            this.angle = r.nextInt(80) + 40;
        }

        else {
            this.angle = r.nextInt(80) + 230;
        }
    }


    public Shape getShape() {
        return ball;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
