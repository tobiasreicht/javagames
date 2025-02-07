package firstgame;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tests.AnimationTest;

import java.awt.*;

public class main {
    public static void main(String[] argv) {
        try {
            AppGameContainer container = new AppGameContainer(new rectangle("rectangles"));
            container.setDisplayMode(1200,900,false);
            container.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


}
