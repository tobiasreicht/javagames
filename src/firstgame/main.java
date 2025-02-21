package firstgame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class main {
    public static void main(String[] argv) {
            try {
                AppGameContainer app = new AppGameContainer(new game("Cininimi Runner"));
                app.setDisplayMode(800, 600, false);
                app.start();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
}

